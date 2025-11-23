package es.fpcampuscamara.aad.macho.binario;

/* Equipo 12: Juan Luis Gil de Miguel, Ricardo Boza Villar */

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Clase principal de la aplicación de gestión de empleados en fichero binario.
 * <p>
 * Ofrece un menú interactivo con dos modos de operación:
 * <ul>
 *   <li><b>Opción 1:</b> Inserción manual de un empleado, solicitando cada campo al usuario
 *       y aplicando validaciones inmediatas.</li>
 *   <li><b>Opción 2:</b> Lectura de empleados desde un fichero de texto en formato CSV,
 *       con validación de cada línea antes de persistirla.</li>
 * </ul>
 * Los empleados válidos se almacenan en un fichero binario mediante {@link EmpleadoDAO}.
 * </p>
 *
 * <p>Formato esperado del fichero de texto (CSV con separador ';'):</p>
 * <pre>
 * nombre;sexo;salario;anio;mes;dia;tipo;provincia
 * </pre>
 *
 * <p>Validaciones aplicadas:</p>
 * <ul>
 *   <li><b>Nombre:</b> no vacío, máximo 30 caracteres.</li>
 *   <li><b>Sexo:</b> se interpreta como 'M' (mujer/femenino) o 'H' (hombre/masculino). Se aceptan alias de 1 a 4 letras y nombres completos</li>
 *   <li><b>Salario:</b> rango 0–99,999.99.</li>
 *   <li><b>Fecha:</b> año 1900–2100, mes 1–12, día 1–31; además validada en el constructor de {@link Empleado}.</li>
 *   <li><b>Tipo de empleado:</b> interpretado mediante {@code parseTipoEmpleado}, acepta códigos y alias textuales.</li>
 *   <li><b>Provincia:</b> interpretada mediante {@code parseProvincia}, acepta nombre completo y abreviaturas de 2–4 letras.</li>
 * </ul>
 *
 * <p>
 * En caso de error de formato o validación, la línea se descarta y se informa al usuario,
 * garantizando que solo se persisten objetos {@link Empleado} válidos.
 * </p>
 *
 * <p>Ejemplo de ejecución:</p>
 * <pre>
 * Seleccione una opción:
 * 1. Insertar un empleado manualmente
 * 2. Append empleados desde un fichero de texto
 * </pre>
 *
 * @author Juan Luis Gil de Miguel
 * @author Ricardo Boza Villar
 */
public class MainA {

