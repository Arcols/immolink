package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import modele.Menu;
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
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import DAO.jdbc.BatimentDAO;
import DAO.jdbc.BienLouableDAO;
import modele.Charte;
import modele.ModelePageNouveauBail;
import modele.ResizedImage;

public class PageNouveauBail {

    private JFrame frame;
    private JLabel logo;
    private JLabel choix_surface;
    private JLabel choix_nb_piece;
    private JTextField choix_loyer;
    private JTextField choix_prevision;
    private JTextField choix_depot_garantie;
    private JTextField choixIcc;
    private JTextField choixIndexEau;
    private JDateChooser choix_date_debut;
    private JDateChooser choix_date_fin;
    private JComboBox choix_complement;
    private JComboBox choix_adresse;
    private JComboBox choix_ville;
    private JButton valider;
    private JCheckBox solde_tout_compte;
    private JTable table;
    private DefaultTableModel tableModel;
    private Map<String, List<String>> mapVillesAdresses;
    private Map<String, List<String>> mapAdressesComplement;
    private Set<String> setVilles;
    private Set<String> setAdresse;

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
    public PageNouveauBail() throws SQLException {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() throws SQLException {

        try {
            this.mapVillesAdresses = new BatimentDAO().searchAllBatimentsWithCompl();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.setVilles = this.mapVillesAdresses.keySet();

        this.mapAdressesComplement =new BienLouableDAO().getAllcomplements();
        this.setAdresse = this.mapAdressesComplement.keySet();

        ModelePageNouveauBail modele = new ModelePageNouveauBail(this);

        this.frame = new JFrame();
        this.frame.setBounds(100, 100, 750, 500);
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
        frame.getContentPane().add(body, BorderLayout.CENTER);
        body.setLayout(new BorderLayout(0, 0));

        JPanel titre = new JPanel();
        FlowLayout fl_titre = (FlowLayout) titre.getLayout();
        body.add(titre, BorderLayout.NORTH);

        JLabel titleLabel = new JLabel("Nouveau bail", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        body.add(titleLabel, BorderLayout.NORTH);

        JPanel panel_bien = new JPanel();
        body.add(panel_bien, BorderLayout.WEST);
        GridBagLayout gbl_panel_bien = new GridBagLayout();
        gbl_panel_bien.columnWidths = new int[] {0, 0, 30};
        gbl_panel_bien.rowHeights = new int[] {30, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 60};
        gbl_panel_bien.columnWeights = new double[]{0.0, 0.0};
        gbl_panel_bien.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        panel_bien.setLayout(gbl_panel_bien);


        JLabel ville = new JLabel("Ville");
        GridBagConstraints gbc_ville = new GridBagConstraints();
        gbc_ville.fill = GridBagConstraints.BOTH;
        gbc_ville.insets = new Insets(0, 0, 5, 5);
        gbc_ville.gridx = 0;
        gbc_ville.gridy = 1;
        panel_bien.add(ville, gbc_ville);

        this.choix_ville = new JComboBox();
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

        this.choix_adresse = new JComboBox();
        GridBagConstraints gbc_choix_adresse = new GridBagConstraints();
        gbc_choix_adresse.fill = GridBagConstraints.HORIZONTAL;
        gbc_choix_adresse.insets = new Insets(0, 0, 5, 0);
        gbc_choix_adresse.gridx = 1;
        gbc_choix_adresse.gridy = 2;
        panel_bien.add(choix_adresse, gbc_choix_adresse);
        if (this.setVilles.isEmpty()) {
            choix_adresse.setModel(new DefaultComboBoxModel());
        } else {
            choix_adresse.setModel(new DefaultComboBoxModel(
                    this.mapVillesAdresses.get(choix_ville.getSelectedItem()).toArray(new String[0])));
        }

        JLabel complement = new JLabel("Complément");
        GridBagConstraints gbc_complement = new GridBagConstraints();
        gbc_complement.fill = GridBagConstraints.BOTH;
        gbc_complement.insets = new Insets(0, 0, 5, 5);
        gbc_complement.gridx = 0;
        gbc_complement.gridy = 3;
        panel_bien.add(complement, gbc_complement);

        this.choix_complement = new JComboBox();
        GridBagConstraints gbc_choix_complement = new GridBagConstraints();
        gbc_choix_complement.fill = GridBagConstraints.HORIZONTAL;
        gbc_choix_complement.insets = new Insets(0, 0, 5, 0);
        gbc_choix_complement.gridx = 1;
        gbc_choix_complement.gridy = 3;
        panel_bien.add(choix_complement, gbc_choix_complement);
        if (this.setAdresse.isEmpty()) {
            choix_complement.setModel(new DefaultComboBoxModel());
        } else {
            choix_complement.setModel(new DefaultComboBoxModel(
                    this.mapAdressesComplement.get(choix_adresse.getSelectedItem()).toArray(new String[0])));
        }

        JLabel surface = new JLabel("Surface habitable");
        GridBagConstraints gbc_surface = new GridBagConstraints();
        gbc_surface.fill = GridBagConstraints.BOTH;
        gbc_surface.insets = new Insets(0, 0, 5, 5);
        gbc_surface.gridx = 0;
        gbc_surface.gridy = 4;
        panel_bien.add(surface, gbc_surface);

        this.choix_surface = new JLabel("");
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

        this.choix_nb_piece = new JLabel("");
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

        JLabel prevision = new JLabel("Provision pour charge");
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
        gbc_depot_garantie.insets = new Insets(0, 0, 5, 5);
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

        // ICC
        JLabel labelIcc = new JLabel("ICC");
        GridBagConstraints gbc_labelIcc = new GridBagConstraints();
        gbc_labelIcc.fill = GridBagConstraints.HORIZONTAL;
        gbc_labelIcc.insets = new Insets(10, 0, 5, 5);
        gbc_labelIcc.gridx = 0;
        gbc_labelIcc.gridy = 9;
        panel_bien.add(labelIcc, gbc_labelIcc);

        choixIcc = new JTextField();
        GridBagConstraints gbc_choixIcc = new GridBagConstraints();
        gbc_choixIcc.fill = GridBagConstraints.HORIZONTAL;
        gbc_choixIcc.anchor = GridBagConstraints.WEST;
        gbc_choixIcc.gridx = 1;
        gbc_choixIcc.gridy = 9;
        panel_bien.add(choixIcc, gbc_choixIcc);
        choixIcc.setColumns(7);

        // INDEX EAU
        JLabel labelIndexEau = new JLabel("Index Eau");
        GridBagConstraints gbc_labelIndexEau = new GridBagConstraints();
        gbc_labelIndexEau.fill = GridBagConstraints.HORIZONTAL;
        gbc_labelIndexEau.insets = new Insets(0, 0, 5, 5);
        gbc_labelIndexEau.gridx = 0;
        gbc_labelIndexEau.gridy = 10;
        panel_bien.add(labelIndexEau, gbc_labelIndexEau);

        choixIndexEau = new JTextField();
        GridBagConstraints gbc_choixIndexEau = new GridBagConstraints();
        gbc_choixIndexEau.fill = GridBagConstraints.HORIZONTAL;
        gbc_choixIndexEau.anchor = GridBagConstraints.WEST;
        gbc_choixIndexEau.gridx = 1;
        gbc_choixIndexEau.gridy = 10;
        panel_bien.add(choixIndexEau, gbc_choixIndexEau);


        this.solde_tout_compte = new JCheckBox("Solde de tout compte");
        GridBagConstraints gbc_solde_tout_compte = new GridBagConstraints();
        gbc_solde_tout_compte.fill = GridBagConstraints.BOTH;
        gbc_solde_tout_compte.insets = new Insets(0, 0, 5, 5);
        gbc_solde_tout_compte.anchor = GridBagConstraints.WEST;
        gbc_solde_tout_compte.gridx = 1;
        gbc_solde_tout_compte.gridy = 11;
        panel_bien.add(solde_tout_compte, gbc_solde_tout_compte);

        JPanel panel_east = new JPanel();
        body.add(panel_east, BorderLayout.CENTER);
        GridBagLayout gbl_panel_east = new GridBagLayout();
        gbl_panel_east.columnWidths = new int[]{551, 0};
        gbl_panel_east.rowHeights = new int[] {100, 0, 30};
        gbl_panel_east.columnWeights = new double[]{0.0, Double.MIN_VALUE};
        gbl_panel_east.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        panel_east.setLayout(gbl_panel_east);

        tableModel = new DefaultTableModel(new String[] { "Prénom", "Nom", "Téléphone","Quotité" }, 0){
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Toutes les cellules sont non éditables
            }
        };

        JPanel panel_locataire = new JPanel();
        GridBagConstraints gbc_panel_locataire = new GridBagConstraints();
        gbc_panel_locataire.fill = GridBagConstraints.BOTH;
        gbc_panel_locataire.insets = new Insets(0, 0, 5, 0);
        gbc_panel_locataire.gridx = 0;
        gbc_panel_locataire.gridy = 0;
        panel_east.add(panel_locataire, gbc_panel_locataire);
        GridBagLayout gbl_panel_locataire = new GridBagLayout();
        gbl_panel_locataire.columnWidths = new int[]{551, 0};
        gbl_panel_locataire.rowHeights = new int[] {150, 21, 11};
        gbl_panel_locataire.columnWeights = new double[]{0.0, Double.MIN_VALUE};
        gbl_panel_locataire.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        panel_locataire.setLayout(gbl_panel_locataire);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.anchor = GridBagConstraints.NORTH;
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
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

        JButton btn_supprimer_locataire = new JButton("Supprimer un locataire");
        GridBagConstraints gbc_btn_supprimer_locataire = new GridBagConstraints();
        gbc_btn_supprimer_locataire.anchor = GridBagConstraints.NORTH;
        gbc_btn_supprimer_locataire.gridx = 0;
        gbc_btn_supprimer_locataire.gridy = 2;
        panel_locataire.add(btn_supprimer_locataire, gbc_btn_supprimer_locataire);

        btn_supprimer_locataire.addActionListener(modele.supprimerLocataire());

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

        this.choix_date_debut = new JDateChooser();
        choix_date_debut.setPreferredSize(new Dimension(100, 22));
        panel_date.add(choix_date_debut);

        JLabel date_fin = new JLabel("Date fin");
        panel_date.add(date_fin);

        this.choix_date_fin = new JDateChooser();
        choix_date_fin.setPreferredSize(new Dimension(100, 22));
        panel_date.add(choix_date_fin);

        JPanel bas_de_page = new JPanel();
        this.frame.getContentPane().add(bas_de_page, BorderLayout.SOUTH);
        bas_de_page.setLayout(new BorderLayout(0, 0));

        this.valider = new JButton("Valider");
        valider.setEnabled(false);
        valider.setHorizontalTextPosition(SwingConstants.LEFT);
        valider.setVerticalTextPosition(SwingConstants.TOP);
        valider.setVerticalAlignment(SwingConstants.BOTTOM);
        bas_de_page.add(valider, BorderLayout.EAST);

        JButton quitter = new JButton("Quitter");
        quitter.setHorizontalTextPosition(SwingConstants.LEFT);
        quitter.setVerticalTextPosition(SwingConstants.TOP);
        quitter.setVerticalAlignment(SwingConstants.BOTTOM);
        bas_de_page.add(quitter, BorderLayout.WEST);

        this.frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                ResizedImage res = new ResizedImage();
                res.resizeImage("logo+nom.png", PageNouveauBail.this.frame,
                        PageNouveauBail.this.logo, 3, 8);
                int frameWidth = PageNouveauBail.this.frame.getWidth();
                int frameHeight = PageNouveauBail.this.frame.getHeight();

                int newFontSize = Math.min(frameWidth, frameHeight) / 30;

                // Appliquer la nouvelle police au bouton
                Font resizedFont = new Font("Arial", Font.PLAIN, newFontSize);
                b_baux.setFont(resizedFont);
                b_accueil.setFont(resizedFont);
                b_biens.setFont(resizedFont);
            }
        });


