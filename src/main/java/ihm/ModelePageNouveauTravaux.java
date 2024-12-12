package ihm;

import DAO.BienLouableDAO;
import DAO.DAOException;
import DAO.jdbc.DevisDAO;
import classes.BienLouable;
import classes.Devis;
import enumeration.TypeLogement;
import modele.PageNouveauTravaux;

import java.awt.event.ActionListener;

public class ModelePageNouveauTravaux {
    private PageNouveauTravaux pageNouveauTravaux;

    public ModelePageNouveauTravaux(PageNouveauTravaux pageNouveauTravaux) {
        this.pageNouveauTravaux = pageNouveauTravaux;
    }



    public ActionListener getAjouterTravauxListener(Integer id) throws DAOException {
        BienLouableDAO bienLouableDAO = new DAO.jdbc.BienLouableDAO();
        BienLouable bienLouable = bienLouableDAO.readId(id);
        return e -> {
            java.sql.Date sqlDateDebut = java.sql.Date.valueOf(pageNouveauTravaux.getValueDateDebut().getText());
            java.sql.Date sqlDateFin = java.sql.Date.valueOf(pageNouveauTravaux.getValueDateFin().getText());
            Devis d = new Devis(pageNouveauTravaux.getValueNumDevis().getText(),
                    Float.valueOf(pageNouveauTravaux.getValueMontantDevis().getText()),
                    pageNouveauTravaux.getValueNature().getText(),
                    Float.valueOf(pageNouveauTravaux.getValueMontantTravaux().getText()),
                    sqlDateDebut, sqlDateFin,
                    pageNouveauTravaux.getValueType().getText(),
                    pageNouveauTravaux.getValueAdresse().getText(),
                    pageNouveauTravaux.getValueNom().getText());
            DevisDAO devisDAO = new DevisDAO();
            try {
                devisDAO.create(d,bienLouable.getNumero_fiscal(), TypeLogement.APPARTEMENT);
            } catch (DAOException ex) {
                throw new RuntimeException(ex);
            }
        };
    }
}
