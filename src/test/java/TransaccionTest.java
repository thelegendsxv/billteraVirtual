import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TransaccionTest {
    @Test
    public void testCrearTransaccion() {
        Transaccion transaccion = new Transaccion();
        transaccion.setMonto(100);
        transaccion.setTipo("ingreso");
        assertEquals(100, transaccion.getMonto());
        assertEquals("ingreso", transaccion.getTipo());
    }
}
