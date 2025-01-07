package ihm;

import DAO.DAOException;
import DAO.jdbc.GarageDAO;
import classes.Garage;
import modele.PageMonBien;
import modele.PopUpLieGarageMonBien;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ModelePopUpLieGarageMonBien {
    private PopUpLieGarageMonBien popUpLieGarageMonBien;

    public ModelePopUpLieGarageMonBien(PopUpLieGarageMonBien popUpLieGarageMonBien) {
        this.popUpLieGarageMonBien = popUpLieGarageMonBien;
    }

    public ActionListener quitterPage(Integer idBien) {
        return e -> {
            popUpLieGarageMonBien.getFrame().dispose();
        };
    }
}
