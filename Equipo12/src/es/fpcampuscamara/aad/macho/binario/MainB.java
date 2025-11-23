package es.fpcampuscamara.aad.macho.binario;

/* Equipo 12: Juan Luis Gil de Miguel, Ricardo Boza Villar */

import java.io.DataInputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * Clase principal para la consulta de empleados veteranos.
 * <p>
 * Recorre el fichero binario {@code FICHE.DAT} mediante {@link EmpleadoDAO} y
 * procesa todos los registros de empleados almacenados.
 * </p>
 *
 * <p>Funcionalidad principal:</p>
 * <ul>
 *   <li>Cuenta el número total de empleados en el fichero.</li>
 *   <li>Filtra aquellos con 10 o más años de antigüedad en la empresa.</li>
 *   <li>Muestra un listado paginado de los empleados veteranos (5 por página, configurable).</li>
 *   <li>Calcula y muestra el porcentaje de veteranos respecto al total de empleados.</li>
 * </ul>
 *
 * <p>Formato de salida por empleado:</p>
 * <pre>
 * nombre; sexo; salario; fechaIngreso; tipo; provincia
 * </pre>
 *
 * <p>Interacción:</p>
 * <ul>
 *   <li>Tras imprimir cada página de resultados, la aplicación espera que el usuario pulse ENTER para continuar.</li>
 *   <li>En caso de error de E/S durante la lectura, se informa en {@code System.err}.</li>
 *   <li>Si no hay empleados en el fichero, se muestra un mensaje indicativo.</li>
 * </ul>
 *
 * <p>Ejemplo de ejecución:</p>
 * <pre>
 * Total empleados: 42
 * Número de empleados con 10+ años: 15
 * Porcentaje de empleados con 10+ años: 35.71%
 * </pre>
 *
 * @author Juan Luis Gil de Miguel
 * @author Ricardo Boza Villar
 */
public class MainB {

    public static void main(String[] args) {
        System.out.println("Consulta de empleados veteranos.");
        int total = 0;
        int veteranos = 0;
        int pageSize = 5;
        int printed = 0;

        EmpleadoDAO dao = new EmpleadoDAO("FICHE.DAT");

        try (DataInputStream in = new DataInputStream(
                 new BufferedInputStream(new FileInputStream("FICHE.DAT")));
             BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {

            Empleado e;
            while ((e = dao.leeEmpleado(in)) != null) {
                total++;
                if (e.getAntiguedad() >= 10) {
                    veteranos++;
                    // Mostrar línea resumida por empleado
                    System.out.printf("%s; %c; %.2f; %d/%02d/%02d; %s; %s%n",
                            e.getNombre(), e.getSexo(), e.getSalarioBase(),
                            e.getAnio(), e.getMes(), e.getDia(),
                            e.getTipo(), e.getProvincia());
                    printed++;
                    if (printed % pageSize == 0) {
                        System.out.print("Pulse Enter para continuar...");
                        br.readLine(); // pausa hasta ENTER
                    }
                }
            }
        } catch (IOException ex) {
            System.err.println("Error leyendo empleados: " + ex.getMessage());
        }

        if (total > 0) {
            double porcentaje = (veteranos * 100.0) / total;
            System.out.println("Total empleados: " + total);
            System.out.println("Número de empleados con 10+ años: " + veteranos);
            System.out.printf("Porcentaje de empleados con 10+ años: %.2f%%%n", porcentaje);
        } else {
            System.out.println("No hay empleados en el fichero.");
        }
    }
}
