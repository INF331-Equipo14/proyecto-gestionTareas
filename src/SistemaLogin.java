import java.io.*;
import java.util.*;

public class SistemaLogin {
    private static final String ARCHIVO_USUARIOS = "src/usuarios.txt";
    private List<Usuario> usuarios;

    public SistemaLogin() {
        usuarios = new ArrayList<>();
        cargarUsuarios();
    }

    private void cargarUsuarios() {
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_USUARIOS))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                Usuario usuario = Usuario.fromString(linea);
                if (usuario != null) {
                    usuarios.add(usuario);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar los usuarios: " + e.getMessage());
        }
    }

    private void guardarUsuario(Usuario usuario) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_USUARIOS, true))) {
            bw.write(usuario.toString());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al guardar el usuario: " + e.getMessage());
        }
    }

    public boolean registrar(String nombreUsuario, String contrasena) {
        if (buscarUsuario(nombreUsuario) != null) {
            System.out.println("El usuario ya existe.");
            return false;
        }
        Usuario nuevoUsuario = new Usuario(nombreUsuario, contrasena);
        usuarios.add(nuevoUsuario);
        guardarUsuario(nuevoUsuario);
        System.out.println("Usuario registrado con éxito.");
        return true;
    }

    public boolean iniciarSesion(String nombreUsuario, String contrasena) {
        Usuario usuario = buscarUsuario(nombreUsuario);
        if (usuario != null && usuario.getContrasena().equals(contrasena)) {
            System.out.println("Login exitoso.");
            return true;
        }
        System.out.println("Nombre de usuario o contraseña incorrectos.");
        return false;
    }

    private Usuario buscarUsuario(String nombreUsuario) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNombreUsuario().equals(nombreUsuario)) {
                return usuario;
            }
        }
        return null;
    }
}
