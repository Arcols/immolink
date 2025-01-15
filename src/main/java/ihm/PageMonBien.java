package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import DAO.jdbc.BienLouableDAO;
import DAO.jdbc.DiagnosticDAO;
import DAO.jdbc.GarageDAO;
import classes.Diagnostic;
import classes.Garage;
import enumeration.TypeLogement;
import modele.*;

public class PageMonBien {

    private JFrame frame;
    private JLabel logo;
    private JLabel affichageNumeroFiscal;
    private JLabel affichageNumeroFiscalGarage;
    private JLabel affichageVilleGarage;
    private JLabel affichageAdresseGarage;
    private JLabel affichageComplementGarage;
    private JLabel affichageVille;
    private JLabel affichageAdresse;
    private JLabel affichageComplement;
    private JLabel affichageCoutTravaux;
    private JTable tableDiagnostics;
    private JTable tableTravaux;
    private DefaultTableModel tableModel;
    private JPanel tableau_diagnostic;
    private JLabel diagnostics;
    private JButton garageButton;
    private JButton deleteButton = new JButton();

    /**
     * Create the application.
     */
    public PageMonBien(int idBien,TypeLogement typeLogement) throws DAOException, SQLException {
        initialize(idBien,typeLogement);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize(int idBien,TypeLogement typeLogement) throws DAOException, SQLException {
        this.affichageNumeroFiscal = new JLabel("New label");
        this.affichageVille = new JLabel("New label");
        this.affichageAdresse = new JLabel("New label");
        this.affichageComplement = new JLabel("New label");
        this.affichageCoutTravaux = new JLabel("New label");
        this.tableDiagnostics= new JTable();
        this.frame = new JFrame();
        this.frame.setBounds(0, 0, 1300, 750);
        this.frame.getContentPane().setBackground(Charte.FOND.getCouleur());
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ModelePageMonBien modele = new  ModelePageMonBien(this);

        try {
            // Chargement des données du bien
            modele.chargerDonneesBien(idBien,typeLogement, this);
        } catch (DAOException e) {
            JOptionPane.showMessageDialog(frame, "Erreur lors du chargement des données du bien : " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        // Panel d'entête pour le logo et le nom de l'appli
        JPanel entete = new JPanel();
        this.frame.getContentPane().add(entete, BorderLayout.NORTH);
        entete.setLayout(new BorderLayout(0, 0));
        this.frame.getContentPane().setBackground(Charte.FOND.getCouleur());

        entete.setBackground(Charte.ENTETE.getCouleur());
        entete.setBorder(new LineBorder(Color.BLACK, 2));

        this.logo = new JLabel("");
        entete.add(this.logo, BorderLayout.WEST);

        modele.Menu m = new Menu(this.frame);

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

        JLabel lblNewLabel = new JLabel("");
        String titrePage = TypeLogement.getString(typeLogement);
        if(typeLogement.equals(TypeLogement.BATIMENT)){
            titrePage += " - " + new DAO.jdbc.BatimentDAO().readId(idBien).getAdresse();
        }
        else{
            titrePage += " - " + new BienLouableDAO().readId(idBien).getAdresse();
        }
        lblNewLabel.setText(titrePage);
        lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        body.add(lblNewLabel, BorderLayout.NORTH);

        DefaultTableModel model = new DefaultTableModel();

        JPanel panel_1 = new JPanel();
        body.add(panel_1, BorderLayout.CENTER);
        GridBagLayout gbl_panel_1 = new GridBagLayout();
        gbl_panel_1.columnWidths = new int[] { 114, 550, 250 };
        gbl_panel_1.rowHeights = new int[] { 300};
        gbl_panel_1.columnWeights = new double[] { 0.0, 0.0, 0.0 };
        gbl_panel_1.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
        panel_1.setLayout(gbl_panel_1);

        JPanel panel = new JPanel();
        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.anchor = GridBagConstraints.WEST;
        gbc_panel.insets = new Insets(0, 0, 0, 5);
        gbc_panel.gridx = 0;
        gbc_panel.gridy = 0;
        panel_1.add(panel, gbc_panel);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[] { 0, 0 };
        gbl_panel.rowHeights = new int[] { 0 };
        gbl_panel.columnWeights = new double[] { 0.0, 0.0 };
        gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0 };
        panel.setLayout(gbl_panel);

        JLabel labelNumeroFiscal = new JLabel("Numero fiscal");
        GridBagConstraints gbc_labelNumeroFiscal = new GridBagConstraints();
        gbc_labelNumeroFiscal.anchor = GridBagConstraints.WEST;
        gbc_labelNumeroFiscal.insets = new Insets(0, 0, 5, 5);
        gbc_labelNumeroFiscal.gridx = 0;
        gbc_labelNumeroFiscal.gridy = 0;
        panel.add(labelNumeroFiscal, gbc_labelNumeroFiscal);


        GridBagConstraints gbcaffichageTypeBien = new GridBagConstraints();
        gbcaffichageTypeBien.anchor = GridBagConstraints.WEST;
        gbcaffichageTypeBien.insets = new Insets(0, 0, 5, 5);
        gbcaffichageTypeBien.gridx = 1;
        gbcaffichageTypeBien.gridy = 0;
        panel.add(this.affichageNumeroFiscal, gbcaffichageTypeBien);

        JLabel labelVille = new JLabel("Ville");
        GridBagConstraints gbc_labelVille = new GridBagConstraints();
        gbc_labelVille.anchor = GridBagConstraints.WEST;
        gbc_labelVille.insets = new Insets(0, 0, 5, 5);
        gbc_labelVille.gridx = 0;
        gbc_labelVille.gridy = 1;
        panel.add(labelVille, gbc_labelVille);


        GridBagConstraints gbc_affichageVille = new GridBagConstraints();
        gbc_affichageVille.anchor = GridBagConstraints.WEST;
        gbc_affichageVille.insets = new Insets(0, 0, 5, 5);
        gbc_affichageVille.gridx = 1;
        gbc_affichageVille.gridy = 1;
        panel.add(affichageVille, gbc_affichageVille);

        JLabel labelAdresse = new JLabel("Adresse");
        GridBagConstraints gbc_labelAdresse = new GridBagConstraints();
        gbc_labelAdresse.anchor = GridBagConstraints.WEST;
        gbc_labelAdresse.insets = new Insets(0, 0, 5, 5);
        gbc_labelAdresse.gridx = 0;
        gbc_labelAdresse.gridy = 2;
        panel.add(labelAdresse, gbc_labelAdresse);


        GridBagConstraints gbc_affichageAdresse = new GridBagConstraints();
        gbc_affichageAdresse.anchor = GridBagConstraints.WEST;
        gbc_affichageAdresse.insets = new Insets(0, 0, 5, 5);
        gbc_affichageAdresse.gridx = 1;
        gbc_affichageAdresse.gridy = 2;
        panel.add(this.affichageAdresse, gbc_affichageAdresse);

        JLabel labelComplement = new JLabel("Complement");
        GridBagConstraints gbc_labelComplement = new GridBagConstraints();
        gbc_labelComplement.anchor = GridBagConstraints.WEST;
        gbc_labelComplement.insets = new Insets(0, 0, 5, 5);
        gbc_labelComplement.gridx = 0;
        gbc_labelComplement.gridy = 3;
        panel.add(labelComplement, gbc_labelComplement);


        GridBagConstraints gbc_affichageComplement = new GridBagConstraints();
        gbc_affichageComplement.anchor = GridBagConstraints.WEST;
        gbc_affichageComplement.insets = new Insets(0, 0, 5, 5);
        gbc_affichageComplement.gridx = 1;
        gbc_affichageComplement.gridy = 3;
        panel.add(this.affichageComplement, gbc_affichageComplement);

        JLabel labelCoutTravaux = new JLabel("Coût travaux");
        GridBagConstraints gbc_labelCoutTravaux = new GridBagConstraints();
        gbc_labelCoutTravaux.anchor = GridBagConstraints.WEST;
        gbc_labelCoutTravaux.insets = new Insets(0, 0, 5, 5);
        gbc_labelCoutTravaux.gridx = 0;
        gbc_labelCoutTravaux.gridy = 4;
        panel.add(labelCoutTravaux, gbc_labelCoutTravaux);

        GridBagConstraints gbc_affichageCoutTravaux = new GridBagConstraints();
        gbc_affichageCoutTravaux.anchor = GridBagConstraints.WEST;
        gbc_affichageCoutTravaux.insets = new Insets(0, 0, 5, 5);
        gbc_affichageCoutTravaux.gridx = 1;
        gbc_affichageCoutTravaux.gridy = 4;
        panel.add(this.affichageCoutTravaux, gbc_affichageCoutTravaux);

        if(typeLogement.estBienLouable()){
            garageButton = new JButton();
            GridBagConstraints gbc_buttonGarage = new GridBagConstraints();
            gbc_buttonGarage.anchor = GridBagConstraints.WEST;
            gbc_buttonGarage.insets = new Insets(0, 0, 5, 5);
            gbc_buttonGarage.gridwidth = 2;
            gbc_buttonGarage.gridx = 0;
            gbc_buttonGarage.gridy = 5;
            if(new BienLouableDAO().getTypeFromId(idBien).equals(TypeLogement.MAISON)
                    || new BienLouableDAO().getTypeFromId(idBien).equals(TypeLogement.APPARTEMENT)) {
                if (new BienLouableDAO().haveGarage(idBien)) {
                    Garage garage = new GarageDAO().read(new GarageDAO().readIdGarageFromBien(idBien));

                    JLabel labelMonGarage = new JLabel("Mon Garage");
                    labelMonGarage.setFont(new Font("Arial", Font.PLAIN, 15));
                    GridBagConstraints gbc_labelMonGarage = new GridBagConstraints();
                    gbc_labelMonGarage.anchor = GridBagConstraints.CENTER;
                    gbc_labelMonGarage.insets = new Insets(30, 0, 5, 5);
                    gbc_buttonGarage.gridwidth = 2;
                    gbc_labelMonGarage.gridx = 0;
                    gbc_labelMonGarage.gridy = 6;
                    panel.add(labelMonGarage, gbc_labelMonGarage);

                    JLabel labelNumeroFiscalGarage = new JLabel("Numero fiscal");
                    GridBagConstraints gbc_labelNumeroFiscalGarage = new GridBagConstraints();
                    gbc_labelNumeroFiscalGarage.anchor = GridBagConstraints.WEST;
                    gbc_labelNumeroFiscalGarage.insets = new Insets(0, 0, 5, 5);
                    gbc_labelNumeroFiscalGarage.gridx = 0;
                    gbc_labelNumeroFiscalGarage.gridy = 7;
                    panel.add(labelNumeroFiscalGarage, gbc_labelNumeroFiscalGarage);

                    this.affichageNumeroFiscalGarage = new JLabel(garage.getNumeroFiscal());
                    GridBagConstraints gbcaffichageNumeroFiscalGarage = new GridBagConstraints();
                    gbcaffichageNumeroFiscalGarage.anchor = GridBagConstraints.WEST;
                    gbcaffichageNumeroFiscalGarage.insets = new Insets(0, 0, 5, 5);
                    gbcaffichageNumeroFiscalGarage.gridx = 1;
                    gbcaffichageNumeroFiscalGarage.gridy = 7;
                    panel.add(this.affichageNumeroFiscalGarage, gbcaffichageNumeroFiscalGarage);

                    JLabel labelVilleGarage = new JLabel("Ville");
                    GridBagConstraints gbc_labelVilleGarage = new GridBagConstraints();
                    gbc_labelVilleGarage.anchor = GridBagConstraints.WEST;
                    gbc_labelVilleGarage.insets = new Insets(0, 0, 5, 5);
                    gbc_labelVilleGarage.gridx = 0;
                    gbc_labelVilleGarage.gridy = 8;
                    panel.add(labelVilleGarage, gbc_labelVilleGarage);

                    this.affichageVilleGarage = new JLabel(garage.getVille());
                    GridBagConstraints gbcaffichageVilleGarage = new GridBagConstraints();
                    gbcaffichageVilleGarage.anchor = GridBagConstraints.WEST;
                    gbcaffichageVilleGarage.insets = new Insets(0, 0, 5, 5);
                    gbcaffichageVilleGarage.gridx = 1;
                    gbcaffichageVilleGarage.gridy = 8;
                    panel.add(this.affichageVilleGarage, gbcaffichageVilleGarage);

                    JLabel labelAdresseGarage = new JLabel("Adresse");
                    GridBagConstraints gbc_labelAdresseGarage = new GridBagConstraints();
                    gbc_labelAdresseGarage.anchor = GridBagConstraints.WEST;
                    gbc_labelAdresseGarage.insets = new Insets(0, 0, 5, 5);
                    gbc_labelAdresseGarage.gridx = 0;
                    gbc_labelAdresseGarage.gridy = 9;
                    panel.add(labelAdresseGarage, gbc_labelAdresseGarage);

                    this.affichageAdresseGarage = new JLabel(garage.getAdresse());
                    GridBagConstraints gbcaffichageAdresseGarage = new GridBagConstraints();
                    gbcaffichageAdresseGarage.anchor = GridBagConstraints.WEST;
                    gbcaffichageAdresseGarage.insets = new Insets(0, 0, 5, 5);
                    gbcaffichageAdresseGarage.gridx = 1;
                    gbcaffichageAdresseGarage.gridy = 9;
                    panel.add(this.affichageAdresseGarage, gbcaffichageAdresseGarage);

                    JLabel labelComplémentGarage = new JLabel("Complément");
                    GridBagConstraints gbc_labelComplémentGarage = new GridBagConstraints();
                    gbc_labelComplémentGarage.anchor = GridBagConstraints.WEST;
                    gbc_labelComplémentGarage.insets = new Insets(0, 0, 5, 5);
                    gbc_labelComplémentGarage.gridx = 0;
                    gbc_labelComplémentGarage.gridy = 10;
                    panel.add(labelComplémentGarage, gbc_labelComplémentGarage);

                    this.affichageComplementGarage = new JLabel(garage.getComplementAdresse());
                    GridBagConstraints gbcaffichageComplementGarage = new GridBagConstraints();
                    gbcaffichageComplementGarage.anchor = GridBagConstraints.WEST;
                    gbcaffichageComplementGarage.insets = new Insets(0, 0, 5, 5);
                    gbcaffichageComplementGarage.gridx = 1;
                    gbcaffichageComplementGarage.gridy = 10;
                    panel.add(this.affichageComplementGarage, gbcaffichageComplementGarage);

                    gbc_buttonGarage.gridx = 0;
                    gbc_buttonGarage.gridy = 11;

                    panel.add(garageButton, gbc_buttonGarage);

                    garageButton.setText("Délier mon Garage");
                    garageButton.addActionListener(modele.delierGarage(idBien,typeLogement));
                } else {
                    panel.add(garageButton, gbc_buttonGarage);
                    garageButton.setText("Ajouter Garage");
                    this.garageButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            showGaragePopup(idBien);
                        }
                    });
                }
            }
            else{
                panel.add(garageButton, gbc_buttonGarage);
                garageButton.setText("Lier mon garage");
                this.garageButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        showLierGarageAuBienPopup(idBien);
                    }
                });
            }
        }

        GridBagConstraints gbc_buttonDelete = new GridBagConstraints();
        gbc_buttonDelete.anchor = GridBagConstraints.WEST;
        gbc_buttonDelete.insets = new Insets(0, 0, 5, 5);
        gbc_buttonDelete.gridwidth = 2;
        gbc_buttonDelete.gridx = 0;
        gbc_buttonDelete.gridy = 12;

        panel.add(deleteButton, gbc_buttonDelete);

        deleteButton.setText("Supprimer mon bien");
        deleteButton.addActionListener(modele.deleteBienLouable(frame,idBien,typeLogement));

        JPanel panel_diagnostic = new JPanel();
        GridBagConstraints gbc_diagnostic = new GridBagConstraints();
        gbc_diagnostic.fill = GridBagConstraints.BOTH;
        gbc_diagnostic.anchor = GridBagConstraints.NORTHWEST;
        gbc_diagnostic.insets = new Insets(5, 5, 5, 5);
        gbc_diagnostic.gridx = 1;
        gbc_diagnostic.gridy = 0;
        panel_1.add(panel_diagnostic, gbc_diagnostic);
        panel_diagnostic.setLayout(new BorderLayout(0, 0));
        this.diagnostics = new JLabel("Diagnostics");
        this.diagnostics.setHorizontalAlignment(SwingConstants.CENTER);
        panel_diagnostic.add(this.diagnostics, BorderLayout.NORTH);

        DiagnosticDAO diagnosticDAO = new DiagnosticDAO();
        List<Diagnostic> diagnosticList = diagnosticDAO.readAllDiag(idBien);
        int row = 0;

        String[] nomdiagnostic = new String[diagnosticList.size()];
        for (classes.Diagnostic diagnostic : diagnosticList) {
            String diagnosticName = diagnostic.getReference();
            if (diagnostic.getDateInvalidite() != null) {
                diagnosticName += " - Périme en " + diagnostic.getDateInvalidite().toString();
            }
            nomdiagnostic[row]=diagnosticName;
            row++;
        }

        // Panel principal (avec un défilement si nécessaire)
        this.tableau_diagnostic = new JPanel(new GridBagLayout()); // Remplacer GridLayout par GridBagLayout

        // Créer un GridBagConstraints pour gérer le placement des composants
        GridBagConstraints gbc_diag = new GridBagConstraints();
        gbc_diag.fill = GridBagConstraints.HORIZONTAL;
        gbc_diag.insets = new Insets(5, 5, 5, 5); // Espacement entre les composants

        int rowTab = 0; // Initialiser le compteur de ligne pour GridBagLayout

        for (String diagnostic : nomdiagnostic) {
            // Créer le label pour chaque diagnostic
            JLabel label = new JLabel(diagnostic);
            gbc_diag.gridx = 0; // Première colonne pour le label
            gbc_diag.gridy = rowTab;
            this.tableau_diagnostic.add(label, gbc_diag);

            // Créer le bouton "Importer" pour chaque diagnostic
            JButton bouton = new JButton("Télécharger");
            bouton.addActionListener(modele.openDiag(diagnostic,idBien));
            gbc_diag.gridx = 1; // Deuxième colonne pour le bouton
            this.tableau_diagnostic.add(bouton, gbc_diag);

            // Créer le bouton "Modifier" pour les diagnostiques périmables
            if(diagnosticList.get(rowTab).getDateInvalidite() != null){
                JButton boutonModif = new JButton("Modifier");
                boutonModif.addActionListener(modele.getTelechargerPDFButton(frame,diagnostic,idBien,typeLogement));
                gbc_diag.gridx = 2;
                this.tableau_diagnostic.add(boutonModif, gbc_diag);
            }
            rowTab++; // Incrémenter la ligne pour le prochain diagnostic
        }

        JScrollPane scrollPaneDiag = new JScrollPane(this.tableau_diagnostic);
        panel_diagnostic.add(scrollPaneDiag, BorderLayout.CENTER);

        JScrollPane scrollPane_1 = new JScrollPane();
        GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
        gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
        gbc_scrollPane_1.anchor = GridBagConstraints.NORTHWEST;
        gbc_scrollPane_1.gridx = 2;
        gbc_scrollPane_1.gridy = 0;
        panel_1.add(scrollPane_1, gbc_scrollPane_1);

        this.tableTravaux = new JTable();
        scrollPane_1.setViewportView(this.tableTravaux);
        this.tableTravaux.setModel(model);
        try {
            DefaultTableModel modele2 = ModelePageMonBien.loadDataTravauxToTable(idBien,typeLogement);
            tableTravaux.setModel(modele2);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Erreur lors du chargement des données : " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        JPanel bas_de_page = new JPanel();
        this.frame.getContentPane().add(bas_de_page, BorderLayout.SOUTH);
        bas_de_page.setLayout(new BorderLayout(0, 0));

        JButton quitter = new JButton("Quitter");
        quitter.setEnabled(true); // Le bouton est maintenant activé
        quitter.setHorizontalTextPosition(SwingConstants.LEFT);
        quitter.setVerticalTextPosition(SwingConstants.TOP);
        quitter.setVerticalAlignment(SwingConstants.BOTTOM);
        bas_de_page.add(quitter, BorderLayout.WEST);
        quitter.addActionListener(modele.quitterPage());



        JButton ajouter = new JButton("Nouveau travaux");
        ajouter.setEnabled(true); // Le bouton est maintenant activé
        ajouter.setHorizontalTextPosition(SwingConstants.LEFT);
        ajouter.setVerticalTextPosition(SwingConstants.TOP);
        ajouter.setVerticalAlignment(SwingConstants.BOTTOM);
        bas_de_page.add(ajouter, BorderLayout.EAST);

        ajouter.addActionListener(modele.ouvrirPageNouveauTravaux(idBien,typeLogement));
        this.frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                ResizedImage res = new ResizedImage();
                res.resizeImage("logo+nom.png", PageMonBien.this.frame,
                        PageMonBien.this.logo, 3, 8);
                int frameWidth = PageMonBien.this.frame.getWidth();
                int frameHeight = PageMonBien.this.frame.getHeight();

                int newFontSize = Math.min(frameWidth, frameHeight) / 30;

                // Appliquer la nouvelle police au bouton
                Font resizedFont = new Font("Arial", Font.PLAIN, newFontSize);
                b_baux.setFont(resizedFont);
                b_accueil.setFont(resizedFont);
                b_biens.setFont(resizedFont);
            }
        });
        frame.setVisible(true);

        tableTravaux.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Vérifier s'il s'agit d'un double-clic
                if (evt.getClickCount() == 2) {
                    // Obtenir l'index de la ligne sélectionnée
                    int selectedRow = tableTravaux.getSelectedRow();
                    if (selectedRow != -1) {
                        // Récupérer l'ID du travail correspondant depuis le modèle de table
                        String numDevis = (String) tableTravaux.getValueAt(selectedRow, 0);
                        int idTravail = 0;
                        try {
                            idTravail = new DAO.jdbc.DevisDAO().getId(new DAO.jdbc.DevisDAO().read(numDevis));
                        } catch (DAOException e) {
                            throw new RuntimeException(e);
                        }

                        try {
                            // Ouvrir la page correspondante
                            frame.dispose();
                            new PageUnTravail(idBien,typeLogement, idTravail);
                        } catch (DAOException e) {
                            JOptionPane.showMessageDialog(frame,
                                    "Erreur lors de l'ouverture de la page du travail : " + e.getMessage(),
                                    "Erreur", JOptionPane.ERROR_MESSAGE);
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
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

    public void getAffichageNumeroFiscal(String valeur) {
        this.affichageNumeroFiscal.setText(valeur);
    }

    public JLabel getAffichageVille() {
        return this.affichageVille;
    }

    public JLabel getAffichageAdresse() {
        return this.affichageAdresse;
    }

    public JLabel getAffichageComplement() {
        return this.affichageComplement;
    }

    public JLabel getAffichageCoutTravaux() {
        return this.affichageCoutTravaux;
    }

    private void showGaragePopup(Integer idBien) {
        PopUpLieGarageMonBien popup = new PopUpLieGarageMonBien(this,idBien);
        popup.getFrame().setVisible(true);
    }

    private void showLierGarageAuBienPopup(Integer idBien) {
        PopUpLierGarageAuBien popup = new PopUpLierGarageAuBien(this,idBien);
        popup.getFrame().setVisible(true);
    }
}

