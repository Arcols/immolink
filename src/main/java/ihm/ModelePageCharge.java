package ihm;

import DAO.jdbc.BailDAO;
import classes.Bail;
import modele.*;

import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ModelePageCharge {

    private PageCharge pageCharge;

    public ModelePageCharge(PageCharge pageCharge) {
        this.pageCharge = pageCharge;
    }

    public ActionListener ouvrirPageFacture() {
        return e -> {
            pageCharge.getFrame().dispose();
            new PageFacture(pageCharge.getId_bail());
        };
    }
    public ActionListener quitterPage() {
        return e -> {
            pageCharge.getFrame().dispose();
            Bail bail = new BailDAO().getBailFromId(pageCharge.getId_bail());
            new PageUnBail(bail);
        };
    }
}
