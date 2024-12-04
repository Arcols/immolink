package ihm;

import DAO.jdbc.LocataireDAO;
import classes.Locataire;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ModelePageAccueil {

    /**
     * Charge les données des locataires dans un DefaultTableModel.
     *
     * @return DefaultTableModel rempli avec les données des locataires.
     * @throws SQLException si une erreur survient lors de la récupération des données.
     */
    public static DefaultTableModel loadDataLocataireToTable() throws SQLException {
        // Liste des colonnes
        String[] columnNames = {"Nom", "Prénom", "Téléphone", "Mail", "Genre", "Date d'arrivée"};

        // Création du modèle de table
        DefaultTableModel model = new DefaultTableModel(columnNames, 0); // `0` pour aucune ligne au départ

        // Récupération des locataires
        LocataireDAO locataireDAO = new LocataireDAO();
        List<Locataire> locataires = locataireDAO.getAllLocataire();

        // Remplissage du modèle avec les données des locataires
        for (Locataire locataire : locataires) {
            Object[] rowData = {
                    locataire.getNom(),
                    locataire.getPrénom(),
                    locataire.getTéléphone(),
                    locataire.getMail(),
                    locataire.getGenre(),
                    locataire.getDateArrive()
            };
            model.addRow(rowData); // Ajout de la ligne dans le modèle
        }

        return model; // Retourne le modèle rempli
    }
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
    private static void setSeuilMicrofoncier(double seuil) throws SQLException {
        // remplir

    }
}
