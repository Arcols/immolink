package DAO;

import  classes.Devis;
import enumeration.TypeLogement;

import java.sql.Date;
import java.util.List;

public interface DevisDAO {

    /**
     * Crée un nouveau devis dans la base de données.
     * @param devis L'objet devis à insérer
     * @param num_fiscal Le numéro fiscal du bien associé au devis
     * @param typeLogement Le type de logement associé au devis
     * @throws DAOException en cas d'erreur lors de la création du devis
     */
    void create(Devis devis, String num_fiscal, TypeLogement typeLogement) throws DAOException;

    /**
     * Trouve un devis dans la base de données à l'aide de son num_devis.
     * @param num_devis Le numéro du devis à trouver
     * @return L'objet devis trouvé
     * @throws DAOException en cas d'erreur lors de la recherche du devis
     */
    Devis read(String num_devis) throws DAOException;

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
     * Retourne une liste de devis d'un bien mis en paramètre
     * @param num_fiscal le numéro fiscal du bien
     * @param typeLogement le type de logement
     * @param annee Date pour avoir l'année des devis
     * @return la liste des devis
     */
    List<Devis> getAllDevisFromABienAndDate(String num_fiscal, TypeLogement typeLogement, Date annee);

    /**
     * Récupère le montant total des devis d'un bien
     * @param num_fiscal
     * @param typeLogement
     * @return
     */
    double getMontantTotalDevis(String num_fiscal, TypeLogement typeLogement);

    /**
     * Récupère le montant total des travaux d'un bien
     * @param num_fiscal
     * @param typeLogement
     * @return
     */
    double getMontantTotalTravaux(String num_fiscal, TypeLogement typeLogement);

    /**
     * Récupère un devis à partir de son identifiant
     * @param id l'identifiant du devis
     * @return le devis
     */
    Devis readId(Integer id);

    /**
     * Supprime un devis de la base de données en utilisant son identifiant.
     * @param id L'identifiant unique du devis à supprimer
     */
    void delete(Integer id);
}
