package DAO;

import java.sql.Date;
import java.util.List;

import classes.Bail;
import classes.BienLouable;

public interface BailDAO {

        /**
         * Crée un nouveau bail dans la base de données.
         * @param bail L'objet bail à insérer
         * @throws DAOException en cas d'erreur lors de la création du bien
         */
        void create(Bail bail) throws DAOException;

        /**
         * Récupère tous les loyers de tous les bails de la base de données.
         * @return le montant total des loyers
         */
        double getAllLoyer();

        /**
         * Récupère l'identifiant d'un bail dans la base de données.
         * @param bail L'objet bail à insérer
         * @return l'identifiant du bail
         */
        int getId(Bail bail);

        /**
         *  Récupère la liste des identifiants des baux associés à un bien
         * @param id_bien l'identifiant du bien
         * @return la liste des identifiants des baux associés à un bien
         */
        List<Integer> getIDBeaux(Integer id_bien);

        /**
         * Récupère un bail de la base de données en utilisant son identifiant.
         * @return L'objet Bail trouvé, ou null si aucun bail n'est trouvé
         */
        List<Bail> getAllBaux();

        /**
         * Supprime un bail de la base de données en utilisant son identifiant.
         * @param id_bail L'identifiant unique du bail à supprimer
         */
        void delete(int id_bail);

        /**
         * Récupère un bail de la base de données en utilisant son identifiant.
         * @param idBail L'identifiant du bail à récupérer
         * @return L'objet Bail trouvé, ou null si aucun bail n'est trouvé
         */
        Integer getIdBienLouable(int idBail);

        /**
         * Récupère un bail de la base de données en utilisant son identifiant.
         * @param idBail L'identifiant du bail à récupérer
         * @return L'objet Bail trouvé, ou null si aucun bail n'est trouvé
         */
        Bail getBailFromId(int idBail);

        Bail getBailFromBienEtDate(BienLouable bien, Date date_debut);
        /**
         * Met à jour le loyer d'un bail dans la base de données.
         * @param idBail L'identifiant du bail à mettre à jour
         * @param loyer  Le nouveau loyer
         */
        void updateLoyer(int idBail, double loyer);
}
