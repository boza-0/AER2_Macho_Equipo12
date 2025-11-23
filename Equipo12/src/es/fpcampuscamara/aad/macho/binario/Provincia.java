package es.fpcampuscamara.aad.macho.binario;

/* Equipo 12: Juan Luis Gil de Miguel, Ricardo Boza Villar */

/**
 * Enumeración de las provincias de Andalucía utilizadas en la aplicación.
 * <p>
 * Cada constante representa una provincia con:
 * <ul>
 *   <li>Un código numérico único ({@code byte}) para almacenamiento en fichero binario.</li>
 *   <li>Un nombre legible para mostrar en pantalla.</li>
 * </ul>
 * </p>
 *
 * <p>Uso principal:</p>
 * <ul>
 *   <li>Identificar la provincia de destino de un {@link Empleado}.</li>
 *   <li>Persistir y reconstruir empleados en el fichero binario mediante {@link EmpleadoDAO}.</li>
 *   <li>Permitir búsquedas flexibles a través de alias en los métodos de parseo.</li>
 * </ul>
 *
 * <p>Ejemplo de uso:</p>
 * <pre>
 *     Provincia p = Provincia.JAEN;
 *     System.out.println(p.getCodigo()); // 6
 *     System.out.println(p);             // "Jaén"
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
public enum Provincia {

    /** Provincia de Almería (código 1). */
    ALMERIA((byte)1, "Almería"),

    /** Provincia de Cádiz (código 2). */
    CADIZ((byte)2, "Cádiz"),

    /** Provincia de Córdoba (código 3). */
    CORDOBA((byte)3, "Córdoba"),

    /** Provincia de Granada (código 4). */
    GRANADA((byte)4, "Granada"),

    /** Provincia de Huelva (código 5). */
    HUELVA((byte)5, "Huelva"),

    /** Provincia de Jaén (código 6). */
    JAEN((byte)6, "Jaén"),

    /** Provincia de Málaga (código 7). */
    MALAGA((byte)7, "Málaga"),

    /** Provincia de Sevilla (código 8). */
    SEVILLA((byte)8, "Sevilla");

    /** Código numérico de la provincia. */
    private final byte codigo;

    /** Nombre legible de la provincia. */
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
    public byte getCodigo() { return codigo; }

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
     * @throws IllegalArgumentException si el código no corresponde a ninguna provincia
     */
    public static Provincia fromCodigo(byte codigo) {
        for (Provincia p : values()) {
            if (p.codigo == codigo) return p;
        }
        throw new IllegalArgumentException("Código de provincia inválido: " + codigo);
    }
}
