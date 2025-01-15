package modele;

import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import DAO.jdbc.LocataireDAO;
import DAO.jdbc.LouerDAO;
import DAO.jdbc.RegimeDAO;
import classes.Locataire;
import ihm.PageAccueil;
import ihm.PageDeclarationFiscale;
import ihm.PageNouveauLocataire;

public class ModelePageAccueil {


    private PageAccueil pageAccueil;

    public ModelePageAccueil(PageAccueil pageAccueil) {
        this.pageAccueil = pageAccueil;
    }
    /**
     * Charge les données des locataires dans un DefaultTableModel.
     *
     * @return DefaultTableModel rempli avec les données des locataires.
     * @throws SQLException si une erreur survient lors de la récupération des données.
     */
    public static DefaultTableModel loadDataLocataireToTable() throws SQLException {
        // Liste des colonnes
        String[] columnNames = {"Nom", "Prénom", "Lieu Naissance", "Date Naissance", "Téléphone", "Mail", "Genre", "Date arrivée","Statut"};

        // Création du modèle de table
        DefaultTableModel model = new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Toutes les cellules sont non éditables
            }
        };


        // Récupération des locataires
        LocataireDAO locataireDAO = new LocataireDAO();
        List<Locataire> locataires = locataireDAO.getAllLocataire();


        // Remplissage du modèle avec les données des locataires
        for (Locataire locataire : locataires) {
            Object[] rowData = {
                    locataire.getNom(),
                    locataire.getPrénom(),
                    locataire.getLieuNaissance(),
                    locataire.getDateNaissance(),
                    locataire.getTéléphone(),
                    locataire.getMail(),
                    locataire.getGenre(),
                    locataire.getDateArrive(),
                    statut(locataire)
            };
            model.addRow(rowData); // Ajout de la ligne dans le modèle
        }

        return model; // Retourne le modèle rempli
    }

    public static String statut(Locataire locataire){
        LouerDAO louerDAO = new LouerDAO();
        LocataireDAO locataireDAO = new LocataireDAO();
        if (louerDAO.getStatut(locataireDAO.getId(locataire))){
            return "Payé";
        }
        return "Retard";
    }

    /**
     * ActionListener pour actualiser le seuil microfoncier.
     */
    public static ActionListener getActionListenerForActualiser(JFrame parentFrame) {
        return e -> {
            JDialog dialog = new JDialog(parentFrame, "Saisir le seuil microfoncier", true);
            dialog.setSize(400, 200);
            dialog.setLayout(null);

            JLabel label = new JLabel("Seuil du régime microfoncier :");
            label.setBounds(20, 30, 200, 25);
            dialog.add(label);

            JTextField seuilField = new JTextField();
            seuilField.setBounds(220, 30, 100, 25);

            // Charger la valeur actuelle du seuil microfoncier
            Float valeurActuelle = getValeurRegime();
            if (valeurActuelle != null) {
                seuilField.setText(String.valueOf(valeurActuelle)); // Préremplit le champ avec la valeur actuelle
            }

            dialog.add(seuilField);

            JButton validerButton = new JButton("Valider");
            validerButton.setBounds(150, 100, 100, 30);
            dialog.add(validerButton);

            validerButton.addActionListener(event -> {
                try {
                    double seuil = Double.parseDouble(seuilField.getText());
                    setSeuilMicrofoncier(seuil); // Appelle la méthode métier pour enregistrer le seuil
                    JOptionPane.showMessageDialog(dialog,
                            "Le seuil du régime microfoncier a été mis à jour à " + seuil + " €.",
                            "Confirmation",
                            JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog,
                            "Veuillez entrer un nombre valide.",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(dialog,
                            "Erreur lors de l'enregistrement du seuil : " + ex.getMessage(),
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            });

            dialog.setLocationRelativeTo(parentFrame);
            dialog.setVisible(true);
        };
    }


    public DefaultTableCellRenderer couleurLigne() {
        return new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                // Appeler la méthode parente pour obtenir le composant par défaut
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Vérifier si la dernière colonne contient "Retard"
                int lastColumnIndex = table.getColumnCount() - 1;
                String status = table.getValueAt(row, lastColumnIndex).toString();

                // Appliquer une couleur de fond si "Retard"
                if ("Retard".equals(status)) {
                    component.setBackground(Color.decode("#f5b942"));
                    component.setForeground(Color.BLACK); // Contraste pour le texte
                } else {
                    component.setBackground(Color.decode("#7fe075")); // Couleur de fond par défaut
                    component.setForeground(Color.BLACK); // Texte noir par défaut
                }

                // Prioriser la couleur de sélection si la ligne est sélectionnée
                if (isSelected) {
                    component.setBackground(table.getSelectionBackground());
                    component.setForeground(table.getSelectionForeground());
                }

                return component;
            }
        };
    }

    /**
     * Méthode pour récupérer la valeur actuelle du seuil microfoncier depuis la base de données.
     *
     * @return La valeur actuelle du seuil, ou null si une erreur survient.
     */
    private static Float getValeurRegime() {
        try {
            RegimeDAO regimeDAO = new RegimeDAO();
            return regimeDAO.getValeur(); // Récupère la valeur depuis la DAO
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "Erreur lors de la récupération de la valeur actuelle : " + ex.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return null; // En cas d'erreur, retourne null
        }
    }

    /**
     * Met à jour la valeur du seuil microfoncier dans la base de données.
     *
     * @param seuil nouvelle valeur du seuil
     * @throws SQLException en cas d'erreur lors de l'enregistrement
     */
    private static void setSeuilMicrofoncier(double seuil) throws SQLException {
        RegimeDAO regimeDAO = new RegimeDAO();
        try {
            regimeDAO.updateValeur((float) seuil); // Utilise la DAO pour mettre à jour la valeur
        } catch (Exception e) {
            throw new SQLException("Impossible de mettre à jour le seuil microfoncier.", e);
        }
    }

    public ActionListener ouvrirNouveauLocataire(){
        return e->{
            pageAccueil.getFrame().dispose();
            PageNouveauLocataire PageNouveauLocataire = new PageNouveauLocataire();
            PageNouveauLocataire.main(null);
        };
    }

    public ActionListener choix_annee() {
        return e -> {
            JDialog dialog = new JDialog((Frame) null, "Saisir année de la déclaration fiscale ", true);
            dialog.setSize(400, 200);
            dialog.setLayout(null);

            JLabel label = new JLabel("Année de la déclaration fiscale :");
            label.setBounds(20, 30, 200, 25);
            dialog.add(label);

            JTextField choix_annee = new JTextField();
            choix_annee.setBounds(220, 30, 100, 25);
            dialog.add(choix_annee);

            JButton validerButton = new JButton("Valider");
            validerButton.setBounds(150, 100, 100, 30);
            dialog.add(validerButton);

            validerButton.addActionListener(event -> {
                dialog.dispose();
                this.pageAccueil.getFrame().dispose();
                PageDeclarationFiscale pagedecla = new PageDeclarationFiscale(choix_annee.getText());
            });

            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        };
    }
}
