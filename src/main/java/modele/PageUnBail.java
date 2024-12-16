package modele;

import DAO.DAOException;
import DAO.jdbc.BienLouableDAO;
import DAO.jdbc.LouerDAO;
import classes.Bail;
import classes.BienLouable;
import classes.Locataire;
import ihm.Charte;
import ihm.Menu;
import ihm.ModelePageBienImmobilier;
import ihm.ResizedImage;

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

        // Panel d'entête pour le logo et le nom de l'appli
        JPanel entete = new JPanel();
        this.frame.getContentPane().add(entete, BorderLayout.NORTH);
        entete.setLayout(new BorderLayout(0, 0));
        this.frame.getContentPane().setBackground(Charte.FOND.getCouleur());

        entete.setBackground(Charte.ENTETE.getCouleur());
        entete.setBorder(new LineBorder(Color.BLACK, 2));

        this.logo = new JLabel("");
        entete.add(this.logo, BorderLayout.WEST);

        ihm.Menu m = new Menu(this.frame);

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
        frame.getContentPane().add(body, BorderLayout.CENTER);
        body.setLayout(new BorderLayout(0, 0));

        JPanel titre = new JPanel();
        FlowLayout fl_titre = (FlowLayout) titre.getLayout();
        body.add(titre, BorderLayout.NORTH);

        JLabel titrePage = new JLabel("Un bail");
        titrePage.setAlignmentY(0.0f);
        titrePage.setAlignmentX(0.5f);
        titre.add(titrePage);

        JPanel panel_locataires = new JPanel();
        body.add(panel_locataires);
        panel_locataires.setLayout(new BorderLayout(0, 0));

        JLabel locataires = new JLabel("Locataires");
        locataires.setHorizontalAlignment(SwingConstants.CENTER);
        panel_locataires.add(locataires, BorderLayout.NORTH);

        List<Integer> idLocataires = new LouerDAO().getIdLoc(new DAO.jdbc.BailDAO().getId(bail));
        String[] nomlocataires = new String[idLocataires.size()];
        int i =0;
        for (int id : idLocataires) {
            Locataire loc = new DAO.jdbc.LocataireDAO().getLocFromId(id);
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
}

