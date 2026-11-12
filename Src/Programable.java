import java.time.LocalDate;
import java.util.List;

public interface Programable extends Comparable<Programable> {

    void programar(LocalDate fecha, int hora, String sede);

    LocalDate getFecha();

    Integer getHora();

    String getSede();

    boolean estaProgramado();

    List<Equipo> getEquiposInvolucrados();

    String getDescripcion();

    @Override
    default int compareTo(Programable otro) {

        // Primero comparar las fechas.
        int comparacionFecha = this.getFecha().compareTo(otro.getFecha());

        if (comparacionFecha != 0) {
            return comparacionFecha;
        }

        // Si las fechas son iguales, comparar las horas.
        int comparacionHora = this.getHora().compareTo(otro.getHora());

        if (comparacionHora != 0) {
            return comparacionHora;
        }

        // Si también tienen la misma hora, comparar las sedes.
        return this.getSede().compareToIgnoreCase(otro.getSede());
    }
}
