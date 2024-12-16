package classes;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Date;

public class Diagnostic {

	private String reference;
	private String pdfPath;
	private Date dateInvalidite;

	/**
	 * Constructor without dateInvalidite
	 * @param reference the reference of the diagnostic
	 * @param pdfPath the path to the PDF file
	 * @throws IOException if the file path is invalid
	 */
	public Diagnostic(String reference, String pdfPath) throws IOException {
		this.reference = reference;
		File file = new File(pdfPath);
		if (!file.exists()) {
			throw new IOException("Invalid file path: " + pdfPath);
		}
		this.pdfPath = file.getAbsolutePath();
		this.dateInvalidite = null;
	}

	/**
	 * Constructor with dateInvalidite
	 * @param reference the reference of the diagnostic
	 * @param pdfPath the path to the PDF file
	 * @param dateInvalidite the date of invalidity
	 * @throws IOException if the file path is invalid
	 */
	public Diagnostic(String reference, String pdfPath, Date dateInvalidite) throws IOException {
		this.reference = reference;
		File file = new File(pdfPath);
		if (!file.exists()) {
			throw new IOException("Invalid file path: " + pdfPath);
		}
		this.pdfPath = file.getAbsolutePath();
		this.dateInvalidite = dateInvalidite;
	}

	/**
	 * Updates the diagnostic with the new diagnostic
	 * @param diagnostic the new diagnostic
	 */
	public void miseAJourDiagnostic(Diagnostic diagnostic) {
		this.pdfPath = diagnostic.getPdfPath();
	}

	/**
	 * Checks if the diagnostic has the same reference as the given diagnostic
	 * @param diagnostic the diagnostic to compare
	 * @return true if the references are the same, false otherwise
	 */
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

	/**
	 * Checks if the diagnostic is expired
	 * @return true if the diagnostic is expired, false otherwise
	 */
	public boolean estExpire() {
		if (this.dateInvalidite == null) {
			return false;
		}
		Date currentDate = new Date(System.currentTimeMillis());
		return currentDate.after(this.dateInvalidite); // Returns true if the current date is after dateInvalidite
	}

	/**
	 * Opens the PDF file
	 * @throws IOException if the file does not exist
	 */
	public void ouvrirPdf() throws IOException {
		File pdfFile = new File(this.pdfPath);
		if (Desktop.isDesktopSupported()) {
			Desktop.getDesktop().open(pdfFile);
		} else {
			throw new IOException("Desktop is not supported");
		}
	}
}