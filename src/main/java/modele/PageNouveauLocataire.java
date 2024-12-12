package modele;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import DAO.jdbc.LocataireDAO;
import classes.Locataire;
import ihm.Charte;
import ihm.Menu;
import ihm.ModelePageNouveauLocataire;
import ihm.ResizedImage;

public class PageNouveauLocataire {

    private JFrame frame;
    private JLabel logo;
    private JTextField nomValeur;
    private JTextField prenomValeur;
    private JTextField telephoneValeur;
    private JTextField mailValeur;
    private JTextField dateValeur;
    private JComboBox villeValeur;
    private JComboBox genreValeur;
    private JComboBox adresseValeur;
    private JButton enregistrerButton;
    private Map<String, List<String>> mapVillesAdresses;
    private LocataireDAO daoLoc;
    private Set<String> setVilles;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    PageNouveauLocataire window = new PageNouveauLocataire();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public JFrame getFrame() {
        return frame;
    }

    /**
     * Create the application.
     */
    public PageNouveauLocataire() {
        this.initialize();
    }

    /**
     * Initialize the contents of the frame.
     *
     * @return
     */
    private JTextField initialize() {
        ModelePageNouveauLocataire modele = new ModelePageNouveauLocataire(this);
        try {
            DAO.jdbc.BatimentDAO tousBat = new DAO.jdbc.BatimentDAO();
            mapVillesAdresses = tousBat.searchAllBatiments();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.setVilles = this.mapVillesAdresses.keySet();
        this.frame = new JFrame();
        this.frame.setBounds(100, 100, 750, 400);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel entete = new JPanel();
        this.frame.getContentPane().add(entete, BorderLayout.NORTH);
        entete.setLayout(new BorderLayout(0, 0));
        this.frame.getContentPane().setBackground(Charte.FOND.getCouleur());
    }
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize {
		ModelePageNouveauLocataire modele = new ModelePageNouveauLocataire(this);
		try {
			DAO.jdbc.BatimentDAO tousBat = new DAO.jdbc.BatimentDAO();
			mapVillesAdresses = tousBat.searchAllBatiments();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setVilles = this.mapVillesAdresses.keySet();
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
        frame.getContentPane().add(body, BorderLayout.CENTER);
        body.setLayout(new BorderLayout(0, 0));

        JPanel panelTitre = new JPanel();
        body.add(panelTitre, BorderLayout.NORTH);

        JLabel labelTitre = new JLabel("Nouveau locataire");
        labelTitre.setFont(new Font("Tahoma", Font.BOLD, 15));
        panelTitre.add(labelTitre);
        labelTitre.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel donnees_loca = new JPanel();
        body.add(donnees_loca);
        GridBagLayout gbl_donnees_loca = new GridBagLayout();
        gbl_donnees_loca.columnWidths = new int[] { 40, 0, 0, 40, 0, 0, 0 };
        gbl_donnees_loca.rowHeights = new int[] { 40, 40, 40, 40, 40, 0 };
        gbl_donnees_loca.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
        gbl_donnees_loca.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
        donnees_loca.setLayout(gbl_donnees_loca);

        JLabel labelNom = new JLabel("Nom");
        GridBagConstraints gbc_labelNom = new GridBagConstraints();
        gbc_labelNom.anchor = GridBagConstraints.WEST;
        gbc_labelNom.insets = new Insets(0, 0, 5, 5);
        gbc_labelNom.gridx = 1;
        gbc_labelNom.gridy = 0;
        donnees_loca.add(labelNom, gbc_labelNom);

        nomValeur = new JTextField();
        GridBagConstraints gbc_nomValeur = new GridBagConstraints();
        gbc_nomValeur.anchor = GridBagConstraints.WEST;
        gbc_nomValeur.insets = new Insets(0, 0, 5, 5);
        gbc_nomValeur.gridx = 2;
        gbc_nomValeur.gridy = 0;
        donnees_loca.add(nomValeur, gbc_nomValeur);
        nomValeur.setColumns(10);

        JLabel labelPrenom = new JLabel("Prénom");
        GridBagConstraints gbc_labelPrenom = new GridBagConstraints();
        gbc_labelPrenom.anchor = GridBagConstraints.WEST;
        gbc_labelPrenom.insets = new Insets(0, 0, 5, 5);
        gbc_labelPrenom.gridx = 3;
        gbc_labelPrenom.gridy = 0;
        donnees_loca.add(labelPrenom, gbc_labelPrenom);

        prenomValeur = new JTextField();
        GridBagConstraints gbc_prenomValeur = new GridBagConstraints();
        gbc_prenomValeur.anchor = GridBagConstraints.WEST;
        gbc_prenomValeur.insets = new Insets(0, 0, 5, 5);
        gbc_prenomValeur.gridx = 4;
        gbc_prenomValeur.gridy = 0;
        donnees_loca.add(prenomValeur, gbc_prenomValeur);
        prenomValeur.setColumns(10);

        JLabel labelTelephone = new JLabel("Téléphone");
        GridBagConstraints gbc_labelTelephone = new GridBagConstraints();
        gbc_labelTelephone.anchor = GridBagConstraints.WEST;
        gbc_labelTelephone.insets = new Insets(0, 0, 5, 5);
        gbc_labelTelephone.gridx = 1;
        gbc_labelTelephone.gridy = 1;
        donnees_loca.add(labelTelephone, gbc_labelTelephone);

        telephoneValeur = new JTextField();
        GridBagConstraints gbc_telephoneValeur = new GridBagConstraints();
        gbc_telephoneValeur.anchor = GridBagConstraints.WEST;
        gbc_telephoneValeur.insets = new Insets(0, 0, 5, 5);
        gbc_telephoneValeur.gridx = 2;
        gbc_telephoneValeur.gridy = 1;
        donnees_loca.add(telephoneValeur, gbc_telephoneValeur);
        telephoneValeur.setColumns(10);

        JLabel labelMail = new JLabel("Mail");
        GridBagConstraints gbc_labelMail = new GridBagConstraints();
        gbc_labelMail.anchor = GridBagConstraints.WEST;
        gbc_labelMail.insets = new Insets(0, 0, 5, 5);
        gbc_labelMail.gridx = 3;
        gbc_labelMail.gridy = 1;
        donnees_loca.add(labelMail, gbc_labelMail);

        mailValeur = new JTextField();
        GridBagConstraints gbc_mailValeur = new GridBagConstraints();
        gbc_mailValeur.anchor = GridBagConstraints.WEST;
        gbc_mailValeur.insets = new Insets(0, 0, 5, 5);
        gbc_mailValeur.gridx = 4;
        gbc_mailValeur.gridy = 1;
        donnees_loca.add(mailValeur, gbc_mailValeur);
        mailValeur.setColumns(10);

        JLabel labelDate = new JLabel("Date arrivée");
        GridBagConstraints gbc_labelDate = new GridBagConstraints();
        gbc_labelDate.anchor = GridBagConstraints.WEST;
        gbc_labelDate.insets = new Insets(0, 0, 5, 5);
        gbc_labelDate.gridx = 3;
        gbc_labelDate.gridy = 2;
        donnees_loca.add(labelDate, gbc_labelDate);

        dateValeur = new JTextField();
        GridBagConstraints gbc_dateValeur = new GridBagConstraints();
        gbc_dateValeur.fill = GridBagConstraints.HORIZONTAL;
        gbc_dateValeur.insets = new Insets(0, 0, 5, 5);
        gbc_dateValeur.gridx = 4;
        gbc_dateValeur.gridy = 2;
        donnees_loca.add(dateValeur, gbc_dateValeur);
        dateValeur.setColumns(10);

        JLabel labelGenre = new JLabel("Genre");
        GridBagConstraints gbc_labelGenre = new GridBagConstraints();
        gbc_labelGenre.anchor = GridBagConstraints.WEST;
        gbc_labelGenre.insets = new Insets(0, 0, 5, 5);
        gbc_labelGenre.gridx = 1;
        gbc_labelGenre.gridy = 2;
        donnees_loca.add(labelGenre, gbc_labelGenre);

        this.genreValeur = new JComboBox();
        this.genreValeur.setModel(new DefaultComboBoxModel(new String[] { "H", "F", "O" }));
        GridBagConstraints gbc_genreValeur = new GridBagConstraints();
        gbc_genreValeur.fill = GridBagConstraints.HORIZONTAL;
        gbc_genreValeur.insets = new Insets(0, 0, 5, 5);
        gbc_genreValeur.gridx = 2;
        gbc_genreValeur.gridy = 2;
        donnees_loca.add(this.genreValeur, gbc_genreValeur);

        this.enregistrerButton = new JButton("Enregistrer");
        this.enregistrerButton.setEnabled(false);

        GridBagConstraints gbc_enregistrerButton = new GridBagConstraints();
        gbc_enregistrerButton.gridx = 6;
        gbc_enregistrerButton.gridy = 5;
        donnees_loca.add(enregistrerButton, gbc_enregistrerButton);

        JButton quitter = new JButton("Quitter");
        GridBagConstraints gbc_quitter = new GridBagConstraints();
        gbc_quitter.gridx = 1;
        gbc_quitter.gridy = 5;
        donnees_loca.add(quitter, gbc_quitter);

        
        JButton quitter = new JButton("Quitter");
        GridBagConstraints gbc_quitter = new GridBagConstraints();
        gbc_quitter.gridx = 1;
        gbc_quitter.gridy = 5;
        donnees_loca.add(quitter, gbc_quitter);

		enregistrerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				java.sql.Date sqlDate = java.sql.Date.valueOf(dateValeur.getText());
				daoLoc = new LocataireDAO();
				Locataire l = new Locataire(nomValeur.getText(), prenomValeur.getText(), telephoneValeur.getText(),
						mailValeur.getText(), sqlDate, (String) genreValeur.getSelectedItem());

			}
		});

