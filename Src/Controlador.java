
import java.time.LocalDate;
import java.util.List;

public class Controlador {

    private Torneo torneo;
    private Vista vista;

    public Controlador() {
        torneo = new Torneo();
        vista = new Vista();
    }

    public void iniciar() {

        cargarDatosIniciales();

        int opcion = 0;

        while (opcion != 8) {

            opcion = vista.mostrarMenu();

            try {

                switch (opcion) {

                    case 1:
                        registrarEquipo();
                        break;

                    case 2:
                        crearPartido();
                        break;

                    case 3:
                        crearExhibicion();
                        break;

                    case 4:
                        programarEvento();
                        break;

                    case 5:
                        registrarResultado();
                        break;

                    case 6:
                        vista.mostrarTabla(torneo.obtenerTablaPosiciones());
                        break;

                    case 7:
                        vista.mostrarCalendario(torneo);
                        break;

                    case 8:
                        vista.mostrarMensaje("Gracias por utilizar ArenaGT.");
                        break;

                    default:
                        vista.mostrarMensaje("Opción no válida.");
                        break;
                }

            } catch (IllegalArgumentException | IllegalStateException e) {
                vista.mostrarMensaje("Error: " + e.getMessage());
            }
        }
    }

    private void cargarDatosIniciales() {

        torneo.registrarEquipo("FAR", "Faro", "Empresa A");
        torneo.registrarEquipo("LUN", "Luna", "Empresa B");
        torneo.registrarEquipo("SOL", "Sol", "Empresa A");
        torneo.registrarEquipo("ATL", "Atlas", "Empresa C");

        Equipo faro = torneo.buscarEquipo("FAR");
        Equipo luna = torneo.buscarEquipo("LUN");
        Equipo sol = torneo.buscarEquipo("SOL");
        Equipo atlas = torneo.buscarEquipo("ATL");

        FutbolSala partido1 = new FutbolSala(faro, luna);
        Baloncesto partido2 = new Baloncesto(sol, atlas);
        FutbolSala partido3 = new FutbolSala(faro, sol);
        Baloncesto partido4 = new Baloncesto(luna, atlas);

        torneo.agregarEvento(partido1);
        torneo.agregarEvento(partido2);
        torneo.agregarEvento(partido3);
        torneo.agregarEvento(partido4);

        torneo.programarEvento(
            partido1, LocalDate.of(2026, 11, 20), 9, "Cancha A"
        );

        torneo.programarEvento(
            partido2, LocalDate.of(2026, 11, 20), 10, "Cancha B"
        );

        torneo.programarEvento(
            partido3, LocalDate.of(2026, 11, 20), 11, "Cancha C"
        );

        torneo.programarEvento(
            partido4, LocalDate.of(2026, 11, 20), 12, "Cancha D"
        );

        torneo.registrarResultado(partido1, 2, 2);
        torneo.registrarResultado(partido2, 80, 75);

        Exhibicion exhibicion = new Exhibicion("Clínica de baloncesto");

        torneo.agregarEvento(exhibicion);

        torneo.programarEvento(
            exhibicion, LocalDate.of(2026, 11, 20), 13, "Cancha E"
        );
    }

    private void registrarEquipo() {

        String codigo = vista.leerTexto("Código del equipo:");

        if (codigo == null) return;

        String nombre = vista.leerTexto("Nombre del equipo:");

        if (nombre == null) return;

        String empresa = vista.leerTexto("Empresa representada:");

        if (empresa == null) return;

        torneo.registrarEquipo(codigo, nombre, empresa);

        vista.mostrarMensaje("Equipo registrado correctamente.");
    }

