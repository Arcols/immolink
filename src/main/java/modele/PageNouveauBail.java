package modele;

import ihm.ModelePageNouveauBail;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
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
        ModelePageNouveauBail modele = new ModelePageNouveauBail(this);

        this.frame = new JFrame();
        this.frame.setBounds(100, 100, 750, 400);
        this.frame.getContentPane().setBackground(FOND.getCouleur());
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel d'entête pour le logo et le nom de l'appli
        JPanel entete = new JPanel();
        this.frame.getContentPane().add(entete, BorderLayout.NORTH);
        entete.setLayout(new BorderLayout(0, 0));

        entete.setBackground(ENTETE.getCouleur());
        entete.setBorder(new LineBorder(Color.BLACK, 2));
        // Label pour le logo (Image)
        this.logo = new JLabel("");
        entete.add(this.logo, BorderLayout.WEST);
        JPanel menu_bouttons = new JPanel();

        entete.add(menu_bouttons, BorderLayout.CENTER);
        menu_bouttons.setLayout(new GridLayout(0, 5, 0, 0));
        menu_bouttons.setBackground(ENTETE.getCouleur());

        JButton b_accueil = new JButton("Accueil");
        b_accueil.setBorderPainted(false);
        b_accueil.setBackground(ENTETE.getCouleur());
        b_accueil.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menu_bouttons.add(b_accueil);

        JButton b_profil = new JButton("Profil");
        b_profil.setBorderPainted(false);
        b_profil.setBackground(ENTETE.getCouleur());
        b_profil.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menu_bouttons.add(b_profil);
        menu_bouttons.add(b_profil);

        JButton b_baux = new JButton("Mes baux");
        b_baux.setBorderPainted(false);
        b_baux.setBackground(ENTETE.getCouleur());
        b_baux.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menu_bouttons.add(b_baux);
        menu_bouttons.add(b_baux);

        JButton b_loca = new JButton("Locataires");
        b_loca.setBorderPainted(false);
        b_loca.setBackground(ENTETE.getCouleur());
        b_loca.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menu_bouttons.add(b_loca);
        menu_bouttons.add(b_loca);

        JButton b_biens = new JButton("Mes Biens");
        b_biens.setBorderPainted(false);
        b_biens.setBackground(ENTETE.getCouleur());
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
        panel_bien.setLayout(new GridLayout(0, 2, 0, 0));

        JLabel type_de_bien = new JLabel("Type de bien");
        panel_bien.add(type_de_bien);

        JComboBox choix_type_de_bien = new JComboBox();
        panel_bien.add(choix_type_de_bien);

        JLabel ville = new JLabel("Ville");
        panel_bien.add(ville);

        JComboBox choix_ville = new JComboBox();
        panel_bien.add(choix_ville);

        JLabel Adresse = new JLabel("Adresse");
        panel_bien.add(Adresse);

        JComboBox choix_adresse = new JComboBox();
        panel_bien.add(choix_adresse);

        JLabel complement = new JLabel("Complément");
        panel_bien.add(complement);

        JComboBox choix_complement = new JComboBox();
        panel_bien.add(choix_complement);

        JLabel surface = new JLabel("Surface habitable");
        panel_bien.add(surface);

        JLabel choix_surface = new JLabel("New label");
        panel_bien.add(choix_surface);

        JLabel nb_piece = new JLabel("Nombre de pièces");
        panel_bien.add(nb_piece);

        JLabel choix_nb_piece = new JLabel("New label");
        panel_bien.add(choix_nb_piece);

        JLabel loyer = new JLabel("Loyer");
        panel_bien.add(loyer);

        choix_loyer = new JTextField();
        panel_bien.add(choix_loyer);
        choix_loyer.setColumns(10);

        JLabel prevision = new JLabel("Prévision pour charge");
        panel_bien.add(prevision);

        choix_prevision = new JTextField();
        panel_bien.add(choix_prevision);
        choix_prevision.setColumns(10);

        JLabel depot_garantie = new JLabel("Dépôt de garantie");
        panel_bien.add(depot_garantie);

        choix_depot_garantie = new JTextField();
        panel_bien.add(choix_depot_garantie);
        choix_depot_garantie.setColumns(10);

        JPanel panel_east = new JPanel();
        body.add(panel_east, BorderLayout.CENTER);
        panel_east.setLayout(new GridLayout(2, 0, 0, 0));

        JPanel panel_locataire = new JPanel();
        panel_east.add(panel_locataire);
        panel_locataire.setLayout(new BorderLayout(0, 0));

        tableModel = new DefaultTableModel(new String[] { "Prénom", "Nom", "Téléphone" }, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel_locataire.add(scrollPane, BorderLayout.CENTER);

        JButton btn_ajouter_locataire = new JButton("Ajouter un locataire");
        panel_locataire.add(btn_ajouter_locataire, BorderLayout.SOUTH);

        JPanel panel_date = new JPanel();
        panel_east.add(panel_date);
        panel_date.setLayout(new GridLayout(0, 2, 0, 0));

        JLabel date_debut = new JLabel("Date début");
        panel_date.add(date_debut);

        JFormattedTextField choix_date_debut = new JFormattedTextField();
        panel_date.add(choix_date_debut);

        JLabel date_fin = new JLabel("Date fin");
        panel_date.add(date_fin);

        JFormattedTextField choix_date_fin = new JFormattedTextField();
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

        btn_ajouter_locataire.addActionListener(modele.getAjouterLocataire());

    }

    public JFrame getFrame() {
        return frame;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

}
