package ihm;

import DAO.BienLouableDAO;
import DAO.DAOException;
import DAO.jdbc.DevisDAO;
import classes.Bail;
import classes.BienLouable;
import classes.Devis;
import enumeration.TypeLogement;
import modele.PageNouveauBienImmobilier;
import modele.PageNouveauTravaux;
import modele.PageUnBail;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
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
            Devis d = new Devis(pageNouveauTravaux.getValueNumDevis().getText(), Float.valueOf(pageNouveauTravaux.getValueMontantDevis().getText()), pageNouveauTravaux.getValueNature().getText(), Float.valueOf(pageNouveauTravaux.getValueMontantTravaux().getText()), sqlDateDebut, sqlDateFin, pageNouveauTravaux.getValueType().getText(), pageNouveauTravaux.getValueAdresse().getText(), pageNouveauTravaux.getValueNom().getText());

            DevisDAO devisDAO = new DevisDAO();
            try {
                // Enregistrer le devis dans la base de données
                devisDAO.create(d, bienLouable.getNumero_fiscal(), TypeLogement.APPARTEMENT);

                // Afficher une popup de confirmation
                JOptionPane.showMessageDialog(null, "Les travaux ont été enregistrés avec succès.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                refreshPage(e,id);
            } catch (DAOException ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    private void refreshPage(ActionEvent e, Integer idBail) throws DAOException {
        JFrame ancienneFenetre = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
        ancienneFenetre.dispose();
        PageNouveauTravaux nouvellePage = new PageNouveauTravaux(idBail);
        nouvellePage.getFrame().setVisible(true);
    }
}
