 // Obtener referencias a los elementos select
 var diaSelect = document.getElementById("dia");
 var mesSelect = document.getElementById("mes");
 var anyoSelect = document.getElementById("anyo");

 // Función para cargar los días
 function cargarDias() {
     for (var i = 1; i <= 31; i++) {
         var option = document.createElement("option");
         option.text = i;
         option.value = i;
         diaSelect.add(option);
     }
 }

 // Función para cargar los meses
 function cargarMeses() {
     var meses = [
         "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio",
         "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
     ];
     for (var i = 0; i < meses.length; i++) {
         var option = document.createElement("option");
         option.text = meses[i];
         option.value = i + 1;
         mesSelect.add(option);
     }
 }

 // Función para cargar los años hasta 1940
 function cargarAnyos() {
     var year = new Date().getFullYear();
     for (var i = year; i >= 1940; i--) {
         var option = document.createElement("option");
         option.text = i;
         option.value = i;
         anyoSelect.add(option);
     }
 }

 // LLamada a la funciones para cargar los datos
 cargarDias();
 cargarMeses();
 cargarAnyos();