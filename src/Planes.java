import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Planes {
    private static final Logger logger = LogManager.getLogger(Planes.class);
    private ArrayList<Tarea> tareasLunes = new ArrayList<>();
    private ArrayList<Tarea> tareasMartes = new ArrayList<>();
    private ArrayList<Tarea> tareasMiercoles = new ArrayList<>();
    private ArrayList<Tarea> tareasJueves = new ArrayList<>();
    private ArrayList<Tarea> tareasViernes = new ArrayList<>();
    private ArrayList<Tarea> tareasArchivadas = new ArrayList<>(); // Lista para tareas archivadas

    // Método para agregar una tarea
    public void agregarTarea(String dia, String titulo, String descripcion, LocalDate fechaVencimiento, ArrayList<String> etiquetas) {
        Tarea tarea = new Tarea(titulo, descripcion, fechaVencimiento, etiquetas, dia);
        switch (dia.toLowerCase()) {
            case "lunes":
                tareasLunes.add(tarea);
                logger.info("Tarea añadida al lunes: " + tarea);
                break;
            case "martes":
                tareasMartes.add(tarea);
                logger.info("Tarea añadida al martes: " + tarea);
                break;
            case "miercoles":
                tareasMiercoles.add(tarea);
                logger.info("Tarea añadida al miércoles: " + tarea);
                break;
            case "jueves":
                tareasJueves.add(tarea);
                logger.info("Tarea añadida al jueves: " + tarea);
                break;
            case "viernes":
                tareasViernes.add(tarea);
                logger.info("Tarea añadida al viernes: " + tarea);
                break;
            default:
                logger.warn("Día no válido: {}", dia);
                break;
        }
    }

    // Método para eliminar una tarea
    public void eliminarTarea(int ID) {
        // Try to find the task in the weekly lists
        Tarea tarea = encontrarTareaEnSemanal(ID);
        if (tarea != null) {
            // Remove the task from the specific weekly list
            eliminarDeListaSemanal(tarea);
            logger.info("Tarea eliminada de la lista semanal: ID={}", ID);
            return;
        }
        
        // If not found in weekly lists, check the archived list
        tarea = encontrarTareaEnArchivadas(ID);
        if (tarea != null) {
            // Remove the task from the archived list
            tareasArchivadas.remove(tarea);
            logger.info("Tarea eliminada de la lista archivada: ID={}", ID);
        } else {
            logger.warn("Tarea con ID {} no encontrada.", ID);
        }
    }
    // Method to remove the task from the specific weekly list
    private void eliminarDeListaSemanal(Tarea tarea) {
        List<Tarea> lista = switch(tarea.getDia().toLowerCase()) {
            case "lunes" -> tareasLunes;
            case "martes" -> tareasMartes;
            case "miercoles" -> tareasMiercoles;
            case "jueves" -> tareasJueves;
            case "viernes" -> tareasViernes;
            default -> null;
        };

        if (lista != null) {
            lista.remove(tarea);
        }
    }
    // Method to find a task by ID in the archived list
    private Tarea encontrarTareaEnArchivadas(int ID) {
    return tareasArchivadas.stream()
            .filter(t -> t.getId() == ID)
            .findFirst()
            .orElse(null);
}
    

    // Método para actualizar una tarea
    public void actualizar(int ID, String apartado, String nuevoValor) {
        ArrayList<Tarea> listaTareas = encontrarTareaPorID(ID);
        if (listaTareas != null) {
            Tarea tarea = listaTareas.stream()
                                     .filter(t -> t.getId() == ID)
                                     .findFirst()
                                     .orElse(null);
            if (tarea != null) {
                switch (apartado.toLowerCase()) {
                    case "estado":
                        try {
                            tarea.setEstado(Tarea.EstadoTarea.valueOf(nuevoValor.toUpperCase()));
                            logger.info("Estado de la tarea con ID " + ID + " actualizado a " + nuevoValor);
                        } catch (IllegalArgumentException e) {
                            logger.warn("Estado no válido: {}", nuevoValor);
                        }
                        break;
                    case "titulo":
                        tarea.setTitulo(nuevoValor);
                        logger.info("Título de la tarea con ID " + ID + " actualizado a " + nuevoValor);
                        break;
                    case "descripcion":
                        tarea.setDescripcion(nuevoValor);
                        logger.info("Descripción de la tarea con ID " + ID + " actualizada a " + nuevoValor);
                        break;
                    case "fecha":
                        try {
                            tarea.setFechaVencimiento(LocalDate.parse(nuevoValor, Tarea.FORMATO_FECHA));
                            logger.info("Fecha de vencimiento de la tarea con ID " + ID + " actualizada a " + nuevoValor);
                        } catch (Exception e) {
                            logger.warn("Fecha no válida para la tarea con ID " + ID + ": " + nuevoValor);
                        }
                        break;
                    case "etiquetas":
                        tarea.setEtiquetas(new ArrayList<>(List.of(nuevoValor.split(",\\s*"))));
                        logger.info("Etiquetas de la tarea con ID " + ID + " actualizadas a " + nuevoValor);
                        break;
                    case "dia":
                        tarea.setDia(nuevoValor);
                        actualizarListaTarea(tarea);
                        logger.info("Día de la tarea con ID " + ID + " actualizado a " + nuevoValor);
                        break;
                    default:
                        logger.warn("Apartado no válido para la tarea con ID " + ID + ": " + apartado);
                        break;
                }
                logger.info("Tarea actualizada: ID={}, Apartado={}, Nuevo Valor={}", ID, apartado, nuevoValor);
            } else {
                logger.warn("Tarea con ID " + ID + " no encontrada para actualización.");
            }
        }
    }

    // Método para actualizar la lista de tareas si cambia el día
    private void actualizarListaTarea(Tarea tarea) {
        eliminarTarea(tarea.getId());
        agregarTarea(tarea.getDia(), tarea.getTitulo(), tarea.getDescripcion(), tarea.getFechaVencimiento(), tarea.getEtiquetas());
    }

    // Método para marcar una tarea como completada
    public void marcarComoCompletada(int ID) {
        // Buscar la tarea en las listas semanales
        Tarea tarea = encontrarTareaEnSemanal(ID);
    
        if (tarea == null) {
            // Si la tarea no está en las listas semanales, buscar en las archivadas
            tarea = encontrarTareaEnArchivadas(ID);
    
            if (tarea != null) {
                logger.info("La tarea ya está completada: ID={}", ID);
                return;
            } else {
                logger.warn("Tarea con ID {} no encontrada.", ID);
                return;
            }
        }
    
        // Si la tarea está en alguna lista semanal y no está completada, marcar como completada
        if (tarea != null && tarea.getEstado() != Tarea.EstadoTarea.COMPLETADA) {
            // Marcar la tarea como completada
            tarea.setEstado(Tarea.EstadoTarea.COMPLETADA);
    
            // Añadir la tarea a las archivadas
            tareasArchivadas.add(tarea);
    
            // Eliminar la tarea de la lista semanal donde se encuentra
            eliminarDeListaSemanal(tarea);
            logger.info("Tarea marcada como completada y movida a archivadas: ID={}", ID);
        }
    }
    
    
    // Método para marcar una tarea como en progreso
    // Método para marcar una tarea como en progreso
    // Método para marcar una tarea como en progreso
    public void marcarComoEnProgreso(int ID) {
        // Buscar la tarea en la lista archivada
        Tarea tareaArchivada = tareasArchivadas.stream()
                .filter(t -> t.getId() == ID)
                .findFirst()
                .orElse(null);
        
        if (tareaArchivada != null) {
            // Cambiar el estado de la tarea a EN_PROGRESO
            tareaArchivada.setEstado(Tarea.EstadoTarea.EN_PROGRESO);
            
            // Agregar la tarea a la lista semanal correspondiente basada en su día
            switch (tareaArchivada.getDia().toLowerCase()) {
                case "lunes":
                    tareasLunes.add(tareaArchivada);
                    break;
                case "martes":
                    tareasMartes.add(tareaArchivada);
                    break;
                case "miercoles":
                    tareasMiercoles.add(tareaArchivada);
                    break;
                case "jueves":
                    tareasJueves.add(tareaArchivada);
                    break;
                case "viernes":
                    tareasViernes.add(tareaArchivada);
                    break;
                default:
                    logger.warn("Día no válido para la tarea: {}", tareaArchivada.getDia());
                    return;
            }
            
            // Eliminar la tarea de la lista archivada
            tareasArchivadas.remove(tareaArchivada);
            logger.info("La tarea ha sido marcada como 'En Progreso' y movida de vuelta a su lista semanal: ID={}", ID);
        } else {
            logger.warn("La tarea no se encuentra en la lista archivada: ID={}", ID);
        }
    }

    // Method to find a task by ID in the weekly tasks lists
    private Tarea encontrarTareaEnSemanal(int ID) {
        return Stream.of(tareasLunes, tareasMartes, tareasMiercoles, tareasJueves, tareasViernes)
                .flatMap(List::stream)
                .filter(t -> t.getId() == ID)
                .findFirst()
                .orElse(null);
    }
    // Método para mostrar todas las tareas
    public void mostrarTareas() {
        logger.info("Mostrando todas las tareas.");
        mostrarTareasPorDia("Lunes", tareasLunes);
        mostrarTareasPorDia("Martes", tareasMartes);
        mostrarTareasPorDia("Miércoles", tareasMiercoles);
        mostrarTareasPorDia("Jueves", tareasJueves);
        mostrarTareasPorDia("Viernes", tareasViernes);
        System.out.println("\nTareas Archivadas:");
        mostrarTareasPorLista(tareasArchivadas);
    }

    // Mostrar tareas por día en formato de tabla con ancho fijo
    private void mostrarTareasPorDia(String dia, List<Tarea> tareas) {
        System.out.printf("\n%s:\n", dia);
        System.out.printf("%-4s | %-30s | %-30s | %-10s | %-11s | %-16s%n",
                "ID", "Título", "Descripción", "Fecha", "Estado", "Etiquetas");
        System.out.println("-----+--------------------------------+--------------------------------+------------+-------------+-------------------");
        mostrarTareasPorLista(tareas);
    }

    // Mostrar tareas por lista en formato de tabla con ancho fijo
    private void mostrarTareasPorLista(List<Tarea> tareas) {
        if (tareas.isEmpty()) {
            System.out.println("No hay tareas.");
        } else {
            for (Tarea tarea : tareas) {
                System.out.printf("%-4d | %-30s | %-30s | %-10s | %-11s | %-16s%n",
                        tarea.getId(),
                        tarea.getTitulo(),
                        tarea.getDescripcion(),
                        tarea.getFechaVencimiento().format(Tarea.FORMATO_FECHA),
                        tarea.getEstado(),
                        String.join(", ", tarea.getEtiquetas()));
            }
        }
    }

    // Método para filtrar tareas por fecha
    public void filtrarPorFecha(LocalDate fecha) {
        logger.info("Filtrando tareas por fecha: " + fecha);
        filtrarTareasPorLista(fecha, tareasLunes, "Lunes");
        filtrarTareasPorLista(fecha, tareasMartes, "Martes");
        filtrarTareasPorLista(fecha, tareasMiercoles, "Miércoles");
        filtrarTareasPorLista(fecha, tareasJueves, "Jueves");
        filtrarTareasPorLista(fecha, tareasViernes, "Viernes");
    }

    // Filtrar tareas por fecha y mostrar en formato de tabla con ancho fijo
    private void filtrarTareasPorLista(LocalDate fecha, List<Tarea> tareas, String dia) {
        List<Tarea> filtradas = filtrarPorFecha(fecha, tareas);
        if (!filtradas.isEmpty()) {
            System.out.printf("\n%s:\n", dia);
            System.out.printf("%-4s | %-30s | %-30s | %-10s | %-11s | %-16s%n",
                    "ID", "Título", "Descripción", "Fecha", "Estado", "Etiquetas");
            System.out.println("-----+--------------------------------+--------------------------------+------------+-------------+-------------------");
            mostrarTareasPorLista(filtradas);
        }
    }

    // Filtrar tareas por fecha
    private List<Tarea> filtrarPorFecha(LocalDate fecha, List<Tarea> tareas) {
        return tareas.stream()
                .filter(tarea -> tarea.getFechaVencimiento().isEqual(fecha))
                .collect(Collectors.toList());
    }

    // Método para filtrar tareas por estado
    public void filtrarPorEstado(Tarea.EstadoTarea estado) {
        logger.info("Filtrando tareas por estado: " + estado);
    
        // Filtrar tareas de cada día y las archivadas por estado
        List<Tarea> tareasFiltradas = new ArrayList<>();
        tareasFiltradas.addAll(filtrarYEliminarDuplicados(tareasLunes, estado));
        tareasFiltradas.addAll(filtrarYEliminarDuplicados(tareasMartes, estado));
        tareasFiltradas.addAll(filtrarYEliminarDuplicados(tareasMiercoles, estado));
        tareasFiltradas.addAll(filtrarYEliminarDuplicados(tareasJueves, estado));
        tareasFiltradas.addAll(filtrarYEliminarDuplicados(tareasViernes, estado));
        tareasFiltradas.addAll(filtrarYEliminarDuplicados(tareasArchivadas, estado));
    
        // Mostrar tareas filtradas
        if (tareasFiltradas.isEmpty()) {
            logger.info("No se encontraron tareas con el estado: " + estado);
        } else {
            System.out.printf("%-4s | %-30s | %-30s | %-10s | %-11s | %-16s%n",
                    "ID", "Título", "Descripción", "Fecha", "Estado", "Etiquetas");
            System.out.println("-----+--------------------------------+--------------------------------+------------+-------------+-------------------");
            mostrarTareasPorLista(tareasFiltradas);
        }
    }

// Filtrar tareas por estado y eliminar duplicados
private List<Tarea> filtrarYEliminarDuplicados(List<Tarea> tareas, Tarea.EstadoTarea estado) {
    // Filtrar tareas por estado
    List<Tarea> filtradas = tareas.stream()
                                  .filter(t -> t.getEstado() == estado)
                                  .collect(Collectors.toList());
    
    // Eliminar duplicados por ID
    Map<Integer, Tarea> tareasUnicas = new HashMap<>();
    for (Tarea tarea : filtradas) {
        tareasUnicas.putIfAbsent(tarea.getId(), tarea);
    }
    
    return new ArrayList<>(tareasUnicas.values());
}

    // Método para filtrar tareas por etiqueta
    public void filtrarPorEtiqueta(String etiqueta) {
        logger.info("Filtrando tareas por etiqueta: " + etiqueta);
        filtrarTareasPorEtiqueta(etiqueta, tareasLunes, "Lunes");
        filtrarTareasPorEtiqueta(etiqueta, tareasMartes, "Martes");
        filtrarTareasPorEtiqueta(etiqueta, tareasMiercoles, "Miércoles");
        filtrarTareasPorEtiqueta(etiqueta, tareasJueves, "Jueves");
        filtrarTareasPorEtiqueta(etiqueta, tareasViernes, "Viernes");
        filtrarTareasPorEtiqueta(etiqueta, tareasArchivadas, "Archivadas");

    }

    // Filtrar tareas por etiqueta y mostrar en formato de tabla con ancho fijo
    private void filtrarTareasPorEtiqueta(String etiqueta, List<Tarea> tareas, String dia) {
        List<Tarea> filtradas = filtrarPorEtiqueta(etiqueta, tareas);
        if (!filtradas.isEmpty()) {
            System.out.printf("\n%s:\n", dia);
            System.out.printf("%-4s | %-30s | %-30s | %-10s | %-11s | %-16s%n",
                    "ID", "Título", "Descripción", "Fecha", "Estado", "Etiquetas");
            System.out.println("-----+--------------------------------+--------------------------------+------------+-------------+-------------------");
            mostrarTareasPorLista(filtradas);
        }
        else{
            logger.info("No se encontraron tareas con la etiqueta: " + etiqueta);
        }
    }

    // Filtrar tareas por etiqueta
    private List<Tarea> filtrarPorEtiqueta(String etiqueta, List<Tarea> tareas) {
        return tareas.stream()
                .filter(tarea -> tarea.getEtiquetas().contains(etiqueta))
                .collect(Collectors.toList());
    }

    // Método para encontrar una tarea por ID en las listas
    private ArrayList<Tarea> encontrarTareaPorID(int ID) {
        ArrayList<Tarea> listaTareas = new ArrayList<>();
        listaTareas.addAll(tareasLunes);
        listaTareas.addAll(tareasMartes);
        listaTareas.addAll(tareasMiercoles);
        listaTareas.addAll(tareasJueves);
        listaTareas.addAll(tareasViernes);
        listaTareas.addAll(tareasArchivadas);
        return listaTareas;
    }
}