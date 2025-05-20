package modele;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.toedter.calendar.JDateChooser;

import DAO.DAOException;
import DAO.db.ConnectionDB;
import DAO.jdbc.BatimentDAO;
import DAO.jdbc.DiagnosticDAO;
import DAO.jdbc.GarageDAO;
import DAO.jdbc.LogementDAO;
import classes.Batiment;
import classes.Diagnostic;
import classes.Garage;
import classes.Logement;
import enumeration.NomsDiags;
import enumeration.TypeLogement;
import ihm.PageMesBiens;
import ihm.PageNouveauBienImmobilier;
import ihm.PopUpCreationGarageLieBL;

public class ModelePageNouveauBienImmobilier {

	private final PageNouveauBienImmobilier page_nouveau_bien_immobilier;

	public ModelePageNouveauBienImmobilier(PageNouveauBienImmobilier page_nouveau_bien_immobilier) {
		this.page_nouveau_bien_immobilier = page_nouveau_bien_immobilier;
	}

	public ActionListener getVilleActionListener(Map<String, List<String>> mapVillesAdresses) {
		return e -> {
			String ville_choisi = (String) this.page_nouveau_bien_immobilier.getChoix_ville().getSelectedItem();
			if (!mapVillesAdresses.containsKey(ville_choisi)) {
				this.page_nouveau_bien_immobilier.getChoix_adresse().setModel(new DefaultComboBoxModel());
			} else {
				this.page_nouveau_bien_immobilier.getChoix_adresse().setModel(
						new DefaultComboBoxModel(mapVillesAdresses.get(ville_choisi).toArray(new String[0])));
			}
		};
	}

	public ActionListener getCheckFieldsActionListener() {
		return e -> checkFields();
	}

	public ActionListener getValidateActionListener() {
		return e -> {
			String type_bien = (String) page_nouveau_bien_immobilier.getChoix_type_de_bien().getSelectedItem();
            TypeLogement type_choisi = TypeLogement.fromString(type_bien);
			try {
				switch (type_choisi) {
					case APPARTEMENT:
						handleAppartementCreation(e);
						break;
					case BATIMENT:
						handleBatimentCreation(e);
						break;
					case GARAGE_PAS_ASSOCIE:
						handleGarageCreation(e);
						break;
					case MAISON:
						handleMaisonCreation(e);
						break;
				}
			} catch (Exception ex) {
				if (ex.getMessage().contains("numero_fiscal")){
					JOptionPane.showMessageDialog(null, "Le numéro fiscal est déjà utilisé pour un autre bien louable.",
							"Erreur", JOptionPane.ERROR_MESSAGE);
				} else{
					JOptionPane.showMessageDialog(null, "Une erreur est survenue lors de la création du bien immobilier.\n "+ex.getMessage(),
							"Erreur", JOptionPane.ERROR_MESSAGE);
				}
			}
		};
	}

