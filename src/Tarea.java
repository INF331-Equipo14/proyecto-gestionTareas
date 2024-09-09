import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Tarea {
    private static int ultimoId = 0; // Atributo estático para llevar el control del último ID generado
    private int id;
    private String titulo;
    private String descripcion;
    private EstadoTarea estado; // Uso de enumeración para el estado
    private LocalDate fechaVencimiento;
    private ArrayList<String> etiquetas; // Atributo para etiquetas
    private String dia; // Nuevo atributo para registrar el día

    // FORMATO DE FECHA
    static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    // ENUMERACIÓN PARA ESTADOS DE TAREA
    public enum EstadoTarea {
        EN_PROGRESO("En Progreso"),
        COMPLETADA("Completada");

        private final String descripcion;

        EstadoTarea(String descripcion) {
            this.descripcion = descripcion;
        }

        @Override
        public String toString() {
            return descripcion;
        }
    }

    // CONSTRUCTORES
    public Tarea(String titulo, String descripcion, String fechaVencimientoStr, ArrayList<String> etiquetas, String dia) {
        this.id = ++ultimoId; // Incrementa el ID automáticamente
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estado = EstadoTarea.EN_PROGRESO; // Estado inicial
        this.fechaVencimiento = parseFecha(fechaVencimientoStr); // Analiza la fecha
        this.etiquetas = etiquetas != null ? etiquetas : new ArrayList<>(); // Inicializa etiquetas
        this.dia = dia; // Asigna el día
    }

    public Tarea(String titulo, String descripcion, LocalDate fechaVencimiento, ArrayList<String> etiquetas, String dia) {
        this(titulo, descripcion, fechaVencimiento.format(FORMATO_FECHA), etiquetas, dia); // Llama al otro constructor
    }

    // PARSING DE FECHA
    static LocalDate parseFecha(String fechaStr) {
        try {
            return LocalDate.parse(fechaStr, FORMATO_FECHA);
        } catch (DateTimeParseException e) {
            System.out.println("Fecha no válida. Se establecerá la fecha actual.");
            return LocalDate.now(); // Establece la fecha actual si la fecha proporcionada no es válida
        }
    }

    // MÉTODOS PARA MANIPULAR ETIQUETAS
    public ArrayList<String> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(ArrayList<String> etiquetas) {
        this.etiquetas = etiquetas;
    }

    public void agregarEtiqueta(String etiqueta) {
        if (!etiquetas.contains(etiqueta)) {
            etiquetas.add(etiqueta);
        }
    }

    public void eliminarEtiqueta(String etiqueta) {
        etiquetas.remove(etiqueta);
    }

    // MÉTODO toString MODIFICADO
    @Override
    public String toString() {
        return "ID: " + id + ", Título: " + titulo + ", Descripción: " + descripcion +
               ", Estado: " + estado +
               ", Fecha de Vencimiento: " + (fechaVencimiento != null ? fechaVencimiento.format(FORMATO_FECHA) : "No especificada") +
               ", Etiquetas: " + (etiquetas.isEmpty() ? "No etiquetas" : String.join(", ", etiquetas)) +
               ", Día: " + (dia != null ? dia : "No asignado");
    }

    public void cambiarEstado() {
        if (this.estado == EstadoTarea.EN_PROGRESO) {
            this.estado = EstadoTarea.COMPLETADA;
        } else {
            this.estado = EstadoTarea.EN_PROGRESO;
        }
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EstadoTarea getEstado() {
        return estado;
    }

    public void setEstado(EstadoTarea estado) {
        this.estado = estado;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }
}
