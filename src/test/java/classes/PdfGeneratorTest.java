package classes;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class PdfGeneratorTest {

    @Test
    public void testGenerateChargesPdf() {
        String filePath = "test_charges.pdf";
        String senderName = "John Doe";
        String senderAddress = "123 Main St, Paris";
        String senderPhone = "0606060606";
        String recipientName = "Jane Smith";
        String recipientAddress = "456 Elm St, Lyon";
        String currentDate = "2023-10-01";
        String periodStart = "2023-01-01";
        String periodEnd = "2023-12-31";
        double waterCharge = 50.0;
        double wasteCharge = 30.0;
        double maintenanceCharge = 20.0;
        double lightingCharge = 10.0;
        double provisions = 100.0;
        String genre = "F";

        PdfGenerator.generateChargesPdf(filePath, senderName, senderAddress, senderPhone, recipientName, recipientAddress, currentDate, periodStart, periodEnd, waterCharge, wasteCharge, maintenanceCharge, lightingCharge, provisions, genre);

        File pdfFile = new File(filePath);
        assertTrue(pdfFile.exists());

        // Clean up the generated file
        pdfFile.delete();
    }

    @Test
    public void testGenerateChargesPdfWithGenreH() {
        String filePath = "test_charges_H.pdf";
        String senderName = "John Doe";
        String senderAddress = "123 Main St, Paris";
        String senderPhone = "0606060606";
        String recipientName = "Jane Smith";
        String recipientAddress = "456 Elm St, Lyon";
        String currentDate = "2023-10-01";
        String periodStart = "2023-01-01";
        String periodEnd = "2023-12-31";
        double waterCharge = 50.0;
        double wasteCharge = 30.0;
        double maintenanceCharge = 20.0;
        double lightingCharge = 10.0;
        double provisions = 100.0;
        String genre = "H";

        PdfGenerator.generateChargesPdf(filePath, senderName, senderAddress, senderPhone, recipientName, recipientAddress, currentDate, periodStart, periodEnd, waterCharge, wasteCharge, maintenanceCharge, lightingCharge, provisions, genre);

        File pdfFile = new File(filePath);
        assertTrue(pdfFile.exists());

        // Clean up the generated file
        pdfFile.delete();
    }

    @Test
    public void testGenerateChargesPdfWithGenreC() {
        String filePath = "test_charges_C.pdf";
        String senderName = "John Doe";
        String senderAddress = "123 Main St, Paris";
        String senderPhone = "0606060606";
        String recipientName = "Jane Smith";
        String recipientAddress = "456 Elm St, Lyon";
        String currentDate = "2023-10-01";
        String periodStart = "2023-01-01";
        String periodEnd = "2023-12-31";
        double waterCharge = 50.0;
        double wasteCharge = 30.0;
        double maintenanceCharge = 20.0;
        double lightingCharge = 10.0;
        double provisions = 100.0;
        String genre = "C";

        PdfGenerator.generateChargesPdf(filePath, senderName, senderAddress, senderPhone, recipientName, recipientAddress, currentDate, periodStart, periodEnd, waterCharge, wasteCharge, maintenanceCharge, lightingCharge, provisions, genre);

        File pdfFile = new File(filePath);
        assertTrue(pdfFile.exists());

        // Clean up the generated file
        pdfFile.delete();
    }

    @Test
    public void testOuvrirPdf() throws IOException {
        String filePath = "test_open.pdf";
        String senderName = "John Doe";
        String senderAddress = "123 Main St, Paris";
        String senderPhone = "0606060606";
        String recipientName = "Jane Smith";
        String recipientAddress = "456 Elm St, Lyon";
        String currentDate = "2023-10-01";
        String periodStart = "2023-01-01";
        String periodEnd = "2023-12-31";
        double waterCharge = 50.0;
        double wasteCharge = 30.0;
        double maintenanceCharge = 20.0;
        double lightingCharge = 10.0;
        double provisions = 100.0;
        String genre = "F";

        PdfGenerator.generateChargesPdf(filePath, senderName, senderAddress, senderPhone, recipientName, recipientAddress, currentDate, periodStart, periodEnd, waterCharge, wasteCharge, maintenanceCharge, lightingCharge, provisions, genre);

        File pdfFile = new File(filePath);
        assertTrue(pdfFile.exists());

        // Test opening the PDF
        PdfGenerator.ouvrirPdf(filePath);

        // Clean up the generated file
        pdfFile.delete();
    }

    @Test
    public void testGenerateChargesPdfWithNegativeResregu() {
        String filePath = "test_charges_negative_resregu.pdf";
        String senderName = "John Doe";
        String senderAddress = "123 Main St, Paris";
        String senderPhone = "0606060606";
        String recipientName = "Jane Smith";
        String recipientAddress = "456 Elm St, Lyon";
        String currentDate = "2023-10-01";
        String periodStart = "2023-01-01";
        String periodEnd = "2023-12-31";
        double waterCharge = 20.0;
        double wasteCharge = 10.0;
        double maintenanceCharge = 5.0;
        double lightingCharge = 5.0;
        double provisions = 100.0;
        String genre = "F";

        PdfGenerator.generateChargesPdf(filePath, senderName, senderAddress, senderPhone, recipientName, recipientAddress, currentDate, periodStart, periodEnd, waterCharge, wasteCharge, maintenanceCharge, lightingCharge, provisions, genre);

        File pdfFile = new File(filePath);
        assertTrue(pdfFile.exists());

        // Clean up the generated file
        pdfFile.delete();
    }

}