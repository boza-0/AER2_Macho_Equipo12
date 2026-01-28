
/* Equipo 12: Juan Luis Gil de Miguel, Ricardo Boza Villar */
public enum Provincia {

    /**
     * Provincia de Almería (código 1).
     */
    ALMERIA((byte) 1, "Almería"),
    /**
     * Provincia de Cádiz (código 2).
     */
    CADIZ((byte) 2, "Cádiz"),
    /**
     * Provincia de Córdoba (código 3).
     */
    CORDOBA((byte) 3, "Córdoba"),
    /**
     * Provincia de Granada (código 4).
     */
    GRANADA((byte) 4, "Granada"),
    /**
     * Provincia de Huelva (código 5).
     */
    HUELVA((byte) 5, "Huelva"),
    /**
     * Provincia de Jaén (código 6).
     */
    JAEN((byte) 6, "Jaén"),
    /**
     * Provincia de Málaga (código 7).
     */
    MALAGA((byte) 7, "Málaga"),
    /**
     * Provincia de Sevilla (código 8).
     */
    SEVILLA((byte) 8, "Sevilla");
    /**
     * Código numérico de la provincia.
     */
    private final byte codigo;
    /**
     * Nombre legible de la provincia.
     */
    private final String nombre;

    /**
     * Constructor privado de la enumeración.
     *
     * @param codigo código numérico de la provincia
     * @param nombre nombre legible de la provincia
     */
    Provincia(byte codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    /**
     * Devuelve el código numérico de la provincia.
     *
     * @return código de la provincia
     */
    public byte getCodigo() {
        return codigo;
    }

    /**
     * Devuelve el nombre legible de la provincia.
     *
     * @return nombre de la provincia
     */
    @Override
    public String toString() {
        return nombre;
    }

    /**
     * Obtiene la provincia correspondiente a un código numérico.
     *
     * @param codigo código de la provincia
     * @return la provincia correspondiente
     * @throws IllegalArgumentException si el código no corresponde a ninguna
     * provincia
     */
    public static Provincia fromCodigo(byte codigo) {
        for (Provincia p : values()) {
            if (p.codigo == codigo) {
                return p;
            }
        }
        throw new IllegalArgumentException("Código de provincia inválido: " + codigo);
    }

    public static Provincia fromInput(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Provincia inválida");
        }

        String s = normalizar(input);

        switch (s) {

            // ALMERÍA
            case "AL":
            case "ALM":
            case "ALME":
            case "ALMER":
            case "ALMERI":
            case "ALMERIA":
                return ALMERIA;

            // CÁDIZ
            case "CA":
            case "CAD":
            case "CADI":
            case "CADIZ":
                return CADIZ;

            // CÓRDOBA
            case "CO":
            case "COR":
            case "CORD":
            case "CORDO":
            case "CORDOB":
            case "CORDOBA":
                return CORDOBA;

            // GRANADA
            case "GR":
            case "GRA":
            case "GRAN":
            case "GRANA":
            case "GRANAD":
            case "GRANADA":
                return GRANADA;

            // HUELVA
            case "HU":
            case "HUE":
            case "HUEL":
            case "HUELV":
            case "HUELVA":
                return HUELVA;

            // JAÉN
            case "JA":
            case "JAE":
            case "JAEN":
                return JAEN;

            // MÁLAGA
            case "MA":
            case "MAL":
            case "MALA":
            case "MALAG":
            case "MALAGA":
                return MALAGA;

            // SEVILLA
            case "SE":
            case "SEV":
            case "SEVI":
            case "SEVIL":
            case "SEVILL":
            case "SEVILLA":
                return SEVILLA;

            default:
                throw new IllegalArgumentException("Provincia inválida: " + input);
        }
    }

    private static String normalizar(String input) {
        String s = input.trim().toUpperCase();
        s = s.replace("Á", "A").replace("É", "E")
                .replace("Í", "I").replace("Ó", "O")
                .replace("Ú", "U");
        return s.replaceAll("\\s+", " ");
    }
}
