package modele;

import ihm.PageBaux;
import ihm.PageNouveauBail;

import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ModelePageBaux {

    private PageBaux pageBaux;

    public ModelePageBaux(PageBaux pageBaux) {
        this.pageBaux = pageBaux;
    }

    public ActionListener ouvrirPageNouveauBail() {
        return e -> {
            pageBaux.getFrame().dispose();
            PageNouveauBail PageNouveauBail = null;
            try {
                PageNouveauBail = new PageNouveauBail();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            PageNouveauBail.main(null);
        };
    }
}
