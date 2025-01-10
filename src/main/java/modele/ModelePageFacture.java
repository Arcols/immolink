package modele;

import DAO.DAOException;
import DAO.jdbc.ChargeDAO;
import DAO.jdbc.FactureDAO;
import classes.Facture;
import ihm.PageCharge;
import ihm.PageFacture;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModelePageFacture {
    private PageFacture pageFacture;

    public ModelePageFacture(PageFacture pageFacture) {
        this.pageFacture= pageFacture;
    }


    public ActionListener quitterPage() {
        return e -> {
            pageFacture.getFrame().dispose();
            new PageCharge(pageFacture.getId_bail());
        };
    }

    public ActionListener ajouterFacture(){
        return e ->
            {
                FactureDAO daofacture = new FactureDAO();
                java.sql.Date date = new java.sql.Date(this.pageFacture.getDateChooser().getDate().getTime());
                Facture factureACreer = new Facture(this.pageFacture.getChoix_num_facture().getText(),
                        this.pageFacture.getChoix_type().getSelectedItem().toString(),
                        date,
                        Double.valueOf(this.pageFacture.getChoix_montant().getText()));
                try {
                    int id_charge = new ChargeDAO().getId(this.pageFacture.getChoix_type().getSelectedItem().toString(),this.pageFacture.getId_bail());
                    daofacture.create(factureACreer,id_charge);
                } catch (DAOException ex) {
                    throw new RuntimeException(ex);
                }
                refreshPage(e);
            };
    }

    private void refreshPage(ActionEvent e) {
        JFrame ancienneFenetre = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
        ancienneFenetre.dispose();
        PageFacture nouvellePage = new PageFacture(pageFacture.getId_bail());
        nouvellePage.getFrame().setVisible(true);
    }

    public DocumentListener getTextFieldDocumentListener() {
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                pageFacture.checkFields();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                pageFacture.checkFields();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                pageFacture.checkFields();
            }
        };
    }

}
