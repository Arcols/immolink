package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import modele.Menu;

import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.toedter.calendar.JDateChooser;


import classes.Locataire;
import modele.Charte;
import modele.ModelePageNouveauLocataire;
import modele.ResizedImage;

public class PageNouveauLocataire extends PageAbstraite{

    private JFrame frame;
    private JTextField nom_valeur;
    private JTextField prenom_valeur;
    private JTextField lieu_naissance_valeur;
    private JDateChooser choix_date_naissance;
    private JTextField telephone_valeur;
    private JTextField mail_valeur;
    private JDateChooser choix_date;
    private JComboBox genre_valeur;
    private JButton enregistrer_pas_collocation;
    private JButton quitter_pas_colloc;
    private Set<String> setVilles;
    private JLabel label_nom_2;
    private JTextField nom_valeur_2;
    private JLabel label_prenom_2;
    private JTextField prenom_valeur_2;
    private JLabel label_lieu_naissance_2;
    private JTextField lieu_naissance_valeur_2;
    private JLabel label_date_naissance_2;
    private JDateChooser choix_date_naissance_2;
    private JLabel label_colocataire_2;
    private  JLabel label_colocataire_1;
    private JButton bouton_enregistrer_colloc;
    private JButton bouton_quitter_colloc;
    private Map<String, List<String>> map_villes_adresses;
    private ModelePageNouveauLocataire modele;


    /**
     * Create the application.
     */
    public PageNouveauLocataire(int x,int y) {
        super(x,y);
        this.modele = new ModelePageNouveauLocataire(this);
        this.CreerSpecific();
    }


