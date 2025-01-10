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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

import DAO.DAOException;
import DAO.db.ConnectionDB;
import DAO.jdbc.LocataireDAO;
import classes.Locataire;
import modele.Charte;
import modele.ModelePageNouveauLocataire;
import modele.ResizedImage;

public class PageNouveauLocataire {

    private JFrame frame;
    private JLabel logo;
    private JTextField nomValeur;
    private JTextField prenomValeur;
    private JTextField lieuNaissanceValeur;
    private JDateChooser dateNaissanceChooser;
    private JTextField telephoneValeur;
    private JTextField mailValeur;
    private JDateChooser dateChooser;
    private JComboBox villeValeur;
    private JComboBox genreValeur;
    private JComboBox adresseValeur;
    private JButton enregistrerButtonPasColloc;
    private JButton quitterPasColloc;
    private Map<String, List<String>> mapVillesAdresses;
    private LocataireDAO daoLoc;
    private Set<String> setVilles;

    private JLabel labelNom2;
    private JTextField nomValeur2;
    private JLabel labelPrenom2;
    private JTextField prenomValeur2;
    private JLabel labelLieuNaissance2;
    private JTextField lieuNaissanceValeur2;
    private JLabel labelDateNaissance2;
    private JDateChooser dateNaissanceChooser2;
    private JLabel label_coloc2;
    private  JLabel label_coloc1;
    private JButton enregistrerButtonColloc;
    private JButton quitterColloc;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    PageNouveauLocataire window = new PageNouveauLocataire();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public JFrame getFrame() {
        return frame;
    }

