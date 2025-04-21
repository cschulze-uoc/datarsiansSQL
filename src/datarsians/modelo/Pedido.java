package datarsians.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "Pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numero_pedido")
    private int numeroPedido;

    @ManyToOne
    @JoinColumn(name = "email_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "codigo_articulo", nullable = false)
    private Articulo articulo;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @Column(name = "fecha_pedido", nullable = false)
    private LocalDateTime fechaHoraPedido;

    public Pedido() {}
    public Pedido(Cliente cliente, Articulo articulo, int cantidad) {

        this.cliente = cliente;
        this.articulo = articulo;
        this.cantidad = cantidad;
        this.fechaHoraPedido =  LocalDateTime.now();
    }


    public void setNumeroPedido(int numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setFechaHoraPedido(LocalDateTime fechaHoraPedido) {
        this.fechaHoraPedido = fechaHoraPedido;
    }

    public int getNumeroPedido() {
        return numeroPedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public LocalDateTime getFechaHoraPedido() {
        return fechaHoraPedido;
    }

    public double calcularPrecioPedido() {
        double precioArticulo = articulo.getPrecioVenta();
        double gastosEnvio = articulo.getGastosEnvio();
        double total = (precioArticulo + gastosEnvio) * cantidad;
        if (cliente instanceof ClientePremium) {
            total *= (1 - cliente.obtenerDescuentoEnvio());
        }
        return total;
    }

    public boolean esCancelable() {
        long tiempoTranscurrido = Duration.between(fechaHoraPedido, LocalDateTime.now()).toMinutes();
        long tiempoLimite = articulo.getTiempoPreparacion();
        return tiempoTranscurrido <= tiempoLimite;
    }

    @Override
    public String toString() {
        return "\n  Pedido #" + numeroPedido + "\n" +
                "   Cliente         : " + cliente.getNombre() + " (" + cliente.getEmail() + ")\n" +
                "   Artículo        : " + articulo.getDescripcion() + "\n" +
                "   Código Artículo : " + articulo.getCodigo() + "\n" +
                "   Cantidad        : " + cantidad + "\n" +
                "   Fecha Pedido    : " + fechaHoraPedido + "\n" +
                "   Precio Total    : " + String.format("%.2f", calcularPrecioPedido()) + "€\n" +
                "   Cancelable      : " + (esCancelable() ? "Sí" : "No");
    }
}