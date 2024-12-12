package modele;

import DAO.DAOException;
import DAO.jdbc.BienLouableDAO;
import DAO.jdbc.LouerDAO;
import classes.Bail;
import classes.BienLouable;
import classes.Locataire;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.util.List;

import static ihm.Charte.ENTETE;
import static ihm.Charte.FOND;


public class PageUnBail {

    private JFrame frame;
    private JLabel logo;
    private JTextField choix_loyer;
    private JTextField choix_prevision;
    private JTextField choix_depot_garantie;
    private JTable table;
    private DefaultTableModel tableModel;
    private Bail bail;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Bail bail = new Bail(false,"0",0,0,0,new Date(0),new Date(0));
                    modele.PageUnBail window = new modele.PageUnBail(bail);
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
    public PageUnBail(Bail bail) {
        initialize(bail);
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

        JButton b_baux = new JButton("Mes baux");
        b_baux.setBorderPainted(false);
        b_baux.setBackground(ENTETE.getCouleur());
        b_baux.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menu_bouttons.add(b_baux);

        JButton b_loca = new JButton("Locataires");
        b_loca.setBorderPainted(false);
        b_loca.setBackground(ENTETE.getCouleur());
        b_loca.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menu_bouttons.add(b_loca);

        JButton b_biens = new JButton("Mes Biens");
        b_biens.setBorderPainted(false);
        b_biens.setBackground(ENTETE.getCouleur());
        b_biens.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menu_bouttons.add(b_biens);

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
    }
}

