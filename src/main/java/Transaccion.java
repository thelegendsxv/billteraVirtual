import java.time.LocalDateTime;

public class Transaccion {
    private String id;
    private double monto;
    private enum categoria {}
    private Billetera origen;
    private Billetera destino;
    private String tipo;  // Se necesita para identificar si es "gasto" o "ingreso"
    private LocalDateTime fecha;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Billetera getOrigen() {
        return origen;
    }

    public void setOrigen(Billetera origen) {
        this.origen = origen;
    }

    public Billetera getDestino() {
        return destino;
    }

    public void setDestino(Billetera destino) {
        this.destino = destino;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Transaccion(String id, double monto, Billetera origen, Billetera destino, String tipo, LocalDateTime fecha) {
        this.id = id;
        this.monto = monto;
        this.origen = origen;
        this.destino = destino;
        this.tipo = tipo;
        this.fecha = fecha;
    }
}


