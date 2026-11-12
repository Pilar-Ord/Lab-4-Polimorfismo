public class Baloncesto extends Partido {

    public Baloncesto(Equipo equipoLocal, Equipo equipoVisitante) {
        super(equipoLocal, equipoVisitante);
    }

    @Override
    protected void validarMarcador(
        int marcadorLocal, int marcadorVisitante
    ) {
        if (marcadorLocal == marcadorVisitante) {
            throw new IllegalArgumentException(
                "Un partido de baloncesto no puede terminar empatado."
            );
        }
    }

    @Override
    protected int calcularPuntos(int marcadorPropio, int marcadorRival) {
        if (marcadorPropio > marcadorRival) {
            return 2;
        }

        return 1;
    }

    @Override
    public String getDeporte() {
        return "Baloncesto";
    }
}