package datarsians.excepciones;

public class EmailNoValido extends RuntimeException {
    public EmailNoValido(String mensaje) {
        super(mensaje);
    }

}
