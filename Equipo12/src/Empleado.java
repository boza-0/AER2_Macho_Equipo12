
import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.util.Date;

/* Equipo 12: Juan Luis Gil de Miguel, Ricardo Boza Villar */
public class Empleado {

    private final String nombre;
    private final Sexo sexo;
    private final BigDecimal salarioBase;
    private final Date fechaIngreso;
    private final TipoEmpleado tipoEmpleado;
    private final Provincia provincia;

    public Empleado(String nombre,
            Sexo sexo,
            BigDecimal salarioBase,
            Date fechaIngreso,
            TipoEmpleado tipoEmpleado,
            Provincia provincia) {

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (nombre.trim().length() > 30) {
            throw new IllegalArgumentException("El nombre excede los 30 caracteres.");
        }
        this.nombre = nombre.trim();

        if (sexo == null) {
            throw new IllegalArgumentException("Sexo no puede ser nulo.");
        }
        this.sexo = sexo;

        if (salarioBase == null) {
            throw new IllegalArgumentException("Salario base no puede ser nulo.");
        }
        if (salarioBase.compareTo(BigDecimal.ZERO) < 0
                || salarioBase.compareTo(new BigDecimal("9999.99")) > 0) {
            throw new IllegalArgumentException("Salario fuera de rango (0–9999.99).");
        }
        this.salarioBase = salarioBase;

        if (fechaIngreso == null) {
            throw new IllegalArgumentException("Fecha de ingreso no puede ser nula.");
        }
        // Defensive copy
        this.fechaIngreso = new Date(fechaIngreso.getTime());

        if (tipoEmpleado == null) {
            throw new IllegalArgumentException("Tipo de empleado no puede ser nulo.");
        }
        this.tipoEmpleado = tipoEmpleado;

        if (provincia == null) {
            throw new IllegalArgumentException("Provincia no puede ser nula.");
        }
        this.provincia = provincia;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public BigDecimal getSalarioBase() {
        return salarioBase;
    }

    public Date getFechaIngreso() {
        // Defensive copy
        return new Date(fechaIngreso.getTime());
    }

    public TipoEmpleado getTipoEmpleado() {
        return tipoEmpleado;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public int getAntiguedad() {
        GregorianCalendar ingreso = new GregorianCalendar();
        ingreso.setTime(fechaIngreso);

        GregorianCalendar hoy = new GregorianCalendar();

        int antiguedad = hoy.get(GregorianCalendar.YEAR)
                - ingreso.get(GregorianCalendar.YEAR);

        if (hoy.get(GregorianCalendar.MONTH) < ingreso.get(GregorianCalendar.MONTH)
                || (hoy.get(GregorianCalendar.MONTH) == ingreso.get(GregorianCalendar.MONTH)
                && hoy.get(GregorianCalendar.DAY_OF_MONTH) < ingreso.get(GregorianCalendar.DAY_OF_MONTH))) {
            antiguedad--;
        }

        return antiguedad;
    }

    public int getTrienios() {
        return getAntiguedad() / 3;
    }

    /**
     * Calcula los complementos del empleado según reglas de negocio.
     */
    public BigDecimal getComplementos() {
        BigDecimal complementos = BigDecimal.ZERO;

        // Trienios
        complementos = complementos.add(
                BigDecimal.valueOf(getTrienios()).multiply(BigDecimal.valueOf(24)));

        // Destino especial
        if (provincia == Provincia.JAEN
                || provincia == Provincia.HUELVA
                || provincia == Provincia.ALMERIA) {

            complementos = complementos.add(
                    salarioBase.multiply(new BigDecimal("0.10")));
        }

        // Complemento por sexo
        if (sexo == Sexo.HOMBRE) {
            complementos = complementos.add(BigDecimal.valueOf(120));
        }

        return complementos;
    }

    public BigDecimal getSueldo() {
        return salarioBase.add(getComplementos());
    }
}
