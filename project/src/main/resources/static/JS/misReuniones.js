$(document).ready(function() {
    $('.editBtn').click(function() {
        var row = $(this).closest('tr');
        var id = row.find('.reunionId').val();  // Extraer el ID desde el input oculto
        var invitados = row.find('.invitados').val();
        var name = row.find('td:nth-child(2)').text();
        var description = row.find('td:nth-child(3)').text();
        var dateTime = row.find('td:nth-child(4)').text().split('-');

        var date = convertDateFormat(dateTime[0].trim());
        var startTime = dateTime.length > 1 ? dateTime[1].trim() : '';
        var endTime = dateTime.length > 2 ? dateTime[2].trim() : '';
        var link = row.find('td:nth-child(5) a').attr('href');

        // Asignar los valores en el modal
        $('#meetingId').val(id);
        $('#meetingInvitados').val(invitados);
        $('#meetingName').val(name);
        $('#meetingDesc').val(description);
        $('#meetingDate').val(date);
        $('#meetingStart').val(startTime);
        $('#meetingEnd').val(endTime);
        $('#meetingLink').val(link);
        
        // Mostrar el modal
        $('#editModal').modal('show');
    });
});

function convertDateFormat(dateStr) {
    var parts = dateStr.split('/');
    return parts[2] + '-' + parts[1] + '-' + parts[0];
}
