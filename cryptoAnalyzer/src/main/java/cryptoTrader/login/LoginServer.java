package cryptoTrader.login;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import cryptoTrader.gui.MainUI;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * This is LoginServer class where the login-ui sets.
 *
 * It takes the username and password from the user and validates them.
 * Then, if the credentials are right, it logs in into the main interface where trading is.
 *  	otherwise, terminate the program if no such a user.
 *
 * @author Hala Elewa
 */
public class LoginServer extends LoginSubject implements ActionListener, initiateSession, validateUser {

	// instance for this class
	private static LoginServer instance;
	
	private JFrame frame; // main frame of the programe
	private JPanel loginPanel;
	private JLabel userLabel;
	private JLabel passLabel;
	private JTextField userField;
	private JCheckBox showPassword;
	private JPasswordField passField;
	private JButton loginBtn;

	/**
	 * Simple method to get this class instance
	 *
	 * @return instance of this class
	 */
	public static LoginServer getInstance() {
		// if we don't have an instance, initiate one
		if (instance == null)
			instance = new LoginServer();
		
		return instance;
	}

	/**
	 * In the constructor we made and initialize the gui components
	 */
	private LoginServer() {
		
		// initiate the frame instance and set the login-window title
		frame = new JFrame("Login - Crypto Analyzer");
		frame.setSize(400, 250);
        
        // set the look and feel for the program
        try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {

			e.printStackTrace();
		}
        
        frame.setLocationRelativeTo(null);  // center the window for the screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // make the window close when press X
         
        // create a new panel with GridBagLayout manager
        loginPanel = new JPanel(new GridBagLayout());
         
        GridBagConstraints constraints = new GridBagConstraints();
//        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);  // determine the grid insets
        
        // create components
        userLabel = new JLabel("Username");
        passLabel = new JLabel("Password");
        userField = new JTextField(20);
        passField = new JPasswordField(20);
        showPassword = new JCheckBox("show password");
        showPassword.setFocusPainted(false);
        showPassword.addActionListener(this);
        loginBtn = new JButton("Login");
        loginBtn.setFocusPainted(false);
        loginBtn.addActionListener(this);
        loginBtn.setEnabled(false);
        loginBtn.setToolTipText("Type a user name to enable");
        
        // add a documentListener to userField to know once the user type something in userField
        userField.getDocument().addDocumentListener(new DocumentListener() {
			
        	// if there was a remove action to text field, check if the text field is empty
			@Override
			public void removeUpdate(DocumentEvent e) {
				
				// username text field is empty, disable the login button and show its tooltip
				if (userField.getText().length() == 0) {
					loginBtn.setEnabled(false);
					loginBtn.setToolTipText("Type a user name to enable");
				}
			}
			
			// if there was an insert action made to text field
			@Override
			public void insertUpdate(DocumentEvent e) {
				loginBtn.setEnabled(true);  // enable the button
				loginBtn.setToolTipText(null);  // disable the tooltip
			}
			
			// not needed
			@Override
			public void changedUpdate(DocumentEvent e) {}
		});
         
        // add components to the panel
        constraints.gridx = 0;
        constraints.gridy = 0;     
        loginPanel.add(userLabel, constraints);
 
        constraints.gridx = 1;
        loginPanel.add(userField, constraints);
         
        constraints.gridx = 0;
        constraints.gridy = 1;     
        loginPanel.add(passLabel, constraints);
         
        constraints.gridx = 1;
        loginPanel.add(passField, constraints);
        
        constraints.gridx = 1;
        constraints.gridy = 2;
        loginPanel.add(showPassword, constraints);
         
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        loginPanel.add(loginBtn, constraints);
         
        // set border for the panel
        loginPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Login Info"));
         
        frame.add(loginPanel);  // add the panel to this frame
	}

	/**
	 * @return user's username.
	 */
	private String getUser() {
		return userField.getText().strip();
	}

