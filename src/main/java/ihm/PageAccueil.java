package ihm;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

import DAO.DAOException;
import DAO.db.ConnectionDB;
import DAO.jdbc.LocataireDAO;
import classes.Locataire;
import modele.Charte;
import modele.Menu;
import modele.ModelePageAccueil;
import modele.ResizedImage;

import javax.swing.*; // Composants Swing classiques
import javax.swing.table.TableColumnModel;

import com.formdev.flatlaf.FlatLightLaf;


public class PageAccueil {

	private JFrame frame;
	private JLabel logo;
	private JTable table;
	private LocataireDAO daoLoc;

	/**
	 * Lance l'application.
	 */
	public static void main(String[] args) {
        try {
			UIManager.setLookAndFeel(new FlatLightLaf());
		} catch (UnsupportedLookAndFeelException e) {
			throw new RuntimeException(e);
		}
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
		JPanel menuPanel = new JPanel(new GridLayout(1, 6, 10, 0));
		menuPanel.setBackground(Charte.ENTETE.getCouleur());

		Menu menuListener = new Menu(frame);
        String notifs = null;
        try {
            notifs = "Notifications ("+menuListener.getNbNotifs()+")";
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        String[] menuItems = { "Accueil", "Mes baux", "Mes Biens", notifs };

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
		DefaultTableModel model = null;
		// Chargement des données dans le tableau
		try {
			model = ModelePageAccueil.loadDataLocataireToTable();
			table.setModel(model);
			TableColumnModel columnModel = table.getColumnModel();
			columnModel.getColumn(0).setPreferredWidth(100); // Nom
			columnModel.getColumn(1).setPreferredWidth(100); // Prénom
			columnModel.getColumn(2).setPreferredWidth(100); // Lieu Naissance
			columnModel.getColumn(3).setPreferredWidth(160); // Date Naissance
			columnModel.getColumn(4).setPreferredWidth(80); // Téléphone
			columnModel.getColumn(5).setPreferredWidth(150); // Mail
			columnModel.getColumn(6).setPreferredWidth(50);  // Genre
			columnModel.getColumn(7).setPreferredWidth(80); // Date d'arrivée

			// Ajouter un renderer pour colorer les lignes en fonction de la dernière colonne
			table.setDefaultRenderer(Object.class, modele.couleurLigne());
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
		declaFiscaleButton.addActionListener(modele.choix_annee());

		JButton ajouter = new JButton("Ajouter un locataire");
		bas_de_page.add(ajouter, BorderLayout.EAST);
		ajouter.addActionListener(modele.ouvrirNouveauLocataire());
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// Action to perform on application close
				performCloseAction();
			}
		});

		DefaultTableModel finalModel = model;
		table.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				// Vérifier s'il s'agit d'un double-clic
				if (evt.getClickCount() == 2) {
					// Obtenir l'index de la ligne cliquée
					int row = table.getSelectedRow();

					// Récupérer les données de la ligne sélectionnée
					if (row != -1) {
						String nom = (String) finalModel.getValueAt(row, 0);
						String prenom = (String) finalModel.getValueAt(row, 1);
						String telephone = (String) finalModel.getValueAt(row, 4);

                        LocataireDAO locataireDAO = new LocataireDAO();
                        Locataire locataire = locataireDAO.getLocataireByNomPrénomTel(nom,prenom,telephone);
                        frame.dispose();
                        new PageUnLocataire(locataire);

                        // Ouvrir une nouvelle fenêtre avec ces données
					}
				}
			}
		});
	}

	private void performCloseAction() {
		ConnectionDB.destroy(); // fermeture de la connection
		frame.dispose();
	}

	public JFrame getFrame() {
		return frame;
	}
}