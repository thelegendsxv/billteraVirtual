import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.util.UUID;

class PlataformaTest {
    private Plataforma plataforma;
    private Usuario usuario;
    private Billetera billetera;
    private Billetera billeteraDestino;

    @BeforeEach
    void setUp() {
        plataforma = new Plataforma();
        usuario = new Usuario("Jose", "calle 123", "123", "jose@gmail.com", "12345", true);
        billetera = new Billetera("9876543210", 1000.0, new ArrayList<>(), usuario);
        billeteraDestino = new Billetera("1234567890", 500.0, new ArrayList<>(), usuario);
    }

    // 1️⃣ Prueba para registrar usuario
    @Test
    public void registrarUsuarioTest() {

        Usuario usuario = new Usuario("Jose", "calle 123", "123", "jose@gmail.com", "12345", true);
        assertDoesNotThrow(() -> Plataforma.registrarUsuario(usuario));
        ArrayList<Usuario> usuarios = Plataforma.getUsuarios();
        assertNotNull(usuarios);
    }


    // 2️⃣ Prueba para actualizar datos de usuario
    @Test
    void actualizacionDatos_DeberiaLanzarExcepcion_CuandoIdEsVacio() {
        Exception exception = assertThrows(Exception.class, () -> {
            plataforma.actualizacionDatos("", "nuevoCorreo@test.com", "nuevaDireccion");
        });
        assertEquals("El ID no puede estar vacío", exception.getMessage());
    }

    // 3️⃣ Prueba para consultar saldo en billetera
    @Test
    void consultaSaldo_DeberiaLanzarExcepcion_CuandoSaldoEsNegativo() {
        billetera.setSaldo(-50.0);
        Exception exception = assertThrows(Exception.class, () -> {
            billetera.consultaSaldo();
        });
        assertEquals("Saldo inválido", exception.getMessage());
    }

    // 4️⃣ Prueba para consultar transacciones
    @Test
    void consultarTransacciones_DeberiaLanzarExcepcion_CuandoListaNoInicializada() {
        billetera.setTransacciones(null);
        Exception exception = assertThrows(Exception.class, () -> {
            billetera.consultarTransacciones();
        });
        assertEquals("Lista de transacciones no inicializada", exception.getMessage());
    }

    // 5️⃣ Prueba para realizar transacción cuando es nula
    @Test
    void realizarTransaccion_DeberiaLanzarExcepcion_CuandoTransaccionEsNula() {
        Exception exception = assertThrows(Exception.class, () -> {
            billetera.realizarTransaccion(null);
        });
        assertEquals("Transacción no puede ser nula", exception.getMessage());
    }

    // 6️⃣ Prueba para transacción con saldo insuficiente (incluyendo el costo de $200)
    @Test
    void realizarTransaccion_DeberiaLanzarExcepcion_CuandoSaldoInsuficiente() {
        Transaccion transaccion = new Transaccion("1", 900.0, billetera, billeteraDestino, "gasto", LocalDateTime.now());

        Exception exception = assertThrows(Exception.class, () -> {
            billetera.realizarTransaccion(transaccion);
        });
        assertEquals("Saldo insuficiente para realizar la transacción y cubrir el costo de $200.", exception.getMessage());
    }

    // 7️⃣ Prueba para realizar transacción exitosa
    @Test
    void realizarTransaccion_DeberiaReducirSaldoOrigenYAumentarDestino() throws Exception {
        Transaccion transaccion = new Transaccion("1", 700.0, billetera, billeteraDestino, "ingreso", LocalDateTime.now());

        billetera.realizarTransaccion(transaccion);

        assertEquals(100.0, billetera.getSaldo()); // 1000 - 700 - 200 = 100
        assertEquals(1200.0, billeteraDestino.getSaldo()); // 500 + 700 = 1200
    }

    // 8️⃣ Prueba para obtener porcentaje de gastos/ingresos sin ingresos
    @Test
    void obtenerPorcentajeGastosIngresos_DeberiaLanzarExcepcion_CuandoNoHayIngresos() {
        List<Transaccion> transacciones = new ArrayList<>();
        transacciones.add(new Transaccion("1", 100.0, billetera, billetera, "gasto", LocalDateTime.now()));
        billetera.setTransacciones(transacciones);

        Exception exception = assertThrows(Exception.class, () -> {
            billetera.obtenerPorcentajeGastosIngresos();
        });
        assertEquals("No hay ingresos registrados, no se puede calcular el porcentaje", exception.getMessage());
    }

