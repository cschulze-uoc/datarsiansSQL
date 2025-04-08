package datarsians.vista;

import datarsians.excepciones.DniDuplicado;
import datarsians.excepciones.EmailDuplicado;
import datarsians.excepciones.EmailNoValido;
import datarsians.modelo.Datos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

/**
 * Clase con métodos para permitir la interacción del usuario con la aplicación
 */
public class ConsoleHelper {
    private static final Scanner teclado = new Scanner(System.in);

    /**
     * SolicitarDatoPorConsola Metodo genérico para solicitar un String al usuario por consola
     *
     * @param prompt El mensaje que se le mostrará al usuario
     * @param longitudEsperada El número de carácteres que el usuario debe introducir. Si es null, no se comprueba
     * @return La entrada de texto del usuario en una línea de consola
     */
    public static String SolicitarTextoPorConsola(String prompt, Integer longitudEsperada, boolean AceptarEmpty) {

        String entrada;

        do {
            if (prompt != null) {
                System.out.println(prompt);
            }
            entrada = teclado.nextLine().trim();

            // Si longitudEsperada es null o menor o igual a 0, no validamos la longitud
            if (longitudEsperada == null || longitudEsperada <= 0) {
                break;
            }

            // Validar longitud de la entrada
            if ((entrada.length() == longitudEsperada) || (AceptarEmpty  && entrada.isEmpty())) {
                break;
            } else {
                System.out.println("Error: La entrada debe tener exactamente " + longitudEsperada + " caracteres. Por favor, inténtalo de nuevo.");
            }
        } while (true);

        return entrada;
    }

    /**
     * SolicitarEmailPorConsola Metodo para solicitar un email al usuario y validarlo.
     *
     * @param prompt El mensaje que se mostrara al usuario.
     * @retrum El email que el usuario ha ingresado.
     */
    public static String SolicitarEmailPorConsola(String prompt, List<String> listaEmails) {
        String email;

        while (true) {
            System.out.println(prompt);
            email = teclado.nextLine().trim();

            try {
                if (!Datos.esEmailValido(email)) {
                    throw new EmailNoValido("Error: El email ingresado no es válido.");
                }

                if (listaEmails != null && listaEmails.contains(email)) {
                    throw new EmailDuplicado("Error: El email ya está registrado.");
                }

                return email;

            } catch (EmailNoValido | EmailDuplicado e){
                System.out.println(e.getMessage());

                System.out.println("¿Desea intentar de nuevo?");
                System.out.println("1. Volver a ingresar el email.");
                System.out.println("2. Anular grabación.");

                int seleccion = SolicitarNumeroPorConsola(1, 2, "Seleccione una opción:");

                if (seleccion == 2) {
                    System.out.println("Operación cancelada.");
                    return null;
                }
            }
        }
    }

    /**
     * seleccionarOpcion Metodo genérico para que el usuario escoja una opción dentro de una ENUM
     *
     * @param enumClass La enumeración dentro de la cual tendra que escoger el usuario
     * @return La entrada de la enumeración que ha seleccionado el usuario
     */
    public static <T extends Enum<T>> T seleccionarOpcion(Class<T> enumClass, boolean OpcionTodos, String caption) {
        T[] opciones = enumClass.getEnumConstants();

        System.out.println(caption);
        for (int i = 0; i < opciones.length; i++) {
            System.out.println(i + ". " + opciones[i]);
        }
        if (OpcionTodos) System.out.println(opciones.length + ". Todos");

        int seleccion = -1;
        while (seleccion < 0 || seleccion >= opciones.length ) {
            System.out.print("Ingrese el número correspondiente a su selección: ");
            if (teclado.hasNextInt()) {
                seleccion = teclado.nextInt();
                teclado.nextLine();
                if (seleccion < 0 || seleccion >= opciones.length) {
                    System.out.println("Selección inválida. Por favor, intente nuevamente.");
                }
            } else {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                teclado.next();
            }
        }
        return opciones[seleccion];
    }

