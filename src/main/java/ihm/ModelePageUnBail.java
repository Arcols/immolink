package ihm;

import DAO.DAOException;
import DAO.jdbc.BailDAO;
import DAO.jdbc.BienLouableDAO;
import DAO.jdbc.DevisDAO;
import DAO.jdbc.LogementDAO;
import classes.Bail;
import classes.BienLouable;
import classes.Locataire;
import classes.Logement;
import enumeration.TypeLogement;
import modele.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ModelePageUnBail {
    private PageUnBail pageUnBail;

    public ModelePageUnBail(PageUnBail pageUnBail){
        this.pageUnBail = pageUnBail;
    }

    public void chargerDonneesBail(int idBail, PageUnBail page) throws DAOException {
        try {
            // Récupération des informations du bien via le DAO
            BailDAO bailDAO = new DAO.jdbc.BailDAO();
            Bail bail = bailDAO.getBailFromId(idBail);

            BienLouableDAO bienLouableDAO = new DAO.jdbc.BienLouableDAO();
            BienLouable bienLouable = bienLouableDAO.readId(bailDAO.getIdBienLouable(idBail));

            LogementDAO logementDAO = new DAO.jdbc.LogementDAO();
            Logement logement = logementDAO.read(bailDAO.getIdBienLouable(idBail));

            DevisDAO devisDAO =new DevisDAO();

            if (bail != null) {
                // Mise à jour des labels avec les informations du bien
                page.getAffichageVille().setText(bienLouable.getVille());
                page.getAffichageAdresse().setText(bienLouable.getAdresse());
                page.getAffichageComplement().setText(bienLouable.getComplement_adresse());
                page.getAffichageSurface().setText(String.valueOf(logement.getSurface()));
                page.getAffichageNbPieces().setText(String.valueOf(logement.getNbPiece()));
                page.getAffichageLoyer().setText(String.valueOf(bail.getLoyer()));
                page.getAffichageProvision().setText(String.valueOf(bail.getCharge()));
                page.getAffichageGarantie().setText(String.valueOf(bail.getDepot_garantie()));
            }
        } catch (DAOException e) {
            throw new DAOException("Erreur lors du chargement des informations du bien : " + e.getMessage(), e);
        }
    }

    public ActionListener getActionListenerForModifierLoyer(JFrame parentFrame, int idBail) {
        return e -> {

            BailDAO bailDAO = new DAO.jdbc.BailDAO();
            Bail bail = bailDAO.getBailFromId(idBail);

            JDialog dialog = new JDialog(parentFrame, "Modifier le loyer", true);
            dialog.setSize(400, 200);
            dialog.setLayout(null);

            JLabel label = new JLabel("Entrez le nouveau loyer :");
            label.setBounds(20, 30, 200, 25);
            dialog.add(label);

            JTextField loyerField = new JTextField();
            loyerField.setBounds(220, 30, 100, 25);

            // Charger la valeur actuelle du loyer
            Double valeurActuelle = bail.getLoyer();
            if (valeurActuelle != null) {
                loyerField.setText(String.valueOf(valeurActuelle));
            }

            dialog.add(loyerField);

            JButton validerButton = new JButton("Valider");
            validerButton.setBounds(150, 100, 100, 30);
            dialog.add(validerButton);

            validerButton.addActionListener(event -> {
                try {
                    double loyer = Double.parseDouble(loyerField.getText());

                    // Vérification : le loyer ne peut pas être négatif
                    if (loyer < 0) {
                        JOptionPane.showMessageDialog(dialog,
                                "Le loyer ne peut pas être une valeur négative.",
                                "Erreur",
                                JOptionPane.ERROR_MESSAGE);
                        return; // Arrête l'exécution si la valeur est négative
                    }

                    bailDAO.updateLoyer(idBail, loyer);  // Met à jour le loyer dans la base
                    JOptionPane.showMessageDialog(dialog,
                            "Le loyer a été mis à jour à " + loyer + " €.",
                            "Confirmation",
                            JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                    refreshPage(e,idBail);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog,
                            "Veuillez entrer un nombre valide.",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            });

            dialog.setLocationRelativeTo(parentFrame);
            dialog.setVisible(true);
        };
    }

    private static void refreshPage(ActionEvent e, int idBail) {
        BailDAO bailDAO = new DAO.jdbc.BailDAO();
        Bail bail = bailDAO.getBailFromId(idBail);
        JFrame ancienneFenetre = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
        ancienneFenetre.dispose();
        PageUnBail nouvellePage = new PageUnBail(bail);
        nouvellePage.getFrame().setVisible(true);
    }

    public ActionListener quitterPage(){
        return e -> {
            pageUnBail.getFrame().dispose();
            PageBaux pageMesBiens = new PageBaux();
            PageBaux.main(null);
        };
    }
}
