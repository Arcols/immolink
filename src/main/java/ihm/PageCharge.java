package ihm;

import DAO.DAOException;
import DAO.jdbc.ChargeDAO;
import modele.*;
import modele.Menu;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.Date;
import java.time.LocalDate;

public class PageCharge {

    private JFrame frame;
    private JLabel logo;
    private int id_bail;
    private JPanel contenu;

    public PageCharge(Integer idBail) {
        this.id_bail = idBail;
        this.initialize(idBail);
    }

    private void initialize(Integer idBail) {
        this.frame = new JFrame();
        ModelePageCharge modele = new ModelePageCharge(this);
        this.frame.setBounds(100, 100, 750, 400);
        this.frame.getContentPane().setBackground(Charte.FOND.getCouleur());
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel d'entête pour le logo et le nom de l'appli
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
        this.frame.getContentPane().add(body, BorderLayout.CENTER);
        body.setLayout(new BorderLayout(0, 0));

        JPanel titre = new JPanel();
        FlowLayout fl_titre = (FlowLayout) titre.getLayout();
        body.add(titre, BorderLayout.NORTH);

        JLabel titleLabel = new JLabel("Liste des charges", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        body.add(titleLabel, BorderLayout.NORTH);

        contenu = new JPanel();
        body.add(contenu, BorderLayout.CENTER);
        contenu.setLayout(new BorderLayout(0, 0));

        int annee_actuelle=LocalDate.now().getYear();

        createInfoCharge(annee_actuelle,BorderLayout.CENTER);
        createInfoCharge(annee_actuelle-1,BorderLayout.SOUTH);

        JPanel panelbouton = new JPanel();
        body.add(panelbouton, BorderLayout.SOUTH);

        JButton archivage = new JButton("Données archivées");
        panelbouton.add(archivage);
        archivage.addActionListener(modele.Archivage(idBail));
        JButton facture = new JButton("Ajouter une facture");
        panelbouton.add(facture);
        facture.addActionListener(modele.ouvrirPageFacture());

        JPanel bas_de_page = new JPanel();
        this.frame.getContentPane().add(bas_de_page, BorderLayout.SOUTH);
        bas_de_page.setLayout(new BorderLayout(0, 0));

        JButton regularisation = new JButton("Régularisation des charges");
        regularisation.setHorizontalTextPosition(SwingConstants.LEFT);
        regularisation.setVerticalTextPosition(SwingConstants.TOP);
        regularisation.setVerticalAlignment(SwingConstants.BOTTOM);
        bas_de_page.add(regularisation, BorderLayout.EAST);
        regularisation.addActionListener(modele.Regularisation());

        JButton quitter = new JButton("Quitter");
        quitter.setHorizontalTextPosition(SwingConstants.LEFT);
        quitter.setVerticalTextPosition(SwingConstants.TOP);
        quitter.setVerticalAlignment(SwingConstants.BOTTOM);
        bas_de_page.add(quitter, BorderLayout.WEST);
        quitter.addActionListener(modele.quitterPage());
        this.frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                ResizedImage res = new ResizedImage();
                res.resizeImage("logo+nom.png", PageCharge.this.frame,
                        PageCharge.this.logo, 3, 8);
                int frameWidth = PageCharge.this.frame.getWidth();
                int frameHeight = PageCharge.this.frame.getHeight();

                int newFontSize = Math.min(frameWidth, frameHeight) / 30;

                // Appliquer la nouvelle police au bouton
                Font resizedFont = new Font("Arial", Font.PLAIN, newFontSize);
                b_baux.setFont(resizedFont);
                b_accueil.setFont(resizedFont);
                b_biens.setFont(resizedFont);
            }
        });
        this.frame.setVisible(true);
    }

    public Frame getFrame() {
        return this.frame;
    }

    public int getId_bail() {
        return id_bail;
    }

    public void createInfoCharge(int annee, String border){
        JPanel champ = new JPanel();
        champ.setLayout(new BorderLayout(0,0));
        contenu.add(champ, border);

        JLabel titrecontent = new JLabel("Charges de l'année "+annee);
        titrecontent.setHorizontalAlignment(SwingConstants.CENTER);
        champ.add(titrecontent,BorderLayout.CENTER);

        JPanel donnee = new JPanel();
        champ.add(donnee,BorderLayout.SOUTH);
        GridBagLayout gbl_donnee = new GridBagLayout();
        gbl_donnee.columnWidths = new int[] {30, 30, 30, 30};
        gbl_donnee.rowHeights = new int[] {0, 30, 0, 30, 0, 30};
        gbl_donnee.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0};
        gbl_donnee.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        donnee.setLayout(gbl_donnee);

        ChargeDAO charge = new DAO.jdbc.ChargeDAO();
        Date date_actuelle = Date.valueOf(annee + "-01-01");
        double prix;
        try {
            prix=charge.getMontant(date_actuelle,charge.getId("Eau",this.id_bail));
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        JLabel Eau = new JLabel("Eau : ");
        GridBagConstraints gbc_Eau = new GridBagConstraints();
        gbc_Eau.insets = new Insets(0, 0, 5, 5);
        gbc_Eau.gridx = 0;
        gbc_Eau.gridy = 1;
        donnee.add(Eau, gbc_Eau);

        JLabel prixEau = new JLabel(prix + " €");
        GridBagConstraints gbc_prixEau = new GridBagConstraints();
        gbc_prixEau.insets = new Insets(0, 0, 5, 5);
        gbc_prixEau.gridx = 1;
        gbc_prixEau.gridy = 1;
        donnee.add(prixEau, gbc_prixEau);

        try {
            prix=charge.getMontant(date_actuelle,charge.getId("Electricité",this.id_bail));
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        JLabel Electricite = new JLabel("Electricité : ");
        GridBagConstraints gbc_Electricite = new GridBagConstraints();
        gbc_Electricite.insets = new Insets(0, 0, 5, 5);
        gbc_Electricite.gridx = 2;
        gbc_Electricite.gridy = 1;
        donnee.add(Electricite, gbc_Electricite);


        JLabel prixElectricite = new JLabel(prix + " €");
        GridBagConstraints gbc_prixElectricite = new GridBagConstraints();
        gbc_prixElectricite.insets = new Insets(0, 0, 5, 5);
        gbc_prixElectricite.gridx = 3;
        gbc_prixElectricite.gridy = 1;
        donnee.add(prixElectricite, gbc_prixElectricite);

        try {
            prix=charge.getMontant(date_actuelle,charge.getId("Ordures",this.id_bail));
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        JLabel Ordure = new JLabel("Ordures : ");
        GridBagConstraints gbc_Ordure = new GridBagConstraints();
        gbc_Ordure.insets = new Insets(0, 0, 5, 5);
        gbc_Ordure.gridx = 0;
        gbc_Ordure.gridy = 3;
        donnee.add(Ordure, gbc_Ordure);

        JLabel prixOrdures = new JLabel(prix + " €");
        GridBagConstraints gbc_prixOrdures = new GridBagConstraints();
        gbc_prixOrdures.insets = new Insets(0, 0, 5, 5);
        gbc_prixOrdures.gridx = 1;
        gbc_prixOrdures.gridy = 3;
        donnee.add(prixOrdures, gbc_prixOrdures);

        try {
            prix=charge.getMontant(date_actuelle,charge.getId("Entretien",this.id_bail));
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        JLabel Entretien = new JLabel("Entretien : ");
        GridBagConstraints gbc_Entretien = new GridBagConstraints();
        gbc_Entretien.insets = new Insets(0, 0, 5, 5);
        gbc_Entretien.gridx = 2;
        gbc_Entretien.gridy = 3;
        donnee.add(Entretien, gbc_Entretien);

        JLabel prixEntretien = new JLabel(prix + " €");
        GridBagConstraints gbc_prixEntretien = new GridBagConstraints();
        gbc_prixEntretien.insets = new Insets(0, 0, 5, 5);
        gbc_prixEntretien.gridx = 3;
        gbc_prixEntretien.gridy = 3;
        donnee.add(prixEntretien, gbc_prixEntretien);

    }
}
