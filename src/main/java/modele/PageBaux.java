package modele;

import DAO.DAOException;
import DAO.jdbc.BailDAO;
import DAO.jdbc.BatimentDAO;
import DAO.jdbc.BienLouableDAO;
import DAO.jdbc.LouerDAO;
import classes.Bail;
import classes.BienLouable;
import classes.Locataire;
import ihm.Charte;
import ihm.Menu;
import ihm.ModelePageBaux;
import ihm.ResizedImage;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import static ihm.Charte.*;

public class PageBaux {

	private JFrame frame;
	private JLabel logo;
	private JTextField choix_loyer;
	private JTextField choix_prevision;
	private JTextField choix_depot_garantie;
	private JTable table;
	private DefaultTableModel tableModel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					PageBaux window = new PageBaux();
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
	public PageBaux() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		ModelePageBaux modele=new ModelePageBaux(this);
		this.frame = new JFrame("Page mes baux");
		this.frame.setBounds(100, 100, 750, 400);
		this.frame.getContentPane().setBackground(FOND.getCouleur());
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

		JPanel titre = new JPanel();
		FlowLayout fl_titre = (FlowLayout) titre.getLayout();
		body.add(titre, BorderLayout.NORTH);

		JLabel titrePage = new JLabel("Mes baux");
		titrePage.setAlignmentY(0.0f);
		titrePage.setAlignmentX(0.5f);
		titre.add(titrePage);

		// Créer les données fictives pour le tableau
		List<Bail> listBail = new DAO.jdbc.BailDAO().getAllBaux();

		String[][] data = new String[listBail.size()][];
		String[] ligne;
		int i = 0;
		for (Bail b : listBail) {
			BienLouable logement = null;
			try {
				logement = new BienLouableDAO().readFisc(b.getFisc_bien());
			} catch (DAOException e) {
				throw new RuntimeException(e);
			}
			List<Integer> idLocataires = new LouerDAO().getIdLoc(new DAO.jdbc.BailDAO().getId(b));
			String noms=new String();
			for (int id : idLocataires) {
				Locataire loc = new DAO.jdbc.LocataireDAO().getLocFromId(id);
				noms+=loc.getNom()+" ";
			}
			ligne = new String[]{logement.getAdresse(),
					logement.getComplement_adresse(),
					logement.getVille(),
					noms,
					String.valueOf(b.getLoyer())
			};
			data[i] = ligne;
			i++;
		}
		String[] columns = { "Adresse", "Complément", "Ville", "Locataire(s)", "Loyer", "Statut" };
		// Créer le modèle de table avec les données
		DefaultTableModel tableModel = new DefaultTableModel(data, columns) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Toutes les cellules sont non éditables
			}
		};

		// Créer la table avec ce modèle
		JTable table = new JTable(tableModel);

		// Ajouter la table dans un JScrollPane pour permettre le défilement
		JScrollPane scrollPane = new JScrollPane(table);
		body.add(scrollPane, BorderLayout.CENTER);

		// Panel bas pour le bouton "Nouveau"
		JPanel bas_de_page = new JPanel();
		this.frame.getContentPane().add(bas_de_page, BorderLayout.SOUTH);
		bas_de_page.setLayout(new BorderLayout(0, 0));

		Double totalrevenu = new BailDAO().getAllLoyer();
		JLabel revenu_immobilier = new JLabel("Total revenu immobilier : "+String.valueOf(totalrevenu)+" €");
		revenu_immobilier.setHorizontalTextPosition(SwingConstants.LEFT);
		revenu_immobilier.setVerticalTextPosition(SwingConstants.TOP);
		revenu_immobilier.setVerticalAlignment(SwingConstants.BOTTOM);
		bas_de_page.add(revenu_immobilier, BorderLayout.WEST);

		JButton ajouter = new JButton("Nouveau bail");
		ajouter.setEnabled(true); // Le bouton est maintenant activé
		ajouter.setHorizontalTextPosition(SwingConstants.LEFT);
		ajouter.setVerticalTextPosition(SwingConstants.TOP);
		ajouter.setVerticalAlignment(SwingConstants.BOTTOM);
		bas_de_page.add(ajouter, BorderLayout.EAST);

		ajouter.addActionListener(modele.ouvrirPageNouveauBail());

		this.frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				ResizedImage res = new ResizedImage();
				res.resizeImage("logo+nom.png", PageBaux.this.frame,
						PageBaux.this.logo, 3, 8);
				int frameWidth = PageBaux.this.frame.getWidth();
				int frameHeight = PageBaux.this.frame.getHeight();

				int newFontSize = Math.min(frameWidth, frameHeight) / 30;

				// Appliquer la nouvelle police au bouton
				Font resizedFont = new Font("Arial", Font.PLAIN, newFontSize);
				b_baux.setFont(resizedFont);
				b_accueil.setFont(resizedFont);
				b_biens.setFont(resizedFont);
			}
		});

		table.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				// Vérifier s'il s'agit d'un double-clic
				if (evt.getClickCount() == 2) {
					// Obtenir l'index de la ligne cliquée
					int row = table.getSelectedRow();

					// Récupérer les données de la ligne sélectionnée
					if (row != -1) {
						String adresse = (String) tableModel.getValueAt(row, 0);
						String complement = (String) tableModel.getValueAt(row, 1);
						String ville = (String) tableModel.getValueAt(row, 2);

                        try {
                            BienLouable bien = new BienLouableDAO().readFisc(new BienLouableDAO().getFiscFromCompl(ville,adresse,complement));
							Bail bail =  new BienLouableDAO().getBailFromBien(bien);
							frame.dispose();
							PageUnBail PageUnBail = new PageUnBail(bail);

                        } catch (DAOException e) {
                            throw new RuntimeException(e);
                        }
                        // Ouvrir une nouvelle fenêtre avec ces données
					}
				}
			}
		});
	}

	private void openNewPage(String adresse, String complement, String ville, String locataire, String loyer,
							 String statut) {
		// Créer une nouvelle JFrame
		JFrame newFrame = new JFrame("Détails du bail");
		newFrame.setSize(400, 300);
		newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Ajouter les données dans un panel
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(6, 2));

		panel.add(new JLabel("Adresse:"));
		panel.add(new JLabel(adresse));

		panel.add(new JLabel("Complément:"));
		panel.add(new JLabel(complement));

		panel.add(new JLabel("Ville:"));
		panel.add(new JLabel(ville));

		panel.add(new JLabel("Locataire:"));
		panel.add(new JLabel(locataire));

		panel.add(new JLabel("Loyer:"));
		panel.add(new JLabel(loyer));

		panel.add(new JLabel("Statut:"));
		panel.add(new JLabel(statut));

		newFrame.add(panel);

		// Rendre la nouvelle fenêtre visible
		newFrame.setVisible(true);
	}

	public JFrame getFrame() {
		return frame;
	}

}
