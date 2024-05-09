package com.meethub.project;

/**
 * @author Ruben
 * @version 1.0
 * Clase que contiene los metodos para validar campos
 */
public class CamposValidate {

	
	/**
	 * Método que le pasan una contraseña y valida respecto a los requisitos que seguiere la página
	 * @param newPass contraseña que intenta meter el usuario
	 * @return True, si la contraseña es válida, False si la contraseña no es válida
	 */
    public static boolean validarNuevaContraseña(String newPass) {
        if (newPass == null || newPass.trim().isEmpty()) {
            return false; // Contraseña no debe estar vacía
        }

        // Verificación de longitud mínima y máxima
        boolean minCaracteres = newPass.length() >= 6;
        boolean maxCaracteres = newPass.length() <= 20;

        // Verificación de carácter especial
        boolean caracterEspecial = newPass.matches(".*[!@#$%^&*(),.?\":{}|<>].*");

        // Verificación de al menos un número y dos recomendados
        boolean alMenosUnNumero = newPass.matches(".*\\d.*");
        boolean alMenosDosNumeros = newPass.matches(".*(\\d.*\\d).*");

        // Verificación de mayúsculas y minúsculas
        boolean mayuscula = newPass.matches(".*[A-Z].*");
        boolean minuscula = newPass.matches(".*[a-z].*");

        return minCaracteres && maxCaracteres && caracterEspecial && alMenosUnNumero && alMenosDosNumeros && mayuscula && minuscula;
    }
    
    
    /**
     * Metodo que valida el nombre sobre todo para que no tenga número y minimo tenga dos caracteres
     * @param nombre nombre del usuario
     * @return True, si se ha validado correctamente o False si falla algo en la validación
     */
    public static boolean validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return false; // El nombre no debe estar vacío
        }
        boolean soloLetras = nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ]+");
        boolean minCaracteres = nombre.length() >= 2;
        return soloLetras && minCaracteres;
    }

    
    /**
     * Metodo que valida los apellidos del usuario
     * @param apellidos apellidos del usuario
     * @return True, si la validación ha sido correcta o False, si la validación ha fallado
     */
    public static boolean validarApellidos(String apellidos) {
        if (apellidos == null || apellidos.trim().isEmpty()) {
            return false; // Los apellidos no deben estar vacíos
        }
        boolean soloLetrasYEspacios = apellidos.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+");
        return soloLetrasYEspacios;
    }
    
}

