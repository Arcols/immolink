package ihm;

import DAO.BienLouableDAO;
import DAO.DAOException;
import DAO.jdbc.DevisDAO;
import classes.Bail;
import classes.BienLouable;
import classes.Devis;
import enumeration.TypeLogement;
import modele.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ModelePageUnTravail {
    private PageUnTravail pageUnTravail;

    public ModelePageUnTravail(PageUnTravail pageUnTravail) {
        this.pageUnTravail = pageUnTravail;
    }



    public ActionListener getSupprimerTravauxListener(Integer idTravail, Integer idBien) throws DAOException {

        return e -> {
            DevisDAO devisDAO = new DevisDAO();
            try {
                // Enregistrer le devis dans la base de données
                devisDAO.delete(idTravail);

                // Afficher une popup de confirmation
                JOptionPane.showMessageDialog(null, "Les travaux ont été enregistrés avec succès.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                refreshPage(e,idBien);
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

    public ActionListener quitterPage(Integer idBien){
        return e -> {
            pageUnTravail.getFrame().dispose();
            try {
                PageMonBien pageMonBien = new PageMonBien(idBien);
            } catch (DAOException ex) {
                throw new RuntimeException(ex);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        };
    }
}
