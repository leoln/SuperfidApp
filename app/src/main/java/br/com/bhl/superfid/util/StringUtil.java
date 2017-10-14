package br.com.bhl.superfid.util;

/**
 * Created by leonardoln on 14/10/2017.
 */

public class StringUtil {

    public static String tirarCaracteresEspeciais(String texto ) {
        texto = texto.replaceAll("[-./,;]", "");
        return texto;
    }

}
