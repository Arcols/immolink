package ihm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import DAO.DAOException;
import modele.Menu;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import com.toedter.calendar.JDateChooser;
import enumeration.TypeLogement;
import modele.*;

public class PageNouveauTravaux extends PageAbstraite {

    private JTextField valeur_num_devis;
    private JTextField valeur_num_facture;
    private JTextField valeur_montant_devis;
    private JTextField valeur_montant_travaux;
    private JTextField valeur_nature;
    private JTextField valeur_adresse;
    private JTextField valeur_nom;
    private JTextField valeur_type;
    private JButton bouton_valider;

    private JDateChooser choix_date_debut;
    private JDateChooser choix_date_fin;

    private ModelePageNouveauTravaux modele;
    private int id;
    private TypeLogement typeLogement;

    /**
     * Create the application.
     */
    public PageNouveauTravaux(Integer id, TypeLogement typeLogement, int x, int y) throws DAOException {
        super(x,y);
        this.id=id;
        this.typeLogement=typeLogement;
        this.modele = new ModelePageNouveauTravaux(this);
        this.CreerSpecific();
    }

    @Override
    public void CreerSpecific() {
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

        valeur_num_devis = new JFormattedTextField();
        GridBagConstraints gbc_valueNumDevis = new GridBagConstraints();
        gbc_valueNumDevis.insets = new Insets(0, 0, 5, 0);
        gbc_valueNumDevis.anchor = GridBagConstraints.WEST;
        gbc_valueNumDevis.gridx = 1;
        gbc_valueNumDevis.gridy = 0;
        valeurs.add(valeur_num_devis, gbc_valueNumDevis);
        valeur_num_devis.setColumns(10);

        JLabel label_num_facture = new JLabel("Numero facture");
        GridBagConstraints gbc_labelNumFacture = new GridBagConstraints();
        gbc_labelNumFacture.insets = new Insets(0, 0, 5, 5);
        gbc_labelNumFacture.anchor = GridBagConstraints.WEST;
        gbc_labelNumFacture.gridx = 0;
        gbc_labelNumFacture.gridy = 1;
        valeurs.add(label_num_facture, gbc_labelNumFacture);

        valeur_num_facture = new JTextField();
        GridBagConstraints gbc_valueNumFacture = new GridBagConstraints();
        gbc_valueNumFacture.insets = new Insets(0, 0, 5, 0);
        gbc_valueNumFacture.anchor = GridBagConstraints.WEST;
        gbc_valueNumFacture.gridx = 1;
        gbc_valueNumFacture.gridy = 1;
        valeurs.add(valeur_num_facture, gbc_valueNumFacture);
        valeur_num_facture.setColumns(10);

        JLabel label_montant_devis = new JLabel("Montant du devis");
        GridBagConstraints gbc_labelMontantDevis = new GridBagConstraints();
        gbc_labelMontantDevis.anchor = GridBagConstraints.WEST;
        gbc_labelMontantDevis.insets = new Insets(0, 0, 5, 5);
        gbc_labelMontantDevis.gridx = 0;
        gbc_labelMontantDevis.gridy = 2;
        valeurs.add(label_montant_devis, gbc_labelMontantDevis);

        valeur_montant_devis = new JTextField();
        GridBagConstraints gbc_valueMontantDevis = new GridBagConstraints();
        gbc_valueMontantDevis.anchor = GridBagConstraints.WEST;
        gbc_valueMontantDevis.insets = new Insets(0, 0, 5, 0);
        gbc_valueMontantDevis.gridx = 1;
        gbc_valueMontantDevis.gridy = 2;
        valeurs.add(valeur_montant_devis, gbc_valueMontantDevis);
        valeur_montant_devis.setColumns(10);

        JLabel label_montant_travaux = new JLabel("Montant des travaux");
        GridBagConstraints gbc_labelMontantTeavaux = new GridBagConstraints();
        gbc_labelMontantTeavaux.anchor = GridBagConstraints.WEST;
        gbc_labelMontantTeavaux.insets = new Insets(0, 0, 5, 5);
        gbc_labelMontantTeavaux.gridx = 0;
        gbc_labelMontantTeavaux.gridy = 3;
        valeurs.add(label_montant_travaux, gbc_labelMontantTeavaux);

        valeur_montant_travaux = new JTextField();
        GridBagConstraints gbc_valueMontantTravaux = new GridBagConstraints();
        gbc_valueMontantTravaux.anchor = GridBagConstraints.WEST;
        gbc_valueMontantTravaux.insets = new Insets(0, 0, 5, 0);
        gbc_valueMontantTravaux.gridx = 1;
        gbc_valueMontantTravaux.gridy = 3;
        valeurs.add(valeur_montant_travaux, gbc_valueMontantTravaux);
        valeur_montant_travaux.setColumns(10);

        JLabel label_nature = new JLabel("Nature");
        GridBagConstraints gbc_labelNature = new GridBagConstraints();
        gbc_labelNature.anchor = GridBagConstraints.WEST;
        gbc_labelNature.insets = new Insets(0, 0, 5, 5);
        gbc_labelNature.gridx = 0;
        gbc_labelNature.gridy = 4;
        valeurs.add(label_nature, gbc_labelNature);

        valeur_nature = new JTextField();
        GridBagConstraints gbc_valueNature = new GridBagConstraints();
        gbc_valueNature.anchor = GridBagConstraints.WEST;
        gbc_valueNature.insets = new Insets(0, 0, 5, 0);
        gbc_valueNature.gridx = 1;
        gbc_valueNature.gridy = 4;
        valeurs.add(valeur_nature, gbc_valueNature);
        valeur_nature.setColumns(10);

        JLabel label_adresse = new JLabel("Adresse de l'entreprise");
        GridBagConstraints gbc_labelAdresse = new GridBagConstraints();
        gbc_labelAdresse.anchor = GridBagConstraints.WEST;
        gbc_labelAdresse.insets = new Insets(0, 0, 5, 5);
        gbc_labelAdresse.gridx = 0;
        gbc_labelAdresse.gridy = 5;
        valeurs.add(label_adresse, gbc_labelAdresse);

        valeur_adresse = new JTextField();
        GridBagConstraints gbc_valueAdresse = new GridBagConstraints();
        gbc_valueAdresse.anchor = GridBagConstraints.WEST;
        gbc_valueAdresse.insets = new Insets(0, 0, 5, 0);
        gbc_valueAdresse.gridx = 1;
        gbc_valueAdresse.gridy = 5;
        valeurs.add(valeur_adresse, gbc_valueAdresse);
        valeur_adresse.setColumns(10);

        JLabel label_nom = new JLabel("Nom de l'entreprise");
        GridBagConstraints gbc_LabelNom = new GridBagConstraints();
        gbc_LabelNom.anchor = GridBagConstraints.WEST;
        gbc_LabelNom.insets = new Insets(0, 0, 5, 5);
        gbc_LabelNom.gridx = 0;
        gbc_LabelNom.gridy = 6;
        valeurs.add(label_nom, gbc_LabelNom);

        valeur_nom = new JTextField();
        GridBagConstraints gbc_valueNom = new GridBagConstraints();
        gbc_valueNom.anchor = GridBagConstraints.WEST;
        gbc_valueNom.insets = new Insets(0, 0, 5, 0);
        gbc_valueNom.gridx = 1;
        gbc_valueNom.gridy = 6;
        valeurs.add(valeur_nom, gbc_valueNom);
        valeur_nom.setColumns(10);

        JLabel label_type = new JLabel("Type");
        GridBagConstraints gbc_labelType = new GridBagConstraints();
        gbc_labelType.anchor = GridBagConstraints.WEST;
        gbc_labelType.insets = new Insets(0, 0, 5, 5);
        gbc_labelType.gridx = 0;
        gbc_labelType.gridy = 7;
        valeurs.add(label_type, gbc_labelType);

        valeur_type = new JTextField();
        GridBagConstraints gbc_valueType = new GridBagConstraints();
        gbc_valueType.anchor = GridBagConstraints.WEST;
        gbc_valueType.insets = new Insets(0, 0, 5, 0);
        gbc_valueType.gridx = 1;
        gbc_valueType.gridy = 7;
        valeurs.add(valeur_type, gbc_valueType);
        valeur_type.setColumns(10);

        JLabel label_date_debut = new JLabel("Date de début");
        GridBagConstraints gbc_labelDateDebut = new GridBagConstraints();
        gbc_labelDateDebut.anchor = GridBagConstraints.WEST;
        gbc_labelDateDebut.insets = new Insets(0, 0, 5, 5);
        gbc_labelDateDebut.gridx = 0;
        gbc_labelDateDebut.gridy = 8;
        valeurs.add(label_date_debut, gbc_labelDateDebut);

        choix_date_debut = new JDateChooser();
        choix_date_debut.setPreferredSize(new Dimension(115, 22));
        GridBagConstraints gbc_dateChooserDebut = new GridBagConstraints();
        gbc_dateChooserDebut.anchor = GridBagConstraints.WEST;
        gbc_dateChooserDebut.insets = new Insets(0, 0, 5, 0);
        gbc_dateChooserDebut.gridx = 1;
        gbc_dateChooserDebut.gridy = 8;
        valeurs.add(choix_date_debut, gbc_dateChooserDebut);

        JLabel label_date_facture = new JLabel("Date de facture");
        GridBagConstraints gbc_labelDateFacture = new GridBagConstraints();
        gbc_labelDateFacture.anchor = GridBagConstraints.WEST;
        gbc_labelDateFacture.insets = new Insets(0, 0, 5, 5);
        gbc_labelDateFacture.gridx = 0;
        gbc_labelDateFacture.gridy = 9;
        valeurs.add(label_date_facture, gbc_labelDateFacture);

        choix_date_fin = new JDateChooser();
        choix_date_fin.setPreferredSize(new Dimension(115, 22));
        GridBagConstraints gbc_dateChooserFacture = new GridBagConstraints();
        gbc_dateChooserFacture.anchor = GridBagConstraints.WEST;
        gbc_dateChooserFacture.insets = new Insets(0, 0, 5, 0);
        gbc_dateChooserFacture.gridx = 1;
        gbc_dateChooserFacture.gridy = 9;
        valeurs.add(choix_date_fin, gbc_dateChooserFacture);

        JButton bouton_quitter = new JButton("Quitter");
        bas_de_page.add(bouton_quitter, BorderLayout.WEST);
        bouton_quitter.addActionListener(modele.quitterPage(id,typeLogement));

        this.bouton_valider = new JButton("Valider");
        this.bouton_valider.setEnabled(false);
        bas_de_page.add(this.bouton_valider, BorderLayout.EAST);

        valeur_num_devis.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
        valeur_num_facture.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
        valeur_montant_devis.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
        valeur_montant_travaux.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
        valeur_nature.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
        valeur_adresse.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
        valeur_nom.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
        valeur_type.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
        choix_date_debut.getDateEditor().addPropertyChangeListener("date", evt -> modele.getTextFieldDocumentListener().insertUpdate(null));
        choix_date_fin.getDateEditor().addPropertyChangeListener("date", evt -> modele.getTextFieldDocumentListener().insertUpdate(null));
        try {
            bouton_valider.addActionListener(modele.getAjouterTravauxListener(id,typeLogement));
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }

        frame.addWindowListener(modele.fermerFenetre());
    }

    public JTextField getValueNumDevis() {
        return valeur_num_devis;
    }

    public JTextField getValueNumFacture() {
        return valeur_num_facture;
    }

    public JTextField getValueMontantDevis() {
        return valeur_montant_devis;
    }

    public JTextField getValueMontantTravaux() {
        return valeur_montant_travaux;
    }

    public JTextField getValueNature() {
        return valeur_nature;
    }

    public JTextField getValueAdresse() {
        return valeur_adresse;
    }

    public JTextField getValueNom() {
        return valeur_nom;
    }

    public JTextField getValueType() {
        return valeur_type;
    }

    public JDateChooser getDateChooserDebut() {
        return choix_date_debut;
    }

    public JDateChooser getDateChooserFacture() {
        return choix_date_fin;
    }

    public JButton getBtnValider() {return this.bouton_valider; }
}
