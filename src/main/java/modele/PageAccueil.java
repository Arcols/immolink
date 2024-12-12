package modele;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import DAO.jdbc.LocataireDAO;
import ihm.Charte;
import ihm.Menu;
import ihm.ModelePageAccueil;
import ihm.ResizedImage;

public class PageAccueil {

	private JFrame frame;
	private JLabel logo;
	private JTable table;
	private LocataireDAO daoLoc;

	/**
	 * Lance l'application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				PageAccueil window = new PageAccueil();
				window.frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Constructeur de l'application.
	 */
	public PageAccueil() {
		this.initialize();
	}

	/**
	 * Initialise le contenu de la fenêtre.
	 */
	private void initialize() {
		// Initialisation de la fenêtre principale
		frame = new JFrame("Page d'accueil");
		frame.setBounds(100, 100, 750, 400);
		frame.getContentPane().setBackground(Charte.FOND.getCouleur());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		// Création de l'entête
		initHeader();

		// Création du corps de la fenêtre
		initBody();

		// Gestion des événements de redimensionnement
		frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				ResizedImage res = new ResizedImage();
				res.resizeImage("logo+nom.png", PageAccueil.this.frame,
						PageAccueil.this.logo, 3, 8);
				int frameWidth = PageAccueil.this.frame.getWidth();
				int frameHeight = PageAccueil.this.frame.getHeight();

				int newFontSize = Math.min(frameWidth, frameHeight) / 30;

				// Appliquer la nouvelle police au bouton
				Font resizedFont = new Font("Arial", Font.PLAIN, newFontSize);
				// b_loca.setFont(resizedFont);
				// b_baux.setFont(resizedFont);
				// b_accueil.setFont(resizedFont);
				// b_profil.setFont(resizedFont);
				// b_biens.setFont(resizedFont);
			}
		});

	}

	/**
	 * Initialise l'entête contenant le logo et le menu de navigation.
	 */
	private void initHeader() {
		JPanel headerPanel = new JPanel(new BorderLayout());
		headerPanel.setBackground(Charte.ENTETE.getCouleur());
		headerPanel.setBorder(new LineBorder(Color.BLACK, 2));

		// Logo
		logo = new JLabel();
		headerPanel.add(logo, BorderLayout.WEST);

		// Menu de boutons
		JPanel menuPanel = new JPanel(new GridLayout(1, 5, 10, 0));
		menuPanel.setBackground(Charte.ENTETE.getCouleur());

		String[] menuItems = { "Accueil", "Mes baux", "Mes Biens" };
		Menu menuListener = new Menu(frame);

		for (String item : menuItems) {
			JButton button = createMenuButton(item, menuListener);
			menuPanel.add(button);
		}

		headerPanel.add(menuPanel, BorderLayout.CENTER);
		frame.add(headerPanel, BorderLayout.NORTH);
	}

	/**
	 * Crée un bouton de menu avec les propriétés standard.
	 * 
	 * @param text     Texte du bouton.
	 * @param listener Écouteur d'événements.
	 * @return Le bouton configuré.
	 */
	private JButton createMenuButton(String text, Menu listener) {
		JButton button = new JButton(text);
		button.setBorderPainted(false);
		button.setBackground(Charte.ENTETE.getCouleur());
		button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		button.addActionListener(listener);
		return button;
	}

	/**
	 * Initialise le corps principal de la fenêtre.
	 */
	private void initBody() {
		JPanel bodyPanel = new JPanel(new BorderLayout());
		frame.add(bodyPanel, BorderLayout.CENTER);
		ModelePageAccueil modele=new ModelePageAccueil(this);

		// Titre
		JLabel titleLabel = new JLabel("Locataires", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
		bodyPanel.add(titleLabel, BorderLayout.NORTH);

		// Tableau des locataires
		table = new JTable();
		JScrollPane scrollPane = new JScrollPane(table);
		bodyPanel.add(scrollPane, BorderLayout.CENTER);

		// Chargement des données dans le tableau
		try {
			DefaultTableModel model = ModelePageAccueil.loadDataLocataireToTable();
			table.setModel(model);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(frame, "Erreur lors du chargement des données : " + e.getMessage(),
					"Erreur", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		JPanel bas_de_page = new JPanel();
		this.frame.getContentPane().add(bas_de_page, BorderLayout.SOUTH);
		bas_de_page.setLayout(new BorderLayout(0, 0));

		JPanel regimePanel = new JPanel();
		bas_de_page.add(regimePanel,BorderLayout.WEST);

		JLabel regimeLabel = new JLabel("Mon régime microfoncier");
		regimePanel.add(regimeLabel);

		JButton btnActualiserRegime = new JButton("Actualiser");
		regimePanel.add(btnActualiserRegime);
		btnActualiserRegime.addActionListener(ModelePageAccueil.getActionListenerForActualiser(frame));

		JPanel declaFidscalePanel = new JPanel();
		bas_de_page.add(declaFidscalePanel,BorderLayout.CENTER);

		JLabel declaFiscaleLabel = new JLabel("Déclaration fiscale");
		declaFidscalePanel.add(declaFiscaleLabel);

		JButton declaFiscaleButton = new JButton("Générer");
		declaFidscalePanel.add(declaFiscaleButton);

		JButton ajouter = new JButton("Ajouter un locataire");
		bas_de_page.add(ajouter, BorderLayout.EAST);
		ajouter.addActionListener(modele.ouvrirNouveauLocataire());
	}

	public JFrame getFrame() {
		return frame;
	}
}