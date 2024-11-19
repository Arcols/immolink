package classes;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BienLouable extends BienImmobilier {
    
    private String numero_fiscal;
    private String complement_adresse;
    private Batiment batiment;
    public List<Devis> travaux;
    public List<Diagnostic> diagnostic;

    public BienLouable(String numero_fiscal, String complement_adresse, Batiment batiment, List<Diagnostic> diagnostic)throws IllegalArgumentException {
        if(numero_fiscal.length()!=12){
            throw new IllegalArgumentException("Numéro fiscal invalide");
        }
        this.numero_fiscal = numero_fiscal;
        this.complement_adresse = complement_adresse;
        this.batiment = batiment;
        this.diagnostic = diagnostic;
        this.travaux = new ArrayList<Devis> ();
    }
    
    public String getNumero_fiscal() {
        return this.numero_fiscal;
    }
    
    public String getComplement_adresse() {
        return this.complement_adresse;
    }

    public Batiment getBatiment() {
        return this.batiment;
    }

    public List<Devis> getTravaux() {
        return this.travaux;
    }

    public List<Diagnostic> getDiagnostic() {
        return this.diagnostic;
    }

    public void ajouterTravaux(Devis devis){
        this.travaux.add(devis);
    }
    
    // pas sur que ça serve ?
    /**
     * In : Diagnostic
     * Out : Void
     * La fonction sert à mettre à jour un diagnostic si jamais celui-ci va bientot expirer
     * @param diagnostic
     */
    public void modifierDiagnostic(Diagnostic diagnostic){
        for(Diagnostic d:this.diagnostic){
            if(d.isSameRef(diagnostic)){
                d.miseAJourDiagnostic(diagnostic);
            }
        }
    }
    
   
    
    
    public enum TypeLogement {
        AUTRES(0),
        GARAGE(1);

        private final int value;

        TypeLogement(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

}
