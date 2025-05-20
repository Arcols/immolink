package modele;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import DAO.DAOException;
import DAO.jdbc.BienLouableDAO;
import DAO.jdbc.GarageDAO;
import classes.BienLouable;
import ihm.PageMesBiens;
import ihm.PopUpLierGarageAuBien;

public class ModelePopUpLierGarageAuBien {
    PopUpLierGarageAuBien pop_up_lier_garage_au_bien;
    public ModelePopUpLierGarageAuBien (PopUpLierGarageAuBien pop_up_lier_garage_au_bien) {
        this.pop_up_lier_garage_au_bien = pop_up_lier_garage_au_bien;
    }
    public static DefaultTableModel loadDataBienLouablePasAssosToTable() throws SQLException, DAOException {
        String[] colonnes = {"Numéro Fiscal","Adresse","Complement","Ville"};

        DefaultTableModel model = new DefaultTableModel(colonnes, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Toutes les cellules sont non éditables
            }
        };

        // Récupération des locataires
        BienLouableDAO bien_louable_DAO = new BienLouableDAO();
        List<BienLouable> biens_louables =  bien_louable_DAO.getAllBienLouableNoGarageLink();

        // Remplissage du modèle avec les données des locataires
        for (BienLouable bienLouable : biens_louables) {
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

    public MouseAdapter mouseAdapter(JTable table){
        return new MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                super.mouseClicked(evt);
                TableModel tableModel = table.getModel();
                try {
                    if (evt.getClickCount() == 2) {
                        // Obtenir l'index de la ligne cliquée
                        int row = table.getSelectedRow();
                        // Récupérer les données de la ligne sélectionnée
                        if (row != -1) {
                            String num_fisc = (String) tableModel.getValueAt(row, 0);
                            Integer idBien = -1;
                            try {
                                pop_up_lier_garage_au_bien.setBien( new BienLouableDAO().readFisc(num_fisc));
                            } catch (DAOException e) {
                                throw new RuntimeException(e);
                            }

                            new BienLouableDAO().lierUnGarageAuBienLouable(pop_up_lier_garage_au_bien.getBien(),new GarageDAO().read(pop_up_lier_garage_au_bien.getIdGarage()));
                            pop_up_lier_garage_au_bien.getMainPage().getFrame().dispose();
                            pop_up_lier_garage_au_bien.getFrame().dispose();
                            int x=pop_up_lier_garage_au_bien.getFrame().getX();
                            int y=pop_up_lier_garage_au_bien.getFrame().getY();

                            new PageMesBiens(x,y);
                        }
                    }
                } catch (DAOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }



    public ActionListener quitterPage() {
        return e -> pop_up_lier_garage_au_bien.getFrame().dispose();
    }

    public BienLouable getBien() throws DAOException {

        return new BienLouableDAO().readId(this.pop_up_lier_garage_au_bien.getMainPage().getId_bien());
    }
}