        this.choix_date_fin.getDateEditor().addPropertyChangeListener("date", evt -> modele.getTextFieldDocumentListener().insertUpdate(null));
        this.choix_date_debut.getDateEditor().addPropertyChangeListener("date", evt -> modele.getTextFieldDocumentListener().insertUpdate(null));
        this.choix_loyer.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
        this.choix_loyer.addFocusListener(modele.getFocus());
        this.choix_prevision.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
        this.choix_prevision.addFocusListener(modele.getFocus());
        this.choix_depot_garantie.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
        this.choix_depot_garantie.addFocusListener(modele.getFocus());
        this.choixIcc.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
        this.choixIcc.addFocusListener(modele.getFocus());
        this.choixIndexEau.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
        this.choixIndexEau.addFocusListener(modele.getFocus());
        this.valider.addActionListener(modele.CreationBail());
        this.choix_complement.addActionListener(modele.getSurfaceEtPiece());
        this.choix_ville.addActionListener(modele.getSurfaceEtPiece());
        this.choix_adresse.addActionListener(modele.getSurfaceEtPiece());
        modele.getSurfaceEtPiece().actionPerformed(null);
        this.choix_ville.addActionListener(modele.getVilleActionListener(mapVillesAdresses));
        this.choix_adresse.addActionListener(modele.getAdresseActionListener(mapAdressesComplement));
        quitter.addActionListener(modele.quitterPage());
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

    public JFrame getFrame() {
        return frame;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JComboBox getChoix_complement() {
        return choix_complement;
    }

    public JComboBox getChoix_adresse() {
        return choix_adresse;
    }

    public JDateChooser getChoix_date_fin() {
        return choix_date_fin;
    }

    public JDateChooser getChoix_date_debut() {
        return choix_date_debut;
    }

    public JComboBox getChoix_ville() {
        return choix_ville;
    }

    public JTextField getChoix_loyer() {
        return choix_loyer;
    }

    public JButton getValider() {return valider;}

    public JTextField getChoix_prevision() {
        return choix_prevision;
    }

    public JTextField getChoix_depot_garantie() {
        return choix_depot_garantie;
    }

    public JLabel getChoix_nb_piece() {
        return choix_nb_piece;
    }

    public JLabel getChoix_surface() {
        return choix_surface;
    }

    public JTable getTable(){return this.table;}

    public JCheckBox getSolde_tout_compte() {
        return solde_tout_compte;
    }

    public JTextField getChoixIcc() {
        return choixIcc;
    }

    public JTextField getChoixIndexEau() {
        return choixIndexEau;
    }

}
