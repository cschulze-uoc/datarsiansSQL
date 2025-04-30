package datarsians.excepciones;

public class NifDuplicado extends RuntimeException {
    public NifDuplicado(String mensaje) {
        super(mensaje);
    }
}
