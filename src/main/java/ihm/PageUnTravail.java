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

public class PageUnTravail {

    public JFrame getFrame() {
        return this.frame;
    }

    private JFrame frame;
    private JLabel logo;
    private JLabel valueNumDevis;
    private JLabel valueNumFacture;
    private JLabel valueMontantDevis;
    private JLabel valueMontantTravaux;
    private JLabel valueNature;
    private JLabel ValueAdresse;
    private JLabel valueNom;
    private JLabel valueType;
    private JLabel valueDateDebut;
    private JLabel valueDateFin;


    /**
     * Create the application.
     */
    public PageUnTravail(Integer id, TypeLogement typeLogement, Integer idTravail) throws DAOException {
        this.initialize(id, idTravail,typeLogement);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize(Integer id, Integer idTravail, TypeLogement typelogement) throws DAOException {
        valueNumDevis = new JLabel();
        valueNumFacture = new JLabel();
        valueMontantDevis = new JLabel();
        valueMontantTravaux = new JLabel();
        valueNature = new JLabel();
        ValueAdresse = new JLabel();
        valueNom = new JLabel();
        valueType = new JLabel();
        valueDateDebut = new JLabel();
        valueDateFin = new JLabel();

        ModelePageUnTravail modele = new  ModelePageUnTravail(this);
        try {
            // Chargement des données du bien
            modele.chargerDonneesTravail(idTravail, this);
        } catch (DAOException e) {
            JOptionPane.showMessageDialog(frame, "Erreur lors du chargement des données du bien : " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
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


        GridBagConstraints gbc_valueNumDevis = new GridBagConstraints();
        gbc_valueNumDevis.insets = new Insets(0, 0, 5, 0);
        gbc_valueNumDevis.anchor = GridBagConstraints.WEST;
        gbc_valueNumDevis.gridx = 1;
        gbc_valueNumDevis.gridy = 0;
        valeurs.add(valueNumDevis, gbc_valueNumDevis);

        JLabel labelNumFacture = new JLabel("Numero facture");
        GridBagConstraints gbc_labelNumFacture = new GridBagConstraints();
        gbc_labelNumFacture.insets = new Insets(0, 0, 5, 5);
        gbc_labelNumFacture.anchor = GridBagConstraints.WEST;
        gbc_labelNumFacture.gridx = 0;
        gbc_labelNumFacture.gridy = 1;
        valeurs.add(labelNumFacture, gbc_labelNumFacture);


        GridBagConstraints gbc_valueNumFacture = new GridBagConstraints();
        gbc_valueNumFacture.insets = new Insets(0, 0, 5, 0);
        gbc_valueNumFacture.anchor = GridBagConstraints.WEST;
        gbc_valueNumFacture.gridx = 1;
        gbc_valueNumFacture.gridy = 1;
        valeurs.add(valueNumFacture, gbc_valueNumFacture);

        JLabel labelMontantDevis = new JLabel("Montant du devis");
        GridBagConstraints gbc_labelMontantDevis = new GridBagConstraints();
        gbc_labelMontantDevis.anchor = GridBagConstraints.SOUTHWEST;
        gbc_labelMontantDevis.insets = new Insets(0, 0, 5, 5);
        gbc_labelMontantDevis.gridx = 0;
        gbc_labelMontantDevis.gridy = 2;
        valeurs.add(labelMontantDevis, gbc_labelMontantDevis);


        GridBagConstraints gbc_valueMontantDevis = new GridBagConstraints();
        gbc_valueMontantDevis.anchor = GridBagConstraints.WEST;
        gbc_valueMontantDevis.insets = new Insets(0, 0, 5, 0);
        gbc_valueMontantDevis.gridx = 1;
        gbc_valueMontantDevis.gridy = 2;
        valeurs.add(valueMontantDevis, gbc_valueMontantDevis);

        JLabel labelMontantTeavaux = new JLabel("Montant des travaux");
        GridBagConstraints gbc_labelMontantTeavaux = new GridBagConstraints();
        gbc_labelMontantTeavaux.anchor = GridBagConstraints.WEST;
        gbc_labelMontantTeavaux.insets = new Insets(0, 0, 5, 5);
        gbc_labelMontantTeavaux.gridx = 0;
        gbc_labelMontantTeavaux.gridy = 3;
        valeurs.add(labelMontantTeavaux, gbc_labelMontantTeavaux);


        GridBagConstraints gbc_valueMontantTravaux = new GridBagConstraints();
        gbc_valueMontantTravaux.anchor = GridBagConstraints.WEST;
        gbc_valueMontantTravaux.insets = new Insets(0, 0, 5, 0);
        gbc_valueMontantTravaux.gridx = 1;
        gbc_valueMontantTravaux.gridy = 3;
        valeurs.add(valueMontantTravaux, gbc_valueMontantTravaux);

        JLabel labelNature = new JLabel("Nature");
        GridBagConstraints gbc_labelNature = new GridBagConstraints();
        gbc_labelNature.anchor = GridBagConstraints.WEST;
        gbc_labelNature.insets = new Insets(0, 0, 5, 5);
        gbc_labelNature.gridx = 0;
        gbc_labelNature.gridy = 4;
        valeurs.add(labelNature, gbc_labelNature);


        GridBagConstraints gbc_valueNature = new GridBagConstraints();
        gbc_valueNature.anchor = GridBagConstraints.WEST;
        gbc_valueNature.insets = new Insets(0, 0, 5, 0);
        gbc_valueNature.gridx = 1;
        gbc_valueNature.gridy = 4;
        valeurs.add(valueNature, gbc_valueNature);

        JLabel labelAdresse = new JLabel("Adresse de l'entreprise");
        GridBagConstraints gbc_labelAdresse = new GridBagConstraints();
        gbc_labelAdresse.anchor = GridBagConstraints.WEST;
        gbc_labelAdresse.insets = new Insets(0, 0, 5, 5);
        gbc_labelAdresse.gridx = 0;
        gbc_labelAdresse.gridy = 5;
        valeurs.add(labelAdresse, gbc_labelAdresse);


        GridBagConstraints gbc_valueAdresse = new GridBagConstraints();
        gbc_valueAdresse.anchor = GridBagConstraints.WEST;
        gbc_valueAdresse.insets = new Insets(0, 0, 5, 0);
        gbc_valueAdresse.gridx = 1;
        gbc_valueAdresse.gridy = 5;
        valeurs.add(ValueAdresse, gbc_valueAdresse);

        JLabel LabelNom = new JLabel("Nom de l'entreprise");
        GridBagConstraints gbc_LabelNom = new GridBagConstraints();
        gbc_LabelNom.anchor = GridBagConstraints.WEST;
        gbc_LabelNom.insets = new Insets(0, 0, 5, 5);
        gbc_LabelNom.gridx = 0;
        gbc_LabelNom.gridy = 6;
        valeurs.add(LabelNom, gbc_LabelNom);


        GridBagConstraints gbc_valueNom = new GridBagConstraints();
        gbc_valueNom.anchor = GridBagConstraints.WEST;
        gbc_valueNom.insets = new Insets(0, 0, 5, 0);
        gbc_valueNom.gridx = 1;
        gbc_valueNom.gridy = 6;
        valeurs.add(valueNom, gbc_valueNom);

        JLabel labelType = new JLabel("Type");
        GridBagConstraints gbc_labelType = new GridBagConstraints();
        gbc_labelType.anchor = GridBagConstraints.WEST;
        gbc_labelType.insets = new Insets(0, 0, 5, 5);
        gbc_labelType.gridx = 0;
        gbc_labelType.gridy = 7;
        valeurs.add(labelType, gbc_labelType);


        GridBagConstraints gbc_valueType = new GridBagConstraints();
        gbc_valueType.anchor = GridBagConstraints.WEST;
        gbc_valueType.insets = new Insets(0, 0, 5, 0);
        gbc_valueType.gridx = 1;
        gbc_valueType.gridy = 7;
        valeurs.add(valueType, gbc_valueType);

        JLabel labelDateDebut = new JLabel("Date de début");
        GridBagConstraints gbc_labelDateDebut = new GridBagConstraints();
        gbc_labelDateDebut.anchor = GridBagConstraints.WEST;
        gbc_labelDateDebut.insets = new Insets(0, 0, 5, 5);
        gbc_labelDateDebut.gridx = 0;
        gbc_labelDateDebut.gridy = 8;
        valeurs.add(labelDateDebut, gbc_labelDateDebut);


        GridBagConstraints gbc_valueDateDebut = new GridBagConstraints();
        gbc_valueDateDebut.anchor = GridBagConstraints.WEST;
        gbc_valueDateDebut.insets = new Insets(0, 0, 5, 0);
        gbc_valueDateDebut.gridx = 1;
        gbc_valueDateDebut.gridy = 8;
        valeurs.add(valueDateDebut, gbc_valueDateDebut);

        JLabel labelDateFin = new JLabel("Date de fin");
        GridBagConstraints gbc_labelDateFin = new GridBagConstraints();
        gbc_labelDateFin.anchor = GridBagConstraints.WEST;
        gbc_labelDateFin.insets = new Insets(0, 0, 5, 5);
        gbc_labelDateFin.gridx = 0;
        gbc_labelDateFin.gridy = 9;
        valeurs.add(labelDateFin, gbc_labelDateFin);

        
        GridBagConstraints gbc_valueDateFin = new GridBagConstraints();
        gbc_valueDateFin.insets = new Insets(0, 0, 5, 0);
        gbc_valueDateFin.anchor = GridBagConstraints.WEST;
        gbc_valueDateFin.gridx = 1;
        gbc_valueDateFin.gridy = 9;
        valeurs.add(valueDateFin, gbc_valueDateFin);

        JPanel panelValider = new JPanel();
        Body.add(panelValider, BorderLayout.SOUTH);
        panelValider.setLayout(new BorderLayout(0, 0));

        JButton btnQuitter = new JButton("Quitter");
        panelValider.add(btnQuitter, BorderLayout.WEST);
        btnQuitter.addActionListener(modele.quitterPage(id,typelogement));

        JButton btnSupprimer = new JButton("Supprimer");
        panelValider.add(btnSupprimer, BorderLayout.EAST);
        b_biens.addActionListener(m);

        this.frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                ResizedImage res = new ResizedImage();
                res.resizeImage("logo+nom.png", PageUnTravail.this.frame,
                        PageUnTravail.this.logo, 3, 8);
                int frameWidth = PageUnTravail.this.frame.getWidth();
                int frameHeight = PageUnTravail.this.frame.getHeight();

                int newFontSize = Math.min(frameWidth, frameHeight) / 30;

                // Appliquer la nouvelle police au bouton
                Font resizedFont = new Font("Arial", Font.PLAIN, newFontSize);
                b_baux.setFont(resizedFont);
                b_accueil.setFont(resizedFont);
                b_biens.setFont(resizedFont);
            }
        });
        frame.setVisible(true);
        btnSupprimer.addActionListener(modele.getSupprimerTravauxListener(idTravail,id,typelogement));
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
    public JLabel getValueNumDevis() {
        return valueNumDevis;
    }

    public JLabel getValueNumFacture() {
        return valueNumFacture;
    }

    public JLabel getValueMontantDevis() {
        return valueMontantDevis;
    }

    public JLabel getValueMontantTravaux() {
        return valueMontantTravaux;
    }

    public JLabel getValueNature() {
        return valueNature;
    }

    public JLabel getValueAdresse() {
        return ValueAdresse;
    }

    public JLabel getValueNom() {
        return valueNom;
    }

    public JLabel getValueType() {
        return valueType;
    }

    public JLabel getValueDateDebut() {
        return valueDateDebut;
    }

    public JLabel getValueDateFin() {
        return valueDateFin;
    }
}
