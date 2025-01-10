package ihm;

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
import java.awt.event.*;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import DAO.jdbc.BatimentDAO;
import classes.Diagnostic;
import classes.Garage;
import enumeration.NomsDiags;
import enumeration.TypeLogement;
import modele.Charte;
import modele.Menu;
import modele.ModelePageNouveauBienImmobilier;
import modele.ResizedImage;

public class PageNouveauBienImmobilier {

	private JFrame frame;
	private JPanel tableau_diagnostic;
	private JPanel panel_caracteristique;
	private JLabel logo;
	private JLabel diagnostics;
	private JLabel surface;
	private JLabel nombre_piece;
	private JLabel complement_adresse;
	private JLabel code_postal = new JLabel("Code postal");
	private JFormattedTextField choix_num_fiscal;
	private JTextField choix_complement_adresse;
	private JButton valider;
	private JComboBox choix_adresse;
	private JComboBox choix_ville;
	private JComboBox choix_type_de_bien;
	private JTextField texte_ville = new JTextField();
	private JTextField texte_adresse = new JTextField();
	private JFormattedTextField texte_code_postal;
	private JSpinner choix_nb_piece;
	private JSpinner choix_surface;
	private JButton addGarageButton = new JButton("Lier un garage");
	private List<Diagnostic> liste_diagnostic;
	private Map<String, Diagnostic> map_diagnostic;
	private Set<String> setVilles;
	private Map<String, List<String>> mapVillesAdresses;
	private Garage garageLie = new Garage("            ", "", "", "", TypeLogement.NONE);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					PageNouveauBienImmobilier window = new PageNouveauBienImmobilier();
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
	public PageNouveauBienImmobilier() {
		this.initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		ModelePageNouveauBienImmobilier modele = new ModelePageNouveauBienImmobilier(this);
		this.map_diagnostic = new HashMap<>();
		initialiseMapDiagnostic();
		this.liste_diagnostic = new ArrayList<>();
		try {
			BatimentDAO batimentDAO = new BatimentDAO();
			this.mapVillesAdresses = batimentDAO.searchAllBatiments();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Initialisation du JFrame
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

		JLabel titleLabel = new JLabel("Nouveau bien immobilier", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
		body.add(titleLabel, BorderLayout.NORTH);

		JPanel contenu = new JPanel();
		body.add(contenu, BorderLayout.CENTER);
		contenu.setLayout(new GridLayout(1, 2, 0, 0));

		this.panel_caracteristique = new JPanel();
		contenu.add(this.panel_caracteristique);
		GridBagLayout gbl_panel_caracteristique = new GridBagLayout();
		gbl_panel_caracteristique.columnWidths = new int[] { 0 };
		gbl_panel_caracteristique.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_caracteristique.columnWeights = new double[] { 0.0, 0.0 };
		gbl_panel_caracteristique.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		this.panel_caracteristique.setLayout(gbl_panel_caracteristique);

		JLabel type_de_bien = new JLabel("Type de bien");
		GridBagConstraints gbc_type_de_bien = new GridBagConstraints();
		gbc_type_de_bien.fill = GridBagConstraints.BOTH;
		gbc_type_de_bien.insets = new Insets(0, 0, 5, 5);
		gbc_type_de_bien.gridx = 0;
		gbc_type_de_bien.gridy = 0;
		this.panel_caracteristique.add(type_de_bien, gbc_type_de_bien);
		this.choix_type_de_bien = new JComboBox();
		this.choix_type_de_bien
				.setModel(new DefaultComboBoxModel(new String[] { "Appartement", "Bâtiment", "Garage","Maison" }));
		GridBagConstraints gbc_choix_type_de_bien = new GridBagConstraints();
		gbc_choix_type_de_bien.fill = GridBagConstraints.HORIZONTAL;
		gbc_choix_type_de_bien.insets = new Insets(0, 0, 5, 0);
		gbc_choix_type_de_bien.gridx = 1;
		gbc_choix_type_de_bien.gridy = 0;
		this.panel_caracteristique.add(this.choix_type_de_bien, gbc_choix_type_de_bien);

		JLabel num_fiscal = new JLabel("Numéro Fiscal");
		GridBagConstraints gbc_num_fiscal = new GridBagConstraints();
		gbc_num_fiscal.fill = GridBagConstraints.BOTH;
		gbc_num_fiscal.insets = new Insets(0, 0, 5, 5);
		gbc_num_fiscal.gridx = 0;
		gbc_num_fiscal.gridy = 1;
		this.panel_caracteristique.add(num_fiscal, gbc_num_fiscal);

		this.choix_num_fiscal = new JFormattedTextField();
		this.choix_num_fiscal.setColumns(12);
		this.choix_num_fiscal.setDocument(new PlainDocument() {
			@Override
			public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
				if (str == null || getLength() + str.length() <= 12) {
					super.insertString(offs, str, a);
				}
			}
		});
		GridBagConstraints gbc_choix_num_fiscal = new GridBagConstraints();
		gbc_choix_num_fiscal.fill = GridBagConstraints.HORIZONTAL;
		gbc_choix_num_fiscal.insets = new Insets(0, 0, 5, 0);
		gbc_choix_num_fiscal.gridx = 1;
		gbc_choix_num_fiscal.gridy = 1;
		this.panel_caracteristique.add(this.choix_num_fiscal, gbc_choix_num_fiscal);

		// code postal max 5 caractères
		texte_code_postal = new JFormattedTextField();
		this.texte_code_postal.setColumns(5);
		this.texte_code_postal.setDocument(new PlainDocument() {
			@Override
			public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
				if (str == null || getLength() + str.length() <= 5) {
					super.insertString(offs, str, a);
				}
			}
		});

		// Ajout des listeners sur chaque champ de texte

		JLabel ville = new JLabel("Ville");
		GridBagConstraints gbc_ville = new GridBagConstraints();
		gbc_ville.fill = GridBagConstraints.BOTH;
		gbc_ville.insets = new Insets(0, 0, 5, 5);
		gbc_ville.gridx = 0;
		gbc_ville.gridy = 2;
		this.panel_caracteristique.add(ville, gbc_ville);

		this.choix_ville = new JComboBox();
		GridBagConstraints gbc_choix_ville = new GridBagConstraints();
		gbc_choix_ville.fill = GridBagConstraints.HORIZONTAL;
		gbc_choix_ville.insets = new Insets(0, 0, 5, 0);
		gbc_choix_ville.gridx = 1;
		gbc_choix_ville.gridy = 2;
		this.panel_caracteristique.add(choix_ville, gbc_choix_ville);
		this.setVilles = this.mapVillesAdresses.keySet();
		if (!this.setVilles.isEmpty()) {
			choix_ville.setModel(new DefaultComboBoxModel(this.setVilles.toArray(new String[0])));
		} else {
			choix_ville.setModel(new DefaultComboBoxModel());
		}

		JLabel adresse = new JLabel("Adresse");
		GridBagConstraints gbc_adresse = new GridBagConstraints();
		gbc_adresse.fill = GridBagConstraints.BOTH;
		gbc_adresse.insets = new Insets(0, 0, 5, 5);
		gbc_adresse.gridx = 0;
		gbc_adresse.gridy = 3;
		this.panel_caracteristique.add(adresse, gbc_adresse);

		this.choix_adresse = new JComboBox();
		GridBagConstraints gbc_choix_adresse = new GridBagConstraints();
		gbc_choix_adresse.fill = GridBagConstraints.HORIZONTAL;
		gbc_choix_adresse.insets = new Insets(0, 0, 5, 0);
		gbc_choix_adresse.gridx = 1;
		gbc_choix_adresse.gridy = 3;
		this.panel_caracteristique.add(this.choix_adresse, gbc_choix_adresse);
		if (this.setVilles.isEmpty()) {
			this.choix_adresse.setModel(new DefaultComboBoxModel());
		} else {
			this.choix_adresse.setModel(new DefaultComboBoxModel(
					this.mapVillesAdresses.get(this.choix_ville.getSelectedItem()).toArray(new String[0])));
		}

		this.complement_adresse = new JLabel("Complément d'adresse");
		GridBagConstraints gbc_complement_adresse = new GridBagConstraints();
		gbc_complement_adresse.fill = GridBagConstraints.BOTH;
		gbc_complement_adresse.insets = new Insets(0, 0, 5, 5);
		gbc_complement_adresse.gridx = 0;
		gbc_complement_adresse.gridy = 4;
		this.panel_caracteristique.add(this.complement_adresse, gbc_complement_adresse);

		this.choix_complement_adresse = new JTextField();
		GridBagConstraints gbc_choix_complement_adresse = new GridBagConstraints();
		gbc_choix_complement_adresse.fill = GridBagConstraints.HORIZONTAL;
		gbc_choix_complement_adresse.insets = new Insets(0, 0, 5, 0);
		gbc_choix_complement_adresse.gridx = 1;
		gbc_choix_complement_adresse.gridy = 4;
		this.panel_caracteristique.add(this.choix_complement_adresse, gbc_choix_complement_adresse);
		this.choix_complement_adresse.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		this.choix_complement_adresse.setAlignmentX(Component.RIGHT_ALIGNMENT);
		this.choix_complement_adresse.setColumns(10);

		this.surface = new JLabel("Surface habitable");
		GridBagConstraints gbc_surface = new GridBagConstraints();
		gbc_surface.fill = GridBagConstraints.BOTH;
		gbc_surface.insets = new Insets(0, 0, 5, 5);
		gbc_surface.gridx = 0;
		gbc_surface.gridy = 5;
		this.panel_caracteristique.add(this.surface, gbc_surface);

		this.choix_surface = new JSpinner();
		GridBagConstraints gbc_choix_surface = new GridBagConstraints();
		gbc_choix_surface.fill = GridBagConstraints.HORIZONTAL;
		gbc_choix_surface.insets = new Insets(0, 0, 5, 0);
		gbc_choix_surface.gridx = 1;
		gbc_choix_surface.gridy = 5;
		this.panel_caracteristique.add(this.choix_surface, gbc_choix_surface);
		this.choix_surface
				.setModel(new SpinnerNumberModel(Double.valueOf(9), Double.valueOf(9), null, Double.valueOf(0.5)));
		this.choix_surface
				.setModel(new SpinnerNumberModel(Double.valueOf(9), Double.valueOf(9), null, Double.valueOf(0.5)));
		JSpinner.NumberEditor editor = new JSpinner.NumberEditor(this.choix_surface, "#0.## 'm²'");
		editor.setAlignmentY(1.0f);
		editor.setAlignmentX(1.0f);
		this.choix_surface.setEditor(editor);
		this.choix_surface.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		this.choix_surface.setAlignmentX(Component.RIGHT_ALIGNMENT);

		this.nombre_piece = new JLabel("Nombre de pièces");
		GridBagConstraints gbc_nombre_piece = new GridBagConstraints();
		gbc_nombre_piece.fill = GridBagConstraints.BOTH;
		gbc_nombre_piece.insets = new Insets(0, 0, 0, 5);
		gbc_nombre_piece.gridx = 0;
		gbc_nombre_piece.gridy = 6;
		this.panel_caracteristique.add(this.nombre_piece, gbc_nombre_piece);

		this.choix_nb_piece = new JSpinner();
		GridBagConstraints gbc_choix_nb_piece = new GridBagConstraints();
		gbc_choix_nb_piece.fill = GridBagConstraints.HORIZONTAL;
		gbc_choix_nb_piece.gridx = 1;
		gbc_choix_nb_piece.gridy = 6;
		this.panel_caracteristique.add(this.choix_nb_piece, gbc_choix_nb_piece);
		this.choix_nb_piece.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		this.choix_nb_piece.setAlignmentX(Component.RIGHT_ALIGNMENT);
		this.choix_nb_piece
				.setModel(new SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));
		this.addGarageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showGaragePopup();
			}
		});
		GridBagConstraints gbc_check_garage = new GridBagConstraints();
		gbc_check_garage.fill = GridBagConstraints.HORIZONTAL;
		gbc_check_garage.gridwidth = 2;
		gbc_check_garage.insets = new Insets(10, 0, 0, 0);
		gbc_check_garage.gridx = 0;
		gbc_check_garage.gridy = 7;
		this.panel_caracteristique.add(this.addGarageButton, gbc_check_garage);
		this.addGarageButton.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		this.addGarageButton.setAlignmentX(Component.RIGHT_ALIGNMENT);

		JPanel panel_diagnostic = new JPanel();
		contenu.add(panel_diagnostic);
		panel_diagnostic.setLayout(new BorderLayout(0, 0));
		this.diagnostics = new JLabel("Diagnostics");
		this.diagnostics.setHorizontalAlignment(SwingConstants.CENTER);
		panel_diagnostic.add(this.diagnostics, BorderLayout.NORTH);

		String[] nomdiagnostics = { "Certificat de surface habitable", "Diagnostique de performance énergétique",
				"Dossier amiante parties privatives", "Constat de risque d'exposition au plomb avant location",
				"État des risques, pollutions et des nuisances sonores aériennes",
				"Diagnostique de l'état de l'installation d'électricité",
				"Diagnostique de l'état de l'installation de gaz"};

		// Panel principal (avec un défilement si nécessaire)
		this.tableau_diagnostic = new JPanel(new GridBagLayout()); // Remplacer GridLayout par GridBagLayout

		// Créer un GridBagConstraints pour gérer le placement des composants
		GridBagConstraints gbc_diag = new GridBagConstraints();
		gbc_diag.fill = GridBagConstraints.HORIZONTAL;
		gbc_diag.insets = new Insets(5, 5, 5, 5); // Espacement entre les composants

		int row = 0; // Initialiser le compteur de ligne pour GridBagLayout

		for (String diagnostic : nomdiagnostics) {
			// Créer le label pour chaque diagnostic
			JLabel label = new JLabel(diagnostic);
			gbc_diag.gridx = 0; // Première colonne pour le label
			gbc_diag.gridy = row;
			this.tableau_diagnostic.add(label, gbc_diag);

			// Créer le bouton "Importer" pour chaque diagnostic
			JButton bouton = new JButton("Importer");
			bouton.addActionListener(modele.getTelechargerPDFButton(diagnostic));
			gbc_diag.gridx = 1; // Deuxième colonne pour le bouton
			this.tableau_diagnostic.add(bouton, gbc_diag);

			row++; // Incrémenter la ligne pour le prochain diagnostic
		}

		JScrollPane scrollPane = new JScrollPane(this.tableau_diagnostic);
		panel_diagnostic.add(scrollPane, BorderLayout.CENTER);

		JPanel bas_de_page = new JPanel();
		this.frame.getContentPane().add(bas_de_page, BorderLayout.SOUTH);
		bas_de_page.setLayout(new BorderLayout(0, 0));

		this.valider = new JButton("Valider");
		this.valider.setEnabled(false);
		this.valider.setHorizontalTextPosition(SwingConstants.LEFT);
		this.valider.setVerticalTextPosition(SwingConstants.TOP);
		this.valider.setVerticalAlignment(SwingConstants.BOTTOM);
		bas_de_page.add(this.valider, BorderLayout.EAST);

		JButton quitter = new JButton("Quitter");
		quitter.setHorizontalTextPosition(SwingConstants.LEFT);
		quitter.setVerticalTextPosition(SwingConstants.TOP);
		quitter.setVerticalAlignment(SwingConstants.BOTTOM);
		bas_de_page.add(quitter, BorderLayout.WEST);

		this.valider.addActionListener(modele.getValidateActionListener());
		this.frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				ResizedImage res = new ResizedImage();
				res.resizeImage("logo+nom.png", PageNouveauBienImmobilier.this.frame,
						PageNouveauBienImmobilier.this.logo, 3, 8);
				int frameWidth = PageNouveauBienImmobilier.this.frame.getWidth();
				int frameHeight = PageNouveauBienImmobilier.this.frame.getHeight();

				int newFontSize = Math.min(frameWidth, frameHeight) / 30;

				// Appliquer la nouvelle police au bouton
				Font resizedFont = new Font("Arial", Font.PLAIN, newFontSize);
				b_baux.setFont(resizedFont);
				b_accueil.setFont(resizedFont);
				b_biens.setFont(resizedFont);
			}
		});
		this.choix_ville.addActionListener(modele.getVilleActionListener(mapVillesAdresses));
		this.choix_type_de_bien.addActionListener(modele.getChoixTypeBienListener());
		this.choix_type_de_bien.addActionListener(modele.getCheckFieldsActionListener());
		this.choix_num_fiscal.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
		this.choix_complement_adresse.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
		this.texte_ville.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
		this.texte_adresse.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
		quitter.addActionListener(modele.quitterPage());
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

	public JComboBox getChoix_adresse() {
		return choix_adresse;
	}

	public JLabel getComplement_adresse() {
		return complement_adresse;
	}

	public JComboBox getChoix_ville() {
		return this.choix_ville;
	}

	public JTextField getChoix_num_fiscal() {
		return this.choix_num_fiscal;
	}

	public JPanel getPanel_caracteristique() {
		return panel_caracteristique;
	}

	public JComboBox getChoix_type_de_bien() {
		return this.choix_type_de_bien;
	}

	public JTextField getTexte_ville() {
		return this.texte_ville;
	}

	public JLabel getNombre_piece() {
		return nombre_piece;
	}

	public JTextField getTexte_adresse() {
		return this.texte_adresse;
	}

	public JTextField getChoix_complement_adresse() {
		return this.choix_complement_adresse;
	}

	public JButton getValider() {
		return this.valider;
	}

	public JButton getAddGarageButton() {
		return this.addGarageButton;
	}

	public JSpinner getChoix_nb_piece() {
		return choix_nb_piece;
	}

	public JSpinner getChoix_surface() {
		return choix_surface;
	}

	public Map<String, Diagnostic> getMap_diagnostic() {
		return map_diagnostic;
	}

	public List<Diagnostic> getListe_diagnostic() {
		return map_diagnostic.values().stream().collect(Collectors.toList());
	}

	public JLabel getDiagnostics() {
		return diagnostics;
	}

	public JPanel getTableau_diagnostic() {
		return tableau_diagnostic;
	}

	public JLabel getSurface() {
		return surface;
	}

	public JTextField getTexte_code_postal() {
		return texte_code_postal;
	}

	public JLabel getCode_postalLabel() {
		return code_postal;
	}

	public Garage getGarageLie() {return this.garageLie;}

	public void initialiseMapDiagnostic() {
		for (NomsDiags diag : NomsDiags.values()) {
			this.map_diagnostic.put(diag.name(), null);
		}
	}

	public boolean isMapDiagnosticFull() {
		for (Map.Entry<String, Diagnostic> entry : this.map_diagnostic.entrySet()) {
			if (!entry.getKey().equals("GAZ") && entry.getValue() == null) {
				return false;
			}
		}
		return true;
	}

	public void checkFields() {
		// Vérifier le type de bien sélectionné
		String selectedType = (String) this.getChoix_type_de_bien().getSelectedItem();

		// Définir les critères de validation en fonction du type sélectionné
		boolean isFilled;

		if ("Bâtiment".equals(selectedType)) {
			// Critères pour "Bâtiment" : vérifier que texte_ville et texte_adresse sont
			// remplis
			isFilled = !this.getTexte_ville().getText().trim().isEmpty()
					&& !this.getTexte_adresse().getText().trim().isEmpty();
		} else if ("Appartement".equals(selectedType) || "Maison".equals(selectedType)) {
			// Critères pour les autres types de bien : vérifier choix_complement_adresse et
			// choix_num_fiscal
			isFilled = !this.getChoix_complement_adresse().getText().trim().isEmpty()
					&& !this.getChoix_num_fiscal().getText().trim().isEmpty() && isMapDiagnosticFull();
		} else {
			isFilled = !this.getChoix_complement_adresse().getText().trim().isEmpty()
					&& !this.getChoix_num_fiscal().getText().trim().isEmpty();
		}

		// Active ou désactive le bouton "Valider"
		this.getValider().setEnabled(isFilled);
	}

	public void addGarage(Garage garage) {
		this.garageLie = garage;
	}

	private void showGaragePopup() {
		PopUpCreationGarageLieBL popup = new PopUpCreationGarageLieBL(this);
		popup.getFrame().setVisible(true);
	}

}