package es.fpcampuscamara.aad.macho.binario;

/* Equipo 12: Juan Luis Gil de Miguel, Ricardo Boza Villar */

import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;

/**
 * Entidad que representa a un empleado de la empresa.
 * <p>
 * Modela tanto los datos personales como las condiciones laborales de un empleado,
 * incluyendo nombre, sexo, salario base, fecha de ingreso, tipo de contrato y provincia de destino.
 * </p>
 *
 * <p>Además de los atributos básicos, esta clase implementa lógica de negocio para:</p>
 * <ul>
 *   <li><b>Cálculo de antigüedad:</b> número de años transcurridos desde la fecha de ingreso.</li>
 *   <li><b>Trienios:</b> cada 3 años trabajados generan un complemento fijo de 24€.</li>
 *   <li><b>Complemento por destino:</b> empleados destinados en {@code JAEN}, {@code HUELVA} o {@code ALMERIA}
 *       reciben un 10% adicional sobre el salario base.</li>
 *   <li><b>Complemento por sexo:</b> todos los hombres ('H') reciben un complemento fijo de 120€.</li>
 *   <li><b>Sueldo total:</b> suma del salario base más los complementos anteriores.</li>
 * </ul>
 *
 * <p>Validaciones:</p>
 * <ul>
 *   <li>El nombre no puede ser nulo ni vacío, y tiene un máximo de 30 caracteres.</li>
 *   <li>El sexo debe ser 'M' (mujer) o 'H' (hombre).</li>
 *   <li>El salario base debe estar en el rango 0–99,999.99.</li>
 *   <li>La fecha de ingreso debe ser válida (año 1900–2100, mes 1–12, día 1–31).</li>
 *   <li>El tipo de empleado y la provincia no pueden ser nulos.</li>
 * </ul>
 *
 * <p>
 * La clase garantiza que no se pueden crear instancias con datos inválidos,
 * lanzando {@link IllegalArgumentException} en caso de violación de las reglas anteriores.
 * </p>
 *
 * @author Juan Luis Gil de Miguel
 * @author Ricardo Boza Villar
 */
public class Empleado {

    /** Nombre del empleado (máx. 30 caracteres). */
    private final String nombre;

    /** Sexo del empleado: 'M' (mujer) o 'H' (hombre). */
    private final char sexo;

    /** Salario base del empleado (0..99999.99). */
    private final float salarioBase;

    /** Año de entrada en la empresa (1900..2100). */
    private final short anio;

    /** Mes de entrada en la empresa (1..12). */
    private final byte mes;

    /** Día de entrada en la empresa (1..31). */
    private final byte dia;

    /** Tipo de empleado (enum {@link TipoEmpleado}). */
    private final TipoEmpleado tipo;

    /** Provincia de destino (enum {@link Provincia}). */
    private final Provincia provincia;

    /**
     * Crea un nuevo empleado con los datos especificados, validando cada campo.
     * <p>
     * Reglas de validación aplicadas:
     * <ul>
     *   <li><b>nombre</b>: no puede ser nulo ni vacío; máximo 30 caracteres.</li>
     *   <li><b>sexo</b>: debe ser 'M' (mujer) o 'H' (hombre).</li>
     *   <li><b>salarioBase</b>: debe estar en el rango 0–99,999.99.</li>
     *   <li><b>anio, mes, dia</b>: deben formar una fecha válida; se comprueba con {@code esFechaValida}.</li>
     *   <li><b>tipo</b>: no puede ser nulo; corresponde a un valor de {@link TipoEmpleado}.</li>
     *   <li><b>provincia</b>: no puede ser nulo; corresponde a un valor de {@link Provincia}.</li>
     * </ul>
     *
     * @param nombre nombre del empleado
     * @param sexo sexo del empleado ('M' o 'H')
     * @param salarioBase salario base en euros
     * @param anio año de entrada
     * @param mes mes de entrada (1–12)
     * @param dia día de entrada (1–31, validado junto con mes y año)
     * @param tipo tipo de empleado (enum {@link TipoEmpleado})
     * @param provincia provincia de destino (enum {@link Provincia})
     * @throws IllegalArgumentException si alguno de los parámetros no cumple las reglas de validación
     */
    public Empleado(String nombre, char sexo, float salarioBase,
                    short anio, byte mes, byte dia,
                    TipoEmpleado tipo, Provincia provincia) {

        // Nombre: not null/empty, max length 30
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (nombre.length() > 30) {
            throw new IllegalArgumentException("El nombre excede los 30 caracteres.");
        }
        this.nombre = nombre.trim();

        // Sexo: must be 'M' or 'H'
        if (sexo != 'M' && sexo != 'H') {
            throw new IllegalArgumentException("Sexo inválido: debe ser 'M' o 'H'.");
        }
        this.sexo = sexo;

        // Salario: 0–99999.99
        if (salarioBase < 0 || salarioBase > 99999.99f) {
            throw new IllegalArgumentException("Salario fuera de rango (0–99999.99).");
        }
        this.salarioBase = salarioBase;

        // Fecha: must be valid
        if (!esFechaValida(anio, mes, dia)) {
            throw new IllegalArgumentException("Fecha inválida: " + dia + "/" + mes + "/" + anio);
        }
        this.anio = anio;
        this.mes = mes;
        this.dia = dia;

        // TipoEmpleado: must not be null
        if (tipo == null) {
            throw new IllegalArgumentException("Tipo de empleado no puede ser nulo.");
        }
        this.tipo = tipo;

        // Provincia: must not be null
        if (provincia == null) {
            throw new IllegalArgumentException("Provincia no puede ser nula.");
        }
        this.provincia = provincia;
    }

