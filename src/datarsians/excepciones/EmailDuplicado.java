package datarsians.excepciones;

public class EmailDuplicado extends RuntimeException {
    public EmailDuplicado(String mensaje) {
        super (mensaje);
    }
}
