package modele;

import DAO.jdbc.BatimentDAO;
import classes.Diagnostic;
import classes.Garage;
import enumeration.NomsDiags;
import enumeration.TypeLogement;
import ihm.Charte;
import ihm.Menu;
import ihm.ResizedImage;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class PageCharge {

    private JFrame frame;
    private JLabel logo;
    /**
     * Launch the application.
     */

    public JFrame getFrame() {
        return this.frame;
    }

    /**
     * Create the application.
     */
    public PageCharge(Integer idBail) {
        this.initialize(idBail);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize(Integer idBail) {
        this.frame = new JFrame();
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
        menu_bouttons.setLayout(new GridLayout(0, 3, 0, 0));
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

        JPanel body = new JPanel();
        this.frame.getContentPane().add(body, BorderLayout.CENTER);
        body.setLayout(new BorderLayout(0, 0));

        JPanel titre = new JPanel();
        FlowLayout fl_titre = (FlowLayout) titre.getLayout();
        body.add(titre, BorderLayout.NORTH);

        JLabel titrePage = new JLabel("Liste des charges");
        titrePage.setAlignmentY(0.0f);
        titrePage.setAlignmentX(0.5f);
        titre.add(titrePage);

        JPanel contenu = new JPanel();
        body.add(contenu, BorderLayout.CENTER);
        GridBagLayout gbl_contenu = new GridBagLayout();
        gbl_contenu.columnWidths = new int[] {30, 30, 30, 30};
        gbl_contenu.rowHeights = new int[] {0, 30, 0, 30, 0, 30};
        gbl_contenu.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0};
        gbl_contenu.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        contenu.setLayout(gbl_contenu);

        JLabel Eau = new JLabel("Eau : ");
        GridBagConstraints gbc_Eau = new GridBagConstraints();
        gbc_Eau.insets = new Insets(0, 0, 5, 5);
        gbc_Eau.gridx = 0;
        gbc_Eau.gridy = 1;
        contenu.add(Eau, gbc_Eau);

        JLabel prixEau = new JLabel("O");
        GridBagConstraints gbc_prixEau = new GridBagConstraints();
        gbc_prixEau.insets = new Insets(0, 0, 5, 5);
        gbc_prixEau.gridx = 1;
        gbc_prixEau.gridy = 1;
        contenu.add(prixEau, gbc_prixEau);

        JLabel Electricite = new JLabel("Electricité : ");
        GridBagConstraints gbc_Electricite = new GridBagConstraints();
        gbc_Electricite.insets = new Insets(0, 0, 5, 5);
        gbc_Electricite.gridx = 2;
        gbc_Electricite.gridy = 1;
        contenu.add(Electricite, gbc_Electricite);

        JLabel prixElectricite = new JLabel("O");
        GridBagConstraints gbc_prixElectricite = new GridBagConstraints();
        gbc_prixElectricite.insets = new Insets(0, 0, 5, 5);
        gbc_prixElectricite.gridx = 3;
        gbc_prixElectricite.gridy = 1;
        contenu.add(prixElectricite, gbc_prixElectricite);

        JLabel Ordure = new JLabel("Ordures : ");
        GridBagConstraints gbc_Ordure = new GridBagConstraints();
        gbc_Ordure.insets = new Insets(0, 0, 5, 5);
        gbc_Ordure.gridx = 0;
        gbc_Ordure.gridy = 3;
        contenu.add(Ordure, gbc_Ordure);

        JLabel prixOrdures = new JLabel("O");
        GridBagConstraints gbc_prixOrdures = new GridBagConstraints();
        gbc_prixOrdures.insets = new Insets(0, 0, 5, 5);
        gbc_prixOrdures.gridx = 1;
        gbc_prixOrdures.gridy = 3;
        contenu.add(prixOrdures, gbc_prixOrdures);

        JLabel Entretien = new JLabel("Entretien : ");
        GridBagConstraints gbc_Entretien = new GridBagConstraints();
        gbc_Entretien.insets = new Insets(0, 0, 5, 5);
        gbc_Entretien.gridx = 2;
        gbc_Entretien.gridy = 3;
        contenu.add(Entretien, gbc_Entretien);

        JLabel prixEntretien = new JLabel("O");
        GridBagConstraints gbc_prixEntretien = new GridBagConstraints();
        gbc_prixEntretien.insets = new Insets(0, 0, 5, 5);
        gbc_prixEntretien.gridx = 3;
        gbc_prixEntretien.gridy = 3;
        contenu.add(prixEntretien, gbc_prixEntretien);

        JPanel panelbouton = new JPanel();
        body.add(panelbouton, BorderLayout.SOUTH);

        JButton archivage = new JButton("Données archivées");
        panelbouton.add(archivage);

        JButton facture = new JButton("Ajouter une facture");
        panelbouton.add(facture);

        JPanel bas_de_page = new JPanel();
        this.frame.getContentPane().add(bas_de_page, BorderLayout.SOUTH);
        bas_de_page.setLayout(new BorderLayout(0, 0));

        JButton regularisation = new JButton("Régularisation des charges");
        regularisation.setEnabled(false);
        regularisation.setHorizontalTextPosition(SwingConstants.LEFT);
        regularisation.setVerticalTextPosition(SwingConstants.TOP);
        regularisation.setVerticalAlignment(SwingConstants.BOTTOM);
        bas_de_page.add(regularisation, BorderLayout.EAST);

        JButton quitter = new JButton("Quitter");
        quitter.setHorizontalTextPosition(SwingConstants.LEFT);
        quitter.setVerticalTextPosition(SwingConstants.TOP);
        quitter.setVerticalAlignment(SwingConstants.BOTTOM);
        bas_de_page.add(quitter, BorderLayout.WEST);

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
    }
}
