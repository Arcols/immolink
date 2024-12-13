package ihm;

import DAO.DAOException;
import DAO.jdbc.BienLouableDAO;
import DAO.jdbc.LocataireDAO;
import classes.BienLouable;
import classes.Locataire;
import modele.PageMesBiens;
import modele.PageNouveauBail;
import modele.PageNouveauBienImmobilier;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

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
    public static String[][] loadDataBienImmoToTable() throws SQLException, DAOException {

        // Récupération des locataires
        BienLouableDAO bienLouableDAO = new BienLouableDAO();
        List<BienLouable> biensLouables = bienLouableDAO.findAll();

        // Remplissage du modèle avec les données des locataires
        int i = 0;
        String[][] rowData = new String[biensLouables.size()][];
        String[] ligne;
        for (BienLouable bien : biensLouables) {
             ligne = new String[]{
                     bien.getAdresse(),
                     bien.getComplement_adresse(),
                     bien.getVille()
             };
             rowData[i] = ligne;
             i++;
        }

        return rowData; // Retourne le modèle rempli
    }


}
