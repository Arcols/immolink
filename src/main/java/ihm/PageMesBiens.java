package ihm;

import java.awt.*;

import modele.Menu;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

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
import DAO.jdbc.BatimentDAO;
import DAO.jdbc.BienLouableDAO;
import classes.BienLouable;
import enumeration.TypeLogement;
import modele.*;

public class PageMesBiens {

    private JFrame frame;
    private JLabel logo;
    private JTable table;
    private DefaultTableModel tableModel;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                PageMesBiens window = new PageMesBiens();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    /**
     * Create the application.
     */
    public PageMesBiens() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        // Initialisation du JFrame
        ModelePageMesBiens modele = new ModelePageMesBiens(this);
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

        JLabel titleLabel = new JLabel("Mes biens", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        body.add(titleLabel, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        body.add(panel, BorderLayout.CENTER);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[] { 30, 0, 30 };
        gbl_panel.rowHeights = new int[] { 30, 170, 40, 30 };
        gbl_panel.columnWeights = new double[] { 0.0, 1.0 };
        gbl_panel.rowWeights = new double[] { 0.0, 1.0, 1.0 };
        panel.setLayout(gbl_panel);

        try {
            tableModel = ModelePageMesBiens.loadDataBienImmoToTable();
            table = new JTable(tableModel);
        } catch (SQLException | DAOException e) {
            JOptionPane.showMessageDialog(frame, "Erreur lors du chargement des données : " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(table);
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridx = 1;
        gbc_scrollPane.gridy = 1;
        panel.add(scrollPane, gbc_scrollPane);

        JPanel panel_1 = new JPanel();
        GridBagConstraints gbc_panel_1 = new GridBagConstraints();
        gbc_panel_1.anchor = GridBagConstraints.SOUTH;
        gbc_panel_1.gridx = 1;
        gbc_panel_1.gridy = 2;
        panel.add(panel_1, gbc_panel_1);
        GridBagLayout gbl_panel_1 = new GridBagLayout();
        gbl_panel_1.columnWidths = new int[] { 0 };
        gbl_panel_1.rowHeights = new int[] { 0 };
        gbl_panel_1.columnWeights = new double[] { 0.0, 0.0, 0.0 };
        gbl_panel_1.rowWeights = new double[] { 0.0, 0.0 };
        panel_1.setLayout(gbl_panel_1);

        JPanel bas_de_page = new JPanel();
        this.frame.getContentPane().add(bas_de_page, BorderLayout.SOUTH);
        bas_de_page.setLayout(new BorderLayout(0, 0));

        JButton ajouter = new JButton("Nouveau bien");
        ajouter.setEnabled(true); // Le bouton est maintenant activé
        ajouter.setHorizontalTextPosition(SwingConstants.LEFT);
        ajouter.setVerticalTextPosition(SwingConstants.TOP);
        ajouter.setVerticalAlignment(SwingConstants.BOTTOM);
        bas_de_page.add(ajouter, BorderLayout.EAST);

        this.frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                ResizedImage res = new ResizedImage();
                res.resizeImage("logo+nom.png", PageMesBiens.this.frame,
                        PageMesBiens.this.logo, 3, 8);
                int frameWidth = PageMesBiens.this.frame.getWidth();
                int frameHeight = PageMesBiens.this.frame.getHeight();

                int newFontSize = Math.min(frameWidth, frameHeight) / 30;

                // Appliquer la nouvelle police au bouton
                Font resizedFont = new Font("Arial", Font.PLAIN, newFontSize);
                b_baux.setFont(resizedFont);
                b_accueil.setFont(resizedFont);
                b_biens.setFont(resizedFont);
            }
        });

        ajouter.addActionListener(modele.ouvrirNouveauBien());

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Vérifier s'il s'agit d'un double-clic
                if (evt.getClickCount() == 2) {
                    // Obtenir l'index de la ligne cliquée
                    int row = table.getSelectedRow();

                    // Récupérer les données de la ligne sélectionnée
                    if (row != -1) {
                        String adresse = (String) tableModel.getValueAt(row, 2);
                        String complement = (String) tableModel.getValueAt(row, 3);
                        String ville = (String) tableModel.getValueAt(row, 1);
                        TypeLogement type = TypeLogement.fromString((String) tableModel.getValueAt(row, 0));
                        try {
                            if(type.estBienLouable()) {
                                BienLouable bien = new BienLouableDAO().readFisc(new BienLouableDAO().getFiscFromCompl(ville, adresse, complement));
                                frame.dispose();
                                new PageMonBien(new BienLouableDAO().getId(bien.getNumeroFiscal()),type);
                            }else{
                                frame.dispose();
                                Integer IdBat = new BatimentDAO().getIdBat(ville,adresse);
                                new PageMonBien(IdBat,type);
                            }
                        } catch (DAOException e) {
                            throw new RuntimeException(e);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
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

}
