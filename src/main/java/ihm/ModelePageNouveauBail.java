package ihm;

import DAO.DAOException;
import DAO.jdbc.BailDAO;
import classes.Bail;
import classes.BienLouable;
import modele.PageNouveauBail;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class ModelePageNouveauBail {

    private PageNouveauBail pageNouveauBail;

    public ModelePageNouveauBail(PageNouveauBail pageNouveauBail) {
        this.pageNouveauBail = pageNouveauBail;
    }

    public ActionListener getAjouterLocataire() {
        return e -> {
            // Données fictives pour les locataires

            String[][] locataires = {
                    {"Alice", "Dupont", "0123456789"},
                    {"Bob", "Martin", "0987654321"},
                    {"Charlie", "Durand", "0543210987"}
            };//IL FAUT FAIRE LA REQUETE SQL
            String[] columns = {"Prénom", "Nom", "Téléphone"};
            // Créer une table avec les données des locataires
            DefaultTableModel model = new DefaultTableModel(locataires, columns);
            JTable selectionTable = new JTable(model);
            selectionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            // ScrollPane pour la table
            JScrollPane scrollPanePopUp = new JScrollPane(selectionTable);

            // Afficher la table dans un JOptionPane
            int result = JOptionPane.showConfirmDialog(this.pageNouveauBail.getFrame(), scrollPanePopUp, "Sélectionner un locataire",
                    JOptionPane.OK_CANCEL_OPTION);

            // Si l'utilisateur clique sur OK
            if (result == JOptionPane.OK_OPTION) {
                int selectedRow = selectionTable.getSelectedRow();
                if (selectedRow >= 0) {
                    // Récupérer les données du locataire sélectionné
                    String prenom = model.getValueAt(selectedRow, 0).toString();
                    String nom = model.getValueAt(selectedRow, 1).toString();
                    String telephone = model.getValueAt(selectedRow, 2).toString();

                    // Ajouter ces données dans la table principale
                    this.pageNouveauBail.getTableModel().addRow(new String[]{prenom, nom, telephone});
                } else {
                    // Aucune ligne sélectionnée
                    JOptionPane.showMessageDialog(this.pageNouveauBail.getFrame(), "Veuillez sélectionner un locataire.");
                }
            }
        };
    }

    public ActionListener getSurfaceEtPiece() {
        return e -> {
            String ville=(String) this.pageNouveauBail.getChoix_ville().getSelectedItem();
            String adresse=(String) this.pageNouveauBail.getChoix_adresse().getSelectedItem();
            String compl=(String) this.pageNouveauBail.getChoix_complement().getSelectedItem();
            Double surface=new DAO.jdbc.BienLouableDAO().getSurfaceFromCompl(ville,adresse,compl);
            this.pageNouveauBail.getChoix_surface().setText(surface.toString()+" m²");
            Integer nbpiece=new DAO.jdbc.BienLouableDAO().getNbPieceFromCompl(ville,adresse,compl);
            this.pageNouveauBail.getChoix_nb_piece().setText(nbpiece.toString());
        };
    }
    public DocumentListener getTextFieldDocumentListener() {
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                pageNouveauBail.checkFields();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                pageNouveauBail.checkFields();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                pageNouveauBail.checkFields();
            }
        };
    }

    public ActionListener CreationBail(){
        return e -> {
            String ville=(String) this.pageNouveauBail.getChoix_ville().getSelectedItem();
            String adresse=(String) this.pageNouveauBail.getChoix_adresse().getSelectedItem();
            String compl=(String) this.pageNouveauBail.getChoix_complement().getSelectedItem();
            String numfisc=new DAO.jdbc.BienLouableDAO().getFiscFromCompl(ville,adresse,compl);
            java.sql.Date sqlDateDebut = java.sql.Date.valueOf(pageNouveauBail.getChoix_date_debut().getText());
            java.sql.Date sqlDateFin = java.sql.Date.valueOf(pageNouveauBail.getChoix_date_fin().getText());
            Bail bail=new Bail(this.pageNouveauBail.getSolde_tout_compte().isSelected(),
                    numfisc,
                    Double.parseDouble(this.pageNouveauBail.getChoix_loyer().getText()),
                    Double.parseDouble(this.pageNouveauBail.getChoix_prevision().getText()),
                    Double.parseDouble(this.pageNouveauBail.getChoix_depot_garantie().getText()),
                    sqlDateDebut,
                    sqlDateFin);
            try {
                new BailDAO().create(bail);
            } catch (DAOException ex) {
                JOptionPane.showMessageDialog(null, "Une erreur est survenue lors de la création du logement.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        };
    }

}

