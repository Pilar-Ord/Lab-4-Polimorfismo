import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class Exhibicion implements Programable {

    private final String titulo;
    private EstadoExhibicion estado;
    private LocalDate fecha;
    private Integer hora;
    private String sede;

    public Exhibicion(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "El título de la exhibición no puede estar vacío."
            );
        }

        this.titulo = titulo.trim();
        this.estado = EstadoExhibicion.SIN_PROGRAMAR;
        this.fecha = null;
        this.hora = null;
        this.sede = null;
    }

    @Override
    public void programar(LocalDate fecha, int hora, String sede) {
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
        this.estado = EstadoExhibicion.PROGRAMADA;
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
        return estado == EstadoExhibicion.PROGRAMADA;
    }

    @Override
    public List<Equipo> getEquiposInvolucrados() {
        return Collections.emptyList();
    }

    @Override
    public String getDescripcion() {
        return "Exhibición: " + titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public EstadoExhibicion getEstado() {
        return estado;
    }
}
