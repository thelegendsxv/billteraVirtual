import java.util.ArrayList;
import java.util.List;

public class Billetera {
    private String numero;
    private double saldo;
    private List<Transaccion> transacciones;
    private Usuario usuario;

    public Billetera(double saldo) {
        this.numero = numero;
        this.saldo = saldo;
        this.usuario = usuario;
        this.transacciones = new ArrayList<>();
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void consultaSaldo() throws Exception {
        if (saldo < 0) {
            throw new Exception("Saldo inválido");
        }
        System.out.println("Saldo actual: " + saldo);
    }

    public List<Transaccion> consultarTransacciones() throws Exception {
        if (transacciones == null) {
            throw new Exception("Lista de transacciones no inicializada");
        }
        return transacciones;
    }

    public void realizarTransaccion(Transaccion transaccion) throws Exception {
        if (transaccion == null) {
            throw new Exception("Transacción no puede ser nula");
        }
        if (transaccion.getTipo().equalsIgnoreCase("gasto") && transaccion.getMonto() > saldo) {
            throw new Exception("Saldo insuficiente para realizar la transacción");
        }


        Billetera origen = transaccion.getOrigen();
        Billetera destino = transaccion.getOrigen();
        //Origen
        origen.setSaldo(origen.getSaldo() - transaccion.getMonto());
        destino.setSaldo(destino.getSaldo() + transaccion.getMonto());

        origen.getTransacciones().add(transaccion);
        destino.getTransacciones().add(transaccion);
        System.out.println("Transacción realizada con éxito.");
    }

    public double obtenerPorcentajeGastosIngresos() throws Exception {
        if (transacciones == null) {
            throw new Exception("Lista de transacciones no inicializada");
        }
        double totalGastos = 0;
        double totalIngresos = 0;

        for (Transaccion t : transacciones) {
            if (t.getTipo().equalsIgnoreCase("gasto")) {
                totalGastos += t.getMonto();
            } else {
                totalIngresos += t.getMonto();
            }
        }

        if (totalIngresos == 0) {
            throw new Exception("No hay ingresos registrados, no se puede calcular el porcentaje");
        }

        return (totalGastos / totalIngresos) * 100;
    }

    public List<Transaccion> getTransacciones() {
        return transacciones;
    }

    public void setTransacciones(List<Transaccion> transacciones) {
        this.transacciones = transacciones;
    }
}
