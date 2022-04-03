package cryptoTrader.login;

import java.io.IOException;

/**
 * The validateUser interface used to validate user's credentials.
 *
 * @author Hala Elewa
 */
public interface validateUser {
	
	public boolean validateUser(String username, String password) throws IOException;
}
