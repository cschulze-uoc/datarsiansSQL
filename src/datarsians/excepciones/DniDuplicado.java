package datarsians.excepciones;

public class DniDuplicado extends RuntimeException {
    public DniDuplicado(String mensaje) {
        super(mensaje);
    }
}
