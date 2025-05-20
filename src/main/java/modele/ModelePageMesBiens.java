
package modele;

import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import DAO.jdbc.BatimentDAO;
import DAO.jdbc.BienLouableDAO;
import classes.Batiment;
import classes.BienLouable;
import enumeration.TypeLogement;
import ihm.PageMesBiens;
import ihm.PageMonBien;
import ihm.PageNouveauBienImmobilier;

public class ModelePageMesBiens {

    private final PageMesBiens page_mes_biens;

    public ModelePageMesBiens(PageMesBiens page_mes_biens) {
        this.page_mes_biens = page_mes_biens;
    }

    public ActionListener ouvrirNouveauBien(){
        return e->{
            page_mes_biens.getFrame().dispose();
            int x=page_mes_biens.getFrame().getX();
            int y=page_mes_biens.getFrame().getY();

            new PageNouveauBienImmobilier(x,y);
        };
    }

    /**
     * Charge les données des locataires dans un DefaultTableModel.
     *
     * @return DefaultTableModel rempli avec les données des locataires.
     * @throws SQLException si une erreur survient lors de la récupération des données.
     */
    public static DefaultTableModel loadDataBienImmoToTable() throws SQLException, DAOException {
        String[] colonnes = {"Type","Ville","Adresse","Complement"};

        DefaultTableModel model = new DefaultTableModel(colonnes, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Toutes les cellules sont non éditables
            }
        };

        BienLouableDAO bien_louable_DAO = new BienLouableDAO();
        List<BienLouable> biens_louables = bien_louable_DAO.findAll();
        Object[] rowData;
        // Remplissage du modèle avec les données des biens louables
        for (BienLouable bien : biens_louables) {
            rowData = new Object[]{
                    TypeLogement.getString(bien.getTypeLogement()),
                    bien.getVille(),
                    bien.getAdresse(),
                    bien.getComplementAdresse()
            };
            model.addRow(rowData);
        }

        BatimentDAO batiment_DAO = new BatimentDAO();
        List<Batiment> batiments = batiment_DAO.findAll();
        // Remplissage du modèle avec les données des bâtiments
        for (Batiment bat : batiments) {
            rowData = new Object[]{
                    TypeLogement.getString(TypeLogement.BATIMENT),
                    bat.getVille(),
                    bat.getAdresse(),
                    ""
            };
            model.addRow(rowData);
        }
        return model; // Retourne le modèle rempli
    }

    public MouseAdapter mouseAdapter(JTable table) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    // Obtenir l'index de la ligne cliquée
                    int row = table.getSelectedRow();

                    // Récupérer les données de la ligne sélectionnée
                    TableModel table_model = table.getModel();
                    if (row != -1) {
                        String adresse = (String) table_model.getValueAt(row, 2);
                        String complement = (String) table_model.getValueAt(row, 3);
                        String ville = (String) table_model.getValueAt(row, 1);
                        TypeLogement type = TypeLogement.fromString((String) table_model.getValueAt(row, 0));
                        int x=page_mes_biens.getFrame().getX();
                        int y=page_mes_biens.getFrame().getY();
                        try {
                            if(type.estBienLouable()) {
                                BienLouable bien = new BienLouableDAO().readFisc(new BienLouableDAO().getFiscFromCompl(ville, adresse, complement));
                                page_mes_biens.getFrame().dispose();


                                new PageMonBien(new BienLouableDAO().getId(bien.getNumeroFiscal()),type,x,y);
                            }else{
                                page_mes_biens.getFrame().dispose();
                                Integer IdBat = new BatimentDAO().getIdBat(ville,adresse);
                                new PageMonBien(IdBat,type,x,y);
                            }
                        } catch (DAOException exept) {
                            throw new RuntimeException(exept);
                        } catch (SQLException exept) {
                            throw new RuntimeException(exept);
                        }
                    }
                }
            }
        };
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
        page_mes_biens.getFrame().dispose();
    }
}
