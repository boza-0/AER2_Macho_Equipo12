package es.fpcampuscamara.aad.macho.binario;

/* Equipo 12: Juan Luis Gil de Miguel, Ricardo Boza Villar */

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.EnumMap;

/**
 * Obtiene el nombre de la provincia(s) con mayor número de empleados.
 * <p>
 * Recorre todos los registros del fichero binario {@code FICHE.DAT} mediante {@link EmpleadoDAO},
 * contabiliza los empleados por provincia y muestra aquellas con el máximo número.
 * </p>
 *
 * @author Juan Luis Gil de Miguel
 * @author Ricardo Boza Villar
 */
public class MainE {

    public static void main(String[] args) {
        EmpleadoDAO dao = new EmpleadoDAO("FICHE.DAT");

        Map<Provincia, Integer> conteo = new EnumMap<>(Provincia.class);
        int max = 0;

        try (DataInputStream in = new DataInputStream(
                 new BufferedInputStream(new FileInputStream("FICHE.DAT")))) {

            Empleado e;
            while ((e = dao.leeEmpleado(in)) != null) {
                Provincia p = e.getProvincia();
                int nuevoConteo = conteo.containsKey(p) ? conteo.get(p) + 1 : 1;
                conteo.put(p, nuevoConteo);
                if (nuevoConteo > max) {
                    max = nuevoConteo;
                }
            }

        } catch (IOException ex) {
            System.err.println("Error leyendo empleados: " + ex.getMessage());
        }

        if (conteo.isEmpty()) {
            System.out.println("No hay empleados en el fichero.");
        } else {
            System.out.println("Provincia(s) con mayor número de empleados:");
            for (Map.Entry<Provincia, Integer> entry : conteo.entrySet()) {
                if (entry.getValue() == max) {
                    System.out.println(" - " + entry.getKey() + " (" + entry.getValue() + ")");
                }
            }
        }
    }
}