		this.frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				ResizedImage res = new ResizedImage();
				res.resizeImage("logo+nom.png", PageNouveauLocataire.this.frame,
						PageNouveauLocataire.this.logo, 3, 8);
				int frameWidth = PageNouveauLocataire.this.frame.getWidth();
				int frameHeight = PageNouveauLocataire.this.frame.getHeight();

                int newFontSize = Math.min(frameWidth, frameHeight) / 30;

                // Appliquer la nouvelle police au bouton
                Font resizedFont = new Font("Arial", Font.PLAIN, newFontSize);
                b_baux.setFont(resizedFont);
                b_accueil.setFont(resizedFont);
                b_biens.setFont(resizedFont);
            }
        });

        enregistrerButton.addActionListener(modele.getAjouterLocataireListener());
        nomValeur.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
        prenomValeur.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
        telephoneValeur.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
        dateValeur.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
        quitter.addActionListener(modele.quitterBouton());
    }

    public JTextField getDateValeur() {
        return dateValeur;
    }

    public JTextField getNomValeur() {
        return nomValeur;
    }

    public JTextField getPrenomValeur() {
        return prenomValeur;
    }

    public JTextField getTelephoneValeur() {
        return telephoneValeur;
    }

    public JTextField getMailValeur() {
        return mailValeur;
    }

    public JComboBox getGenreValeur() {
        return genreValeur;
    }

    public JComboBox getAdresseValeur() {
        return adresseValeur;
    }

	public JComboBox getVilleValeur() {
		return villeValeur;
	}

    public void checkFields() {
        // Vérification si tous les champs sont remplis
        boolean isFilled = !nomValeur.getText().trim().isEmpty() && !prenomValeur.getText().trim().isEmpty()
                && !telephoneValeur.getText().trim().isEmpty() && !dateValeur.getText().trim().isEmpty();

        // Active ou désactive le bouton "Valider"
        enregistrerButton.setEnabled(isFilled);
    }
}