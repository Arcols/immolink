package ihm;

import modele.PageNouveauBail;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;

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
}

