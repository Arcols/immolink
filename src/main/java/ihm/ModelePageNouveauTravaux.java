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
            java.sql.Date sqlDateDebut = new java.sql.Date(pageNouveauTravaux.getDateChooserDebut().getDate().getTime());
            java.sql.Date sqlDateFin = new java.sql.Date(pageNouveauTravaux.getDateChooserFin().getDate().getTime());
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
