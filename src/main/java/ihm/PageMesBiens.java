package ihm;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import DAO.DAOException;
import modele.ModelePageMesBiens;

public class PageMesBiens extends PageAbstraite {
    private ModelePageMesBiens modele;
    private JTable table;
    private DefaultTableModel table_model;

    /**
     * Create the application.
     */
    public PageMesBiens(int x, int y) {
        super(x,y);
        modele = new ModelePageMesBiens(this);
        CreerSpecific();
    }

    /**
     * Initialize the contents of the frame.
     */
    @Override
    public void CreerSpecific() {
        JPanel titre = new JPanel();
        panel_body.add(titre, BorderLayout.NORTH);

        JLabel label_titre = new JLabel("Mes biens", SwingConstants.CENTER);
        label_titre.setFont(new Font("Arial", Font.BOLD, 16));
        panel_body.add(label_titre, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel_body.add(panel, BorderLayout.CENTER);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[] { 30, 0, 30 };
        gbl_panel.rowHeights = new int[] { 30, 170, 40, 30 };
        gbl_panel.columnWeights = new double[] { 0.0, 1.0 };
        gbl_panel.rowWeights = new double[] { 0.0, 1.0, 1.0 };
        panel.setLayout(gbl_panel);

        try {
            table_model = ModelePageMesBiens.loadDataBienImmoToTable();
            table = new JTable(table_model);
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

        ajouter.addActionListener(modele.ouvrirNouveauBien());

        table.addMouseListener(modele.mouseAdapter(table));


        frame.addWindowListener(modele.fermerFenetre());
    }
}
