public class Usuario {
    private String nombreUsuario;
    private String contrasena;

    public Usuario(String nombreUsuario, String contrasena) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    @Override
    public String toString() {
        return nombreUsuario + "," + contrasena;
    }

    public static Usuario fromString(String line) {
        String[] parts = line.split(",");
        if (parts.length == 2) {
            return new Usuario(parts[0], parts[1]);
        }
        return null;
    }
}

