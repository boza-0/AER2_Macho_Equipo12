package es.fpcampuscamara.aad.macho.binario;

/* Equipo 12: Juan Luis Gil de Miguel, Ricardo Boza Villar */

import java.io.*;

/**
 * Data Access Object (DAO) para la entidad {@link Empleado}.
 * <p>
 * Proporciona operaciones básicas de persistencia sobre un fichero binario secuencial:
 * <ul>
 *   <li><b>escribeEmpleado</b>: añade un nuevo registro al final del fichero.</li>
 *   <li><b>leeEmpleado</b>: reconstruye un objeto {@code Empleado} a partir de un registro leído.</li>
 * </ul>
 * </p>
 *
 * <p>Formato de almacenamiento por registro:</p>
 * <ol>
 *   <li>Nombre: {@code UTF}</li>
 *   <li>Sexo: {@code char}</li>
 *   <li>Salario base: {@code float}</li>
 *   <li>Año de entrada: {@code short}</li>
 *   <li>Mes de entrada: {@code byte}</li>
 *   <li>Día de entrada: {@code byte}</li>
 *   <li>Tipo de empleado: {@code char} (código del enum {@link TipoEmpleado})</li>
 *   <li>Provincia: {@code byte} (código del enum {@link Provincia})</li>
 * </ol>
 *
 * <p>
 * El fichero se abre en modo <i>append</i> para escritura, garantizando que no se sobrescriben
 * registros existentes, y en modo secuencial para lectura. Al llegar al fin de fichero,
 * {@link #leeEmpleado(DataInputStream)} devuelve {@code null}.
 * </p>
 *
 * <p>Ejemplo de uso:</p>
 * <pre>
 * EmpleadoDAO dao = new EmpleadoDAO("FICHE.DAT");
 * dao.escribeEmpleado(e); // persistir un empleado
 * try (DataInputStream in = new DataInputStream(new FileInputStream("FICHE.DAT"))) {
 *     Empleado emp;
 *     while ((emp = dao.leeEmpleado(in)) != null) {
 *         // procesar empleado
 *     }
 * }
 * </pre>
 *
 * <p>
 * Esta clase no implementa concurrencia ni bloqueo de registros; se asume acceso secuencial
 * desde un único proceso.
 * </p>
 *
 * @author Juan Luis Gil de Miguel
 * @author Ricardo Boza Villar
 */
public class EmpleadoDAO {

    /** Ruta del fichero binario donde se almacenan los empleados. */
    private final String fichero;

    /**
     * Crea un DAO asociado a un fichero binario.
     *
     * @param fichero ruta del fichero binario donde se guardarán/leerán los empleados
     */
    public EmpleadoDAO(String fichero) {
        this.fichero = fichero;
    }

    /**
     * Escribe (append) un empleado al final del fichero binario secuencial.
     *
     * @param e el empleado a escribir
     * @throws IOException si ocurre un error de E/S durante la escritura
     */
    public void escribeEmpleado(Empleado e) throws IOException {
        try (DataOutputStream out = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream(fichero, true)))) {
            out.writeUTF(e.getNombre());
            out.writeChar(e.getSexo());
            out.writeFloat(e.getSalarioBase()); // salario base
            out.writeShort(e.getAnio());
            out.writeByte(e.getMes());
            out.writeByte(e.getDia());
            out.writeChar(e.getTipo().getCodigo());      // código del enum TipoEmpleado
            out.writeByte(e.getProvincia().getCodigo()); // código del enum Provincia
        }
    }

    /**
     * Lee el siguiente registro desde el flujo binario y lo convierte en un objeto {@link Empleado}.
     * <p>
     * Devuelve {@code null} al llegar al fin de fichero.
     * </p>
     *
     * @param in flujo de entrada binario abierto sobre el fichero
     * @return un objeto Empleado reconstruido, o {@code null} si se alcanzó el fin de fichero
     * @throws IOException si ocurre un error de E/S durante la lectura
     */
    public Empleado leeEmpleado(DataInputStream in) throws IOException {
        try {
            String nombre = in.readUTF();
            char sexo = in.readChar();
            float salario = in.readFloat();
            short anio = in.readShort();
            byte mes = in.readByte();
            byte dia = in.readByte();
            char tipoCodigo = in.readChar();
            byte provinciaCodigo = in.readByte();

            TipoEmpleado tipo = TipoEmpleado.fromCodigo(tipoCodigo);
            Provincia provincia = Provincia.fromCodigo(provinciaCodigo);

            return new Empleado(nombre, sexo, salario, anio, mes, dia, tipo, provincia);
        } catch (EOFException eof) {
            return null;
        }
    }
}
