import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;


public class PlanesTest {

    private Planes planes;

    public void SetUp(){
        planes = new Planes();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate fechaVencimiento = LocalDate.parse("09-09-2024", formatter);
        ArrayList<String> etiquetas = new ArrayList<>();
        etiquetas.add("Urgente");
        planes.agregarTarea("Lunes", "tarea", "estudiar", fechaVencimiento, etiquetas);
    }

    @AfterEach
    public void tearDown() {
        // Optionally, remove any users created during tests if needed
        planes.eliminarTarea(1);
    }

    @Test
    public void AgregarTarea() {
        SetUp();
        assertNotNull(planes.encontrarTareaEnSemanal(1), "Fallo al agregar la tarea");
    }

    @Test
    public void AgregarTareaSinDia() {
        planes = new Planes();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate fechaVencimiento = LocalDate.parse("09-09-2024", formatter);
        ArrayList<String> etiquetas = new ArrayList<>();
        etiquetas.add("Urgente");
        planes.agregarTarea("", "tarea", "estudiar", fechaVencimiento, etiquetas);
        assertNull(planes.encontrarTareaEnSemanal(1), "Se registro la tarea sin dia");
    }

    @Test
    public void FiltrarPorFecha() {
        SetUp();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate fecha = LocalDate.parse("09-09-2024", formatter);
        List<Tarea> tareasLunes = planes.getTareaDia("lunes");

        List<Tarea> result = planes.filtrarPorFecha(fecha, tareasLunes);
        assertTrue(result.size() > 0, "No se encontro la tarea por fecha");
    }

    @Test
    public void FiltrarPorEtiqueta() {
        SetUp();
        List<Tarea> tareasLunes = planes.getTareaDia("lunes");

        List<Tarea> result = planes.filtrarPorEtiqueta("Urgente", tareasLunes);
        assertTrue(result.size() > 0, "No se encontro la tarea por etiqueta");
    }

    @Test
    public void FiltrarPorEstado() {
        SetUp();
        Tarea.EstadoTarea estado = Tarea.EstadoTarea.valueOf("EN PROGRESO".replace(" ", "_"));

        List<Tarea> result = planes.filtrarPorEstado(estado);
        assertTrue(result.size() > 0, "No se encontro la tarea por estado");
    }
}