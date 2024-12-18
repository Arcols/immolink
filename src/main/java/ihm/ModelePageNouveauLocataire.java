package ihm;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import DAO.jdbc.LocataireDAO;
import classes.Locataire;
import modele.PageAccueil;
import modele.PageNouveauLocataire;

public class ModelePageNouveauLocataire {
    private PageNouveauLocataire pageNouveauLocataire;

    public ModelePageNouveauLocataire(PageNouveauLocataire pageNouveauLocataire) {
        this.pageNouveauLocataire = pageNouveauLocataire;
    }

    public ActionListener getAjouterLocataireListener() {
        return e -> {
            try {
                java.sql.Date sqlDate = new java.sql.Date(pageNouveauLocataire.getDateChooser().getDate().getTime());
                Locataire l = new Locataire(pageNouveauLocataire.getNomValeur().getText(),
                        pageNouveauLocataire.getPrenomValeur().getText(),
                        pageNouveauLocataire.getTelephoneValeur().getText(),
                        pageNouveauLocataire.getMailValeur().getText(), sqlDate,
                        (String) pageNouveauLocataire.getGenreValeur().getSelectedItem());
                LocataireDAO locataireDAO = new LocataireDAO();
                locataireDAO.addLocataire(l);
                JOptionPane.showMessageDialog(null,"Le locataire a été ajouté avec succès.","Information",JOptionPane.INFORMATION_MESSAGE);
                refreshPage(e);
            } catch (IllegalArgumentException ex) {
                // Gestion d'une date invalide ou autre erreur
                JOptionPane.showMessageDialog(null, "Erreur : Veuillez entrer une date valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                // Gestion d'erreurs inattendues
                JOptionPane.showMessageDialog(null, "Une erreur est survenue : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        };
    }

    public DocumentListener getTextFieldDocumentListener() {
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                pageNouveauLocataire.checkFields();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                pageNouveauLocataire.checkFields();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                pageNouveauLocataire.checkFields();
            }
        };
    }

    public ActionListener getVilleActionListener(Map<String, List<String>> mapVillesAdresses) {
        return e -> {
            String selectedVille = (String) this.pageNouveauLocataire.getVilleValeur().getSelectedItem();
            if (!mapVillesAdresses.containsKey(selectedVille)) {
                this.pageNouveauLocataire.getAdresseValeur().setModel(new DefaultComboBoxModel());
            } else {
                this.pageNouveauLocataire.getAdresseValeur().setModel(
                        new DefaultComboBoxModel(mapVillesAdresses.get(selectedVille).toArray(new String[0])));
                this.pageNouveauLocataire.getAdresseValeur().setModel(
                        new DefaultComboBoxModel(mapVillesAdresses.get(selectedVille).toArray(new String[0])));
            }
        };
    }

    public ActionListener quitterBouton(){
        return e -> {
            pageNouveauLocataire.getFrame().dispose();
            PageAccueil PageAccueil = new PageAccueil();
            PageAccueil.main(null);
        };
    }

    private void refreshPage(ActionEvent e) {
        JFrame ancienneFenetre = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
        ancienneFenetre.dispose();
        PageNouveauLocataire nouvellePage = new PageNouveauLocataire();
        nouvellePage.getFrame().setVisible(true);
    }
}
