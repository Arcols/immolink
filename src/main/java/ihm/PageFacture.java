package ihm;

import DAO.DAOException;
import com.toedter.calendar.JDateChooser;
import modele.Charte;
import modele.Menu;
import modele.ModelePageFacture;
import modele.ResizedImage;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class PageFacture {

    private JFrame frame;
    private JLabel logo;
    private JComboBox choix_type;
    private JTextField choix_num_facture;
    private  JTextField choix_montant;
    private  JDateChooser dateChooser;
    private  JPanel contenu;
    private  JLabel montant;
    private JLabel date;
    private int id_bail;
    private JLabel label_index=new JLabel("Index d'eau");
    private JTextField choix_index=new JTextField();
    private JLabel label_prix_conso=new JLabel("Prix m³ d'eau");
    private JTextField choix_prix_conso=new JTextField();
    private JButton valider;
    private GridBagConstraints gbc_montant;
    public PageFacture(int id_bail) {
        this.initialize();
        this.id_bail = id_bail;
    }

    private void initialize() {
        ModelePageFacture modele = new ModelePageFacture(this);
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

        Menu m = new Menu(this.frame);

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
        b_biens.addActionListener(m);

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
        this.frame.getContentPane().add(body, BorderLayout.CENTER);
        body.setLayout(new BorderLayout(0, 0));

        JPanel titre = new JPanel();
        FlowLayout fl_titre = (FlowLayout) titre.getLayout();
        body.add(titre, BorderLayout.NORTH);

        JLabel titrePage = new JLabel("Ajout de facture");
        titrePage.setAlignmentY(0.0f);
        titrePage.setAlignmentX(0.5f);
        titre.add(titrePage);

        contenu = new JPanel();
        body.add(contenu, BorderLayout.CENTER);
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
        choix_type.addItem((Object) "Entretien");
        choix_type.addItem((Object) "Electricité");
        choix_type.addItem((Object) "Ordures");
        choix_type.addItem((Object) "Eau");
        GridBagConstraints gbc_choix_type = new GridBagConstraints();
        gbc_choix_type.insets = new Insets(0, 0, 5, 5);
        gbc_choix_type.fill = GridBagConstraints.HORIZONTAL;
        gbc_choix_type.gridx = 2;
        gbc_choix_type.gridy = 0;
        contenu.add(choix_type, gbc_choix_type);
        choix_type.addActionListener(modele.eauSelected());

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

        dateChooser = new JDateChooser();
        dateChooser.setPreferredSize(new Dimension(100, 22));
        GridBagConstraints gbc_a_REMPLACER_POUR_DATE = new GridBagConstraints();
        gbc_a_REMPLACER_POUR_DATE.insets = new Insets(0, 0, 5, 5);
        gbc_a_REMPLACER_POUR_DATE.fill = GridBagConstraints.HORIZONTAL;
        gbc_a_REMPLACER_POUR_DATE.gridx = 2;
        gbc_a_REMPLACER_POUR_DATE.gridy = 3;
        contenu.add(dateChooser, gbc_a_REMPLACER_POUR_DATE);


        JPanel bas_de_page = new JPanel();
        this.frame.getContentPane().add(bas_de_page, BorderLayout.SOUTH);
        bas_de_page.setLayout(new BorderLayout(0, 0));

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

        this.frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                ResizedImage res = new ResizedImage();
                res.resizeImage("logo+nom.png", PageFacture.this.frame,
                        PageFacture.this.logo, 3, 8);
                int frameWidth = PageFacture.this.frame.getWidth();
                int frameHeight = PageFacture.this.frame.getHeight();

                int newFontSize = Math.min(frameWidth, frameHeight) / 30;

                // Appliquer la nouvelle police au bouton
                Font resizedFont = new Font("Arial", Font.PLAIN, newFontSize);
                b_baux.setFont(resizedFont);
                b_accueil.setFont(resizedFont);
                b_biens.setFont(resizedFont);
            }
        });

        choix_num_facture.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
        choix_montant.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
        dateChooser.getDateEditor().addPropertyChangeListener("date", evt -> modele.getTextFieldDocumentListener().insertUpdate(null));
    }

    public Frame getFrame() {
        return this.frame;
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
        return dateChooser;
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

    public GridBagConstraints getGbc_montant() {
        return gbc_montant;
    }

    public void checkFields() {
        // Vérification si tous les champs sont remplis
        boolean isFilled = (!choix_num_facture.getText().trim().isEmpty() && !choix_montant.getText().trim().isEmpty()
                && dateChooser.getDate() != null) || (!choix_num_facture.getText().trim().isEmpty()  && dateChooser.getDate() != null
                && !choix_index.getText().trim().isEmpty() && !choix_prix_conso.getText().trim().isEmpty());

        // Active ou désactive le bouton "Valider"
        valider.setEnabled(isFilled);
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
}
