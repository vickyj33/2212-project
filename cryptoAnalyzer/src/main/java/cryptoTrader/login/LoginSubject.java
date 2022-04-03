package cryptoTrader.login;

/**
 * This class specifies a common interface for LoginProxy and LoginServer class.
 *
 * It is used too, to restrict the user only to running the app (by requesting a login).
 *
 * @author Hala Elewa
 */
public abstract class LoginSubject {
	public abstract void requestLogin();
}