	private void addDiagnostics(String numero_fiscal) {
		DiagnosticDAO diagnostic_DAO = new DiagnosticDAO();
		for (Diagnostic d : page_nouveau_bien_immobilier.getMap_diagnostic().values()) {
			try {
				if(d == null){
					continue;
				}
				diagnostic_DAO.create(d, numero_fiscal);
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
		if(isGarageLinkedToSameFiscalNumber()){
			return;
		}
		Logement logement = new Logement(
				(Integer) page_nouveau_bien_immobilier.getChoix_nb_piece().getValue(),
				((Double) page_nouveau_bien_immobilier.getChoix_surface().getValue()),
				page_nouveau_bien_immobilier.getChoix_num_fiscal().getText(),
				(String) page_nouveau_bien_immobilier.getChoix_ville().getSelectedItem(),
				(String) page_nouveau_bien_immobilier.getChoix_adresse().getSelectedItem(),
				page_nouveau_bien_immobilier.getChoix_complement_adresse().getText(),
				page_nouveau_bien_immobilier.getListe_diagnostic(),
				TypeLogement.APPARTEMENT);
		LogementDAO logement_DAO = new LogementDAO();
		logement_DAO.create(logement,TypeLogement.APPARTEMENT);
		addDiagnostics(logement.getNumeroFiscal());
		if (!(page_nouveau_bien_immobilier.getGarageLie()).equals(new Garage("            ", "", "", "", TypeLogement.NONE))) {
			logement_DAO.lierUnGarageAuBienLouable(logement, page_nouveau_bien_immobilier.getGarageLie(), TypeLogement.APPARTEMENT);
			JOptionPane.showMessageDialog(null, "L'appartement ainsi que son garage ont été ajoutés !", "Succès",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "L'appartement a été ajouté !", "Succès",
					JOptionPane.INFORMATION_MESSAGE);
		}
		refreshPage(e);
	}

	private void handleMaisonCreation(ActionEvent e) throws DAOException {
		if(isGarageLinkedToSameFiscalNumber()){
			return;
		}
		Logement logement = new Logement(
				(Integer) page_nouveau_bien_immobilier.getChoix_nb_piece().getValue(),
				((Double) page_nouveau_bien_immobilier.getChoix_surface().getValue()),
				page_nouveau_bien_immobilier.getChoix_num_fiscal().getText(),
				(String) page_nouveau_bien_immobilier.getChoix_ville().getSelectedItem(),
				(String) page_nouveau_bien_immobilier.getChoix_adresse().getSelectedItem(),
				page_nouveau_bien_immobilier.getChoix_complement_adresse().getText(),
				page_nouveau_bien_immobilier.getListe_diagnostic(),
				TypeLogement.MAISON);
		LogementDAO logement_DAO = new LogementDAO();
		logement_DAO.create(logement,TypeLogement.MAISON);
		addDiagnostics(logement.getNumeroFiscal());
		if (!(page_nouveau_bien_immobilier.getGarageLie()).equals(new Garage("            ", "", "", "", TypeLogement.NONE))) {
			logement_DAO.lierUnGarageAuBienLouable(logement, page_nouveau_bien_immobilier.getGarageLie(), TypeLogement.MAISON);
			JOptionPane.showMessageDialog(null, "La maison ainsi que son garage ont été ajoutés !", "Succès",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "La maison a été ajouté !", "Succès",
					JOptionPane.INFORMATION_MESSAGE);
		}
		refreshPage(e);
	}

	private void handleBatimentCreation(ActionEvent e) throws Exception {
		Batiment batiment = new Batiment(
				page_nouveau_bien_immobilier.getChoix_num_fiscal().getText(),
				page_nouveau_bien_immobilier.getTexte_ville().getText(),
				page_nouveau_bien_immobilier.getTexte_adresse().getText(),
				page_nouveau_bien_immobilier.getTexte_code_postal().getText());
		BatimentDAO batimentDAO = new BatimentDAO();
		batimentDAO.create(batiment);
		JOptionPane.showMessageDialog(null, "Le Bâtiment a été ajouté !", "Succès", JOptionPane.INFORMATION_MESSAGE);
		refreshPage(e);
	}

	private void handleGarageCreation(ActionEvent e) throws Exception {
		Garage garage = new Garage(
				page_nouveau_bien_immobilier.getChoix_num_fiscal().getText(),
				(String) page_nouveau_bien_immobilier.getChoix_ville().getSelectedItem(),
				(String) page_nouveau_bien_immobilier.getChoix_adresse().getSelectedItem(),
				page_nouveau_bien_immobilier.getChoix_complement_adresse().getText(),
				TypeLogement.GARAGE_PAS_ASSOCIE);
		GarageDAO garage_DAO = new GarageDAO();
		garage_DAO.create(garage);
		JOptionPane.showMessageDialog(null, "Le Garage a été crée !", "Succès",
				JOptionPane.INFORMATION_MESSAGE);
		refreshPage(e);
	}

	private void refreshPage(ActionEvent e) {
		JFrame ancienne_fenetre = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
		ancienne_fenetre.dispose();
		int x=ancienne_fenetre.getX();
		int y=ancienne_fenetre.getY();

		PageNouveauBienImmobilier nouvelle_page = new PageNouveauBienImmobilier(x,y);
		nouvelle_page.getFrame().setVisible(true);
	}

	public DocumentListener getTextFieldDocumentListener() {
		return new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {

				checkFields();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				checkFields();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				checkFields();
			}
		};
	}

	public ActionListener getChoixTypeBienListener() {
		return e -> {
			String type_choisi = (String) this.page_nouveau_bien_immobilier.getChoix_type_de_bien().getSelectedItem();

			boolean est_apt = "Appartement".equals(type_choisi) || "Maison".equals(type_choisi);
			boolean est_bat = "Bâtiment".equals(type_choisi);

			// Gérer la visibilité des composants
			this.page_nouveau_bien_immobilier.getDiagnostics().setVisible(est_apt);
			this.page_nouveau_bien_immobilier.getTableau_diagnostic().setVisible(est_apt);
			this.page_nouveau_bien_immobilier.getSurface().setVisible(est_apt);
			this.page_nouveau_bien_immobilier.getChoix_surface().setVisible(est_apt);
			this.page_nouveau_bien_immobilier.getNombre_piece().setVisible(est_apt);
			this.page_nouveau_bien_immobilier.getChoix_nb_piece().setVisible(est_apt);
			this.page_nouveau_bien_immobilier.getComplement_adresse().setVisible(!est_bat);
			this.page_nouveau_bien_immobilier.getChoix_complement_adresse().setVisible(!est_bat);
			this.page_nouveau_bien_immobilier.getAddGarageButton().setVisible(est_apt);

			// Remplacer les JComboBox par JTextField pour "Bâtiment"
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.insets = new Insets(0, 0, 5, 0);

			// Ville
			if (est_bat) {
				this.page_nouveau_bien_immobilier.getPanel_caracteristique()
						.remove(this.page_nouveau_bien_immobilier.getChoix_ville());
				gbc.gridx = 1;
				gbc.gridy = 2;
				this.page_nouveau_bien_immobilier.getPanel_caracteristique()
						.add(this.page_nouveau_bien_immobilier.getTexte_ville(), gbc);
			} else {
				this.page_nouveau_bien_immobilier.getPanel_caracteristique()
						.remove(this.page_nouveau_bien_immobilier.getTexte_ville());
				gbc.gridx = 1;
				gbc.gridy = 2;
				this.page_nouveau_bien_immobilier.getPanel_caracteristique()
						.add(this.page_nouveau_bien_immobilier.getChoix_ville(), gbc);
			}

			// Adresse
			if (est_bat) {
				this.page_nouveau_bien_immobilier.getPanel_caracteristique()
						.remove(this.page_nouveau_bien_immobilier.getChoix_adresse());
				gbc.gridx = 1;
				gbc.gridy = 3;
				this.page_nouveau_bien_immobilier.getPanel_caracteristique()
						.add(this.page_nouveau_bien_immobilier.getTexte_adresse(), gbc);
			} else {
				this.page_nouveau_bien_immobilier.getPanel_caracteristique()
						.remove(this.page_nouveau_bien_immobilier.getTexte_adresse());
				gbc.gridx = 1;
				gbc.gridy = 3;
				this.page_nouveau_bien_immobilier.getPanel_caracteristique()
						.add(this.page_nouveau_bien_immobilier.getChoix_adresse(), gbc);
			}

			// Code Postal
			if (est_bat) {
				gbc.gridx = 1;
				gbc.gridy = 4;
				this.page_nouveau_bien_immobilier.getPanel_caracteristique()
						.add(this.page_nouveau_bien_immobilier.getTexte_code_postal(), gbc);
			} else {
				this.page_nouveau_bien_immobilier.getPanel_caracteristique()
						.remove(this.page_nouveau_bien_immobilier.getTexte_code_postal());
			}

			if (est_bat) {
				gbc.gridx = 0;
				gbc.gridy = 4;
				this.page_nouveau_bien_immobilier.getPanel_caracteristique()
						.add(this.page_nouveau_bien_immobilier.getCode_postalLabel(), gbc);
			} else {
				this.page_nouveau_bien_immobilier.getPanel_caracteristique()
						.remove(this.page_nouveau_bien_immobilier.getCode_postalLabel());
			}

			// Rafraîchir l'interface
			this.page_nouveau_bien_immobilier.getPanel_caracteristique().revalidate();
			this.page_nouveau_bien_immobilier.getPanel_caracteristique().repaint();
		};
	}

	public ActionListener getTelechargerPDFButton(String diagnostic) {
		return e -> {
			// Créer un JFileChooser pour permettre de sélectionner un fichier
			JFileChooser choix_fichier = new JFileChooser();
			choix_fichier.setDialogTitle("Sélectionnez un fichier à associer au diagnostic");
			Date date = null;
			// Ouvrir le dialogue de sélection de fichier
			int valeur_retour = choix_fichier.showOpenDialog(null);

			if (valeur_retour == JFileChooser.APPROVE_OPTION) {
				// Obtenir le fichier sélectionné
				File selectedFile = choix_fichier.getSelectedFile();
				try {
					if (diagnostic == NomsDiags.PERFORMANCE_ENERGETIQUE.getDescription()
							|| diagnostic == NomsDiags.ELECTRICITE.getDescription()
							|| diagnostic == NomsDiags.GAZ.getDescription()) {
						date = setDateDiag();
					}
					// Ajouter le diagnostic à la map
					NomsDiags diag = NomsDiags.fromDescription(diagnostic);
					this.page_nouveau_bien_immobilier.getMap_diagnostic().put(diag.name(),
							new Diagnostic(diagnostic, selectedFile.getAbsolutePath(), date));
					if (isMapDiagnosticFull()) {
						checkFields();
					}

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				JButton btn = (JButton) e.getSource();
				if (!btn.getText().contains("\u2705")) {
					btn.setText(btn.getText() + " \u2705");
				}
			}
		};
	}

	public boolean isMapDiagnosticFull() {
		for (Map.Entry<String, Diagnostic> entry : this.page_nouveau_bien_immobilier.getMap_diagnostic().entrySet()) {
			if (!entry.getKey().equals("GAZ") && entry.getValue() == null) {
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

		JDateChooser champ_seuil = new JDateChooser();
		champ_seuil.setPreferredSize(new Dimension(100, 22));
		champ_seuil.setBounds(220, 30, 100, 25);

		dialog.add(champ_seuil);

		JButton bouton_valider = new JButton("Valider");
		bouton_valider.setBounds(150, 100, 100, 30);
		dialog.add(bouton_valider);

		bouton_valider.addActionListener(event -> {
			try {
				java.sql.Date date_SQL = new java.sql.Date(champ_seuil.getDate().getTime());
				date.set(date_SQL);
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
			page_nouveau_bien_immobilier.getFrame().dispose();
			int x=page_nouveau_bien_immobilier.getFrame().getX();
			int y=page_nouveau_bien_immobilier.getFrame().getY();

			new PageMesBiens(x,y);
		};
	}
	public void checkFields() {
		// Vérifier le type de bien sélectionné
		String selectedType = (String) page_nouveau_bien_immobilier.getChoix_type_de_bien().getSelectedItem();

		// Définir les critères de validation en fonction du type sélectionné
		boolean isFilled;

		if ("Bâtiment".equals(selectedType)) {
			// Critères pour "Bâtiment" : vérifier que texte_ville et texte_adresse sont
			// remplis
			isFilled = !page_nouveau_bien_immobilier.getTexte_ville().getText().trim().isEmpty()
					&& !page_nouveau_bien_immobilier.getTexte_adresse().getText().trim().isEmpty();
		} else if ("Appartement".equals(selectedType) || "Maison".equals(selectedType)) {
			// Critères pour les autres types de bien : vérifier choix_complement_adresse et
			// choix_num_fiscal
			isFilled = !page_nouveau_bien_immobilier.getChoix_complement_adresse().getText().trim().isEmpty()
					&& !page_nouveau_bien_immobilier.getChoix_num_fiscal().getText().trim().isEmpty() && isMapDiagnosticFull();
		} else {
			isFilled = !page_nouveau_bien_immobilier.getChoix_complement_adresse().getText().trim().isEmpty()
					&& !page_nouveau_bien_immobilier.getChoix_num_fiscal().getText().trim().isEmpty();
		}

		// Active ou désactive le bouton "Valider"
		page_nouveau_bien_immobilier.getValider().setEnabled(isFilled);
	}

	private boolean isGarageLinkedToSameFiscalNumber() {
		if (page_nouveau_bien_immobilier.getGarageLie().getNumeroFiscal().equals(page_nouveau_bien_immobilier.getChoix_num_fiscal().getText())) {
			JOptionPane.showMessageDialog(null, "Un garage ne peut pas être lié avec le même numéro fiscal qu'un bien louable", "Erreur",
					JOptionPane.ERROR_MESSAGE);
			return true;
		}
		return false;
	}

	public void showGaragePopup() {
		PopUpCreationGarageLieBL popup = new PopUpCreationGarageLieBL(page_nouveau_bien_immobilier);
		popup.getFrame().setVisible(true);
	}

	public void initialiseMapDiagnostic() {
		for (NomsDiags diag : NomsDiags.values()) {
			page_nouveau_bien_immobilier.getMap_diagnostic().put(diag.name(), null);
		}
	}

	public WindowListener fermerFenetre(){
		return new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// Action to perform on application close
				performCloseAction();
			}
		};
	}
	private void performCloseAction() {
		ConnectionDB.destroy(); // fermeture de la connection
		page_nouveau_bien_immobilier.getFrame().dispose();
	}
}
