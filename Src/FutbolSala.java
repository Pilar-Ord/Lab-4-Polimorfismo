public class FutbolSala extends Partido {

    public FutbolSala(Equipo equipoLocal, Equipo equipoVisitante) {
        super(equipoLocal, equipoVisitante);
    }

    @Override
    protected void validarMarcador(
        int marcadorLocal, int marcadorVisitante
    ) {
        // Se permiten empates.
        // Partido ya rechaza los marcadores negativos.
    }

    @Override
    protected int calcularPuntos(int marcadorPropio, int marcadorRival) {
        if (marcadorPropio > marcadorRival) {
            return 3;
        }

        if (marcadorPropio == marcadorRival) {
            return 1;
        }

        return 0;
    }

    @Override
    public String getDeporte() {
        return "Fútbol sala";
    }
}