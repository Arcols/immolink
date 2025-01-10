package ihm;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import DAO.DAOException;
import DAO.jdbc.DiagnosticDAO;
import DAO.jdbc.LocataireDAO;
import DAO.jdbc.RegimeDAO;
import classes.Locataire;
import modele.PageAccueil;
import modele.PageNotifications;
import modele.PageNouveauLocataire;

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

        return model;
    }


}