    @Override
    public void CreerSpecific(){
        try {
            map_villes_adresses = modele.getVilles();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.setVilles = this.map_villes_adresses.keySet();

        JPanel panel_titre = new JPanel();
        panel_body.add(panel_titre, BorderLayout.NORTH);

        JLabel label_titre = new JLabel("Nouveau locataire");
        label_titre.setFont(new Font("Tahoma", Font.BOLD, 15));
        panel_titre.add(label_titre);
        label_titre.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel donnees_loca = new JPanel();
        panel_body.add(donnees_loca);
        GridBagLayout gbl_donnees_loca = new GridBagLayout();
        gbl_donnees_loca.columnWidths = new int[]{40, 0, 0, 40, 0, 0, 0};
        gbl_donnees_loca.rowHeights = new int[]{40, 40, 40, 40, 40, 0};
        gbl_donnees_loca.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        gbl_donnees_loca.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        donnees_loca.setLayout(gbl_donnees_loca);

        label_colocataire_1 = new JLabel("Locataire Couple 1");
        label_colocataire_1.setFont(new Font("Tahoma", Font.BOLD, 14));
        GridBagConstraints gbc_label_coloc1 = new GridBagConstraints();
        gbc_label_coloc1.gridx = 3;
        gbc_label_coloc1.gridy = 0;
        donnees_loca.add(label_colocataire_1, gbc_label_coloc1);

        JLabel label_nom = new JLabel("Nom");
        GridBagConstraints gbc_labelNom = new GridBagConstraints();
        gbc_labelNom.anchor = GridBagConstraints.WEST;
        gbc_labelNom.insets = new Insets(0, 0, 5, 5);
        gbc_labelNom.gridx = 1;
        gbc_labelNom.gridy = 1;
        donnees_loca.add(label_nom, gbc_labelNom);

        nom_valeur = new JTextField();
        GridBagConstraints gbc_nomValeur = new GridBagConstraints();
        gbc_nomValeur.anchor = GridBagConstraints.WEST;
        gbc_nomValeur.insets = new Insets(0, 0, 5, 5);
        gbc_nomValeur.gridx = 2;
        gbc_nomValeur.gridy = 1;
        donnees_loca.add(nom_valeur, gbc_nomValeur);
        nom_valeur.setColumns(10);

        JLabel label_prenom = new JLabel("Prénom");
        GridBagConstraints gbc_labelPrenom = new GridBagConstraints();
        gbc_labelPrenom.anchor = GridBagConstraints.WEST;
        gbc_labelPrenom.insets = new Insets(0, 0, 5, 5);
        gbc_labelPrenom.gridx = 3;
        gbc_labelPrenom.gridy = 1;
        donnees_loca.add(label_prenom, gbc_labelPrenom);

        prenom_valeur = new JTextField();
        GridBagConstraints gbc_prenomValeur = new GridBagConstraints();
        gbc_prenomValeur.anchor = GridBagConstraints.WEST;
        gbc_prenomValeur.insets = new Insets(0, 0, 5, 5);
        gbc_prenomValeur.gridx = 4;
        gbc_prenomValeur.gridy = 1;
        donnees_loca.add(prenom_valeur, gbc_prenomValeur);
        prenom_valeur.setColumns(10);

        JLabel label_lieu_naissance = new JLabel("Lieu naissance");
        GridBagConstraints gbc_labelLieuNaissance = new GridBagConstraints();
        gbc_labelLieuNaissance.anchor = GridBagConstraints.WEST;
        gbc_labelLieuNaissance.insets = new Insets(0, 0, 5, 5);
        gbc_labelLieuNaissance.gridx = 1;
        gbc_labelLieuNaissance.gridy = 2;
        donnees_loca.add(label_lieu_naissance, gbc_labelLieuNaissance);

        lieu_naissance_valeur = new JTextField();
        GridBagConstraints gbc_lieuNaissanceValeur = new GridBagConstraints();
        gbc_lieuNaissanceValeur.anchor = GridBagConstraints.WEST;
        gbc_lieuNaissanceValeur.insets = new Insets(0, 0, 5, 5);
        gbc_lieuNaissanceValeur.gridx = 2;
        gbc_lieuNaissanceValeur.gridy = 2;
        donnees_loca.add(lieu_naissance_valeur, gbc_lieuNaissanceValeur);
        lieu_naissance_valeur.setColumns(10);

        JLabel label_date_naissance = new JLabel("Date naissance");
        GridBagConstraints gbc_labelDateNaissance = new GridBagConstraints();
        gbc_labelDateNaissance.anchor = GridBagConstraints.WEST;
        gbc_labelDateNaissance.insets = new Insets(0, 0, 5, 5);
        gbc_labelDateNaissance.gridx = 3;
        gbc_labelDateNaissance.gridy = 2;
        donnees_loca.add(label_date_naissance, gbc_labelDateNaissance);

        choix_date_naissance = new JDateChooser();
        choix_date_naissance.setPreferredSize(new Dimension(100, 22));
        GridBagConstraints gbc_dateNaissanceChooser = new GridBagConstraints();
        gbc_dateNaissanceChooser.anchor = GridBagConstraints.WEST;
        gbc_dateNaissanceChooser.insets = new Insets(0, 0, 5, 0);
        gbc_dateNaissanceChooser.gridx = 4;
        gbc_dateNaissanceChooser.gridy = 2;
        donnees_loca.add(choix_date_naissance, gbc_dateNaissanceChooser);

        JLabel label_telephone = new JLabel("Téléphone");
        GridBagConstraints gbc_labelTelephone = new GridBagConstraints();
        gbc_labelTelephone.anchor = GridBagConstraints.WEST;
        gbc_labelTelephone.insets = new Insets(0, 0, 5, 5);
        gbc_labelTelephone.gridx = 1;
        gbc_labelTelephone.gridy = 3;
        donnees_loca.add(label_telephone, gbc_labelTelephone);

        telephone_valeur = new JTextField();
        GridBagConstraints gbc_telephoneValeur = new GridBagConstraints();
        gbc_telephoneValeur.anchor = GridBagConstraints.WEST;
        gbc_telephoneValeur.insets = new Insets(0, 0, 5, 5);
        gbc_telephoneValeur.gridx = 2;
        gbc_telephoneValeur.gridy = 3;
        donnees_loca.add(telephone_valeur, gbc_telephoneValeur);
        telephone_valeur.setColumns(10);
        this.telephone_valeur.setDocument(new PlainDocument() {
            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if (str == null || getLength() + str.length() <= 10) {
                    super.insertString(offs, str, a);
                }
            }
        });

        JLabel label_mail = new JLabel("Mail");
        GridBagConstraints gbc_labelMail = new GridBagConstraints();
        gbc_labelMail.anchor = GridBagConstraints.WEST;
        gbc_labelMail.insets = new Insets(0, 0, 5, 5);
        gbc_labelMail.gridx = 3;
        gbc_labelMail.gridy = 3;
        donnees_loca.add(label_mail, gbc_labelMail);

        mail_valeur = new JTextField();
        GridBagConstraints gbc_mailValeur = new GridBagConstraints();
        gbc_mailValeur.anchor = GridBagConstraints.WEST;
        gbc_mailValeur.insets = new Insets(0, 0, 5, 5);
        gbc_mailValeur.gridx = 4;
        gbc_mailValeur.gridy = 3;
        donnees_loca.add(mail_valeur, gbc_mailValeur);
        mail_valeur.setColumns(10);

        JLabel label_date = new JLabel("Date arrivée");
        GridBagConstraints gbc_labelDate = new GridBagConstraints();
        gbc_labelDate.anchor = GridBagConstraints.WEST;
        gbc_labelDate.insets = new Insets(0, 0, 5, 5);
        gbc_labelDate.gridx = 3;
        gbc_labelDate.gridy = 4;
        donnees_loca.add(label_date, gbc_labelDate);

        choix_date = new JDateChooser();
        choix_date.setPreferredSize(new Dimension(100, 22));
        GridBagConstraints gbc_dateChooserDebut = new GridBagConstraints();
        gbc_dateChooserDebut.anchor = GridBagConstraints.WEST;
        gbc_dateChooserDebut.insets = new Insets(0, 0, 5, 0);
        gbc_dateChooserDebut.gridx = 4;
        gbc_dateChooserDebut.gridy = 4;
        donnees_loca.add(choix_date, gbc_dateChooserDebut);


        JLabel label_genre = new JLabel("Genre");
        GridBagConstraints gbc_labelGenre = new GridBagConstraints();
        gbc_labelGenre.anchor = GridBagConstraints.WEST;
        gbc_labelGenre.insets = new Insets(0, 0, 5, 5);
        gbc_labelGenre.gridx = 1;
        gbc_labelGenre.gridy = 4;
        donnees_loca.add(label_genre, gbc_labelGenre);

        this.genre_valeur = new JComboBox();
        this.genre_valeur.setModel(new DefaultComboBoxModel(new String[]{"H", "F", "C"}));
        GridBagConstraints gbc_genreValeur = new GridBagConstraints();
        gbc_genreValeur.fill = GridBagConstraints.HORIZONTAL;
        gbc_genreValeur.insets = new Insets(0, 0, 5, 5);
        gbc_genreValeur.gridx = 2;
        gbc_genreValeur.gridy = 4;
        donnees_loca.add(this.genre_valeur, gbc_genreValeur);


        this.enregistrer_pas_collocation = new JButton("Enregistrer");
        this.enregistrer_pas_collocation.setEnabled(false);
        GridBagConstraints gbc_enregistrerButtonPasColloc = new GridBagConstraints();
        gbc_enregistrerButtonPasColloc.gridx = 6;
        gbc_enregistrerButtonPasColloc.gridy = 5;
        gbc_enregistrerButtonPasColloc.insets = new Insets(0, 0, 5, 5);
        donnees_loca.add(enregistrer_pas_collocation, gbc_enregistrerButtonPasColloc);

        label_colocataire_2 = new JLabel("Locataire Couple 2");
        label_colocataire_2.setFont(new Font("Tahoma", Font.BOLD, 14));
        GridBagConstraints gbc_label_coloc2 = new GridBagConstraints();
        gbc_label_coloc2.gridx = 3;
        gbc_label_coloc2.gridy = 5;
        gbc_label_coloc2.insets = new Insets(0, 0, 5, 5);
        donnees_loca.add(label_colocataire_2, gbc_label_coloc2);

        quitter_pas_colloc = new JButton("Quitter");
        GridBagConstraints gbc_quitterPasColloc = new GridBagConstraints();
        gbc_quitterPasColloc.gridx = 1;
        gbc_quitterPasColloc.gridy = 5;
        gbc_quitterPasColloc.insets = new Insets(0, 0, 5, 5);
        donnees_loca.add(quitter_pas_colloc, gbc_quitterPasColloc);

        label_nom_2 = new JLabel("Nom ");
        GridBagConstraints gbc_labelNom2 = new GridBagConstraints();
        gbc_labelNom2.anchor = GridBagConstraints.WEST;
        gbc_labelNom2.insets = new Insets(0, 0, 5, 5);
        gbc_labelNom2.gridx = 1;
        gbc_labelNom2.gridy = 6;
        donnees_loca.add(label_nom_2, gbc_labelNom2);

        nom_valeur_2 = new JTextField();
        GridBagConstraints gbc_nomValeur2 = new GridBagConstraints();
        gbc_nomValeur2.anchor = GridBagConstraints.WEST;
        gbc_nomValeur2.insets = new Insets(0, 0, 5, 5);
        gbc_nomValeur2.gridx = 2;
        gbc_nomValeur2.gridy = 6;
        donnees_loca.add(nom_valeur_2, gbc_nomValeur2);
        nom_valeur_2.setColumns(10);

        label_prenom_2 = new JLabel("Prénom ");
        GridBagConstraints gbc_labelPrenom2 = new GridBagConstraints();
        gbc_labelPrenom2.anchor = GridBagConstraints.WEST;
        gbc_labelPrenom2.insets = new Insets(0, 0, 5, 5);
        gbc_labelPrenom2.gridx = 3;
        gbc_labelPrenom2.gridy = 6;
        donnees_loca.add(label_prenom_2, gbc_labelPrenom2);

        prenom_valeur_2 = new JTextField();
        GridBagConstraints gbc_prenomValeur2 = new GridBagConstraints();
        gbc_prenomValeur2.anchor = GridBagConstraints.WEST;
        gbc_prenomValeur2.insets = new Insets(0, 0, 5, 5);
        gbc_prenomValeur2.gridx = 4;
        gbc_prenomValeur2.gridy = 6;
        donnees_loca.add(prenom_valeur_2, gbc_prenomValeur2);
        prenom_valeur_2.setColumns(10);

        label_lieu_naissance_2 = new JLabel("Lieu naissance");
        GridBagConstraints gbc_labelLieuNaissance2 = new GridBagConstraints();
        gbc_labelLieuNaissance2.anchor = GridBagConstraints.WEST;
        gbc_labelLieuNaissance2.insets = new Insets(0, 0, 5, 5);
        gbc_labelLieuNaissance2.gridx = 1;
        gbc_labelLieuNaissance2.gridy = 7;
        donnees_loca.add(label_lieu_naissance_2, gbc_labelLieuNaissance2);

        lieu_naissance_valeur_2 = new JTextField();
        GridBagConstraints gbc_lieuNaissanceValeur2 = new GridBagConstraints();
        gbc_lieuNaissanceValeur2.anchor = GridBagConstraints.WEST;
        gbc_lieuNaissanceValeur2.insets = new Insets(0, 0, 5, 5);
        gbc_lieuNaissanceValeur2.gridx = 2;
        gbc_lieuNaissanceValeur2.gridy = 7;
        donnees_loca.add(lieu_naissance_valeur_2, gbc_lieuNaissanceValeur2);
        lieu_naissance_valeur_2.setColumns(10);

        label_date_naissance_2 = new JLabel("Date naissance");
        GridBagConstraints gbc_labelDateNaissance2 = new GridBagConstraints();
        gbc_labelDateNaissance2.anchor = GridBagConstraints.WEST;
        gbc_labelDateNaissance2.insets = new Insets(0, 0, 5, 5);
        gbc_labelDateNaissance2.gridx = 3;
        gbc_labelDateNaissance2.gridy = 7;
        donnees_loca.add(label_date_naissance_2, gbc_labelDateNaissance2);

        choix_date_naissance_2 = new JDateChooser();
        choix_date_naissance_2.setPreferredSize(new Dimension(100, 22));
        GridBagConstraints gbc_dateNaissanceChooser2 = new GridBagConstraints();
        gbc_dateNaissanceChooser2.anchor = GridBagConstraints.WEST;
        gbc_dateNaissanceChooser2.insets = new Insets(0, 0, 5, 0);
        gbc_dateNaissanceChooser2.gridx = 4;
        gbc_dateNaissanceChooser2.gridy = 7;
        donnees_loca.add(choix_date_naissance_2, gbc_dateNaissanceChooser2);

        this.bouton_enregistrer_colloc = new JButton("Enregistrer");
        this.bouton_enregistrer_colloc.setEnabled(false);
        GridBagConstraints gbc_enregistrerButtonColloc = new GridBagConstraints();
        gbc_enregistrerButtonColloc.gridx = 6;
        gbc_enregistrerButtonColloc.gridy = 9;
        gbc_enregistrerButtonColloc.insets = new Insets(0, 0, 5, 5);
        donnees_loca.add(bouton_enregistrer_colloc, gbc_enregistrerButtonColloc);

        bouton_quitter_colloc = new JButton("Quitter");
        GridBagConstraints gbc_quitterColloc = new GridBagConstraints();
        gbc_quitterColloc.gridx = 1;
        gbc_quitterColloc.gridy = 9;
        gbc_quitterColloc.insets = new Insets(0, 0, 5, 5);
        donnees_loca.add(bouton_quitter_colloc, gbc_quitterColloc);

        modele.getChoixGenreValeurListener().actionPerformed(null);
        enregistrer_pas_collocation.addActionListener(modele.getAjouterLocataireListener());
        bouton_enregistrer_colloc.addActionListener(modele.getAjouterLocataireListener());
        nom_valeur.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
        prenom_valeur.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
        lieu_naissance_valeur.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
        choix_date_naissance.getDateEditor().addPropertyChangeListener("date", evt -> modele.getTextFieldDocumentListener().insertUpdate(null));
        telephone_valeur.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
        nom_valeur_2.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
        prenom_valeur_2.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
        lieu_naissance_valeur_2.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
        choix_date_naissance_2.getDateEditor().addPropertyChangeListener("date", evt -> modele.getTextFieldDocumentListener().insertUpdate(null));
        choix_date.getDateEditor().addPropertyChangeListener("date", evt -> modele.getTextFieldDocumentListener().insertUpdate(null));
        quitter_pas_colloc.addActionListener(modele.quitterBouton());
        bouton_quitter_colloc.addActionListener(modele.quitterBouton());
        this.genre_valeur.addActionListener(modele.getChoixGenreValeurListener());
    }

