package es.fpcampuscamara.aad.macho.binario;

/* Equipo 12: Juan Luis Gil de Miguel, Ricardo Boza Villar */

/**
 * Enumeración de los tipos de empleado reconocidos por la aplicación.
 * <p>
 * Cada constante representa una modalidad contractual o laboral con:
 * <ul>
 *   <li>Un código único ({@code char}) para almacenamiento en fichero binario.</li>
 *   <li>Una descripción legible para mostrar en pantalla.</li>
 * </ul>
 * </p>
 *
 * <p>Uso principal:</p>
 * <ul>
 *   <li>Identificar el tipo de contrato de un {@link Empleado}.</li>
 *   <li>Persistir y reconstruir empleados en el fichero binario mediante {@link EmpleadoDAO}.</li>
 *   <li>Permitir búsquedas flexibles a través de alias en los métodos de parseo.</li>
 * </ul>
 *
 * <p>Ejemplo de uso:</p>
 * <pre>
 *     TipoEmpleado t = TipoEmpleado.FIJO;
 *     System.out.println(t.getCodigo()); // 'F'
 *     System.out.println(t);             // "Fijo"
 * </pre>
 *
 * <p>
 * Esta enumeración garantiza consistencia entre la representación interna (código)
 * y la externa (nombre), evitando ambigüedades en la gestión de empleados.
 * </p>
 *
 * @author Juan Luis Gil de Miguel
 * @author Ricardo Boza Villar
 */
public enum TipoEmpleado {

    /** Empleado a comisión (código 'C'). */
    COMISION('C', "A comisión"),

    /** Empleado fijo (código 'F'). */
    FIJO('F', "Fijo"),

    /** Empleado a domicilio (código 'D'). */
    DOMICILIO('D', "A domicilio");

    /** Código asociado al tipo de empleado. */
    private final char codigo;

    /** Descripción legible del tipo de empleado. */
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
    public char getCodigo() { return codigo; }

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
     * @throws IllegalArgumentException si el código no corresponde a ningún tipo válido
     */
    public static TipoEmpleado fromCodigo(char codigo) {
        for (TipoEmpleado t : values()) {
            if (t.codigo == codigo) return t;
        }
        throw new IllegalArgumentException("Código de tipo inválido: " + codigo);
    }
}
