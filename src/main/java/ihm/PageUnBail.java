package ihm;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.sql.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import DAO.DAOException;
import classes.Bail;
import modele.ModelePageUnBail;


public class PageUnBail extends PageAbstraite {

    private GridBagConstraints gbc_loc;
    private JPanel tableau_locataire;
    private JLabel affichage_ville;
    private JLabel affichage_adresse;
    private JLabel affichage_complement;
    private JLabel affichage_surface;
    private JLabel affichage_loyer;
    private JLabel affichage_provision;
    private JLabel affichage_nb_pieces;
    private JLabel affichage_garantie;
    private Bail bail;
    private ModelePageUnBail modele;
    
    /**
     * Create the application.
     */
    public PageUnBail(Bail bail, int x, int y) {
        super(x,y);
        this.bail = bail;
        this.modele = new  ModelePageUnBail(this);
        this.CreerSpecific();
    }


    @Override
    public void CreerSpecific(){
        JPanel panel = new JPanel();
        panel_body.add(panel, BorderLayout.CENTER);
        panel.setLayout(new GridLayout(1, 2, 0, 0));

        JPanel panel_infos = new JPanel();
        panel.add(panel_infos);
        GridBagLayout gbl_panel_Infos = new GridBagLayout();
        gbl_panel_Infos.columnWidths = new int[] {0, 0};
        gbl_panel_Infos.rowHeights = new int[] {0};
        gbl_panel_Infos.columnWeights = new double[]{0.0, 0.0, 0.0};
        gbl_panel_Infos.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        panel_infos.setLayout(gbl_panel_Infos);

        JLabel label_ville = new JLabel("Ville");
        GridBagConstraints gbc_labelVille = new GridBagConstraints();
        gbc_labelVille.anchor = GridBagConstraints.WEST;
        gbc_labelVille.insets = new Insets(0, 0, 5, 5);
        gbc_labelVille.gridx = 0;
        gbc_labelVille.gridy = 0;
        panel_infos.add(label_ville, gbc_labelVille);

        this.affichage_ville = new JLabel("New label");
        GridBagConstraints gbc_affichageVille = new GridBagConstraints();
        gbc_affichageVille.anchor = GridBagConstraints.WEST;
        gbc_affichageVille.insets = new Insets(0, 0, 5, 0);
        gbc_affichageVille.gridx = 2;
        gbc_affichageVille.gridy = 0;
        panel_infos.add(affichage_ville, gbc_affichageVille);

        JLabel label_adresse = new JLabel("Adresse");
        GridBagConstraints gbc_labelAdresse = new GridBagConstraints();
        gbc_labelAdresse.anchor = GridBagConstraints.WEST;
        gbc_labelAdresse.insets = new Insets(0, 0, 5, 5);
        gbc_labelAdresse.gridx = 0;
        gbc_labelAdresse.gridy = 1;
        panel_infos.add(label_adresse, gbc_labelAdresse);

        this.affichage_adresse = new JLabel("New label");
        GridBagConstraints gbc_affichageAdresse = new GridBagConstraints();
        gbc_affichageAdresse.anchor = GridBagConstraints.WEST;
        gbc_affichageAdresse.insets = new Insets(0, 0, 5, 0);
        gbc_affichageAdresse.gridx = 2;
        gbc_affichageAdresse.gridy = 1;
        panel_infos.add(affichage_adresse, gbc_affichageAdresse);

        JLabel label_complement = new JLabel("Complement");
        GridBagConstraints gbc_labelComplement = new GridBagConstraints();
        gbc_labelComplement.anchor = GridBagConstraints.WEST;
        gbc_labelComplement.insets = new Insets(0, 0, 5, 5);
        gbc_labelComplement.gridx = 0;
        gbc_labelComplement.gridy = 2;
        panel_infos.add(label_complement, gbc_labelComplement);

        this.affichage_complement = new JLabel("New label");
        GridBagConstraints gbc_affichageComplement = new GridBagConstraints();
        gbc_affichageComplement.anchor = GridBagConstraints.WEST;
        gbc_affichageComplement.insets = new Insets(0, 0, 5, 0);
        gbc_affichageComplement.gridx = 2;
        gbc_affichageComplement.gridy = 2;
        panel_infos.add(affichage_complement, gbc_affichageComplement);

        JLabel label_surface = new JLabel("Surface habitable");
        GridBagConstraints gbc_labelSurface = new GridBagConstraints();
        gbc_labelSurface.anchor = GridBagConstraints.WEST;
        gbc_labelSurface.insets = new Insets(0, 0, 5, 5);
        gbc_labelSurface.gridx = 0;
        gbc_labelSurface.gridy = 3;
        panel_infos.add(label_surface, gbc_labelSurface);

        this.affichage_surface = new JLabel("New label");
        GridBagConstraints gbc_affichageSurface = new GridBagConstraints();
        gbc_affichageSurface.anchor = GridBagConstraints.WEST;
        gbc_affichageSurface.insets = new Insets(0, 0, 5, 0);
        gbc_affichageSurface.gridx = 2;
        gbc_affichageSurface.gridy = 3;
        panel_infos.add(affichage_surface, gbc_affichageSurface);

        JLabel label_nb_pieces = new JLabel("Nombre de piéces");
        GridBagConstraints gbc_labelNbPieces = new GridBagConstraints();
        gbc_labelNbPieces.anchor = GridBagConstraints.WEST;
        gbc_labelNbPieces.insets = new Insets(0, 0, 5, 5);
        gbc_labelNbPieces.gridx = 0;
        gbc_labelNbPieces.gridy = 4;
        panel_infos.add(label_nb_pieces, gbc_labelNbPieces);

        this.affichage_nb_pieces = new JLabel("New label");
        GridBagConstraints gbc_affichageNbPieces = new GridBagConstraints();
        gbc_affichageNbPieces.anchor = GridBagConstraints.WEST;
        gbc_affichageNbPieces.insets = new Insets(0, 0, 5, 0);
        gbc_affichageNbPieces.gridx = 2;
        gbc_affichageNbPieces.gridy = 4;
        panel_infos.add(affichage_nb_pieces, gbc_affichageNbPieces);

        JLabel label_loyer = new JLabel("Loyer");
        GridBagConstraints gbc_labelLoyer = new GridBagConstraints();
        gbc_labelLoyer.anchor = GridBagConstraints.WEST;
        gbc_labelLoyer.insets = new Insets(0, 0, 5, 5);
        gbc_labelLoyer.gridx = 0;
        gbc_labelLoyer.gridy = 5;
        panel_infos.add(label_loyer, gbc_labelLoyer);

        this.affichage_loyer = new JLabel("New label");
        GridBagConstraints gbc_affichageLoyer = new GridBagConstraints();
        gbc_affichageLoyer.anchor = GridBagConstraints.WEST;
        gbc_affichageLoyer.insets = new Insets(0, 0, 5, 0);
        gbc_affichageLoyer.gridx = 2;
        gbc_affichageLoyer.gridy = 5;
        panel_infos.add(affichage_loyer, gbc_affichageLoyer);

        JLabel label_provision = new JLabel("Prevision pour charges");
        GridBagConstraints gbc_labelProvision = new GridBagConstraints();
        gbc_labelProvision.anchor = GridBagConstraints.WEST;
        gbc_labelProvision.insets = new Insets(0, 0, 5, 5);
        gbc_labelProvision.gridx = 0;
        gbc_labelProvision.gridy = 6;
        panel_infos.add(label_provision, gbc_labelProvision);

        this.affichage_provision = new JLabel("New label");
        GridBagConstraints gbc_affichageProvision = new GridBagConstraints();
        gbc_affichageProvision.anchor = GridBagConstraints.WEST;
        gbc_affichageProvision.insets = new Insets(0, 0, 5, 0);
        gbc_affichageProvision.gridx = 2;
        gbc_affichageProvision.gridy = 6;
        panel_infos.add(affichage_provision, gbc_affichageProvision);


        JButton bouton_modifier_provision = new JButton("Modifier");
        GridBagConstraints gbc_btnUpdateProvision = new GridBagConstraints();
        gbc_affichageProvision.insets = new Insets(0, 0, 5, 5);
        gbc_btnUpdateProvision.gridx = 3;
        gbc_btnUpdateProvision.gridy = 6;
        panel_infos.add(bouton_modifier_provision, gbc_btnUpdateProvision);
        bouton_modifier_provision.addActionListener(modele.getUpdateProvisionPourCharge(modele.getIdBail()));

        JLabel label_garantie = new JLabel("Dépôt de garantie");
        GridBagConstraints gbc_labelGarantie = new GridBagConstraints();
        gbc_labelGarantie.anchor = GridBagConstraints.WEST;
        gbc_labelGarantie.insets = new Insets(0, 0, 0, 5);
        gbc_labelGarantie.gridx = 0;
        gbc_labelGarantie.gridy = 7;
        panel_infos.add(label_garantie, gbc_labelGarantie);

        this.affichage_garantie = new JLabel("New label");
        GridBagConstraints gbc_affichageGarantie = new GridBagConstraints();
        gbc_affichageGarantie.anchor = GridBagConstraints.WEST;
        gbc_affichageGarantie.gridx = 2;
        gbc_affichageGarantie.gridy = 7;
        panel_infos.add(affichage_garantie, gbc_affichageGarantie);

        // ICC
        JLabel label_icc = new JLabel("ICC");
        GridBagConstraints gbc_labelIcc = new GridBagConstraints();
        gbc_labelIcc.anchor = GridBagConstraints.WEST;
        gbc_labelIcc.insets = new Insets(0, 0, 0, 5);
        gbc_labelIcc.gridx = 0;
        gbc_labelIcc.gridy = 8;
        panel_infos.add(label_icc, gbc_labelIcc);

        JLabel affichage_icc = new JLabel(String.valueOf(bail.getIcc()));
        GridBagConstraints gbc_affichageIcc = new GridBagConstraints();
        gbc_affichageIcc.anchor = GridBagConstraints.WEST;
        gbc_affichageIcc.gridx = 2;
        gbc_affichageIcc.gridy = 8;
        panel_infos.add(affichage_icc, gbc_affichageIcc);

        // Index Eau
        JLabel label_index_eau = new JLabel("Index Eau");
        GridBagConstraints gbc_labelIndexEau = new GridBagConstraints();
        gbc_labelIndexEau.anchor = GridBagConstraints.WEST;
        gbc_labelIndexEau.insets = new Insets(0, 0, 0, 5);
        gbc_labelIndexEau.gridx = 0;
        gbc_labelIndexEau.gridy = 9;
        panel_infos.add(label_index_eau, gbc_labelIndexEau);

        JLabel affichage_index_eau = new JLabel(String.valueOf(bail.getIndexEau()));
        GridBagConstraints gbc_affichageIndexEau = new GridBagConstraints();
        gbc_affichageIndexEau.anchor = GridBagConstraints.WEST;
        gbc_affichageIndexEau.gridx = 2;
        gbc_affichageIndexEau.gridy = 9;
        panel_infos.add(affichage_index_eau, gbc_affichageIndexEau);

        JPanel panel_locataires = new JPanel();
        panel.add(panel_locataires);
        panel_locataires.setLayout(new BorderLayout(0, 0));

        JLabel locataires = new JLabel("Locataires");
        locataires.setHorizontalAlignment(SwingConstants.CENTER);
        panel_locataires.add(locataires, BorderLayout.NORTH);

        // Panel principal (avec un défilement si nécessaire)
        this.tableau_locataire = new JPanel(new GridBagLayout()); // Remplacer GridLayout par GridBagLayout

        // Créer un GridBagConstraints pour gérer le placement des composants
        gbc_loc = new GridBagConstraints();
        gbc_loc.fill = GridBagConstraints.HORIZONTAL;
        gbc_loc.insets = new Insets(5, 5, 5, 5); // Espacement entre les composants

        modele.creerTableauLocataire();

        JScrollPane scrollPane = new JScrollPane(this.tableau_locataire);
        panel_locataires.add(scrollPane, BorderLayout.CENTER);

        JPanel panelModifs = new JPanel();
        bas_de_page.add(panelModifs);
        GridBagLayout gbl_panelModifs = new GridBagLayout();
        gbl_panelModifs.columnWidths = new int[] {0};
        gbl_panelModifs.rowHeights = new int[] {0};
        gbl_panelModifs.columnWeights = new double[]{0.0,0.0,0.0,0.0,0.0};
        gbl_panelModifs.rowWeights = new double[]{0.0};
        panelModifs.setLayout(gbl_panelModifs);

        JButton bouton_ajout_charges = new JButton("Gestion des charges");
        GridBagConstraints gbc_btnAjoutCharges = new GridBagConstraints();
        gbc_btnAjoutCharges.insets = new Insets(0,0,5,5);
        gbc_btnAjoutCharges.gridx = 2;
        gbc_btnAjoutCharges.gridy = 0;
        panelModifs.add(bouton_ajout_charges,gbc_btnAjoutCharges);
        bouton_ajout_charges.addActionListener(modele.BtnPageCharge());

        JButton bouton_ajout_locataire = new JButton("Ajouter un locataire");
        GridBagConstraints gbc_btnAjoutLocataire = new GridBagConstraints();
        gbc_btnAjoutLocataire.insets = new Insets(0, 0, 5, 5);
        gbc_btnAjoutLocataire.gridx = 3;
        gbc_btnAjoutLocataire.gridy = 0;
        panelModifs.add(bouton_ajout_locataire, gbc_btnAjoutLocataire);
        bouton_ajout_locataire.addActionListener(modele.getAjouterLocataire(modele.getIdBail()));

        if(bail.getDernierAnniversaire().before(modele.enleveUneAnneeADate(new Date(System.currentTimeMillis())))){
            JButton btnModifierICC = new JButton("Modifier l'ICC (Anniversaie de votre bail)");
            GridBagConstraints gbc_btnModifierICC = new GridBagConstraints();
            gbc_btnModifierICC.insets = new Insets(0, 0, 5, 5);
            gbc_btnModifierICC.gridx = 4;
            gbc_btnModifierICC.gridy = 0;
            panelModifs.add(btnModifierICC, gbc_btnModifierICC);
            btnModifierICC.addActionListener(modele.getModifierICC(frame,modele.getIdBail()));
        }

        if(bail.getDateFin().before(new Date(System.currentTimeMillis()))){
            JButton bouton_supprimer_bail = new JButton("Supprimer le bail");
            GridBagConstraints gbc_btnDeleteBail = new GridBagConstraints();
            gbc_btnDeleteBail.insets = new Insets(0, 0, 5, 0);
            gbc_btnDeleteBail.gridx = 5;
            gbc_btnDeleteBail.gridy = 0;
            panelModifs.add(bouton_supprimer_bail, gbc_btnDeleteBail);
            bouton_supprimer_bail.addActionListener(modele.deleteBail(modele.getIdBail()));
        }

        JButton bouton_quitter = new JButton("Quitter");
        GridBagConstraints gbc_btnQuitter = new GridBagConstraints();
        gbc_btnQuitter.insets = new Insets(0,0,5,5);
        gbc_btnQuitter.gridx = 1;
        gbc_btnQuitter.gridy = 0;
        panelModifs.add(bouton_quitter,gbc_btnQuitter);
        bouton_quitter.addActionListener(modele.quitterPage());

        try {
            // Chargement des données du bien
            modele.chargerDonneesBail(modele.getIdBail(), this);

        } catch (DAOException e) {
            JOptionPane.showMessageDialog(frame, "Erreur lors du chargement des données du bien : " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        frame.addWindowListener(modele.fermerFenetre());
    }

    public JLabel getAffichageVille() {
        return affichage_ville;
    }

    public JLabel getAffichageAdresse() {
        return affichage_adresse;
    }

    public JLabel getAffichageComplement() {
        return affichage_complement;
    }

    public JLabel getAffichageSurface() {
        return affichage_surface;
    }

    public JLabel getAffichageLoyer() {
        return affichage_loyer;
    }

    public JLabel getAffichageProvision() {
        return affichage_provision;
    }

    public JLabel getAffichageNbPieces() {
        return affichage_nb_pieces;
    }

    public JLabel getAffichageGarantie() {
        return affichage_garantie;
    }

    public Bail getBail() {
        return bail;
    }

    public JPanel getTableau_locataire() {
        return tableau_locataire;
    }

    public GridBagConstraints getGbc_loc() {
        return gbc_loc;
    }

}
