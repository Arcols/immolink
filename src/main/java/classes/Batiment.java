package classes;

import java.util.ArrayList;
import java.util.List;

public class Batiment extends BienImmobilier {
	private String adresse;
	private String numero_fiscal;
	private String ville;
	private String code_postal;
	public List<BienLouable> bien_louable;

	// Un batiment est initialisé sans bien louable
	public Batiment(String numero_fiscal, String ville, String adresse,String code_postal) {
		if(code_postal.length()!=5){
			throw new IllegalArgumentException("Code postal invalide");
		}
		if (numero_fiscal.length() != 12) {
			throw new IllegalArgumentException("Numéro fiscal invalide");
		}
		setNumero_fiscal(numero_fiscal);
		setVille(ville);
		setAdresse(adresse);
		setCode_postal(code_postal);
		setBien_louable(new ArrayList<>());
	}

	private void setCode_postal(String codePostal) {this.code_postal = codePostal;}

	public String getAdresse() {
		return this.adresse;
	}

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

	public void setVille(String ville) {
		this.ville = ville;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public void setNumero_fiscal(String numero_fiscal) {
		this.numero_fiscal = numero_fiscal;
	}

	public void setBien_louable(List<BienLouable> bien_louable) {
		this.bien_louable = bien_louable;
	}

	public String getCodePostal() {return this.code_postal;}
}