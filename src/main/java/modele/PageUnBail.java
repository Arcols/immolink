package modele;

import DAO.DAOException;
import DAO.jdbc.BailDAO;
import DAO.jdbc.BienLouableDAO;
import DAO.jdbc.LocataireDAO;
import DAO.jdbc.LouerDAO;
import classes.Bail;
import classes.BienLouable;
import classes.Locataire;
import ihm.*;
import ihm.Menu;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.Date;
import java.util.List;

import static ihm.Charte.ENTETE;
import static ihm.Charte.FOND;


public class PageUnBail {

    private JFrame frame;
    private JLabel logo;
    private JPanel tableau_diagnostic;
    private JLabel affichageVille;
    private JLabel affichageAdresse;
    private JLabel affichageComplement;
    private JLabel affichageSurface;
    private JLabel affichageLoyer;
    private JLabel affichageProvision;
    private JLabel affichageNbPieces;
    private JLabel affichageGarantie;

    /**
     * Create the application.
     */
    public PageUnBail(Bail bail) {
        this.initialize(bail);
    }

    public JFrame getFrame() {
        return frame;
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize(Bail bail) {
        this.frame = new JFrame();
        this.frame.setBounds(100, 100, 750, 400);
        this.frame.getContentPane().setBackground(FOND.getCouleur());
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ModelePageUnBail modele = new  ModelePageUnBail(this);
        // Panel d'entête pour le logo et le nom de l'appli
        JPanel entete = new JPanel();
        this.frame.getContentPane().add(entete, BorderLayout.NORTH);
        entete.setLayout(new BorderLayout(0, 0));
        this.frame.getContentPane().setBackground(FOND.getCouleur());

        entete.setBackground(ENTETE.getCouleur());
        entete.setBorder(new LineBorder(Color.BLACK, 2));

        this.logo = new JLabel("");
        entete.add(this.logo, BorderLayout.WEST);

        Menu m = new Menu(this.frame);

        JPanel menu_bouttons = new JPanel();

        entete.add(menu_bouttons, BorderLayout.CENTER);
        menu_bouttons.setLayout(new GridLayout(0, 3, 0, 0));
        menu_bouttons.setBackground(ENTETE.getCouleur());

        JButton b_accueil = new JButton("Accueil");
        b_accueil.setBorderPainted(false);
        b_accueil.setBackground(ENTETE.getCouleur());
        b_accueil.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menu_bouttons.add(b_accueil);
        b_accueil.addActionListener(m);

        JButton b_baux = new JButton("Mes baux");
        b_baux.setBorderPainted(false);
        b_baux.setBackground(ENTETE.getCouleur());
        b_baux.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menu_bouttons.add(b_baux);
        menu_bouttons.add(b_baux);
        b_baux.addActionListener(m);

        JButton b_biens = new JButton("Mes Biens");
        b_biens.setBorderPainted(false);
        b_biens.setBackground(ENTETE.getCouleur());
        b_biens.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menu_bouttons.add(b_biens);
        menu_bouttons.add(b_biens);
        b_biens.addActionListener(m);

        JPanel body = new JPanel();
        frame.getContentPane().add(body, BorderLayout.CENTER);
        body.setLayout(new BorderLayout(0, 0));

        JPanel titre = new JPanel();
        FlowLayout fl_titre = (FlowLayout) titre.getLayout();
        body.add(titre, BorderLayout.NORTH);

        JLabel titrePage = new JLabel("Un bail");
        titrePage.setAlignmentY(0.0f);
        titrePage.setAlignmentX(0.5f);
        titre.add(titrePage);

        JPanel panel = new JPanel();
        body.add(panel, BorderLayout.CENTER);
        panel.setLayout(new GridLayout(1, 2, 0, 0));

        JPanel panel_Infos = new JPanel();
        panel.add(panel_Infos);
        GridBagLayout gbl_panel_Infos = new GridBagLayout();
        gbl_panel_Infos.columnWidths = new int[] {0, 0};
        gbl_panel_Infos.rowHeights = new int[] {0};
        gbl_panel_Infos.columnWeights = new double[]{0.0, 0.0, 0.0};
        gbl_panel_Infos.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        panel_Infos.setLayout(gbl_panel_Infos);

        JLabel labelVille = new JLabel("Ville");
        GridBagConstraints gbc_labelVille = new GridBagConstraints();
        gbc_labelVille.anchor = GridBagConstraints.WEST;
        gbc_labelVille.insets = new Insets(0, 0, 5, 5);
        gbc_labelVille.gridx = 0;
        gbc_labelVille.gridy = 0;
        panel_Infos.add(labelVille, gbc_labelVille);

        this.affichageVille = new JLabel("New label");
        GridBagConstraints gbc_affichageVille = new GridBagConstraints();
        gbc_affichageVille.anchor = GridBagConstraints.WEST;
        gbc_affichageVille.insets = new Insets(0, 0, 5, 0);
        gbc_affichageVille.gridx = 2;
        gbc_affichageVille.gridy = 0;
        panel_Infos.add(affichageVille, gbc_affichageVille);

        JLabel labelAdresse = new JLabel("Adresse");
        GridBagConstraints gbc_labelAdresse = new GridBagConstraints();
        gbc_labelAdresse.anchor = GridBagConstraints.WEST;
        gbc_labelAdresse.insets = new Insets(0, 0, 5, 5);
        gbc_labelAdresse.gridx = 0;
        gbc_labelAdresse.gridy = 1;
        panel_Infos.add(labelAdresse, gbc_labelAdresse);

        this.affichageAdresse = new JLabel("New label");
        GridBagConstraints gbc_affichageAdresse = new GridBagConstraints();
        gbc_affichageAdresse.anchor = GridBagConstraints.WEST;
        gbc_affichageAdresse.insets = new Insets(0, 0, 5, 0);
        gbc_affichageAdresse.gridx = 2;
        gbc_affichageAdresse.gridy = 1;
        panel_Infos.add(affichageAdresse, gbc_affichageAdresse);

        JLabel labelComplement = new JLabel("Complement");
        GridBagConstraints gbc_labelComplement = new GridBagConstraints();
        gbc_labelComplement.anchor = GridBagConstraints.WEST;
        gbc_labelComplement.insets = new Insets(0, 0, 5, 5);
        gbc_labelComplement.gridx = 0;
        gbc_labelComplement.gridy = 2;
        panel_Infos.add(labelComplement, gbc_labelComplement);

        this.affichageComplement = new JLabel("New label");
        GridBagConstraints gbc_affichageComplement = new GridBagConstraints();
        gbc_affichageComplement.anchor = GridBagConstraints.WEST;
        gbc_affichageComplement.insets = new Insets(0, 0, 5, 0);
        gbc_affichageComplement.gridx = 2;
        gbc_affichageComplement.gridy = 2;
        panel_Infos.add(affichageComplement, gbc_affichageComplement);

        JLabel labelSurface = new JLabel("Surface habitable");
        GridBagConstraints gbc_labelSurface = new GridBagConstraints();
        gbc_labelSurface.anchor = GridBagConstraints.WEST;
        gbc_labelSurface.insets = new Insets(0, 0, 5, 5);
        gbc_labelSurface.gridx = 0;
        gbc_labelSurface.gridy = 3;
        panel_Infos.add(labelSurface, gbc_labelSurface);

        this.affichageSurface = new JLabel("New label");
        GridBagConstraints gbc_affichageSurface = new GridBagConstraints();
        gbc_affichageSurface.anchor = GridBagConstraints.WEST;
        gbc_affichageSurface.insets = new Insets(0, 0, 5, 0);
        gbc_affichageSurface.gridx = 2;
        gbc_affichageSurface.gridy = 3;
        panel_Infos.add(affichageSurface, gbc_affichageSurface);

        JLabel labelNbPieces = new JLabel("Nombre de piéces");
        GridBagConstraints gbc_labelNbPieces = new GridBagConstraints();
        gbc_labelNbPieces.anchor = GridBagConstraints.WEST;
        gbc_labelNbPieces.insets = new Insets(0, 0, 5, 5);
        gbc_labelNbPieces.gridx = 0;
        gbc_labelNbPieces.gridy = 4;
        panel_Infos.add(labelNbPieces, gbc_labelNbPieces);

        this.affichageNbPieces = new JLabel("New label");
        GridBagConstraints gbc_affichageNbPieces = new GridBagConstraints();
        gbc_affichageNbPieces.anchor = GridBagConstraints.WEST;
        gbc_affichageNbPieces.insets = new Insets(0, 0, 5, 0);
        gbc_affichageNbPieces.gridx = 2;
        gbc_affichageNbPieces.gridy = 4;
        panel_Infos.add(affichageNbPieces, gbc_affichageNbPieces);

        JLabel labelLoyer = new JLabel("Loyer");
        GridBagConstraints gbc_labelLoyer = new GridBagConstraints();
        gbc_labelLoyer.anchor = GridBagConstraints.WEST;
        gbc_labelLoyer.insets = new Insets(0, 0, 5, 5);
        gbc_labelLoyer.gridx = 0;
        gbc_labelLoyer.gridy = 5;
        panel_Infos.add(labelLoyer, gbc_labelLoyer);

        this.affichageLoyer = new JLabel("New label");
        GridBagConstraints gbc_affichageLoyer = new GridBagConstraints();
        gbc_affichageLoyer.anchor = GridBagConstraints.WEST;
        gbc_affichageLoyer.insets = new Insets(0, 0, 5, 0);
        gbc_affichageLoyer.gridx = 2;
        gbc_affichageLoyer.gridy = 5;
        panel_Infos.add(affichageLoyer, gbc_affichageLoyer);

        JLabel labelProvision = new JLabel("Prevision pour charges");
        GridBagConstraints gbc_labelProvision = new GridBagConstraints();
        gbc_labelProvision.anchor = GridBagConstraints.WEST;
        gbc_labelProvision.insets = new Insets(0, 0, 5, 5);
        gbc_labelProvision.gridx = 0;
        gbc_labelProvision.gridy = 6;
        panel_Infos.add(labelProvision, gbc_labelProvision);

        this.affichageProvision = new JLabel("New label");
        GridBagConstraints gbc_affichageProvision = new GridBagConstraints();
        gbc_affichageProvision.anchor = GridBagConstraints.WEST;
        gbc_affichageProvision.insets = new Insets(0, 0, 5, 0);
        gbc_affichageProvision.gridx = 2;
        gbc_affichageProvision.gridy = 6;
        panel_Infos.add(affichageProvision, gbc_affichageProvision);

        JLabel labelGarantie = new JLabel("Dépôt de garantie");
        GridBagConstraints gbc_labelGarantie = new GridBagConstraints();
        gbc_labelGarantie.anchor = GridBagConstraints.WEST;
        gbc_labelGarantie.insets = new Insets(0, 0, 0, 5);
        gbc_labelGarantie.gridx = 0;
        gbc_labelGarantie.gridy = 7;
        panel_Infos.add(labelGarantie, gbc_labelGarantie);

        this.affichageGarantie = new JLabel("New label");
        GridBagConstraints gbc_affichageGarantie = new GridBagConstraints();
        gbc_affichageGarantie.anchor = GridBagConstraints.WEST;
        gbc_affichageGarantie.gridx = 2;
        gbc_affichageGarantie.gridy = 7;
        panel_Infos.add(affichageGarantie, gbc_affichageGarantie);

        JPanel panel_locataires = new JPanel();
        panel.add(panel_locataires);
        panel_locataires.setLayout(new BorderLayout(0, 0));

        JLabel locataires = new JLabel("Locataires");
        locataires.setHorizontalAlignment(SwingConstants.CENTER);
        panel_locataires.add(locataires, BorderLayout.NORTH);

        System.out.println(bail.getFisc_bien());

        List<Integer> idLocataires = new LouerDAO().getIdLoc(new BailDAO().getId(bail));
        String[] nomlocataires = new String[idLocataires.size()];
        int i =0;
        for (int id : idLocataires) {
            Locataire loc = new LocataireDAO().getLocFromId(id);
            nomlocataires[i]=loc.getNom();
            i++;
        }

        // Panel principal (avec un défilement si nécessaire)
        this.tableau_diagnostic = new JPanel(new GridBagLayout()); // Remplacer GridLayout par GridBagLayout

        // Créer un GridBagConstraints pour gérer le placement des composants
        GridBagConstraints gbc_diag = new GridBagConstraints();
        gbc_diag.fill = GridBagConstraints.HORIZONTAL;
        gbc_diag.insets = new Insets(5, 5, 5, 5); // Espacement entre les composants

        int row = 0; // Initialiser le compteur de ligne pour GridBagLayout

        for (String locataire : nomlocataires) {
            // Créer le label pour chaque diagnostic
            JLabel label = new JLabel(locataire);
            gbc_diag.gridx = 0; // Première colonne pour le label
            gbc_diag.gridy = row;
            this.tableau_diagnostic.add(label, gbc_diag);

            // Créer le bouton "Importer" pour chaque diagnostic
            JButton bouton = new JButton("Importer");
            gbc_diag.gridx = 1; // Deuxième colonne pour le bouton
            this.tableau_diagnostic.add(bouton, gbc_diag);

            row++; // Incrémenter la ligne pour le prochain diagnostic
        }

        JScrollPane scrollPane = new JScrollPane(this.tableau_diagnostic);
        panel_locataires.add(scrollPane, BorderLayout.CENTER);

        JPanel basPage = new JPanel();
        body.add(basPage, BorderLayout.SOUTH);
        basPage.setLayout(new GridLayout(2, 0, 0, 0));

        JPanel panelModifs = new JPanel();
        basPage.add(panelModifs);
        GridBagLayout gbl_panelModifs = new GridBagLayout();
        gbl_panelModifs.columnWidths = new int[] {0};
        gbl_panelModifs.rowHeights = new int[] {0};
        gbl_panelModifs.columnWeights = new double[]{0.0, 0.0};
        gbl_panelModifs.rowWeights = new double[]{0.0};
        panelModifs.setLayout(gbl_panelModifs);

        JButton btnModifierLoyer = new JButton("Modifier le loyer");
        GridBagConstraints gbc_btnModifierLoyer = new GridBagConstraints();
        gbc_btnModifierLoyer.insets = new Insets(0, 0, 5, 5);
        gbc_btnModifierLoyer.gridx = 0;
        gbc_btnModifierLoyer.gridy = 0;
        panelModifs.add(btnModifierLoyer, gbc_btnModifierLoyer);

        JButton btnAjoutLocataire = new JButton("Ajouter un locataire");
        GridBagConstraints gbc_btnAjoutLocataire = new GridBagConstraints();
        gbc_btnAjoutLocataire.insets = new Insets(0, 0, 5, 0);
        gbc_btnAjoutLocataire.gridx = 1;
        gbc_btnAjoutLocataire.gridy = 0;
        panelModifs.add(btnAjoutLocataire, gbc_btnAjoutLocataire);

        JPanel panelQuitter = new JPanel();
        basPage.add(panelQuitter);
        FlowLayout fl_panelQuitter = new FlowLayout(FlowLayout.LEFT, 5, 5);
        panelQuitter.setLayout(fl_panelQuitter);

        JButton btnQuitter = new JButton("Quitter");
        btnQuitter.setHorizontalAlignment(SwingConstants.LEFT);
        panelQuitter.add(btnQuitter);

        try {
            // Instanciation du DAO

            // Chargement des données du bien
            modele.chargerDonneesBail(new BailDAO().getId(bail), this);


        } catch (DAOException e) {
            JOptionPane.showMessageDialog(frame, "Erreur lors du chargement des données du bien : " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        frame.setVisible(true);
        this.frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                ResizedImage res = new ResizedImage();
                res.resizeImage("logo+nom.png", PageUnBail.this.frame,
                        PageUnBail.this.logo, 3, 8);
                int frameWidth = PageUnBail.this.frame.getWidth();
                int frameHeight = PageUnBail.this.frame.getHeight();

                int newFontSize = Math.min(frameWidth, frameHeight) / 30;

                // Appliquer la nouvelle police au bouton
                Font resizedFont = new Font("Arial", Font.PLAIN, newFontSize);
                b_baux.setFont(resizedFont);
                b_accueil.setFont(resizedFont);
                b_biens.setFont(resizedFont);
            }
        });
    }
    public JLabel getAffichageVille() {
        return affichageVille;
    }

    public JLabel getAffichageAdresse() {
        return affichageAdresse;
    }

    public JLabel getAffichageComplement() {
        return affichageComplement;
    }

    public JLabel getAffichageSurface() {
        return affichageSurface;
    }

    public JLabel getAffichageLoyer() {
        return affichageLoyer;
    }

    public JLabel getAffichageProvision() {
        return affichageProvision;
    }

    public JLabel getAffichageNbPieces() {
        return affichageNbPieces;
    }

    public JLabel getAffichageGarantie() {
        return affichageGarantie;
    }
}

