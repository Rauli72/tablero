public class tablero {

    private casilla[][] casillas;

    public tablero() {
        casillas = new casilla[8][8];
        for (int fila = 0; fila < 8; fila++) {
            for (int col = 0; col < 8; col++) {
                casillas[fila][col] = new casilla(fila, col);
            }
        }
    }

    // ====== COLUMNA MEDIANTE SWITCH (MAYÚS / MINÚS) ======
    private int col(char c) {
        c = Character.toUpperCase(c);

        return switch (c) {
            case 'A' -> 0;
            case 'B' -> 1;
            case 'C' -> 2;
            case 'D' -> 3;
            case 'E' -> 4;
            case 'F' -> 5;
            case 'G' -> 6;
            case 'H' -> 7;
            default -> -1;
        };
    }

    // ====== FILA (SIEMPRE NÚMERO) ======
    private int fila(char n) {
        return 8 - Character.getNumericValue(n);
    }

    // ====== COLOCAR PIEZA (ACEPTA MAYÚS / MINÚS) ======
    public void colocarPieza(char tipo, char color, String pos) {
        char columna = Character.toUpperCase(pos.charAt(0));
        char filaChar = pos.charAt(1);

        int col = col(columna);
        int fila = fila(filaChar);

        // Usamos setters
        casillas[fila][col].setPieza(new pieza(
                String.valueOf(Character.toUpperCase(tipo)),
                String.valueOf(Character.toUpperCase(color))
        ));
    }

    // ====== PROCESAR MOVIMIENTO ======
    public boolean procesarMovimiento(String jugada) {

        if (jugada.contains(" ")) {
            System.out.println("Formato incorrecto: no se permiten espacios.");
            return false;
        }

        if (!jugada.startsWith("[") || !jugada.endsWith("]")) {
            System.out.println("Formato incorrecto.");
            return false;
        }

        String contenido = jugada.substring(1, jugada.length() - 1);
        int coma = contenido.indexOf(",");
        if (coma == -1 || coma != contenido.lastIndexOf(",")) {
            System.out.println("Formato incorrecto.");
            return false;
        }

        String antes = contenido.substring(0, coma);
        String despues = contenido.substring(coma + 1);

        if (!((antes.length() == 2 || antes.length() == 3) &&
                (despues.length() == 2 || despues.length() == 3))) {
            System.out.println("Formato de casilla incorrecto.");
            return false;
        }

        char tipo;
        String origen;
        String destino;

        if (antes.length() == 2) {
            tipo = 'P';
            origen = antes;
        } else {
            tipo = Character.toUpperCase(antes.charAt(0));
            origen = antes.substring(1);
        }

        destino = (despues.length() == 2) ? despues : despues.substring(1);

        if (!esCasillaValida(origen) || !esCasillaValida(destino)) {
            System.out.println("Casilla inválida.");
            return false;
        }

        int fi_ini = fila(origen.charAt(1));
        int co_ini = col(origen.charAt(0));
        int fi_fin = fila(destino.charAt(1));
        int co_fin = col(destino.charAt(0));

        pieza p = casillas[fi_ini][co_ini].getPieza(); // Usamos getter

        if (p == null || !p.getTipo().equals(String.valueOf(tipo))) { // Comparamos con getter
            System.out.println("La pieza indicada no coincide.");
            return false;
        }

        boolean valido = switch (tipo) {
            case 'C' -> MovimientoCaballo.esValido(fi_ini, co_ini, fi_fin, co_fin);
            case 'T' -> MovimientoTorre.esValido(fi_ini, co_ini, fi_fin, co_fin);
            case 'A' -> MovimientoAlfil.esValido(fi_ini, co_ini, fi_fin, co_fin);
            case 'D' -> MovimientoReina.esValido(fi_ini, co_ini, fi_fin, co_fin);
            case 'R' -> MovimientoRey.esValido(fi_ini, co_ini, fi_fin, co_fin);
            case 'P' -> MovimientoPeon.esValido(fi_ini, co_ini, fi_fin, co_fin, p.getColor(), casillas);
            default -> false;
        };

        if (!valido) {
            System.out.println("Movimiento ilegal.");
            return false;
        }

        // Ejecutar movimiento usando setters
        casillas[fi_fin][co_fin].setPieza(p);
        casillas[fi_ini][co_ini].setPieza(null);

        System.out.println("Movimiento realizado correctamente.");
        return true;
    }

    private boolean esCasillaValida(String s) {
        if (s.length() != 2) return false;

        char c = Character.toUpperCase(s.charAt(0));
        char f = s.charAt(1);

        return c >= 'A' && c <= 'H' && f >= '1' && f <= '8';
    }
}
