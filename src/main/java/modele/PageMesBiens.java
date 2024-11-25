package modele;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import DAO.BienLouableDAO;
import classes.Locataire;
import ihm.Charte;
import ihm.Menu;
import ihm.ResizedImage;
import modele.*;
import java.awt.GridBagLayout;
import javax.swing.JTable;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class PageMesBiens {

    private JFrame frame;
    private JLabel logo;
    private JTable table;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    PageMesBiens window = new PageMesBiens();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public PageMesBiens() {
        this.initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        // Initialisation du JFrame
        this.frame = new JFrame();
        this.frame.setBounds(100, 100, 750, 400);
        this.frame.getContentPane().setBackground(Charte.FOND.getCouleur());
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Panel d'entête pour le logo et le nom de l'appli
        JPanel entete = new JPanel();
        this.frame.getContentPane().add(entete, BorderLayout.NORTH);
        entete.setLayout(new BorderLayout(0, 0));

        entete.setBackground(Charte.ENTETE.getCouleur());
        entete.setBorder(new LineBorder(Color.BLACK, 2));
        // Label pour le logo (Image)
        this.logo = new JLabel("");
        entete.add(this.logo, BorderLayout.WEST);
        JPanel menu_bouttons = new JPanel();

        entete.add(menu_bouttons, BorderLayout.CENTER);
        menu_bouttons.setLayout(new GridLayout(0, 5, 0, 0));
        menu_bouttons.setBackground(Charte.ENTETE.getCouleur());

        Menu m = new Menu(this.frame);

        JButton b_accueil = new JButton("Accueil");
        b_accueil.setBorderPainted(false);
        b_accueil.setBackground(Charte.ENTETE.getCouleur());
        b_accueil.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menu_bouttons.add(b_accueil);

        JButton b_profil = new JButton("Profil");
        b_profil.setBorderPainted(false);
        b_profil.setBackground(Charte.ENTETE.getCouleur());
        b_profil.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menu_bouttons.add(b_profil);
        menu_bouttons.add(b_profil);
        b_profil.addActionListener(m);

        JButton b_baux = new JButton("Mes baux");
        b_baux.setBorderPainted(false);
        b_baux.setBackground(Charte.ENTETE.getCouleur());
        b_baux.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menu_bouttons.add(b_baux);
        menu_bouttons.add(b_baux);
        b_baux.addActionListener(m);

        JButton b_loca = new JButton("Locataires");
        b_loca.setBorderPainted(false);
        b_loca.setBackground(Charte.ENTETE.getCouleur());
        b_loca.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menu_bouttons.add(b_loca);
        menu_bouttons.add(b_loca);
        b_loca.addActionListener(m);

        JButton b_biens = new JButton("Mes Biens");
        b_biens.setBorderPainted(false);
        b_biens.setBackground(Charte.ENTETE.getCouleur());
        b_biens.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menu_bouttons.add(b_biens);
        menu_bouttons.add(b_biens);

        JPanel body = new JPanel();
        frame.getContentPane().add(body, BorderLayout.CENTER);
        body.setLayout(new BorderLayout(0, 0));

        JPanel titre = new JPanel();
        body.add(titre, BorderLayout.NORTH);

        JLabel labelMesBiens = new JLabel("Mes biens");
        titre.add(labelMesBiens);

        JPanel panel = new JPanel();
        body.add(panel, BorderLayout.CENTER);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[] {30, 0, 30};
        gbl_panel.rowHeights = new int[] {30, 170, 40, 30};
        gbl_panel.columnWeights = new double[]{0.0, 1.0};
        gbl_panel.rowWeights = new double[]{0.0, 1.0, 1.0};
        panel.setLayout(gbl_panel);

        table = new JTable();
        GridBagConstraints gbc_table = new GridBagConstraints();
        gbc_table.insets = new Insets(0, 0, 5, 0);
        gbc_table.fill = GridBagConstraints.BOTH;
        gbc_table.gridx = 1;
        gbc_table.gridy = 1;
        panel.add(table, gbc_table);
//        try {
//            loadDataBienImmoToTable();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        JPanel panel_1 = new JPanel();
        GridBagConstraints gbc_panel_1 = new GridBagConstraints();
        gbc_panel_1.anchor = GridBagConstraints.SOUTH;
        gbc_panel_1.gridx = 1;
        gbc_panel_1.gridy = 2;
        panel.add(panel_1, gbc_panel_1);
        GridBagLayout gbl_panel_1 = new GridBagLayout();
        gbl_panel_1.columnWidths = new int[] {0};
        gbl_panel_1.rowHeights = new int[] {0};
        gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0};
        gbl_panel_1.rowWeights = new double[]{0.0, 0.0};
        panel_1.setLayout(gbl_panel_1);

        this.frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                ResizedImage.resizeImage("/ressources/images/logo+nom.png", PageMesBiens.this.frame,
                        PageMesBiens.this.logo, 3, 8);
                int frameWidth = PageMesBiens.this.frame.getWidth();
                int frameHeight = PageMesBiens.this.frame.getHeight();

                int newFontSize = Math.min(frameWidth, frameHeight) / 30;

                // Appliquer la nouvelle police au bouton
                Font resizedFont = new Font("Arial", Font.PLAIN, newFontSize);
                b_loca.setFont(resizedFont);
                b_baux.setFont(resizedFont);
                b_accueil.setFont(resizedFont);
                b_profil.setFont(resizedFont);
                b_biens.setFont(resizedFont);
            }
        });

    }
//    private void loadDataBienImmoToTable() throws SQLException {
//        // Liste des colonnes pour les biens immobiliers
//        String[] columnNames = {"Numéro Fiscal", "Adresse", "Ville", "Complément Adresse"};
//
//        // Création du modèle de table
//        DefaultTableModel model = new DefaultTableModel(columnNames, 0); // `0` pour aucune ligne au départ
//
//        // Récupération des biens immobiliers
//        BienLouableDAO bienDAO = new BienLouableDAOImpl(); // Implémentation fictive
//        List<BienLouable> biens = bienDAO.findAll();
//
//        // Remplissage du modèle avec les données des biens
//        for (BienLouable bien : biens) {
//            Object[] rowData = {
//                    bien.getNumero_fiscal(),
//                    bien.getAdresse(),
//                    bien.getVille(),
//                    bien.getComplement_adresse()
//            };
//            model.addRow(rowData); // Ajout de la ligne dans le modèle
//        }
//
//        // Attribution du modèle au JTable
//        this.table.setModel(model);
//    }



}
