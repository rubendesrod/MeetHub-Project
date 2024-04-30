document.addEventListener("DOMContentLoaded", function () {
  var calendarEl = document.getElementById("calendar");
  var calendar = new FullCalendar.Calendar(calendarEl, {
    initialView: "dayGridMonth",
    locale: "es",
    firstDay: 1, // para que el calendario empiece en lunes
    headerToolbar: {
      left: "prev,next",
      center: "title",
      right: "dayGridMonth,dayGridWeek,dayGridDay",
    },
    buttonText: {
      today: "Hoy",
      month: "Mes",
      week: "Semana",
      day: "Día",
    },
    events: [
      {
        title: "Evento 1",
        start: "2024-04-01",
        description: "hola",
        color: "#f4c22b"
      },
      {
        title: "Evento 2",
        start: "2024-04-05",
        end: "2024-04-07",
        color: "#f4c22b",
        description: "ruben no sabe que mas poner"
      },
    ],
    // Evento para cuando el ratón pase por enicma de uno de los eventos del calendario
    eventMouseEnter: function (info) {
      tippy(info.el, {
        content: info.event.extendedProps.description,
        allowHTML: true,
        arrow: true,
        theme: "light-border", // Usa un tema predefinido o crea uno personalizado
      });
    },
    // Evento para cuando se haga clic en una día
    dateClick: function(info) {
      // Llenar el modal con información o hacer cualquier preparación necesaria
      document.getElementById('modalLabel').innerHTML = 'Agregar Evento [' + info.dateStr +']';

      // Usar Bootstrap para abrir el modal
      var modal = new bootstrap.Modal(document.getElementById('dayClickModal'));
      modal.show();
    }
  });
  calendar.render();
});
