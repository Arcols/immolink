package enumeration;

public enum TypeLogement {
	APPARTEMENT(0),MAISON(1),GARAGE_PAS_ASSOCIE(2),GARAGE_ASSOCIE(3), BATIMENT(4),NONE(-1); ;
	// je veux que avec APPARTEMENT on puisse accéder à "Appartement" et avec MAISON à "Maison" etc...

	private final int value;

	TypeLogement(int value) {
		this.value = value;
	}

	public static TypeLogement fromInt(int typeLogement) {
		switch (typeLogement) {
			case 0:
				return APPARTEMENT;
			case 1:
				return MAISON;
			case 2:
				return GARAGE_PAS_ASSOCIE;
			case 3:
				return GARAGE_ASSOCIE;
			case 4:
				return BATIMENT;
			default:
				throw new IllegalArgumentException("Unknown type: " + typeLogement);
		}
	}

	public int getValue() {
		return this.value;
	}

	public boolean estBienLouable() {
		return this == APPARTEMENT || this == MAISON  || this == GARAGE_PAS_ASSOCIE;
	}

	public boolean estLogement() {
		return this == APPARTEMENT || this == MAISON;
	}

	/**
	 * Retourne le string du logement correspondant au type du logement rentré en paramètre
	 * @param typeLogement
	 * @return
	 */
	public static String getString(TypeLogement typeLogement) {
		switch (typeLogement) {
			case APPARTEMENT:
				return "Appartement";
			case MAISON:
				return "Maison";
			case GARAGE_PAS_ASSOCIE:
				return "Garage";
			case BATIMENT:
				return "Bâtiment";
			default:
				throw new IllegalArgumentException("Unknown type: " + typeLogement);
		}
	}

	/**
	 * Retourne le type de logement correspondant au string rentré en paramètre
	 * @param name
	 * @return
	 */
	public static TypeLogement fromString(String name) {
		switch (name) {
			case "Appartement":
				return APPARTEMENT;
			case "Maison":
				return MAISON;
			case "Garage":
				return GARAGE_PAS_ASSOCIE;
			case "Bâtiment":
				return BATIMENT;
			default:
				throw new IllegalArgumentException("Unknown name: " + name);
		}
	}

	public boolean egal(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != this.getClass()) {
			return false;
		}
		return this.value == ((TypeLogement) obj).value;
	}

}