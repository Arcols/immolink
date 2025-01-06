package modele;

import DAO.DAOException;
import DAO.jdbc.BatimentDAO;
import DAO.jdbc.GarageDAO;
import classes.BienLouable;
import classes.Diagnostic;
import classes.Garage;
import enumeration.TypeLogement;
import ihm.Charte;
import ihm.ModelePageMesBiens;
import ihm.ModelePopUpCreationGarageBL;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PopUpCreationGarageLieBL {
    private JFrame frame;
    private PageNouveauBienImmobilier mainPage;
    private DefaultTableModel tableModel;
    private JTable table;

    public PopUpCreationGarageLieBL(PageNouveauBienImmobilier mainPage) {
        this.mainPage = mainPage;
        this.initialize();
    }

    public JFrame getFrame() {
        return this.frame;
    }

    public PopUpCreationGarageLieBL() {
        this.initialize();
    }

    private void initialize(){
        ModelePopUpCreationGarageBL modele = new ModelePopUpCreationGarageBL();
        // Initialisation du JFrame
        this.frame = new JFrame();
        this.frame.setBounds(150, 150, 600, 400);
        this.frame.getContentPane().setBackground(Charte.FOND.getCouleur());
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel body = new JPanel();
        this.frame.getContentPane().add(body, BorderLayout.CENTER);
        body.setLayout(new BorderLayout(0, 0));

        JPanel titre = new JPanel();
        FlowLayout fl_titre = (FlowLayout) titre.getLayout();
        this.frame.getContentPane().add(titre, BorderLayout.NORTH);

        JLabel titrePage = new JLabel("Garage à lier au bien louable");
        titrePage.setFont(new Font("Arial", Font.PLAIN, 25));
        titrePage.setAlignmentY(0.0f);
        titrePage.setAlignmentX(0.5f);
        titre.add(titrePage);

        JPanel panel = new JPanel();
        this.frame.getContentPane().add(panel, BorderLayout.CENTER);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[] { 30, 0, 30 };
        gbl_panel.rowHeights = new int[] { 30, 170, 40, 30 };
        gbl_panel.columnWeights = new double[] { 0.0, 1.0 };
        gbl_panel.rowWeights = new double[] { 0.0, 1.0, 1.0 };
        panel.setLayout(gbl_panel);

        try {
            tableModel = ModelePopUpCreationGarageBL.loadDataGaragesPasAssosToTable();
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

        JButton quitter = new JButton("Quitter");
        quitter.setHorizontalTextPosition(SwingConstants.LEFT);
        quitter.setVerticalTextPosition(SwingConstants.TOP);
        quitter.setVerticalAlignment(SwingConstants.BOTTOM);
        bas_de_page.add(quitter, BorderLayout.WEST);

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                handleTableDoubleClick(evt);
            }
        });
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    private void handleTableDoubleClick(java.awt.event.MouseEvent evt) {
        // Vérifier s'il s'agit d'un double-clic
        if (evt.getClickCount() == 2) {
            // Obtenir l'index de la ligne cliquée
            int row = table.getSelectedRow();

            // Récupérer les données de la ligne sélectionnée
            if (row != -1) {
                String num_fisc = (String) tableModel.getValueAt(row, 0);
                Integer Id_garage = -1;
                try {
                    Id_garage = new GarageDAO().getIdGarage(num_fisc, TypeLogement.GARAGE_PAS_ASSOCIE);
                } catch (DAOException e) {
                    throw new RuntimeException(e);
                }
                Garage garage = null;
                try {
                    garage = new GarageDAO().read(Id_garage);
                } catch (DAOException e) {
                    throw new RuntimeException(e);
                }
                mainPage.addGarage(garage);
                this.frame.dispose();
            }
        }
    }
}
