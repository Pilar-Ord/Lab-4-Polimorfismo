public class Equipo implements Comparable<Equipo> {

    private String codigo;
    private String nombre;
    private String empresa;
    private int puntosTabla;

    public Equipo(String codigo, String nombre, String empresa) {

        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "El codigo del equipo no puede estar vacio."
            );
        }

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "El nombre del equipo no puede estar vacio."
            );
        }

        if (empresa == null || empresa.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "La empresa del equipo no puede estar vacia."
            );
        }

        this.codigo = codigo.trim();
        this.nombre = nombre.trim();
        this.empresa = empresa.trim();
        this.puntosTabla = 0;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmpresa() {
        return empresa;
    }

    public int getPuntosTabla() {
        return puntosTabla;
    }

    public void sumarPuntos(int puntos) {

        if (puntos < 0) {
            throw new IllegalArgumentException(
                "Los puntos no pueden ser negativos."
            );
        }

        this.puntosTabla += puntos;
    }

    @Override
    public int compareTo(Equipo otro) {

        if (otro == null) {
            throw new IllegalArgumentException(
                "El equipo a comparar no puede ser nulo."
            );
        }

        int comparacion = Integer.compare(
            otro.puntosTabla, this.puntosTabla
        );

        if (comparacion != 0) {
            return comparacion;
        }

        return this.nombre.compareToIgnoreCase(otro.nombre);
    }

    @Override
    public String toString() {

        return String.format(
            "----- EQUIPO: %s -----%n"
          + "Codigo:   %s%n"
          + "Nombre:   %s%n"
          + "Empresa:  %s%n"
          + "Puntos:   %d",
            nombre, codigo, nombre, empresa, puntosTabla
        );
    }
}
