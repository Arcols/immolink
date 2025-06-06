package classes;

import enumeration.TypeLogement;

import java.util.List;

public class Logement extends BienLouable {
	private final int nb_piece;
	private final double surface;

	/**
	 * Constructeur de la classe Logement
	 * @param nb_piece le nombre de pièces
	 * @param surface la surface
	 * @param numero_fiscal le numero fiscal du bien
	 * @param ville la ville du bien
	 * @param adresse l'adresse du bien
	 * @param complement_adresse le complement d'adresse du bien
	 * @param diagnostic la liste des diagnostics du bien
	 * @param typeLogement le type de logement
	 * @throws IllegalArgumentException si la surface est inferieure à 9m²
	 */
	public Logement(int nb_piece, double surface, String numero_fiscal, String ville, String adresse,
					String complement_adresse, List<Diagnostic> diagnostic, TypeLogement typeLogement)
			throws IllegalArgumentException {
		super(numero_fiscal, ville, adresse, complement_adresse, diagnostic, null,typeLogement);
		if (!(typeLogement.equals(TypeLogement.GARAGE_ASSOCIE) || typeLogement.equals(TypeLogement.GARAGE_PAS_ASSOCIE)) && surface < 9.0F) {
			throw new IllegalArgumentException(" Un logement fait au minimum 9m²");
		}
		this.nb_piece = nb_piece;
		this.surface = surface;
	}

	public int getNbPiece() {
		return this.nb_piece;
	}

	public double getSurface() {
		return this.surface;
	}

}
