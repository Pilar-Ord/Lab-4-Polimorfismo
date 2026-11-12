
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import java.awt.Font;

public class Vista {

    public Vista() {
        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 14));
        UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.BOLD, 13));
    }

    public int mostrarMenu() {
        String[] opciones = {
            "Registrar equipo",
            "Crear encuentro",
            "Crear exhibición",
            "Programar evento",
            "Registrar resultado",
            "Tabla de posiciones",
            "Calendario",
            "Salir"
        };

        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JLabel titulo = new JLabel(
            "<html><center><h2>ARENA GT</h2>"
            + "<font size='3'>COPA DEPORTIVA</font>"
            + "</center></html>",
            JLabel.CENTER
        );

        panel.add(titulo, BorderLayout.NORTH);

        JLabel mensaje = new JLabel("Seleccione una opción:", JLabel.CENTER);
        panel.add(mensaje, BorderLayout.CENTER);

        int opcion = JOptionPane.showOptionDialog(
            null,
            panel,
            "ArenaGT",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            opciones,
            opciones[0]
        );

        if (opcion == -1) {
            return 8;
        }

        return opcion + 1;
    }

    public String leerTexto(String mensaje) {
        return JOptionPane.showInputDialog(
            null,
            mensaje,
            "ArenaGT - Ingreso de datos",
            JOptionPane.QUESTION_MESSAGE
        );
    }

    public int leerEntero(String mensaje) {
        while (true) {
            String entrada = JOptionPane.showInputDialog(
                null,
                mensaje,
                "ArenaGT - Ingreso de datos",
                JOptionPane.QUESTION_MESSAGE
            );

            if (entrada == null) {
                return -1;
            }

            try {
                return Integer.parseInt(entrada.trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                    null,
                    "Debe ingresar un número entero.",
                    "Dato inválido",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    public LocalDate leerFecha(String mensaje) {
        while (true) {
            String entrada = JOptionPane.showInputDialog(
                null,
                mensaje,
                "ArenaGT - Fecha",
                JOptionPane.QUESTION_MESSAGE
            );

            if (entrada == null) {
                return null;
            }

            try {
                return LocalDate.parse(entrada.trim());
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(
                    null,
                    "Formato incorrecto.\nUtilice AAAA-MM-DD.",
                    "Fecha inválida",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    public boolean cancelar() {
        return JOptionPane.showConfirmDialog(
            null,
            "¿Desea regresar al menú principal?",
            "Cancelar operación",
            JOptionPane.YES_NO_OPTION
        ) == JOptionPane.YES_OPTION;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(
            null,
            mensaje,
            "ArenaGT",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    public void mostrarTabla(List<Equipo> equipos) {
        StringBuilder contenido = new StringBuilder();

        contenido.append("TABLA DE POSICIONES\n\n");

        if (equipos.isEmpty()) {
            contenido.append("No hay equipos registrados.");
        } else {
            contenido.append(
                String.format(
                    "%-10s %-18s %-18s %s%n",
                    "Código", "Equipo", "Empresa", "Puntos"
                )
            );

            contenido.append(
                "----------------------------------------------------------\n"
            );

            for (Equipo equipo : equipos) {
                contenido.append(
                    String.format(
                        "%-10s %-18s %-18s %d%n",
                        equipo.getCodigo(),
                        equipo.getNombre(),
                        equipo.getEmpresa(),
                        equipo.getPuntosTabla()
                    )
                );
            }
        }

        JOptionPane.showMessageDialog(
            null,
            contenido.toString(),
            "ArenaGT - Tabla de posiciones",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    public void mostrarCalendario(Iterable<Programable> eventos) {
        StringBuilder contenido = new StringBuilder();

        contenido.append("CALENDARIO DE EVENTOS\n\n");

        boolean hayEventos = false;

        for (Programable evento : eventos) {
            hayEventos = true;

            contenido.append("Fecha: " + evento.getFecha() + "\n");
            contenido.append("Hora: " + evento.getHora() + ":00\n");
            contenido.append("Sede: " + evento.getSede() + "\n");
            contenido.append(evento.getDescripcion() + "\n");
            contenido.append("--------------------------------\n");
        }

        if (!hayEventos) {
            contenido.append("No hay eventos programados.");
        }

        JOptionPane.showMessageDialog(
            null,
            contenido.toString(),
            "ArenaGT - Calendario",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}
