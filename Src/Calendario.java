import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Calendario implements Iterable<Programable> {

    private List<Programable> eventos;

    public Calendario() {
        eventos = new ArrayList<>();
    }

    public void agregarEvento(Programable evento) {

        if (evento == null) {
            throw new IllegalArgumentException(
                "El evento no puede ser nulo."
            );
        }

        if (eventos.contains(evento)) {
            throw new IllegalArgumentException(
                "El evento ya está registrado."
            );
        }

        eventos.add(evento);
    }

    public List<Programable> obtenerEventos() {

        return Collections.unmodifiableList(
            new ArrayList<>(eventos)
        );
    }

    public void programarEvento(
        Programable evento, LocalDate fecha, int hora, String sede
    ) {

        if (evento == null || !eventos.contains(evento)) {
            throw new IllegalArgumentException(
                "El evento no pertenece a este calendario."
            );
        }

        // Validar los datos antes de modificar el evento.
        if (fecha == null) {
            throw new IllegalArgumentException(
                "La fecha no puede ser nula."
            );
        }

        if (hora < 0 || hora > 23) {
            throw new IllegalArgumentException(
                "La hora debe estar entre 0 y 23."
            );
        }

        if (sede == null || sede.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "La sede no puede estar vacía."
            );
        }

        validarConflictos(evento, fecha, hora, sede.trim());

        evento.programar(fecha, hora, sede);
    }

    private void validarConflictos(
        Programable evento, LocalDate fecha, int hora, String sede
    ) {

        for (Programable otro : eventos) {

            // No comparar el evento consigo mismo al reprogramarlo.
            if (otro == evento || !otro.estaProgramado()) {
                continue;
            }

            // Comprobar si la sede ya está ocupada.
            boolean mismaFecha = otro.getFecha().equals(fecha);
            boolean mismaHora = otro.getHora() == hora;

            if (mismaFecha && mismaHora
                && otro.getSede().equalsIgnoreCase(sede)) {

                throw new IllegalArgumentException(
                    "La sede ya está ocupada en esa fecha y hora."
                );
            }

            // Comprobar si alguno de los equipos ya tiene un partido.
            if (evento instanceof Partido && otro instanceof Partido) {

                if (mismaFecha && mismaHora) {

                    List<Equipo> equiposEvento =
                        evento.getEquiposInvolucrados();

                    List<Equipo> equiposOtro =
                        otro.getEquiposInvolucrados();

                    for (Equipo equipo : equiposEvento) {

                        if (equiposOtro.contains(equipo)) {

                            throw new IllegalArgumentException(
                                "Uno de los equipos ya tiene un encuentro "
                                + "programado en esa fecha y hora."
                            );
                        }
                    }
                }
            }
        }
    }

    @Override
    public Iterator<Programable> iterator() {

        List<Programable> eventosProgramados = new ArrayList<>();

        for (Programable evento : eventos) {

            if (evento.estaProgramado()) {
                eventosProgramados.add(evento);
            }
        }

        Collections.sort(eventosProgramados);

        return Collections.unmodifiableList(
            eventosProgramados
        ).iterator();
    }
}
