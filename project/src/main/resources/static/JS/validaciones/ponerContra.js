document.addEventListener("DOMContentLoaded", function() {
    const passwordInput = document.getElementById("password");
    const confirmPasswordInput = document.getElementById("confirmPassword");
    const submitButton = document.getElementById("btnCrear");

    function validarContraseñas() {
        const passwordValue = passwordInput.value.trim();
        const confirmPasswordValue = confirmPasswordInput.value.trim();

        // Verificar que las contraseñas no están vacías
        if (passwordValue === '' || confirmPasswordValue === '') {
            submitButton.disabled = true;
            return;
        }

        // Verificación de longitud mínima
        const minCaracteres = passwordValue.length >= 6;

        // Verificación de carácter especial
        const caracterEspecial = /[!@#$%^&*(),.?":{}|<>]/.test(passwordValue);

        // Verificación de al menos un número
        const alMenosUnNumero = /\d/.test(passwordValue);

        // Validar que las contraseñas coincidan
        const contraseñasCoinciden = passwordValue === confirmPasswordValue;

        // Si todas las validaciones son correctas, habilitar el botón
        if (minCaracteres && caracterEspecial && alMenosUnNumero && contraseñasCoinciden) {
            submitButton.disabled = false;
        } else {
            submitButton.disabled = true;
        }
    }

    passwordInput.addEventListener("input", validarContraseñas);
    confirmPasswordInput.addEventListener("input", validarContraseñas);
});
