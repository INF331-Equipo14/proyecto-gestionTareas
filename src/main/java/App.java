import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {
    private static final Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) {
        Planes planes = new Planes();
        SistemaLogin sistema = new SistemaLogin();
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n1. Registrar");
            System.out.println("\n2. Iniciar sesión");
            System.out.println("\n3. Salir");
            System.out.println();
            System.out.print("Selecciona una opción: ");
            int acceso = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (acceso) {
                case 1:
                    System.out.print("Nombre de usuario: ");
                    String nuevoUsuario = scanner.nextLine();
                    System.out.print("Contraseña: ");
                    String nuevaContrasena = scanner.nextLine();
                    sistema.registrar(nuevoUsuario, nuevaContrasena);
                    logger.info("Usuario registrado: {}", nuevoUsuario);
                    break;
                case 2:
                    System.out.print("Nombre de usuario: ");
                    String usuario = scanner.nextLine();
                    System.out.print("Contraseña: ");
                    String contrasena = scanner.nextLine();
                    if (sistema.iniciarSesion(usuario, contrasena)) {
                        logger.info("Inicio de sesión exitoso para el usuario: {}", usuario);
                        while (true) {
                            imprimirMenu();
                            System.out.print("Seleccionar opción: ");
                            int opcion = scanner.nextInt();
                            scanner.nextLine();  // Limpiar el buffer
                
                            switch (opcion) {
                                case 1:
                                    agregarTarea(planes, scanner);
                                    break;
                                case 2:
                                    eliminarTarea(planes, scanner);
                                    break;
                                case 3:
                                    actualizarTarea(planes, scanner);
                                    break;
                                case 4:
                                    filtrarPorFecha(planes, scanner);
                                    break;
                                case 5:
                                    filtrarPorEtiqueta(planes, scanner);
                                    break;
                                case 6:
                                    filtrarPorEstado(planes, scanner);
                                    break;
                                case 7:
                                    mostrarTareas(planes);
                                    break;
                                case 8:
                                    marcarComoCompletada(planes, scanner);
                                    break;
                                case 9:
                                    marcarComoEnProgreso(planes, scanner);
                                    break;
                                case 10:
                                    System.out.println("Saliendo...");
                                    logger.info("Usuario {} salió del sistema", usuario);
                                    scanner.close();
                                    return;
                                default:
                                    System.out.println("Opción no válida.");
                                    logger.warn("Opción no válida seleccionada: {}", opcion);
                                    break;
                            }
                        }
                    } else {
                        logger.warn("Error de inicio de sesión para el usuario: {}", usuario);
                        break;
                    }
                case 3:
                    System.out.println("Saliendo...");
                    logger.info("Salida del sistema");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opción no válida.");
                    logger.warn("Opción no válida seleccionada: {}", acceso);
            }
        }
    }

    private static void imprimirMenu() {
        System.out.println("-------------------------------");
        System.out.println("Menú de Gestión de Tareas");
        System.out.println("-------------------------------");
        System.out.println("1. Agregar tarea");
        System.out.println("2. Eliminar tarea");
        System.out.println("3. Actualizar tarea");
        System.out.println("4. Filtrar por fecha");
        System.out.println("5. Filtrar por etiqueta");
        System.out.println("6. Filtrar por estado");
        System.out.println("7. Mostrar todas las tareas");
        System.out.println("8. Marcar tarea como completada");
        System.out.println("9. Marcar tarea como en progreso");
        System.out.println("10. Salir");
        System.out.println("-------------------------------");
    }

    private static void agregarTarea(Planes planes, Scanner scanner) {
        System.out.print("Día (Lunes, Martes, etc.): ");
        String dia = scanner.nextLine();
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();

        LocalDate fechaVencimiento = null;
        
        while (fechaVencimiento == null) {
            System.out.print("Fecha de vencimiento (dd-MM-yyyy): ");
            String fechaInput = scanner.nextLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            try {
                fechaVencimiento = LocalDate.parse(fechaInput, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha incorrecto. Inténtalo de nuevo.");
                logger.error("Formato de fecha incorrecto: {}", fechaInput, e);
            };
        }

        System.out.print("Etiquetas (separadas por comas): ");
        ArrayList<String> etiquetas = new ArrayList<>();
        for (String etiqueta : scanner.nextLine().split(",\\s*")) {
            if (!etiqueta.trim().isEmpty()) {
                etiquetas.add(etiqueta.trim());
            }
        }
        
        planes.agregarTarea(dia, titulo, descripcion, fechaVencimiento, etiquetas);
        logger.info("Tarea agregada: Título '{}', Día '{}'", titulo, dia);
    }

    private static void eliminarTarea(Planes planes, Scanner scanner) {
        System.out.print("ID de la tarea a eliminar: ");
        int idEliminar = scanner.nextInt();
        scanner.nextLine();  // Limpiar el buffer
        planes.eliminarTarea(idEliminar);
        logger.info("Tarea eliminada: ID {}", idEliminar);
    }

    private static void actualizarTarea(Planes planes, Scanner scanner) {
        System.out.print("ID de la tarea a actualizar: ");
        int idActualizar = scanner.nextInt();
        scanner.nextLine();  // Limpiar el buffer
        System.out.print("Apartado a actualizar (estado, título, descripción, fecha, etiquetas, día): ");
        String apartado = scanner.nextLine();
        System.out.print("Nuevo valor: ");
        String nuevoValor = scanner.nextLine();
        planes.actualizar(idActualizar, apartado, nuevoValor);
        logger.info("Tarea actualizada: ID {}, Apartado '{}', Nuevo Valor '{}'", idActualizar, apartado, nuevoValor);
    }

    private static void filtrarPorFecha(Planes planes, Scanner scanner) {
        System.out.print("Introduce la fecha para filtrar las tareas (formato dd-MM-yyyy): ");
        String fechaInput = scanner.nextLine();
        LocalDate fechaFiltro = null;
        try {
            fechaFiltro = LocalDate.parse(fechaInput, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            planes.filtrarPorFecha(fechaFiltro);
            logger.info("Filtrado de tareas por fecha: {}", fechaFiltro);
        } catch (DateTimeParseException e) {
            System.out.println("Formato de fecha incorrecto. Por favor, usa el formato dd-MM-yyyy.");
            logger.error("Formato de fecha incorrecto: {}", fechaInput, e);
        }
    }

    private static void filtrarPorEtiqueta(Planes planes, Scanner scanner) {
        System.out.print("Introduce la etiqueta para filtrar las tareas: ");
        String etiquetaFiltro = scanner.nextLine().trim();
        if (!etiquetaFiltro.isEmpty()) {
            planes.filtrarPorEtiqueta(etiquetaFiltro);
            logger.info("Filtrado de tareas por etiqueta: {}", etiquetaFiltro);
        } else {
            System.out.println("No se introdujo ninguna etiqueta. Inténtalo de nuevo.");
            logger.warn("No se introdujo ninguna etiqueta para el filtro.");
        }
    }

    private static void filtrarPorEstado(Planes planes, Scanner scanner) {
        System.out.println("Introduce el estado para filtrar las tareas:");
        System.out.println("Opciones válidas: 'En Progreso' o 'Completada'");
        String estadoFiltro = scanner.nextLine().trim();
        
        try {
            // Convertir el estado ingresado a mayúsculas y reemplazar espacios por guiones bajos
            Tarea.EstadoTarea estado = Tarea.EstadoTarea.valueOf(estadoFiltro.toUpperCase().replace(" ", "_"));
            planes.filtrarPorEstado(estado);
            logger.info("Filtrado de tareas por estado: {}", estado);
        } catch (IllegalArgumentException e) {
            System.out.println("Estado no válido. Usa 'En Progreso' o 'Completada'.");
            logger.error("Estado no válido: {}", estadoFiltro, e);
        }
    }
    
    private static void mostrarTareas(Planes planes) {
        System.out.println("-------------------------------");
        System.out.println("Estas son tus tareas de la semana");
        System.out.println("-------------------------------");
        planes.mostrarTareas();
        logger.info("Tareas mostradas");
    }

    private static void marcarComoCompletada(Planes planes, Scanner scanner) {
        System.out.print("ID de la tarea a marcar como completada: ");
        int idCompletar = scanner.nextInt();
        scanner.nextLine();  // Limpiar el buffer
        planes.marcarComoCompletada(idCompletar);
        logger.info("Tarea marcada como completada: ID {}", idCompletar);
    }

    private static void marcarComoEnProgreso(Planes planes, Scanner scanner) {
        System.out.print("ID de la tarea a marcar como en progreso: ");
        int idProgreso = scanner.nextInt();
        scanner.nextLine();  // Limpiar el buffer
        planes.marcarComoEnProgreso(idProgreso);
        logger.info("Tarea marcada como en progreso: ID {}", idProgreso);
    }
}
