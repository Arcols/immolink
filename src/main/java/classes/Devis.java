package classes;

import java.sql.Date;

public class Devis {

	private String num_devis;
	private String num_facture;
	private float montant_devis;
	private float montant_travaux;
	private String nature;
	private Date date_debut;
	private Date date_facture;
	private String type;
	private String adresse_entreprise;
	private String nom_entreprise;

	/**
	 * Constructeur de la classe Devis
	 * @param num_devis
	 * @param num_facture
	 * @param montant_devis
	 * @param nature
	 * @param montant_travaux
	 * @param date_debut
	 * @param date_facture
	 * @param type
	 * @param adresse_entreprise
	 * @param nom_entreprise
	 */
	public Devis(String num_devis,String num_facture, float montant_devis, String nature,
				 float montant_travaux, Date date_debut, Date date_facture,
				 String type, String adresse_entreprise, String nom_entreprise) {
		this.nature = nature;
		this.num_devis = num_devis;
		this.num_facture=num_facture;
		this.montant_devis = montant_devis;
		this.montant_travaux = montant_travaux;
		this.date_debut = date_debut;
		this.date_facture = date_facture;
		this.type = type;
		this.adresse_entreprise = adresse_entreprise;
		this.nom_entreprise = nom_entreprise;
	}

	public String getNumDevis() {
		return this.num_devis;
	}

	public String getNumFacture() {
		return this.num_facture;
	}

	public String getNature() {
		return this.nature;
	}

	public float getMontantDevis() {
		return this.montant_devis;
	}

	public float getMontantTravaux() {
		return this.montant_travaux;
	}

	public Date getDateDebut() {
		return this.date_debut;
	}

	public Date getDateFacture() {
		return this.date_facture;
	}

	public String getType() {
		return this.type;
	}

	public String getAdresseEntreprise() {
		return this.adresse_entreprise;
	}

	public String getNomEntreprise() {
		return this.nom_entreprise;
	}
}
