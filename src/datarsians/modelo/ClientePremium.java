package datarsians.modelo;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("PREMIUM")
public class ClientePremium extends Cliente {

    public ClientePremium() {}

    public ClientePremium(String nombre, String domicilio, String nif, String email) {
        super(nombre, domicilio, nif, email);
    }

    @Override
    public double obtenerDescuentoEnvio() {
        return 0.20; // 20% de descuento en los gastos de envío
    }

    @Override
    public double obtenerCuotaAnual() {
        return 30; // Paga una cuota anual de 30 euros
    }

    @Override
    public String toString() {
        return "  Cliente Premium\n" +
                "   Nombre      : " + getNombre() + "\n" +
                "   Domicilio   : " + getDomicilio() + "\n" +
                "   NIF         : " + getNif() + "\n" +
                "   Email       : " + getEmail() + "\n" +
                "   Cuota Anual : " + obtenerCuotaAnual() + "\n" +
                "   Descuento Envío: " + obtenerDescuentoEnvio() + "\n";
    }
}