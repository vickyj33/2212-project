package cryptoTrader.login;

/**
 * This proxy keeps a reference to the LoginServece class
 * 		it also controls access to it.
 *
 * 	It is used to lock the access to the loginServer
 * 		also, to get an instance from loginServer and request to run login app.
 *
 * @author Hala ELewa
 */
public class LoginProxy extends LoginSubject {
	private LoginServer login;

	/**
	 * request to run the login app from the loginServer
	 */
	@Override
	public void requestLogin() {
		if (login == null)
			login = LoginServer.getInstance();
		
		login.requestLogin();
	}
}
