package modele;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import DAO.DAOException;
import DAO.jdbc.BailDAO;
import DAO.jdbc.DiagnosticDAO;

public class ModelePageNotifications {
    
    /**
     * Charge les notifications dans un DefaultTableModel.
     *
     * @return DefaultTableModel rempli avec les notifications.
     * @throws DAOException si une erreur survient lors de la récupération des données.
     */
    public static DefaultTableModel getNotifications() throws DAOException {
        String[] nom_colonne = {"Type", "Intitulé"};

        // Création du modèle de table
        DefaultTableModel model = new DefaultTableModel(nom_colonne, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Toutes les cellules sont non éditables
            }
        };

        DiagnosticDAO diagnostic_DAO = new DiagnosticDAO();
        List<String> notifs_diag = diagnostic_DAO.readDiagPerimes();
        for (String diag : notifs_diag) {
            Object[] rowData = {
                    "Diagnostique périmé",
                    diag
            };
            model.addRow(rowData); // Ajout de la ligne dans le modèle
        }

        BailDAO bail_DAO = new BailDAO();
        List<String> notifs_ICC = bail_DAO.getBauxNouvelICC();
        for (String bail : notifs_ICC) {
            Object[] rowData = {
                    "Anniversaire bail",
                    bail
            };
            model.addRow(rowData); // Ajout de la ligne dans le modèle
        }
        return model;
    }
}
