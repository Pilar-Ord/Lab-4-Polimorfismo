import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Torneo implements Iterable<Programable> {

    private List<Equipo> equipos;
    private Calendario calendario;

    public Torneo() {
        equipos = new ArrayList<>();
        calendario = new Calendario();
    }

    public void registrarEquipo(String codigo, String nombre, String empresa) {

        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "El código del equipo no puede estar vacío."
            );
        }

        if (buscarEquipo(codigo) != null) {
            throw new IllegalArgumentException(
                "Ya existe un equipo con ese código."
            );
        }

        Equipo nuevoEquipo = new Equipo(
            codigo.trim(),
            nombre,
            empresa
        );

        equipos.add(nuevoEquipo);
    }

    public Equipo buscarEquipo(String codigo) {

        if (codigo == null) {
            return null;
        }

        for (Equipo equipo : equipos) {

            if (equipo.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return equipo;
            }
        }

        return null;
    }

    public void agregarEvento(Programable evento) {

        calendario.agregarEvento(evento);
    }

    public List<Programable> obtenerEventos() {

        return calendario.obtenerEventos();
    }

    public void programarEvento(
        Programable evento, java.time.LocalDate fecha,
        int hora, String sede
    ) {

        calendario.programarEvento(evento, fecha, hora, sede);
    }

    public void registrarResultado(
        Puntuable encuentro, int marcadorLocal, int marcadorVisitante
    ) {

        if (encuentro == null) {
            throw new IllegalArgumentException(
                "El encuentro no puede ser nulo."
            );
        }

        if (!(encuentro instanceof Programable)) {
            throw new IllegalArgumentException(
                "El elemento seleccionado no es un encuentro."
            );
        }

        Programable evento = (Programable) encuentro;

        if (!calendario.obtenerEventos().contains(evento)) {
            throw new IllegalArgumentException(
                "El encuentro no pertenece a este torneo."
            );
        }

        encuentro.registrarResultado(marcadorLocal, marcadorVisitante);
    }

    public List<Equipo> obtenerTablaPosiciones() {

        List<Equipo> tabla = new ArrayList<>(equipos);

        Collections.sort(tabla);

        return tabla;
    }

    @Override
    public Iterator<Programable> iterator() {

        return calendario.iterator();
    }
}
