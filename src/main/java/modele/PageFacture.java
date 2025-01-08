package modele;

import DAO.DAOException;
import DAO.jdbc.BatimentDAO;
import DAO.jdbc.ChargeDAO;
import classes.Diagnostic;
import classes.Garage;
import enumeration.NomsDiags;
import enumeration.TypeLogement;
import ihm.Charte;
import ihm.Menu;
import ihm.ResizedImage;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;

public class PageFacture {

    private JFrame frame;
    private JLabel logo;

    public PageFacture(Integer idCharge) {
        this.initialize(idCharge);
    }

    private void initialize(Integer idCharge) {
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
        menu_bouttons.setLayout(new GridLayout(0, 3, 0, 0));
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

        JPanel contenu = new JPanel();
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

        JComboBox choix_type = new JComboBox();
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

        JTextField choix_num_facture = new JTextField();
        GridBagConstraints gbc_choix_num_facture = new GridBagConstraints();
        gbc_choix_num_facture.insets = new Insets(0, 0, 5, 5);
        gbc_choix_num_facture.fill = GridBagConstraints.HORIZONTAL;
        gbc_choix_num_facture.gridx = 2;
        gbc_choix_num_facture.gridy = 1;
        contenu.add(choix_num_facture, gbc_choix_num_facture);
        choix_num_facture.setColumns(10);

        JLabel montant = new JLabel("Montant");
        GridBagConstraints gbc_montant = new GridBagConstraints();
        gbc_montant.anchor = GridBagConstraints.EAST;
        gbc_montant.insets = new Insets(0, 0, 5, 5);
        gbc_montant.gridx = 1;
        gbc_montant.gridy = 2;
        contenu.add(montant, gbc_montant);

        JTextField choix_montant = new JTextField();
        GridBagConstraints gbc_choix_montant = new GridBagConstraints();
        gbc_choix_montant.insets = new Insets(0, 0, 5, 5);
        gbc_choix_montant.fill = GridBagConstraints.HORIZONTAL;
        gbc_choix_montant.gridx = 2;
        gbc_choix_montant.gridy = 2;
        contenu.add(choix_montant, gbc_choix_montant);
        choix_montant.setColumns(10);

        JLabel date = new JLabel("Date de la facture");
        GridBagConstraints gbc_date = new GridBagConstraints();
        gbc_date.insets = new Insets(0, 0, 5, 5);
        gbc_date.gridx = 1;
        gbc_date.gridy = 3;
        contenu.add(date, gbc_date);

        JTextField A_REMPLACER_POUR_DATE = new JTextField();
        GridBagConstraints gbc_a_REMPLACER_POUR_DATE = new GridBagConstraints();
        gbc_a_REMPLACER_POUR_DATE.insets = new Insets(0, 0, 5, 5);
        gbc_a_REMPLACER_POUR_DATE.fill = GridBagConstraints.HORIZONTAL;
        gbc_a_REMPLACER_POUR_DATE.gridx = 2;
        gbc_a_REMPLACER_POUR_DATE.gridy = 3;
        contenu.add(A_REMPLACER_POUR_DATE, gbc_a_REMPLACER_POUR_DATE);
        A_REMPLACER_POUR_DATE.setColumns(10);

        JPanel bas_de_page = new JPanel();
        this.frame.getContentPane().add(bas_de_page, BorderLayout.SOUTH);
        bas_de_page.setLayout(new BorderLayout(0, 0));

        JButton quitter = new JButton("Quitter");
        quitter.setHorizontalTextPosition(SwingConstants.LEFT);
        quitter.setVerticalTextPosition(SwingConstants.TOP);
        quitter.setVerticalAlignment(SwingConstants.BOTTOM);
        bas_de_page.add(quitter, BorderLayout.WEST);

        JButton valider = new JButton("Ajouter");
        bas_de_page.add(valider, BorderLayout.EAST);
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
    }
}
