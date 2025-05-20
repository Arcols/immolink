package modele;

import java.awt.Component;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import DAO.BienLouableDAO;
import DAO.DAOException;
import DAO.db.ConnectionDB;
import DAO.jdbc.BatimentDAO;
import DAO.jdbc.DevisDAO;
import classes.Batiment;
import classes.BienLouable;
import classes.Devis;
import enumeration.TypeLogement;
import ihm.PageMonBien;
import ihm.PageNouveauTravaux;

public class ModelePageNouveauTravaux {
    private final PageNouveauTravaux page_nouveau_travaux;

    public ModelePageNouveauTravaux(PageNouveauTravaux page_nouveau_travaux) {
        this.page_nouveau_travaux = page_nouveau_travaux;
    }

    public ActionListener getAjouterTravauxListener(Integer id,TypeLogement typeLogement) throws DAOException {
        return e -> {
            try {
                // Vérification si le montant des travaux est un float
                Float.parseFloat(page_nouveau_travaux.getValueMontantDevis().getText());
                Float.parseFloat(page_nouveau_travaux.getValueMontantTravaux().getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Le montant des travaux et des devis doivent être un nombre.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            java.sql.Date date_debut = new java.sql.Date(page_nouveau_travaux.getDateChooserDebut().getDate().getTime());
            java.sql.Date date_facture = new java.sql.Date(page_nouveau_travaux.getDateChooserFacture().getDate().getTime());
            Devis d = new Devis(page_nouveau_travaux.getValueNumDevis().getText(),page_nouveau_travaux.getValueNumFacture().getText(), Float.valueOf(page_nouveau_travaux.getValueMontantDevis().getText()),page_nouveau_travaux.getValueNature().getText(), Float.valueOf(page_nouveau_travaux.getValueMontantTravaux().getText()), date_debut, date_facture, page_nouveau_travaux.getValueType().getText(), page_nouveau_travaux.getValueAdresse().getText(), page_nouveau_travaux.getValueNom().getText());

            DevisDAO devis_DAO = new DevisDAO();
            try {
                if(typeLogement == TypeLogement.BATIMENT){
                    BatimentDAO batiment_DAO = new DAO.jdbc.BatimentDAO();
                    Batiment batiment = batiment_DAO.readId(id);
                    devis_DAO.create(d, batiment.getNumeroFiscal(), typeLogement);
                }else{
                    BienLouableDAO bien_louable_DAO = new DAO.jdbc.BienLouableDAO();
                    BienLouable bienLouable = bien_louable_DAO.readId(id);
                    devis_DAO.create(d, bienLouable.getNumeroFiscal(), typeLogement);
                }

                // Afficher une popup de confirmation
                JOptionPane.showMessageDialog(null, "Les travaux ont été enregistrés avec succès.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                refreshPage(e,id,typeLogement);
            } catch (DAOException ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    private void refreshPage(ActionEvent e, Integer IdBien,TypeLogement typeLogement) throws DAOException {
        JFrame ancienne_fenetre = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
        ancienne_fenetre.dispose();
        int x=ancienne_fenetre.getX();
        int y=ancienne_fenetre.getY();

        PageNouveauTravaux nouvelle_fenetre = new PageNouveauTravaux(IdBien,typeLogement,x,y);
        nouvelle_fenetre.getFrame().setVisible(true);
    }
    public ActionListener quitterPage(int id,TypeLogement typeLogement){
        return e -> {
            page_nouveau_travaux.getFrame().dispose();
            try {
                int x=page_nouveau_travaux.getFrame().getX();
                int y=page_nouveau_travaux.getFrame().getY();
                new PageMonBien(id,typeLogement,x,y);
            } catch (DAOException | SQLException ex) {
                throw new RuntimeException(ex);
            }
        };
    }
    public DocumentListener getTextFieldDocumentListener() {
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkFields();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {checkFields();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkFields();
            }
        };
    }


    public void checkFields() {
        // Vérification si tous les champs sont remplis
        boolean isFilled = !page_nouveau_travaux.getValueMontantDevis().getText().trim().isEmpty()
                && !page_nouveau_travaux.getValueMontantTravaux() .getText().trim().isEmpty() && !page_nouveau_travaux.getValueNature().getText().trim().isEmpty()
                && !page_nouveau_travaux.getValueAdresse().getText().trim().isEmpty()
                && !page_nouveau_travaux.getValueNom().getText().trim().isEmpty()
                && page_nouveau_travaux.getDateChooserDebut().getDate() != null
                && page_nouveau_travaux.getDateChooserFacture().getDate() != null;

        // Active ou désactive le bouton "Valider"
        page_nouveau_travaux.getBtnValider().setEnabled(isFilled);
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
        page_nouveau_travaux.getFrame().dispose();
    }
}
