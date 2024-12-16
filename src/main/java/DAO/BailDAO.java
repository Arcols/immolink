package DAO;

import java.util.List;

import classes.Bail;

public interface BailDAO {
        /**
         * Crée un nouveau bail dans la base de données.
         *
         * @param bail L'objet bail à insérer
         * @throws DAOException en cas d'erreur lors de la création du bien
         */
        void create(Bail bail) throws DAOException;

        /**
         * Récupère tous les loyers de tous les bails de la base de données.
         *
         * @return le montant total des loyers
         */
        double getAllLoyer();

        /**
         * /**
         * Récupère l'identifiant d'un bail dans la base de données.
         *
         * @param bail L'objet bail à insérer
         * @return l'identifiant du bail
         */
        int getId(Bail bail);

        /**
         * Récupère un bail de la base de données en utilisant son identifiant.
         *
         * @return L'objet Bail trouvé, ou null si aucun bail n'est trouvé
         */
        List<Bail> getAllBaux();

        /**
         * Récupère un bail de la base de données en utilisant son identifiant.
         * @param idBail L'identifiant du bail à récupérer
         * @return L'objet Bail trouvé, ou null si aucun bail n'est trouvé
         */
        int getIdBienLouable(int idBail);

        /**
         * Récupère un bail de la base de données en utilisant son identifiant.
         * @param idBail L'identifiant du bail à récupérer
         * @return L'objet Bail trouvé, ou null si aucun bail n'est trouvé
         */
        Bail getBailFromId(int idBail);

        /**
        * Met à jour le loyer d'un bail dans la base de données.
                * @param idBail L'identifiant du bail à mettre à jour
                * @param loyer  Le nouveau loyer
         */
        void updateLoyer(int idBail, double loyer);
}
