package DAO;

public interface RegimeDAO {

    Integer idRegime = 1;

    /**
     * Recupere la valeur du regime microfoncier
     * @return un float de la valeur du regime microfoncier
     * @throws DAOException en cas d'erreur lors de la recuperation du regime
     */
    public Float getValeur();

    /**
     * Met a jour la valeur du regime microfoncier
     * @param nouvelleValeur float de la nouvelle valeur du regime microfoncier
     * @throws DAOException en cas d'erreur lors de la mise à jour du regime
     */
    public void updateValeur(Float nouvelleValeur);
}
