import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;


class BilleteraTest {
    private Billetera billetera;

    @BeforeEach
    void setUp() {
        billetera = new Billetera(1000);
    }

    @Test
    void testConsultaSaldo() throws Exception {
        billetera.consultaSaldo();
        assertEquals(1000, billetera.getSaldo());
    }

    @Test
    void testConsultaSaldoInvalido() {
        billetera.setSaldo(-10);
        Exception exception = assertThrows(Exception.class, () -> billetera.consultaSaldo());
        assertEquals("Saldo inválido", exception.getMessage());
    }

    @Test
    void testConsultarTransacciones() throws Exception {
        assertNotNull(billetera.consultarTransacciones());
    }

    @Test
    void testRealizarTransaccion() throws Exception {
        Transaccion ingreso = new Transaccion("ingreso", 500);
        billetera.realizarTransaccion(ingreso);
        assertEquals(1500, billetera.getSaldo());
    }

    @Test
    void testRealizarTransaccionSaldoInsuficiente() {
        Transaccion gasto = new Transaccion("gasto", 2000);
        Exception exception = assertThrows(Exception.class, () -> billetera.realizarTransaccion(gasto));
        assertEquals("Saldo insuficiente para realizar la transacción", exception.getMessage());
    }

    @Test
    void testObtenerPorcentajeGastosIngresos() throws Exception {
        billetera.realizarTransaccion(new Transaccion("ingreso", 1000));
        billetera.realizarTransaccion(new Transaccion("gasto", 500));
        assertEquals(50.0, billetera.obtenerPorcentajeGastosIngresos());
    }

    @Test
    void testObtenerPorcentajeGastosIngresosSinIngresos() {
        Exception exception = assertThrows(Exception.class, () -> billetera.obtenerPorcentajeGastosIngresos());
        assertEquals("No hay ingresos registrados, no se puede calcular el porcentaje", exception.getMessage());
    }
}
