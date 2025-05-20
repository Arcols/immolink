package modele;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import DAO.jdbc.BailDAO;
import DAO.jdbc.ChargeDAO;
import DAO.jdbc.FactureDAO;
import classes.Bail;
import classes.Facture;
import com.toedter.calendar.JDateChooser;
import ihm.PageCharge;
import ihm.PageFacture;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;

public class ModelePageFacture {
    private final PageFacture page_facture;

    public ModelePageFacture(PageFacture page_facture) {
        this.page_facture= page_facture;
    }


    public ActionListener quitterPage() {
        return e -> {
            page_facture.getFrame().dispose();
            int x=page_facture.getFrame().getX();
            int y=page_facture.getFrame().getY();
            new PageCharge(page_facture.getId_bail(),x,y);
        };
    }

    public ActionListener ajouterFacture(){
        return e ->
            {
                FactureDAO facture_DAO = new FactureDAO();
                java.sql.Date date = new java.sql.Date(this.page_facture.getDateChooser().getDate().getTime());
                Facture facture_a_creer;
                boolean validite_facture_eau =true;

                if(page_facture.getChoix_type().getSelectedItem() == "Eau"){
                    BailDAO bail_DAO = new BailDAO();
                    Bail bail = bail_DAO.getBailFromId(page_facture.getId_bail());
                    int nouveau_index_eau = Integer.valueOf(page_facture.getChoix_index().getText());

                    if(nouveau_index_eau>bail.getIndexEau()) {
                        double prix_facture = (nouveau_index_eau - bail.getIndexEau()) * Double.valueOf(page_facture.getChoix_prix_conso().getText());
                        facture_a_creer = new Facture(this.page_facture.getChoix_num_facture().getText(),
                                this.page_facture.getChoix_type().getSelectedItem().toString(),
                                date,
                                prix_facture);
                        bail_DAO.updateIndexeEau(page_facture.getId_bail(), nouveau_index_eau);
                    } else {
                        validite_facture_eau=false;
                        facture_a_creer=null;
                    }
                } else {

                    try {
                        // Vérification si le montant est un float
                        Float.parseFloat(page_facture.getChoix_montant().getText());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Le montant de la facture doit être un nombre.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (Double.valueOf(this.page_facture.getChoix_montant().getText())<0.0){
                        JOptionPane.showMessageDialog(null, "Le montant de la facture doit être positive.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    facture_a_creer = new Facture(this.page_facture.getChoix_num_facture().getText(),
                            this.page_facture.getChoix_type().getSelectedItem().toString(),
                            date,
                            Double.valueOf(this.page_facture.getChoix_montant().getText()));
                }
                try {
                    if(validite_facture_eau) {
                        int id_charge = new ChargeDAO().getId(this.page_facture.getChoix_type().getSelectedItem().toString(), this.page_facture.getId_bail());
                        facture_DAO.create(facture_a_creer, id_charge);
                    } else {
                        JOptionPane.showMessageDialog(null, "L'index d'eau doit être supérieur au précédent.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (DAOException ex) {
                    throw new RuntimeException(ex);
                }
                refreshPage(e);
            };
    }

    private void refreshPage(ActionEvent e) {
        JFrame ancienne_fenetre = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
        ancienne_fenetre.dispose();
        int x=ancienne_fenetre.getX();
        int y=ancienne_fenetre.getY();
        PageFacture nouvelle_page = new PageFacture(page_facture.getId_bail(),x,y);
        nouvelle_page.getFrame().setVisible(true);
    }

    public DocumentListener getTextFieldDocumentListener() {
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkFields();
            }

            @Override
            public void removeUpdate(DocumentEvent e) { checkFields(); }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkFields();
            }
        };
    }

    public ActionListener eauSelected() {
        return e -> {
            String choix_type_facture = (String) page_facture.getChoix_type().getSelectedItem();

            boolean isEau = (choix_type_facture == "Eau");

            this.page_facture.getLabelMontant().setVisible(!isEau);
            this.page_facture.getChoix_montant().setVisible(!isEau);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(0, 0, 5, 0);

            if(isEau){
                gbc.gridx = 1;
                gbc.gridy = 2;
                this.page_facture.getContenu()
                        .add(this.page_facture.getLabel_index(), gbc);
            } else{
                this.page_facture.getContenu()
                        .remove(this.page_facture.getLabel_index());
            }
            if(isEau){
                gbc.gridx = 2;
                gbc.gridy = 2;
                this.page_facture.getContenu()
                        .add(this.page_facture.getChoix_index(), gbc);
            } else{
                this.page_facture.getContenu()
                        .remove(this.page_facture.getChoix_index());
            }

            if(isEau){
                gbc.gridx = 1;
                gbc.gridy = 3;
                this.page_facture.getContenu()
                        .add(this.page_facture.getLabel_prix_conso(), gbc);
            } else{
                this.page_facture.getContenu()
                        .remove(this.page_facture.getLabel_prix_conso());
            }
            if(isEau){
                gbc.gridx = 2;
                gbc.gridy = 3;
                this.page_facture.getContenu()
                        .add(this.page_facture.getChoix_prix_conso(), gbc);
            } else{
                this.page_facture.getContenu()
                        .remove(this.page_facture.getChoix_prix_conso());
            }

            if(isEau){
                this.page_facture.getContenu()
                        .remove(this.page_facture.getDate());
                gbc.gridx = 1;
                gbc.gridy = 4;
                this.page_facture.getContenu()
                        .add(this.page_facture.getDate(), gbc);
            } else{
                this.page_facture.getContenu()
                        .remove(this.page_facture.getDate());
                gbc.gridx = 1;
                gbc.gridy = 3;
                this.page_facture.getContenu()
                        .add(this.page_facture.getDate(), gbc);
            }

            if(isEau){
                this.page_facture.getContenu()
                        .remove(this.page_facture.getDateChooser());
                gbc.gridx = 2;
                gbc.gridy = 4;
                this.page_facture.getContenu()
                        .add(this.page_facture.getDateChooser(), gbc);
            } else{
                this.page_facture.getContenu()
                        .remove(this.page_facture.getDateChooser());
                gbc.gridx = 2;
                gbc.gridy = 3;
                this.page_facture.getContenu()
                        .add(this.page_facture.getDateChooser(), gbc);
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
        page_facture.getFrame().dispose();
    }

    public ActionListener getCheckFieldsActionListener() {
        return e -> checkFields();
    }

    public void checkFields() {
        // Vérification si tous les champs sont remplis
        boolean isFilled = false;
        if(page_facture.getChoix_type().getSelectedItem() == "Eau") {
            isFilled = (!page_facture.getChoix_num_facture().getText().trim().isEmpty() && page_facture.getDateChooser().getDate() != null
                    && !page_facture.getChoix_index().getText().trim().isEmpty() && !page_facture.getChoix_prix_conso().getText().trim().isEmpty());
        } else {
            isFilled =(!page_facture.getChoix_num_facture().getText().trim().isEmpty() && !page_facture.getChoix_montant().getText().trim().isEmpty()
                    && page_facture.getDateChooser().getDate() != null);
        }

        // Active ou désactive le bouton "Valider"
        page_facture.getValider().setEnabled(isFilled);
    }
}
