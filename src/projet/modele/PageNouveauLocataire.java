package projet.modele;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import projet.ihm.Charte;
import projet.ihm.ResizedImage;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PageNouveauLocataire {

	private JFrame frame;
	private JLabel logo;
	private JTextField nomValeur;
	private JTextField prenomValeur;
	private JTextField telephoneValeur;
	private JTextField mailValeur;
	private JTextField dateValeur;

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

	/**
	 * Create the application.
	 */
	public PageNouveauLocataire() {
		this.initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
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

		entete.add(menu_bouttons, BorderLayout.CENTER);
		menu_bouttons.setLayout(new GridLayout(0, 5, 0, 0));
		menu_bouttons.setBackground(Charte.ENTETE.getCouleur());

		JButton b_accueil = new JButton("Accueil");
		b_accueil.setBorderPainted(false);
		b_accueil.setBackground(Charte.ENTETE.getCouleur());
		b_accueil.setCursor(new Cursor(Cursor.HAND_CURSOR));
		menu_bouttons.add(b_accueil);

		JButton b_profil = new JButton("Profil");
		b_profil.setBorderPainted(false);
		b_profil.setBackground(Charte.ENTETE.getCouleur());
		b_profil.setCursor(new Cursor(Cursor.HAND_CURSOR));
		menu_bouttons.add(b_profil);
		menu_bouttons.add(b_profil);

		JButton b_bails = new JButton("Mes bails");
		b_bails.setBorderPainted(false);
		b_bails.setBackground(Charte.ENTETE.getCouleur());
		b_bails.setCursor(new Cursor(Cursor.HAND_CURSOR));
		menu_bouttons.add(b_bails);
		menu_bouttons.add(b_bails);

		JButton b_loca = new JButton("Locataires");
		b_loca.setBorderPainted(false);
		b_loca.setBackground(Charte.ENTETE.getCouleur());
		b_loca.setCursor(new Cursor(Cursor.HAND_CURSOR));
		menu_bouttons.add(b_loca);
		menu_bouttons.add(b_loca);

		JButton b_biens = new JButton("Mes Biens");
		b_biens.setBorderPainted(false);
		b_biens.setBackground(Charte.ENTETE.getCouleur());
		b_biens.setCursor(new Cursor(Cursor.HAND_CURSOR));
		menu_bouttons.add(b_biens);
		menu_bouttons.add(b_biens);
		
		JPanel body = new JPanel();
		frame.getContentPane().add(body, BorderLayout.CENTER);
		body.setLayout(new BorderLayout(0, 0));
		
		JPanel panelTitre = new JPanel();
		body.add(panelTitre, BorderLayout.NORTH);
		
		JLabel labelTitre = new JLabel("Nouveau locataire");
		panelTitre.add(labelTitre);
		labelTitre.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel donnees_loca = new JPanel();
		body.add(donnees_loca);
		GridBagLayout gbl_donnees_loca = new GridBagLayout();
		gbl_donnees_loca.rowHeights = new int[] {40, 40, 40, 40, 0, 0};
		gbl_donnees_loca.columnWidths = new int[] {30, 0, 0, 100, 0, 0, 0};
		gbl_donnees_loca.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gbl_donnees_loca.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
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
		
		JLabel labelVille = new JLabel("Ville");
		GridBagConstraints gbc_labelVille = new GridBagConstraints();
		gbc_labelVille.anchor = GridBagConstraints.WEST;
		gbc_labelVille.insets = new Insets(0, 0, 5, 5);
		gbc_labelVille.gridx = 4;
		gbc_labelVille.gridy = 0;
		donnees_loca.add(labelVille, gbc_labelVille);
		
		JComboBox villeValeur = new JComboBox();
		GridBagConstraints gbc_villeValeur = new GridBagConstraints();
		gbc_villeValeur.fill = GridBagConstraints.HORIZONTAL;
		gbc_villeValeur.insets = new Insets(0, 0, 5, 5);
		gbc_villeValeur.gridx = 5;
		gbc_villeValeur.gridy = 0;
		donnees_loca.add(villeValeur, gbc_villeValeur);
		
		JLabel labelPrenom = new JLabel("Prénom");
		GridBagConstraints gbc_labelPrenom = new GridBagConstraints();
		gbc_labelPrenom.anchor = GridBagConstraints.WEST;
		gbc_labelPrenom.insets = new Insets(0, 0, 5, 5);
		gbc_labelPrenom.gridx = 1;
		gbc_labelPrenom.gridy = 1;
		donnees_loca.add(labelPrenom, gbc_labelPrenom);
		
		prenomValeur = new JTextField();
		GridBagConstraints gbc_prenomValeur = new GridBagConstraints();
		gbc_prenomValeur.anchor = GridBagConstraints.WEST;
		gbc_prenomValeur.insets = new Insets(0, 0, 5, 5);
		gbc_prenomValeur.gridx = 2;
		gbc_prenomValeur.gridy = 1;
		donnees_loca.add(prenomValeur, gbc_prenomValeur);
		prenomValeur.setColumns(10);
		
		JLabel labelAdresse = new JLabel("Adresse");
		GridBagConstraints gbc_labelAdresse = new GridBagConstraints();
		gbc_labelAdresse.anchor = GridBagConstraints.WEST;
		gbc_labelAdresse.insets = new Insets(0, 0, 5, 5);
		gbc_labelAdresse.gridx = 4;
		gbc_labelAdresse.gridy = 1;
		donnees_loca.add(labelAdresse, gbc_labelAdresse);
		
		JComboBox adresseValeur = new JComboBox();
		GridBagConstraints gbc_adresseValeur = new GridBagConstraints();
		gbc_adresseValeur.fill = GridBagConstraints.HORIZONTAL;
		gbc_adresseValeur.insets = new Insets(0, 0, 5, 5);
		gbc_adresseValeur.gridx = 5;
		gbc_adresseValeur.gridy = 1;
		donnees_loca.add(adresseValeur, gbc_adresseValeur);
		
		JLabel labelTelephone = new JLabel("Téléphone");
		GridBagConstraints gbc_labelTelephone = new GridBagConstraints();
		gbc_labelTelephone.anchor = GridBagConstraints.WEST;
		gbc_labelTelephone.insets = new Insets(0, 0, 5, 5);
		gbc_labelTelephone.gridx = 1;
		gbc_labelTelephone.gridy = 2;
		donnees_loca.add(labelTelephone, gbc_labelTelephone);
		
		telephoneValeur = new JTextField();
		GridBagConstraints gbc_telephoneValeur = new GridBagConstraints();
		gbc_telephoneValeur.anchor = GridBagConstraints.WEST;
		gbc_telephoneValeur.insets = new Insets(0, 0, 5, 5);
		gbc_telephoneValeur.gridx = 2;
		gbc_telephoneValeur.gridy = 2;
		donnees_loca.add(telephoneValeur, gbc_telephoneValeur);
		telephoneValeur.setColumns(10);
		
		JLabel labelComplement = new JLabel("Complément");
		GridBagConstraints gbc_labelComplement = new GridBagConstraints();
		gbc_labelComplement.anchor = GridBagConstraints.WEST;
		gbc_labelComplement.insets = new Insets(0, 0, 5, 5);
		gbc_labelComplement.gridx = 4;
		gbc_labelComplement.gridy = 2;
		donnees_loca.add(labelComplement, gbc_labelComplement);
		
		JComboBox complementValeur = new JComboBox();
		GridBagConstraints gbc_complementValeur = new GridBagConstraints();
		gbc_complementValeur.fill = GridBagConstraints.HORIZONTAL;
		gbc_complementValeur.insets = new Insets(0, 0, 5, 5);
		gbc_complementValeur.gridx = 5;
		gbc_complementValeur.gridy = 2;
		donnees_loca.add(complementValeur, gbc_complementValeur);
		
		JLabel labelMail = new JLabel("Mail");
		GridBagConstraints gbc_labelMail = new GridBagConstraints();
		gbc_labelMail.anchor = GridBagConstraints.WEST;
		gbc_labelMail.insets = new Insets(0, 0, 5, 5);
		gbc_labelMail.gridx = 1;
		gbc_labelMail.gridy = 3;
		donnees_loca.add(labelMail, gbc_labelMail);
		
		mailValeur = new JTextField();
		GridBagConstraints gbc_mailValeur = new GridBagConstraints();
		gbc_mailValeur.anchor = GridBagConstraints.WEST;
		gbc_mailValeur.insets = new Insets(0, 0, 5, 5);
		gbc_mailValeur.gridx = 2;
		gbc_mailValeur.gridy = 3;
		donnees_loca.add(mailValeur, gbc_mailValeur);
		mailValeur.setColumns(10);
		
		JLabel labelDate = new JLabel("Date arrivée");
		GridBagConstraints gbc_labelDate = new GridBagConstraints();
		gbc_labelDate.anchor = GridBagConstraints.WEST;
		gbc_labelDate.insets = new Insets(0, 0, 5, 5);
		gbc_labelDate.gridx = 4;
		gbc_labelDate.gridy = 3;
		donnees_loca.add(labelDate, gbc_labelDate);
		
		dateValeur = new JTextField();
		GridBagConstraints gbc_dateValeur = new GridBagConstraints();
		gbc_dateValeur.fill = GridBagConstraints.HORIZONTAL;
		gbc_dateValeur.insets = new Insets(0, 0, 5, 5);
		gbc_dateValeur.gridx = 5;
		gbc_dateValeur.gridy = 3;
		donnees_loca.add(dateValeur, gbc_dateValeur);
		dateValeur.setColumns(10);
		
		JButton enregistrerButton = new JButton("Enregistrer");
		enregistrerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_enregistrerButton = new GridBagConstraints();
		gbc_enregistrerButton.insets = new Insets(0, 0, 0, 5);
		gbc_enregistrerButton.gridx = 5;
		gbc_enregistrerButton.gridy = 5;
		donnees_loca.add(enregistrerButton, gbc_enregistrerButton);

		this.frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				ResizedImage.resizeImage("/ressources/images/logo+nom.png", PageNouveauLocataire.this.frame,
						PageNouveauLocataire.this.logo, 3, 8);
				int frameWidth = PageNouveauLocataire.this.frame.getWidth();
				int frameHeight = PageNouveauLocataire.this.frame.getHeight();

				int newFontSize = Math.min(frameWidth, frameHeight) / 30;

				// Appliquer la nouvelle police au bouton
				Font resizedFont = new Font("Arial", Font.PLAIN, newFontSize);
				b_loca.setFont(resizedFont);
				b_bails.setFont(resizedFont);
				b_accueil.setFont(resizedFont);
				b_profil.setFont(resizedFont);
				b_biens.setFont(resizedFont);
			}
		});
	}

}
