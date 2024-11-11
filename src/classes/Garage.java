package classes;

import java.util.List;

public class Garage extends BienLouable {

    public Garage(String numero_fiscal, String complement_adresse, Batiment batiment, List<Diagnostic> diagnostic) {
        super(numero_fiscal, complement_adresse, batiment, diagnostic);
    }
}
