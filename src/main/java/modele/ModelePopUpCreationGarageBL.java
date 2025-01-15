package modele;

import DAO.DAOException;
import DAO.jdbc.GarageDAO;
import classes.Garage;
import ihm.PopUpCreationGarageLieBL;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ModelePopUpCreationGarageBL {
    PopUpCreationGarageLieBL popUpCreationGarageLieBL;
    public ModelePopUpCreationGarageBL (PopUpCreationGarageLieBL popUpCreationGarageLieBL) {
        this.popUpCreationGarageLieBL = popUpCreationGarageLieBL;
    }
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
                    garage.getNumeroFiscal(),
                    garage.getAdresse(),
                    garage.getComplementAdresse(),
                    garage.getVille()
            };
            model.addRow(rowData);
        }
        return model; // Retourne le modèle rempli
    }
    public ActionListener quitterPage() {
        return e -> {
            popUpCreationGarageLieBL.getFrame().dispose();
        };
    }
}
