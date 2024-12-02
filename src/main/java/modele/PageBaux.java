package modele;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;

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
		this.frame = new JFrame();
		this.frame.setBounds(100, 100, 750, 400);
		this.frame.getContentPane().setBackground(FOND.getCouleur());
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Panel d'entête pour le logo et le nom de l'appli
		JPanel entete = new JPanel();
		this.frame.getContentPane().add(entete, BorderLayout.NORTH);
		entete.setLayout(new BorderLayout(0, 0));

		entete.setBackground(ENTETE.getCouleur());
		entete.setBorder(new LineBorder(Color.BLACK, 2));
		// Label pour le logo (Image)
		this.logo = new JLabel("");
		entete.add(this.logo, BorderLayout.WEST);
		JPanel menu_bouttons = new JPanel();

		entete.add(menu_bouttons, BorderLayout.CENTER);
		menu_bouttons.setLayout(new GridLayout(0, 5, 0, 0));
		menu_bouttons.setBackground(ENTETE.getCouleur());

		JButton b_accueil = new JButton("Accueil");
		b_accueil.setBorderPainted(false);
		b_accueil.setBackground(ENTETE.getCouleur());
		b_accueil.setCursor(new Cursor(Cursor.HAND_CURSOR));
		menu_bouttons.add(b_accueil);

		JButton b_profil = new JButton("Profil");
		b_profil.setBorderPainted(false);
		b_profil.setBackground(ENTETE.getCouleur());
		b_profil.setCursor(new Cursor(Cursor.HAND_CURSOR));
		menu_bouttons.add(b_profil);

		JButton b_baux = new JButton("Mes baux");
		b_baux.setBorderPainted(false);
		b_baux.setBackground(ENTETE.getCouleur());
		b_baux.setCursor(new Cursor(Cursor.HAND_CURSOR));
		menu_bouttons.add(b_baux);

		JButton b_loca = new JButton("Locataires");
		b_loca.setBorderPainted(false);
		b_loca.setBackground(ENTETE.getCouleur());
		b_loca.setCursor(new Cursor(Cursor.HAND_CURSOR));
		menu_bouttons.add(b_loca);

		JButton b_biens = new JButton("Mes Biens");
		b_biens.setBorderPainted(false);
		b_biens.setBackground(ENTETE.getCouleur());
		b_biens.setCursor(new Cursor(Cursor.HAND_CURSOR));
		menu_bouttons.add(b_biens);

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
		String[][] data = { { "123 Rue A", "Bâtiment 1", "Paris", "Alice Dupont", "800", "Payé" },
				{ "456 Rue B", "Bâtiment 2", "Lyon", "Bob Martin", "900", "Payé" },
				{ "789 Rue C", "Bâtiment 3", "Marseille", "Charlie Durand", "950", "Payé" },
				{ "101 Rue D", "Bâtiment 4", "Paris", "David Lefevre", "850", "Payé" },
				{ "202 Rue E", "Bâtiment 5", "Bordeaux", "Eve Robert", "950", "Payé" },
				{ "303 Rue F", "Bâtiment 6", "Lille", "Franck Bernard", "980", "Payé" } };

		String[] columns = { "Adresse", "Complément", "Ville", "Locataires", "Loyer", "Statut" };

		// Créer le modèle de table avec les données
		DefaultTableModel tableModel = new DefaultTableModel(data, columns);

		// Créer la table avec ce modèle
		JTable table = new JTable(tableModel);

		// Ajouter la table dans un JScrollPane pour permettre le défilement
		JScrollPane scrollPane = new JScrollPane(table);
		body.add(scrollPane, BorderLayout.CENTER);

		// Panel bas pour le bouton "Nouveau"
		JPanel bas_de_page = new JPanel();
		this.frame.getContentPane().add(bas_de_page, BorderLayout.SOUTH);
		bas_de_page.setLayout(new BorderLayout(0, 0));

		JButton ajouter = new JButton("Nouveau bail");
		ajouter.setEnabled(true); // Le bouton est maintenant activé
		ajouter.setHorizontalTextPosition(SwingConstants.LEFT);
		ajouter.setVerticalTextPosition(SwingConstants.TOP);
		ajouter.setVerticalAlignment(SwingConstants.BOTTOM);
		bas_de_page.add(ajouter, BorderLayout.EAST);
	}

}
