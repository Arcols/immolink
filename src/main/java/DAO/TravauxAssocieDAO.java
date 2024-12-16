package DAO;

import classes.Devis;
import enumeration.TypeLogement;

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

}
