package DAO;

import classes.Devis;
import enumeration.TypeLogement;

import java.sql.Date;
import java.util.List;

public interface TravauxAssocieDAO {

    /**
     * Associe un nouveau devis à un bien louable dans la base de données.
     * @param num_fiscal le numéro fiscal du bien louable
     * @param devis le devis à associer
     * @param typeLogement le type de logement
     * @throws DAOException en cas d'erreur lors de la création de l'association
     */
    void create(String num_fiscal, Devis devis, TypeLogement typeLogement) throws DAOException;

    /**
     * Récupère tous les id des devis d'un bien louable dans la base de données.
     * @param num_fiscal le numéro fiscal du bien louable
     * @param typeLogement le type de logement
     * @return Une liste de tous les id des devis
     * @throws DAOException en cas d'erreur lors de la lecture des devis
     */
    List<Integer> findAll(String num_fiscal, TypeLogement typeLogement) throws DAOException;

    /**
     * Récupère tous les id des devis d'un bien en fonction d'une date
     * @param num_fiscal le numéro fiscal du bien
     * @param typeLogement le type de logement
     * @param annee date pour avoir l'annee des devis
     * @return Une liste de tous les id des devis
     * @throws DAOException en cas d'erreur lors de la lecture des devis
     */
    List<Integer> findAllWithDate(String num_fiscal, TypeLogement typeLogement, Date annee) throws DAOException;

    /**
     * Supprime une association entre un devis et un bien louable dans la base de données.
     * @param id_devis l'id du devis
     * @param id_bien l'id du bien louable
     * @throws DAOException
     */
    void delete (Integer id_devis, Integer id_bien, TypeLogement typeLogement) throws DAOException;

}
