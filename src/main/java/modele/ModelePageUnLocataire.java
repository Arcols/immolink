
package modele;

import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import DAO.DAOException;
import DAO.jdbc.*;
import classes.BienLouable;
import classes.Locataire;
import ihm.*;


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
    public static DefaultTableModel loadDataBauxToTable(Locataire locataire) throws SQLException, DAOException {
        // Définition des colonnes avec une colonne "Payé" qui contiendra des cases à cocher
        String[] columns = {"Ville", "Adresse", "Complément", "Payé", "idBail"};

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

            rowData = new Object[]{
                    bienLouable.getVille(),
                    bienLouable.getAdresse(),
                    bienLouable.getComplementAdresse(),
                    new LouerDAO().getLoyerPaye(locataireDAO.getId(locataire),idBail),
                    idBail
            };
            model.addRow(rowData);
        }

        return model; // Retourne le modèle rempli
    }

    public TableModelListener modifPaiement(DefaultTableModel tableModel, Locataire locataire) {
        return new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();

                // Vérifie si c'est la colonne "Payé"
                if (column == 3) { // Colonne 3 correspond à "Payé"
                    Boolean paiement = (Boolean) tableModel.getValueAt(row, column);
                    Integer idBail = (Integer) tableModel.getValueAt(row, 4);
                    LouerDAO louerDAO=new LouerDAO();

                    // Action à effectuer
                    if (paiement != null) {
                        if (paiement) {
                            louerDAO.updatePaiement(idBail,new LocataireDAO().getId(locataire), Date.valueOf(LocalDate.now()));
                        } else {
                            louerDAO.updatePaiement(idBail,new LocataireDAO().getId(locataire), Date.valueOf(LocalDate.now().minusMonths(1)));
                        }
                    } else {
                        System.err.println("Erreur : La valeur de paiement est nulle.");
                    }
                }
            }
        };
    }

    public ActionListener quitterPage(){
        return e -> {
            pageUnLocataire.getFrame().dispose();
            PageAccueil.main(null);
        };
    }


}
