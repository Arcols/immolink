package modele;

import ihm.PopUpLieGarageMonBien;

import java.awt.event.ActionListener;

public class ModelePopUpLieGarageMonBien {
    private PopUpLieGarageMonBien popUpLieGarageMonBien;

    public ModelePopUpLieGarageMonBien(PopUpLieGarageMonBien popUpLieGarageMonBien) {
        this.popUpLieGarageMonBien = popUpLieGarageMonBien;
    }

    public ActionListener quitterPage(Integer idBien) {
        return e -> {
            popUpLieGarageMonBien.getFrame().dispose();
        };
    }
}
