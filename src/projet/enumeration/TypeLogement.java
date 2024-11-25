package projet.enumeration;

public enum TypeLogement {
	APPARTEMENT(0), BATIMENT(1), GARAGE(2);

	private final int value;

	TypeLogement(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}
}