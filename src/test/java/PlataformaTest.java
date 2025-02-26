import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

class PlataformaTest {
    private Plataforma plataforma;
    private Usuario usuario;
    private Billetera billetera;

    @BeforeEach
    void setUp() {
        List<Usuario> usuarios = new ArrayList<>();
        List<Billetera> billeteras = new ArrayList<>();
        plataforma = new Plataforma("MiPlataforma", usuarios, billeteras);
        usuario = new Usuario("123", "correo@test.com", "Calle 123");
    }

    @Test
    void testRegistrarUsuario() throws Exception {
        plataforma.registrarUsuario(usuario);
        assertTrue(plataforma.getListaUsuarios().contains(usuario));
    }

    @Test
    void testRegistrarUsuarioNulo() {
        Exception exception = assertThrows(Exception.class, () -> plataforma.registrarUsuario(null));
        assertEquals("El usuario no puede ser nulo", exception.getMessage());
    }

    @Test
    void testActualizacionDatos() throws Exception {
        plataforma.registrarUsuario(usuario);
        plataforma.actualizacionDatos("123", "jose@gmail.com", "Calle 456");
        assertEquals("jose@gmail.com", usuario.getCorreo());
        assertEquals("Calle 456", usuario.getDireccion());
    }

    @Test
    void testActualizacionDatosUsuarioNoEncontrado() {
        Exception exception = assertThrows(Exception.class, () -> plataforma.actualizacionDatos("999", "nuevo@test.com", "Nueva Calle 456"));
        assertEquals("Usuario no encontrado", exception.getMessage());
    }

    @Test
    void testEliminacionUsuario() throws Exception {
        plataforma.registrarUsuario(usuario);
        plataforma.eliminacionUsuario("123");
        assertFalse(plataforma.getListaUsuarios().contains(usuario));
    }

    @Test
    void testEliminacionUsuarioNoEncontrado() {
        Exception exception = assertThrows(Exception.class, () -> plataforma.eliminacionUsuario("999"));
        assertEquals("Usuario no encontrado", exception.getMessage());
    }

    @Test
    void testCreacionBilletera() {
        billetera = plataforma.creacionBilletera();
        assertTrue(plataforma.getListaBilleteras().contains(billetera));
    }

    @Test
    void testVerificarNumeroBilletera() throws Exception {
        billetera = plataforma.creacionBilletera();
        assertTrue(plataforma.verificarNumero(billetera.toString()));
    }

    @Test
    void testVerificarNumeroBilleteraNoEncontrado() throws Exception {
        assertFalse(plataforma.verificarNumero("9999999999"));
    }
}
