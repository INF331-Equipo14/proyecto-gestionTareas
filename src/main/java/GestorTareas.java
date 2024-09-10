import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GestorTareas {
    private List<Tarea> tareasActivas;
    private List<Tarea> tareasArchivadas;

    public GestorTareas() {
        this.tareasActivas = new ArrayList<>();
        this.tareasArchivadas = new ArrayList<>();
    }

    // Agregar una tarea
    public void agregarTarea(Tarea tarea) {
        tareasActivas.add(tarea);
    }

    // Marcar una tarea como completada
    public void marcarComoCompletada(int id) {
        for (Tarea tarea : tareasActivas) {
            if (tarea.getId() == id) {
                tarea.setEstado(Tarea.EstadoTarea.COMPLETADA);
                tareasArchivadas.add(tarea);
                tareasActivas.remove(tarea);
                break;
            }
        }
    }

    // Consultar tareas activas por estado
    public List<Tarea> filtrarTareasActivasPorEstado(String estado) {
        return tareasActivas.stream()
                .filter(tarea -> tarea.getEstado().toString().equalsIgnoreCase(estado))
                .collect(Collectors.toList());
    }

    // Consultar tareas archivadas por estado
    public List<Tarea> filtrarTareasArchivadasPorEstado(String estado) {
        return tareasArchivadas.stream()
                .filter(tarea -> tarea.getEstado().toString().equalsIgnoreCase(estado))
                .collect(Collectors.toList());
    }

    // Mostrar todas las tareas activas
    public void mostrarTareasActivas() {
        System.out.println("Tareas Activas:");
        tareasActivas.forEach(System.out::println);
    }

    // Mostrar todas las tareas archivadas
    public void mostrarTareasArchivadas() {
        System.out.println("Tareas Archivadas:");
        tareasArchivadas.forEach(System.out::println);
    }
}
