package ihm;

import DAO.DAOException;
import DAO.jdbc.ChargeDAO;
import DAO.jdbc.FactureDAO;
import classes.Facture;
import modele.PageBaux;
import modele.PageCharge;
import modele.PageFacture;

import java.awt.event.ActionListener;

public class ModelePageFacture {
    private PageFacture pageFacture;

    public ModelePageFacture(PageFacture pageFacture) {
        this.pageFacture= pageFacture;
    }


    public ActionListener quitterPage() {
        return e -> {
            pageFacture.getFrame().dispose();
            new PageCharge(pageFacture.getId_bail());
        };
    }

    public ActionListener ajouterFacture(){
        return e ->
            {

                FactureDAO daofacture = new FactureDAO();
                java.sql.Date date = new java.sql.Date(this.pageFacture.getDateChooser().getDate().getTime());
                Facture factureACreer = new Facture(this.pageFacture.getChoix_num_facture().getText(),
                        this.pageFacture.getChoix_type().getSelectedItem().toString(),
                        date,
                        Double.valueOf(this.pageFacture.getChoix_montant().getText()));
                try {
                    int id_charge = new ChargeDAO().getId(this.pageFacture.getChoix_type().getSelectedItem().toString(),this.pageFacture.getId_bail());
                    daofacture.create(factureACreer,id_charge);
                } catch (DAOException ex) {
                    throw new RuntimeException(ex);
                }
            };
    }
}