    // 9️⃣ Prueba para obtener porcentaje de gastos/ingresos con datos válidos
    @Test
    void obtenerPorcentajeGastosIngresos_DeberiaCalcularCorrectamente() throws Exception {
        List<Transaccion> transacciones = new ArrayList<>();
        transacciones.add(new Transaccion("1", 100.0, billetera, billetera, "gasto", LocalDateTime.now()));
        transacciones.add(new Transaccion("2", 500.0, billetera, billetera, "ingreso", LocalDateTime.now()));
        billetera.setTransacciones(transacciones);

        double porcentaje = billetera.obtenerPorcentajeGastosIngresos();
        assertEquals(20.0, porcentaje, 0.1); // (100 / 500) * 100 = 20%
    }

    // 🔟 Prueba para eliminación de usuario
    @Test
    void eliminacionUsuario_DeberiaLanzarExcepcion_CuandoIdEsVacio() {
        Exception exception = assertThrows(Exception.class, () -> {
            plataforma.eliminacionUsuario("");
        });
        assertEquals("El ID no puede estar vacío", exception.getMessage());
    }

    // 1️⃣1️⃣ Prueba para crear billetera correctamente
    @Test
    void creacionBilletera_DeberiaAgregarNuevaBilletera() {
        int cantidadAntes = plataforma.getListaBilleteras().size();
        plataforma.creacionBilletera();
        int cantidadDespues = plataforma.getListaBilleteras().size();

        assertEquals(cantidadAntes + 1, cantidadDespues);
    }

    // 1️⃣2️⃣ Prueba para verificar número de billetera
    @Test
    void verificarNumero_DeberiaLanzarExcepcion_CuandoNumeroEsVacio() {
        Exception exception = assertThrows(Exception.class, () -> {
            plataforma.verificarNumero("");
        });
        assertEquals("El número no puede estar vacío", exception.getMessage());
    }

    // 1️⃣3️⃣ Prueba para obtener usuario cuando existe
    @Test
    void obtenerUsuario_DeberiaRetornarUsuario_CuandoExiste() throws Exception {
        plataforma.registrarUsuario(usuario);
        Usuario usuarioEncontrado = plataforma.obtenerUsuario(usuario.getId());

        assertNotNull(usuarioEncontrado);
        assertEquals(usuario.getId(), usuarioEncontrado.getId());
    }

    // 1️⃣4️⃣ Prueba para obtener usuario cuando no existe
    @Test
    void obtenerUsuario_DeberiaRetornarNull_CuandoNoExiste() {
        Usuario usuarioNoExistente = plataforma.obtenerUsuario("999");

        assertNull(usuarioNoExistente);
    }

    // 1️⃣5️⃣ Prueba para obtener billetera cuando existe
    @Test
    void obtenerBilletera_DeberiaRetornarBilletera_CuandoExiste() {
        plataforma.getListaBilleteras().add(billetera);
        Billetera billeteraEncontrada = plataforma.obtenerBilletera(billetera.getNumero());

        assertNotNull(billeteraEncontrada);
        assertEquals(billetera.getNumero(), billeteraEncontrada.getNumero());
    }

    // 1️⃣6️⃣ Prueba para obtener billetera cuando no existe
    @Test
    void obtenerBilletera_DeberiaRetornarNull_CuandoNoExiste() {
        Billetera billeteraNoExistente = plataforma.obtenerBilletera("0000000000");

        assertNull(billeteraNoExistente);
    }

    // 1️⃣7️⃣ Prueba para generar número único (valida que sean diferentes)
    @Test
    void generarNumeroUnico_DeberiaGenerarNumerosDiferentes() {
        long numero1 = Plataforma.generarNumeroUnico();
        long numero2 = Plataforma.generarNumeroUnico();

        assertNotEquals(numero1, numero2);
    }

}
