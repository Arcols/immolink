
package modele;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import DAO.jdbc.*;
import classes.BienLouable;
import classes.Locataire;
import ihm.*;


public class ModelePageUnLocataire {
    
    private final PageUnLocataire page_un_locataire;
    
    public ModelePageUnLocataire(PageUnLocataire page_un_locataire) {
        this.page_un_locataire = page_un_locataire;
    }
    
    /**
     * Charge les données des locataires dans un DefaultTableModel.
     *
     * @return DefaultTableModel rempli avec les données des locataires.
     * @throws SQLException si une erreur survient lors de la récupération des données.
     */
    public static DefaultTableModel loadDataBauxToTable(Locataire locataire) throws SQLException, DAOException {
        // Définition des colonnes avec une colonne "Payé" qui contiendra des cases à cocher
        String[] colonnes = {"Ville", "Adresse", "Complément", "Payé", "id_bail"};

        // Création du modèle avec surcharge pour empêcher l'édition des cellules, sauf la colonne "Payé"
        DefaultTableModel model = new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int ligne, int colonne) {
                return colonne == 3; // Seule la colonne "Payé" est éditable
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 3) {
                    return Boolean.class; // La colonne "Payé" contiendra des cases à cocher
                }
                return String.class; // Les autres colonnes contiendront des chaînes de caractères
            }
        };

        LocataireDAO locataire_DAO = new LocataireDAO();
        List<Integer> id_baux = locataire_DAO.getBauxLocataire(locataire_DAO.getId(locataire));
        Object[] donnee_ligne;

        // Remplissage du modèle avec les données des biens louables
        for (Integer id_bail : id_baux) {
            BienLouable bien_louable = new BienLouableDAO().readId(new BailDAO().getIdBienLouable(id_bail));

            donnee_ligne = new Object[]{
                    bien_louable.getVille(),
                    bien_louable.getAdresse(),
                    bien_louable.getComplementAdresse(),
                    new LouerDAO().getLoyerPaye(locataire_DAO.getId(locataire),id_bail),
                    id_bail
            };
            model.addRow(donnee_ligne);
        }

        return model; // Retourne le modèle rempli
    }

    public TableModelListener modifPaiement(DefaultTableModel modele_table, Locataire locataire) {
        return e -> {
            int ligne = e.getFirstRow();
            int colonne = e.getColumn();

            // Vérifie si c'est la colonne "Payé"
            if (colonne == 3) { // Colonne 3 correspond à "Payé"
                Boolean paiement = (Boolean) modele_table.getValueAt(ligne, colonne);
                Integer id_bail = (Integer) modele_table.getValueAt(ligne, 4);
                LouerDAO louer_DAO=new LouerDAO();

                // Action à effectuer
                if (paiement != null) {
                    if (paiement) {
                        louer_DAO.updatePaiement(id_bail,new LocataireDAO().getId(locataire), Date.valueOf(LocalDate.now()));
                    } else {
                        louer_DAO.updatePaiement(id_bail,new LocataireDAO().getId(locataire), Date.valueOf(LocalDate.now().minusMonths(1)));
                    }
                } else {
                    System.err.println("Erreur : La valeur de paiement est nulle.");
                }
            }
        };
    }

    public ActionListener quitterPage(){
        return e -> {
            page_un_locataire.getFrame().dispose();
            PageAccueil.main(null);
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
        page_un_locataire.getFrame().dispose();
    }
}
