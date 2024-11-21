package projet.classes;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Diagnostic {

	private String reference;
	private byte[] pdf_data;
	private Date date_invalidite;

	// Constructeur si il n'y a pas de date d'invalidite présent
	public Diagnostic(String reference, String pdf_chemin) throws IOException, SQLException {
		this.reference = reference;
		this.pdf_data = loadFileAsBytes(pdf_chemin);
		this.date_invalidite = null;
		// insertIntoTable(pdf_data,reference,date_invalidite);
	}

	// Constructeur si il y a une date d'invalidite
	public Diagnostic(String reference, String pdf_chemin, Date date_invalidite) throws IOException, SQLException {
		this.reference = reference;
		this.pdf_data = loadFileAsBytes(pdf_chemin);
		this.date_invalidite = date_invalidite;
		// insertIntoTable(pdf_data,reference,date_invalidite);
	}

	/**
	 * In : Diagnostic Out : Void Remplace le pdf du diagnostic pris en paramètre
	 * Pour appliquer cette fonction il faut que les deux diagnostics aient la même
	 * référence
	 * 
	 * @param diagnostic
	 */
	public void miseAJourDiagnostic(Diagnostic diagnostic) {
		this.pdf_data = diagnostic.getPdfData();
	}

	public boolean isSameRef(Diagnostic diagnostic) {
		return this.reference.equals(diagnostic.getReference());
	}

	/**
	 * In : String, chemin du pdf Out : byte[], les données du pdf
	 */
	private byte[] loadFileAsBytes(String pdf_chemin) throws IOException {
		File file = new File(pdf_chemin);
		return Files.readAllBytes(file.toPath());
	}

	public byte[] getPdfData() {
		return this.pdf_data;
	}

	public String getReference() {
		return this.reference;
	}

	public Date getDateInvalidite() {
		return this.date_invalidite;
	}

	public boolean estExpire() {
		if (this.date_invalidite == null) {
			return false; // Si la date d'invalidité est null, le diagnostic n'a pas d'expiration
		}
		Date currentDate = new Date(System.currentTimeMillis());
		return currentDate.after(this.date_invalidite); // Renvoie true si la date actuelle est après la date
														// d'invalidité
	}

	/**
	 * In : / Out : / La fonction sert à ouvrir le pdf enregistré dans le diagnostic
	 * depuis le bureau. Elle va créer un nouveau pdf temporaire pour l'ouvrir
	 * depuis le bureau. Le pdf sera supprimé à la fin de la fonction
	 * 
	 * @throws IOException
	 */
	public void ouvrirPdf() throws IOException {
		File tempPdf = File.createTempFile("tempPdf", ".pdf");
		tempPdf.deleteOnExit(); // Le fichier sera supprimé après la fermeture du programme

		FileOutputStream fos = new FileOutputStream(tempPdf);
		fos.write(pdf_data);

		if (Desktop.isDesktopSupported()) {
			Desktop.getDesktop().open(tempPdf);
		} else {
			System.out.println("L'ouverture par le bureau n'est pas supportée.");
		}
	}

	// probablement inutile
	private void insertIntoTable(byte[] pdf_data, String reference, Date date_expiration) throws SQLException {
		ConnectionDB db = new ConnectionDB();
		String query = "INSERT INTO diagnostiques (pdf_diag, type, date_expiration) VALUES (?, ?, ?)";
		PreparedStatement pstmt = db.getConnection().prepareStatement(query);
		pstmt.setBytes(1, pdf_data);
		pstmt.setString(2, reference);
		pstmt.setDate(3, date_expiration);
		pstmt.executeUpdate();
		pstmt.close();
		db.closeConnection();
	}
}
