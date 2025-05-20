package modele;

import static java.lang.String.*;

import java.awt.Component;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import DAO.jdbc.DevisDAO;
import DAO.jdbc.TravauxAssocieDAO;
import classes.Devis;
import enumeration.TypeLogement;
import ihm.PageMonBien;
import ihm.PageUnTravail;

public class ModelePageUnTravail {
    private final PageUnTravail page_un_travail;

    public ModelePageUnTravail(PageUnTravail page_un_travail) {
        this.page_un_travail = page_un_travail;
    }

    public void chargerDonneesTravail(int id_travail, PageUnTravail page) throws DAOException {
        // Récupération des informations du bien via le DAO
        DevisDAO devis_DAO = new DevisDAO();
        Devis devis = devis_DAO.readId(id_travail);

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

    public ActionListener getSupprimerTravauxListener(Integer id_travail, Integer idBien,TypeLogement typeLogement) {
        return e -> {
            DevisDAO devis_DAO = new DevisDAO();
            TravauxAssocieDAO travaux_associes_DAO = new TravauxAssocieDAO();
            try {
                // Enregistrer le devis dans la base de données
                travaux_associes_DAO.delete(id_travail,idBien,typeLogement);
                devis_DAO.delete(id_travail);

                // Afficher une popup de confirmation
                JOptionPane.showMessageDialog(null, "Le travail a été supprimé avec succès.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                refreshPage(e,idBien,typeLogement);
            } catch (DAOException | SQLException ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    private void refreshPage(ActionEvent e, Integer idBail,TypeLogement typeLogement) throws DAOException, SQLException {
        JFrame ancienne_fenetre = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
        ancienne_fenetre.dispose();
        int x=ancienne_fenetre.getX();
        int y=ancienne_fenetre.getY();
        PageMonBien nouvelle_page = new PageMonBien(idBail,typeLogement,x,y);
        nouvelle_page.getFrame().setVisible(true);
    }

    public ActionListener quitterPage(Integer idBien,TypeLogement typeLogement){
        return e -> {
            page_un_travail.getFrame().dispose();
            int x=page_un_travail.getFrame().getX();
            int y=page_un_travail.getFrame().getY();

            try {
                new PageMonBien(idBien,typeLogement,x,y);
            } catch (DAOException | SQLException ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    public WindowListener fermerFenetre(){
        return new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Action to perform on application close
                performCloseAction();
            }
        };
    }
    private void performCloseAction() {
        ConnectionDB.destroy(); // fermeture de la connection
        page_un_travail.getFrame().dispose();
    }
}
