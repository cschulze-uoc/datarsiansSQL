package datarsians.utils;

import datarsians.excepciones.EmailDuplicado;
import datarsians.excepciones.EmailNoValido;
import datarsians.excepciones.NifDuplicado;
import datarsians.excepciones.NifNoValido;

import java.util.List;

public class ClienteValidator {
    public static void validarEmail(String email, List<String> listaEmails) {
        if (!Validations.esEmailValido(email)) {
            throw new EmailNoValido("Error: El email ingresado no es válido.");
        }

        if (listaEmails != null && listaEmails.contains(email)) {
            throw new EmailDuplicado("Error: El email ya está registrado.");
        }
    }


    public static void validarNif(String nif, List<String> listaNifs) {

        if (!Validations.esNifValido(nif)) {
            throw new NifNoValido("Error: El NIF debe tener 9 caracteres.");
        }

        if (listaNifs != null && listaNifs.contains(nif)) {
            throw new NifDuplicado("Error: El NIF ingresado ya existe.");
        }
    }
}
