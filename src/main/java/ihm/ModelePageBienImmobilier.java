package ihm;

import DAO.jdbc.BienLouableDAO;
import classes.BienLouable;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

public class ModelePageBienImmobilier {

	private static DefaultTableModel loadDataBienImmoToTable() throws SQLException {
		// Liste des colonnes pour les biens immobiliers
		String[] columnNames = {"Numéro Fiscal", "Adresse", "Ville", "Complément Adresse"};

		// Création du modèle de table
		DefaultTableModel model = new DefaultTableModel(columnNames, 0); // `0` pour aucune ligne au départ

		// Récupération des biens immobiliers
		BienLouableDAO allBiens = new BienLouableDAO();
		List<BienLouable> biens = allBiens.findAll();

		// Remplissage du modèle avec les données des biens
		for (BienLouable bien2 : biens) {
			Object[] rowData = {
					bien2.getNumero_fiscal(),
					bien2.getAdresse(),
					bien2.getVille(),
					bien2.getComplement_adresse()
			};
			model.addRow(rowData); // Ajout de la ligne dans le modèle
		}

		return model;
	}
}
