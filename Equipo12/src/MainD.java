
import java.io.DataInputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.math.BigDecimal;

/* Equipo 12: Juan Luis Gil de Miguel, Ricardo Boza Villar */
public class MainD {

    public static void main(String[] args) {

        EmpleadoDAO dao = new EmpleadoDAO();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        BigDecimal maxSueldo = BigDecimal.ZERO;
        List<Empleado> mejorPagados = new ArrayList<>();

        try (DataInputStream in = new DataInputStream(
                new BufferedInputStream(new FileInputStream("FICHE.DAT")))) {

            Empleado e;
            while ((e = dao.leeEmpleado(in)) != null) {
                BigDecimal sueldo = e.getSueldo();
                int cmp = sueldo.compareTo(maxSueldo);

                if (cmp > 0) {
                    maxSueldo = sueldo;
                    mejorPagados.clear();
                    mejorPagados.add(e);
                } else if (cmp == 0) {
                    mejorPagados.add(e);
                }
            }

        } catch (IOException ioex) {
            ioex.printStackTrace(System.err);
        }

        if (mejorPagados.isEmpty()) {
            System.out.println("No hay empleados en el fichero.");
        } else {
            System.out.printf("Sueldo máximo: %.2f €%n", maxSueldo.doubleValue());
            System.out.println("Empleado(s) con sueldo superior:");
            for (Empleado e : mejorPagados) {
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
            }
        }
    }
}
