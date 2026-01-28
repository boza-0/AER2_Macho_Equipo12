
/* Equipo 12: Juan Luis Gil de Miguel, Ricardo Boza Villar */
public enum TipoEmpleado {

    /**
     * Empleado a comisión (código 'C').
     */
    COMISION('C', "A comisión"),
    /**
     * Empleado fijo (código 'F').
     */
    FIJO('F', "Fijo"),
    /**
     * Empleado a domicilio (código 'D').
     */
    DOMICILIO('D', "A domicilio");
    /**
     * Código asociado al tipo de empleado.
     */
    private final char codigo;
    /**
     * Descripción legible del tipo de empleado.
     */
    private final String descripcion;

    /**
     * Constructor privado de la enumeración.
     *
     * @param codigo código asociado al tipo de empleado
     * @param descripcion descripción legible del tipo de empleado
     */
    TipoEmpleado(char codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    /**
     * Devuelve el código asociado al tipo de empleado.
     *
     * @return código del tipo de empleado
     */
    public char getCodigo() {
        return codigo;
    }

    /**
     * Devuelve la descripción legible del tipo de empleado.
     *
     * @return descripción del tipo de empleado
     */
    @Override
    public String toString() {
        return descripcion;
    }

    /**
     * Obtiene el tipo de empleado correspondiente a un código.
     *
     * @param codigo código del tipo de empleado
     * @return el tipo de empleado correspondiente
     * @throws IllegalArgumentException si el código no corresponde a ningún
     * tipo válido
     */
    public static TipoEmpleado fromCodigo(char codigo) {
        for (TipoEmpleado t : values()) {
            if (t.codigo == codigo) {
                return t;
            }
        }
        throw new IllegalArgumentException("Código de tipo inválido: " + codigo);
    }

    public static TipoEmpleado fromInput(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Tipo inválido");
        }

        String s = normalizar(input);

        switch (s) {

            // COMISIÓN
            case "C":
            case "CO":
            case "COM":
            case "COMI":
            case "COMIS":
            case "COMISI":
            case "COMISION":
            case "A COMISION":
            case "COMISIONES":
                return TipoEmpleado.COMISION;

            // FIJO
            case "F":
            case "FI":
            case "FIJ":
            case "FIJO":
            case "CONTRATO":
            case "CONTRATO FIJO":
                return TipoEmpleado.FIJO;

            // DOMICILIO
            case "D":
            case "DO":
            case "DOM":
            case "DOMI":
            case "DOMIC":
            case "DOMICI":
            case "DOMICIL":
            case "DOMICILI":
            case "DOMICILIO":
            case "A DOMICILIO":
                return TipoEmpleado.DOMICILIO;

            default:
                throw new IllegalArgumentException("Tipo inválido: " + input);
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
