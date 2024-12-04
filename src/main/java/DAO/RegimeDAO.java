package DAO;

public interface RegimeDAO {

    /**
     * Récupère la valeur du regime microfoncier
     * @return un float de la valeur du regime microfoncier
     * @throws DAOException en cas d'erreur lors de la lecture des locataires
     */
    public Float getValeur();
    public void updateValeur(Float nouvelleValeur);
}
