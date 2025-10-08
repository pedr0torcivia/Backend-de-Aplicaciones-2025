package games.entities;

// Definición de un enum para representar las clasificaciones ESRB
public enum EsrbRating {
    E("E"),           // Everyone
    E10_PLUS("E10+"), // Everyone 10+
    T("T"),           // Teen
    M("M"),           // Mature
    AO("AO"),         // Adults Only
    RP("RP"),         // Rating Pending
    UR("UR");         // Unrated / desconocido

    private final String code; // Código asociado a cada clasificación

    // Constructor privado que asigna el código al enum
    EsrbRating(String code){ 
        this.code = code; 
    }

    // Método para obtener el código de la clasificación
    public String code(){ 
        return code; 
    }

    // Método estático para obtener el enum a partir de un string "raw"
    public static EsrbRating fromRaw(String raw){
        
        if (raw == null || raw.isBlank()) return UR; // Si el string es nulo o está vacío, retorna UR (Unrated)
        String u = raw.trim().toUpperCase();
        // Selecciona el enum correspondiente según el valor procesado
        return switch (u) {
            case "E", "EVERYONE" -> E; // Si es "E" o "EVERYONE", retorna E
            case "E10+", "EVERYONE 10+", "EVERYONE10+" -> E10_PLUS; // Variantes para E10+
            case "T", "TEEN" -> T; // "T" o "TEEN" retorna T
            case "M", "MATURE" -> M; // "M" o "MATURE" retorna M
            case "AO", "ADULTS ONLY" -> AO; // "AO" o "ADULTS ONLY" retorna AO
            case "RP", "RATING PENDING" -> RP; // "RP" o "RATING PENDING" retorna RP
            default -> UR; // Si no coincide con ningún caso, retorna UR
        };
    }
}
