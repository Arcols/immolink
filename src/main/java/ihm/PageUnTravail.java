package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import enumeration.TypeLogement;
import modele.*;

public class PageUnTravail extends PageAbstraite {

    private JLabel valeur_num_devis;
    private JLabel valeur_num_facture;
    private JLabel valeur_montant_devis;
    private JLabel valeur_montant_travaux;
    private JLabel valeur_nature;
    private JLabel valeur_adresse;
    private JLabel valeur_nom;
    private JLabel valeur_type;
    private JLabel valeur_date_debut;
    private JLabel valeur_date_fin;
    private ModelePageUnTravail modele;
    private int id;
    private TypeLogement type_logement;
    private int id_travail;

    /**
     * Create the application.
     */
    public PageUnTravail(Integer id, TypeLogement type_logement, Integer id_travail, int x, int y) throws DAOException {
        super(x,y);
        this.modele = new  ModelePageUnTravail(this);
        this.id=id;
        this.type_logement=type_logement;
        this.id_travail=id_travail;
        this.CreerSpecific();
    }
    

    @Override
    public void CreerSpecific(){
        valeur_num_devis = new JLabel();
        valeur_num_facture = new JLabel();
        valeur_montant_devis = new JLabel();
        valeur_montant_travaux = new JLabel();
        valeur_nature = new JLabel();
        valeur_adresse = new JLabel();
        valeur_nom = new JLabel();
        valeur_type = new JLabel();
        valeur_date_debut = new JLabel();
        valeur_date_fin = new JLabel();
        
        try {
            // Chargement des données du bien
            modele.chargerDonneesTravail(id_travail, this);
        } catch (DAOException e) {
            JOptionPane.showMessageDialog(frame, "Erreur lors du chargement des données du bien : " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        JLabel label_titre = new JLabel("Nouveau travaux");
        label_titre.setHorizontalAlignment(SwingConstants.CENTER);
        panel_body.add(label_titre, BorderLayout.NORTH);

        JPanel valeurs = new JPanel();
        panel_body.add(valeurs, BorderLayout.CENTER);
        GridBagLayout gbl_valeurs = new GridBagLayout();
        gbl_valeurs.columnWidths = new int[] {0};
        gbl_valeurs.rowHeights = new int[] {0};
        gbl_valeurs.columnWeights = new double[]{0.0, 0.0};
        gbl_valeurs.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        valeurs.setLayout(gbl_valeurs);

        JLabel label_num_devis = new JLabel("Numero devis");
        GridBagConstraints gbc_labelNumDevis = new GridBagConstraints();
        gbc_labelNumDevis.insets = new Insets(0, 0, 5, 5);
        gbc_labelNumDevis.anchor = GridBagConstraints.WEST;
        gbc_labelNumDevis.gridx = 0;
        gbc_labelNumDevis.gridy = 0;
        valeurs.add(label_num_devis, gbc_labelNumDevis);
        
        GridBagConstraints gbc_valueNumDevis = new GridBagConstraints();
        gbc_valueNumDevis.insets = new Insets(0, 0, 5, 0);
        gbc_valueNumDevis.anchor = GridBagConstraints.WEST;
        gbc_valueNumDevis.gridx = 1;
        gbc_valueNumDevis.gridy = 0;
        valeurs.add(valeur_num_devis, gbc_valueNumDevis);

        JLabel label_num_facture = new JLabel("Numero facture");
        GridBagConstraints gbc_labelNumFacture = new GridBagConstraints();
        gbc_labelNumFacture.insets = new Insets(0, 0, 5, 5);
        gbc_labelNumFacture.anchor = GridBagConstraints.WEST;
        gbc_labelNumFacture.gridx = 0;
        gbc_labelNumFacture.gridy = 1;
        valeurs.add(label_num_facture, gbc_labelNumFacture);
        
        GridBagConstraints gbc_valueNumFacture = new GridBagConstraints();
        gbc_valueNumFacture.insets = new Insets(0, 0, 5, 0);
        gbc_valueNumFacture.anchor = GridBagConstraints.WEST;
        gbc_valueNumFacture.gridx = 1;
        gbc_valueNumFacture.gridy = 1;
        valeurs.add(valeur_num_facture, gbc_valueNumFacture);

        JLabel label_montant_devis = new JLabel("Montant du devis");
        GridBagConstraints gbc_labelMontantDevis = new GridBagConstraints();
        gbc_labelMontantDevis.anchor = GridBagConstraints.SOUTHWEST;
        gbc_labelMontantDevis.insets = new Insets(0, 0, 5, 5);
        gbc_labelMontantDevis.gridx = 0;
        gbc_labelMontantDevis.gridy = 2;
        valeurs.add(label_montant_devis, gbc_labelMontantDevis);

        GridBagConstraints gbc_valueMontantDevis = new GridBagConstraints();
        gbc_valueMontantDevis.anchor = GridBagConstraints.WEST;
        gbc_valueMontantDevis.insets = new Insets(0, 0, 5, 0);
        gbc_valueMontantDevis.gridx = 1;
        gbc_valueMontantDevis.gridy = 2;
        valeurs.add(valeur_montant_devis, gbc_valueMontantDevis);

        JLabel label_montant_travaux = new JLabel("Montant des travaux");
        GridBagConstraints gbc_labelMontantTeavaux = new GridBagConstraints();
        gbc_labelMontantTeavaux.anchor = GridBagConstraints.WEST;
        gbc_labelMontantTeavaux.insets = new Insets(0, 0, 5, 5);
        gbc_labelMontantTeavaux.gridx = 0;
        gbc_labelMontantTeavaux.gridy = 3;
        valeurs.add(label_montant_travaux, gbc_labelMontantTeavaux);

        GridBagConstraints gbc_valueMontantTravaux = new GridBagConstraints();
        gbc_valueMontantTravaux.anchor = GridBagConstraints.WEST;
        gbc_valueMontantTravaux.insets = new Insets(0, 0, 5, 0);
        gbc_valueMontantTravaux.gridx = 1;
        gbc_valueMontantTravaux.gridy = 3;
        valeurs.add(valeur_montant_travaux, gbc_valueMontantTravaux);

        JLabel label_nature = new JLabel("Nature");
        GridBagConstraints gbc_labelNature = new GridBagConstraints();
        gbc_labelNature.anchor = GridBagConstraints.WEST;
        gbc_labelNature.insets = new Insets(0, 0, 5, 5);
        gbc_labelNature.gridx = 0;
        gbc_labelNature.gridy = 4;
        valeurs.add(label_nature, gbc_labelNature);

        GridBagConstraints gbc_valueNature = new GridBagConstraints();
        gbc_valueNature.anchor = GridBagConstraints.WEST;
        gbc_valueNature.insets = new Insets(0, 0, 5, 0);
        gbc_valueNature.gridx = 1;
        gbc_valueNature.gridy = 4;
        valeurs.add(valeur_nature, gbc_valueNature);

        JLabel label_adresse = new JLabel("Adresse de l'entreprise");
        GridBagConstraints gbc_labelAdresse = new GridBagConstraints();
        gbc_labelAdresse.anchor = GridBagConstraints.WEST;
        gbc_labelAdresse.insets = new Insets(0, 0, 5, 5);
        gbc_labelAdresse.gridx = 0;
        gbc_labelAdresse.gridy = 5;
        valeurs.add(label_adresse, gbc_labelAdresse);

        GridBagConstraints gbc_valueAdresse = new GridBagConstraints();
        gbc_valueAdresse.anchor = GridBagConstraints.WEST;
        gbc_valueAdresse.insets = new Insets(0, 0, 5, 0);
        gbc_valueAdresse.gridx = 1;
        gbc_valueAdresse.gridy = 5;
        valeurs.add(valeur_adresse, gbc_valueAdresse);

        JLabel label_nom = new JLabel("Nom de l'entreprise");
        GridBagConstraints gbc_LabelNom = new GridBagConstraints();
        gbc_LabelNom.anchor = GridBagConstraints.WEST;
        gbc_LabelNom.insets = new Insets(0, 0, 5, 5);
        gbc_LabelNom.gridx = 0;
        gbc_LabelNom.gridy = 6;
        valeurs.add(label_nom, gbc_LabelNom);

        GridBagConstraints gbc_valueNom = new GridBagConstraints();
        gbc_valueNom.anchor = GridBagConstraints.WEST;
        gbc_valueNom.insets = new Insets(0, 0, 5, 0);
        gbc_valueNom.gridx = 1;
        gbc_valueNom.gridy = 6;
        valeurs.add(valeur_nom, gbc_valueNom);

        JLabel label_type = new JLabel("Type");
        GridBagConstraints gbc_labelType = new GridBagConstraints();
        gbc_labelType.anchor = GridBagConstraints.WEST;
        gbc_labelType.insets = new Insets(0, 0, 5, 5);
        gbc_labelType.gridx = 0;
        gbc_labelType.gridy = 7;
        valeurs.add(label_type, gbc_labelType);

        GridBagConstraints gbc_valueType = new GridBagConstraints();
        gbc_valueType.anchor = GridBagConstraints.WEST;
        gbc_valueType.insets = new Insets(0, 0, 5, 0);
        gbc_valueType.gridx = 1;
        gbc_valueType.gridy = 7;
        valeurs.add(valeur_type, gbc_valueType);

        JLabel label_date_debut = new JLabel("Date de début");
        GridBagConstraints gbc_labelDateDebut = new GridBagConstraints();
        gbc_labelDateDebut.anchor = GridBagConstraints.WEST;
        gbc_labelDateDebut.insets = new Insets(0, 0, 5, 5);
        gbc_labelDateDebut.gridx = 0;
        gbc_labelDateDebut.gridy = 8;
        valeurs.add(label_date_debut, gbc_labelDateDebut);

        GridBagConstraints gbc_valueDateDebut = new GridBagConstraints();
        gbc_valueDateDebut.anchor = GridBagConstraints.WEST;
        gbc_valueDateDebut.insets = new Insets(0, 0, 5, 0);
        gbc_valueDateDebut.gridx = 1;
        gbc_valueDateDebut.gridy = 8;
        valeurs.add(valeur_date_debut, gbc_valueDateDebut);

        JLabel label_date_fin = new JLabel("Date de fin");
        GridBagConstraints gbc_labelDateFin = new GridBagConstraints();
        gbc_labelDateFin.anchor = GridBagConstraints.WEST;
        gbc_labelDateFin.insets = new Insets(0, 0, 5, 5);
        gbc_labelDateFin.gridx = 0;
        gbc_labelDateFin.gridy = 9;
        valeurs.add(label_date_fin, gbc_labelDateFin);

        GridBagConstraints gbc_valueDateFin = new GridBagConstraints();
        gbc_valueDateFin.insets = new Insets(0, 0, 5, 0);
        gbc_valueDateFin.anchor = GridBagConstraints.WEST;
        gbc_valueDateFin.gridx = 1;
        gbc_valueDateFin.gridy = 9;
        valeurs.add(valeur_date_fin, gbc_valueDateFin);

        JButton bouton_quitter = new JButton("Quitter");
        bas_de_page.add(bouton_quitter, BorderLayout.WEST);
        bouton_quitter.addActionListener(modele.quitterPage(id,type_logement));

        JButton bouton_supprimer = new JButton("Supprimer");
        bas_de_page.add(bouton_supprimer, BorderLayout.EAST);

        bouton_supprimer.addActionListener(modele.getSupprimerTravauxListener(id_travail,id,type_logement));
        frame.addWindowListener(modele.fermerFenetre());
    }

    public JLabel getValueNumDevis() {
        return valeur_num_devis;
    }

    public JLabel getValueNumFacture() {
        return valeur_num_facture;
    }

    public JLabel getValueMontantDevis() {
        return valeur_montant_devis;
    }

    public JLabel getValueMontantTravaux() {
        return valeur_montant_travaux;
    }

    public JLabel getValueNature() {
        return valeur_nature;
    }

    public JLabel getValueAdresse() {
        return valeur_adresse;
    }

    public JLabel getValueNom() {
        return valeur_nom;
    }

    public JLabel getValueType() {
        return valeur_type;
    }

    public JLabel getValueDateDebut() {
        return valeur_date_debut;
    }

    public JLabel getValueDateFin() {
        return valeur_date_fin;
    }
}
