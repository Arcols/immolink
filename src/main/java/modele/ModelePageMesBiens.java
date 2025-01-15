
package modele;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import DAO.DAOException;
import DAO.jdbc.BatimentDAO;
import DAO.jdbc.BienLouableDAO;
import classes.Batiment;
import classes.BienLouable;
import enumeration.TypeLogement;
import ihm.PageMesBiens;
import ihm.PageNouveauBienImmobilier;

public class ModelePageMesBiens {


    private PageMesBiens pageMesBiens;


    public ModelePageMesBiens(PageMesBiens pageMesBiens) {
        this.pageMesBiens = pageMesBiens;
    }

    public ActionListener ouvrirNouveauBien(){
        return e->{
            pageMesBiens.getFrame().dispose();
            PageNouveauBienImmobilier pageNouveauBienImmobilier = new PageNouveauBienImmobilier();
            PageNouveauBienImmobilier.main(null);
        };
    }

    /**
     * Charge les données des locataires dans un DefaultTableModel.
     *
     * @return DefaultTableModel rempli avec les données des locataires.
     * @throws SQLException si une erreur survient lors de la récupération des données.
     */
    public static DefaultTableModel loadDataBienImmoToTable() throws SQLException, DAOException {
        String[] columns = {"Type","Ville","Adresse","Complement"};

        DefaultTableModel model = new DefaultTableModel(columns, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Toutes les cellules sont non éditables
            }
        };

        BienLouableDAO bienLouableDAO = new BienLouableDAO();
        List<BienLouable> biensLouables = bienLouableDAO.findAll();
        Object[] rowData = new Object[4];
        // Remplissage du modèle avec les données des biens louables
        for (BienLouable bien : biensLouables) {
            rowData = new Object[]{
                    TypeLogement.getString(bien.getTypeLogement()),
                    bien.getVille(),
                    bien.getAdresse(),
                    bien.getComplementAdresse()
            };
            model.addRow(rowData);
        }

        BatimentDAO batimentDAO = new BatimentDAO();
        List<Batiment> batiments = batimentDAO.findAll();
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


}
