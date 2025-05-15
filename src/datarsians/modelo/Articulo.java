package datarsians.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Articulo")
public class Articulo {
    @Id
    @Column(name = "codigo")
    private String codigo;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "precio_venta", nullable = false)
    private double precioVenta;

    @Column(name = "gastos_envio", nullable = false)
    private double gastosEnvio;

    @Column(name = "tiempo_preparacion", nullable = false)
    private int tiempoPreparacion;

    public Articulo() {}

    public Articulo(String codigo, String descripcion, double precioVenta, double gastosEnvio, int tiempoPreparacion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precioVenta = precioVenta;
        this.gastosEnvio = gastosEnvio;
        this.tiempoPreparacion = tiempoPreparacion;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public double getGastosEnvio() {
        return gastosEnvio;
    }

    public int getTiempoPreparacion() {
        return tiempoPreparacion;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public void setGastosEnvio(double gastosEnvio) {
        this.gastosEnvio = gastosEnvio;
    }

    public void setTiempoPreparacion(int tiempoPreparacion) {
        this.tiempoPreparacion = tiempoPreparacion;
    }

    @Override
    public String toString() {
        return  "Artículo           : " + codigo +"\n" +
                "   Descripción     : " + descripcion + "\n" +
                "   Precio Venta    : " + String.format("%.2f", precioVenta) + "€\n" +
                "   Gastos Envío    : " + String.format("%.2f", gastosEnvio) + "€\n" +
                "   Tiempo Prep.    : " + tiempoPreparacion + " min";
    }
}