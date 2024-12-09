package ihm;

import DAO.DAOException;
import DAO.jdbc.DiagnosticDAO;
import DAO.jdbc.LocataireDAO;
import classes.Diagnostic;
import classes.Locataire;

import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

public class ModelePageMonBien {

    public static DefaultTableModel loadDataDiagnosticsToTable(int idBien) throws SQLException, DAOException {
        // Liste des colonnes
        String[] columnNames = {"Libellé", "Fichier PDF","Date expiration"};

        // Création du modèle de table
        DefaultTableModel model = new DefaultTableModel(columnNames, 0); // `0` pour aucune ligne au départ

        // Récupération des locataires
        DiagnosticDAO diagnosticDAO = new DiagnosticDAO();
        List<Diagnostic> diagnostics = diagnosticDAO.readAllDiag(idBien);

        // Remplissage du modèle avec les données des locataires
        for (Diagnostic diagnostic : diagnostics) {
            Object[] rowData = {
                    diagnostic.getReference(),
                    diagnostic.getPdfPath(),
                    diagnostic.getDateInvalidite()
            };
            model.addRow(rowData); // Ajout de la ligne dans le modèle
        }

        return model; // Retourne le modèle rempli
    }

    public static DefaultTableModel loadDataTravauxToTable() throws SQLException {
        // Liste des colonnes
        String[] columnNames = {"Montant", "Nature","Montant non deductible"};

        // Création du modèle de table
        DefaultTableModel model = new DefaultTableModel(columnNames, 0); // `0` pour aucune ligne au départ

        // Récupération des locataires
        LocataireDAO locataireDAO = new LocataireDAO();
        List<Locataire> locataires = locataireDAO.getAllLocataire();

        // Remplissage du modèle avec les données des locataires
        for (Locataire locataire : locataires) {
            Object[] rowData = {

            };
            model.addRow(rowData); // Ajout de la ligne dans le modèle
        }

        return model; // Retourne le modèle rempli
    }
}
