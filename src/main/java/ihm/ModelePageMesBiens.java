package ihm;

import DAO.DAOException;
import DAO.jdbc.BienLouableDAO;
import DAO.jdbc.LocataireDAO;
import classes.BienLouable;
import classes.Locataire;

import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

public class ModelePageMesBiens {
    /**
     * Charge les données des locataires dans un DefaultTableModel.
     *
     * @return DefaultTableModel rempli avec les données des locataires.
     * @throws SQLException si une erreur survient lors de la récupération des données.
     */
    public static DefaultTableModel loadDataBienImmoToTable() throws SQLException, DAOException {
        // Liste des colonnes
        String[] columnNames = {"Adresse","Complement","Ville","Type"};

        // Création du modèle de table
        DefaultTableModel model = new DefaultTableModel(columnNames, 0); // `0` pour aucune ligne au départ

        // Récupération des locataires
        BienLouableDAO bienLouableDAO = new BienLouableDAO();
        List<BienLouable> biensLouables = bienLouableDAO.findAll();

        // Remplissage du modèle avec les données des locataires
        for (BienLouable bien : biensLouables) {
            Object[] rowData = {
                    bien.getAdresse(),
                    bien.getComplement_adresse(),
                    bien.getVille()
            };
            model.addRow(rowData); // Ajout de la ligne dans le modèle
        }

        return model; // Retourne le modèle rempli
    }
}
