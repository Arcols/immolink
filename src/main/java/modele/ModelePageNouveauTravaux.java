package modele;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import DAO.BienLouableDAO;
import DAO.DAOException;
import DAO.jdbc.BatimentDAO;
import DAO.jdbc.DevisDAO;
import classes.Batiment;
import classes.BienLouable;
import classes.Devis;
import enumeration.TypeLogement;
import ihm.PageMonBien;
import ihm.PageNouveauTravaux;

public class ModelePageNouveauTravaux {
    private PageNouveauTravaux pageNouveauTravaux;

    public ModelePageNouveauTravaux(PageNouveauTravaux pageNouveauTravaux) {
        this.pageNouveauTravaux = pageNouveauTravaux;
    }

    public ActionListener getAjouterTravauxListener(Integer id,TypeLogement typeLogement) throws DAOException {
        return e -> {
            java.sql.Date sqlDateDebut = new java.sql.Date(pageNouveauTravaux.getDateChooserDebut().getDate().getTime());
            java.sql.Date sqlDateFacture = new java.sql.Date(pageNouveauTravaux.getDateChooserFacture().getDate().getTime());
            Devis d = new Devis(pageNouveauTravaux.getValueNumDevis().getText(),pageNouveauTravaux.getValueNumFacture().getText(), Float.valueOf(pageNouveauTravaux.getValueMontantDevis().getText()),pageNouveauTravaux.getValueNature().getText(), Float.valueOf(pageNouveauTravaux.getValueMontantTravaux().getText()), sqlDateDebut, sqlDateFacture, pageNouveauTravaux.getValueType().getText(), pageNouveauTravaux.getValueAdresse().getText(), pageNouveauTravaux.getValueNom().getText());

            DevisDAO devisDAO = new DevisDAO();
            try {
                if(typeLogement == TypeLogement.BATIMENT){
                    BatimentDAO batimentDAO = new DAO.jdbc.BatimentDAO();
                    Batiment batiment = batimentDAO.readId(id);
                    devisDAO.create(d, batiment.getNumeroFiscal(), typeLogement);
                }else{
                    BienLouableDAO bienLouableDAO = new DAO.jdbc.BienLouableDAO();
                    BienLouable bienLouable = bienLouableDAO.readId(id);
                    devisDAO.create(d, bienLouable.getNumeroFiscal(), typeLogement);
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
        JFrame ancienneFenetre = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
        ancienneFenetre.dispose();
        PageNouveauTravaux nouvellePage = new PageNouveauTravaux(IdBien,typeLogement);
        nouvellePage.getFrame().setVisible(true);
    }
    public ActionListener quitterPage(int id,TypeLogement typeLogement){
        return e -> {
            pageNouveauTravaux.getFrame().dispose();
            try {
                new PageMonBien(id,typeLogement);
            } catch (DAOException ex) {
                throw new RuntimeException(ex);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        };
    }
    public DocumentListener getTextFieldDocumentListener() {
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                pageNouveauTravaux.checkFields();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                pageNouveauTravaux.checkFields();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                pageNouveauTravaux.checkFields();
            }
        };
    }

}
