public class MovimientoReina {

    public static boolean esValido(int fi_ini, int co_ini, int fi_fin, int co_fin) {
        int df = Math.abs(fi_fin - fi_ini);
        int dc = Math.abs(co_fin - co_ini);
        // Torre + Alfil
        boolean linea = df == 0 || dc == 0;
        boolean diagonal = df == dc;
        return linea || diagonal;
    }
}

