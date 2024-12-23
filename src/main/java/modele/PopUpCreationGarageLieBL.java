package modele;

import DAO.jdbc.BatimentDAO;
import classes.Diagnostic;
import classes.Garage;
import enumeration.TypeLogement;
import ihm.Charte;
import ihm.ModelePopUpCreationGarageBL;

import javax.swing.*;
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
    private JPanel panel_caracteristique;
    private JLabel logo;
    private JLabel complement_adresse;
    private JFormattedTextField choix_num_fiscal;
    private JTextField choix_complement_adresse;
    private JButton valider;
    private JComboBox choix_adresse;
    private JComboBox choix_ville;
    private JTextField texte_ville = new JTextField();
    private JTextField texte_adresse = new JTextField();
    private Set<String> setVilles;
    private Map<String, List<String>> mapVillesAdresses;
    private JLabel type_de_bien;

    public PopUpCreationGarageLieBL(PageNouveauBienImmobilier mainPage) { // Modify the constructor
        this.mainPage = mainPage;
        this.initialize();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    PopUpCreationGarageLieBL window = new PopUpCreationGarageLieBL();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public JFrame getFrame() {
        return this.frame;
    }

    public PopUpCreationGarageLieBL() {
        this.initialize();
    }

    private void initialize(){
        ModelePopUpCreationGarageBL modele = new ModelePopUpCreationGarageBL();
        try {
            BatimentDAO batimentDAO = new BatimentDAO();
            this.mapVillesAdresses = batimentDAO.searchAllBatiments();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // Initialisation du JFrame
        this.frame = new JFrame();
        this.frame.setBounds(100, 100, 750, 400);
        this.frame.getContentPane().setBackground(Charte.FOND.getCouleur());
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel body = new JPanel();
        this.frame.getContentPane().add(body, BorderLayout.CENTER);
        body.setLayout(new BorderLayout(0, 0));

        JPanel titre = new JPanel();
        FlowLayout fl_titre = (FlowLayout) titre.getLayout();
        body.add(titre, BorderLayout.NORTH);

        JLabel titrePage = new JLabel("Garage à lier au bien louable");
        titrePage.setFont(new Font("Arial", Font.PLAIN, 25));

        titrePage.setAlignmentY(0.0f);
        titrePage.setAlignmentX(0.5f);
        titre.add(titrePage);

        JPanel contenu = new JPanel();
        body.add(contenu, BorderLayout.CENTER);
        contenu.setLayout(new GridLayout(1, 2, 0, 0));
        this.panel_caracteristique = new JPanel();
        contenu.add(this.panel_caracteristique);
        GridBagLayout gbl_panel_caracteristique = new GridBagLayout();
        gbl_panel_caracteristique.columnWidths = new int[] { 0 };
        gbl_panel_caracteristique.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
        gbl_panel_caracteristique.columnWeights = new double[] { 0.0, 0.0 };
        gbl_panel_caracteristique.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
        this.panel_caracteristique.setLayout(gbl_panel_caracteristique);

        JLabel num_fiscal = new JLabel("Numéro Fiscal");
        GridBagConstraints gbc_num_fiscal = new GridBagConstraints();
        gbc_num_fiscal.fill = GridBagConstraints.BOTH;
        gbc_num_fiscal.insets = new Insets(0, 0, 5, 5);
        gbc_num_fiscal.gridx = 0;
        gbc_num_fiscal.gridy = 0;
        this.panel_caracteristique.add(num_fiscal, gbc_num_fiscal);

        this.choix_num_fiscal = new JFormattedTextField();
        this.choix_num_fiscal.setColumns(12);
        this.choix_num_fiscal.setDocument(new PlainDocument() {
            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if (str == null || getLength() + str.length() <= 12) {
                    super.insertString(offs, str, a);
                }
            }
        });
        GridBagConstraints gbc_choix_num_fiscal = new GridBagConstraints();
        gbc_choix_num_fiscal.fill = GridBagConstraints.HORIZONTAL;
        gbc_choix_num_fiscal.insets = new Insets(0, 0, 5, 0);
        gbc_choix_num_fiscal.gridx = 1;
        gbc_choix_num_fiscal.gridy = 0;
        this.panel_caracteristique.add(this.choix_num_fiscal, gbc_choix_num_fiscal);

        // Ajout des listeners sur chaque champ de texte

        JLabel ville = new JLabel("Ville");
        GridBagConstraints gbc_ville = new GridBagConstraints();
        gbc_ville.fill = GridBagConstraints.BOTH;
        gbc_ville.insets = new Insets(0, 0, 5, 5);
        gbc_ville.gridx = 0;
        gbc_ville.gridy = 1;
        this.panel_caracteristique.add(ville, gbc_ville);

        this.choix_ville = new JComboBox();
        GridBagConstraints gbc_choix_ville = new GridBagConstraints();
        gbc_choix_ville.fill = GridBagConstraints.HORIZONTAL;
        gbc_choix_ville.insets = new Insets(0, 0, 5, 0);
        gbc_choix_ville.gridx = 1;
        gbc_choix_ville.gridy = 1;
        this.panel_caracteristique.add(choix_ville, gbc_choix_ville);
        this.setVilles = this.mapVillesAdresses.keySet();
        if (!this.setVilles.isEmpty()) {
            choix_ville.setModel(new DefaultComboBoxModel(this.setVilles.toArray(new String[0])));
        } else {
            choix_ville.setModel(new DefaultComboBoxModel());
        }

        JLabel adresse = new JLabel("Adresse");
        GridBagConstraints gbc_adresse = new GridBagConstraints();
        gbc_adresse.fill = GridBagConstraints.BOTH;
        gbc_adresse.insets = new Insets(0, 0, 5, 5);
        gbc_adresse.gridx = 0;
        gbc_adresse.gridy = 2;
        this.panel_caracteristique.add(adresse, gbc_adresse);

        this.choix_adresse = new JComboBox();
        GridBagConstraints gbc_choix_adresse = new GridBagConstraints();
        gbc_choix_adresse.fill = GridBagConstraints.HORIZONTAL;
        gbc_choix_adresse.insets = new Insets(0, 0, 5, 0);
        gbc_choix_adresse.gridx = 1;
        gbc_choix_adresse.gridy = 2;
        this.panel_caracteristique.add(this.choix_adresse, gbc_choix_adresse);
        if (this.setVilles.isEmpty()) {
            this.choix_adresse.setModel(new DefaultComboBoxModel());
        } else {
            this.choix_adresse.setModel(new DefaultComboBoxModel(
                    this.mapVillesAdresses.get(this.choix_ville.getSelectedItem()).toArray(new String[0])));
        }

        this.complement_adresse = new JLabel("Complément d'adresse");
        GridBagConstraints gbc_complement_adresse = new GridBagConstraints();
        gbc_complement_adresse.fill = GridBagConstraints.BOTH;
        gbc_complement_adresse.insets = new Insets(0, 0, 5, 5);
        gbc_complement_adresse.gridx = 0;
        gbc_complement_adresse.gridy = 3;
        this.panel_caracteristique.add(this.complement_adresse, gbc_complement_adresse);

        this.choix_complement_adresse = new JTextField();
        GridBagConstraints gbc_choix_complement_adresse = new GridBagConstraints();
        gbc_choix_complement_adresse.fill = GridBagConstraints.HORIZONTAL;
        gbc_choix_complement_adresse.insets = new Insets(0, 0, 5, 0);
        gbc_choix_complement_adresse.gridx = 1;
        gbc_choix_complement_adresse.gridy = 3;
        this.panel_caracteristique.add(this.choix_complement_adresse, gbc_choix_complement_adresse);
        this.choix_complement_adresse.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        this.choix_complement_adresse.setAlignmentX(Component.RIGHT_ALIGNMENT);
        this.choix_complement_adresse.setColumns(10);

        JPanel bas_de_page = new JPanel();
        this.frame.getContentPane().add(bas_de_page, BorderLayout.SOUTH);
        bas_de_page.setLayout(new BorderLayout(0, 0));

        this.valider = new JButton("Valider");
        this.valider.setEnabled(true);
        this.valider.setHorizontalTextPosition(SwingConstants.LEFT);
        this.valider.setVerticalTextPosition(SwingConstants.TOP);
        this.valider.setVerticalAlignment(SwingConstants.BOTTOM);
        bas_de_page.add(this.valider, BorderLayout.EAST);

        JButton quitter = new JButton("Quitter");
        quitter.setHorizontalTextPosition(SwingConstants.LEFT);
        quitter.setVerticalTextPosition(SwingConstants.TOP);
        quitter.setVerticalAlignment(SwingConstants.BOTTOM);
        bas_de_page.add(quitter, BorderLayout.WEST);

        this.valider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Garage garage = createGarage();
                // Send the garage object to the previous page
                // Assuming you have a method in the previous page to handle the new garage
                mainPage.addGarage(garage);
                // Close the popup
                frame.dispose();
            }
        });
    }
    private Garage createGarage() {
        String numeroFiscal = choix_num_fiscal.getText();
        String ville = (String) choix_ville.getSelectedItem();
        String adresse = (String) choix_adresse.getSelectedItem();
        String complementAdresse = choix_complement_adresse.getText();
        TypeLogement typeLogement = TypeLogement.GARAGE_ASSOCIE;

        return new Garage(numeroFiscal, ville, adresse, complementAdresse, typeLogement);
    }
}
