
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;

/* Equipo 12: Juan Luis Gil de Miguel, Ricardo Boza Villar */
public class MainA {

    public static void main(String[] args) {

        EmpleadoDAO dao = new EmpleadoDAO();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {

            while (true) {
                String nombre;
                while (true) {
                    System.out.print("Nombre y apellidos (máx 30 caracteres): ");
                    nombre = br.readLine();

                    if (nombre == null) {
                        return; // EOF → exit program cleanly
                    }

                    if (nombre.isEmpty()) {
                        System.out.println("El nombre no puede estar vacío.");
                        continue;
                    }

                    nombre = nombre.trim();

                    if (nombre.length() > 30) {
                        System.out.println("El nombre es demasiado largo. Inténtelo de nuevo.");
                        continue;
                    }

                    break;
                }

                Sexo sexo;
                while (true) {
                    System.out.print("Sexo (");
                    Sexo[] valores = Sexo.values();
                    for (int i = 0; i < valores.length; i++) {
                        System.out.print(valores[i].getCodigo());
                        if (i < valores.length - 1) {
                            System.out.print("/");
                        }
                    }
                    System.out.print("): ");

                    String input = br.readLine();
                    if (input == null) {
                        return; // EOF → exit program cleanly
                    }
                    try {
                        sexo = Sexo.fromInput(input);
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Sexo inválido.");
                    }
                }

                BigDecimal salarioBase;
                while (true) {
                    System.out.print("Salario base: ");
                    String input = br.readLine();

                    if (input == null) {
                        return; // EOF → exit program cleanly
                    }

                    try {
                        salarioBase = new BigDecimal(input);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Salario inválido.");
                    }
                }

                Date fechaIngreso;
                short anio;
                byte mes;
                byte dia;
                while (true) {
                    try {
                        String input;

                        System.out.print("Año ingreso: ");
                        input = br.readLine();
                        if (input == null) {
                            return;
                        }
                        anio = Short.parseShort(input);

                        System.out.print("Mes ingreso: ");
                        input = br.readLine();
                        if (input == null) {
                            return;
                        }
                        mes = Byte.parseByte(input);

                        System.out.print("Día ingreso: ");
                        input = br.readLine();
                        if (input == null) {
                            return;
                        }
                        dia = Byte.parseByte(input);

                        GregorianCalendar cal = new GregorianCalendar();
                        cal.setLenient(false);
                        cal.set(anio, mes - 1, dia);
                        fechaIngreso = cal.getTime();

                        break;

                    } catch (NumberFormatException e) {
                        System.out.println("Fecha inválida. Formato numérico incorrecto.");
                    } catch (Exception e) {
                        System.out.println("Fecha no válida.");
                    }
                }

                TipoEmpleado tipo;
                while (true) {
                    System.out.print("Tipo empleado (");
                    TipoEmpleado[] tipos = TipoEmpleado.values();
                    for (int i = 0; i < tipos.length; i++) {
                        System.out.print(tipos[i].getCodigo());
                        if (i < tipos.length - 1) {
                            System.out.print("/");
                        }
                    }
                    System.out.print("): ");

                    String input = br.readLine();
                    if (input == null) {
                        return; // EOF → exit program cleanly
                    }

                    try {
                        tipo = TipoEmpleado.fromInput(input);
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Tipo inválido.");
                    }
                }

                Provincia provincia;
                while (true) {
                    System.out.print("Provincia (");
                    Provincia[] provincias = Provincia.values();
                    for (int i = 0; i < provincias.length; i++) {
                        System.out.print(provincias[i].toString());
                        if (i < provincias.length - 1) {
                            System.out.print("/");
                        }
                    }
                    System.out.print("): ");

                    String input = br.readLine();
                    if (input == null) {
                        return; // EOF → exit program cleanly
                    }

                    try {
                        provincia = Provincia.fromInput(input);
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Provincia inválida.");
                    }
                }

                Empleado empleado = new Empleado(
                        nombre,
                        sexo,
                        salarioBase,
                        fechaIngreso,
                        tipo,
                        provincia);

                dao.escribeEmpleado("FICHE.DAT", empleado);
                System.out.println("Empleado guardado.");

                System.out.print("¿Otro empleado? (S/N): ");
                String respuesta = br.readLine();

                if (respuesta == null) {
                    break; // EOF
                }

                respuesta = respuesta.trim();

                if (!respuesta.isEmpty() && !respuesta.equalsIgnoreCase("S")) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
}
