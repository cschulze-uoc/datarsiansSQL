package datarsians.modelo;

import java.time.Duration;
import java.time.LocalDateTime;

public class Pedido {
    private static int contadorPedidos = 1;
    private int numeroPedido;
    private Cliente cliente;
    private Articulo articulo;
    private int cantidad;
    private LocalDateTime fechaHoraPedido;

    public Pedido(Cliente cliente, Articulo articulo, int cantidad) {
        this.numeroPedido = contadorPedidos++;
        this.cliente = cliente;
        this.articulo = articulo;
        this.cantidad = cantidad;
        this.fechaHoraPedido =  LocalDateTime.now();
    }


    public static void setContadorPedidos(int contadorPedidos) {
        Pedido.contadorPedidos = contadorPedidos;
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

    public static int getContadorPedidos() {
        return contadorPedidos;
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
        long tiempoTranscurrido = Duration.between(fechaHoraPedido.toLocalTime(), LocalDateTime.now()).toMinutes();
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