    public JTextField getNomValeur() {
        return nom_valeur;
    }

    public JTextField getPrenomValeur() {
        return prenom_valeur;
    }

    public JTextField getLieuNaissanceValeur(){ return lieu_naissance_valeur;}

    public JDateChooser getDateNaissanceChooser(){ return choix_date_naissance;}

    public JTextField getTelephoneValeur() {
        return telephone_valeur;
    }

    public JTextField getMailValeur() {
        return mail_valeur;
    }

    public JComboBox getGenreValeur() {
        return genre_valeur;
    }

    public JDateChooser getDateChooser() {return choix_date;}

    public JButton getEnregistrerButtonPasColloc(){return enregistrer_pas_collocation;}

    public JButton getQuitterButtonPasColloc(){return quitter_pas_colloc;}

    public JLabel getLabelNom2() {
        return label_nom_2;
    }

    public JTextField getNomValeur2() {
        return nom_valeur_2;
    }

    public JLabel getLabelPrenom2() {
        return label_prenom_2;
    }

    public JTextField getPrenomValeur2() {
        return prenom_valeur_2;
    }

    public JLabel getLabelLieuNaissance2(){ return label_lieu_naissance_2;}

    public JTextField getLieuNaissanceValeur2(){ return lieu_naissance_valeur_2;}

