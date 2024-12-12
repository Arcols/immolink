package ihm;

import modele.PageAccueil;
import modele.PageBaux;
import modele.PageNouveauBail;

import java.awt.event.ActionListener;

public class ModelePageBaux {

    private PageBaux pageBaux;

    public ModelePageBaux(PageBaux pageBaux) {
        this.pageBaux = pageBaux;
    }

    public ActionListener ouvrirPageNouveauBail() {
        return e -> {
            pageBaux.getFrame().dispose();
            PageNouveauBail PageNouveauBail = new PageNouveauBail();
            PageNouveauBail.main(null);
        };
    }
}
