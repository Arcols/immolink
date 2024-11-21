package projet.classes;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import projet.classes.BienLouable.TypeLogement;

public class BienLouable extends BienImmobilier {
    
    private String numero_fiscal;
    private String complement_adresse;
    public List<Devis> travaux;
    public List<Diagnostic> diagnostic;
    private String adresse;
    private String ville;
    
    public BienLouable(String numero_fiscal, String ville,String adresse, String complement_adresse
    			, List<Diagnostic> diagnostic)throws IllegalArgumentException {
        if(numero_fiscal.length()!=12){
            throw new IllegalArgumentException("Numéro fiscal invalide");
        }
        this.numero_fiscal = numero_fiscal;
        this.complement_adresse = complement_adresse;
        this.diagnostic = diagnostic;
        this.adresse=adresse;
        this.ville=ville;
        this.travaux = new ArrayList<Devis> ();
    }
    
    public String getNumero_fiscal() {
        return this.numero_fiscal;
    }
    
    public String getComplement_adresse() {
        return this.complement_adresse;
    }

    public String getVille() {
        return this.ville;
    }

    public String getAdresse() {
    	return this.adresse;
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
    
    protected int foundIDBatInDB(ConnectionDB db, String ville, String adresse) throws SQLException {
        String query_id_batiment = "SELECT id FROM batiment WHERE adresse = ? AND ville = ?";
        PreparedStatement pstmt_id_batiment = null;
        ResultSet rs = null;
        pstmt_id_batiment = db.getConnection().prepareStatement(query_id_batiment);
        pstmt_id_batiment.setString(1, adresse); // Utilisation des paramètres passés
        pstmt_id_batiment.setString(2, ville);
        rs = pstmt_id_batiment.executeQuery();
        if (rs.next()) { // Vérifie s'il y a un résultat
            return rs.getInt("id");
        } else {
            throw new SQLException("Pas de données pour ce couple ville adresse (bizarre)");
        }
    }

    
    public enum TypeLogement {
        APPARTEMENT(0),
        BATIMENT(1),
        GARAGE(2);
    
        public static final int APPARTEMENT_VALUE = 0;
        public static final int BATIMENT_VALUE = 1;
        public static final int GARAGE_VALUE = 2;
    
        private final int value;
    
        TypeLogement(int value) {
            this.value = value;
        }
    
        public int getValue() {
            return value;
        }
    }

}
