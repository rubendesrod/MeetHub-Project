document.addEventListener("DOMContentLoaded", function () {
  var calendarEl = document.getElementById("calendar");
  var calendar = new FullCalendar.Calendar(calendarEl, {
    initialView: "dayGridMonth",
    locale: "es",
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
        color: "#ff9f89",
        description: "hola",
      }, // color de fondo
      {
        title: "Evento 2",
        start: "2024-04-05",
        end: "2024-04-07",
        color: "#f4c22b",
      },
    ],

    eventMouseEnter: function (info) {
      tippy(info.el, {
        content: info.event.extendedProps.description,
        allowHTML: true,
        arrow: true,
        theme: "light-border", // Usa un tema predefinido o crea uno personalizado
      });
    },
  });
  calendar.render();
});
