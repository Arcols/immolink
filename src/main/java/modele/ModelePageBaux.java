package modele;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import DAO.DAOException;
import DAO.jdbc.BailDAO;
import DAO.jdbc.BienLouableDAO;
import DAO.jdbc.LouerDAO;
import classes.Bail;
import ihm.PageBaux;
import ihm.PageNouveauBail;

public class ModelePageBaux {

    private PageBaux pageBaux;

    public ModelePageBaux(PageBaux pageBaux) {
        this.pageBaux = pageBaux;
    }

    public ActionListener ouvrirPageNouveauBail() {
        return e -> {
            pageBaux.getFrame().dispose();
            PageNouveauBail PageNouveauBail = null;
            try {
                PageNouveauBail = new PageNouveauBail();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            PageNouveauBail.main(null);
        };
    }
    public static String statut(Bail bail){
        LouerDAO louerDAO = new LouerDAO();
        BailDAO bailDAO = new BailDAO();
        if (louerDAO.getStatutBail(bailDAO.getId(bail))){
            return "Payé";
        }
        return "Retard";
    }

    public static class CustomRowRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            int villeColumnIndex = 0;
            int adresseColumnIndex = 1;
            int complementColumnIndex = 2;
            int dateColumnIndex = 5;
            Object villeValue = table.getValueAt(row, villeColumnIndex);
            Object adresseValue = table.getValueAt(row, adresseColumnIndex);
            Object complementValue = table.getValueAt(row, complementColumnIndex);
            Object dateValue = table.getValueAt(row, dateColumnIndex);
            Bail bail = null;
            Date sqlDate = Date.valueOf((String) dateValue);
            try {
                bail = new BailDAO().getBailFromBienEtDate(new BienLouableDAO().readFisc(new BienLouableDAO().getFiscFromCompl((String) villeValue,(String) adresseValue,(String) complementValue)),sqlDate);
            } catch (DAOException e) {
                throw new RuntimeException(e);
            }
            int lastColumnIndex = table.getColumnCount() - 1;
            String status = table.getValueAt(row, lastColumnIndex).toString();

            if (bail != null && bail.getDateFin().before(new Date(System.currentTimeMillis()))) {
                Color rouge =Color.decode("#ff5454");
                c.setBackground(rouge);
            }else if ("Retard".equals(status)) {
                c.setBackground(Color.decode("#f5b942"));
            } else {
                c.setBackground(Color.decode("#7fe075"));
            }

            if (isSelected) {
                Color bleu= new Color(38, 117, 191);
                c.setBackground(bleu);
                c.setForeground(Color.WHITE);
            } else {
                c.setForeground(Color.BLACK);
            }


            return c;
        }
    }
}
