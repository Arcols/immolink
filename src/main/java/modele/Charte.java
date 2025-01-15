package modele;

import java.awt.Color;

public enum Charte {
	FOND(new Color(248, 235, 224)), ENTETE(new Color(214, 197, 184));

	private final Color couleur;

	Charte(Color couleur) {
		this.couleur = couleur;
	}

	public Color getCouleur() {
		return this.couleur;
	}
}
