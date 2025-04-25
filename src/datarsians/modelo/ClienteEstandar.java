package datarsians.modelo;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ESTANDAR")
public class ClienteEstandar extends Cliente{

    public ClienteEstandar() {}

    public ClienteEstandar(String nombre, String domicilio, String nif, String email) {
        super(nombre, domicilio, nif, email);
    }

    @Override
    public double obtenerDescuentoEnvio() {
        return 0; // No tiene descuento
    }

    @Override
    public double obtenerCuotaAnual() {
        return 0; // No paga cuota
    }

    @Override
    public String toString() {
        return "  Cliente Estándar\n" +
                "   Nombre     : " + getNombre() + "\n" +
                "   Domicilio  : " + getDomicilio() + "\n" +
                "   NIF        : " + getNif() + "\n" +
                "   Email      : " + getEmail() + "\n" +
                "   Cuota Anual: 0€ (No aplica descuento en envío)";
    }
}
