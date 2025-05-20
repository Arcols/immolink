package ihm;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import DAO.DAOException;
import classes.Diagnostic;
import classes.Garage;
import enumeration.TypeLogement;
import modele.ModelePageMonBien;

public class PageMonBien extends PageAbstraite {
    private ModelePageMonBien modele;
    private int id_bien;
    private TypeLogement type_logement;
    private JLabel affichage_num_fiscal;
    private JLabel affichage_ville;
    private JLabel affichage_adresse;
    private JLabel affichage_complement;
    private JLabel affichage_cout_travaux;
    private JTable table_travaux;
    private final JButton bouton_supprimer = new JButton();

    /**
     * Create the application.
     */
    public PageMonBien(int id_bien,TypeLogement type_logement, int x, int y) throws DAOException, SQLException {
        super(x,y);
        modele = new ModelePageMonBien(this);
        this.id_bien = id_bien;
        this.type_logement = type_logement;
        CreerSpecific();
    }

    /**
     * Initialize the contents of the frame.
     */
    @Override
    public void CreerSpecific() {
        this.affichage_num_fiscal = new JLabel("New label");
        this.affichage_ville = new JLabel("New label");
        this.affichage_adresse = new JLabel("New label");
        this.affichage_complement = new JLabel("New label");
        this.affichage_cout_travaux = new JLabel("New label");
        this.table_travaux= new JTable();

        try {
            // Chargement des données du bien
            modele.chargerDonneesBien();
        } catch (DAOException e) {
            JOptionPane.showMessageDialog(frame, "Erreur lors du chargement des données du bien : " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        JLabel label_titre = new JLabel("");
        String titre_page = TypeLogement.getString(type_logement);

        try {
            titre_page += modele.getAddresse();
        } catch (DAOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        label_titre.setText(titre_page);
        label_titre.setFont(new Font("Arial", Font.PLAIN, 30));
        label_titre.setHorizontalAlignment(SwingConstants.CENTER);
        panel_body.add(label_titre, BorderLayout.NORTH);

        DefaultTableModel model = new DefaultTableModel();

        JPanel panel_1 = new JPanel();
        panel_body.add(panel_1, BorderLayout.CENTER);
        GridBagLayout gbl_panel_1 = new GridBagLayout();
        gbl_panel_1.columnWidths = new int[] { 114, 550, 250 };
        gbl_panel_1.rowHeights = new int[] { 300};
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

        JLabel label_num_fiscal = new JLabel("Numero fiscal");
        GridBagConstraints gbc_labelNumeroFiscal = new GridBagConstraints();
        gbc_labelNumeroFiscal.anchor = GridBagConstraints.WEST;
        gbc_labelNumeroFiscal.insets = new Insets(0, 0, 5, 5);
        gbc_labelNumeroFiscal.gridx = 0;
        gbc_labelNumeroFiscal.gridy = 0;
        panel.add(label_num_fiscal, gbc_labelNumeroFiscal);


        GridBagConstraints gbc_affichage_type_bien = new GridBagConstraints();
        gbc_affichage_type_bien.anchor = GridBagConstraints.WEST;
        gbc_affichage_type_bien.insets = new Insets(0, 0, 5, 5);
        gbc_affichage_type_bien.gridx = 1;
        gbc_affichage_type_bien.gridy = 0;
        panel.add(this.affichage_num_fiscal, gbc_affichage_type_bien);

        JLabel label_ville = new JLabel("Ville");
        GridBagConstraints gbc_labelVille = new GridBagConstraints();
        gbc_labelVille.anchor = GridBagConstraints.WEST;
        gbc_labelVille.insets = new Insets(0, 0, 5, 5);
        gbc_labelVille.gridx = 0;
        gbc_labelVille.gridy = 1;
        panel.add(label_ville, gbc_labelVille);


        GridBagConstraints gbc_affichage_ville = new GridBagConstraints();
        gbc_affichage_ville.anchor = GridBagConstraints.WEST;
        gbc_affichage_ville.insets = new Insets(0, 0, 5, 5);
        gbc_affichage_ville.gridx = 1;
        gbc_affichage_ville.gridy = 1;
        panel.add(affichage_ville, gbc_affichage_ville);

        JLabel label_adresse = new JLabel("Adresse");
        GridBagConstraints gbc_labelAdresse = new GridBagConstraints();
        gbc_labelAdresse.anchor = GridBagConstraints.WEST;
        gbc_labelAdresse.insets = new Insets(0, 0, 5, 5);
        gbc_labelAdresse.gridx = 0;
        gbc_labelAdresse.gridy = 2;
        panel.add(label_adresse, gbc_labelAdresse);


        GridBagConstraints gbc_affichage_adresse = new GridBagConstraints();
        gbc_affichage_adresse.anchor = GridBagConstraints.WEST;
        gbc_affichage_adresse.insets = new Insets(0, 0, 5, 5);
        gbc_affichage_adresse.gridx = 1;
        gbc_affichage_adresse.gridy = 2;
        panel.add(this.affichage_adresse, gbc_affichage_adresse);

        JLabel label_complement = new JLabel("Complement");
        GridBagConstraints gbc_labelComplement = new GridBagConstraints();
        gbc_labelComplement.anchor = GridBagConstraints.WEST;
        gbc_labelComplement.insets = new Insets(0, 0, 5, 5);
        gbc_labelComplement.gridx = 0;
        gbc_labelComplement.gridy = 3;
        panel.add(label_complement, gbc_labelComplement);


        GridBagConstraints gbc_affichage_complement = new GridBagConstraints();
        gbc_affichage_complement.anchor = GridBagConstraints.WEST;
        gbc_affichage_complement.insets = new Insets(0, 0, 5, 5);
        gbc_affichage_complement.gridx = 1;
        gbc_affichage_complement.gridy = 3;
        panel.add(this.affichage_complement, gbc_affichage_complement);

        JLabel label_cout_travaux = new JLabel("Coût travaux");
        GridBagConstraints gbc_labelCoutTravaux = new GridBagConstraints();
        gbc_labelCoutTravaux.anchor = GridBagConstraints.WEST;
        gbc_labelCoutTravaux.insets = new Insets(0, 0, 5, 5);
        gbc_labelCoutTravaux.gridx = 0;
        gbc_labelCoutTravaux.gridy = 4;
        panel.add(label_cout_travaux, gbc_labelCoutTravaux);

        GridBagConstraints gbc_affichage_cout_travaux = new GridBagConstraints();
        gbc_affichage_cout_travaux.anchor = GridBagConstraints.WEST;
        gbc_affichage_cout_travaux.insets = new Insets(0, 0, 5, 5);
        gbc_affichage_cout_travaux.gridx = 1;
        gbc_affichage_cout_travaux.gridy = 4;
        panel.add(this.affichage_cout_travaux, gbc_affichage_cout_travaux);

        if(type_logement.estBienLouable()){
            JButton bouton_garage = new JButton();
            GridBagConstraints gbc_button_garage = new GridBagConstraints();
            gbc_button_garage.anchor = GridBagConstraints.WEST;
            gbc_button_garage.insets = new Insets(0, 0, 5, 5);
            gbc_button_garage.gridwidth = 2;
            gbc_button_garage.gridx = 0;
            gbc_button_garage.gridy = 5;
            if(modele.isMaisonOuAppartement()) {
                if (modele.aUnGarage()) {
                    Garage garage=null;
                    try {
                        garage = modele.getGarage();
                    } catch (DAOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    JLabel label_garage = new JLabel("Mon Garage");
                    label_garage.setFont(new Font("Arial", Font.PLAIN, 15));
                    GridBagConstraints gbc_labelMonGarage = new GridBagConstraints();
                    gbc_labelMonGarage.anchor = GridBagConstraints.CENTER;
                    gbc_labelMonGarage.insets = new Insets(30, 0, 5, 5);
                    gbc_button_garage.gridwidth = 2;
                    gbc_labelMonGarage.gridx = 0;
                    gbc_labelMonGarage.gridy = 6;
                    panel.add(label_garage, gbc_labelMonGarage);

                    JLabel label_numero_fiscal_garage = new JLabel("Numero fiscal");
                    GridBagConstraints gbc_labelNumeroFiscalGarage = new GridBagConstraints();
                    gbc_labelNumeroFiscalGarage.anchor = GridBagConstraints.WEST;
                    gbc_labelNumeroFiscalGarage.insets = new Insets(0, 0, 5, 5);
                    gbc_labelNumeroFiscalGarage.gridx = 0;
                    gbc_labelNumeroFiscalGarage.gridy = 7;
                    panel.add(label_numero_fiscal_garage, gbc_labelNumeroFiscalGarage);

                    JLabel affichage_num_fiscal_garage = new JLabel(garage.getNumeroFiscal());
                    GridBagConstraints gbcaffichageNumeroFiscalGarage = new GridBagConstraints();
                    gbcaffichageNumeroFiscalGarage.anchor = GridBagConstraints.WEST;
                    gbcaffichageNumeroFiscalGarage.insets = new Insets(0, 0, 5, 5);
                    gbcaffichageNumeroFiscalGarage.gridx = 1;
                    gbcaffichageNumeroFiscalGarage.gridy = 7;
                    panel.add(affichage_num_fiscal_garage, gbcaffichageNumeroFiscalGarage);

                    JLabel label_ville_garage = new JLabel("Ville");
                    GridBagConstraints gbc_labelVilleGarage = new GridBagConstraints();
                    gbc_labelVilleGarage.anchor = GridBagConstraints.WEST;
                    gbc_labelVilleGarage.insets = new Insets(0, 0, 5, 5);
                    gbc_labelVilleGarage.gridx = 0;
                    gbc_labelVilleGarage.gridy = 8;
                    panel.add(label_ville_garage, gbc_labelVilleGarage);

                    JLabel affichage_ville_garage = new JLabel(garage.getVille());
                    GridBagConstraints gbcaffichageVilleGarage = new GridBagConstraints();
                    gbcaffichageVilleGarage.anchor = GridBagConstraints.WEST;
                    gbcaffichageVilleGarage.insets = new Insets(0, 0, 5, 5);
                    gbcaffichageVilleGarage.gridx = 1;
                    gbcaffichageVilleGarage.gridy = 8;
                    panel.add(affichage_ville_garage, gbcaffichageVilleGarage);

                    JLabel label_adresse_garage = new JLabel("Adresse");
                    GridBagConstraints gbc_labelAdresseGarage = new GridBagConstraints();
                    gbc_labelAdresseGarage.anchor = GridBagConstraints.WEST;
                    gbc_labelAdresseGarage.insets = new Insets(0, 0, 5, 5);
                    gbc_labelAdresseGarage.gridx = 0;
                    gbc_labelAdresseGarage.gridy = 9;
                    panel.add(label_adresse_garage, gbc_labelAdresseGarage);

                    JLabel affichage_adresse_garage = new JLabel(garage.getAdresse());
                    GridBagConstraints gbcaffichageAdresseGarage = new GridBagConstraints();
                    gbcaffichageAdresseGarage.anchor = GridBagConstraints.WEST;
                    gbcaffichageAdresseGarage.insets = new Insets(0, 0, 5, 5);
                    gbcaffichageAdresseGarage.gridx = 1;
                    gbcaffichageAdresseGarage.gridy = 9;
                    panel.add(affichage_adresse_garage, gbcaffichageAdresseGarage);

                    JLabel label_complement_garage = new JLabel("Complément");
                    GridBagConstraints gbc_labelComplementGarage = new GridBagConstraints();
                    gbc_labelComplementGarage.anchor = GridBagConstraints.WEST;
                    gbc_labelComplementGarage.insets = new Insets(0, 0, 5, 5);
                    gbc_labelComplementGarage.gridx = 0;
                    gbc_labelComplementGarage.gridy = 10;
                    panel.add(label_complement_garage, gbc_labelComplementGarage);

                    JLabel affichage_complement_garage = new JLabel(garage.getComplementAdresse());
                    GridBagConstraints gbcaffichageComplementGarage = new GridBagConstraints();
                    gbcaffichageComplementGarage.anchor = GridBagConstraints.WEST;
                    gbcaffichageComplementGarage.insets = new Insets(0, 0, 5, 5);
                    gbcaffichageComplementGarage.gridx = 1;
                    gbcaffichageComplementGarage.gridy = 10;
                    panel.add(affichage_complement_garage, gbcaffichageComplementGarage);

                    gbc_button_garage.gridx = 0;
                    gbc_button_garage.gridy = 11;

                    panel.add(bouton_garage, gbc_button_garage);

                    bouton_garage.setText("Délier mon Garage");
                    bouton_garage.addActionListener(modele.delierGarage());
                } else {
                    panel.add(bouton_garage, gbc_button_garage);
                    bouton_garage.setText("Ajouter Garage");
                    bouton_garage.addActionListener(modele.ajouterGarage());
                }
            }
            else{
                panel.add(bouton_garage,gbc_button_garage);
                bouton_garage.setText("Lier mon garage");
                bouton_garage.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        modele.showLierGarageAuBienPopup();
                    }
                });
            }
        }

        GridBagConstraints gbc_button_delete = new GridBagConstraints();
        gbc_button_delete.anchor = GridBagConstraints.WEST;
        gbc_button_delete.insets = new Insets(0, 0, 5, 5);
        gbc_button_delete.gridwidth = 2;
        gbc_button_delete.gridx = 0;
        gbc_button_delete.gridy = 12;

        panel.add(bouton_supprimer, gbc_button_delete);

        bouton_supprimer.setText("Supprimer mon bien");
        bouton_supprimer.addActionListener(modele.deleteBienLouable());

        JPanel panel_diagnostic = new JPanel();
        GridBagConstraints gbc_diagnostic = new GridBagConstraints();
        gbc_diagnostic.fill = GridBagConstraints.BOTH;
        gbc_diagnostic.anchor = GridBagConstraints.NORTHWEST;
        gbc_diagnostic.insets = new Insets(5, 5, 5, 5);
        gbc_diagnostic.gridx = 1;
        gbc_diagnostic.gridy = 0;
        panel_1.add(panel_diagnostic, gbc_diagnostic);
        panel_diagnostic.setLayout(new BorderLayout(0, 0));
        JLabel diagnostics = new JLabel("Diagnostics");
        diagnostics.setHorizontalAlignment(SwingConstants.CENTER);
        panel_diagnostic.add(diagnostics, BorderLayout.NORTH);

        List<Diagnostic> diagnosticList = null;
        try {
            diagnosticList = modele.getListDiagnostics();
        } catch (DAOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int row = 0;

        String[] nom_diagnostic = new String[diagnosticList.size()];
        for (classes.Diagnostic diagnostic : diagnosticList) {
            String diagnosticName = diagnostic.getReference();
            if (diagnostic.getDateInvalidite() != null) {
                diagnosticName += " - Périme en " + diagnostic.getDateInvalidite().toString();
            }
            nom_diagnostic[row]=diagnosticName;
            row++;
        }

        // Panel principal (avec un défilement si nécessaire)
        JPanel tableau_diagnostic = new JPanel(new GridBagLayout()); // Remplacer GridLayout par GridBagLayout

        // Créer un GridBagConstraints pour gérer le placement des composants
        GridBagConstraints gbc_diag = new GridBagConstraints();
        gbc_diag.fill = GridBagConstraints.HORIZONTAL;
        gbc_diag.insets = new Insets(5, 5, 5, 5); // Espacement entre les composants

        int rowTab = 0; // Initialiser le compteur de ligne pour GridBagLayout

        for (String diagnostic : nom_diagnostic) {
            // Créer le label pour chaque diagnostic
            JLabel label = new JLabel(diagnostic);
            gbc_diag.gridx = 0; // Première colonne pour le label
            gbc_diag.gridy = rowTab;
            tableau_diagnostic.add(label, gbc_diag);

            // Créer le bouton "Importer" pour chaque diagnostic
            JButton bouton = new JButton("Télécharger");
            bouton.addActionListener(modele.openDiag(diagnostic));
            gbc_diag.gridx = 1; // Deuxième colonne pour le bouton
            tableau_diagnostic.add(bouton, gbc_diag);

            // Créer le bouton "Modifier" pour les diagnostiques périmables
            if(diagnosticList.get(rowTab).getDateInvalidite() != null){
                JButton bouton_modification = new JButton("Modifier");
                bouton_modification.addActionListener(modele.getTelechargerPDFButton(diagnostic));
                gbc_diag.gridx = 2;
                tableau_diagnostic.add(bouton_modification, gbc_diag);
            }
            rowTab++; // Incrémenter la ligne pour le prochain diagnostic
        }

        JScrollPane scrollPane_diagnostic = new JScrollPane(tableau_diagnostic);
        panel_diagnostic.add(scrollPane_diagnostic, BorderLayout.CENTER);

        JScrollPane scrollPane_1 = new JScrollPane();
        GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
        gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
        gbc_scrollPane_1.anchor = GridBagConstraints.NORTHWEST;
        gbc_scrollPane_1.gridx = 2;
        gbc_scrollPane_1.gridy = 0;
        panel_1.add(scrollPane_1, gbc_scrollPane_1);

        this.table_travaux = new JTable();
        scrollPane_1.setViewportView(this.table_travaux);
        this.table_travaux.setModel(model);
        DefaultTableModel modele2;
        try {
            modele2 = ModelePageMonBien.loadDataTravauxToTable(id_bien,type_logement);
            this.table_travaux.setModel(modele2);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DAOException e) {
            e.printStackTrace();
        }
        JPanel bas_de_page = new JPanel();
        this.frame.getContentPane().add(bas_de_page, BorderLayout.SOUTH);
        bas_de_page.setLayout(new BorderLayout(0, 0));

        JButton quitter = new JButton("Quitter");
        quitter.setEnabled(true); // Le bouton est maintenant activé
        quitter.setHorizontalTextPosition(SwingConstants.LEFT);
        quitter.setVerticalTextPosition(SwingConstants.TOP);
        quitter.setVerticalAlignment(SwingConstants.BOTTOM);
        bas_de_page.add(quitter, BorderLayout.WEST);
        quitter.addActionListener(modele.quitterPage());

        JButton ajouter = new JButton("Nouveau travaux");
        ajouter.setEnabled(true); // Le bouton est maintenant activé
        ajouter.setHorizontalTextPosition(SwingConstants.LEFT);
        ajouter.setVerticalTextPosition(SwingConstants.TOP);
        ajouter.setVerticalAlignment(SwingConstants.BOTTOM);
        bas_de_page.add(ajouter, BorderLayout.EAST);

        ajouter.addActionListener(modele.ouvrirPageNouveauTravaux());

        table_travaux.addMouseListener(modele.doubleClick(table_travaux));

        frame.addWindowListener(modele.fermerFenetre());
    }

    public int getId_bien() {
        return id_bien;
    }

    public TypeLogement getType_logement() {
        return type_logement;
    }

    public JFrame getFrame() {
        return frame;
    }
    
    public void getAffichageNumeroFiscal(String valeur) {
        this.affichage_num_fiscal.setText(valeur);
    }

    public JLabel getAffichageVille() {
        return this.affichage_ville;
    }

    public JLabel getAffichageAdresse() {
        return this.affichage_adresse;
    }

    public JLabel getAffichageComplement() {
        return this.affichage_complement;
    }

    public JLabel getAffichageCoutTravaux() {
        return this.affichage_cout_travaux;
    }


}

