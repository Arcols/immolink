package ihm;

import DAO.DAOException;
import DAO.jdbc.BienLouableDAO;
import DAO.jdbc.DevisDAO;
import DAO.jdbc.TravauxAssocieDAO;
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

import static java.lang.String.valueOf;

public class ModelePageUnTravail {
    private PageUnTravail pageUnTravail;

    public ModelePageUnTravail(PageUnTravail pageUnTravail) {
        this.pageUnTravail = pageUnTravail;
    }

    public void chargerDonneesTravail(int idTravail, PageUnTravail page) throws DAOException {
        // Récupération des informations du bien via le DAO
        DevisDAO devisDAO = new DevisDAO();
        Devis devis = devisDAO.readId(idTravail);

        if (devis != null) {
            // Mise à jour des labels avec les informations du bien
            page.getValueNumDevis().setText(valueOf(devis.getNumDevis()));
            page.getValueMontantDevis().setText(valueOf(devis.getMontantDevis()));
            page.getValueMontantTravaux().setText(valueOf(devis.getMontantTravaux()));
            page.getValueNature().setText(devis.getNature());
            page.getValueAdresse().setText(devis.getAdresseEntreprise());
            page.getValueNom().setText(devis.getNomEntreprise());
            page.getValueType().setText(devis.getType());
            page.getValueDateDebut().setText(valueOf(devis.getDateDebut()));
            page.getValueDateFin().setText(valueOf(devis.getDateFin()));
        }
    }

    public ActionListener getSupprimerTravauxListener(Integer idTravail, Integer idBien) throws DAOException {

        return e -> {
            DevisDAO devisDAO = new DevisDAO();
            TravauxAssocieDAO travauxAssocieDAO = new TravauxAssocieDAO();
            try {
                // Enregistrer le devis dans la base de données
                System.out.println("id travail : "+idTravail);
                System.out.println("id bien : "+idBien);
                devisDAO.delete(idTravail);
                travauxAssocieDAO.delete(idTravail,idBien);

                // Afficher une popup de confirmation
                JOptionPane.showMessageDialog(null, "Le travail a été supprimé avec succès.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                refreshPage(e,idBien);
            } catch (DAOException | SQLException ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    private void refreshPage(ActionEvent e, Integer idBail) throws DAOException, SQLException {
        JFrame ancienneFenetre = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
        ancienneFenetre.dispose();
        PageMonBien nouvellePage = new PageMonBien(idBail);
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
