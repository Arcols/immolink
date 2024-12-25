package ihm;

import DAO.DAOException;
import DAO.jdbc.GarageDAO;
import classes.Garage;

import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

public class ModelePopUpLieGarageMonBien {
    public static DefaultTableModel loadDataGaragesPasAssosToTable() throws SQLException, DAOException {
        String[] columns = {"Numéro Fiscal","Adresse","Complement","Ville"};

        DefaultTableModel model = new DefaultTableModel(columns, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Toutes les cellules sont non éditables
            }
        };

        // Récupération des locataires
        GarageDAO garageDAO = new GarageDAO();
        List<Garage> garages = garageDAO.findAllGaragePasAssoc();

        // Remplissage du modèle avec les données des locataires
        for (Garage garage : garages) {
            Object[] rowData= {
                    garage.getNumero_fiscal(),
                    garage.getAdresse(),
                    garage.getComplement_adresse(),
                    garage.getVille()
            };
            model.addRow(rowData);
        }
        return model; // Retourne le modèle rempli
    }

}
