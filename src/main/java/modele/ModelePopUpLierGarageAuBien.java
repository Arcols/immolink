package modele;

import DAO.DAOException;
import DAO.jdbc.BienLouableDAO;
import classes.BienLouable;
import ihm.PopUpLierGarageAuBien;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ModelePopUpLierGarageAuBien {
    PopUpLierGarageAuBien popUpLierGarageAuBien;
    public ModelePopUpLierGarageAuBien (PopUpLierGarageAuBien popUpLierGarageAuBien) {
        this.popUpLierGarageAuBien = popUpLierGarageAuBien;
    }
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
                    bienLouable.getNumeroFiscal(),
                    bienLouable.getAdresse(),
                    bienLouable.getComplementAdresse(),
                    bienLouable.getVille()
            };
            model.addRow(rowData);
        }
        return model; // Retourne le modèle rempli
    }

    public ActionListener quitterPage() {
        return e -> {
            popUpLierGarageAuBien.getFrame().dispose();
        };
    }
}
