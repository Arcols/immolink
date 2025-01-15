package classes;

import enumeration.TypeLogement;

import java.util.ArrayList;
import java.util.List;

public class Batiment extends BienImmobilier {
	private String adresse;
	private String numero_fiscal;
	private String ville;
	private String code_postal;
	public List<BienLouable> bien_louable;

	/**
	 *  Constructeur de la classe Batiment
	 * @param numero_fiscal le numéro fiscal du bien
	 * @param ville la ville du bien
	 * @param adresse l'adresse du bien
	 * @param code_postal le code postal du bien
	 */
	public Batiment(String numero_fiscal, String ville, String adresse,String code_postal) {
		if(code_postal.length()!=5){
			throw new IllegalArgumentException("Code postal invalide");
		}
		if (numero_fiscal.length() != 12) {
			throw new IllegalArgumentException("Numéro fiscal invalide");
		}
		this.adresse = adresse;
		this.code_postal = code_postal;
		this.ville = ville;
		this.numero_fiscal = numero_fiscal;
		this.bien_louable = new ArrayList<BienLouable>();
	}

    public String getAdresse() {
		return this.adresse;
	}

	public String getVille() {
		return this.ville;
	}

	public String getNumeroFiscal() {
		return this.numero_fiscal;
	}

	public List<BienLouable> getBienLouable() {
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

	public void setNumeroFiscal(String numero_fiscal) {
		this.numero_fiscal = numero_fiscal;
	}

	public void setBienLouable(List<BienLouable> bien_louable) {
		this.bien_louable = bien_louable;
	}

	public String getCodePostal() {return this.code_postal;}

	/**
	 * Méthode permettant de récupérer le type de logement
	 * @return le type de logement
	 */
	public TypeLogement getTypeLogement(){
		return TypeLogement.BATIMENT;
	}

	@Override
	public String toString() {
		return "Batiment{" +
				"adresse='" + adresse + '\'' +
				", numero_fiscal='" + numero_fiscal + '\'' +
				", ville='" + ville + '\'' +
				", code_postal='" + code_postal + '\'' +
				", bien_louable=" + bien_louable +
				'}';
	}
}