document.addEventListener("DOMContentLoaded", function() {
    const passwordInput = document.getElementById("password");
    const confirmPasswordInput = document.getElementById("confirmPassword");
    const submitButton = document.getElementById("btnCrear");
    // pongo el boton deshabilitado desde un principio
	submitButton.disabled = false;
    function validarContraseñas() {
        const passwordValue = passwordInput.value.trim();
        const confirmPasswordValue = confirmPasswordInput.value.trim();

        if (passwordValue === confirmPasswordValue && passwordValue !== '') {
            submitButton.disabled = false;
        } else {
            submitButton.disabled = true;
        }
    }

    passwordInput.addEventListener("input", validarContraseñas);
    confirmPasswordInput.addEventListener("input", validarContraseñas);
});