// En este archivo irán todas la validaciones necesarias para los formularios

// Funcion para validar el Login
function validarLogin(event){

    // Elementos del login
    const emailL = document.getElementById("emailL");
    const passL = document.getElementById("passwordL");

    // Validamos que los campos no sean vacíos
    if(emailL && passL){
        alert("Validar todos los datos");
        event.preventDefault();
    }else{
        event.preventDefault();
    }

    
}