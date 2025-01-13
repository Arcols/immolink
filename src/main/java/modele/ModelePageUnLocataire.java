
package modele;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import DAO.DAOException;
import DAO.jdbc.*;
import classes.Bail;
import classes.Batiment;
import classes.BienLouable;
import classes.Locataire;
import enumeration.TypeLogement;
import ihm.PageNouveauBienImmobilier;
import ihm.PageUnLocataire;


public class ModelePageUnLocataire {


    private PageUnLocataire pageUnLocataire;


    public ModelePageUnLocataire(PageUnLocataire pageUnLocataire) {
        this.pageUnLocataire = pageUnLocataire;
    }

    public ActionListener ouvrirNouveauBien(){
        return e->{
            pageUnLocataire.getFrame().dispose();
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
    public static DefaultTableModel loadDataBienImmoToTable(Locataire locataire) throws SQLException, DAOException {
        // Définition des colonnes avec une colonne "Payé" qui contiendra des cases à cocher
        String[] columns = {"Ville", "Adresse", "Complément", "Payé"};

        // Création du modèle avec surcharge pour empêcher l'édition des cellules, sauf la colonne "Payé"
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // Seule la colonne "Payé" est éditable
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 3) {
                    return Boolean.class; // La colonne "Payé" contiendra des cases à cocher
                }
                return String.class; // Les autres colonnes contiendront des chaînes de caractères
            }
        };

        LocataireDAO locataireDAO = new LocataireDAO();
        List<Integer> idBaux = locataireDAO.getBauxLocataire(locataireDAO.getId(locataire));
        Object[] rowData = new Object[4];

        // Remplissage du modèle avec les données des biens louables
        for (Integer idBail : idBaux) {
            BienLouable bienLouable = new BienLouableDAO().readId(new BailDAO().getIdBienLouable(idBail));

            // Ajout des données dans la table avec la colonne "Payé" initialisée à false
            rowData = new Object[]{
                    bienLouable.getVille(),
                    bienLouable.getAdresse(),
                    bienLouable.getComplement_adresse(),
                    new LouerDAO().getLoyerPaye(locataireDAO.getId(locataire),idBail) // Par défaut, la case "Payé" est décochée
            };
            model.addRow(rowData);
        }

        return model; // Retourne le modèle rempli
    }



}
