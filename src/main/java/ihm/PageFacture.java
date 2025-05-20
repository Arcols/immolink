package ihm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.toedter.calendar.JDateChooser;

import modele.ModelePageFacture;

public class PageFacture extends PageAbstraite {

    private JComboBox choix_type;
    private JTextField choix_num_facture;
    private  JTextField choix_montant;
    private  JDateChooser date_chooser;
    private  JPanel contenu;
    private  JLabel montant;
    private JLabel date;
    private ModelePageFacture modele;
    private final int id_bail;
    private final JLabel label_index=new JLabel("Index d'eau");
    private final JTextField choix_index=new JTextField();
    private final JLabel label_prix_conso=new JLabel("Prix m³ d'eau");
    private final JTextField choix_prix_conso=new JTextField();
    private JButton valider;
    private GridBagConstraints gbc_montant;


    public PageFacture(int id_bail,int x,int y) {
        super(x,y);
        this.modele = new ModelePageFacture(this);
        this.id_bail = id_bail;
        CreerSpecific();
    }

    @Override
    public void CreerSpecific() {
        JLabel titre_page = new JLabel("Ajout de facture");
        titre_page.setAlignmentY(0.0f);
        titre_page.setAlignmentX(0.5f);
        panel_body.add(titre_page);

        contenu = new JPanel();
        panel_body.add(contenu, BorderLayout.CENTER);
        GridBagLayout gbl_contenu = new GridBagLayout();
        gbl_contenu.rowHeights = new int[] {0, 0, 0, 0};
        gbl_contenu.columnWidths = new int[] {200, 0, 50, 200};
        gbl_contenu.columnWeights = new double[]{0.0, 0.0, 1.0};
        gbl_contenu.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0};
        contenu.setLayout(gbl_contenu);

        JLabel type = new JLabel("Choix de type");
        GridBagConstraints gbc_type = new GridBagConstraints();
        gbc_type.insets = new Insets(0, 0, 5, 5);
        gbc_type.gridx = 1;
        gbc_type.gridy = 0;
        contenu.add(type, gbc_type);

        choix_type = new JComboBox();
        choix_type.addItem("Entretien");
        choix_type.addItem("Electricité");
        choix_type.addItem("Ordures");
        choix_type.addItem("Eau");
        GridBagConstraints gbc_choix_type = new GridBagConstraints();
        gbc_choix_type.insets = new Insets(0, 0, 5, 5);
        gbc_choix_type.fill = GridBagConstraints.HORIZONTAL;
        gbc_choix_type.gridx = 2;
        gbc_choix_type.gridy = 0;
        contenu.add(choix_type, gbc_choix_type);


        JLabel numero = new JLabel("Numéro de facture");
        GridBagConstraints gbc_numero = new GridBagConstraints();
        gbc_numero.anchor = GridBagConstraints.EAST;
        gbc_numero.insets = new Insets(0, 0, 5, 5);
        gbc_numero.gridx = 1;
        gbc_numero.gridy = 1;
        contenu.add(numero, gbc_numero);

        choix_num_facture = new JTextField();
        GridBagConstraints gbc_choix_num_facture = new GridBagConstraints();
        gbc_choix_num_facture.insets = new Insets(0, 0, 5, 5);
        gbc_choix_num_facture.fill = GridBagConstraints.HORIZONTAL;
        gbc_choix_num_facture.gridx = 2;
        gbc_choix_num_facture.gridy = 1;
        contenu.add(choix_num_facture, gbc_choix_num_facture);
        choix_num_facture.setColumns(10);

        montant = new JLabel("Montant");
        gbc_montant = new GridBagConstraints();
        gbc_montant.anchor = GridBagConstraints.EAST;
        gbc_montant.insets = new Insets(0, 0, 5, 5);
        gbc_montant.gridx = 1;
        gbc_montant.gridy = 2;
        contenu.add(montant, gbc_montant);

        choix_montant = new JTextField();
        GridBagConstraints gbc_choix_montant = new GridBagConstraints();
        gbc_choix_montant.insets = new Insets(0, 0, 5, 5);
        gbc_choix_montant.fill = GridBagConstraints.HORIZONTAL;
        gbc_choix_montant.gridx = 2;
        gbc_choix_montant.gridy = 2;
        contenu.add(choix_montant, gbc_choix_montant);
        choix_montant.setColumns(10);

        this.date = new JLabel("Date de la facture");
        GridBagConstraints gbc_date = new GridBagConstraints();
        gbc_date.insets = new Insets(0, 0, 5, 5);
        gbc_date.gridx = 1;
        gbc_date.gridy = 3;
        contenu.add(date, gbc_date);

        date_chooser = new JDateChooser();
        date_chooser.setPreferredSize(new Dimension(100, 22));
        GridBagConstraints gbc_a_REMPLACER_POUR_DATE = new GridBagConstraints();
        gbc_a_REMPLACER_POUR_DATE.insets = new Insets(0, 0, 5, 5);
        gbc_a_REMPLACER_POUR_DATE.fill = GridBagConstraints.HORIZONTAL;
        gbc_a_REMPLACER_POUR_DATE.gridx = 2;
        gbc_a_REMPLACER_POUR_DATE.gridy = 3;
        contenu.add(date_chooser, gbc_a_REMPLACER_POUR_DATE);

        JButton quitter = new JButton("Quitter");
        quitter.setHorizontalTextPosition(SwingConstants.LEFT);
        quitter.setVerticalTextPosition(SwingConstants.TOP);
        quitter.setVerticalAlignment(SwingConstants.BOTTOM);
        bas_de_page.add(quitter, BorderLayout.WEST);
        quitter.addActionListener(modele.quitterPage());

        valider = new JButton("Ajouter");
        valider.setEnabled(false);
        bas_de_page.add(valider, BorderLayout.EAST);
        valider.addActionListener(modele.ajouterFacture());
        frame.setVisible(true);

        choix_type.addActionListener(modele.eauSelected());
        choix_type.addActionListener(modele.getCheckFieldsActionListener());
        choix_index.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
        choix_prix_conso.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
        choix_num_facture.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
        choix_montant.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
        date_chooser.getDateEditor().addPropertyChangeListener("date", evt -> modele.getTextFieldDocumentListener().insertUpdate(null));

        frame.addWindowListener(modele.fermerFenetre());
    }

    public JComboBox getChoix_type() {
        return choix_type;
    }

    public JTextField getChoix_num_facture() {
        return choix_num_facture;
    }

    public JTextField getChoix_montant() {
        return choix_montant;
    }

    public JDateChooser getDateChooser() {
        return date_chooser;
    }

    public int getId_bail() {
        return id_bail;
    }

    public JPanel getContenu() {
        return contenu;
    }

    public JLabel getLabelMontant() {
        return montant;
    }

    public JLabel getDate() {
        return date;
    }

    public JLabel getLabel_prix_conso() {
        return label_prix_conso;
    }

    public JTextField getChoix_index() {
        return choix_index;
    }

    public JLabel getLabel_index() {
        return label_index;
    }

    public JTextField getChoix_prix_conso() {
        return choix_prix_conso;
    }

    public JButton getValider() {
        return valider;
    }

}
