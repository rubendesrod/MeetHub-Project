package com.meethub.project;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    /**
     * Hashea una contraseña utilizando BCrypt.
     * @param contrasenaPlana La contraseña en texto plano.
     * @return La contraseña hasheada.
     */
    public static String hashPassword(String contrasenaPlana) {
        return BCrypt.hashpw(contrasenaPlana, BCrypt.gensalt());
    }

    /**
     * Verifica una contraseña en texto plano contra una hasheada.
     * @param contrasenaPlana La contraseña en texto plano.
     * @param contrasenaHasheada La contraseña hasheada almacenada.
     * @return true si las contraseñas coinciden, false en caso contrario.
     */
    public static boolean verificarPassword(String contrasenaPlana, String contrasenaHasheada) {
        return BCrypt.checkpw(contrasenaPlana, contrasenaHasheada);
    }
}