    /**
     * Create the application.
     */
    public PageNouveauLocataire() {
        this.initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        ModelePageNouveauLocataire modele = new ModelePageNouveauLocataire(this);
        try {
            DAO.jdbc.BatimentDAO tousBat = new DAO.jdbc.BatimentDAO();
            mapVillesAdresses = tousBat.searchAllBatiments();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.setVilles = this.mapVillesAdresses.keySet();
        this.frame = new JFrame();
        this.frame.setBounds(100, 100, 750, 500);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel entete = new JPanel();
        this.frame.getContentPane().add(entete, BorderLayout.NORTH);
        entete.setLayout(new BorderLayout(0, 0));
        this.frame.getContentPane().setBackground(Charte.FOND.getCouleur());

        entete.setBackground(Charte.ENTETE.getCouleur());
        entete.setBorder(new LineBorder(Color.BLACK, 2));

        this.logo = new JLabel("");
        entete.add(this.logo, BorderLayout.WEST);

        Menu m = new Menu(this.frame);

        JPanel menu_bouttons = new JPanel();

        entete.add(menu_bouttons, BorderLayout.CENTER);
        menu_bouttons.setLayout(new GridLayout(0, 4, 0, 0));
        menu_bouttons.setBackground(Charte.ENTETE.getCouleur());

        JButton b_accueil = new JButton("Accueil");
        b_accueil.setBorderPainted(false);
        b_accueil.setBackground(Charte.ENTETE.getCouleur());
        b_accueil.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menu_bouttons.add(b_accueil);
        b_accueil.addActionListener(m);

        JButton b_baux = new JButton("Mes baux");
        b_baux.setBorderPainted(false);
        b_baux.setBackground(Charte.ENTETE.getCouleur());
        b_baux.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menu_bouttons.add(b_baux);
        menu_bouttons.add(b_baux);
        b_baux.addActionListener(m);

        JButton b_biens = new JButton("Mes Biens");
        b_biens.setBorderPainted(false);
        b_biens.setBackground(Charte.ENTETE.getCouleur());
        b_biens.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menu_bouttons.add(b_biens);
        menu_bouttons.add(b_biens);
        b_biens.addActionListener(m);

        JButton b_notifs = null;
        try {
            b_notifs = new JButton("Notifications ("+m.getNbNotifs()+")");
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        b_notifs.setBorderPainted(false);
        b_notifs.setBackground(Charte.ENTETE.getCouleur());
        b_notifs.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menu_bouttons.add(b_notifs);
        menu_bouttons.add(b_notifs);
        b_notifs.addActionListener(m);

        JPanel body = new JPanel();
        frame.getContentPane().add(body, BorderLayout.CENTER);
        body.setLayout(new BorderLayout(0, 0));

        JPanel panelTitre = new JPanel();
        body.add(panelTitre, BorderLayout.NORTH);

        JLabel labelTitre = new JLabel("Nouveau locataire");
        labelTitre.setFont(new Font("Tahoma", Font.BOLD, 15));
        panelTitre.add(labelTitre);
        labelTitre.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel donnees_loca = new JPanel();
        body.add(donnees_loca);
        GridBagLayout gbl_donnees_loca = new GridBagLayout();
        gbl_donnees_loca.columnWidths = new int[] { 40, 0, 0, 40, 0, 0, 0 };
        gbl_donnees_loca.rowHeights = new int[] { 40, 40, 40, 40, 40, 0 };
        gbl_donnees_loca.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
        gbl_donnees_loca.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
        donnees_loca.setLayout(gbl_donnees_loca);

        label_coloc1 = new JLabel("Locataire Couple 1");
        label_coloc1.setFont(new Font("Tahoma", Font.BOLD, 14));
        GridBagConstraints gbc_label_coloc1 = new GridBagConstraints();
        gbc_label_coloc1.gridx = 3;
        gbc_label_coloc1.gridy = 0;
        donnees_loca.add(label_coloc1, gbc_label_coloc1);

        JLabel labelNom = new JLabel("Nom");
        GridBagConstraints gbc_labelNom = new GridBagConstraints();
        gbc_labelNom.anchor = GridBagConstraints.WEST;
        gbc_labelNom.insets = new Insets(0, 0, 5, 5);
        gbc_labelNom.gridx = 1;
        gbc_labelNom.gridy = 1;
        donnees_loca.add(labelNom, gbc_labelNom);

        nomValeur = new JTextField();
        GridBagConstraints gbc_nomValeur = new GridBagConstraints();
        gbc_nomValeur.anchor = GridBagConstraints.WEST;
        gbc_nomValeur.insets = new Insets(0, 0, 5, 5);
        gbc_nomValeur.gridx = 2;
        gbc_nomValeur.gridy = 1;
        donnees_loca.add(nomValeur, gbc_nomValeur);
        nomValeur.setColumns(10);

        JLabel labelPrenom = new JLabel("Prénom");
        GridBagConstraints gbc_labelPrenom = new GridBagConstraints();
        gbc_labelPrenom.anchor = GridBagConstraints.WEST;
        gbc_labelPrenom.insets = new Insets(0, 0, 5, 5);
        gbc_labelPrenom.gridx = 3;
        gbc_labelPrenom.gridy = 1;
        donnees_loca.add(labelPrenom, gbc_labelPrenom);

        prenomValeur = new JTextField();
        GridBagConstraints gbc_prenomValeur = new GridBagConstraints();
        gbc_prenomValeur.anchor = GridBagConstraints.WEST;
        gbc_prenomValeur.insets = new Insets(0, 0, 5, 5);
        gbc_prenomValeur.gridx = 4;
        gbc_prenomValeur.gridy = 1;
        donnees_loca.add(prenomValeur, gbc_prenomValeur);
        prenomValeur.setColumns(10);

        JLabel labelLieuNaissance = new JLabel("Lieu naissance");
        GridBagConstraints gbc_labelLieuNaissance = new GridBagConstraints();
        gbc_labelLieuNaissance.anchor = GridBagConstraints.WEST;
        gbc_labelLieuNaissance.insets = new Insets(0, 0, 5, 5);
        gbc_labelLieuNaissance.gridx = 1;
        gbc_labelLieuNaissance.gridy = 2;
        donnees_loca.add(labelLieuNaissance, gbc_labelLieuNaissance);

        lieuNaissanceValeur = new JTextField();
        GridBagConstraints gbc_lieuNaissanceValeur = new GridBagConstraints();
        gbc_lieuNaissanceValeur.anchor = GridBagConstraints.WEST;
        gbc_lieuNaissanceValeur.insets = new Insets(0, 0, 5, 5);
        gbc_lieuNaissanceValeur.gridx = 2;
        gbc_lieuNaissanceValeur.gridy = 2;
        donnees_loca.add(lieuNaissanceValeur, gbc_lieuNaissanceValeur);
        lieuNaissanceValeur.setColumns(10);

        JLabel labelDateNaissance = new JLabel("Date naissance");
        GridBagConstraints gbc_labelDateNaissance = new GridBagConstraints();
        gbc_labelDateNaissance.anchor = GridBagConstraints.WEST;
        gbc_labelDateNaissance.insets = new Insets(0, 0, 5, 5);
        gbc_labelDateNaissance.gridx = 3;
        gbc_labelDateNaissance.gridy = 2;
        donnees_loca.add(labelDateNaissance, gbc_labelDateNaissance);

        dateNaissanceChooser = new JDateChooser();
        dateNaissanceChooser.setPreferredSize(new Dimension(100, 22));
        GridBagConstraints gbc_dateNaissanceChooser = new GridBagConstraints();
        gbc_dateNaissanceChooser.anchor = GridBagConstraints.WEST;
        gbc_dateNaissanceChooser.insets = new Insets(0, 0, 5, 0);
        gbc_dateNaissanceChooser.gridx = 4;
        gbc_dateNaissanceChooser.gridy = 2;
        donnees_loca.add(dateNaissanceChooser, gbc_dateNaissanceChooser);

        JLabel labelTelephone = new JLabel("Téléphone");
        GridBagConstraints gbc_labelTelephone = new GridBagConstraints();
        gbc_labelTelephone.anchor = GridBagConstraints.WEST;
        gbc_labelTelephone.insets = new Insets(0, 0, 5, 5);
        gbc_labelTelephone.gridx = 1;
        gbc_labelTelephone.gridy = 3;
        donnees_loca.add(labelTelephone, gbc_labelTelephone);

        telephoneValeur = new JTextField();
        GridBagConstraints gbc_telephoneValeur = new GridBagConstraints();
        gbc_telephoneValeur.anchor = GridBagConstraints.WEST;
        gbc_telephoneValeur.insets = new Insets(0, 0, 5, 5);
        gbc_telephoneValeur.gridx = 2;
        gbc_telephoneValeur.gridy = 3;
        donnees_loca.add(telephoneValeur, gbc_telephoneValeur);
        telephoneValeur.setColumns(10);
        this.telephoneValeur.setDocument(new PlainDocument() {
            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if (str == null || getLength() + str.length() <= 10) {
                    super.insertString(offs, str, a);
                }
            }
        });

        JLabel labelMail = new JLabel("Mail");
        GridBagConstraints gbc_labelMail = new GridBagConstraints();
        gbc_labelMail.anchor = GridBagConstraints.WEST;
        gbc_labelMail.insets = new Insets(0, 0, 5, 5);
        gbc_labelMail.gridx = 3;
        gbc_labelMail.gridy = 3;
        donnees_loca.add(labelMail, gbc_labelMail);

        mailValeur = new JTextField();
        GridBagConstraints gbc_mailValeur = new GridBagConstraints();
        gbc_mailValeur.anchor = GridBagConstraints.WEST;
        gbc_mailValeur.insets = new Insets(0, 0, 5, 5);
        gbc_mailValeur.gridx = 4;
        gbc_mailValeur.gridy = 3;
        donnees_loca.add(mailValeur, gbc_mailValeur);
        mailValeur.setColumns(10);

        JLabel labelDate = new JLabel("Date arrivée");
        GridBagConstraints gbc_labelDate = new GridBagConstraints();
        gbc_labelDate.anchor = GridBagConstraints.WEST;
        gbc_labelDate.insets = new Insets(0, 0, 5, 5);
        gbc_labelDate.gridx = 3;
        gbc_labelDate.gridy = 4;
        donnees_loca.add(labelDate, gbc_labelDate);

        dateChooser = new JDateChooser();
        dateChooser.setPreferredSize(new Dimension(100, 22));
        GridBagConstraints gbc_dateChooserDebut = new GridBagConstraints();
        gbc_dateChooserDebut.anchor = GridBagConstraints.WEST;
        gbc_dateChooserDebut.insets = new Insets(0, 0, 5, 0);
        gbc_dateChooserDebut.gridx = 4;
        gbc_dateChooserDebut.gridy = 4;
        donnees_loca.add(dateChooser, gbc_dateChooserDebut);


        JLabel labelGenre = new JLabel("Genre");
        GridBagConstraints gbc_labelGenre = new GridBagConstraints();
        gbc_labelGenre.anchor = GridBagConstraints.WEST;
        gbc_labelGenre.insets = new Insets(0, 0, 5, 5);
        gbc_labelGenre.gridx = 1;
        gbc_labelGenre.gridy = 4;
        donnees_loca.add(labelGenre, gbc_labelGenre);

        this.genreValeur = new JComboBox();
        this.genreValeur.setModel(new DefaultComboBoxModel(new String[] { "H", "F", "C" }));
        GridBagConstraints gbc_genreValeur = new GridBagConstraints();
        gbc_genreValeur.fill = GridBagConstraints.HORIZONTAL;
        gbc_genreValeur.insets = new Insets(0, 0, 5, 5);
        gbc_genreValeur.gridx = 2;
        gbc_genreValeur.gridy = 4;
        donnees_loca.add(this.genreValeur, gbc_genreValeur);


        this.enregistrerButtonPasColloc = new JButton("Enregistrer");
        this.enregistrerButtonPasColloc.setEnabled(false);
        GridBagConstraints gbc_enregistrerButtonPasColloc = new GridBagConstraints();
        gbc_enregistrerButtonPasColloc.gridx = 6;
        gbc_enregistrerButtonPasColloc.gridy = 5;
        gbc_enregistrerButtonPasColloc.insets = new Insets(0, 0, 5, 5);
        donnees_loca.add(enregistrerButtonPasColloc, gbc_enregistrerButtonPasColloc);

        label_coloc2 = new JLabel("Locataire Couple 2");
        label_coloc2.setFont(new Font("Tahoma", Font.BOLD, 14));
        GridBagConstraints gbc_label_coloc2 = new GridBagConstraints();
        gbc_label_coloc2.gridx = 3;
        gbc_label_coloc2.gridy = 5;
        gbc_label_coloc2.insets = new Insets(0, 0, 5, 5);
        donnees_loca.add(label_coloc2, gbc_label_coloc2);

        quitterPasColloc = new JButton("Quitter");
        GridBagConstraints gbc_quitterPasColloc = new GridBagConstraints();
        gbc_quitterPasColloc.gridx = 1;
        gbc_quitterPasColloc.gridy = 5;
        gbc_quitterPasColloc.insets = new Insets(0, 0, 5, 5);
        donnees_loca.add(quitterPasColloc, gbc_quitterPasColloc);

        labelNom2 = new JLabel("Nom ");
        GridBagConstraints gbc_labelNom2 = new GridBagConstraints();
        gbc_labelNom2.anchor = GridBagConstraints.WEST;
        gbc_labelNom2.insets = new Insets(0, 0, 5, 5);
        gbc_labelNom2.gridx = 1;
        gbc_labelNom2.gridy = 6;
        donnees_loca.add(labelNom2, gbc_labelNom2);

        nomValeur2 = new JTextField();
        GridBagConstraints gbc_nomValeur2 = new GridBagConstraints();
        gbc_nomValeur2.anchor = GridBagConstraints.WEST;
        gbc_nomValeur2.insets = new Insets(0, 0, 5, 5);
        gbc_nomValeur2.gridx = 2;
        gbc_nomValeur2.gridy = 6;
        donnees_loca.add(nomValeur2, gbc_nomValeur2);
        nomValeur2.setColumns(10);

        labelPrenom2 = new JLabel("Prénom ");
        GridBagConstraints gbc_labelPrenom2 = new GridBagConstraints();
        gbc_labelPrenom2.anchor = GridBagConstraints.WEST;
        gbc_labelPrenom2.insets = new Insets(0, 0, 5, 5);
        gbc_labelPrenom2.gridx = 3;
        gbc_labelPrenom2.gridy = 6;
        donnees_loca.add(labelPrenom2, gbc_labelPrenom2);

        prenomValeur2 = new JTextField();
        GridBagConstraints gbc_prenomValeur2 = new GridBagConstraints();
        gbc_prenomValeur2.anchor = GridBagConstraints.WEST;
        gbc_prenomValeur2.insets = new Insets(0, 0, 5, 5);
        gbc_prenomValeur2.gridx = 4;
        gbc_prenomValeur2.gridy = 6;
        donnees_loca.add(prenomValeur2, gbc_prenomValeur2);
        prenomValeur2.setColumns(10);

        labelLieuNaissance2 = new JLabel("Lieu naissance");
        GridBagConstraints gbc_labelLieuNaissance2 = new GridBagConstraints();
        gbc_labelLieuNaissance2.anchor = GridBagConstraints.WEST;
        gbc_labelLieuNaissance2.insets = new Insets(0, 0, 5, 5);
        gbc_labelLieuNaissance2.gridx = 1;
        gbc_labelLieuNaissance2.gridy = 7;
        donnees_loca.add(labelLieuNaissance2, gbc_labelLieuNaissance2);

        lieuNaissanceValeur2 = new JTextField();
        GridBagConstraints gbc_lieuNaissanceValeur2 = new GridBagConstraints();
        gbc_lieuNaissanceValeur2.anchor = GridBagConstraints.WEST;
        gbc_lieuNaissanceValeur2.insets = new Insets(0, 0, 5, 5);
        gbc_lieuNaissanceValeur2.gridx = 2;
        gbc_lieuNaissanceValeur2.gridy = 7;
        donnees_loca.add(lieuNaissanceValeur2, gbc_lieuNaissanceValeur2);
        lieuNaissanceValeur2.setColumns(10);

        labelDateNaissance2 = new JLabel("Date naissance");
        GridBagConstraints gbc_labelDateNaissance2 = new GridBagConstraints();
        gbc_labelDateNaissance2.anchor = GridBagConstraints.WEST;
        gbc_labelDateNaissance2.insets = new Insets(0, 0, 5, 5);
        gbc_labelDateNaissance2.gridx = 3;
        gbc_labelDateNaissance2.gridy = 7;
        donnees_loca.add(labelDateNaissance2, gbc_labelDateNaissance2);

        dateNaissanceChooser2 = new JDateChooser();
        dateNaissanceChooser2.setPreferredSize(new Dimension(100, 22));
        GridBagConstraints gbc_dateNaissanceChooser2 = new GridBagConstraints();
        gbc_dateNaissanceChooser2.anchor = GridBagConstraints.WEST;
        gbc_dateNaissanceChooser2.insets = new Insets(0, 0, 5, 0);
        gbc_dateNaissanceChooser2.gridx = 4;
        gbc_dateNaissanceChooser2.gridy = 7;
        donnees_loca.add(dateNaissanceChooser2, gbc_dateNaissanceChooser2);

        this.enregistrerButtonColloc = new JButton("Enregistrer");
        this.enregistrerButtonColloc.setEnabled(false);
        GridBagConstraints gbc_enregistrerButtonColloc = new GridBagConstraints();
        gbc_enregistrerButtonColloc.gridx = 6;
        gbc_enregistrerButtonColloc.gridy = 9;
        gbc_enregistrerButtonColloc.insets = new Insets(0, 0, 5, 5);
        donnees_loca.add(enregistrerButtonColloc, gbc_enregistrerButtonColloc);

        quitterColloc = new JButton("Quitter");
        GridBagConstraints gbc_quitterColloc = new GridBagConstraints();
        gbc_quitterColloc.gridx = 1;
        gbc_quitterColloc.gridy = 9;
        gbc_quitterColloc.insets = new Insets(0, 0, 5, 5);
        donnees_loca.add(quitterColloc, gbc_quitterColloc);

        enregistrerButtonPasColloc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                java.sql.Date sqlDate = new java.sql.Date(getDateChooser().getDate().getTime());
                java.sql.Date dateNaissance = new java.sql.Date(getDateNaissanceChooser().getDate().getTime());
                daoLoc = new LocataireDAO();
                Locataire l = new Locataire(nomValeur.getText(), prenomValeur.getText(), lieuNaissanceValeur.getText(), dateNaissance.toString(),
                        telephoneValeur.getText(),mailValeur.getText(), sqlDate, (String) genreValeur.getSelectedItem());

            }
        });

        this.frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                ResizedImage res = new ResizedImage();
                res.resizeImage("logo+nom.png", PageNouveauLocataire.this.frame,
                        PageNouveauLocataire.this.logo, 3, 8);
                int frameWidth = PageNouveauLocataire.this.frame.getWidth();
                int frameHeight = PageNouveauLocataire.this.frame.getHeight();

                int newFontSize = Math.min(frameWidth, frameHeight) / 30;

                // Appliquer la nouvelle police au bouton
                Font resizedFont = new Font("Arial", Font.PLAIN, newFontSize);
                b_baux.setFont(resizedFont);
                b_accueil.setFont(resizedFont);
                b_biens.setFont(resizedFont);
            }
        });

        modele.getChoixGenreValeurListener().actionPerformed(null);
        enregistrerButtonPasColloc.addActionListener(modele.getAjouterLocataireListener());
        enregistrerButtonColloc.addActionListener(modele.getAjouterLocataireListener());
        nomValeur.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
        prenomValeur.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
        lieuNaissanceValeur.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
        dateNaissanceChooser.getDateEditor().addPropertyChangeListener("date", evt -> modele.getTextFieldDocumentListener().insertUpdate(null));
        telephoneValeur.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
        nomValeur2.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
        prenomValeur2.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
        lieuNaissanceValeur2.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
        dateNaissanceChooser2.getDateEditor().addPropertyChangeListener("date", evt -> modele.getTextFieldDocumentListener().insertUpdate(null));
        dateChooser.getDateEditor().addPropertyChangeListener("date", evt -> modele.getTextFieldDocumentListener().insertUpdate(null));
        quitterPasColloc.addActionListener(modele.quitterBouton());
        quitterColloc.addActionListener(modele.quitterBouton());
        this.genreValeur.addActionListener(modele.getChoixGenreValeurListener());

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Action to perform on application close
                performCloseAction();
            }
        });
    }

    private void performCloseAction() {
        ConnectionDB.destroy(); // fermeture de la connection
        frame.dispose();
    }

    public JTextField getNomValeur() {
        return nomValeur;
    }

    public JTextField getPrenomValeur() {
        return prenomValeur;
    }

    public JTextField getLieuNaissanceValeur(){ return lieuNaissanceValeur;}

    public JDateChooser getDateNaissanceChooser(){ return dateNaissanceChooser;}

    public JTextField getTelephoneValeur() {
        return telephoneValeur;
    }

    public JTextField getMailValeur() {
        return mailValeur;
    }

    public JComboBox getGenreValeur() {
        return genreValeur;
    }

    public JComboBox getAdresseValeur() {
        return adresseValeur;
    }

    public JComboBox getVilleValeur() {
        return villeValeur;
    }

    public JDateChooser getDateChooser() {return dateChooser;}

    public JButton getEnregistrerButtonPasColloc(){return enregistrerButtonPasColloc;}

    public JButton getQuitterButtonPasColloc(){return quitterPasColloc;}

    public JLabel getLabelNom2() {
        return labelNom2;
    }

    public JTextField getNomValeur2() {
        return nomValeur2;
    }

    public JLabel getLabelPrenom2() {
        return labelPrenom2;
    }

    public JTextField getPrenomValeur2() {
        return prenomValeur2;
    }

    public JLabel getLabelLieuNaissance2(){ return labelLieuNaissance2;}

    public JTextField getLieuNaissanceValeur2(){ return lieuNaissanceValeur2;}

    public JLabel getLabelDateNaissance2(){ return labelDateNaissance2;}

    public JDateChooser getDateNaissanceChooser2(){ return dateNaissanceChooser2;}

    public JLabel getLabelColoc2() {
        return label_coloc2;
    }

    public JLabel getLabelColoc1() {
        return label_coloc1;
    }

    public JButton getEnregistrerButtonColloc() {
        return enregistrerButtonColloc;
    }

    public JButton getQuitterColloc() {
        return quitterColloc;
    }

    public void checkFieldsPasColloc() {
        // Vérification si tous les champs sont remplis
        boolean isFilled = !nomValeur.getText().trim().isEmpty() && !prenomValeur.getText().trim().isEmpty()
                && !telephoneValeur.getText().trim().isEmpty() && dateChooser.getDate() != null;

        // Active ou désactive le bouton "Valider"
        enregistrerButtonPasColloc.setEnabled(isFilled);
    }

    public void checkFieldsColloc(){
        boolean isFilled = !nomValeur.getText().trim().isEmpty() && !prenomValeur.getText().trim().isEmpty()
                && !telephoneValeur.getText().trim().isEmpty() && dateChooser.getDate() != null
                && !nomValeur2.getText().trim().isEmpty() && !prenomValeur2.getText().trim().isEmpty();
        enregistrerButtonColloc.setEnabled(isFilled);
    }
}