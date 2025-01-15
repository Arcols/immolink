package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import modele.Menu;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import com.toedter.calendar.JDateChooser;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import enumeration.TypeLogement;
import modele.*;

public class PageNouveauTravaux {

    public JFrame getFrame() {
        return this.frame;
    }

    private JFrame frame;
    private JLabel logo;
    private JTextField valueNumDevis;
    private JTextField valueNumFacture;
    private JTextField valueMontantDevis;
    private JTextField valueMontantTravaux;
    private JTextField valueNature;
    private JTextField ValueAdresse;
    private JTextField valueNom;
    private JTextField valueType;
    private JButton btnValider;

    private JDateChooser dateChooserDebut;
    private JDateChooser dateChooserFacture;

    /**
     * Create the application.
     */
    public PageNouveauTravaux(Integer id, TypeLogement typeLogement) throws DAOException {
        this.initialize(id,typeLogement);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize(Integer id,TypeLogement typeLogement) throws DAOException {
        ModelePageNouveauTravaux modele = new ModelePageNouveauTravaux(this);
        this.frame = new JFrame();
        this.frame.setBounds(100, 100, 750, 400);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel entete = new JPanel();
        this.frame.getContentPane().add(entete, BorderLayout.NORTH);
        entete.setLayout(new BorderLayout(0, 0));
        this.frame.getContentPane().setBackground(Charte.FOND.getCouleur());

        entete.setBackground(Charte.ENTETE.getCouleur());
        entete.setBorder(new LineBorder(Color.BLACK, 2));

        this.logo = new JLabel("");
        entete.add(this.logo, BorderLayout.WEST);

        JPanel menu_bouttons = new JPanel();

        this.frame.getContentPane().add(entete, BorderLayout.NORTH);
        entete.setLayout(new BorderLayout(0, 0));
        this.frame.getContentPane().setBackground(Charte.FOND.getCouleur());

        entete.setBackground(Charte.ENTETE.getCouleur());
        entete.setBorder(new LineBorder(Color.BLACK, 2));

        this.logo = new JLabel("");
        entete.add(this.logo, BorderLayout.WEST);

        Menu m = new Menu(this.frame);

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

        JPanel Body = new JPanel();
        frame.getContentPane().add(Body, BorderLayout.CENTER);
        Body.setLayout(new BorderLayout(0, 0));

        JLabel labelTitre = new JLabel("Nouveau travaux");
        labelTitre.setHorizontalAlignment(SwingConstants.CENTER);
        Body.add(labelTitre, BorderLayout.NORTH);

        JPanel valeurs = new JPanel();
        Body.add(valeurs, BorderLayout.CENTER);
        GridBagLayout gbl_valeurs = new GridBagLayout();
        gbl_valeurs.columnWidths = new int[] {0};
        gbl_valeurs.rowHeights = new int[] {0};
        gbl_valeurs.columnWeights = new double[]{0.0, 0.0};
        gbl_valeurs.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        valeurs.setLayout(gbl_valeurs);

        JLabel labelNumDevis = new JLabel("Numero devis");
        GridBagConstraints gbc_labelNumDevis = new GridBagConstraints();
        gbc_labelNumDevis.insets = new Insets(0, 0, 5, 5);
        gbc_labelNumDevis.anchor = GridBagConstraints.WEST;
        gbc_labelNumDevis.gridx = 0;
        gbc_labelNumDevis.gridy = 0;
        valeurs.add(labelNumDevis, gbc_labelNumDevis);

        valueNumDevis = new JFormattedTextField();
        GridBagConstraints gbc_valueNumDevis = new GridBagConstraints();
        gbc_valueNumDevis.insets = new Insets(0, 0, 5, 0);
        gbc_valueNumDevis.anchor = GridBagConstraints.WEST;
        gbc_valueNumDevis.gridx = 1;
        gbc_valueNumDevis.gridy = 0;
        valeurs.add(valueNumDevis, gbc_valueNumDevis);
        valueNumDevis.setColumns(10);

        JLabel labelNumFacture = new JLabel("Numero facture");
        GridBagConstraints gbc_labelNumFacture = new GridBagConstraints();
        gbc_labelNumFacture.insets = new Insets(0, 0, 5, 5);
        gbc_labelNumFacture.anchor = GridBagConstraints.WEST;
        gbc_labelNumFacture.gridx = 0;
        gbc_labelNumFacture.gridy = 1;
        valeurs.add(labelNumFacture, gbc_labelNumFacture);

        valueNumFacture = new JTextField();
        GridBagConstraints gbc_valueNumFacture = new GridBagConstraints();
        gbc_valueNumFacture.insets = new Insets(0, 0, 5, 0);
        gbc_valueNumFacture.anchor = GridBagConstraints.WEST;
        gbc_valueNumFacture.gridx = 1;
        gbc_valueNumFacture.gridy = 1;
        valeurs.add(valueNumFacture, gbc_valueNumFacture);
        valueNumFacture.setColumns(10);

        JLabel labelMontantDevis = new JLabel("Montant du devis");
        GridBagConstraints gbc_labelMontantDevis = new GridBagConstraints();
        gbc_labelMontantDevis.anchor = GridBagConstraints.WEST;
        gbc_labelMontantDevis.insets = new Insets(0, 0, 5, 5);
        gbc_labelMontantDevis.gridx = 0;
        gbc_labelMontantDevis.gridy = 2;
        valeurs.add(labelMontantDevis, gbc_labelMontantDevis);

        valueMontantDevis = new JTextField();
        GridBagConstraints gbc_valueMontantDevis = new GridBagConstraints();
        gbc_valueMontantDevis.anchor = GridBagConstraints.WEST;
        gbc_valueMontantDevis.insets = new Insets(0, 0, 5, 0);
        gbc_valueMontantDevis.gridx = 1;
        gbc_valueMontantDevis.gridy = 2;
        valeurs.add(valueMontantDevis, gbc_valueMontantDevis);
        valueMontantDevis.setColumns(10);

        JLabel labelMontantTeavaux = new JLabel("Montant des travaux");
        GridBagConstraints gbc_labelMontantTeavaux = new GridBagConstraints();
        gbc_labelMontantTeavaux.anchor = GridBagConstraints.WEST;
        gbc_labelMontantTeavaux.insets = new Insets(0, 0, 5, 5);
        gbc_labelMontantTeavaux.gridx = 0;
        gbc_labelMontantTeavaux.gridy = 3;
        valeurs.add(labelMontantTeavaux, gbc_labelMontantTeavaux);

        valueMontantTravaux = new JTextField();
        GridBagConstraints gbc_valueMontantTravaux = new GridBagConstraints();
        gbc_valueMontantTravaux.anchor = GridBagConstraints.WEST;
        gbc_valueMontantTravaux.insets = new Insets(0, 0, 5, 0);
        gbc_valueMontantTravaux.gridx = 1;
        gbc_valueMontantTravaux.gridy = 3;
        valeurs.add(valueMontantTravaux, gbc_valueMontantTravaux);
        valueMontantTravaux.setColumns(10);

        JLabel labelNature = new JLabel("Nature");
        GridBagConstraints gbc_labelNature = new GridBagConstraints();
        gbc_labelNature.anchor = GridBagConstraints.WEST;
        gbc_labelNature.insets = new Insets(0, 0, 5, 5);
        gbc_labelNature.gridx = 0;
        gbc_labelNature.gridy = 4;
        valeurs.add(labelNature, gbc_labelNature);

        valueNature = new JTextField();
        GridBagConstraints gbc_valueNature = new GridBagConstraints();
        gbc_valueNature.anchor = GridBagConstraints.WEST;
        gbc_valueNature.insets = new Insets(0, 0, 5, 0);
        gbc_valueNature.gridx = 1;
        gbc_valueNature.gridy = 4;
        valeurs.add(valueNature, gbc_valueNature);
        valueNature.setColumns(10);

        JLabel labelAdresse = new JLabel("Adresse de l'entreprise");
        GridBagConstraints gbc_labelAdresse = new GridBagConstraints();
        gbc_labelAdresse.anchor = GridBagConstraints.WEST;
        gbc_labelAdresse.insets = new Insets(0, 0, 5, 5);
        gbc_labelAdresse.gridx = 0;
        gbc_labelAdresse.gridy = 5;
        valeurs.add(labelAdresse, gbc_labelAdresse);

        ValueAdresse = new JTextField();
        GridBagConstraints gbc_valueAdresse = new GridBagConstraints();
        gbc_valueAdresse.anchor = GridBagConstraints.WEST;
        gbc_valueAdresse.insets = new Insets(0, 0, 5, 0);
        gbc_valueAdresse.gridx = 1;
        gbc_valueAdresse.gridy = 5;
        valeurs.add(ValueAdresse, gbc_valueAdresse);
        ValueAdresse.setColumns(10);

        JLabel LabelNom = new JLabel("Nom de l'entreprise");
        GridBagConstraints gbc_LabelNom = new GridBagConstraints();
        gbc_LabelNom.anchor = GridBagConstraints.WEST;
        gbc_LabelNom.insets = new Insets(0, 0, 5, 5);
        gbc_LabelNom.gridx = 0;
        gbc_LabelNom.gridy = 6;
        valeurs.add(LabelNom, gbc_LabelNom);

        valueNom = new JTextField();
        GridBagConstraints gbc_valueNom = new GridBagConstraints();
        gbc_valueNom.anchor = GridBagConstraints.WEST;
        gbc_valueNom.insets = new Insets(0, 0, 5, 0);
        gbc_valueNom.gridx = 1;
        gbc_valueNom.gridy = 6;
        valeurs.add(valueNom, gbc_valueNom);
        valueNom.setColumns(10);

        JLabel labelType = new JLabel("Type");
        GridBagConstraints gbc_labelType = new GridBagConstraints();
        gbc_labelType.anchor = GridBagConstraints.WEST;
        gbc_labelType.insets = new Insets(0, 0, 5, 5);
        gbc_labelType.gridx = 0;
        gbc_labelType.gridy = 7;
        valeurs.add(labelType, gbc_labelType);

        valueType = new JTextField();
        GridBagConstraints gbc_valueType = new GridBagConstraints();
        gbc_valueType.anchor = GridBagConstraints.WEST;
        gbc_valueType.insets = new Insets(0, 0, 5, 0);
        gbc_valueType.gridx = 1;
        gbc_valueType.gridy = 7;
        valeurs.add(valueType, gbc_valueType);
        valueType.setColumns(10);

        JLabel labelDateDebut = new JLabel("Date de début");
        GridBagConstraints gbc_labelDateDebut = new GridBagConstraints();
        gbc_labelDateDebut.anchor = GridBagConstraints.WEST;
        gbc_labelDateDebut.insets = new Insets(0, 0, 5, 5);
        gbc_labelDateDebut.gridx = 0;
        gbc_labelDateDebut.gridy = 8;
        valeurs.add(labelDateDebut, gbc_labelDateDebut);

        dateChooserDebut = new JDateChooser();
        dateChooserDebut.setPreferredSize(new Dimension(115, 22));
        GridBagConstraints gbc_dateChooserDebut = new GridBagConstraints();
        gbc_dateChooserDebut.anchor = GridBagConstraints.WEST;
        gbc_dateChooserDebut.insets = new Insets(0, 0, 5, 0);
        gbc_dateChooserDebut.gridx = 1;
        gbc_dateChooserDebut.gridy = 8;
        valeurs.add(dateChooserDebut, gbc_dateChooserDebut);

        JLabel labelDateFacture = new JLabel("Date de facture");
        GridBagConstraints gbc_labelDateFacture = new GridBagConstraints();
        gbc_labelDateFacture.anchor = GridBagConstraints.WEST;
        gbc_labelDateFacture.insets = new Insets(0, 0, 5, 5);
        gbc_labelDateFacture.gridx = 0;
        gbc_labelDateFacture.gridy = 9;
        valeurs.add(labelDateFacture, gbc_labelDateFacture);

        dateChooserFacture = new JDateChooser();
        dateChooserFacture.setPreferredSize(new Dimension(115, 22));
        GridBagConstraints gbc_dateChooserFacture = new GridBagConstraints();
        gbc_dateChooserFacture.anchor = GridBagConstraints.WEST;
        gbc_dateChooserFacture.insets = new Insets(0, 0, 5, 0);
        gbc_dateChooserFacture.gridx = 1;
        gbc_dateChooserFacture.gridy = 9;
        valeurs.add(dateChooserFacture, gbc_dateChooserFacture);

        JPanel panelValider = new JPanel();
        Body.add(panelValider, BorderLayout.SOUTH);
        panelValider.setLayout(new BorderLayout(0, 0));

        JButton btnQuitter = new JButton("Quitter");
        panelValider.add(btnQuitter, BorderLayout.WEST);
        btnQuitter.addActionListener(modele.quitterPage(id,typeLogement));

        this.btnValider = new JButton("Valider");
        this.btnValider.setEnabled(false);
        panelValider.add(this.btnValider, BorderLayout.EAST);
        b_biens.addActionListener(m);

        this.frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                ResizedImage res = new ResizedImage();
                res.resizeImage("logo+nom.png", PageNouveauTravaux.this.frame,
                        PageNouveauTravaux.this.logo, 3, 8);
                int frameWidth = PageNouveauTravaux.this.frame.getWidth();
                int frameHeight = PageNouveauTravaux.this.frame.getHeight();

                int newFontSize = Math.min(frameWidth, frameHeight) / 30;

                // Appliquer la nouvelle police au bouton
                Font resizedFont = new Font("Arial", Font.PLAIN, newFontSize);
                b_baux.setFont(resizedFont);
                b_accueil.setFont(resizedFont);
                b_biens.setFont(resizedFont);
            }
        });
        frame.setVisible(true);
        valueNumDevis.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
        valueNumFacture.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
        valueMontantDevis.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
        valueMontantTravaux.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
        valueNature.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
        ValueAdresse.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
        valueNom.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
        valueType.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
        dateChooserDebut.getDateEditor().addPropertyChangeListener("date", evt -> modele.getTextFieldDocumentListener().insertUpdate(null));
        dateChooserFacture.getDateEditor().addPropertyChangeListener("date", evt -> modele.getTextFieldDocumentListener().insertUpdate(null));
        btnValider.addActionListener(modele.getAjouterTravauxListener(id,typeLogement));
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Action to perform on application close
                performCloseAction();
            }
        });
    }

    private void performCloseAction() {
        ConnectionDB.destroy(); // fermeture de la connection
        frame.dispose();
    }

    public JTextField getValueNumDevis() {
        return valueNumDevis;
    }

    public JTextField getValueNumFacture() {
        return valueNumFacture;
    }

    public JTextField getValueMontantDevis() {
        return valueMontantDevis;
    }

    public JTextField getValueMontantTravaux() {
        return valueMontantTravaux;
    }

    public JTextField getValueNature() {
        return valueNature;
    }

    public JTextField getValueAdresse() {
        return ValueAdresse;
    }

    public JTextField getValueNom() {
        return valueNom;
    }

    public JTextField getValueType() {
        return valueType;
    }

    public JDateChooser getDateChooserDebut() {
        return dateChooserDebut;
    }

    public JDateChooser getDateChooserFacture() {
        return dateChooserFacture;
    }

    public void checkFields() {
        // Vérification si tous les champs sont remplis
        boolean isFilled = !valueMontantDevis.getText().trim().isEmpty() && !valueMontantDevis.getText().trim().isEmpty()
                && !valueMontantTravaux.getText().trim().isEmpty() && !valueNature.getText().trim().isEmpty()
                && !ValueAdresse.getText().trim().isEmpty()
                && !valueNom.getText().trim().isEmpty()
                && getDateChooserDebut().getDate() != null
                && getDateChooserFacture().getDate() != null;

        // Active ou désactive le bouton "Valider"
        this.btnValider.setEnabled(isFilled);
    }
}
