package classes;

import enumeration.TypeLogement;

import java.util.ArrayList;
import java.util.List;

public class BienLouable extends BienImmobilier {

	private String numero_fiscal;
	private String complement_adresse;
	private List<Devis> travaux;
	private List<Diagnostic> diagnostic;
	private String adresse;
	private String ville;
	private Integer id_garage_asosscie;
	private TypeLogement typeLogement;

	/**
	 * Constructeur de la classe BienLouable
	 * @param numero_fiscal le numéro fiscal du bien
	 * @param ville la ville du bien
	 * @param adresse l'adresse du bien
	 * @param complement_adresse le complément d'adresse du bien
	 * @param diagnostic la liste des diagnostics du bien
	 * @param id_garage_associe l'identifiant du garage associé
	 * @param typeLogement le type de logement
	 * @throws IllegalArgumentException si le numéro fiscal n'est pas valide
	 */
	public BienLouable(String numero_fiscal, String ville, String adresse, String complement_adresse,
					   List<Diagnostic> diagnostic, Integer id_garage_associe,TypeLogement typeLogement) throws IllegalArgumentException {
		if (numero_fiscal.length() != 12) {
			throw new IllegalArgumentException("Numéro fiscal invalide");
		}
		this.numero_fiscal = numero_fiscal;
		this.complement_adresse = complement_adresse;
		this.diagnostic = diagnostic;
		this.adresse = adresse;
		this.ville = ville;
		this.travaux = new ArrayList<Devis>();
		this.id_garage_asosscie = (id_garage_associe != null) ? id_garage_associe : null;
		this.typeLogement = typeLogement;
	}

	public String getNumeroFiscal() {
		return this.numero_fiscal;
	}

	public String getComplementAdresse() {
		return this.complement_adresse;
	}

	public String getVille() {
		return this.ville;
	}

	public String getAdresse() {
		return this.adresse;
	}

	public List<Devis> getTravaux() {
		return this.travaux;
	}

	public List<Diagnostic> getDiagnostic() {
		return this.diagnostic;
	}

	public void ajouterTravaux(Devis devis) {
		this.travaux.add(devis);
	}

	public Integer getIdgarage() {
		return id_garage_asosscie;
	}

	/**
	 *  Retourne le type de logement d'un bien
	 *  @return le type de logement
	 */
	public TypeLogement getTypeLogement(){
		return typeLogement;
	}

	/**
	 *  Modifie un diagnostic à la liste des diagnostics
	 * @param diagnostic le diagnostic à modifier
	 */
	public void modifierDiagnostic(Diagnostic diagnostic) {
		for (Diagnostic d : this.diagnostic) {
			if (d.isSameRef(diagnostic)) {
				d.miseAJourDiagnostic(diagnostic);
			}
		}
	}

}
