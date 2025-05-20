package modele;

import DAO.DAOException;
import DAO.jdbc.GarageDAO;
import classes.Garage;
import enumeration.TypeLogement;
import ihm.PopUpCreationGarageLieBL;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class ModelePopUpCreationGarageBL {
    PopUpCreationGarageLieBL pop_up_creation_garage_lie_BL;
    public ModelePopUpCreationGarageBL (PopUpCreationGarageLieBL pop_up_creation_garage_lie_BL) {
        this.pop_up_creation_garage_lie_BL = pop_up_creation_garage_lie_BL;
    }
    public static DefaultTableModel loadDataGaragesPasAssosToTable() throws SQLException, DAOException {
        String[] colonnes = {"Numéro Fiscal","Adresse","Complement","Ville"};

        DefaultTableModel model = new DefaultTableModel(colonnes, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Toutes les cellules sont non éditables
            }
        };

        // Récupération des locataires
        GarageDAO garage_DAO = new GarageDAO();
        List<Garage> garages = garage_DAO.findAllGaragePasAssoc();

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
        return e -> pop_up_creation_garage_lie_BL.getFrame().dispose();
    }


    public MouseAdapter doubleClick(){
        return new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
                // Vérifier s'il s'agit d'un double-clic
                if (e.getClickCount() == 2) {
                    // Obtenir l'index de la ligne cliquée
                    int row = pop_up_creation_garage_lie_BL.getTable().getSelectedRow();

                    // Récupérer les données de la ligne sélectionnée
                    if (row != -1) {
                        String num_fisc = (String) pop_up_creation_garage_lie_BL.getTable().getModel().getValueAt(row, 0);
                        Integer Id_garage = -1;
                        try {
                            Id_garage = new GarageDAO().getIdGarage(num_fisc, TypeLogement.GARAGE_PAS_ASSOCIE);
                        } catch (DAOException exept) {
                            throw new RuntimeException(exept);
                        }
                        Garage garage = null;
                        try {
                            garage = new GarageDAO().read(Id_garage);
                        } catch (DAOException exept) {
                            throw new RuntimeException(exept);
                        }
                        pop_up_creation_garage_lie_BL.getMainPage().addGarage(garage);
                        pop_up_creation_garage_lie_BL.getFrame().dispose();
                    }
                }
            }
        };
    }
}
