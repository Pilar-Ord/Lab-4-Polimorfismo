import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class Partido implements Programable, Puntuable {

    private final Equipo equipoLocal;
    private final Equipo equipoVisitante;
    private Integer marcadorLocal;
    private Integer marcadorVisitante;
    private EstadoPartido estado;
    private LocalDate fecha;
    private Integer hora;
    private String sede;

    protected Partido(Equipo equipoLocal, Equipo equipoVisitante) {
        if (equipoLocal == null || equipoVisitante == null) {
            throw new IllegalArgumentException(
                "Los equipos no pueden ser nulos."
            );
        }

        if (equipoLocal.getCodigo().equals(equipoVisitante.getCodigo())) {
            throw new IllegalArgumentException(
                "Un equipo no puede jugar contra sí mismo."
            );
        }

        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
        this.marcadorLocal = null;
        this.marcadorVisitante = null;
        this.estado = EstadoPartido.SIN_PROGRAMAR;
        this.fecha = null;
        this.hora = null;
        this.sede = null;
    }

    @Override
    public void programar(LocalDate fecha, int hora, String sede) {
        if (estado == EstadoPartido.FINALIZADO) {
            throw new IllegalStateException(
                "No se puede reprogramar un partido finalizado."
            );
        }

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

        this.fecha = fecha;
        this.hora = hora;
        this.sede = sede.trim();
        this.estado = EstadoPartido.PROGRAMADO;
    }

    @Override
    public LocalDate getFecha() {
        return fecha;
    }

    @Override
    public Integer getHora() {
        return hora;
    }

    @Override
    public String getSede() {
        return sede;
    }

    @Override
    public boolean estaProgramado() {
        return estado != EstadoPartido.SIN_PROGRAMAR;
    }

    public EstadoPartido getEstado() {
        return estado;
    }

    public Integer getMarcadorLocal() {
        return marcadorLocal;
    }

    public Integer getMarcadorVisitante() {
        return marcadorVisitante;
    }

    @Override
    public List<Equipo> getEquiposInvolucrados() {
        return Collections.unmodifiableList(
            Arrays.asList(equipoLocal, equipoVisitante)
        );
    }
    
    protected abstract void validarMarcador(
    int marcadorLocal, int marcadorVisitante
    );

    protected abstract int calcularPuntos(
        int marcadorPropio, int marcadorRival
    );

    public abstract String getDeporte();

    @Override
    public final void registrarResultado(int marcadorLocal, int marcadorVisitante)
    {
        if (estado != EstadoPartido.PROGRAMADO) {
            throw new IllegalStateException(
                "Solo se puede registrar el resultado de un partido programado."
            );
        }

        if (marcadorLocal < 0 || marcadorVisitante < 0) {
            throw new IllegalArgumentException(
                "Los marcadores no pueden ser negativos."
            );
        }

        validarMarcador(marcadorLocal, marcadorVisitante);

        int puntosLocal = calcularPuntos(
            marcadorLocal, marcadorVisitante
        );

        int puntosVisitante = calcularPuntos(
            marcadorVisitante, marcadorLocal
        );

        this.marcadorLocal = marcadorLocal;
        this.marcadorVisitante = marcadorVisitante;

        equipoLocal.sumarPuntos(puntosLocal);
        equipoVisitante.sumarPuntos(puntosVisitante);

        this.estado = EstadoPartido.FINALIZADO;
    }
    @Override
    public String getDescripcion() {
        String descripcion = getDeporte() + ": "
            + equipoLocal.getNombre() + " vs "
            + equipoVisitante.getNombre()
            + " | Estado: " + estado;

        if (marcadorLocal != null && marcadorVisitante != null) {
            descripcion += " | Marcador: "
                + marcadorLocal + " - " + marcadorVisitante;
        }

        return descripcion;
    }
}