    private void crearPartido() {

        vista.mostrarMensaje("1. Fútbol sala\n2. Baloncesto");

        int deporte = vista.leerEntero("Seleccione el deporte:");

        if (deporte == -1) return;

        String codigoLocal = vista.leerTexto("Código del equipo local:");

        if (codigoLocal == null) return;

        String codigoVisitante = vista.leerTexto("Código del equipo visitante:");

        if (codigoVisitante == null) return;

        Equipo local = torneo.buscarEquipo(codigoLocal);
        Equipo visitante = torneo.buscarEquipo(codigoVisitante);

        if (local == null || visitante == null) {
            throw new IllegalArgumentException(
                "Uno o ambos equipos no existen."
            );
        }

        Partido partido;

        if (deporte == 1) {
            partido = new FutbolSala(local, visitante);

        } else if (deporte == 2) {
            partido = new Baloncesto(local, visitante);

        } else {
            throw new IllegalArgumentException(
                "El deporte seleccionado no existe."
            );
        }

        torneo.agregarEvento(partido);

        vista.mostrarMensaje("Encuentro creado correctamente.");
    }

    private void crearExhibicion() {

        String titulo = vista.leerTexto("Título de la exhibición:");

        if (titulo == null) return;

        Exhibicion exhibicion = new Exhibicion(titulo);

        torneo.agregarEvento(exhibicion);

        vista.mostrarMensaje("Exhibición creada correctamente.");
    }

    private void programarEvento() {

        List<Programable> eventos = torneo.obtenerEventos();

        if (eventos.isEmpty()) {
            vista.mostrarMensaje("No hay eventos registrados.");
            return;
        }

        vista.mostrarMensaje("Eventos disponibles:");

        for (int i = 0; i < eventos.size(); i++) {
            vista.mostrarMensaje(
                (i + 1) + ". " + eventos.get(i).getDescripcion()
            );
        }

        int numero = vista.leerEntero("Seleccione el evento:");

        if (numero == -1) return;

        if (numero < 1 || numero > eventos.size()) {
            throw new IllegalArgumentException(
                "El número de evento no es válido."
            );
        }

        Programable evento = eventos.get(numero - 1);

        LocalDate fecha = vista.leerFecha("Ingrese la fecha (AAAA-MM-DD):");

        if (fecha == null) return;

        int hora = vista.leerEntero("Ingrese la hora (0-23):");

        if (hora == -1) return;

        String sede = vista.leerTexto("Ingrese la sede:");

        if (sede == null) return;

        torneo.programarEvento(evento, fecha, hora, sede);

        vista.mostrarMensaje("Evento programado correctamente.");
    }

    private void registrarResultado() {

        List<Programable> eventos = torneo.obtenerEventos();

        if (eventos.isEmpty()) {
            vista.mostrarMensaje("No hay encuentros registrados.");
            return;
        }

        List<Programable> encuentros = new java.util.ArrayList<>();

        for (Programable evento : eventos) {
            if (evento instanceof Puntuable) {
                encuentros.add(evento);
            }
        }

        if (encuentros.isEmpty()) {
            vista.mostrarMensaje("No hay encuentros registrados.");
            return;
        }

        vista.mostrarMensaje("Encuentros disponibles:");

        for (int i = 0; i < encuentros.size(); i++) {
            vista.mostrarMensaje(
                (i + 1) + ". " + encuentros.get(i).getDescripcion()
            );
        }

        int numero = vista.leerEntero("Seleccione el encuentro:");

        if (numero == -1) return;

        if (numero < 1 || numero > encuentros.size()) {
            throw new IllegalArgumentException(
                "El número de encuentro no es válido."
            );
        }

        Programable evento = encuentros.get(numero - 1);

        int marcadorLocal = vista.leerEntero(
            "Marcador del equipo local:"
        );

        if (marcadorLocal == -1) return;

        int marcadorVisitante = vista.leerEntero(
            "Marcador del equipo visitante:"
        );

        if (marcadorVisitante == -1) return;

        Puntuable encuentro = (Puntuable) evento;

        torneo.registrarResultado(
            encuentro, marcadorLocal, marcadorVisitante
        );

        vista.mostrarMensaje("Resultado registrado correctamente.");
    }
}
