package classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import DAO.db.ConnectionDB;
import enumeration.TypeLogement;

public class Logement extends BienLouable {
	private int nb_piece;
	private double surface;

	// Constructeur si il n'y a pas de garage présent
	public Logement(int nb_piece, double surface, String numero_fiscal, String ville, String adresse,
			String complement_adresse, List<Diagnostic> diagnostic, Boolean haveGarage)
			throws IllegalArgumentException {
		super(numero_fiscal, ville, adresse, complement_adresse, diagnostic,null);
		if (surface < 9.0F) {
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
