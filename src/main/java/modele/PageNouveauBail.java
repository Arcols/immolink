package modele;

import classes.Batiment;
import ihm.Charte;
import ihm.Menu;
import ihm.ModelePageNouveauBail;
import ihm.ResizedImage;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import static ihm.Charte.*;

public class PageNouveauBail {

    private JFrame frame;
    private JLabel logo;
    private JTextField choix_loyer;
    private JTextField choix_prevision;
    private JTextField choix_depot_garantie;
    private JTable table;
    private DefaultTableModel tableModel;
    private Map<String, List<String>> mapVillesAdresses;
    private Set<String> setVilles;


    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    PageNouveauBail window = new PageNouveauBail();
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
    public PageNouveauBail() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        try {
            this.mapVillesAdresses = Batiment.searchAllBatiments();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.setVilles = this.mapVillesAdresses.keySet();

        ModelePageNouveauBail modele = new ModelePageNouveauBail(this);

        this.frame = new JFrame();
        this.frame.setBounds(100, 100, 750, 400);
        this.frame.getContentPane().setBackground(Charte.FOND.getCouleur());
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Menu m = new Menu(this.frame);

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

        JButton b_accueil = new JButton("Accueil");
        b_accueil.setBorderPainted(false);
        b_accueil.setBackground(Charte.ENTETE.getCouleur());
        b_accueil.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menu_bouttons.add(b_accueil);
        b_accueil.addActionListener(m);

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
        FlowLayout fl_titre = (FlowLayout) titre.getLayout();
        body.add(titre, BorderLayout.NORTH);

        JLabel titrePage = new JLabel("Nouveau bail");
        titrePage.setAlignmentY(0.0f);
        titrePage.setAlignmentX(0.5f);
        titre.add(titrePage);

        JPanel panel_bien = new JPanel();
        body.add(panel_bien, BorderLayout.WEST);
        GridBagLayout gbl_panel_bien = new GridBagLayout();
        gbl_panel_bien.columnWidths = new int[] {0, 0, 30};
        gbl_panel_bien.rowHeights = new int[] {30, 0, 0, 0, 0, 0, 0, 0, 0, 90};
        gbl_panel_bien.columnWeights = new double[]{0.0, 0.0};
        gbl_panel_bien.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        panel_bien.setLayout(gbl_panel_bien);


        JLabel ville = new JLabel("Ville");
        GridBagConstraints gbc_ville = new GridBagConstraints();
        gbc_ville.fill = GridBagConstraints.BOTH;
        gbc_ville.insets = new Insets(0, 0, 5, 5);
        gbc_ville.gridx = 0;
        gbc_ville.gridy = 1;
        panel_bien.add(ville, gbc_ville);

        JComboBox choix_ville = new JComboBox();
        GridBagConstraints gbc_choix_ville = new GridBagConstraints();
        gbc_choix_ville.fill = GridBagConstraints.HORIZONTAL;
        gbc_choix_ville.insets = new Insets(0, 0, 5, 0);
        gbc_choix_ville.gridx = 1;
        gbc_choix_ville.gridy = 1;
        panel_bien.add(choix_ville, gbc_choix_ville);
        if (!this.setVilles.isEmpty()) {
            choix_ville.setModel(new DefaultComboBoxModel(this.setVilles.toArray(new String[0])));
        } else {
            choix_ville.setModel(new DefaultComboBoxModel());
        }

        JLabel Adresse = new JLabel("Adresse");
        GridBagConstraints gbc_Adresse = new GridBagConstraints();
        gbc_Adresse.fill = GridBagConstraints.BOTH;
        gbc_Adresse.insets = new Insets(0, 0, 5, 5);
        gbc_Adresse.gridx = 0;
        gbc_Adresse.gridy = 2;
        panel_bien.add(Adresse, gbc_Adresse);

        JComboBox choix_adresse = new JComboBox();
        GridBagConstraints gbc_choix_adresse = new GridBagConstraints();
        gbc_choix_adresse.fill = GridBagConstraints.HORIZONTAL;
        gbc_choix_adresse.insets = new Insets(0, 0, 5, 0);
        gbc_choix_adresse.gridx = 1;
        gbc_choix_adresse.gridy = 2;
        panel_bien.add(choix_adresse, gbc_choix_adresse);

        JLabel complement = new JLabel("Complément");
        GridBagConstraints gbc_complement = new GridBagConstraints();
        gbc_complement.fill = GridBagConstraints.BOTH;
        gbc_complement.insets = new Insets(0, 0, 5, 5);
        gbc_complement.gridx = 0;
        gbc_complement.gridy = 3;
        panel_bien.add(complement, gbc_complement);

        JComboBox choix_complement = new JComboBox();
        GridBagConstraints gbc_choix_complement = new GridBagConstraints();
        gbc_choix_complement.fill = GridBagConstraints.HORIZONTAL;
        gbc_choix_complement.insets = new Insets(0, 0, 5, 0);
        gbc_choix_complement.gridx = 1;
        gbc_choix_complement.gridy = 3;
        panel_bien.add(choix_complement, gbc_choix_complement);

        JLabel surface = new JLabel("Surface habitable");
        GridBagConstraints gbc_surface = new GridBagConstraints();
        gbc_surface.fill = GridBagConstraints.BOTH;
        gbc_surface.insets = new Insets(0, 0, 5, 5);
        gbc_surface.gridx = 0;
        gbc_surface.gridy = 4;
        panel_bien.add(surface, gbc_surface);

        JLabel choix_surface = new JLabel("");
        GridBagConstraints gbc_choix_surface = new GridBagConstraints();
        gbc_choix_surface.fill = GridBagConstraints.BOTH;
        gbc_choix_surface.insets = new Insets(0, 0, 5, 0);
        gbc_choix_surface.gridx = 1;
        gbc_choix_surface.gridy = 4;
        panel_bien.add(choix_surface, gbc_choix_surface);

        JLabel nb_piece = new JLabel("Nombre de pièces");
        GridBagConstraints gbc_nb_piece = new GridBagConstraints();
        gbc_nb_piece.fill = GridBagConstraints.BOTH;
        gbc_nb_piece.insets = new Insets(0, 0, 5, 5);
        gbc_nb_piece.gridx = 0;
        gbc_nb_piece.gridy = 5;
        panel_bien.add(nb_piece, gbc_nb_piece);

        JLabel choix_nb_piece = new JLabel("");
        GridBagConstraints gbc_choix_nb_piece = new GridBagConstraints();
        gbc_choix_nb_piece.fill = GridBagConstraints.BOTH;
        gbc_choix_nb_piece.insets = new Insets(0, 0, 5, 0);
        gbc_choix_nb_piece.gridx = 1;
        gbc_choix_nb_piece.gridy = 5;
        panel_bien.add(choix_nb_piece, gbc_choix_nb_piece);

        JLabel loyer = new JLabel("Loyer");
        GridBagConstraints gbc_loyer = new GridBagConstraints();
        gbc_loyer.fill = GridBagConstraints.BOTH;
        gbc_loyer.insets = new Insets(0, 0, 5, 5);
        gbc_loyer.gridx = 0;
        gbc_loyer.gridy = 6;
        panel_bien.add(loyer, gbc_loyer);

        choix_loyer = new JTextField();
        GridBagConstraints gbc_choix_loyer = new GridBagConstraints();
        gbc_choix_loyer.fill = GridBagConstraints.HORIZONTAL;
        gbc_choix_loyer.anchor = GridBagConstraints.WEST;
        gbc_choix_loyer.insets = new Insets(0, 0, 5, 0);
        gbc_choix_loyer.gridx = 1;
        gbc_choix_loyer.gridy = 6;
        panel_bien.add(choix_loyer, gbc_choix_loyer);
        choix_loyer.setColumns(7);

        JLabel prevision = new JLabel("Prévision pour charge");
        GridBagConstraints gbc_prevision = new GridBagConstraints();
        gbc_prevision.fill = GridBagConstraints.BOTH;
        gbc_prevision.insets = new Insets(0, 0, 5, 5);
        gbc_prevision.gridx = 0;
        gbc_prevision.gridy = 7;
        panel_bien.add(prevision, gbc_prevision);

        choix_prevision = new JTextField();
        GridBagConstraints gbc_choix_prevision = new GridBagConstraints();
        gbc_choix_prevision.fill = GridBagConstraints.HORIZONTAL;
        gbc_choix_prevision.anchor = GridBagConstraints.WEST;
        gbc_choix_prevision.insets = new Insets(0, 0, 5, 0);
        gbc_choix_prevision.gridx = 1;
        gbc_choix_prevision.gridy = 7;
        panel_bien.add(choix_prevision, gbc_choix_prevision);
        choix_prevision.setColumns(7);

        JLabel depot_garantie = new JLabel("Dépôt de garantie");
        GridBagConstraints gbc_depot_garantie = new GridBagConstraints();
        gbc_depot_garantie.fill = GridBagConstraints.BOTH;
        gbc_depot_garantie.insets = new Insets(0, 0, 0, 5);
        gbc_depot_garantie.gridx = 0;
        gbc_depot_garantie.gridy = 8;
        panel_bien.add(depot_garantie, gbc_depot_garantie);

        choix_depot_garantie = new JTextField();
        GridBagConstraints gbc_choix_depot_garantie = new GridBagConstraints();
        gbc_choix_depot_garantie.fill = GridBagConstraints.HORIZONTAL;
        gbc_choix_depot_garantie.anchor = GridBagConstraints.WEST;
        gbc_choix_depot_garantie.gridx = 1;
        gbc_choix_depot_garantie.gridy = 8;
        panel_bien.add(choix_depot_garantie, gbc_choix_depot_garantie);
        choix_depot_garantie.setColumns(7);

        JPanel panel_east = new JPanel();
        body.add(panel_east, BorderLayout.CENTER);
        GridBagLayout gbl_panel_east = new GridBagLayout();
        gbl_panel_east.columnWidths = new int[]{551, 0};
        gbl_panel_east.rowHeights = new int[] {100, 0, 30};
        gbl_panel_east.columnWeights = new double[]{0.0, Double.MIN_VALUE};
        gbl_panel_east.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        panel_east.setLayout(gbl_panel_east);

        tableModel = new DefaultTableModel(new String[] { "Prénom", "Nom", "Téléphone" }, 0);

        JPanel panel_locataire = new JPanel();
        GridBagConstraints gbc_panel_locataire = new GridBagConstraints();
        gbc_panel_locataire.fill = GridBagConstraints.BOTH;
        gbc_panel_locataire.insets = new Insets(0, 0, 5, 0);
        gbc_panel_locataire.gridx = 0;
        gbc_panel_locataire.gridy = 0;
        panel_east.add(panel_locataire, gbc_panel_locataire);
        GridBagLayout gbl_panel_locataire = new GridBagLayout();
        gbl_panel_locataire.columnWidths = new int[]{551, 0};
        gbl_panel_locataire.rowHeights = new int[] {150, 21, 10};
        gbl_panel_locataire.columnWeights = new double[]{0.0, Double.MIN_VALUE};
        gbl_panel_locataire.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        panel_locataire.setLayout(gbl_panel_locataire);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.anchor = GridBagConstraints.NORTH;
        gbc_scrollPane.fill = GridBagConstraints.HORIZONTAL;
        gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
        gbc_scrollPane.gridx = 0;
        gbc_scrollPane.gridy = 0;
        panel_locataire.add(scrollPane, gbc_scrollPane);

        JButton btn_ajouter_locataire = new JButton("Ajouter un locataire");
        GridBagConstraints gbc_btn_ajouter_locataire = new GridBagConstraints();
        gbc_btn_ajouter_locataire.anchor = GridBagConstraints.NORTH;
        gbc_btn_ajouter_locataire.gridx = 0;
        gbc_btn_ajouter_locataire.gridy = 1;
        panel_locataire.add(btn_ajouter_locataire, gbc_btn_ajouter_locataire);

        btn_ajouter_locataire.addActionListener(modele.getAjouterLocataire());

        JPanel panel_date = new JPanel();
        GridBagConstraints gbc_panel_date = new GridBagConstraints();
        gbc_panel_date.anchor = GridBagConstraints.NORTH;
        gbc_panel_date.fill = GridBagConstraints.HORIZONTAL;
        gbc_panel_date.gridx = 0;
        gbc_panel_date.gridy = 1;
        panel_east.add(panel_date, gbc_panel_date);
        panel_date.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JLabel date_debut = new JLabel("Date début");
        panel_date.add(date_debut);

        JFormattedTextField choix_date_debut = new JFormattedTextField();
        choix_date_debut.setColumns(10);
        panel_date.add(choix_date_debut);

        JLabel date_fin = new JLabel("Date fin");
        panel_date.add(date_fin);

        JFormattedTextField choix_date_fin = new JFormattedTextField();
        choix_date_fin.setColumns(10);
        panel_date.add(choix_date_fin);

        JPanel bas_de_page = new JPanel();
        this.frame.getContentPane().add(bas_de_page, BorderLayout.SOUTH);
        bas_de_page.setLayout(new BorderLayout(0, 0));

        JButton valider = new JButton("Valider");
        valider.setEnabled(false);
        valider.setHorizontalTextPosition(SwingConstants.LEFT);
        valider.setVerticalTextPosition(SwingConstants.TOP);
        valider.setVerticalAlignment(SwingConstants.BOTTOM);
        bas_de_page.add(valider, BorderLayout.EAST);
        this.frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                ResizedImage res = new ResizedImage();
                res.resizeImage("logo+nom.png", PageNouveauBail.this.frame,
                        PageNouveauBail
                                .this.logo, 3, 8);
                int frameWidth = PageNouveauBail.this.frame.getWidth();
                int frameHeight = PageNouveauBail.this.frame.getHeight();

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

    public JFrame getFrame() {
        return frame;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

}
