
import java.io.DataInputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.text.SimpleDateFormat;

/* Equipo 12: Juan Luis Gil de Miguel, Ricardo Boza Villar */
public class MainB {

    public static void main(String[] args) {
        System.out.println("Consulta de empleados veteranos.");
        int total = 0;
        int veteranos = 0;
        int pageSize = 5;
        int printed = 0;

        EmpleadoDAO dao = new EmpleadoDAO();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try (DataInputStream in = new DataInputStream(
                new BufferedInputStream(new FileInputStream("FICHE.DAT")));
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {

            Empleado e;
            while ((e = dao.leeEmpleado(in)) != null) {
                total++;
                if (e.getAntiguedad() >= 10) {
                    veteranos++;
                    // Mostrar línea resumida por empleado
                    System.out.printf(
                            "%s; %c; %.2f; %.2f; %.2f; %s; %s; %s%n",
                            e.getNombre(),
                            e.getSexo().getCodigo(),
                            e.getSalarioBase().doubleValue(),
                            e.getComplementos().doubleValue(),
                            e.getSueldo().doubleValue(),
                            sdf.format(e.getFechaIngreso()),
                            e.getTipoEmpleado(),
                            e.getProvincia());

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
