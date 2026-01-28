
import java.io.DataInputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

/* Equipo 12: Juan Luis Gil de Miguel, Ricardo Boza Villar */
public class MainC {

    public static void main(String[] args) {

        EmpleadoDAO dao = new EmpleadoDAO();

        System.out.println("Listado de empleados con nombre y sueldo total.");
        try (DataInputStream in = new DataInputStream(
                new BufferedInputStream(new FileInputStream("FICHE.DAT")))) {

            Empleado e;
            while ((e = dao.leeEmpleado(in)) != null) {
                // Usamos directamente getSueldo() ya implementado en Empleado
                System.out.printf("Nombre: %-30s | Sueldo: %.2f €%n",
                        e.getNombre(), e.getSueldo().doubleValue());
            }

        } catch (IOException ioex) {
            ioex.printStackTrace(System.err);
        }
    }
}
