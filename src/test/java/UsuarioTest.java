import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {
    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario("Jose Bedoya", "Calle 123", "1", "joseM@uniquindio.edu.co", "programacion123", true);
    }

    @Test
    public void testGetters() {
        assertEquals("Jose Bedoya", usuario.getNombre());
        assertEquals("Calle 123", usuario.getDireccion());
        assertEquals("1", usuario.getId());
        assertEquals("joseM@uniquindio.edu.co", usuario.getCorreo());
        assertEquals("programacion123", usuario.getContraseña());
    }

    @Test
    public void testSetters() {
        usuario.setNombre("Luisa Gomez");
        usuario.setDireccion("Avenida 456");
        usuario.setId("2");
        usuario.setCorreo("luisa@gmail..com");
        usuario.setContraseña("luisa123");

        assertEquals("Luisa Gomez", usuario.getNombre());
        assertEquals("Avenida 456", usuario.getDireccion());
        assertEquals("2", usuario.getId());
        assertEquals("luisa@gmail..com", usuario.getCorreo());
        assertEquals("luisa123", usuario.getContraseña());
    }
}
