package projet.ihm;

import java.awt.event.ActionEvent;

import javax.swing.DefaultComboBoxModel;

import projet.modele.PageBienImmobilier;

public class ModelePageBienImmobilier {
	private PageBienImmobilier page;

	public ModelePageBienImmobilier(PageBienImmobilier page) {
		this.page = page;
	}

	public void handleVilleSelection(ActionEvent e) {
		this.page.getChoixVille().setModel(new DefaultComboBoxModel<>(this.page.getMapVillesAdresses()
				.get(this.page.getChoixVille().getSelectedItem()).toArray(new String[0])));
	}
}
