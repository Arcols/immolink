package modele;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import DAO.jdbc.ChargeDAO;
import DAO.jdbc.FactureDAO;
import classes.Facture;
import ihm.PageArchivesFactures;
import ihm.PageCharge;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.util.List;

public class ModelePageArchivesFactures {
    private final PageArchivesFactures page_archive_facture;
    public ModelePageArchivesFactures(PageArchivesFactures page_archive_facture){
        this.page_archive_facture = page_archive_facture;
    }

    public DefaultTableModel loadDataBienImmoToTable() throws SQLException, DAOException {
        String[] columns = {"Numéro facture","libellé","date","montant"};

        DefaultTableModel model = new DefaultTableModel(columns, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Toutes les cellules sont non éditables
            }
        };

        ChargeDAO charge_DAO = new ChargeDAO();
        int id_charge_eau = charge_DAO.getId("Eau", page_archive_facture.getId_bail());
        int id_charge_electricite = charge_DAO.getId("Electricité", page_archive_facture.getId_bail());
        int id_charge_ordure = charge_DAO.getId("Ordures", page_archive_facture.getId_bail());
        int id_charge_entretien = charge_DAO.getId("Entretien", page_archive_facture.getId_bail());

        FactureDAO factureDAO = new FactureDAO();
        List<Facture> factures_eau = factureDAO.getAll(id_charge_eau);
        List<Facture> factures_electricite = factureDAO.getAll(id_charge_electricite);
        List<Facture> factures_ordure = factureDAO.getAll(id_charge_ordure);
        List<Facture> factures_entretien = factureDAO.getAll(id_charge_entretien);

        // Remplissage du modèle avec les données des biens louables
        for (Facture facture_eau : factures_eau) {
            Object[] rowData= {
                    facture_eau.getNumero(),
                    facture_eau.getType(),
                    facture_eau.getDate(),
                    facture_eau.getMontant()
            };
            model.addRow(rowData);
        }
        for (Facture facture_electricite : factures_electricite) {
            Object[] rowData= {
                    facture_electricite.getNumero(),
                    facture_electricite.getType(),
                    facture_electricite.getDate(),
                    facture_electricite.getMontant()
            };
            model.addRow(rowData);
        }

        for (Facture facture_ordure : factures_ordure) {
            Object[] rowData= {
                    facture_ordure.getNumero(),
                    facture_ordure.getType(),
                    facture_ordure.getDate(),
                    facture_ordure.getMontant()
            };
            model.addRow(rowData);
        }
        for (Facture facture_entretien : factures_entretien) {
            Object[] rowData= {
                    facture_entretien.getNumero(),
                    facture_entretien.getType(),
                    facture_entretien.getDate(),
                    facture_entretien.getMontant()
            };
            model.addRow(rowData);
        }

        return model; // Retourne le modèle rempli
    }

    public ActionListener quitterPage(){
        return e -> {
        page_archive_facture.getFrame().dispose();
        int x=page_archive_facture.getFrame().getX();
        int y=page_archive_facture.getFrame().getY();
        new PageCharge(page_archive_facture.getId_bail(),x,y);
    };}

    public WindowListener fermerFenetre(){
        return new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Action to perform on application close
                performCloseAction();
            }
        };
    }
    private void performCloseAction() {
        ConnectionDB.destroy(); // fermeture de la connection
        page_archive_facture.getFrame().dispose();
    }
}
