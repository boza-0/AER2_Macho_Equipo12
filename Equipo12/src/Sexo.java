
/* Equipo 12: Juan Luis Gil de Miguel, Ricardo Boza Villar */
public enum Sexo {

    HOMBRE('H'),
    MUJER('M');
    private final char codigo;

    Sexo(char codigo) {
        this.codigo = codigo;
    }

    public char getCodigo() {
        return codigo;
    }

    public static Sexo fromCodigo(char codigo) {
        for (Sexo s : values()) {
            if (s.codigo == codigo) {
                return s;
            }
        }
        throw new IllegalArgumentException("Sexo inválido: " + codigo);
    }

    public static Sexo fromInput(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Sexo inválido");
        }

        String s = normalizar(input);

        switch (s) {

            // MUJER / FEMENINO
            case "M":
            case "MU":
            case "MUJ":
            case "MUJE":
            case "MUJER":
            case "FE":
            case "FEM":
            case "FEME":
            case "FEMEN":
            case "FEMENI":
            case "FEMENIN":
            case "FEMENINO":
                return Sexo.MUJER;

            // HOMBRE / MASCULINO
            case "H":
            case "HO":
            case "HOM":
            case "HOMB":
            case "HOMBR":
            case "HOMBRE":
            case "MA":
            case "MAS":
            case "MASC":
            case "MASCU":
            case "MASCULI":
            case "MASCULIN":
            case "MASCULINO":
                return Sexo.HOMBRE;

            default:
                throw new IllegalArgumentException("Sexo inválido: " + input);
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
