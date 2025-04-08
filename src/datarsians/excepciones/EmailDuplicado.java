package datarsians.excepciones;

public class EmailDuplicado extends Exception {
    public EmailDuplicado(String mensaje) {
        super (mensaje);
    }
}
