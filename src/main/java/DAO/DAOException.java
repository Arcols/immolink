package DAO;

public class DAOException extends Exception {

	/**
	 *  Constructeur de DAOException
	 * @param message Message d'erreur
	 * @param cause Cause de l'erreur
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}
}
