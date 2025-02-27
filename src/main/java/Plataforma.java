import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Plataforma {
    private String nombre;
    private static List<Usuario> listaUsuarios;
    private List<Billetera> listaBilleteras;

    public Plataforma () {
        this.nombre = nombre;
        this.listaUsuarios = new ArrayList<>();
        this.listaBilleteras = new ArrayList<>();
    }

    public static List<Usuario> getUsuarios() {
        if (listaUsuarios == null) {
            listaUsuarios = new ArrayList<>(); // ✅ Evita que sea null
        }
        return listaUsuarios;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public static void registrarUsuario(Usuario usuario) throws Exception {
        if (usuario == null) {
            throw new Exception("El usuario no puede ser nulo");
        }

        if(obtenerUsuario(usuario.getId()) != null) {
            throw new Exception("El usuario ya existe");
        }

        listaUsuarios.add(usuario);
        System.out.println("Usuario registrado correctamente.");
    }

    public static Usuario obtenerUsuario(String id) {
        return listaUsuarios.stream().filter(usuario -> usuario.getId().equals(id)).findFirst().orElse(null);
    }

    public Billetera obtenerBilletera(String numero) {
        return listaBilleteras.stream().filter(billetera -> billetera.getNumero().equals(numero)).findFirst().orElse(null);
    }

    public void realizarTransaccion(String numeroOrigen, String numeroDestino, double monto, Categoria categoria) throws Exception {
        Billetera origen = obtenerBilletera(numeroOrigen);
        Billetera destino = obtenerBilletera(numeroDestino);

        if (origen == null || destino == null) {
            throw new Exception("Una de las billeteras no existe");
        }

        // Llamar al método de la billetera, que ya maneja la lógica de la transacción
        origen.realizarTransaccion(new Transaccion(
                UUID.randomUUID().toString(),
                monto,
                origen,
                destino,
                categoria.toString(),
                LocalDateTime.now()
        ));
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


    public Billetera creacionBilletera() {
        if (listaBilleteras == null) { // Evita NullPointerException
            listaBilleteras = new ArrayList<>();
        }
        Billetera billetera = new Billetera(generarNumeroUnico());
        listaBilleteras.add(billetera);
        return billetera;
    }

    public static long generarNumeroUnico() {
        String uuid = UUID.randomUUID().toString().replaceAll("[^0-9]", "");
        while (uuid.length() < 10) {  // Evita errores de longitud
            uuid += "0";
        }
        return Long.parseLong(uuid.substring(0, 10));
    }



    public boolean verificarNumero (String numero) throws Exception {
        if (numero == null || numero.isEmpty()) {
            throw new Exception("El número no puede estar vacío");
        }
        for (Billetera billetera : listaBilleteras) {
            if (billetera.toString().equals(numero)) return true;
        }
        return false;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public List<Billetera> getListaBilleteras() {
        return listaBilleteras;
    }

    public void setListaBilleteras(List<Billetera> listaBilleteras) {
        this.listaBilleteras = listaBilleteras;
    }
}

