package classes;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Date;

public class Diagnostic {

	private String reference;
	private String pdfPath;
	private Date dateInvalidite;

	// Constructor without dateInvalidite
	public Diagnostic(String reference, String pdfPath) throws IOException {
		this.reference = reference;
		File file = new File(pdfPath);
		if (!file.exists()) {
			throw new IOException("Invalid file path: " + pdfPath);
		}
		this.pdfPath = file.getAbsolutePath();
		this.dateInvalidite = null;
	}

	// Constructor with dateInvalidite
	public Diagnostic(String reference, String pdfPath, Date dateInvalidite) throws IOException {
		this.reference = reference;
		File file = new File(pdfPath);
		if (!file.exists()) {
			throw new IOException("Invalid file path: " + pdfPath);
		}
		this.pdfPath = file.getAbsolutePath();
		this.dateInvalidite = dateInvalidite;
	}

	public void miseAJourDiagnostic(Diagnostic diagnostic) {
		this.pdfPath = diagnostic.getPdfPath();
	}

	public boolean isSameRef(Diagnostic diagnostic) {
		return this.reference.equals(diagnostic.getReference());
	}

	public String getPdfPath() {
		return this.pdfPath;
	}

	public String getReference() {
		return this.reference;
	}

	public Date getDateInvalidite() {
		return this.dateInvalidite;
	}

	public boolean estExpire() {
		if (this.dateInvalidite == null) {
			return false; // If dateInvalidite is null, the diagnostic does not expire
		}
		Date currentDate = new Date(System.currentTimeMillis());
		return currentDate.after(this.dateInvalidite); // Returns true if the current date is after dateInvalidite
	}

	public void ouvrirPdf() throws IOException {
		File pdfFile = new File(this.pdfPath);
		if (Desktop.isDesktopSupported()) {
			Desktop.getDesktop().open(pdfFile);
		} else {
			System.out.println("Desktop opening is not supported.");
		}
	}
}