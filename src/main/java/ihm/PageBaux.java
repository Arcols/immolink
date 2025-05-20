package ihm;

import java.awt.BorderLayout;
import java.awt.Font;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableModel;

import modele.ModelePageBaux;

public class PageBaux extends PageAbstraite {
	private JTable table;
	private ModelePageBaux modele;
	private TableModel table_model;
	private Set<String> setVilles;
	private Map<String, List<String>> map_villes_adresses;

	/**
	 * Create the application.
	 */
	public PageBaux(int x,int y) {
		super(x,y);
		this.modele = new ModelePageBaux(this);
		try {
			this.map_villes_adresses = this.modele.getAdressesVilles();
			this.setVilles = this.map_villes_adresses.keySet();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		CreerSpecific();
	}

	@Override
	public void CreerSpecific() {
		JLabel titleLabel = new JLabel("Mes baux", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
		panel_body.add(titleLabel, BorderLayout.NORTH);

		// Créer la table avec ce modèle
		table_model = modele.loadDataBauxToTable();
		table = new JTable(table_model);

		table.setDefaultRenderer(Object.class, new ModelePageBaux.CustomRowRenderer());
		table.addMouseListener(modele.mouseAdapter(table));

		// Ajouter la table dans un JScrollPane pour permettre le défilement
		JScrollPane scrollPane = new JScrollPane(table);
		panel_body.add(scrollPane, BorderLayout.CENTER);

		double total_revenu = modele.getRevenu();
		JLabel revenu_immobilier = new JLabel(" Total revenu immobilier : "+ total_revenu +" €");
		revenu_immobilier.setHorizontalTextPosition(SwingConstants.LEFT);
		revenu_immobilier.setVerticalTextPosition(SwingConstants.TOP);
		revenu_immobilier.setVerticalAlignment(SwingConstants.CENTER);
		bas_de_page.add(revenu_immobilier, BorderLayout.WEST);

		JButton ajouter = new JButton("Nouveau bail");
		ajouter.setEnabled(!setVilles.isEmpty());
		ajouter.setHorizontalTextPosition(SwingConstants.LEFT);
		ajouter.setVerticalTextPosition(SwingConstants.TOP);
		ajouter.setVerticalAlignment(SwingConstants.BOTTOM);
		bas_de_page.add(ajouter, BorderLayout.EAST);

		ajouter.addActionListener(modele.ouvrirPageNouveauBail());
		frame.addWindowListener(modele.fermerFenetre());
	}
}
