package classes;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Date;

public class Diagnostic {

	private final String reference;
	private String pdf_path;
	private final Date date_invalidite;

	/**
	 * Constructor without date_invalidite
	 * @param reference the reference of the diagnostic
	 * @param pdf_path the path to the PDF file
	 * @throws IOException if the file path is invalid
	 */
	public Diagnostic(String reference, String pdf_path) throws IOException {
		this.reference = reference;
		File file = new File(pdf_path);
		if (!file.exists()) {
			throw new IOException("Invalid file path: " + pdf_path);
		}
		this.pdf_path = file.getAbsolutePath();
		this.date_invalidite = null;
	}

	/**
	 * Constructor with date_invalidite
	 * @param reference the reference of the diagnostic
	 * @param pdf_path the path to the PDF file
	 * @param date_invalidite the date of invalidity
	 * @throws IOException if the file path is invalid
	 */
	public Diagnostic(String reference, String pdf_path, Date date_invalidite) throws IOException {
		this.reference = reference;
		File file = new File(pdf_path);
		if (!file.exists()) {
			throw new IOException("Invalid file path: " + pdf_path);
		}
		this.pdf_path = file.getAbsolutePath();
		this.date_invalidite = date_invalidite;
	}

	/**
	 * Updates the diagnostic with the new diagnostic
	 * @param diagnostic the new diagnostic
	 */
	public void miseAJourDiagnostic(Diagnostic diagnostic) {
		this.pdf_path = diagnostic.getPdfPath();
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
		return this.pdf_path;
	}

	public String getReference() {
		return this.reference;
	}

	public Date getDateInvalidite() {
		return this.date_invalidite;
	}

	/**
	 * Checks if the diagnostic is expired
	 * @return true if the diagnostic is expired, false otherwise
	 */
	public boolean estExpire() {
		if (this.date_invalidite == null) {
			return false;
		}
		Date currentDate = new Date(System.currentTimeMillis());
		return currentDate.after(this.date_invalidite); // Returns true if the current date is after date_invalidite
	}

	/**
	 * Opens the PDF file
	 * @throws IOException if the file does not exist
	 */
	public void ouvrirPdf() throws IOException {
		File pdfFile = new File(this.pdf_path);
		if (Desktop.isDesktopSupported()) {
			Desktop.getDesktop().open(pdfFile);
		} else {
			throw new IOException("Desktop is not supported");
		}
	}
}