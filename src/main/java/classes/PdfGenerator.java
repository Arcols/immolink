package classes;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PdfGenerator {

    /**
     * Génère un fichier PDF de régularisation des charges
     * @param filePath le chemin du fichier PDF
     * @param nom_expediteur le nom de l'expéditeur
     * @param adresse_expediteur l'adresse de l'expéditeur
     * @param tel_expediteur le téléphone de l'expéditeur
     * @param nom_destinataire le nom du destinataire
     * @param adresse_destinataire l'adresse du destinataire
     * @param date_actuelle la date actuelle
     * @param debut_periode la date de début de la période
     * @param fin_periode la date de fin de la période
     * @param charge_eau le montant de la charge d'eau
     * @param charge_ordure le montant de la charge des ordures ménagères
     * @param charge_maintenance le montant de la charge d'entretien des parties communes
     * @param charge_electricite le montant de la charge d'éclairage des parties communes
     * @param provisions le montant des provisions pour charges
     * @param genre le genre du destinataire
     */
    public static void generateChargesPdf(String filePath,
                                          String nom_expediteur, String adresse_expediteur, String tel_expediteur,
                                          String nom_destinataire, String adresse_destinataire,
                                          String date_actuelle, String debut_periode, String fin_periode,
                                          double charge_eau, double charge_ordure, double charge_maintenance,
                                          double charge_electricite, double provisions,String genre) {
        try {
            // Création du document PDF
            Document document = new Document();
            PdfWriter.getInstance(document, Files.newOutputStream(Paths.get(filePath)));
            document.open();

            // Titre
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            Paragraph title = new Paragraph("Régularisation des charges", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            // Informations de l'expéditeur
            document.add(new Paragraph(nom_expediteur, new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
            document.add(new Paragraph(adresse_expediteur));
            document.add(new Paragraph("Tél : " + tel_expediteur));
            document.add(Chunk.NEWLINE);

            // Informations du destinataire
            document.add(new Paragraph("à"));
            document.add(new Paragraph(nom_destinataire, new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
            document.add(new Paragraph(adresse_destinataire));
            document.add(Chunk.NEWLINE);

            // Date et objet
            document.add(new Paragraph("Toulouse, le " + date_actuelle));
            document.add(new Paragraph("Objet : Régularisation des charges", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
            document.add(Chunk.NEWLINE);

            // Corps principal
            document.add(new Paragraph("Je vous prie de bien vouloir trouver, ci-dessous, le détail des charges qui vous incombent. Ces charges portent sur une période allant du " + debut_periode + " au " + fin_periode + "."));
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
            table.addCell(String.format("%.2f", charge_eau));
            table.addCell("Ordures ménagères");
            table.addCell(String.format("%.2f", charge_ordure));
            table.addCell("Entretien des parties communes");
            table.addCell(String.format("%.2f", charge_maintenance));
            table.addCell("Éclairage parties communes");
            table.addCell(String.format("%.2f", charge_electricite));

            // Total charges
            document.add(table);

            document.add(new Paragraph("Soit un total de : " + String.format("%.2f euros", charge_eau + charge_ordure + charge_maintenance + charge_electricite)));
            document.add(new Paragraph("A déduire les provisions pour charges : " + String.format("%.2f euros", provisions)));
            double resregu=(charge_eau + charge_ordure + charge_maintenance + charge_electricite)-provisions;
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
     * @param pdf_path
     * @throws IOException
     */
    public static void ouvrirPdf(String pdf_path) throws IOException {
        File pdfFile = new File(pdf_path);
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(pdfFile);
        } else {
            throw new IOException("Desktop is not supported");
        }
    }
}
