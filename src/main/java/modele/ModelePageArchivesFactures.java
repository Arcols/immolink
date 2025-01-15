package modele;

import DAO.DAOException;
import DAO.jdbc.ChargeDAO;
import DAO.jdbc.FactureDAO;
import classes.Facture;
import ihm.PageArchivesFactures;
import ihm.PageCharge;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ModelePageArchivesFactures {
    private PageArchivesFactures pageArchivesFactures;
    public ModelePageArchivesFactures(PageArchivesFactures pageArchivesFactures){
        this.pageArchivesFactures = pageArchivesFactures;
    }

    public DefaultTableModel loadDataBienImmoToTable() throws SQLException, DAOException {
        String[] columns = {"Numéro facture","libellé","date","montant"};

        DefaultTableModel model = new DefaultTableModel(columns, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Toutes les cellules sont non éditables
            }
        };

        ChargeDAO chargeDAO = new ChargeDAO();
        int idchargeEau = chargeDAO.getId("Eau", pageArchivesFactures.getId_bail());
        int idchargeElectricite = chargeDAO.getId("Electricité", pageArchivesFactures.getId_bail());
        int idchargeOrdures = chargeDAO.getId("Ordures", pageArchivesFactures.getId_bail());
        int idchargeEntretien = chargeDAO.getId("Entretien", pageArchivesFactures.getId_bail());

        FactureDAO factureDAO = new FactureDAO();
        List<Facture> facturesEau = factureDAO.getAll(idchargeEau);
        List<Facture> facturesElecticite = factureDAO.getAll(idchargeElectricite);
        List<Facture> facturesOrdures = factureDAO.getAll(idchargeOrdures);
        List<Facture> facturesEntretien = factureDAO.getAll(idchargeEntretien);

        // Remplissage du modèle avec les données des biens louables
        for (Facture factureEau : facturesEau) {
            Object[] rowData= {
                    factureEau.getNumero(),
                    factureEau.getType(),
                    factureEau.getDate(),
                    factureEau.getMontant()
            };
            model.addRow(rowData);
        }
        for (Facture factureElectricite : facturesElecticite) {
            Object[] rowData= {
                    factureElectricite.getNumero(),
                    factureElectricite.getType(),
                    factureElectricite.getDate(),
                    factureElectricite.getMontant()
            };
            model.addRow(rowData);
        }

        for (Facture factureOrdures : facturesOrdures) {
            Object[] rowData= {
                    factureOrdures.getNumero(),
                    factureOrdures.getType(),
                    factureOrdures.getDate(),
                    factureOrdures.getMontant()
            };
            model.addRow(rowData);
        }
        for (Facture factureEntretien : facturesEntretien) {
            Object[] rowData= {
                    factureEntretien.getNumero(),
                    factureEntretien.getType(),
                    factureEntretien.getDate(),
                    factureEntretien.getMontant()
            };
            model.addRow(rowData);
        }

        return model; // Retourne le modèle rempli
    }

    public ActionListener quitterPage(int id_bail){
        return e -> {
        pageArchivesFactures.getFrame().dispose();
        new PageCharge(id_bail);
    };}

}
