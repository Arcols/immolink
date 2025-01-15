package modele;

import DAO.DAOException;
import DAO.jdbc.BailDAO;
import DAO.jdbc.ChargeDAO;
import DAO.jdbc.FactureDAO;
import classes.Bail;
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
                Facture factureACreer = null;

                if((String) pageFacture.getChoix_type().getSelectedItem() == "Eau"){
                    BailDAO bailDAO = new BailDAO();
                    Bail bail = bailDAO.getBailFromId(pageFacture.getId_bail());
                    Double prix_facture = (Double.valueOf(pageFacture.getChoix_index().getText()) - bail.getIndexEau()) * Double.valueOf(pageFacture.getChoix_prix_conso().getText());
                    factureACreer = new Facture(this.pageFacture.getChoix_num_facture().getText(),
                            this.pageFacture.getChoix_type().getSelectedItem().toString(),
                            date,
                            prix_facture);
                    bailDAO.updateIndexeEau(pageFacture.getId_bail(), Integer.valueOf(pageFacture.getChoix_index().getText()));
                } else {
                    factureACreer = new Facture(this.pageFacture.getChoix_num_facture().getText(),
                            this.pageFacture.getChoix_type().getSelectedItem().toString(),
                            date,
                            Double.valueOf(this.pageFacture.getChoix_montant().getText()));
                }
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

    public ActionListener eauSelected() {
        return e -> {
            String selectedItem = (String) pageFacture.getChoix_type().getSelectedItem();

            boolean isEau = (selectedItem == "Eau");

            this.pageFacture.getLabelMontant().setVisible(!isEau);
            this.pageFacture.getChoix_montant().setVisible(!isEau);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(0, 0, 5, 0);

            if(isEau){
                gbc.gridx = 1;
                gbc.gridy = 2;
                this.pageFacture.getContenu()
                        .add(this.pageFacture.getLabel_index(), gbc);
            } else{
                this.pageFacture.getContenu()
                        .remove(this.pageFacture.getLabel_index());
            }
            if(isEau){
                gbc.gridx = 2;
                gbc.gridy = 2;
                this.pageFacture.getContenu()
                        .add(this.pageFacture.getChoix_index(), gbc);
            } else{
                this.pageFacture.getContenu()
                        .remove(this.pageFacture.getChoix_index());
            }

            if(isEau){
                gbc.gridx = 1;
                gbc.gridy = 3;
                this.pageFacture.getContenu()
                        .add(this.pageFacture.getLabel_prix_conso(), gbc);
            } else{
                this.pageFacture.getContenu()
                        .remove(this.pageFacture.getLabel_prix_conso());
            }
            if(isEau){
                gbc.gridx = 2;
                gbc.gridy = 3;
                this.pageFacture.getContenu()
                        .add(this.pageFacture.getChoix_prix_conso(), gbc);
            } else{
                this.pageFacture.getContenu()
                        .remove(this.pageFacture.getChoix_prix_conso());
            }

            if(isEau){
                this.pageFacture.getContenu()
                        .remove(this.pageFacture.getDate());
                gbc.gridx = 1;
                gbc.gridy = 4;
                this.pageFacture.getContenu()
                        .add(this.pageFacture.getDate(), gbc);
            } else{
                this.pageFacture.getContenu()
                        .remove(this.pageFacture.getDate());
                gbc.gridx = 1;
                gbc.gridy = 3;
                this.pageFacture.getContenu()
                        .add(this.pageFacture.getDate(), gbc);
            }

            if(isEau){
                this.pageFacture.getContenu()
                        .remove(this.pageFacture.getDateChooser());
                gbc.gridx = 2;
                gbc.gridy = 4;
                this.pageFacture.getContenu()
                        .add(this.pageFacture.getDateChooser(), gbc);
            } else{
                this.pageFacture.getContenu()
                        .remove(this.pageFacture.getDateChooser());
                gbc.gridx = 2;
                gbc.gridy = 3;
                this.pageFacture.getContenu()
                        .add(this.pageFacture.getDateChooser(), gbc);
            }
        };
    }
}
