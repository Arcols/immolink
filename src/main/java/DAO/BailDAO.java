package DAO;

import java.sql.Date;
import java.util.List;

import classes.Bail;
import classes.BienLouable;

public interface BailDAO {

        /**
         * Cree un nouveau bail dans la base de donnees.
         * @param bail L'objet bail à inserer
         * @throws DAOException en cas d'erreur lors de la creation du bien
         */
        void create(Bail bail) throws DAOException;

        /**
         * Recupère tous les loyers de tous les bails de la base de donnees.
         * @return le montant total des loyers
         */
        double getAllLoyer();

        /**
         * Recupère l'identifiant d'un bail dans la base de donnees.
         * @param bail L'objet bail à inserer
         * @return l'identifiant du bail
         */
        int getId(Bail bail);

        /**
         *  Recupère la liste des identifiants des baux associes à un bien
         * @param id_bien l'identifiant du bien
         * @return la liste des identifiants des baux associes à un bien
         */
        List<Integer> getIDBeaux(Integer id_bien);


        /**
         * Recupère un bail de la base de donnees en utilisant son identifiant.
         * @return L'objet Bail trouve, ou null si aucun bail n'est trouve
         */
        List<Bail> getAllBaux();

        /**
         * Supprime un bail de la base de donnees en utilisant son identifiant.
         * @param id_bail L'identifiant unique du bail à supprimer
         */
        void delete(int id_bail) throws DAOException;

        /**
         * Recupère un bail de la base de donnees en utilisant son identifiant.
         * @param idBail L'identifiant du bail à recuperer
         * @return L'objet Bail trouve, ou null si aucun bail n'est trouve
         */
        Integer getIdBienLouable(int idBail);

        /**
         * Recupère un bail de la base de donnees en utilisant son identifiant.
         * @param idBail L'identifiant du bail à recuperer
         * @return L'objet Bail trouve, ou null si aucun bail n'est trouve
         */
        Bail getBailFromId(int idBail);

        /**
         * Recupère un bail de la base de donnees en utilisant son bien et sa date de debut.
         * @param bien L'objet bien à rechercher
         * @param date_debut La date de debut du bail à rechercher
         * @return
         */
        Bail getBailFromBienEtDate(BienLouable bien, Date date_debut);

        /**
         * Met à jour le loyer d'un bail dans la base de donnees.
         * @param idBail L'identifiant du bail à mettre à jour
         * @param loyer  Le nouveau loyer
         */
        void updateLoyer(int idBail, double loyer);

        /**
         * Met à jour l'icc d'un bail dans la base de donnees.
         * @param idBail
         * @param icc
         */
        void updateICC(int idBail,double icc);

        /**
         * Met à jour la date de debut d'un bail dans la base de donnees.
         * @param idBail L'identifiant du bail à mettre à jour
         * @param dateDernierAnniv
         */
        void updateDateDernierAnniversaire(int idBail,Date dateDernierAnniv);

        /**
         * Met à jour la date de debut d'un bail dans la base de donnees.
         * @param idBail L'identifiant du bail à mettre à jour
         * @param previsionPourCharge La nouvelle provision pour charge
         */
        void updateProvisionPourCharge(int idBail, double previsionPourCharge);

        /**
         * Met à jour l'icc d'un bail dans la base de donnees.
         * @param idBail id du bail à modifier
         * @param nouvelIndexe remplace l'indexe du bail
         */
        void updateIndexeEau(int idBail,int nouvelIndexe);
}
