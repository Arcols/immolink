package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.EventQueue;
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

public class PageNouveauBienImmobilier extends PageAbstraite {

	private JPanel tableau_diagnostic;
	private JPanel panel_caracteristique;
	private JLabel diagnostics;
	private JLabel surface;
	private JLabel nombre_piece;
	private JLabel complement_adresse;
	private final JLabel code_postal = new JLabel("Code postal");
	private JFormattedTextField choix_num_fiscal;
	private JTextField choix_complement_adresse;
	private JButton valider;
	private JComboBox choix_adresse;
	private JComboBox choix_ville;
	private JComboBox choix_type_de_bien;
	private final JTextField texte_ville = new JTextField();
	private final JTextField texte_adresse = new JTextField();
	private JFormattedTextField texte_code_postal;
	private JSpinner choix_nb_piece;
	private JSpinner choix_surface;
	private final JButton rajouter_garage_bouton = new JButton("Lier un garage");
	private Map<String, Diagnostic> map_diagnostic;
    private Map<String, List<String>> map_ville_adresse;
	private Garage garage_lie = new Garage("            ", "", "", "", TypeLogement.NONE);

	private ModelePageNouveauBienImmobilier modele;

	/**
	 * Create the application.
	 */
	public PageNouveauBienImmobilier(int x, int y) {
		super(x,y);
		this.modele = new ModelePageNouveauBienImmobilier(this);
		this.CreerSpecific();
	}

	@Override
	public void CreerSpecific(){
		this.map_diagnostic = new HashMap<>();
		modele.initialiseMapDiagnostic();
		try {
			BatimentDAO batimentDAO = new BatimentDAO();
			this.map_ville_adresse = batimentDAO.searchAllBatiments();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JPanel titre = new JPanel();
		panel_body.add(titre, BorderLayout.NORTH);

		JLabel label_titre = new JLabel("Nouveau bien immobilier", SwingConstants.CENTER);
		label_titre.setFont(new Font("Arial", Font.BOLD, 16));
		panel_body.add(label_titre, BorderLayout.NORTH);

		JPanel contenu = new JPanel();
		panel_body.add(contenu, BorderLayout.CENTER);
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
		Set<String> set_ville = this.map_ville_adresse.keySet();
		if (!set_ville.isEmpty()) {
			choix_ville.setModel(new DefaultComboBoxModel(set_ville.toArray(new String[0])));
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
		if (set_ville.isEmpty()) {
			this.choix_adresse.setModel(new DefaultComboBoxModel());
		} else {
			this.choix_adresse.setModel(new DefaultComboBoxModel(
					this.map_ville_adresse.get(this.choix_ville.getSelectedItem()).toArray(new String[0])));
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
		this.rajouter_garage_bouton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				modele.showGaragePopup();
			}
		});
		GridBagConstraints gbc_check_garage = new GridBagConstraints();
		gbc_check_garage.fill = GridBagConstraints.HORIZONTAL;
		gbc_check_garage.gridwidth = 2;
		gbc_check_garage.insets = new Insets(10, 0, 0, 0);
		gbc_check_garage.gridx = 0;
		gbc_check_garage.gridy = 7;
		this.panel_caracteristique.add(this.rajouter_garage_bouton, gbc_check_garage);
		this.rajouter_garage_bouton.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		this.rajouter_garage_bouton.setAlignmentX(Component.RIGHT_ALIGNMENT);

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
		this.choix_ville.addActionListener(modele.getVilleActionListener(map_ville_adresse));
		this.choix_type_de_bien.addActionListener(modele.getChoixTypeBienListener());
		this.choix_type_de_bien.addActionListener(modele.getCheckFieldsActionListener());
		this.choix_num_fiscal.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
		this.choix_complement_adresse.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
		this.texte_ville.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
		this.texte_adresse.getDocument().addDocumentListener(modele.getTextFieldDocumentListener());
		quitter.addActionListener(modele.quitterPage());

		frame.addWindowListener(modele.fermerFenetre());
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
		return this.rajouter_garage_bouton;
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

	public Garage getGarageLie() {return this.garage_lie;}

	public void addGarage(Garage garage) {
		this.garage_lie = garage;
	}



}