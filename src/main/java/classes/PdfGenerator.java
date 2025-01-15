package classes;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.*;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PdfGenerator {

    /**
     * Génère un fichier PDF de régularisation des charges
     * @param filePath le chemin du fichier PDF
     * @param senderName le nom de l'expéditeur
     * @param senderAddress l'adresse de l'expéditeur
     * @param senderPhone le téléphone de l'expéditeur
     * @param recipientName le nom du destinataire
     * @param recipientAddress l'adresse du destinataire
     * @param currentDate la date actuelle
     * @param periodStart la date de début de la période
     * @param periodEnd la date de fin de la période
     * @param waterCharge le montant de la charge d'eau
     * @param wasteCharge le montant de la charge des ordures ménagères
     * @param maintenanceCharge le montant de la charge d'entretien des parties communes
     * @param lightingCharge le montant de la charge d'éclairage des parties communes
     * @param provisions le montant des provisions pour charges
     * @param genre le genre du destinataire
     */
    public static void generateChargesPdf(String filePath,
                                          String senderName, String senderAddress, String senderPhone,
                                          String recipientName, String recipientAddress,
                                          String currentDate, String periodStart, String periodEnd,
                                          double waterCharge, double wasteCharge, double maintenanceCharge,
                                          double lightingCharge, double provisions,String genre) {
        try {
            // Création du document PDF
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Titre
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            Paragraph title = new Paragraph("Régularisation des charges", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            // Informations de l'expéditeur
            document.add(new Paragraph(senderName, new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
            document.add(new Paragraph(senderAddress));
            document.add(new Paragraph("Tél : " + senderPhone));
            document.add(Chunk.NEWLINE);

            // Informations du destinataire
            document.add(new Paragraph("à"));
            document.add(new Paragraph(recipientName, new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
            document.add(new Paragraph(recipientAddress));
            document.add(Chunk.NEWLINE);

            // Date et objet
            document.add(new Paragraph("Toulouse, le " + currentDate));
            document.add(new Paragraph("Objet : Régularisation des charges", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
            document.add(Chunk.NEWLINE);

            // Corps principal
            document.add(new Paragraph("Je vous prie de bien vouloir trouver, ci-dessous, le détail des charges qui vous incombent. Ces charges portent sur une période allant du " + periodStart + " au " + periodEnd + "."));
            document.add(Chunk.NEWLINE);

            // Tableau des charges
            PdfPTable table = new PdfPTable(2); // 2 colonnes : Description et Montant
            table.setWidthPercentage(100);
            table.setSpacingBefore(10);
            table.setSpacingAfter(10);

            // En-têtes
            table.addCell(new PdfPCell(new Phrase("Description")));
            table.addCell(new PdfPCell(new Phrase("Montant (euros)")));

            // Charges
            table.addCell("Eau");
            table.addCell(String.format("%.2f", waterCharge));
            table.addCell("Ordures ménagères");
            table.addCell(String.format("%.2f", wasteCharge));
            table.addCell("Entretien des parties communes");
            table.addCell(String.format("%.2f", maintenanceCharge));
            table.addCell("Éclairage parties communes");
            table.addCell(String.format("%.2f", lightingCharge));

            // Total charges
            document.add(table);

            document.add(new Paragraph("Soit un total de : " + String.format("%.2f euros", waterCharge + wasteCharge + maintenanceCharge + lightingCharge)));
            document.add(new Paragraph("A déduire les provisions pour charges : " + String.format("%.2f euros", provisions)));
            double resregu=(waterCharge + wasteCharge + maintenanceCharge + lightingCharge)-provisions;
            if (resregu>0) {
                document.add(new Paragraph("Vous restez nous devoir : " + String.format("%.2f euros", resregu)));
            } else {
                document.add(new Paragraph("Nous vous devons : " + String.format("%.2f euros", -resregu)));
            }
            document.add(Chunk.NEWLINE);

            // Conclusion
            String politesse="";
            switch (genre) {
                case "H":
                    politesse = "Monsieur";
                    break;
                case "F":
                    politesse = "Madame";
                    break;
                case "C":
                    politesse = "Madame, Monsieur";
                    break;
            }
            document.add(new Paragraph("Je vous prie de croire, "+politesse+", à ma considération distinguée."));
            document.add(Chunk.NEWLINE);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Ouvre un fichier PDF
     * @param pdfPath
     * @throws IOException
     */
    public static void ouvrirPdf(String pdfPath) throws IOException {
        File pdfFile = new File(pdfPath);
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(pdfFile);
        } else {
            throw new IOException("Desktop is not supported");
        }
    }
}
