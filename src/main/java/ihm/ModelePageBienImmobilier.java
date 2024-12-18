package ihm;

import DAO.DAOException;
import DAO.jdbc.DiagnosticDAO;
import DAO.jdbc.GarageDAO;
import DAO.jdbc.LogementDAO;
import DAO.jdbc.BatimentDAO;
import classes.Batiment;
import classes.Diagnostic;
import classes.Garage;
import classes.Logement;
import enumeration.NomsDiags;
import enumeration.TypeLogement;
import modele.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class ModelePageBienImmobilier {

	private PageNouveauBienImmobilier pageNouveauBienImmobilier;

	public ModelePageBienImmobilier(PageNouveauBienImmobilier pageNouveauBienImmobilier) {
		this.pageNouveauBienImmobilier = pageNouveauBienImmobilier;
	}

	public ActionListener getVilleActionListener(Map<String, List<String>> mapVillesAdresses) {
		return e -> {
			String selectedVille = (String) this.pageNouveauBienImmobilier.getChoix_ville().getSelectedItem();
			if (!mapVillesAdresses.containsKey(selectedVille)) {
				this.pageNouveauBienImmobilier.getChoix_adresse().setModel(new DefaultComboBoxModel());
			} else {
				this.pageNouveauBienImmobilier.getChoix_adresse().setModel(
						new DefaultComboBoxModel(mapVillesAdresses.get(selectedVille).toArray(new String[0])));
			}
		};
	}

	public ActionListener getCheckFieldsActionListener() {
		return e -> pageNouveauBienImmobilier.checkFields();
	}

	public ActionListener getValidateActionListener() {
		return e -> {
			TypeLogement selectedType = TypeLogement.values()[pageNouveauBienImmobilier.getChoix_type_de_bien()
					.getSelectedIndex()];
			try {
				switch (selectedType) {
					case APPARTEMENT:
						handleAppartementCreation(e);
						break;
					case BATIMENT:
						handleBatimentCreation(e);
						break;
					case GARAGE:
						handleGarageCreation(e);
						break;
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Une erreur est survenue lors de la création du bien immobilier.",
						"Erreur", JOptionPane.ERROR_MESSAGE);
			}
		};
	}

	private void addDiagnostics(String numero_fiscal) {
		DiagnosticDAO diagnosticDAO = new DiagnosticDAO();
		for (Diagnostic d : pageNouveauBienImmobilier.getMap_diagnostic().values()) {
			try {
				diagnosticDAO.create(d, numero_fiscal);
			} catch (DAOException e) {
				JOptionPane.showMessageDialog(null, "Problème lors de l'ajout d'un de vos diagnostics", "Erreur",
						JOptionPane.INFORMATION_MESSAGE);
				throw new RuntimeException();
			}
		}
		JOptionPane.showMessageDialog(null, "Vos diagnostics ont été ajoutés !", "Succès",
				JOptionPane.INFORMATION_MESSAGE);
	}

	private void handleAppartementCreation(ActionEvent e) throws Exception {
		Logement logement = new Logement(
				(Integer) pageNouveauBienImmobilier.getChoix_nb_piece().getValue(),
				((Double) pageNouveauBienImmobilier.getChoix_surface().getValue()),
				pageNouveauBienImmobilier.getChoix_num_fiscal().getText(),
				(String) pageNouveauBienImmobilier.getChoix_ville().getSelectedItem(),
				(String) pageNouveauBienImmobilier.getChoix_adresse().getSelectedItem(),
				pageNouveauBienImmobilier.getChoix_complement_adresse().getText(),
				pageNouveauBienImmobilier.getListe_diagnostic());
		LogementDAO logementDAO = new LogementDAO();
		logementDAO.create(logement);
		addDiagnostics(logement.getNumero_fiscal());
		if (pageNouveauBienImmobilier.getCheck_garage().isSelected()) {
			Garage garage = new Garage(
					pageNouveauBienImmobilier.getChoix_num_fiscal().getText(),
					(String) pageNouveauBienImmobilier.getChoix_ville().getSelectedItem(),
					(String) pageNouveauBienImmobilier.getChoix_adresse().getSelectedItem(),
					pageNouveauBienImmobilier.getChoix_complement_adresse().getText());
			GarageDAO garageDAO = new GarageDAO();
			garageDAO.create(garage);
			logementDAO.lierUnGarageAuBienLouable(logement, garage);
			JOptionPane.showMessageDialog(null, "L'appartement ainsi que son garage ont été ajoutés !", "Succès",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "L'appartement a été ajouté !", "Succès",
					JOptionPane.INFORMATION_MESSAGE);
		}
		refreshPage(e);
	}

	private void handleBatimentCreation(ActionEvent e) throws Exception {
		Batiment batiment = new Batiment(
				pageNouveauBienImmobilier.getChoix_num_fiscal().getText(),
				pageNouveauBienImmobilier.getTexte_ville().getText(),
				pageNouveauBienImmobilier.getTexte_adresse().getText(),
				pageNouveauBienImmobilier.getTexte_code_postal().getText());
		BatimentDAO batimentDAO = new BatimentDAO();
		batimentDAO.create(batiment);
		JOptionPane.showMessageDialog(null, "Le Bâtiment a été ajouté !", "Succès", JOptionPane.INFORMATION_MESSAGE);
		refreshPage(e);
	}

	private void handleGarageCreation(ActionEvent e) throws Exception {
		Garage garage = new Garage(
				pageNouveauBienImmobilier.getChoix_num_fiscal().getText(),
				(String) pageNouveauBienImmobilier.getChoix_ville().getSelectedItem(),
				(String) pageNouveauBienImmobilier.getChoix_adresse().getSelectedItem(),
				pageNouveauBienImmobilier.getChoix_complement_adresse().getText());
		GarageDAO garageDAO = new GarageDAO();
		garageDAO.create(garage);
		LogementDAO logementDAO = new LogementDAO();
		Integer idLogement = logementDAO.getId(pageNouveauBienImmobilier.getChoix_num_fiscal().getText());
		logementDAO.lierUnGarageAuBienLouable(logementDAO.read(idLogement), garage);
		JOptionPane.showMessageDialog(null, "Le Garage a été ajouté et lié à votre appartement !", "Succès",
				JOptionPane.INFORMATION_MESSAGE);
		refreshPage(e);
	}

	private void refreshPage(ActionEvent e) {
		JFrame ancienneFenetre = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
		ancienneFenetre.dispose();
		PageNouveauBienImmobilier nouvellePage = new PageNouveauBienImmobilier();
		nouvellePage.getFrame().setVisible(true);
	}

	public DocumentListener getTextFieldDocumentListener() {
		return new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				pageNouveauBienImmobilier.checkFields();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				pageNouveauBienImmobilier.checkFields();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				pageNouveauBienImmobilier.checkFields();
			}
		};
	}

	public ActionListener getChoixTypeBienListener() {
		return e -> {
			String selectedType = (String) this.pageNouveauBienImmobilier.getChoix_type_de_bien().getSelectedItem();

			boolean isAppartement = "Appartement".equals(selectedType);
			boolean isBatiment = "Bâtiment".equals(selectedType);

			// Gérer la visibilité des composants
			this.pageNouveauBienImmobilier.getDiagnostics().setVisible(isAppartement);
			this.pageNouveauBienImmobilier.getTableau_diagnostic().setVisible(isAppartement);
			this.pageNouveauBienImmobilier.getSurface().setVisible(isAppartement);
			this.pageNouveauBienImmobilier.getChoix_surface().setVisible(isAppartement);
			this.pageNouveauBienImmobilier.getNombre_piece().setVisible(isAppartement);
			this.pageNouveauBienImmobilier.getChoix_nb_piece().setVisible(isAppartement);
			this.pageNouveauBienImmobilier.getComplement_adresse().setVisible(!isBatiment);
			this.pageNouveauBienImmobilier.getChoix_complement_adresse().setVisible(!isBatiment);
			this.pageNouveauBienImmobilier.getCheck_garage().setVisible(isAppartement);

			// Remplacer les JComboBox par JTextField pour "Bâtiment"
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.insets = new Insets(0, 0, 5, 0);

			// Ville
			if (isBatiment) {
				this.pageNouveauBienImmobilier.getPanel_caracteristique()
						.remove(this.pageNouveauBienImmobilier.getChoix_ville());
				gbc.gridx = 1;
				gbc.gridy = 2;
				this.pageNouveauBienImmobilier.getPanel_caracteristique()
						.add(this.pageNouveauBienImmobilier.getTexte_ville(), gbc);
			} else {
				this.pageNouveauBienImmobilier.getPanel_caracteristique()
						.remove(this.pageNouveauBienImmobilier.getTexte_ville());
				gbc.gridx = 1;
				gbc.gridy = 2;
				this.pageNouveauBienImmobilier.getPanel_caracteristique()
						.add(this.pageNouveauBienImmobilier.getChoix_ville(), gbc);
			}

			// Adresse
			if (isBatiment) {
				this.pageNouveauBienImmobilier.getPanel_caracteristique()
						.remove(this.pageNouveauBienImmobilier.getChoix_adresse());
				gbc.gridx = 1;
				gbc.gridy = 3;
				this.pageNouveauBienImmobilier.getPanel_caracteristique()
						.add(this.pageNouveauBienImmobilier.getTexte_adresse(), gbc);
			} else {
				this.pageNouveauBienImmobilier.getPanel_caracteristique()
						.remove(this.pageNouveauBienImmobilier.getTexte_adresse());
				gbc.gridx = 1;
				gbc.gridy = 3;
				this.pageNouveauBienImmobilier.getPanel_caracteristique()
						.add(this.pageNouveauBienImmobilier.getChoix_adresse(), gbc);
			}

			// Code Postal
			if (isBatiment) {
				gbc.gridx = 1;
				gbc.gridy = 4;
				this.pageNouveauBienImmobilier.getPanel_caracteristique()
						.add(this.pageNouveauBienImmobilier.getTexte_code_postal(), gbc);
			} else {
				this.pageNouveauBienImmobilier.getPanel_caracteristique()
						.remove(this.pageNouveauBienImmobilier.getTexte_code_postal());
			}

			if (isBatiment) {
				gbc.gridx = 0;
				gbc.gridy = 4;
				this.pageNouveauBienImmobilier.getPanel_caracteristique()
						.add(this.pageNouveauBienImmobilier.getCode_postalLabel(), gbc);
			} else {
				this.pageNouveauBienImmobilier.getPanel_caracteristique()
						.remove(this.pageNouveauBienImmobilier.getCode_postalLabel());
			}

			// Rafraîchir l'interface
			this.pageNouveauBienImmobilier.getPanel_caracteristique().revalidate();
			this.pageNouveauBienImmobilier.getPanel_caracteristique().repaint();
		};
	}

	public ActionListener getTelechargerPDFButton(String diagnostic) {
		return e -> {
			// Créer un JFileChooser pour permettre de sélectionner un fichier
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Sélectionnez un fichier à associer au diagnostic");
			Date date = null;
			// Ouvrir le dialogue de sélection de fichier
			int returnValue = fileChooser.showOpenDialog(null);

			if (returnValue == JFileChooser.APPROVE_OPTION) {
				// Obtenir le fichier sélectionné
				File selectedFile = fileChooser.getSelectedFile();
				try {
					if (diagnostic == NomsDiags.PERFORMANCE_ENERGETIQUE.getDescription()
							|| diagnostic == NomsDiags.ELECTRICITE.getDescription()) {
						date = setDateDiag();
					}
					// Ajouter le diagnostic à la map
					NomsDiags diag = NomsDiags.fromDescription(diagnostic);
					this.pageNouveauBienImmobilier.getMap_diagnostic().put(diag.name(),
							new Diagnostic(diagnostic, selectedFile.getAbsolutePath(), date));
					if (isMapDiagnosticFull()) {
						pageNouveauBienImmobilier.checkFields();
					}

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				JButton btn = (JButton) e.getSource();
				btn.setText(btn.getText()+" \u2705");
			}
		};
	}

	public boolean isMapDiagnosticFull() {
		for (Map.Entry<String, Diagnostic> entry : this.pageNouveauBienImmobilier.getMap_diagnostic().entrySet()) {
			if (entry.getValue() == null) {
				return false;
			}
		}
		return true;
	}

	public Date setDateDiag() {
		AtomicReference<Date> date = new AtomicReference<>();
		JDialog dialog = new JDialog((Frame) null, "Saisir la date de péremption du diagnostic ", true);
		dialog.setSize(400, 200);
		dialog.setLayout(null);

		JLabel label = new JLabel("Date de péremption du diagnostic :");
		label.setBounds(20, 30, 200, 25);
		dialog.add(label);

		JTextField seuilField = new JTextField();
		seuilField.setPreferredSize(new Dimension(100, 22));
		seuilField.setBounds(220, 30, 100, 25);

		dialog.add(seuilField);

		JButton validerButton = new JButton("Valider");
		validerButton.setBounds(150, 100, 100, 30);
		dialog.add(validerButton);

		validerButton.addActionListener(event -> {
			try {
				date.set(Date.valueOf(seuilField.getText()));
				JOptionPane.showMessageDialog(dialog,
						"La date de péremption du diagnostic a été mis à jour à " + date + ".",
						"Confirmation",
						JOptionPane.INFORMATION_MESSAGE);
				dialog.dispose();
			} catch (IllegalArgumentException ex) {
				JOptionPane.showMessageDialog(dialog,
						"Veuillez entrer une date valide sous le format yyyy-mm-dd.",
						"Erreur",
						JOptionPane.ERROR_MESSAGE);
			}
		});

		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
		return date.get();
	}
	public ActionListener quitterPage(){
		return e -> {
			pageNouveauBienImmobilier.getFrame().dispose();
			PageMesBiens pageMesBiens = new PageMesBiens();
			PageMesBiens.main(null);
		};
	}
}
