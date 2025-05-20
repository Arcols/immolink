package modele;

import java.awt.Component;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import DAO.db.ConnectionDB;
import DAO.jdbc.BatimentDAO;
import DAO.jdbc.LocataireDAO;
import classes.Locataire;
import ihm.PageAccueil;
import ihm.PageNouveauLocataire;

public class ModelePageNouveauLocataire {
    private final PageNouveauLocataire page_nouveau_locataire;

    public ModelePageNouveauLocataire(PageNouveauLocataire page_nouveau_locataire) {
        this.page_nouveau_locataire = page_nouveau_locataire;
    }

    public ActionListener getAjouterLocataireListener() {
        return e -> {
            try {
                // Vérification si le téléphone est un integer
                Integer.parseInt(page_nouveau_locataire.getTelephoneValeur().getText());
                if (page_nouveau_locataire.getTelephoneValeur().getText().length() != 10) {
                    throw new NumberFormatException("Numéro de téléphone invalide");
                }
                try {
                    java.sql.Date date_SQL = new java.sql.Date(page_nouveau_locataire.getDateChooser().getDate().getTime());
                    java.sql.Date date_naissance = new java.sql.Date(page_nouveau_locataire.getDateNaissanceChooser().getDate().getTime());
                    if (page_nouveau_locataire.getGenreValeur().getSelectedItem().equals("C")) {
                        java.sql.Date date_naissance2 = new java.sql.Date(page_nouveau_locataire.getDateNaissanceChooser2().getDate().getTime());
                        Locataire l = new Locataire(page_nouveau_locataire.getNomValeur().getText() + ", " + page_nouveau_locataire.getNomValeur2().getText(),
                                page_nouveau_locataire.getPrenomValeur().getText() + ", " + page_nouveau_locataire.getPrenomValeur2().getText(),
                                page_nouveau_locataire.getLieuNaissanceValeur().getText() + ", " + page_nouveau_locataire.getLieuNaissanceValeur2().getText(),
                                date_naissance + ", " + date_naissance2,
                                page_nouveau_locataire.getTelephoneValeur().getText(),
                                page_nouveau_locataire.getMailValeur().getText(),
                                date_SQL, (String) page_nouveau_locataire.getGenreValeur().getSelectedItem());
                        LocataireDAO locataireDAO = new LocataireDAO();
                        locataireDAO.addLocataire(l);
                    } else {
                        Locataire l = new Locataire(page_nouveau_locataire.getNomValeur().getText(),
                                page_nouveau_locataire.getPrenomValeur().getText(),
                                page_nouveau_locataire.getLieuNaissanceValeur().getText(),
                                date_naissance.toString(),
                                page_nouveau_locataire.getTelephoneValeur().getText(),
                                page_nouveau_locataire.getMailValeur().getText(), date_SQL,
                                (String) page_nouveau_locataire.getGenreValeur().getSelectedItem());
                        LocataireDAO locataireDAO = new LocataireDAO();
                        locataireDAO.addLocataire(l);
                    }
                    JOptionPane.showMessageDialog(null, "Les locataires ont été ajoutés avec succès.", "Information", JOptionPane.INFORMATION_MESSAGE);
                    refreshPage(e);
                } catch (IllegalArgumentException ex) {
                    // Gestion d'une date invalide ou autre erreur
                    popUpError("Erreur : Veuillez entrer une date valide.");
                } catch (Exception ex) {
                    // Gestion d'erreurs inattendues
                    popUpError("Une erreur est survenue : " + ex.getMessage());
                }
            }catch(NumberFormatException ex){
                popUpError("Erreur : Veuillez entrer un numéro de téléphone valide.");
            }
        };
    }

    private void popUpError(String message) {
        JOptionPane.showMessageDialog(null, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    public DocumentListener getTextFieldDocumentListener() {
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                page_nouveau_locataire.checkFieldsPasColloc();
                page_nouveau_locataire.checkFieldsColloc();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                page_nouveau_locataire.checkFieldsPasColloc();
                page_nouveau_locataire.checkFieldsColloc();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                page_nouveau_locataire.checkFieldsPasColloc();
                page_nouveau_locataire.checkFieldsColloc();
            }
        };
    }

    public ActionListener quitterBouton(){
        return e -> {
            int x=page_nouveau_locataire.getFrame().getX();
            int y=page_nouveau_locataire.getFrame().getY();
            page_nouveau_locataire.getFrame().dispose();

            new PageAccueil(x,y);
        };
    }

    private void refreshPage(ActionEvent e) {
        JFrame ancienne_fenetre = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
        ancienne_fenetre.dispose();
        int x=ancienne_fenetre.getX();
        int y=ancienne_fenetre.getY();

        PageNouveauLocataire nouvelle_page = new PageNouveauLocataire(x,y);
        nouvelle_page.getFrame().setVisible(true);
    }

    public ActionListener getChoixGenreValeurListener() {
        return e -> {
            String type_choisi = (String) this.page_nouveau_locataire.getGenreValeur().getSelectedItem();

            boolean pas_couple = "H".equals(type_choisi) || "F".equals(type_choisi);
            boolean est_couple = "C".equals(type_choisi);

            this.page_nouveau_locataire.getEnregistrerButtonPasColloc().setVisible(pas_couple);
            this.page_nouveau_locataire.getQuitterButtonPasColloc().setVisible(pas_couple);

            this.page_nouveau_locataire.getEnregistrerButtonColloc().setVisible(est_couple);
            this.page_nouveau_locataire.getQuitterColloc().setVisible(est_couple);

            this.page_nouveau_locataire.getLabelColoc1().setVisible(est_couple);
            this.page_nouveau_locataire.getLabelColoc2().setVisible(est_couple);
            this.page_nouveau_locataire.getLabelNom2().setVisible(est_couple);
            this.page_nouveau_locataire.getLabelPrenom2().setVisible(est_couple);
            this.page_nouveau_locataire.getNomValeur2().setVisible(est_couple);
            this.page_nouveau_locataire.getPrenomValeur2().setVisible(est_couple);
            this.page_nouveau_locataire.getLabelLieuNaissance2().setVisible(est_couple);
            this.page_nouveau_locataire.getLieuNaissanceValeur2().setVisible(est_couple);
            this.page_nouveau_locataire.getLabelDateNaissance2().setVisible(est_couple);
            this.page_nouveau_locataire.getDateNaissanceChooser2().setVisible(est_couple);

        };
    }

    public Map<String, List<String>> getVilles() throws SQLException {
        return new BatimentDAO().searchAllBatiments();
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
        page_nouveau_locataire.getFrame().dispose();
    }
}
