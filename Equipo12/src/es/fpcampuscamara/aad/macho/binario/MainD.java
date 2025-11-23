package es.fpcampuscamara.aad.macho.binario;

/* Equipo 12: Juan Luis Gil de Miguel, Ricardo Boza Villar */

import java.io.DataInputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

/**
 * Obtiene el nombre y apellidos del empleado(s) con el sueldo superior.
 * <p>
 * Recorre todos los registros del fichero binario {@code FICHE.DAT} mediante {@link EmpleadoDAO},
 * calcula el sueldo de cada empleado usando {@link Empleado#getSueldo()}, y muestra
 * aquellos que alcanzan el sueldo máximo.
 * </p>
 *
 * @author Juan Luis Gil de Miguel
 * @author Ricardo Boza Villar
 */
public class MainD {

    public static void main(String[] args) {
        EmpleadoDAO dao = new EmpleadoDAO("FICHE.DAT");

        float maxSueldo = Float.MIN_VALUE;
        List<Empleado> mejoresPagados = new ArrayList<Empleado>();

        try (DataInputStream in = new DataInputStream(
                 new BufferedInputStream(new FileInputStream("FICHE.DAT")))) {

            Empleado e;
            while ((e = dao.leeEmpleado(in)) != null) {
                float sueldo = e.getSueldo();
                if (sueldo > maxSueldo) {
                    maxSueldo = sueldo;
                    mejoresPagados.clear();
                    mejoresPagados.add(e);
                } else if (sueldo == maxSueldo) {
                    mejoresPagados.add(e);
                }
            }

        } catch (IOException ex) {
            System.err.println("Error leyendo empleados: " + ex.getMessage());
        }

        if (mejoresPagados.isEmpty()) {
            System.out.println("No hay empleados en el fichero.");
        } else {
            System.out.printf("Sueldo máximo: %.2f €%n", maxSueldo);
            System.out.println("Empleado(s) con sueldo superior:");
            for (Empleado e : mejoresPagados) {
                System.out.println(" - " + e.getNombre());
            }
        }
    }
}
