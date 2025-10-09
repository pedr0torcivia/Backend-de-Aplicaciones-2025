package blockbuster.entities;

public enum Clasificacion {
    ATP,
    INFANTIL_7,
    ADOLESCENTES_13,
    ADOLESCENTES_16,
    ADULTOS_18;

    public static Clasificacion fromRaw(String raw) {
        if (raw == null || raw.trim().isEmpty()) return null;

        String normalized = raw.trim().toUpperCase().replace(' ', '_').replace('-', '_');
        try {
            return Clasificacion.valueOf(normalized);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}

