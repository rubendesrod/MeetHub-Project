// Evento para cuando se hace clic en el boton añadir
document.getElementById('botonAnadirIntegrante').addEventListener('click', function() {
    var emailInput = document.getElementById('emailInput');
    var email = emailInput.value;
    var emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    var errorEmailVacio = document.getElementById('errorEmailVacio');
    var errorEmailFormato = document.getElementById('errorEmailFormato');
    var errorEmailRepetido = document.getElementById('errorEmailRepetido');
    var listaEmail = document.getElementById('listaEmail'); // Input donde se añadirán los emails separados por comas

    if(email) {
        if(emailPattern.test(email)) {
            var exists = Array.from(document.querySelectorAll('#integrantesList .email-text'))
                              .some(span => span.textContent.trim() === email.trim());

            if(!exists) {
                resetErrors();
                emailInput.classList.add('is-valid');

                var listItem = document.createElement('li');
                listItem.className = 'list-group-item';

                var emailText = document.createElement('span');
                emailText.textContent = email;
                emailText.className = 'email-text';
                emailText.style.marginLeft = '10px';
                
                var deleteBtn = document.createElement('span');
                deleteBtn.textContent = '×';
                deleteBtn.className = 'delete-btn';
                deleteBtn.style.cursor = 'pointer';
                deleteBtn.onclick = function() {
                    this.parentElement.remove();
                    updateEmailList(); // Llamar a la función cada vez que se elimina un correo
                };

                listItem.appendChild(deleteBtn);
                listItem.appendChild(emailText);
                document.getElementById('integrantesList').appendChild(listItem);

                emailInput.value = ''; // Limpiar el input después de añadir
                updateEmailList(); // Actualizar la lista de correos cada vez que se añade un nuevo correo
            } else {
                displayError(errorEmailRepetido, 'El email ya ha sido añadido a la lista.');
            }
        } else {
            displayError(errorEmailFormato, 'Formato de email no válido.');
        }
    } else {
        displayError(errorEmailVacio, 'El campo de email no puede estar vacío.');
    }
});

function updateEmailList() {
    var allEmails = Array.from(document.querySelectorAll('#integrantesList .email-text'))
                         .map(span => span.textContent.trim());
    document.getElementById('listaEmail').value = allEmails.join(',');
}


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

// Funcion para quitar todos mensajes de error
function resetErrors() {
    var errors = document.querySelectorAll('.error-message');
    errors.forEach(function(error) {
        error.style.display = 'none';
    });
    emailInput.classList.remove('is-invalid', 'is-valid');
}

// Funcion para desplegar el mensaje y el div de error que quiero mostrar
function displayError(element, message) {
    resetErrors();
    emailInput.classList.add('is-invalid');
    element.textContent = message;
    element.style.display = 'block';
}