package modele;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.*;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import DAO.jdbc.BailDAO;
import DAO.jdbc.BatimentDAO;
import DAO.jdbc.BienLouableDAO;
import DAO.jdbc.LouerDAO;
import classes.Bail;
import classes.BienLouable;
import classes.Locataire;
import ihm.PageBaux;
import ihm.PageNouveauBail;
import ihm.PageUnBail;

public class ModelePageBaux {

    private final PageBaux page_baux;

    public ModelePageBaux(PageBaux page_baux) {
        this.page_baux = page_baux;
    }

    public ActionListener ouvrirPageNouveauBail() {
        return e -> {
            page_baux.getFrame().dispose();
            int x=page_baux.getFrame().getX();
            int y=page_baux.getFrame().getY();

            try {
                new PageNouveauBail(x,y);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        };
    }
    public static String statut(Bail bail){
        LouerDAO louer_DAO = new LouerDAO();
        BailDAO bail_DAO = new BailDAO();
        if (louer_DAO.getStatutBail(bail_DAO.getId(bail))){
            return "Payé";
        }
        return "Retard";
    }

    public static class CustomRowRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            int colonne_ville_index = 0;
            int colonne_adresse_index = 1;
            int colonne_complement_index = 2;
            int colonne_date_index = 5;
            Object valeur_ville = table.getValueAt(row, colonne_ville_index);
            Object valeur_adresse = table.getValueAt(row, colonne_adresse_index);
            Object valeur_complement = table.getValueAt(row, colonne_complement_index);
            Object valeur_date = table.getValueAt(row, colonne_date_index);
            Bail bail;
            Date date_sql = Date.valueOf((String) valeur_date);
            try {
                bail = new BailDAO().getBailFromBienEtDate(new BienLouableDAO().readFisc(new BienLouableDAO().getFiscFromCompl((String) valeur_ville, (String) valeur_adresse, (String) valeur_complement)), date_sql);
            } catch (DAOException e) {
                throw new RuntimeException(e);
            }
            int dernier_index_colonne = table.getColumnCount() - 1;
            String status = table.getValueAt(row, dernier_index_colonne).toString();

            if (bail != null && bail.getDateFin().before(new Date(System.currentTimeMillis()))) {
                Color rouge = Color.decode("#ff5454");
                c.setBackground(rouge);
            } else if ("Retard".equals(status)) {
                c.setBackground(Color.decode("#f5b942"));
            } else {
                c.setBackground(Color.decode("#7fe075"));
            }

            if (isSelected) {
                Color bleu = new Color(38, 117, 191);
                c.setBackground(bleu);
                c.setForeground(Color.WHITE);
            } else {
                c.setForeground(Color.BLACK);
            }

            return c;
        }
    }

    public MouseAdapter mouseAdapter(JTable table) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                TableModel table_model = table.getModel();

                // Vérifier s'il s'agit d'un double-clic
                if (e.getClickCount() == 2) {
                    // Obtenir l'index de la ligne cliquée
                    int row = table.getSelectedRow();

                    // Récupérer les données de la ligne sélectionnée
                    if (row != -1) {
                        String ville = (String) table_model.getValueAt(row, 0);
                        String adresse = (String) table_model.getValueAt(row, 1);
                        String complement = (String) table_model.getValueAt(row, 2);
                        String date_str = (String) table_model.getValueAt(row, 5);
                        Date date = java.sql.Date.valueOf(date_str);

                        try {
                            BienLouable bien = new BienLouableDAO().readFisc(new BienLouableDAO().getFiscFromCompl(ville, adresse, complement));
                            Bail bail = new BailDAO().getBailFromBienEtDate(bien, date);
                            page_baux.getFrame().dispose();
                            int x=page_baux.getFrame().getX();
                            int y=page_baux.getFrame().getY();

                            new PageUnBail(bail,x,y);

                        } catch (DAOException exception) {
                            throw new RuntimeException(exception);
                        }
                    }
                }
            }
        };
    }

    public TableModel loadDataBauxToTable(){
        String[] columns = { "Ville", "Adresse", "Complément", "Locataire(s)", "Loyer","Date début", "Statut" };

        DefaultTableModel table_modele = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Toutes les cellules sont non éditables
            }
        };
        BailDAO bailDAO = new DAO.jdbc.BailDAO();
        List<Bail> list_bail =  bailDAO.getAllBaux();
        String[][] data = new String[list_bail.size()][];
        String[] ligne;
        int i = 0;
        for (Bail b : list_bail) {
            BienLouable logement = null;
            try {
                logement = new BienLouableDAO().readFisc(b.getFiscBien());
            } catch (DAOException e) {
                throw new RuntimeException(e);
            }
            List<Integer> id_locataires = new LouerDAO().getIdLoc(new DAO.jdbc.BailDAO().getId(b));
            String noms= "";
            for (int id : id_locataires) {
                Locataire loc = new DAO.jdbc.LocataireDAO().getLocFromId(id);
                noms+=loc.getNom()+" ";
            }

            ligne = new String[]{
                    logement.getVille(),
                    logement.getAdresse(),
                    logement.getComplementAdresse(),
                    noms,
                    String.valueOf(b.getLoyer()),
                    b.getDateDebut().toString(),
                    ModelePageBaux.statut(b)
            };
            data[i] = ligne;
            i++;
            table_modele.addRow(ligne);
        }

        return table_modele;
    }

    public double getRevenu(){
       return new BailDAO().getAllLoyer();
    }

    public Map<String, List<String>> getAdressesVilles() throws SQLException {
        return new BatimentDAO().searchAllBatimentsWithCompl();
    }

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
        page_baux.getFrame().dispose();
    }
}
