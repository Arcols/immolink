package enumeration;

public enum NomsDiags {
    SURFACE_HABITABLE("Certificat de surface habitable"),
    PERFORMANCE_ENERGETIQUE("Diagnostique de performance énergétique"),
    AMIANTE("Dossier amiante parties privatives"),
    PLOMB("Constat de risque d'exposition au plomb avant location"),
    RISQUES_POLLUTIONS_NUISANCES("État des risques, pollutions et des nuisances sonores aériennes"),
    ELECTRICITE("Diagnostique de l'état de l'installation d'électricité"),
    GAZ("Diagnostique de l'état de l'installation de gaz"),;

    private final String description;

    NomsDiags(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static NomsDiags fromDescription(String description) {
        for (NomsDiags diag : NomsDiags.values()) {
            if (diag.getDescription().equals(description)) {
                return diag;
            }
        }
        throw new IllegalArgumentException("No enum constant with description " + description);
    }
}