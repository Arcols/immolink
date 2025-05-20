package ihm;

import DAO.DAOException;
import DAO.jdbc.BienLouableDAO;
import DAO.jdbc.GarageDAO;
import classes.Garage;
import enumeration.TypeLogement;
import modele.Charte;
import modele.ModelePopUpCreationGarageBL;
import modele.ModelePopUpLieGarageMonBien;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;

public class PopUpLieGarageMonBien {
    private JFrame frame;
    private final PageMonBien page_principale;
    private DefaultTableModel table_modele;
    private JTable table;
    private final Integer id_bien;

    public PopUpLieGarageMonBien(PageMonBien page_principale, Integer id_bien) {
        this.page_principale = page_principale;
        this.id_bien = id_bien;
        this.initialize();
    }
    private void initialize() {
        ModelePopUpLieGarageMonBien modele = new ModelePopUpLieGarageMonBien(this);
        // Initialisation du JFrame
        this.frame = new JFrame();
        this.frame.setBounds(150, 150, 600, 400);
        this.frame.getContentPane().setBackground(Charte.FOND.getCouleur());
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel body = new JPanel();
        this.frame.getContentPane().add(body, BorderLayout.CENTER);
        body.setLayout(new BorderLayout(0, 0));

        JPanel titre = new JPanel();
        this.frame.getContentPane().add(titre, BorderLayout.NORTH);

        JLabel titre_page = new JLabel("Garage à lier au bien louable");
        titre_page.setFont(new Font("Arial", Font.PLAIN, 25));
        titre_page.setAlignmentY(0.0f);
        titre_page.setAlignmentX(0.5f);
        titre.add(titre_page);

        JPanel panel = new JPanel();
        this.frame.getContentPane().add(panel, BorderLayout.CENTER);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{30, 0, 30};
        gbl_panel.rowHeights = new int[]{30, 170, 40, 30};
        gbl_panel.columnWeights = new double[]{0.0, 1.0};
        gbl_panel.rowWeights = new double[]{0.0, 1.0, 1.0};
        panel.setLayout(gbl_panel);

        try {
            table_modele = ModelePopUpCreationGarageBL.loadDataGaragesPasAssosToTable();
            table = new JTable(table_modele);
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
        gbl_panel_1.columnWidths = new int[]{0};
        gbl_panel_1.rowHeights = new int[]{0};
        gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0};
        gbl_panel_1.rowWeights = new double[]{0.0, 0.0};
        panel_1.setLayout(gbl_panel_1);
        
        JPanel bas_de_page = new JPanel();
        this.frame.getContentPane().add(bas_de_page, BorderLayout.SOUTH);
        bas_de_page.setLayout(new BorderLayout(0, 0));

        JButton quitter = new JButton("Quitter");
        quitter.setHorizontalTextPosition(SwingConstants.LEFT);
        quitter.setVerticalTextPosition(SwingConstants.TOP);
        quitter.setVerticalAlignment(SwingConstants.BOTTOM);
        bas_de_page.add(quitter, BorderLayout.WEST);
        quitter.addActionListener(modele.quitterPage());

        table.addMouseListener(modele.doubleClick());
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public JTable getTable() {
        return table;
    }

    public DefaultTableModel getTableModel() {
        return table_modele;
    }

    public Integer getIdBien() {
        return id_bien;
    }

    public PageMonBien getMainPage() {
        return page_principale;
    }

    public JFrame getFrame() {
        return frame;
    }
}