    public static void main(String[] args) {
        String ficheroBinario = "FICHE.DAT";
        EmpleadoDAO dao = new EmpleadoDAO(ficheroBinario);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Insertar un empleado manualmente");
            System.out.println("2. Append empleados desde un fichero de texto");

            int opcion;
            try {
                opcion = Integer.parseInt(br.readLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Debe ser un número.");
                return;
            }

            if (opcion == 1) {
                // Lectura manual con validación
                String nombre;
                while (true) {
                    System.out.print("Nombre y apellidos (máx 30 caracteres): ");
                    nombre = br.readLine().trim();
                    if (nombre.isEmpty()) {
                        System.out.println("El nombre no puede estar vacío.");
                        continue;
                    }
                    if (nombre.length() > 30) {
                        System.out.println("El nombre es demasiado largo. Inténtelo de nuevo.");
                        continue;
                    }
                    break;
                }

                char sexo;
                while (true) {
                    System.out.print("Sexo (M/H, mujer/hombre, femenino/masculino): ");
                    String input = br.readLine();
                    try {
                        sexo = parseSexo(input); // devuelve 'M' o 'H'
                        break;                   // válido, salimos del bucle
                    } catch (IllegalArgumentException ex) {
                        System.out.println("Valor inválido. Use M/mujer/femenino o H/hombre/masculino.");
                    }
                }

                float salarioBase;
                while (true) {
                    System.out.print("Salario base (0 - 99999.99): ");
                    try {
                        salarioBase = Float.parseFloat(br.readLine());
                        if (salarioBase >= 0 && salarioBase <= 99999.99f) break;
                        System.out.println("El salario debe estar entre 0 y 99999.99.");
                    } catch (NumberFormatException e) {
                        System.out.println("Entrada inválida. Introduzca un número.");
                    }
                }

                short anio;
                byte mes;
                byte dia;
                while (true) {
                    try {
                        System.out.print("Año de ingreso (1900 - 2100): ");
                        anio = Short.parseShort(br.readLine());
                        if (anio < 1900 || anio > 2100) {
                            System.out.println("El año debe estar entre 1900 y 2100.");
                            continue;
                        }

                        System.out.print("Mes de ingreso (1 - 12): ");
                        mes = Byte.parseByte(br.readLine());
                        if (mes < 1 || mes > 12) {
                            System.out.println("El mes debe estar entre 1 y 12.");
                            continue;
                        }

                        System.out.print("Día de ingreso (1 - 31): ");
                        dia = Byte.parseByte(br.readLine());
                        if (dia < 1 || dia > 31) {
                            System.out.println("El día debe estar entre 1 y 31.");
                            continue;
                        }

                        // ✅ Validación final con el método estático de Empleado
                        if (Empleado.esFechaValida(anio, mes, dia)) {
                            break; // fecha válida, salimos del bucle
                        } else {
                            System.out.println("La fecha introducida no es válida (ej. 31/02/2020). Vuelva a intentarlo.");
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Entrada inválida. Introduzca un número entero.");
                    }
                }

                TipoEmpleado tipo;
                while (true) {
                    System.out.print("Tipo de empleado (C/F/D o texto: 'a comisión', 'fijo', 'a domicilio'): ");
                    String input = br.readLine();
                    try {
                        tipo = parseTipoEmpleado(input);
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Tipo inválido. Inténtelo de nuevo.");
                    }
                }

                Provincia provincia;
                while (true) {
                    System.out.print("Provincia (escriba al menos unas letras del nombre): ");
                    String input = br.readLine();
                    try {
                        provincia = parseProvincia(input); // tu parser con casos ALMERIA, AL, ALM, ALME, etc.
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Provincia inválida. Inténtelo de nuevo.");
                    }
                }
                
                try {
                    Empleado e = new Empleado(nombre, sexo, salarioBase, anio, mes, dia, tipo, provincia);
                    dao.escribeEmpleado(e);
                    System.out.println("Empleado insertado correctamente en " + ficheroBinario);
                } catch (IOException ex) {
                    System.err.println("Error al escribir el empleado en el fichero: " + ex.getMessage());
                }

            } else if (opcion == 2) {
                // Lectura desde fichero de texto
                System.out.print("Introduzca la ruta absoluta del fichero de texto: ");
                String rutaTxt = br.readLine().trim();

                try (BufferedReader fileReader = new BufferedReader(
                         new InputStreamReader(new FileInputStream(rutaTxt), StandardCharsets.UTF_8))) {

                    String linea;
                    int numLinea = 0;

                    while ((linea = fileReader.readLine()) != null) {
                        numLinea++;
                        String[] partes = linea.split(";");
                        if (partes.length != 8) {
                            System.out.println("Línea inválida (" + numLinea + "): " + linea);
                            continue;
                        }
                        try {
                            String nombre = partes[0].trim();

                            // Usa tu método flexible
                            char sexo = parseSexo(partes[1].trim());

                            float salarioBase = Float.parseFloat(partes[2].trim());
                            if (salarioBase < 0 || salarioBase > 99999.99f) {
                                throw new IllegalArgumentException("Salario fuera de rango: " + salarioBase);
                            }

                            short anio = Short.parseShort(partes[3].trim());
                            byte mes = Byte.parseByte(partes[4].trim());
                            byte dia = Byte.parseByte(partes[5].trim());

                            if (!Empleado.esFechaValida(anio, mes, dia)) {
                                throw new IllegalArgumentException("Fecha inválida: " + dia + "/" + mes + "/" + anio);
                            }

                            TipoEmpleado tipo = parseTipoEmpleado(partes[6].trim());
                            Provincia provincia = parseProvincia(partes[7].trim());

                            Empleado e = new Empleado(nombre, sexo, salarioBase, anio, mes, dia, tipo, provincia);
                            dao.escribeEmpleado(e);

                        } catch (Exception ex) {
                            System.out.println("Error procesando línea " + numLinea + ": " + ex.getMessage());
                        }
                    }

                } catch (FileNotFoundException e) {
                    System.out.println("El fichero no existe: " + rutaTxt);
                } catch (IOException e) {
                    System.out.println("Error leyendo el fichero: " + e.getMessage());
                }
                System.out.println("Empleados del fichero de texto añadidos a " + ficheroBinario);

            } else {
                System.out.println("Opción no válida.");
            }
        } catch (IOException ex) {
            System.err.println("Error de E/S: " + ex.getMessage());
        }
    }

    /**
     * Normaliza una cadena de entrada:
     * <ul>
     *   <li>Convierte a mayúsculas.</li>
     *   <li>Elimina tildes en vocales.</li>
     *   <li>Colapsa espacios múltiples en uno solo.</li>
     * </ul>
     *
     * @param input texto original introducido por el usuario
     * @return texto normalizado en mayúsculas, sin tildes ni espacios extra
     */
    private static String normalizar(String input) {
        if (input == null) return "";
        String s = input.trim().toUpperCase();
        // Reemplazar vocales acentuadas
        s = s.replace("Á","A").replace("É","E").replace("Í","I")
            .replace("Ó","O").replace("Ú","U");
        // Colapsar espacios extra
        s = s.replaceAll("\\s+", " ");
        return s;
    }

    /**
     * Interpreta la entrada del usuario como el sexo de un empleado.
     * <p>Acepta alias flexibles como:
     * "M", "MUJER", "F", "FEMENINO"; "H", "HOMBRE", "MASCULINO".</p>
     *
     * @param input texto introducido por el usuario
     * @return 'M' para mujer, 'H' para hombre
     * @throws IllegalArgumentException si la entrada no corresponde a un sexo válido
     */
    private static char parseSexo(String input) {
        String s = normalizar(input);

        switch (s) {
            // Mujer / femenino
            case "M":       // 1 letra
            case "MU":      // 2 letras
            case "MUJ":     // 3 letras
            case "MUJE":    // 4 letras
            case "MUJER":   // completo
            case "F":       // 1 letra (alias femenino)
            case "FE":      // 2 letras
            case "FEM":     // 3 letras
            case "FEME":    // 4 letras
            case "FEMENINO": // completo
                return 'M';

            // Hombre / masculino
            case "H":       // 1 letra
            case "HO":      // 2 letras
            case "HOM":     // 3 letras
            case "HOMB":    // 4 letras
            case "HOMBRE":  // completo
            case "MA":      // 2 letras (alias masculino)
            case "MAS":     // 3 letras
            case "MASC":    // 4 letras
            case "MASCULINO": // completo
                return 'H';

            default:
                throw new IllegalArgumentException("Sexo inválido: " + input);
        }

    }

    /**
     * Interpreta la entrada del usuario como un {@link TipoEmpleado}.
     * <p>Acepta tanto códigos (C/F/D) como alias textuales:
     * "COMISION", "A COMISION", "FIJO", "CONTRATO FIJO", "DOMICILIO", "A DOMICILIO".</p>
     *
     * @param input texto introducido por el usuario o leído del fichero
     * @return valor correspondiente de {@link TipoEmpleado}
     * @throws IllegalArgumentException si la entrada no corresponde a ningún tipo válido
     */
    private static TipoEmpleado parseTipoEmpleado(String input) {
        String s = normalizar(input);

        switch (s) {
            case "C":
            case "COMI":
            case "COMISION":
            case "A COMISION":
            case "COMISIONES":   // plural opcional
                return TipoEmpleado.COMISION;

            case "F":
            case "FIJO":
            case "CONTRATO FIJO": // alias opcional
                return TipoEmpleado.FIJO;

            case "D":
            case "DOMI":
            case "DOMICILIO":
            case "A DOMICILIO":
                return TipoEmpleado.DOMICILIO;

            default:
                throw new IllegalArgumentException("Tipo inválido: " + input);
        }
    }

    /**
     * Interpreta la entrada del usuario como una {@link Provincia}.
     * <p>Acepta nombre completo y abreviaturas de 2–4 letras:
     * "ALMERIA", "AL", "ALM", "ALME"; "SEVILLA", "SE", "SEV", "SEVI", etc.</p>
     *
     * @param input texto introducido por el usuario o leído del fichero
     * @return valor correspondiente de {@link Provincia}
     * @throws IllegalArgumentException si la entrada no corresponde a ninguna provincia válida
     */
    private static Provincia parseProvincia(String input) {
        String s = normalizar(input);

        switch (s) {
            case "ALMERIA": case "AL": case "ALM": case "ALME":
                return Provincia.ALMERIA;
            case "CADIZ": case "CA": case "CAD": case "CADI":
                return Provincia.CADIZ;
            case "CORDOBA": case "CO": case "COR": case "CORD":
                return Provincia.CORDOBA;
            case "GRANADA": case "GR": case "GRA": case "GRAN":
                return Provincia.GRANADA;
            case "HUELVA": case "HU": case "HUE": case "HUEL":
                return Provincia.HUELVA;
            case "JAEN": case "JA": case "JAE":
                return Provincia.JAEN;
            case "MALAGA": case "MA": case "MAL": case "MALA":
                return Provincia.MALAGA;
            case "SEVILLA": case "SE": case "SEV": case "SEVI":
                return Provincia.SEVILLA;
            default:
                throw new IllegalArgumentException("Provincia inválida: " + input);
        }
    }

}
