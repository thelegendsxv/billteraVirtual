import java.util.List;

public class Plataforma {
    private String nombre;
    private List<Usuario> listaUsuarios;
    private List<Billetera> listaBilleteras;

    public Plataforma(String nombre, List<Usuario> listaUsuarios, List<Billetera> listaBilleteras) {
        this.nombre = nombre;
        this.listaUsuarios = listaUsuarios;
        this.listaBilleteras = listaBilleteras;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void registrarUsuario(Usuario usuario) throws Exception {
        if (usuario == null) {
            throw new Exception("El usuario no puede ser nulo");
        }
        listaUsuarios.add(usuario);
        System.out.println("Usuario registrado correctamente.");
    }

    public void actualizacionDatos(String id, String nuevoCorreo, String nuevaDireccion) throws Exception {
        if (id == null || id.isEmpty()) {
            throw new Exception("El ID no puede estar vacío");
        }
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getId().equals(id)) {
                usuario.setCorreo(nuevoCorreo);
                usuario.setDireccion(nuevaDireccion);
                System.out.println("Datos actualizados correctamente.");
                return;
            }
        }
        throw new Exception("Usuario no encontrado");
    }

    public void eliminacionUsuario(String id) throws Exception {
        if (id == null || id.isEmpty()) {
            throw new Exception("El ID no puede estar vacío");
        }
        for (int i = 0; i < listaUsuarios.size(); i++) {
            if (listaUsuarios.get(i).getId().equals(id)) {
                listaUsuarios.remove(i);
                System.out.println("Usuario eliminado correctamente.");
                return;
            }
        }
        throw new Exception("Usuario no encontrado");
    }

    public Billetera creacionBilletera() throws Exception {
        Billetera billetera = new Billetera();
        listaBilleteras.add(billetera);
        return billetera;
    }

    public boolean verificarNumero(String numero) throws Exception {
        if (numero == null || numero.isEmpty()) {
            throw new Exception("El número no puede estar vacío");
        }
        for (Billetera billetera : listaBilleteras) {
            if (billetera.toString().equals(numero)) return true;
        }
        return false;
    }
}
