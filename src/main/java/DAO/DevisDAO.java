package DAO;

import  classes.Devis;
import enumeration.TypeLogement;

import java.sql.Date;
import java.util.List;

public interface DevisDAO {

    /**
     * Cree un nouveau devis dans la base de donnees.
     * @param devis L'objet devis à inserer
     * @param num_fiscal Le numero fiscal du bien associe au devis
     * @param typeLogement Le type de logement associe au devis
     * @throws DAOException en cas d'erreur lors de la creation du devis
     */
    void create(Devis devis, String num_fiscal, TypeLogement typeLogement) throws DAOException;

    /**
     * Trouve un devis dans la base de donnees à l'aide de son num_devis.
     * @param num_devis Le numero du devis à trouver
     * @return L'objet devis trouve
     * @throws DAOException en cas d'erreur lors de la recherche du devis
     */
    Devis read(String num_devis) throws DAOException;

    /**
     * Recupère l'identifiant d'un devis dans la base de donnees.
     * @param devis L'objet devis à rechercher
     * @return L'identifiant du devis
     */
    Integer getId(Devis devis);

    /**
     * Retourne une liste de devis d'un bien mis en paramètre
     * @param num_fiscal le numero fiscal du bien
     * @param typeLogement le type de logement
     * @return la liste des devis
     */
    List<Devis> getAllDevisFromABien(String num_fiscal,TypeLogement typeLogement);

    /**
     * Retourne une liste de devis d'un bien mis en paramètre
     * @param num_fiscal le numero fiscal du bien
     * @param typeLogement le type de logement
     * @param annee Date pour avoir l'annee des devis
     * @return la liste des devis
     */
    List<Devis> getAllDevisFromABienAndDate(String num_fiscal, TypeLogement typeLogement, Date annee);



    /**
     * Recupère le montant total des travaux d'un bien
     * @param num_fiscal
     * @param typeLogement
     * @return
     */
    double getMontantTotalTravaux(String num_fiscal, TypeLogement typeLogement);

    /**
     * Recupère un devis à partir de son identifiant
     * @param id l'identifiant du devis
     * @return le devis
     */
    Devis readId(Integer id);

    /**
     * Supprime un devis de la base de donnees en utilisant son identifiant.
     * @param id L'identifiant unique du devis à supprimer
     */
    void delete(Integer id);
}
