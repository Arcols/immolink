package classes;

import java.util.ArrayList;
import java.util.List;


public class Batiment extends BienImmobilier {
	private String adresse;

	private String numero_fiscal;
	// private String code_postal;

	private String ville;

	public List<BienLouable> bien_louable;

	// Un batiment est initialisé sans bien louable
	public Batiment(String numero_fiscal, String ville, String adresse) {
		if (numero_fiscal.length() != 12) {
			throw new IllegalArgumentException("Numéro fiscal invalide");
		}
		this.adresse = adresse;
		// this.code_postal = code_postal;
		setAdresse(adresse);
		setBien_louable(new ArrayList<>());
		setNumero_fiscal(numero_fiscal);
		setVille(ville);
	}

	public String getAdresse() {
		return this.adresse;
	}

	// public String getCode_postal() {return this.code_postal;}

	public String getVille() {
		return this.ville;
	}

	public String getNumeroFiscal() {
		return this.numero_fiscal;
	}

	public List<BienLouable> getBien_louable() {
		return this.bien_louable;
	}

	public void ajouterBienLouable(BienLouable bien_louable) {
		this.bien_louable.add(bien_louable);
	}

	public void supprimerBienLouable(BienLouable bien_louable) {
		this.bien_louable.remove(bien_louable);
	}

	public void setVille(String lyon) {
		this.ville=ville;
	}
	public void setAdresse(String s) {
		this.adresse=adresse;
	}
	public void setNumero_fiscal(String s) {
		this.numero_fiscal=numero_fiscal;
	}
	public void setBien_louable(List<BienLouable> bien_louable) {
		this.bien_louable=bien_louable;
	}

}
