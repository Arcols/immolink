package classes;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class Diagnostic {

    private String reference;
    private byte[] pdf_data;

    public Diagnostic(String reference, String pdf_chemin) throws IOException {
        this.reference = reference;
        this.pdf_data = loadFileAsBytes(pdf_chemin);
    }
    /**
     * In : String, chemin du pdf 
     * Out : byte[], les données du pdf 
     */
    private byte[] loadFileAsBytes(String pdf_chemin) throws IOException {
        File file = new File(pdf_chemin);
        return Files.readAllBytes(file.toPath());
    }

    public byte[] getPdfData() {
        return pdf_data;
    }

    public String getReference() {
        return reference;
    }
    
    /**
     * In : /
     * Out : /
     * La fonction sert à ouvrir le pdf enregistré dans le diagnostic depuis le bureau.
     * Elle va créer un nouveau pdf temporaire pour l'ouvrir depuis le bureau. 
     * Le pdf sera supprimé à la fin de la fonction
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

}