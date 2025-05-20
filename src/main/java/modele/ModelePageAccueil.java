package modele;

import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import DAO.db.ConnectionDB;
import DAO.jdbc.LocataireDAO;
import DAO.jdbc.LouerDAO;
import DAO.jdbc.RegimeDAO;
import classes.Locataire;
import ihm.PageAccueil;
import ihm.PageDeclarationFiscale;
import ihm.PageNouveauLocataire;
import ihm.PageUnLocataire;

public class ModelePageAccueil {

    private final PageAccueil page_accueil;

    public ModelePageAccueil(PageAccueil page_accueil) {
        this.page_accueil = page_accueil;
    }
    /**
     * Charge les données des locataires dans un DefaultTableModel.
     *
     * @return DefaultTableModel rempli avec les données des locataires.
     * @throws SQLException si une erreur survient lors de la récupération des données.
     */
    public static DefaultTableModel loadDataLocataireToTable() throws SQLException {
        // Liste des colonnes
        String[] nom_colonne = {"Nom", "Prénom", "Lieu Naissance", "Date Naissance", "Téléphone", "Mail", "Genre", "Date arrivée","Statut"};

        // Création du modèle de table
        DefaultTableModel model = new DefaultTableModel(nom_colonne, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Toutes les cellules sont non éditables
            }
        };


        // Récupération des locataires
        LocataireDAO locataire_DAO = new LocataireDAO();
        List<Locataire> locataires = locataire_DAO.getAllLocataire();


        // Remplissage du modèle avec les données des locataires
        for (Locataire locataire : locataires) {
            Object[] rowData = {
                    locataire.getNom(),
                    locataire.getPrenom(),
                    locataire.getLieuNaissance(),
                    locataire.getDateNaissance(),
                    locataire.getTelephone(),
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
        LouerDAO louer_DAO = new LouerDAO();
        LocataireDAO locataire_DAO = new LocataireDAO();
        if (louer_DAO.getStatut(locataire_DAO.getId(locataire))){
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

            JTextField seuil_field = new JTextField();
            seuil_field.setBounds(220, 30, 100, 25);

            // Charger la valeur actuelle du seuil microfoncier
            Float valeur_actuelle = getValeurRegime();
            if (valeur_actuelle != null) {
                seuil_field.setText(String.valueOf(valeur_actuelle)); // Préremplit le champ avec la valeur actuelle
            }

            dialog.add(seuil_field);

            JButton valider_bouton = new JButton("Valider");
            valider_bouton.setBounds(150, 100, 100, 30);
            dialog.add(valider_bouton);

            valider_bouton.addActionListener(event -> {
                try {
                    double seuil = Double.parseDouble(seuil_field.getText());
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
            RegimeDAO regime_DAO = new RegimeDAO();
            return regime_DAO.getValeur(); // Récupère la valeur depuis la DAO
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
        RegimeDAO regime_DAO = new RegimeDAO();
        try {
            regime_DAO.updateValeur((float) seuil); // Utilise la DAO pour mettre à jour la valeur
        } catch (Exception e) {
            throw new SQLException("Impossible de mettre à jour le seuil microfoncier.", e);
        }
    }

    public ActionListener ouvrirNouveauLocataire(){
        return e->{
            page_accueil.getFrame().dispose();
            int x=page_accueil.getFrame().getX();
            int y=page_accueil.getFrame().getY();
            new PageNouveauLocataire(x,y);
        };
    }

    public MouseAdapter mouseAdapter(JTable table) {
        return new MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                super.mouseClicked(e);
                TableModel finalModel = table.getModel();
                // Vérifier s'il s'agit d'un double-clic
                if (e.getClickCount() == 2) {
                    // Obtenir l'index de la ligne cliquée
                    int row = table.getSelectedRow();

                    // Récupérer les données de la ligne sélectionnée
                    if (row != -1) {
                        String nom = (String) finalModel.getValueAt(row, 0);
                        String prenom = (String) finalModel.getValueAt(row, 1);
                        String telephone = (String) finalModel.getValueAt(row, 4);

                        LocataireDAO locataireDAO = new LocataireDAO();
                        Locataire locataire = locataireDAO.getLocataireByNomPrenom(nom, prenom, telephone);
                        page_accueil.getFrame().dispose();
                        int x=page_accueil.getFrame().getX();
                        int y=page_accueil.getFrame().getY();

                        new PageUnLocataire(locataire,x,y);

                        // Ouvrir une nouvelle fenêtre avec ces données
                    }
                }
            }
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

            JButton valider_bouton = new JButton("Valider");
            valider_bouton.setBounds(150, 100, 100, 30);
            dialog.add(valider_bouton);

            valider_bouton.addActionListener(event -> {
                dialog.dispose();
                this.page_accueil.getFrame().dispose();
                int x=page_accueil.getFrame().getX();
                int y=page_accueil.getFrame().getY();

                new PageDeclarationFiscale(choix_annee.getText(),x,y);
            });

            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
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
        page_accueil.getFrame().dispose();
    }
}
