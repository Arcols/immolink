package ihm;

import classes.Locataire;
import modele.PageNouveauLocataire;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ModelePageNouveauLocataire {
    private PageNouveauLocataire pageNouveauLocataire;

    public ModelePageNouveauLocataire(PageNouveauLocataire pageNouveauLocataire) {
        this.pageNouveauLocataire = pageNouveauLocataire;
    }

    public ActionListener getAjouterLocataireListener(){
        return e->{
            java.sql.Date sqlDate = java.sql.Date.valueOf(pageNouveauLocataire.getDateValeur().getText());
            try {
                Locataire l = new Locataire(pageNouveauLocataire.getNomValeur().getText(), pageNouveauLocataire.getPrenomValeur().getText(), pageNouveauLocataire.getTelephoneValeur().getText(),
                        pageNouveauLocataire.getMailValeur().getText(), sqlDate, (String) pageNouveauLocataire.getGenreValeur().getSelectedItem());

                JOptionPane.showMessageDialog(null,"Le locataire a bien été ajouté !", "Succès",JOptionPane.INFORMATION_MESSAGE);

                // Fermer l'ancienne page
                JFrame ancienneFenetre = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
                ancienneFenetre.dispose();

                // Ouvrir une nouvelle instance de la même page
                PageNouveauLocataire nouvellePage = new PageNouveauLocataire(); // Remplacez par le constructeur de votre page
                nouvellePage.getFrame().setVisible(true);
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null,"Erreur dans la saisie d'information", "Erreur de saisie",JOptionPane.ERROR_MESSAGE);
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
                this.pageNouveauLocataire.getAdresseValeur().setModel(new DefaultComboBoxModel(mapVillesAdresses.get(selectedVille).toArray(new String[0])));
            }
        };
    }
}
