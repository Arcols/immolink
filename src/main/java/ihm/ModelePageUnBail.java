package ihm;

import DAO.DAOException;
import DAO.jdbc.BailDAO;
import DAO.jdbc.BienLouableDAO;
import DAO.jdbc.DevisDAO;
import DAO.jdbc.LogementDAO;
import classes.Bail;
import classes.BienLouable;
import classes.Logement;
import enumeration.TypeLogement;
import modele.PageMonBien;
import modele.PageUnBail;

public class ModelePageUnBail {
    private PageUnBail pageUnBail;

    public ModelePageUnBail(PageUnBail pageUnBail){
        this.pageUnBail = pageUnBail;
    }

    public void chargerDonneesBail(int idBail, PageUnBail page) throws DAOException {
        try {
            // Récupération des informations du bien via le DAO
            BailDAO bailDAO = new DAO.jdbc.BailDAO();
            Bail bail = bailDAO.getBailFromId(idBail);

            BienLouableDAO bienLouableDAO = new DAO.jdbc.BienLouableDAO();
            BienLouable bienLouable = bienLouableDAO.readId(bailDAO.getIdBienLouable(idBail));

            LogementDAO logementDAO = new DAO.jdbc.LogementDAO();
            Logement logement = logementDAO.read(bailDAO.getIdBienLouable(idBail));

            DevisDAO devisDAO =new DevisDAO();

            if (bail != null) {
                // Mise à jour des labels avec les informations du bien
                page.getAffichageVille().setText(bienLouable.getVille());
                page.getAffichageAdresse().setText(bienLouable.getAdresse());
                page.getAffichageComplement().setText(bienLouable.getComplement_adresse());
                page.getAffichageSurface().setText(String.valueOf(logement.getSurface()));
                page.getAffichageNbPieces().setText(String.valueOf(logement.getNbPiece()));
                page.getAffichageLoyer().setText(String.valueOf(bail.getLoyer()));
                page.getAffichageProvision().setText(String.valueOf(bail.getCharge()));
                page.getAffichageGarantie().setText(String.valueOf(bail.getDepot_garantie()));
            }
        } catch (DAOException e) {
            throw new DAOException("Erreur lors du chargement des informations du bien : " + e.getMessage(), e);
        }
    }
}
