package ihm;

import DAO.DAOException;
import modele.*;
import modele.Menu;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.SQLException;
import java.util.Arrays;

public class PageArchivesFactures {

    private JFrame frame;
    private JLabel logo;
    private int id_bail;
    private DefaultTableModel tableModel;
    private JTable table;

    public PageArchivesFactures(Integer idBail) {
        this.id_bail = idBail;
        this.initialize(idBail);
    }

    private void initialize(Integer idBail) {
        // Initialisation du JFrame
        ModelePageArchivesFactures modele = new ModelePageArchivesFactures(this);
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
        body.add(titre, BorderLayout.NORTH);

        JLabel titleLabel = new JLabel("Liste des factures", SwingConstants.CENTER);
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
            tableModel = modele.loadDataBienImmoToTable();
            table = new JTable(tableModel);
            TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
            table.setRowSorter(sorter);

            sorter.setSortKeys(
                    Arrays.asList(new RowSorter.SortKey(2, SortOrder.DESCENDING))
            );
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

        JButton quitter = new JButton("Quitter");
        quitter.setHorizontalTextPosition(SwingConstants.LEFT);
        quitter.setVerticalTextPosition(SwingConstants.TOP);
        quitter.setVerticalAlignment(SwingConstants.BOTTOM);
        bas_de_page.add(quitter, BorderLayout.WEST);
        quitter.addActionListener(modele.quitterPage(id_bail));

        this.frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                ResizedImage res = new ResizedImage();
                res.resizeImage("logo+nom.png", PageArchivesFactures.this.frame,
                        PageArchivesFactures.this.logo, 3, 8);
                int frameWidth = PageArchivesFactures.this.frame.getWidth();
                int frameHeight = PageArchivesFactures.this.frame.getHeight();

                int newFontSize = Math.min(frameWidth, frameHeight) / 30;

                // Appliquer la nouvelle police au bouton
                Font resizedFont = new Font("Arial", Font.PLAIN, newFontSize);
                b_baux.setFont(resizedFont);
                b_accueil.setFont(resizedFont);
                b_biens.setFont(resizedFont);
            }
        });

        this.frame.setVisible(true);
    }

    public int getId_bail() {
        return id_bail;
    }

    public JFrame getFrame() {
        return frame;
    }
}
