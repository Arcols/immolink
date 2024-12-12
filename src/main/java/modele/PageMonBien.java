package modele;

import DAO.DAOException;
import DAO.jdbc.LogementDAO;
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
    private JLabel affichageNumeroFiscal;
    private JLabel affichageVille;
    private JLabel affichageAdresse;
    private JLabel affichageComplement;
    private JTable tableDiagnostics;
    private JTable tableTravaux;
    private DefaultTableModel tableModel;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    PageMonBien window = new PageMonBien(95);
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
    public PageMonBien(int idBien) throws DAOException, SQLException {
        initialize(idBien);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize(int idBien) throws DAOException, SQLException {
        this.affichageNumeroFiscal = new JLabel("New label");
        this.affichageVille = new JLabel("New label");
        this.affichageAdresse = new JLabel("New label");
        this.affichageComplement = new JLabel("New label");
        this.tableDiagnostics= new JTable();

        this.frame = new JFrame();
        this.frame.setBounds(100, 100, 750, 400);
        this.frame.getContentPane().setBackground(Charte.FOND.getCouleur());
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Menu m = new Menu(this.frame);

        try {
            // Instanciation du DAO et du modèle
            ModelePageMonBien modele = new ModelePageMonBien();

            // Chargement des données du bien
            modele.chargerDonneesBien(idBien, this);

        } catch (DAOException e) {
            JOptionPane.showMessageDialog(frame, "Erreur lors du chargement des données du bien : " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

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
        try {
            DefaultTableModel modelDiagnostics = ModelePageMonBien.loadDataDiagnosticsToTable(idBien);
            this.tableDiagnostics.setModel(modelDiagnostics);
        } catch (SQLException | DAOException e) {
            JOptionPane.showMessageDialog(frame, "Erreur lors du chargement des données : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        DefaultTableModel model = new DefaultTableModel();

        JPanel panel_1 = new JPanel();
        body.add(panel_1, BorderLayout.CENTER);
        GridBagLayout gbl_panel_1 = new GridBagLayout();
        gbl_panel_1.columnWidths = new int[] { 114, 250, 250 };
        gbl_panel_1.rowHeights = new int[] { 119};
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

        JScrollPane scrollPane = new JScrollPane();
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.anchor = GridBagConstraints.NORTHWEST;
        gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
        gbc_scrollPane.gridx = 1;
        gbc_scrollPane.gridy = 0;
        panel_1.add(scrollPane, gbc_scrollPane);

        this.tableDiagnostics = new JTable();
        scrollPane.setViewportView(this.tableDiagnostics);
        this.tableDiagnostics.setModel(model);

        try {
            DefaultTableModel modelDiagnostics = ModelePageMonBien.loadDataDiagnosticsToTable(idBien);
            this.tableDiagnostics.setModel(modelDiagnostics);
        } catch (SQLException | DAOException e) {
            JOptionPane.showMessageDialog(frame, "Erreur lors du chargement des diagnostics : " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

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
            DefaultTableModel modele2 = ModelePageMonBien.loadDataTravauxToTable();
            tableTravaux.setModel(modele2);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Erreur lors du chargement des données : " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
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

    public void setAffichageNumeroFiscal(String valeur) {
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


}
