// Funcion para cuando el documento haya cargado correctamente
$(document).ready(function() {
    // Evento para el boton de editar de cada fila para cuando se haga clic en el 
  $('.editBtn').click(function() {
    // Saco el elemento padre
    var row = $(this).closest('tr');
    var id = $(this).data('id'); // ID de la fila
    var name = row.find('td:nth-child(2)').text();
    var description = row.find('td:nth-child(3)').text();
    var dateTime = row.find('td:nth-child(4)').text().split('-'); // Dividir la fecha y la hora
    var date = convertDateFormat(dateTime[0]); // Fecha
    var time = dateTime[1]; // Hora
    var link = row.find('td:nth-child(5) a').attr('href');
    // Setear los valores en el modal
    $('#meetingName').val(name);
    $('#meetingDesc').val(description);
    $('#meetingDate').val(date);
    $('#meetingTime').val(time);
    $('#meetingLink').val(link);
  });
});

// Funcion para convertir la fecha en formato YYYY-MM-DD
function convertDateFormat(dateStr) {
    var parts = dateStr.split('/'); // Divide la fecha basándose en "/"
    // Devuelvo el string con el formato que necesito para la input date
    return parts[2] + '-' + parts[1] + '-' + parts[0]; 
}
