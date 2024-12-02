package ihm;

import DAO.jdbc.LocataireDAO;
import classes.Locataire;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

public class ModelePageAccueil {

    /**
     * Charge les données des locataires dans un DefaultTableModel.
     *
     * @return DefaultTableModel rempli avec les données des locataires.
     * @throws SQLException si une erreur survient lors de la récupération des données.
     */
    public static DefaultTableModel loadDataLocataireToTable() throws SQLException {
        // Liste des colonnes
        String[] columnNames = {"Nom", "Prénom", "Téléphone", "Mail", "Genre", "Date d'arrivée"};

        // Création du modèle de table
        DefaultTableModel model = new DefaultTableModel(columnNames, 0); // `0` pour aucune ligne au départ

        // Récupération des locataires
        LocataireDAO locataireDAO = new LocataireDAO();
        List<Locataire> locataires = locataireDAO.getAllLocataire();

        // Remplissage du modèle avec les données des locataires
        for (Locataire locataire : locataires) {
            Object[] rowData = {
                    locataire.getNom(),
                    locataire.getPrénom(),
                    locataire.getTéléphone(),
                    locataire.getMail(),
                    locataire.getGenre(),
                    locataire.getDateArrive()
            };
            model.addRow(rowData); // Ajout de la ligne dans le modèle
        }

        return model; // Retourne le modèle rempli
    }
}
