package ihm;

import DAO.DAOException;
import DAO.LogementDAO;
import DAO.jdbc.BienLouableDAO;
import DAO.jdbc.DiagnosticDAO;
import DAO.jdbc.LocataireDAO;
import classes.BienLouable;
import classes.Diagnostic;
import classes.Locataire;
import classes.Logement;
import modele.PageMonBien;

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

    public void chargerDonneesBien(int idBien, PageMonBien page) throws DAOException {
        try {
            // Récupération des informations du bien via le DAO
            BienLouableDAO bienLouableDAO = new DAO.jdbc.BienLouableDAO();
            BienLouable bienLouable = bienLouableDAO.readId(idBien);

            if (bienLouable != null) {
                // Mise à jour des labels avec les informations du bien
                page.getAffichageNumeroFiscal().setText(bienLouable.getNumero_fiscal());
                page.getAffichageVille().setText(bienLouable.getVille());
                page.getAffichageAdresse().setText(bienLouable.getAdresse());
                page.getAffichageComplement().setText(bienLouable.getComplement_adresse());
            }
        } catch (DAOException e) {
            throw new DAOException("Erreur lors du chargement des informations du bien : " + e.getMessage(), e);
        }
    }

}
