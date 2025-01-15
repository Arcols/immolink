package modele;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import DAO.DAOException;
import DAO.jdbc.BailDAO;
import DAO.jdbc.DiagnosticDAO;
import ihm.PageNotifications;

public class ModelePageNotifications {

    private PageNotifications pageNotifications;

    public ModelePageNotifications(PageNotifications pageNotifications) {
        this.pageNotifications = pageNotifications;
    }
    /**
     * Charge les notifications dans un DefaultTableModel.
     *
     * @return DefaultTableModel rempli avec les notifications.
     * @throws DAOException si une erreur survient lors de la récupération des données.
     */
    public static DefaultTableModel getNotifications() throws DAOException {
        String[] columnNames = {"Type", "Intitulé"};

        // Création du modèle de table
        DefaultTableModel model = new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Toutes les cellules sont non éditables
            }
        };

        DiagnosticDAO diagnosticDAO = new DiagnosticDAO();
        List<String> notifsDiag = diagnosticDAO.readDiagPerimes();
        for (String diag : notifsDiag) {
            Object[] rowData = {
                    "Diagnostique périmé",
                    diag
            };
            model.addRow(rowData); // Ajout de la ligne dans le modèle
        }

        BailDAO bailDAO = new BailDAO();
        List<String> notifsICC = bailDAO.getBauxNouvelICC();
        for (String bail : notifsICC) {
            Object[] rowData = {
                    "Anniversaire bail",
                    bail
            };
            model.addRow(rowData); // Ajout de la ligne dans le modèle
        }

        return model;
    }


}
