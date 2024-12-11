package classes;

import DAO.db.ConnectionDB;
import enumeration.TypeLogement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

	public BienLouable(String numero_fiscal, String ville, String adresse, String complement_adresse,
					   List<Diagnostic> diagnostic, Integer id_garage_associe) throws IllegalArgumentException {
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
	}

	public String getNumero_fiscal() {
		return this.numero_fiscal;
	}

	public String getComplement_adresse() {
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
	
	public TypeLogement getTypeLogement(){
		return TypeLogement.APPARTEMENT;
	}

	/**
	 * In : Diagnostic Out : Void La fonction sert à mettre à jour un diagnostic si
	 * jamais celui-ci va bientot expirer
	 * 
	 * @param diagnostic
	 */
	public void modifierDiagnostic(Diagnostic diagnostic) {
		for (Diagnostic d : this.diagnostic) {
			if (d.isSameRef(diagnostic)) {
				d.miseAJourDiagnostic(diagnostic);
			}
		}
	}

}
