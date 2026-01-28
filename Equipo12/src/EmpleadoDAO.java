
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

/* Equipo 12: Juan Luis Gil de Miguel, Ricardo Boza Villar */
public class EmpleadoDAO {

    public void escribeEmpleado(String fichero, Empleado e) throws IOException {
        if (fichero == null || fichero.trim().isEmpty()) {
            throw new IllegalArgumentException("Ruta de fichero inválida.");
        }
        if (e == null) {
            throw new IllegalArgumentException("Empleado no puede ser nulo.");
        }

        try (DataOutputStream out = new DataOutputStream(
                new BufferedOutputStream(
                new FileOutputStream(fichero.trim(), true)))) {

            out.writeUTF(e.getNombre());
            out.writeChar(e.getSexo().getCodigo());
            out.writeUTF(e.getSalarioBase().toPlainString());
            out.writeLong(e.getFechaIngreso().getTime());
            out.writeChar(e.getTipoEmpleado().getCodigo());
            out.writeByte(e.getProvincia().getCodigo());
        }
    }

    public Empleado leeEmpleado(DataInputStream in) throws IOException {
        try {
            String nombre = in.readUTF();
            Sexo sexo = Sexo.fromCodigo(in.readChar());
            BigDecimal salarioBase = new BigDecimal(in.readUTF());
            Date fechaIngreso = new Date(in.readLong());
            TipoEmpleado tipoEmpleado = TipoEmpleado.fromCodigo(in.readChar());
            Provincia provincia = Provincia.fromCodigo(in.readByte());

            return new Empleado(
                    nombre,
                    sexo,
                    salarioBase,
                    fechaIngreso,
                    tipoEmpleado,
                    provincia);

        } catch (EOFException eof) {
            return null;
        }
    }
}
