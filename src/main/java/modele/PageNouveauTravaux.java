package modele;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import DAO.DAOException;
import ihm.*;

import javax.swing.SwingConstants;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;

public class PageNouveauTravaux {

    public JFrame getFrame() {
        return this.frame;
    }

    private JFrame frame;
    private JLabel logo;
    private JTextField valueNumDevis;
    private JTextField valueMontantDevis;
    private JTextField valueMontantTravaux;
    private JTextField valueNature;
    private JTextField ValueAdresse;
    private JTextField valueNom;
    private JTextField valueType;
    private JTextField valueDateDebut;
    private JTextField valueDateFin;


    /**
     * Create the application.
     */
    public PageNouveauTravaux(Integer id) throws DAOException {
        this.initialize(id);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize(Integer id) throws DAOException {
        ModelePageNouveauTravaux modele = new ModelePageNouveauTravaux(this);
        this.frame = new JFrame();
        this.frame.setBounds(100, 100, 750, 400);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel entete = new JPanel();
        this.frame.getContentPane().add(entete, BorderLayout.NORTH);
        entete.setLayout(new BorderLayout(0, 0));
        this.frame.getContentPane().setBackground(Charte.FOND.getCouleur());

        entete.setBackground(Charte.ENTETE.getCouleur());
        entete.setBorder(new LineBorder(Color.BLACK, 2));

        this.logo = new JLabel("");
        entete.add(this.logo, BorderLayout.WEST);

        JPanel menu_bouttons = new JPanel();

        this.frame.getContentPane().add(entete, BorderLayout.NORTH);
        entete.setLayout(new BorderLayout(0, 0));
        this.frame.getContentPane().setBackground(Charte.FOND.getCouleur());

        entete.setBackground(Charte.ENTETE.getCouleur());
        entete.setBorder(new LineBorder(Color.BLACK, 2));

        this.logo = new JLabel("");
        entete.add(this.logo, BorderLayout.WEST);

        Menu m = new Menu(this.frame);


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

        JPanel Body = new JPanel();
        frame.getContentPane().add(Body, BorderLayout.CENTER);
        Body.setLayout(new BorderLayout(0, 0));

        JLabel labelTitre = new JLabel("Nouveau travaux");
        labelTitre.setHorizontalAlignment(SwingConstants.CENTER);
        Body.add(labelTitre, BorderLayout.NORTH);

        JPanel valeurs = new JPanel();
        Body.add(valeurs, BorderLayout.CENTER);
        GridBagLayout gbl_valeurs = new GridBagLayout();
        gbl_valeurs.columnWidths = new int[] {0};
        gbl_valeurs.rowHeights = new int[] {0};
        gbl_valeurs.columnWeights = new double[]{0.0, 0.0};
        gbl_valeurs.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        valeurs.setLayout(gbl_valeurs);

        JLabel labelNumDevis = new JLabel("Numero devis");
        GridBagConstraints gbc_labelNumDevis = new GridBagConstraints();
        gbc_labelNumDevis.insets = new Insets(0, 0, 5, 5);
        gbc_labelNumDevis.anchor = GridBagConstraints.WEST;
        gbc_labelNumDevis.gridx = 0;
        gbc_labelNumDevis.gridy = 0;
        valeurs.add(labelNumDevis, gbc_labelNumDevis);

        valueNumDevis = new JTextField();
        GridBagConstraints gbc_valueNumDevis = new GridBagConstraints();
        gbc_valueNumDevis.insets = new Insets(0, 0, 5, 0);
        gbc_valueNumDevis.anchor = GridBagConstraints.WEST;
        gbc_valueNumDevis.gridx = 1;
        gbc_valueNumDevis.gridy = 0;
        valeurs.add(valueNumDevis, gbc_valueNumDevis);
        valueNumDevis.setColumns(10);

        JLabel labelMontantDevis = new JLabel("Montant du devis");
        GridBagConstraints gbc_labelMontantDevis = new GridBagConstraints();
        gbc_labelMontantDevis.anchor = GridBagConstraints.SOUTHWEST;
        gbc_labelMontantDevis.insets = new Insets(0, 0, 5, 5);
        gbc_labelMontantDevis.gridx = 0;
        gbc_labelMontantDevis.gridy = 1;
        valeurs.add(labelMontantDevis, gbc_labelMontantDevis);

        valueMontantDevis = new JTextField();
        GridBagConstraints gbc_valueMontantDevis = new GridBagConstraints();
        gbc_valueMontantDevis.anchor = GridBagConstraints.WEST;
        gbc_valueMontantDevis.insets = new Insets(0, 0, 5, 0);
        gbc_valueMontantDevis.gridx = 1;
        gbc_valueMontantDevis.gridy = 1;
        valeurs.add(valueMontantDevis, gbc_valueMontantDevis);
        valueMontantDevis.setColumns(10);

        JLabel labelMontantTeavaux = new JLabel("Montant des travaux");
        GridBagConstraints gbc_labelMontantTeavaux = new GridBagConstraints();
        gbc_labelMontantTeavaux.anchor = GridBagConstraints.WEST;
        gbc_labelMontantTeavaux.insets = new Insets(0, 0, 5, 5);
        gbc_labelMontantTeavaux.gridx = 0;
        gbc_labelMontantTeavaux.gridy = 2;
        valeurs.add(labelMontantTeavaux, gbc_labelMontantTeavaux);

        valueMontantTravaux = new JTextField();
        GridBagConstraints gbc_valueMontantTravaux = new GridBagConstraints();
        gbc_valueMontantTravaux.anchor = GridBagConstraints.WEST;
        gbc_valueMontantTravaux.insets = new Insets(0, 0, 5, 0);
        gbc_valueMontantTravaux.gridx = 1;
        gbc_valueMontantTravaux.gridy = 2;
        valeurs.add(valueMontantTravaux, gbc_valueMontantTravaux);
        valueMontantTravaux.setColumns(10);

        JLabel labelNature = new JLabel("Nature");
        GridBagConstraints gbc_labelNature = new GridBagConstraints();
        gbc_labelNature.anchor = GridBagConstraints.WEST;
        gbc_labelNature.insets = new Insets(0, 0, 5, 5);
        gbc_labelNature.gridx = 0;
        gbc_labelNature.gridy = 3;
        valeurs.add(labelNature, gbc_labelNature);

        valueNature = new JTextField();
        GridBagConstraints gbc_valueNature = new GridBagConstraints();
        gbc_valueNature.anchor = GridBagConstraints.WEST;
        gbc_valueNature.insets = new Insets(0, 0, 5, 0);
        gbc_valueNature.gridx = 1;
        gbc_valueNature.gridy = 3;
        valeurs.add(valueNature, gbc_valueNature);
        valueNature.setColumns(10);

        JLabel labelAdresse = new JLabel("Adresse de l'entreprise");
        GridBagConstraints gbc_labelAdresse = new GridBagConstraints();
        gbc_labelAdresse.anchor = GridBagConstraints.WEST;
        gbc_labelAdresse.insets = new Insets(0, 0, 5, 5);
        gbc_labelAdresse.gridx = 0;
        gbc_labelAdresse.gridy = 4;
        valeurs.add(labelAdresse, gbc_labelAdresse);

        ValueAdresse = new JTextField();
        GridBagConstraints gbc_valueAdresse = new GridBagConstraints();
        gbc_valueAdresse.anchor = GridBagConstraints.WEST;
        gbc_valueAdresse.insets = new Insets(0, 0, 5, 0);
        gbc_valueAdresse.gridx = 1;
        gbc_valueAdresse.gridy = 4;
        valeurs.add(ValueAdresse, gbc_valueAdresse);
        ValueAdresse.setColumns(10);

        JLabel LabelNom = new JLabel("Nom de l'entreprise");
        GridBagConstraints gbc_LabelNom = new GridBagConstraints();
        gbc_LabelNom.anchor = GridBagConstraints.WEST;
        gbc_LabelNom.insets = new Insets(0, 0, 5, 5);
        gbc_LabelNom.gridx = 0;
        gbc_LabelNom.gridy = 5;
        valeurs.add(LabelNom, gbc_LabelNom);

        valueNom = new JTextField();
        GridBagConstraints gbc_valueNom = new GridBagConstraints();
        gbc_valueNom.anchor = GridBagConstraints.WEST;
        gbc_valueNom.insets = new Insets(0, 0, 5, 0);
        gbc_valueNom.gridx = 1;
        gbc_valueNom.gridy = 5;
        valeurs.add(valueNom, gbc_valueNom);
        valueNom.setColumns(10);

        JLabel labelType = new JLabel("Type");
        GridBagConstraints gbc_labelType = new GridBagConstraints();
        gbc_labelType.anchor = GridBagConstraints.WEST;
        gbc_labelType.insets = new Insets(0, 0, 5, 5);
        gbc_labelType.gridx = 0;
        gbc_labelType.gridy = 6;
        valeurs.add(labelType, gbc_labelType);

        valueType = new JTextField();
        GridBagConstraints gbc_valueType = new GridBagConstraints();
        gbc_valueType.anchor = GridBagConstraints.WEST;
        gbc_valueType.insets = new Insets(0, 0, 5, 0);
        gbc_valueType.gridx = 1;
        gbc_valueType.gridy = 6;
        valeurs.add(valueType, gbc_valueType);
        valueType.setColumns(10);

        JLabel labelDateDebut = new JLabel("Date de début");
        GridBagConstraints gbc_labelDateDebut = new GridBagConstraints();
        gbc_labelDateDebut.anchor = GridBagConstraints.WEST;
        gbc_labelDateDebut.insets = new Insets(0, 0, 5, 5);
        gbc_labelDateDebut.gridx = 0;
        gbc_labelDateDebut.gridy = 7;
        valeurs.add(labelDateDebut, gbc_labelDateDebut);

        valueDateDebut = new JTextField();
        GridBagConstraints gbc_valueDateDebut = new GridBagConstraints();
        gbc_valueDateDebut.anchor = GridBagConstraints.WEST;
        gbc_valueDateDebut.insets = new Insets(0, 0, 5, 0);
        gbc_valueDateDebut.gridx = 1;
        gbc_valueDateDebut.gridy = 7;
        valeurs.add(valueDateDebut, gbc_valueDateDebut);
        valueDateDebut.setColumns(10);

        JLabel labelDateFin = new JLabel("Date de fin");
        GridBagConstraints gbc_labelDateFin = new GridBagConstraints();
        gbc_labelDateFin.anchor = GridBagConstraints.WEST;
        gbc_labelDateFin.insets = new Insets(0, 0, 5, 5);
        gbc_labelDateFin.gridx = 0;
        gbc_labelDateFin.gridy = 8;
        valeurs.add(labelDateFin, gbc_labelDateFin);

        valueDateFin = new JTextField();
        GridBagConstraints gbc_valueDateFin = new GridBagConstraints();
        gbc_valueDateFin.insets = new Insets(0, 0, 5, 0);
        gbc_valueDateFin.anchor = GridBagConstraints.WEST;
        gbc_valueDateFin.gridx = 1;
        gbc_valueDateFin.gridy = 8;
        valeurs.add(valueDateFin, gbc_valueDateFin);
        valueDateFin.setColumns(10);

        JPanel panelValider = new JPanel();
        Body.add(panelValider, BorderLayout.SOUTH);
        panelValider.setLayout(new BorderLayout(0, 0));

//        JButton btnQuitter = new JButton("Quitter");
//        panelValider.add(btnQuitter, BorderLayout.WEST);

        JButton btnValider = new JButton("Valider");
        panelValider.add(btnValider, BorderLayout.EAST);
        b_biens.addActionListener(m);

        this.frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                ResizedImage res = new ResizedImage();
                res.resizeImage("logo+nom.png", PageNouveauTravaux.this.frame,
                        PageNouveauTravaux.this.logo, 3, 8);
                int frameWidth = PageNouveauTravaux.this.frame.getWidth();
                int frameHeight = PageNouveauTravaux.this.frame.getHeight();

                int newFontSize = Math.min(frameWidth, frameHeight) / 30;

                // Appliquer la nouvelle police au bouton
                Font resizedFont = new Font("Arial", Font.PLAIN, newFontSize);
                b_baux.setFont(resizedFont);
                b_accueil.setFont(resizedFont);
                b_biens.setFont(resizedFont);
            }
        });
        frame.setVisible(true);
        btnValider.addActionListener(modele.getAjouterTravauxListener(id));
    }
    public JTextField getValueNumDevis() {
        return valueNumDevis;
    }

    public JTextField getValueMontantDevis() {
        return valueMontantDevis;
    }

    public JTextField getValueMontantTravaux() {
        return valueMontantTravaux;
    }

    public JTextField getValueNature() {
        return valueNature;
    }

    public JTextField getValueAdresse() {
        return ValueAdresse;
    }

    public JTextField getValueNom() {
        return valueNom;
    }

    public JTextField getValueType() {
        return valueType;
    }

    public JTextField getValueDateDebut() {
        return valueDateDebut;
    }

    public JTextField getValueDateFin() {
        return valueDateFin;
    }
}
