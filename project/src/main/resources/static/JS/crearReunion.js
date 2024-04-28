// Evento para cuando se hace clic en el boton añadir
document.getElementById('botonAnadirIntegrante').addEventListener('click', function() {
    var email = document.getElementById('emailInput').value; // Obtener el correo del input
    var emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // Expresión regular para validar el formato del email

    if(email) { // Verificar que el correo no esté vacío
        if(emailPattern.test(email)){ // Verificar que el formato del correo es válido
            errorEmailVacio.style.display = 'none';
            errorEmailFormato.style.display = 'none';
            emailInput.classList.remove('is-invalid'); // quito la clase en rojo del input
            emailInput.classList.add('is-valid') // añado una clase en verde al input
            // se crea un elemto li para el UL
            var listItem = document.createElement('li');
            listItem.className = 'list-group-item';

            var deleteBtn = document.createElement('span');
            deleteBtn.textContent = '×'; // Usar el símbolo de "X"
            deleteBtn.className = 'delete-btn';
            deleteBtn.onclick = function() {
                this.parentElement.remove();
            };
            // email
            var emailText = document.createElement('span');
            emailText.textContent = email;
            emailText.style.marginLeft = '10px'; // Añadir algo de espacio entre la "X" y el correo

            listItem.appendChild(deleteBtn); // Añadir primero el botón de eliminar
            listItem.appendChild(emailText); // Luego añadir el texto del correo
            document.getElementById('integrantesList').appendChild(listItem); // Añadir el correo a la lista

            emailInput.value = ''; // Limpiar el input
        }else{
            errorEmailVacio.style.display = 'none';
            emailInput.classList.add('is-invalid'); // añado la clase is-invalid al input
            errorEmailFormato.style.display = 'block'; // Hago el display del mensaje de textp
        }
    } else {
        errorEmailFormato.style.display = 'none';
        emailInput.classList.add('is-invalid'); // añado la clase is-invalid al input
        errorEmailVacio.style.display = 'block'; // Hago el display del mensaje de textp
     }
});

// Evento para cuando se hace clic en el boton de reset
document.getElementById('btnResetar').addEventListener('click', function () {
    // Obtengo todos los inputs que hay en el formulario
    var inputs = document.querySelectorAll("input");
    inputs.forEach(input => {
        // Seteo a null todos los inputs
        input.value = "";
    })
    // Borro las clase de valid e invalid al input del correo
    emailInput.classList.remove('is-invalid');
    emailInput.classList.remove('is-valid');
    // Pongo en display none todos los mensajes de error
    errorEmailVacio.style.display = 'none';
    errorEmailFormato.style.display = 'none';
    // Obtener el ul y borrar todos sus elementos li
    var listaIntegrantes = document.getElementById('integrantesList');
    listaIntegrantes.innerHTML = ''; // Establecer el contenido del ul a una cadena vacía para eliminar todos los li
})