    // -------------------
    // Getters
    // -------------------

    /** @return el nombre del empleado */
    public String getNombre() { return nombre; }

    /** @return el sexo del empleado ('M' o 'H') */
    public char getSexo() { return sexo; }

    /** @return el salario base del empleado */
    public float getSalarioBase() { return salarioBase; }

    /** @return el año de entrada del empleado */
    public short getAnio() { return anio; }

    /** @return el mes de entrada del empleado */
    public byte getMes() { return mes; }

    /** @return el día de entrada del empleado */
    public byte getDia() { return dia; }

    /** @return el tipo de empleado */
    public TipoEmpleado getTipo() { return tipo; }

    /** @return la provincia de destino */
    public Provincia getProvincia() { return provincia; }

    // -------------------
    // Lógica de negocio
    // -------------------

    /**
     * Comprueba si la fecha de entrada es válida.
     *
     * @return {@code true} si la fecha es válida, {@code false} en caso contrario
     */
    public static boolean esFechaValida(short anio, byte mes, byte dia) {
        GregorianCalendar ingreso = new GregorianCalendar(anio, mes - 1, dia);
        return ingreso.get(GregorianCalendar.YEAR) == anio &&
               ingreso.get(GregorianCalendar.MONTH) == (mes - 1) &&
               ingreso.get(GregorianCalendar.DAY_OF_MONTH) == dia;
    }

    /**
     * Devuelve la fecha de entrada a la empresa como objeto {@link GregorianCalendar}.
     *
     * @return fecha de entrada, o {@code null} si la fecha no es válida
     */
    public GregorianCalendar getFechaEntrada() {
        if (!esFechaValida(anio, mes, dia)) {
            return null;
        }
        return new GregorianCalendar(anio, mes - 1, dia);
    }

    /**
     * Calcula la antigüedad del empleado en años completos.
     *
     * @return años de antigüedad, o 0 si la fecha no es válida
     */
    public byte getAntiguedad() {
        GregorianCalendar ingreso = getFechaEntrada();
        if (ingreso == null) return 0;

        GregorianCalendar hoy = new GregorianCalendar();
        int years = hoy.get(GregorianCalendar.YEAR) - ingreso.get(GregorianCalendar.YEAR);
        if (hoy.get(GregorianCalendar.DAY_OF_YEAR) < ingreso.get(GregorianCalendar.DAY_OF_YEAR)) {
            years--;
        }
        return (byte) (years > 0 ? years : 0);
    }

    /**
     * Calcula el número de trienios (cada 3 años trabajados).
     *
     * @return número de trienios, o 0 si la fecha no es válida
     */
    public byte getTrienios() {
        return (byte) (getAntiguedad() / 3);
    }

    /**
     * Calcula los complementos del empleado según reglas de negocio.
     *
     * @return cantidad total de complementos
     */
    public float getComplementos() {
        float complementos = 0;

        // Trienios
        complementos += getTrienios() * 24;

        // Destino especial
        if (provincia == Provincia.JAEN ||
            provincia == Provincia.HUELVA ||
            provincia == Provincia.ALMERIA) {
            complementos += salarioBase * 0.10f;
        }

        // Por la cara
        if (sexo == 'H') {
            complementos += 120;
        }

        return complementos;
    }

    /**
     * Calcula el sueldo total del empleado.
     *
     * @return salario base más complementos
     */
    public float getSueldo() {
        return salarioBase + getComplementos();
    }

    // -------------------
    // Métodos de impresión
    // -------------------

    /**
     * Imprime la fecha de entrada en formato yyyy-MM-dd.
     * Si la fecha no es válida, imprime "Fecha no válida".
     */
    public void imprimeFechaEntrada() {
        if (!esFechaValida(anio, mes, dia)) {
            System.out.println("Fecha no válida");
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            System.out.println(sdf.format(getFechaEntrada().getTime()));
        }
    }

    /**
     * Devuelve una representación JSON del empleado.
     *
     * @return cadena JSON con los atributos del empleado
     */
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaStr = esFechaValida(anio, mes, dia) ? sdf.format(getFechaEntrada().getTime()) : "Fecha no válida";

        return "{\n" +
            "  \"nombre\": \"" + nombre + "\",\n" +
            "  \"sexo\": \"" + sexo + "\",\n" +
            "  \"salarioBase\": " + salarioBase + ",\n" +
            "  \"complementos\": " + getComplementos() + ",\n" +
            "  \"sueldo\": " + getSueldo() + ",\n" +
            "  \"fechaEntrada\": \"" + fechaStr + "\",\n" +
            "  \"antiguedad\": " + getAntiguedad() + ",\n" +
            "  \"trienios\": " + getTrienios() + ",\n" +
            "  \"tipo\": \"" + tipo.name() + "\",\n" +
            "  \"provincia\": \"" + provincia.name() + "\"\n" +
            "}";
    }
}
