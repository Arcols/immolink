package modele;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import DAO.jdbc.LocataireDAO;
import classes.Locataire;
import ihm.PageAccueil;
import ihm.PageNouveauLocataire;

public class ModelePageNouveauLocataire {
    private PageNouveauLocataire pageNouveauLocataire;

    public ModelePageNouveauLocataire(PageNouveauLocataire pageNouveauLocataire) {
        this.pageNouveauLocataire = pageNouveauLocataire;
    }

    public ActionListener getAjouterLocataireListener() {
        return e -> {
            try {
                java.sql.Date sqlDate = new java.sql.Date(pageNouveauLocataire.getDateChooser().getDate().getTime());
                java.sql.Date dateNaiss = new java.sql.Date(pageNouveauLocataire.getDateNaissanceChooser().getDate().getTime());
                if(pageNouveauLocataire.getGenreValeur().getSelectedItem().equals("C")){
                    java.sql.Date dateNaiss2 = new java.sql.Date(pageNouveauLocataire.getDateNaissanceChooser2().getDate().getTime());
                    Locataire l = new Locataire(pageNouveauLocataire.getNomValeur().getText()+", "+pageNouveauLocataire.getNomValeur2().getText(),
                            pageNouveauLocataire.getPrenomValeur().getText()+", "+pageNouveauLocataire.getPrenomValeur2().getText(),
                            pageNouveauLocataire.getLieuNaissanceValeur().getText()+", "+pageNouveauLocataire.getLieuNaissanceValeur2().getText(),
                            dateNaiss.toString()+", "+dateNaiss2.toString(),
                            pageNouveauLocataire.getTelephoneValeur().getText(),
                            pageNouveauLocataire.getMailValeur().getText(),
                            sqlDate, (String) pageNouveauLocataire.getGenreValeur().getSelectedItem());
                    LocataireDAO locataireDAO = new LocataireDAO();
                    locataireDAO.addLocataire(l);
                    JOptionPane.showMessageDialog(null,"Les locataires ont été ajoutés avec succès.","Information",JOptionPane.INFORMATION_MESSAGE);
                    refreshPage(e);
                }else{
                    Locataire l = new Locataire(pageNouveauLocataire.getNomValeur().getText(),
                            pageNouveauLocataire.getPrenomValeur().getText(),
                            pageNouveauLocataire.getLieuNaissanceValeur().getText(),
                            dateNaiss.toString(),
                            pageNouveauLocataire.getTelephoneValeur().getText(),
                            pageNouveauLocataire.getMailValeur().getText(), sqlDate,
                            (String) pageNouveauLocataire.getGenreValeur().getSelectedItem());
                    LocataireDAO locataireDAO = new LocataireDAO();
                    locataireDAO.addLocataire(l);
                    JOptionPane.showMessageDialog(null,"Les locataires ont été ajoutés avec succès.","Information",JOptionPane.INFORMATION_MESSAGE);
                    refreshPage(e);
                }
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
                pageNouveauLocataire.checkFieldsPasColloc();
                pageNouveauLocataire.checkFieldsColloc();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                pageNouveauLocataire.checkFieldsPasColloc();
                pageNouveauLocataire.checkFieldsColloc();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                pageNouveauLocataire.checkFieldsPasColloc();
                pageNouveauLocataire.checkFieldsColloc();
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

    public ActionListener getChoixGenreValeurListener() {
        return e -> {
            String selectedType = (String) this.pageNouveauLocataire.getGenreValeur().getSelectedItem();

            boolean isNotCouple = "H".equals(selectedType) || "F".equals(selectedType);
            boolean isCouple = "C".equals(selectedType);

            this.pageNouveauLocataire.getEnregistrerButtonPasColloc().setVisible(isNotCouple);
            this.pageNouveauLocataire.getQuitterButtonPasColloc().setVisible(isNotCouple);

            this.pageNouveauLocataire.getEnregistrerButtonColloc().setVisible(isCouple);
            this.pageNouveauLocataire.getQuitterColloc().setVisible(isCouple);

            this.pageNouveauLocataire.getLabelColoc1().setVisible(isCouple);
            this.pageNouveauLocataire.getLabelColoc2().setVisible(isCouple);
            this.pageNouveauLocataire.getLabelNom2().setVisible(isCouple);
            this.pageNouveauLocataire.getLabelPrenom2().setVisible(isCouple);
            this.pageNouveauLocataire.getNomValeur2().setVisible(isCouple);
            this.pageNouveauLocataire.getPrenomValeur2().setVisible(isCouple);
            this.pageNouveauLocataire.getLabelLieuNaissance2().setVisible(isCouple);
            this.pageNouveauLocataire.getLieuNaissanceValeur2().setVisible(isCouple);
            this.pageNouveauLocataire.getLabelDateNaissance2().setVisible(isCouple);
            this.pageNouveauLocataire.getDateNaissanceChooser2().setVisible(isCouple);

        };
    }
}
