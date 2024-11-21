package projet.classes;

public class Devis {
	private String num_devis;

	private float montant;

	private String nature;

	private float montant_nondeductible;

	public Devis(String num_devis, float montant, String nature, float montant_nondeductible) {
		this.num_devis = num_devis;
		this.montant = montant;
		this.nature = nature;
		this.montant_nondeductible = montant_nondeductible;
	}

	public String getNum_devis() {
		return this.num_devis;
	}

	public float getMontant() {
		return this.montant;
	}

	public String getNature() {
		return this.nature;
	}

	public float getMontantNondeductible() {
		return this.montant_nondeductible;
	}

	public float getMontantDeductible() {
		return this.montant - this.montant_nondeductible;
	}

}
