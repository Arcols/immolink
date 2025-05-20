package classes;

import enumeration.TypeLogement;

import java.util.ArrayList;
import java.util.List;

public class BienLouable {

	private final String numero_fiscal;
	private final String complement_adresse;
	private final List<Devis> travaux;
	private final List<Diagnostic> diagnostic;
	private final String adresse;
	private final String ville;
	private final Integer id_garage_asosscie;
	private final TypeLogement type_logement;

	/**
	 * Constructeur de la classe BienLouable
	 * @param numero_fiscal le numero fiscal du bien
	 * @param ville la ville du bien
	 * @param adresse l'adresse du bien
	 * @param complement_adresse le complement d'adresse du bien
	 * @param diagnostic la liste des diagnostics du bien
	 * @param id_garage_associe l'identifiant du garage associe
	 * @param type_logement le type de logement
	 * @throws IllegalArgumentException si le numero fiscal n'est pas valide
	 */
	public BienLouable(String numero_fiscal, String ville, String adresse, String complement_adresse,
					   List<Diagnostic> diagnostic, Integer id_garage_associe,TypeLogement type_logement) throws IllegalArgumentException {
		if (numero_fiscal.length() != 12) {
			throw new IllegalArgumentException("Numero fiscal invalide");
		}
		this.numero_fiscal = numero_fiscal;
		this.complement_adresse = complement_adresse;
		this.diagnostic = diagnostic;
		this.adresse = adresse;
		this.ville = ville;
		this.travaux = new ArrayList<>();
		this.id_garage_asosscie = id_garage_associe;
		this.type_logement = type_logement;
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
		return type_logement;
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
