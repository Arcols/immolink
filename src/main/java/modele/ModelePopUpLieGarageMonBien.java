package modele;

import DAO.DAOException;
import DAO.jdbc.BienLouableDAO;
import DAO.jdbc.GarageDAO;
import classes.Garage;
import enumeration.TypeLogement;
import ihm.PageMonBien;
import ihm.PopUpLieGarageMonBien;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.sql.SQLException;

public class ModelePopUpLieGarageMonBien {
    private final PopUpLieGarageMonBien pop_up_lie_garage_mon_bien;

    public ModelePopUpLieGarageMonBien(PopUpLieGarageMonBien pop_up_lie_garage_mon_bien) {
        this.pop_up_lie_garage_mon_bien = pop_up_lie_garage_mon_bien;
    }

    public ActionListener quitterPage() {
        return e -> pop_up_lie_garage_mon_bien.getFrame().dispose();
    }

    public MouseAdapter doubleClick(){
        return new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    handleTableDoubleClick(evt);
                } catch (DAOException | SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    private void handleTableDoubleClick(java.awt.event.MouseEvent evt) throws DAOException, SQLException {
        // Vérifier s'il s'agit d'un double-clic
        if (evt.getClickCount() == 2) {
            // Obtenir l'index de la ligne cliquée
            int row = pop_up_lie_garage_mon_bien.getTable().getSelectedRow();

            // Récupérer les données de la ligne sélectionnée
            if (row != -1) {
                String num_fisc = (String) pop_up_lie_garage_mon_bien.getTableModel().getValueAt(row, 0);
                Integer Id_garage = -1;
                try {
                    Id_garage = new GarageDAO().getIdGarage(num_fisc, TypeLogement.GARAGE_PAS_ASSOCIE);
                } catch (DAOException e) {
                    throw new RuntimeException(e);
                }
                Garage garage = null;
                try {
                    garage = new GarageDAO().read(Id_garage);
                } catch (DAOException e) {
                    throw new RuntimeException(e);
                }
                new BienLouableDAO().lierUnGarageAuBienLouable(new BienLouableDAO().readId(pop_up_lie_garage_mon_bien.getIdBien()),garage);
                pop_up_lie_garage_mon_bien.getMainPage().getFrame().dispose();
                pop_up_lie_garage_mon_bien.getFrame().dispose();
                int x=pop_up_lie_garage_mon_bien.getFrame().getX();
                int y=pop_up_lie_garage_mon_bien.getFrame().getY();

                PageMonBien page = new PageMonBien(pop_up_lie_garage_mon_bien.getIdBien(),new BienLouableDAO().getTypeFromId(pop_up_lie_garage_mon_bien.getIdBien()),x,y);
            }
        }
    }
}
