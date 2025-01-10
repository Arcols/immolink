package modele;

import static java.lang.String.*;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import DAO.DAOException;
import DAO.jdbc.DevisDAO;
import DAO.jdbc.TravauxAssocieDAO;
import classes.Devis;
import enumeration.TypeLogement;
import ihm.PageMonBien;
import ihm.PageUnTravail;

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
            page.getValueNumFacture().setText(valueOf(devis.getNumFacture()));
            page.getValueMontantDevis().setText(valueOf(devis.getMontantDevis()));
            page.getValueMontantTravaux().setText(valueOf(devis.getMontantTravaux()));
            page.getValueNature().setText(devis.getNature());
            page.getValueAdresse().setText(devis.getAdresseEntreprise());
            page.getValueNom().setText(devis.getNomEntreprise());
            page.getValueType().setText(devis.getType());
            page.getValueDateDebut().setText(valueOf(devis.getDateDebut()));
            page.getValueDateFin().setText(valueOf(devis.getDateFacture()));
        }
    }

    public ActionListener getSupprimerTravauxListener(Integer idTravail, Integer idBien,TypeLogement typeLogement) throws DAOException {
        return e -> {
            DevisDAO devisDAO = new DevisDAO();
            TravauxAssocieDAO travauxAssocieDAO = new TravauxAssocieDAO();
            try {
                // Enregistrer le devis dans la base de données
                devisDAO.delete(idTravail);
                travauxAssocieDAO.delete(idTravail,idBien,typeLogement);

                // Afficher une popup de confirmation
                JOptionPane.showMessageDialog(null, "Le travail a été supprimé avec succès.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                refreshPage(e,idBien,typeLogement);
            } catch (DAOException | SQLException ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    private void refreshPage(ActionEvent e, Integer idBail,TypeLogement typeLogement) throws DAOException, SQLException {
        JFrame ancienneFenetre = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
        ancienneFenetre.dispose();
        PageMonBien nouvellePage = new PageMonBien(idBail,typeLogement);
        nouvellePage.getFrame().setVisible(true);
    }

    public ActionListener quitterPage(Integer idBien,TypeLogement typeLogement){
        return e -> {
            pageUnTravail.getFrame().dispose();
            try {
                PageMonBien pageMonBien = new PageMonBien(idBien,typeLogement);
            } catch (DAOException ex) {
                throw new RuntimeException(ex);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        };
    }
}