    public JLabel getLabelDateNaissance2(){ return label_date_naissance_2;}

    public JDateChooser getDateNaissanceChooser2(){ return choix_date_naissance_2;}

    public JLabel getLabelColoc2() {
        return label_colocataire_2;
    }

    public JLabel getLabelColoc1() {
        return label_colocataire_1;
    }

    public JButton getEnregistrerButtonColloc() {
        return bouton_enregistrer_colloc;
    }

    public JButton getQuitterColloc() {
        return bouton_quitter_colloc;
    }

    public void checkFieldsPasColloc() {
        // Vérification si tous les champs sont remplis
        boolean isFilled = !nom_valeur.getText().trim().isEmpty() && !prenom_valeur.getText().trim().isEmpty()
                && !telephone_valeur.getText().trim().isEmpty() && choix_date.getDate() != null;

        // Active ou désactive le bouton "Valider"
        enregistrer_pas_collocation.setEnabled(isFilled);
    }

    public void checkFieldsColloc(){
        boolean isFilled = !nom_valeur.getText().trim().isEmpty() && !prenom_valeur.getText().trim().isEmpty()
                && !telephone_valeur.getText().trim().isEmpty() && choix_date.getDate() != null
                && !nom_valeur_2.getText().trim().isEmpty() && !prenom_valeur_2.getText().trim().isEmpty();
        bouton_enregistrer_colloc.setEnabled(isFilled);
    }

}