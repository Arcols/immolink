package DAO;

import  classes.Devis;
import enumeration.TypeLogement;

import java.util.List;

public interface DevisDAO {

    /**
     * Crée un nouveau devis dans la base de données.
     *
     * @param devis L'objet devis à insérer
     * @param num_fiscal Le numéro fiscal du bien associé au devis
     * @param typeLogement Le type de logement associé au devis
     * @throws DAOException en cas d'erreur lors de la création du devis
     */
    void create(Devis devis, String num_fiscal, TypeLogement typeLogement) throws DAOException;

    /**
     * Trouve un devis dans la base de données.
     *
     * @param reference La référence du devis à trouver
     * @return L'objet devis trouvé
     * @throws DAOException en cas d'erreur lors de la recherche du devis
     */
    Devis read(String reference) throws DAOException;

    /**
     * Récupère l'identifiant d'un devis dans la base de données.
     * @param devis L'objet devis à rechercher
     * @return L'identifiant du devis
     */
    Integer getId(Devis devis);

    /**
     * Retourne une liste de devis d'un bien mis en paramètre
     * @param num_fiscal le numéro fiscal du bien
     * @param typeLogement le type de logement
     * @return la liste des devis
     */
    List<Devis> getAllDevisFromABien(String num_fiscal,TypeLogement typeLogement);

    /**
     * Récupère un devis à partir de son identifiant
     * @param id l'identifiant du devis
     * @return le devis
     */
    Devis readId(Integer id);


    }
