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
import java.util.List;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import DAO.jdbc.LocataireDAO;
import classes.Locataire;
import ihm.Charte;
import ihm.Menu;
import ihm.ResizedImage;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

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
				ResizedImage.resizeImage("/ressources/images/logo+nom.png", frame, logo, 3, 8);
			}
		});

	}
	private void loadDataToTable() throws SQLException {
	    // Liste des colonnes
	    String[] columnNames = {"Nom", "Prénom", "Téléphone", "Mail", "Genre", "Date d'arrivée"};

	    // Création du modèle de table
	    DefaultTableModel model = new DefaultTableModel(columnNames, 0); // `0` pour aucune ligne au départ

	    // Récupération des locataires
		daoLoc = new LocataireDAO();
	    List<Locataire> locataires = daoLoc.getAllLocataire();

	    // Remplissage du modèle avec les données des locataires
	    for (Locataire locataire : locataires) {
	        Object[] rowData = {
	            locataire.getNom(),
	            locataire.getPrénom(),
	            locataire.getTéléphone(),
	            locataire.getMail(),
	            locataire.getGenre(),
	            locataire.getDateArrive()
	        };
	        model.addRow(rowData); // Ajout de la ligne dans le modèle
	    }

	    // Attribution du modèle au JTable
	    this.table.setModel(model);
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

		String[] menuItems = {"Accueil", "Profil", "Mes baux", "Locataires", "Mes Biens"};
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
	 * @param text Texte du bouton.
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
			DefaultTableModel model = modele.ModelePageAccueil.loadDataLocataireToTable();
			table.setModel(model);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(frame, "Erreur lors du chargement des données : " + e.getMessage(),
					"Erreur", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		JPanel boutonsPanel = new JPanel();
		bodyPanel.add(boutonsPanel, BorderLayout.SOUTH);
		GridBagLayout gbl_boutonsPanel = new GridBagLayout();
		gbl_boutonsPanel.columnWidths = new int[] {0};
		gbl_boutonsPanel.rowHeights = new int[] {0};
		gbl_boutonsPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0};
		gbl_boutonsPanel.rowWeights = new double[]{0.0};
		boutonsPanel.setLayout(gbl_boutonsPanel);

		JPanel regimePanel = new JPanel();
		GridBagConstraints gbc_regimePanel = new GridBagConstraints();
		gbc_regimePanel.anchor = GridBagConstraints.NORTHWEST;
		gbc_regimePanel.insets = new Insets(0, 0, 0, 5);
		gbc_regimePanel.gridx = 1;
		gbc_regimePanel.gridy = 0;
		boutonsPanel.add(regimePanel, gbc_regimePanel);
		regimePanel.setLayout(new GridLayout(2, 1, 0, 0));

		JLabel regimeLabel = new JLabel("Mon régime microfoncier");
		regimePanel.add(regimeLabel);

		JButton btnNewButton = new JButton("Actualiser");
		regimePanel.add(btnNewButton);

		JPanel declaFidscalePanel = new JPanel();
		GridBagConstraints gbc_declaFidscalePanel = new GridBagConstraints();
		gbc_declaFidscalePanel.anchor = GridBagConstraints.NORTHWEST;
		gbc_declaFidscalePanel.gridx = 3;
		gbc_declaFidscalePanel.gridy = 0;
		boutonsPanel.add(declaFidscalePanel, gbc_declaFidscalePanel);
		declaFidscalePanel.setLayout(new GridLayout(0, 1, 0, 0));

		JLabel declaFiscaleLabel = new JLabel("Déclaration fiscale");
		declaFidscalePanel.add(declaFiscaleLabel);

		JButton declaFiscaleButton = new JButton("Générer");
		declaFidscalePanel.add(declaFiscaleButton);
	}
}