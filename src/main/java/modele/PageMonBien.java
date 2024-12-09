package modele;

import DAO.DAOException;
import ihm.*;
import ihm.Menu;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.SQLException;

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

public class PageMonBien {

    private JFrame frame;
    private JLabel logo;
    private JTextField choix_loyer;
    private JTextField choix_prevision;
    private JTextField choix_depot_garantie;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTable tableDiagnostics;
    private JTable tableTravaux;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    PageMonBien window = new PageMonBien(1);
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
    public PageMonBien(int idBien) {
        initialize(idBien);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize(int idBien) {

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

        JLabel lblNewLabel = new JLabel("Mon bien");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        body.add(lblNewLabel, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        body.add(panel, BorderLayout.WEST);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[] {0, 0};
        gbl_panel.rowHeights = new int[] {0};
        gbl_panel.columnWeights = new double[]{0.0, 0.0};
        gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0};
        panel.setLayout(gbl_panel);

        JLabel affichageTypeBien = new JLabel("Type de bien");
        GridBagConstraints gbc_affichageTypeBien = new GridBagConstraints();
        gbc_affichageTypeBien.insets = new Insets(0, 0, 5, 5);
        gbc_affichageTypeBien.gridx = 0;
        gbc_affichageTypeBien.gridy = 0;
        panel.add(affichageTypeBien, gbc_affichageTypeBien);

        JLabel lblNewLabel_2 = new JLabel("New label");
        GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
        gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_2.gridx = 1;
        gbc_lblNewLabel_2.gridy = 0;
        panel.add(lblNewLabel_2, gbc_lblNewLabel_2);

        JLabel labelVille = new JLabel("Ville");
        GridBagConstraints gbc_labelVille = new GridBagConstraints();
        gbc_labelVille.insets = new Insets(0, 0, 5, 5);
        gbc_labelVille.gridx = 0;
        gbc_labelVille.gridy = 1;
        panel.add(labelVille, gbc_labelVille);

        JLabel affichageVille = new JLabel("New label");
        GridBagConstraints gbc_affichageVille = new GridBagConstraints();
        gbc_affichageVille.insets = new Insets(0, 0, 5, 5);
        gbc_affichageVille.gridx = 1;
        gbc_affichageVille.gridy = 1;
        panel.add(affichageVille, gbc_affichageVille);

        JLabel labelAdresse = new JLabel("Adresse");
        GridBagConstraints gbc_labelAdresse = new GridBagConstraints();
        gbc_labelAdresse.insets = new Insets(0, 0, 5, 5);
        gbc_labelAdresse.gridx = 0;
        gbc_labelAdresse.gridy = 2;
        panel.add(labelAdresse, gbc_labelAdresse);

        JLabel affichageAdresse = new JLabel("New label");
        GridBagConstraints gbc_affichageAdresse = new GridBagConstraints();
        gbc_affichageAdresse.insets = new Insets(0, 0, 5, 5);
        gbc_affichageAdresse.gridx = 1;
        gbc_affichageAdresse.gridy = 2;
        panel.add(affichageAdresse, gbc_affichageAdresse);

        JLabel labelComplement = new JLabel("Complement");
        GridBagConstraints gbc_labelComplement = new GridBagConstraints();
        gbc_labelComplement.insets = new Insets(0, 0, 5, 5);
        gbc_labelComplement.gridx = 0;
        gbc_labelComplement.gridy = 3;
        panel.add(labelComplement, gbc_labelComplement);

        JLabel affichageComplement = new JLabel("New label");
        GridBagConstraints gbc_affichageComplement = new GridBagConstraints();
        gbc_affichageComplement.insets = new Insets(0, 0, 5, 5);
        gbc_affichageComplement.gridx = 1;
        gbc_affichageComplement.gridy = 3;
        panel.add(affichageComplement, gbc_affichageComplement);

        JScrollPane scrollPane = new JScrollPane();
        body.add(scrollPane, BorderLayout.CENTER);

        tableDiagnostics = new JTable();
        scrollPane.setViewportView(tableDiagnostics);
        try {
            DefaultTableModel model = ModelePageMonBien.loadDataDiagnosticsToTable(idBien);
            table.setModel(model);
        } catch (SQLException | DAOException e) {
            JOptionPane.showMessageDialog(frame, "Erreur lors du chargement des données : " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }



        JScrollPane scrollPane_1 = new JScrollPane();
        body.add(scrollPane_1, BorderLayout.EAST);

        tableTravaux = new JTable();
        scrollPane_1.setViewportView(tableTravaux);




        this.frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                ResizedImage res = new ResizedImage();
                res.resizeImage("logo+nom.png", PageMonBien.this.frame,
                        PageMonBien
                                .this.logo, 3, 8);
                int frameWidth = PageMonBien.this.frame.getWidth();
                int frameHeight = PageMonBien.this.frame.getHeight();

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
