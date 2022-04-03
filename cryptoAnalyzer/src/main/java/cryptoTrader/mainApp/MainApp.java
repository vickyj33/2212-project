package cryptoTrader.mainApp;

import javax.swing.JFrame;

import cryptoTrader.gui.MainUI;
import cryptoTrader.login.LoginProxy;
import cryptoTrader.login.LoginSubject;

/**
 * This is the main class where we run the whole app.
 * The Login app designed using proxy design pattern.
 *
 * @author Hala Elewa
 * @author Ian Guenther Green
 */
public class MainApp {

    public static void main(String[] args) {
        // create a loginProxy instance that references to loginSubject
        LoginSubject loginProxy = new LoginProxy();
        loginProxy.requestLogin(); // request from the loginProxy to run the program
		
    }
}
