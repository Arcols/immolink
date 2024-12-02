package ihm;

import DAO.DAOException;
import DAO.jdbc.BienLouableDAO;
import classes.BienLouable;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

public class ModelePageBienImmobilier {

	public static DefaultTableModel loadDataBienImmoToTable() throws SQLException, DAOException {
		// Liste des colonnes pour les biens immobiliers
		String[] columnNames = {"Numéro Fiscal", "Adresse", "Ville", "Complément Adresse"};

		// Création du modèle de table
		DefaultTableModel model = new DefaultTableModel(columnNames, 0); // `0` pour aucune ligne au départ

		// Récupération des biens immobiliers
		BienLouableDAO biensDAO=new BienLouableDAO();
        List<BienLouable> biens = biensDAO.findAll();

        // Remplissage du modèle avec les données des biens
		for (BienLouable bien : biens) {
			Object[] rowData = {
					bien.getNumero_fiscal(),
					bien.getAdresse(),
					bien.getVille(),
					bien.getComplement_adresse()
			};
			model.addRow(rowData); // Ajout de la ligne dans le modèle
		}

		return model;
	}
}