    /**
     * SolicitarNumeroPorConsola Metodo genérico para que el usuario introduzca un número
     *
     * @param min El valor mínimo que debe introducir el usuario para que la entrada sea valida
     * @param max El valor máximo que debe introducir el usuario para que la entrada sea valida
     * @param texto El texto que se imprimira en consola solicitando al usuario
     * @return La entrada de la enumeración que ha seleccionado el usuario
     */
    public static int SolicitarNumeroPorConsola(int min, int max, String texto) {
        int seleccion = Integer.MIN_VALUE;

        while (true) {
            System.out.print(texto);
            if ( teclado.hasNextInt()) {
                seleccion = teclado.nextInt();
                teclado.nextLine();
                if (seleccion >= min && seleccion <= max) {
                    return seleccion;
                } else {
                    System.out.println("Error: Número fuera del rango permitido");
                }
            } else {
                System.out.println("Entrada inválida. Por favor, ingrese un número");
                teclado.next();
            }
        }
    }


    /**
     * SolicitarNumeroFloatPorConsola Metodo genérico para que el usuario introduzca un número flotante
     *
     * @param min El valor mínimo que debe introducir el usuario para que la entrada sea valida
     * @param max El valor máximo que debe introducir el usuario para que la entrada sea valida
     * @param texto El texto que se imprimira en consola solicitando al usuario
     * @return La entrada flotante que ha seleccionado el usuario
     */
    public static float SolicitarNumeroPorConsola(float min, float max, String texto) {
        if (texto != null) System.out.print(texto);
        float seleccion = -1f;
        while (seleccion < min || seleccion >= max) {
            if (teclado.hasNextFloat()) {
                seleccion = teclado.nextFloat();
                teclado.nextLine();
                if (seleccion < min || seleccion > max) {
                    System.out.println("Selección inválida. Por favor, intente nuevamente.");
                }
            } else {
                System.out.println("Entrada inválida. Por favor, ingrese un número flotante.");
                teclado.next(); // Limpiar la entrada no válida
            }
        }
        return seleccion;
    }

    /**
     * EsperarTecla Metodo que espera a que el usuario pulse enter. Si introduce algo antes del enter, es ignorado
     *
     */
    public static void EsperarTecla() {
        try {
            System.out.println("Presiona enter para continuar...");
            teclado.nextLine();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * SolicitarFechaPorConsola Metodo genérico para que el usuario introduzca una fecha
     *
     * @param texto El texto que se imprimirá en consola solicitando al usuario
     * @return La fecha que ha seleccionado el usuario con la hora 00:00
     */
    public static LocalDateTime SolicitarFechaPorConsola(String texto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (true) {
            System.out.print(texto);;
            try {
                return LocalDate.parse(teclado.nextLine().trim(), formatter).atStartOfDay();
            } catch (Exception e){
                System.out.print("Formato de fecha incorrecto, inténtelo de nuevo");
            }
        }
    }

    /**
     * seleccionarOpcion Metodo genérico para que el usuario seleccione un elemento de una lista
     *
     * @param opciones La lista entre la que habrá que seleccionar una opción
     * @param atributoAMostrar El atributo de la clase T de la lista de opciones que se mostrará en consola
     * @return El elemento seleccionado
     */
    public static <T> T seleccionarOpcion(List<T> opciones, Function<T, String> atributoAMostrar) {
        if (opciones.isEmpty()) {
            System.out.println("No hay opciones disponibles.");
            return null;
        }

        for (int i = 0; i < opciones.size(); i++) {
            System.out.println((i + 1) + ". " + atributoAMostrar.apply(opciones.get(i)));
        }

        int seleccion = SolicitarNumeroPorConsola(1, opciones.size(), "Seleccione una opción: ");
        return opciones.get(seleccion - 1);
    }

    public static String SolicitarDniPorConsola(String prompt, List<String> listaDnis) {
        String dni;
        while (true) {
            System.out.println(prompt);
            dni = teclado.nextLine().trim();

            if (dni.length() !=9) {
                System.out.println("Error: El DNI debe tener 9 caracteres.");
                continue;
            }

            try {
                if (listaDnis.contains(dni)) {
                    throw new DniDuplicado("Error: El DNI ingresado ya existe.");
                }
                return dni;
            } catch (DniDuplicado e) {
                System.out.println(e.getMessage());
                System.out.println("Desea intentar de nuevo?");
                System.out.println("1. Volver a ingresar el DNI.");
                System.out.println("2. Anular gravación.");
                int seleccion = SolicitarNumeroPorConsola( 1, 2, "Seleccione una opción: ");

                if (seleccion == 2) {
                    System.out.println("Operación cancelada.");
                    return null;
                }
            }
        }
    }
}

