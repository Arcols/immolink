package projet.modele;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import projet.ihm.Charte;
import projet.ihm.Menu;
import projet.ihm.ResizedImage;

public class PageBienImmobilier {

	private JFrame frame;
	private JLabel logo;
	private JTextField choix_num_fiscal;
	private JTextField choix_complement_adresse;
	private JButton valider;
	private JTextField texte_ville = new JTextField();
	private JTextField texte_adresse = new JTextField();
	private JComboBox choix_type_de_bien;

	private void checkFields() {
		// Vérifier le type de bien sélectionné
		String selectedType = (String) choix_type_de_bien.getSelectedItem();

		// Définir les critères de validation en fonction du type sélectionné
		boolean isFilled;

		if ("Bâtiment".equals(selectedType)) {
			// Critères pour "Bâtiment" : vérifier que texte_ville et texte_adresse sont
			// remplis
			isFilled = !texte_ville.getText().trim().isEmpty() && !texte_adresse.getText().trim().isEmpty();
		} else {
			// Critères pour les autres types de bien : vérifier choix_complement_adresse et
			// choix_num_fiscal
			isFilled = !choix_complement_adresse.getText().trim().isEmpty()
					&& !choix_num_fiscal.getText().trim().isEmpty();
		}

		// Active ou désactive le bouton "Valider"
		valider.setEnabled(isFilled);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					PageBienImmobilier window = new PageBienImmobilier();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public JFrame getFrame() {
		return this.frame;
	}

	/**
	 * Create the application.
	 */
	public PageBienImmobilier() {
		this.initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// Initialisation du JFrame
		this.frame = new JFrame();
		this.frame.setBounds(100, 100, 750, 400);
		this.frame.getContentPane().setBackground(Charte.FOND.getCouleur());
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Panel d'entête pour le logo et le nom de l'appli
		JPanel entete = new JPanel();
		this.frame.getContentPane().add(entete, BorderLayout.NORTH);
		entete.setLayout(new BorderLayout(0, 0));

		entete.setBackground(Charte.ENTETE.getCouleur());
		entete.setBorder(new LineBorder(Color.BLACK, 2));
		// Label pour le logo (Image)
		this.logo = new JLabel("");
		entete.add(this.logo, BorderLayout.WEST);
		JPanel menu_bouttons = new JPanel();

		entete.add(menu_bouttons, BorderLayout.CENTER);
		menu_bouttons.setLayout(new GridLayout(0, 5, 0, 0));
		menu_bouttons.setBackground(Charte.ENTETE.getCouleur());

		Menu m = new Menu(this.frame);

		JButton b_accueil = new JButton("Accueil");
		b_accueil.setBorderPainted(false);
		b_accueil.setBackground(Charte.ENTETE.getCouleur());
		b_accueil.setCursor(new Cursor(Cursor.HAND_CURSOR));
		menu_bouttons.add(b_accueil);
		b_accueil.addActionListener(m);

		JButton b_profil = new JButton("Profil");
		b_profil.setBorderPainted(false);
		b_profil.setBackground(Charte.ENTETE.getCouleur());
		b_profil.setCursor(new Cursor(Cursor.HAND_CURSOR));
		menu_bouttons.add(b_profil);
		menu_bouttons.add(b_profil);
		b_profil.addActionListener(m);

		JButton b_baux = new JButton("Mes baux");
		b_baux.setBorderPainted(false);
		b_baux.setBackground(Charte.ENTETE.getCouleur());
		b_baux.setCursor(new Cursor(Cursor.HAND_CURSOR));
		menu_bouttons.add(b_baux);
		menu_bouttons.add(b_baux);
		b_baux.addActionListener(m);

		JButton b_loca = new JButton("Locataires");
		b_loca.setBorderPainted(false);
		b_loca.setBackground(Charte.ENTETE.getCouleur());
		b_loca.setCursor(new Cursor(Cursor.HAND_CURSOR));
		menu_bouttons.add(b_loca);
		menu_bouttons.add(b_loca);
		b_loca.addActionListener(m);

		JButton b_biens = new JButton("Mes Biens");
		b_biens.setBorderPainted(false);
		b_biens.setBackground(Charte.ENTETE.getCouleur());
		b_biens.setCursor(new Cursor(Cursor.HAND_CURSOR));
		menu_bouttons.add(b_biens);
		menu_bouttons.add(b_biens);

		JPanel body = new JPanel();
		frame.getContentPane().add(body, BorderLayout.CENTER);
		body.setLayout(new BorderLayout(0, 0));

		JPanel titre = new JPanel();
		FlowLayout fl_titre = (FlowLayout) titre.getLayout();
		body.add(titre, BorderLayout.NORTH);

		JLabel titrePage = new JLabel("Nouveau bien immobilier");
		titrePage.setAlignmentY(0.0f);
		titrePage.setAlignmentX(0.5f);
		titre.add(titrePage);

		JPanel contenu = new JPanel();
		body.add(contenu, BorderLayout.CENTER);
		contenu.setLayout(new GridLayout(1, 2, 0, 0));

		JPanel panel_caracteristique = new JPanel();
		contenu.add(panel_caracteristique);
		GridBagLayout gbl_panel_caracteristique = new GridBagLayout();
		gbl_panel_caracteristique.columnWidths = new int[] { 0 };
		gbl_panel_caracteristique.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_caracteristique.columnWeights = new double[] { 0.0, 0.0 };
		gbl_panel_caracteristique.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		panel_caracteristique.setLayout(gbl_panel_caracteristique);

		JLabel type_de_bien = new JLabel("Type de bien");
		GridBagConstraints gbc_type_de_bien = new GridBagConstraints();
		gbc_type_de_bien.fill = GridBagConstraints.BOTH;
		gbc_type_de_bien.insets = new Insets(0, 0, 5, 5);
		gbc_type_de_bien.gridx = 0;
		gbc_type_de_bien.gridy = 0;
		panel_caracteristique.add(type_de_bien, gbc_type_de_bien);
		choix_type_de_bien = new JComboBox();
		choix_type_de_bien.setModel(new DefaultComboBoxModel(new String[] { "Appartement", "Bâtiment", "Garage" }));
		GridBagConstraints gbc_choix_type_de_bien = new GridBagConstraints();
		gbc_choix_type_de_bien.fill = GridBagConstraints.HORIZONTAL;
		gbc_choix_type_de_bien.insets = new Insets(0, 0, 5, 0);
		gbc_choix_type_de_bien.gridx = 1;
		gbc_choix_type_de_bien.gridy = 0;
		panel_caracteristique.add(choix_type_de_bien, gbc_choix_type_de_bien);
		choix_type_de_bien.addActionListener(e -> checkFields());

		JLabel num_fiscal = new JLabel("Numéro Fiscal");
		GridBagConstraints gbc_num_fiscal = new GridBagConstraints();
		gbc_num_fiscal.fill = GridBagConstraints.BOTH;
		gbc_num_fiscal.insets = new Insets(0, 0, 5, 5);
		gbc_num_fiscal.gridx = 0;
		gbc_num_fiscal.gridy = 1;
		panel_caracteristique.add(num_fiscal, gbc_num_fiscal);

		choix_num_fiscal = new JTextField();
		GridBagConstraints gbc_choix_num_fiscal = new GridBagConstraints();
		gbc_choix_num_fiscal.fill = GridBagConstraints.HORIZONTAL;
		gbc_choix_num_fiscal.insets = new Insets(0, 0, 5, 0);
		gbc_choix_num_fiscal.gridx = 1;
		gbc_choix_num_fiscal.gridy = 1;
		panel_caracteristique.add(choix_num_fiscal, gbc_choix_num_fiscal);
		choix_num_fiscal.setColumns(10);

		// Ajout des listeners sur chaque champ de texte

		JLabel ville = new JLabel("Ville");
		GridBagConstraints gbc_ville = new GridBagConstraints();
		gbc_ville.fill = GridBagConstraints.BOTH;
		gbc_ville.insets = new Insets(0, 0, 5, 5);
		gbc_ville.gridx = 0;
		gbc_ville.gridy = 2;
		panel_caracteristique.add(ville, gbc_ville);

		JComboBox choix_ville = new JComboBox();
		GridBagConstraints gbc_choix_ville = new GridBagConstraints();
		gbc_choix_ville.fill = GridBagConstraints.HORIZONTAL;
		gbc_choix_ville.insets = new Insets(0, 0, 5, 0);
		gbc_choix_ville.gridx = 1;
		gbc_choix_ville.gridy = 2;
		panel_caracteristique.add(choix_ville, gbc_choix_ville);
		choix_ville.setModel(new DefaultComboBoxModel());

		JLabel adresse = new JLabel("Adresse");
		GridBagConstraints gbc_adresse = new GridBagConstraints();
		gbc_adresse.fill = GridBagConstraints.BOTH;
		gbc_adresse.insets = new Insets(0, 0, 5, 5);
		gbc_adresse.gridx = 0;
		gbc_adresse.gridy = 3;
		panel_caracteristique.add(adresse, gbc_adresse);

		JComboBox choix_adresse = new JComboBox();
		GridBagConstraints gbc_choix_adresse = new GridBagConstraints();
		gbc_choix_adresse.fill = GridBagConstraints.HORIZONTAL;
		gbc_choix_adresse.insets = new Insets(0, 0, 5, 0);
		gbc_choix_adresse.gridx = 1;
		gbc_choix_adresse.gridy = 3;
		panel_caracteristique.add(choix_adresse, gbc_choix_adresse);
		choix_adresse.setModel(new DefaultComboBoxModel());

		JLabel complement_adresse = new JLabel("Complément d'adresse");
		GridBagConstraints gbc_complement_adresse = new GridBagConstraints();
		gbc_complement_adresse.fill = GridBagConstraints.BOTH;
		gbc_complement_adresse.insets = new Insets(0, 0, 5, 5);
		gbc_complement_adresse.gridx = 0;
		gbc_complement_adresse.gridy = 4;
		panel_caracteristique.add(complement_adresse, gbc_complement_adresse);

		choix_complement_adresse = new JTextField();
		GridBagConstraints gbc_choix_complement_adresse = new GridBagConstraints();
		gbc_choix_complement_adresse.fill = GridBagConstraints.HORIZONTAL;
		gbc_choix_complement_adresse.insets = new Insets(0, 0, 5, 0);
		gbc_choix_complement_adresse.gridx = 1;
		gbc_choix_complement_adresse.gridy = 4;
		panel_caracteristique.add(choix_complement_adresse, gbc_choix_complement_adresse);
		choix_complement_adresse.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		choix_complement_adresse.setAlignmentX(Component.RIGHT_ALIGNMENT);
		choix_complement_adresse.setColumns(10);

		JLabel surface = new JLabel("Surface habitable");
		GridBagConstraints gbc_surface = new GridBagConstraints();
		gbc_surface.fill = GridBagConstraints.BOTH;
		gbc_surface.insets = new Insets(0, 0, 5, 5);
		gbc_surface.gridx = 0;
		gbc_surface.gridy = 5;
		panel_caracteristique.add(surface, gbc_surface);

		JSpinner choix_surface = new JSpinner();
		GridBagConstraints gbc_choix_surface = new GridBagConstraints();
		gbc_choix_surface.fill = GridBagConstraints.HORIZONTAL;
		gbc_choix_surface.insets = new Insets(0, 0, 5, 0);
		gbc_choix_surface.gridx = 1;
		gbc_choix_surface.gridy = 5;
		panel_caracteristique.add(choix_surface, gbc_choix_surface);
		choix_surface.setModel(new SpinnerNumberModel(Double.valueOf(9), Double.valueOf(9), null, Double.valueOf(0.5)));
		JSpinner.NumberEditor editor = new JSpinner.NumberEditor(choix_surface, "#0.## 'm²'");
		editor.setAlignmentY(1.0f);
		editor.setAlignmentX(1.0f);
		choix_surface.setEditor(editor);
		choix_surface.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		choix_surface.setAlignmentX(Component.RIGHT_ALIGNMENT);

		JLabel nombre_piece = new JLabel("Nombre de pièces");
		GridBagConstraints gbc_nombre_piece = new GridBagConstraints();
		gbc_nombre_piece.fill = GridBagConstraints.BOTH;
		gbc_nombre_piece.insets = new Insets(0, 0, 0, 5);
		gbc_nombre_piece.gridx = 0;
		gbc_nombre_piece.gridy = 6;
		panel_caracteristique.add(nombre_piece, gbc_nombre_piece);

		JSpinner choix_nb_piece = new JSpinner();
		GridBagConstraints gbc_choix_nb_piece = new GridBagConstraints();
		gbc_choix_nb_piece.fill = GridBagConstraints.HORIZONTAL;
		gbc_choix_nb_piece.gridx = 1;
		gbc_choix_nb_piece.gridy = 6;
		panel_caracteristique.add(choix_nb_piece, gbc_choix_nb_piece);
		choix_nb_piece.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		choix_nb_piece.setAlignmentX(Component.RIGHT_ALIGNMENT);
		choix_nb_piece
				.setModel(new SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));

		JPanel panel_diagnostic = new JPanel();
		contenu.add(panel_diagnostic);
		panel_diagnostic.setLayout(new BorderLayout(0, 0));
		JLabel diagnostics = new JLabel("Diagnostics");
		diagnostics.setHorizontalAlignment(SwingConstants.CENTER);
		panel_diagnostic.add(diagnostics, BorderLayout.NORTH);

		String[] diagnostics1 = { "Certificat de surface habitable", "Diagnostique de performance énergétique",
				"Dossier amiante parties privatives", "Constat de risque d'exposition au plomb avant location",
				"État des risques, pollutions et des nuisances sonores aériennes",
				"Diagnostique de l'état de l'installation d'électricité" };

		// Panel principal (avec un défilement si nécessaire)
		JPanel tableau_diagnostic = new JPanel(new GridBagLayout()); // Remplacer GridLayout par GridBagLayout

		// Créer un GridBagConstraints pour gérer le placement des composants
		GridBagConstraints gbc_diag = new GridBagConstraints();
		gbc_diag.fill = GridBagConstraints.HORIZONTAL;
		gbc_diag.insets = new Insets(5, 5, 5, 5); // Espacement entre les composants

		int row = 0; // Initialiser le compteur de ligne pour GridBagLayout

		for (String diagnostic : diagnostics1) {
			// Créer le label pour chaque diagnostic
			JLabel label = new JLabel(diagnostic);
			gbc_diag.gridx = 0; // Première colonne pour le label
			gbc_diag.gridy = row;
			tableau_diagnostic.add(label, gbc_diag);

			// Créer le bouton "Télécharger" pour chaque diagnostic
			JButton bouton = new JButton("Télécharger");
			bouton.addActionListener(e -> System.out.println("Téléchargement demandé pour : " + diagnostic));
			gbc_diag.gridx = 1; // Deuxième colonne pour le bouton
			tableau_diagnostic.add(bouton, gbc_diag);

			row++; // Incrémenter la ligne pour le prochain diagnostic
		}

		JScrollPane scrollPane = new JScrollPane(tableau_diagnostic);
		panel_diagnostic.add(scrollPane, BorderLayout.CENTER);

		JPanel bas_de_page = new JPanel();
		frame.getContentPane().add(bas_de_page, BorderLayout.SOUTH);
		bas_de_page.setLayout(new BorderLayout(0, 0));

		this.valider = new JButton("Valider");
		this.valider.setEnabled(false);
		this.valider.setHorizontalTextPosition(SwingConstants.LEFT);
		this.valider.setVerticalTextPosition(SwingConstants.TOP);
		this.valider.setVerticalAlignment(SwingConstants.BOTTOM);
		bas_de_page.add(this.valider, BorderLayout.EAST);

		this.frame.addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent e) {
				ResizedImage.resizeImage("/ressources/images/logo+nom.png", PageBienImmobilier.this.frame,
						PageBienImmobilier.this.logo, 3, 8);
				int frameWidth = PageBienImmobilier.this.frame.getWidth();
				int frameHeight = PageBienImmobilier.this.frame.getHeight();

				int newFontSize = Math.min(frameWidth, frameHeight) / 30;

				// Appliquer la nouvelle police au bouton
				Font resizedFont = new Font("Arial", Font.PLAIN, newFontSize);
				b_loca.setFont(resizedFont);
				b_baux.setFont(resizedFont);
				b_accueil.setFont(resizedFont);
				b_profil.setFont(resizedFont);
				b_biens.setFont(resizedFont);
			}
		});

		DocumentListener textListener = new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				checkFields();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				checkFields();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				checkFields();
			}
		};
		choix_num_fiscal.getDocument().addDocumentListener(textListener);
		choix_complement_adresse.getDocument().addDocumentListener(textListener);
		texte_ville.getDocument().addDocumentListener(textListener);
		texte_adresse.getDocument().addDocumentListener(textListener);

		choix_type_de_bien.addActionListener(e -> {
			String selectedType = (String) choix_type_de_bien.getSelectedItem();
			boolean isAppartement = "Appartement".equals(selectedType);
			boolean isBatiment = "Bâtiment".equals(selectedType);

			// Gérer la visibilité des composants
			diagnostics.setVisible(isAppartement);
			tableau_diagnostic.setVisible(isAppartement);
			surface.setVisible(isAppartement);
			choix_surface.setVisible(isAppartement);
			nombre_piece.setVisible(isAppartement);
			choix_nb_piece.setVisible(isAppartement);
			complement_adresse.setVisible(!isBatiment);
			choix_complement_adresse.setVisible(!isBatiment);
			num_fiscal.setVisible(!isBatiment);
			choix_num_fiscal.setVisible(!isBatiment);

			// Remplacer les JComboBox par JTextField pour "Bâtiment"
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.insets = new Insets(0, 0, 5, 0);

			// Ville
			if (isBatiment) {
				panel_caracteristique.remove(choix_ville);
				gbc.gridx = 1;
				gbc.gridy = 2;
				panel_caracteristique.add(texte_ville, gbc);
			} else {
				panel_caracteristique.remove(texte_ville);
				gbc.gridx = 1;
				gbc.gridy = 2;
				panel_caracteristique.add(choix_ville, gbc);
			}

			// Adresse
			if (isBatiment) {
				panel_caracteristique.remove(choix_adresse);
				gbc.gridx = 1;
				gbc.gridy = 3;
				panel_caracteristique.add(texte_adresse, gbc);
			} else {
				panel_caracteristique.remove(texte_adresse);
				gbc.gridx = 1;
				gbc.gridy = 3;
				panel_caracteristique.add(choix_adresse, gbc);
			}

			// Rafraîchir l'interface
			panel_caracteristique.revalidate();
			panel_caracteristique.repaint();
		});

	}

}