package ihm;

import DAO.DAOException;
import DAO.jdbc.BienLouableDAO;
import DAO.jdbc.GarageDAO;
import classes.BienLouable;
import classes.Garage;

import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

public class ModelePopUpLierGarageAuBien {
    public static DefaultTableModel loadDataBienLouablePasAssosToTable() throws SQLException, DAOException {
        String[] columns = {"Numéro Fiscal","Adresse","Complement","Ville"};

        DefaultTableModel model = new DefaultTableModel(columns, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Toutes les cellules sont non éditables
            }
        };

        // Récupération des locataires
        BienLouableDAO bienLouableDAO = new BienLouableDAO();
        List<BienLouable> bienLouables =  bienLouableDAO.getAllBienLouableNoGarageLink();

        // Remplissage du modèle avec les données des locataires
        for (BienLouable bienLouable : bienLouables) {
            Object[] rowData= {
                    bienLouable.getNumero_fiscal(),
                    bienLouable.getAdresse(),
                    bienLouable.getComplement_adresse(),
                    bienLouable.getVille()
            };
            model.addRow(rowData);
        }
        return model; // Retourne le modèle rempli
    }
}
