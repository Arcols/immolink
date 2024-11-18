package projet.modele;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import projet.ihm.Charte;
import projet.ihm.ResizedImage;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

public class PageBienImmobilier {

	private JFrame frame;
	private JLabel logo;
	private JTextField choix_num_fiscal;
	private JTextField choix_complement_adresse;
	private JButton valider;

	private void checkFields() {
		// Vérification si tous les champs sont remplis
		boolean isFilled = !choix_complement_adresse.getText().trim().isEmpty()
				&& !choix_num_fiscal.getText().trim().isEmpty();

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
		panel_caracteristique.setLayout(new GridLayout(8, 2, 0, 0));

		JLabel type_de_bien = new JLabel("Type de bien");
		panel_caracteristique.add(type_de_bien);
		JComboBox choix_type_de_bien = new JComboBox();
		choix_type_de_bien.setModel(new DefaultComboBoxModel(new String[] { "Appartement", "Bâtiment", "Garage" }));
		panel_caracteristique.add(choix_type_de_bien);

		JLabel num_fiscal = new JLabel("Numéro Fiscal");
		panel_caracteristique.add(num_fiscal);

		choix_num_fiscal = new JTextField();
		panel_caracteristique.add(choix_num_fiscal);
		choix_num_fiscal.setColumns(10);

		JLabel ville = new JLabel("Ville");
		panel_caracteristique.add(ville);

		JComboBox choix_ville = new JComboBox();
		choix_ville.setModel(new DefaultComboBoxModel());
		panel_caracteristique.add(choix_ville);

		JLabel adresse = new JLabel("Adresse");
		panel_caracteristique.add(adresse);

		JComboBox choix_adresse = new JComboBox();
		choix_adresse.setModel(new DefaultComboBoxModel());
		panel_caracteristique.add(choix_adresse);

		JLabel complement_adresse = new JLabel("Complément d'adresse");
		panel_caracteristique.add(complement_adresse);

		choix_complement_adresse = new JTextField();
		choix_complement_adresse.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		choix_complement_adresse.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panel_caracteristique.add(choix_complement_adresse);
		choix_complement_adresse.setColumns(10);

		JLabel surface = new JLabel("Surface habitable");
		panel_caracteristique.add(surface);

		JSpinner choix_surface = new JSpinner();
		choix_surface.setModel(new SpinnerNumberModel(Double.valueOf(9), Double.valueOf(9), null, Double.valueOf(0.5)));
		JSpinner.NumberEditor editor = new JSpinner.NumberEditor(choix_surface, "#0.## 'm²'");
		editor.setAlignmentY(1.0f);
		editor.setAlignmentX(1.0f);
		choix_surface.setEditor(editor);
		choix_surface.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		choix_surface.setAlignmentX(Component.RIGHT_ALIGNMENT);

		panel_caracteristique.add(choix_surface);

		JLabel nombre_piece = new JLabel("Nombre de pièces");
		panel_caracteristique.add(nombre_piece);

		JSpinner choix_nb_piece = new JSpinner();
		choix_nb_piece.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		choix_nb_piece.setAlignmentX(Component.RIGHT_ALIGNMENT);
		choix_nb_piece
				.setModel(new SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));
		panel_caracteristique.add(choix_nb_piece);

		JPanel panel_diagnostic = new JPanel();
		contenu.add(panel_diagnostic);
		panel_diagnostic.setLayout(new BorderLayout(0, 0));
		JLabel diagnostics = new JLabel("Diagnostics");
		diagnostics.setHorizontalAlignment(SwingConstants.CENTER);
		panel_diagnostic.add(diagnostics, BorderLayout.NORTH);

		JTable tableau_diagnostic = new JTable();
		tableau_diagnostic.setModel(new DefaultTableModel(new Object[][] {}, new String[] {}));
		DefaultTableModel model = (DefaultTableModel) tableau_diagnostic.getModel();
		model.setColumnIdentifiers(new String[] { "Libellé", "Fichier PDF" });
		JButton bouton_telechargement = new JButton();

		panel_diagnostic.add(new JScrollPane(tableau_diagnostic), BorderLayout.CENTER);

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
				b_bails.setFont(resizedFont);
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

		// Ajout des listeners sur chaque champ de texte
		choix_num_fiscal.getDocument().addDocumentListener(textListener);
		choix_complement_adresse.getDocument().addDocumentListener(textListener);

		choix_type_de_bien.addActionListener(e -> {
			String selectedType = (String) choix_type_de_bien.getSelectedItem();
			boolean isAppartement = "Appartement".equals(selectedType);

			// Gérer la visibilité des composants
			tableau_diagnostic.setVisible(isAppartement);
			surface.setVisible(isAppartement);
			choix_surface.setVisible(isAppartement);
			nombre_piece.setVisible(isAppartement);
			choix_nb_piece.setVisible(isAppartement);

			// Rafraîchir l'interface
			panel_caracteristique.revalidate();
			panel_caracteristique.repaint();
			panel_diagnostic.revalidate();
			panel_diagnostic.repaint();
		});

	}

}