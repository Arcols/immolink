package ihm;

import DAO.jdbc.LocataireDAO;
import classes.Locataire;
import modele.PageNouveauBienImmobilier;
import modele.PageNouveauLocataire;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
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

    public ActionListener getAjouterLocataireListener() {
        return e -> {
            java.sql.Date sqlDate = java.sql.Date.valueOf(pageNouveauLocataire.getDateValeur().getText());
            Locataire l = new Locataire(pageNouveauLocataire.getNomValeur().getText(),
                    pageNouveauLocataire.getPrenomValeur().getText(),
                    pageNouveauLocataire.getTelephoneValeur().getText(),
                    pageNouveauLocataire.getMailValeur().getText(), sqlDate,
                    (String) pageNouveauLocataire.getGenreValeur().getSelectedItem());
            LocataireDAO locataireDAO = new LocataireDAO();
            locataireDAO.addLocataire(l);
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
}