	/**
	 * @return user's password.
	 */
	private char[] getPassword() {
		return passField.getPassword();
	}

	/**
	 * Check if there is a user with the given username, if there is
	 * validate then the user's credentials if correct.
	 *
	 * @param username the user's username.
	 * @param password the user's password.
	 * @return true if there is such a user with provided credentials, false otherwise.
	 * @throws IOException Thrown if the database file is missing.
	 */
	private boolean validate(String username, String password) throws IOException {
		String fileName = "users.txt";
		
		BufferedReader reader = null;
		
		// check the existence of the database file
		try {
			reader = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			System.err.println("Database file (users.txt) not found!");
			System.out.println("Make sure it is found and try again!");
			System.exit(1);
		}
		
		String line = ""; // represents the current line in users.txt
		
		// stores the password that its username match the written username's password
		String storedPassword = "";
		
		int userNumbers = 0;
		int userNotFoundCounter = 0;
		
		while ((line = reader.readLine()) != null) {
			line = line.strip(); // strip any whitespace
			storedPassword = reader.readLine(); // get password from database
			userNumbers++; // count each loop as a user
			
			// the username is not found, increase the userNotFoundCounter by 1
			if (!username.equals(line)) {
				userNotFoundCounter++;
			}
			
			// username found, break the loop and login into the interface
			else {
				break;
			}
			
		}
		
		// if userNumbers and userNotFoundCounter are equal, then username not found!
		// show an error dialog
		if (userNumbers == userNotFoundCounter) {
			showErrorDialog(String.format("You have entered an invalid username - %s", username), "Invalid username");
			frame.dispose();
			return false;
		}
		
		else { // username is found, validate the user credentials
			System.out.println(storedPassword + " " + password);
			// passwords are match
			if (storedPassword.equals(password)) {
				frame.dispose();
				startSession();  // start the logged-in interface session
			}
			
			else { // password are no match, pop-up an error dialog message
				showErrorDialog("Invalid Password!", "Error - Wrong Password");
			}
		}
		
		return true;
	}

	/**
	 * @param msg Message to be presented.
	 * @param title Title of the dialog.
	 */
	private void showErrorDialog(String msg, String title) {
		JOptionPane.showMessageDialog(frame, msg, title, JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Run the login app, just by the set the visibility to true
	 */
	private void runApp() {
		frame.setVisible(true);
	}

	/**
	 * Here, we program what to happend when the loginButton is clicked
	 * or when the showPassword checkBox is clicked, and so.
	 *
	 * @param e ActionEvent which could be any component from the app.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// action triggered by loginBtn
		if (e.getSource() == loginBtn) {
			/* validate if the username is valid!
			   if so, check the credentials if correct
			   		  if right, login into the interface, otherwise show an error message dialog
			*/
			try {
				validateUser(getUser(), new String(getPassword()));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		// action triggered by showPassword check box
		else if (e.getSource() == showPassword) {
			// show password as chars
			if (showPassword.isSelected()) {
                passField.setEchoChar((char) 0);
            } else { // show password as bullet points
                passField.setEchoChar('');
            }
		}
	}

	/**
	 * Validate the user's credentials.
	 *
	 * @param username user's username.
	 * @param password user's password.
	 * @return true if the user's credentials is correct, false otherwise.
	 * @throws IOException Thrown if the database text file is missing.
	 */
	@Override
	public boolean validateUser(String username, String password) throws IOException {
		return validate(username, password);
	}

	/**
	 * Create a session for mainUI after validation is passed
	 * and open the main interface for trading.
	 */
	@Override
	public void startSession() {
		
		// Unset the look and feel for the program
        try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {

			e.printStackTrace();
		}
		
		MainUI mainUI = MainUI.getInstance();
		mainUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainUI.setSize(900, 600);
		mainUI.pack();
		mainUI.setVisible(true);
		
	}

	/**
	 * Request the login app to open.
	 */
	@Override
	public void requestLogin() {
		runApp();
	}
}
