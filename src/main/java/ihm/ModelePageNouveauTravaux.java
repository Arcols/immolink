package ihm;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import DAO.BienLouableDAO;
import DAO.DAOException;
import DAO.jdbc.DevisDAO;
import classes.BienLouable;
import classes.Devis;
import enumeration.TypeLogement;
import modele.PageAccueil;
import modele.PageBaux;
import modele.PageMonBien;
import modele.PageNouveauTravaux;

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
                devisDAO.create(d, bienLouable.getNumero_fiscal(), bienLouableDAO.getTypeFromId(id));

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
    public ActionListener quitterPage(int id){
        return e -> {
            pageNouveauTravaux.getFrame().dispose();
            try {
                new PageMonBien(id);
            } catch (DAOException ex) {
                throw new RuntimeException(ex);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        };
    }
}
