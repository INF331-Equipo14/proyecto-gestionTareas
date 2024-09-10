import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class SistemaLoginTest {

    private SistemaLogin sistema;

    @BeforeEach
    public void SetUp(){
        sistema = new SistemaLogin();
    }

    @AfterEach
    public void tearDown() {
        // Optionally, remove any users created during tests if needed
        sistema.eliminarUsuario("usuario");
        sistema.eliminarUsuario("");
    }

    @Test
    public void registrarUsuario() {
        boolean result = sistema.registrar("usuario", "1234");
        assertNotNull(sistema.buscarUsuario("usuario"), "Usuario no registrado");
    }

    @Test
    public void registrarUsuarioSinPassword() {
        boolean result = sistema.registrar("usuario", "");
        assertNull(sistema.buscarUsuario("usuario"), "Usuario sin contraseña registrado");
    }

    @Test
    public void registrarUsuarioSinNombre() {
        boolean result = sistema.registrar("", "123");
        assertNull(sistema.buscarUsuario(""), "Usuario sin nombre registrado");
    }

    @Test
    public void registrarUsuarioSinDatos() {
        boolean result = sistema.registrar("", "");
        assertNull(sistema.buscarUsuario(""), "Usuario sn nombre ni contraseña registrado");
    }

    @Test
    public void iniciarSesion() {
        boolean result = sistema.registrar("usuario", "1234");
        assertTrue(result, "registro correcto");
        if(result){
            assertTrue(sistema.iniciarSesion("usuario", "1234"), "Login fallido");
        }
    }

    @Test
    public void iniciarSesionSinPassword() {
        boolean result = sistema.registrar("usuario", "1234");
        assertTrue(result, "registro correcto");
        if(result){
            assertFalse(sistema.iniciarSesion("usuario", ""), "Login sin password exitoso");
        }
    }

    @Test
    public void iniciarSesionSinNombre() {
        boolean result = sistema.registrar("usuario", "1234");
        assertTrue(result, "registro correcto");
        if(result){
            assertFalse(sistema.iniciarSesion("", "1234"), "Login sin nombre exitoso");
        }
    }

    @Test
    public void iniciarSesionSinDatos() {
        assertFalse(sistema.iniciarSesion("", ""), "Loginsin datos exitoso");
    }
}