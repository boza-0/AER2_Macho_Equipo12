package es.fpcampuscamara.aad.macho.binario;

/* Equipo 12: Juan Luis Gil de Miguel, Ricardo Boza Villar */

import java.io.DataInputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Listado de empleados con nombre y sueldo total.
 * <p>
 * El sueldo se calcula como {@code salarioBase + complementos}, donde los complementos
 * siguen las reglas de negocio definidas en {@link Empleado#getComplementos()}.
 * </p>
 *
 * @author Juan Luis Gil de Miguel
 * @author Ricardo Boza Villar
 */
public class MainC {

    public static void main(String[] args) {
        System.out.println("Listado de empleados con nombre y sueldo total.");
        EmpleadoDAO dao = new EmpleadoDAO("FICHE.DAT");

        try (DataInputStream in = new DataInputStream(
                 new BufferedInputStream(new FileInputStream("FICHE.DAT")))) {

            Empleado e;
            while ((e = dao.leeEmpleado(in)) != null) {
                // Usamos directamente getSueldo() ya implementado en Empleado
                System.out.printf("Nombre: %-30s | Sueldo: %.2f €%n",
                                  e.getNombre(), e.getSueldo());
            }

        } catch (IOException ex) {
            System.err.println("Error leyendo empleados: " + ex.getMessage());
        }
    }
}
