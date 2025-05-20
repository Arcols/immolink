package ihm;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.SQLException;

// Composants Swing classiques
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import com.formdev.flatlaf.FlatLightLaf;

import modele.ModelePageAccueil;


public class PageAccueil extends PageAbstraite{
	private ModelePageAccueil modele;
	private JTable table;

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

	public PageAccueil(){
		super();
		modele = new ModelePageAccueil(this);
		CreerSpecific();
	}

	/**
	 * Constructeur de l'application.
	 */
	public PageAccueil(int x,int y) {
		super(x,y);
		modele = new ModelePageAccueil(this);
		CreerSpecific();
	}

	@Override
	public void CreerSpecific() {
		JLabel titleLabel = new JLabel("Locataires", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
		panel_body.add(titleLabel, BorderLayout.NORTH);

		// Tableau des locataires
		table = new JTable();
		JScrollPane scrollPane = new JScrollPane(table);
		panel_body.add(scrollPane, BorderLayout.CENTER);
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
		JPanel panel_regime = new JPanel();
		bas_de_page.add(panel_regime,BorderLayout.WEST);

		JLabel regimeLabel = new JLabel("Mon régime microfoncier");
		panel_regime.add(regimeLabel);

		JButton btn_actualiser_regime = new JButton("Actualiser");
		panel_regime.add(btn_actualiser_regime);
		btn_actualiser_regime.addActionListener(ModelePageAccueil.getActionListenerForActualiser(frame));

		JPanel panel_decla_fidscale = new JPanel();
		bas_de_page.add(panel_decla_fidscale,BorderLayout.CENTER);

		JLabel declaFiscaleLabel = new JLabel("Déclaration fiscale");
		panel_decla_fidscale.add(declaFiscaleLabel);

		JButton btn_decla_fidscale = new JButton("Générer");
		panel_decla_fidscale.add(btn_decla_fidscale);
		btn_decla_fidscale.addActionListener(modele.choix_annee());

		JButton btn_ajouter = new JButton("Ajouter un locataire");
		bas_de_page.add(btn_ajouter, BorderLayout.EAST);
		btn_ajouter.addActionListener(modele.ouvrirNouveauLocataire());
		frame.addWindowListener(modele.fermerFenetre());

		table.addMouseListener(modele.mouseAdapter(table));
	}
}