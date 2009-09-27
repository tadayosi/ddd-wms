package wms.infrastructure.utils;

/**
 * 可読性向上用ユーティリティ
 * 
 * @author digitalsoul
 *
 */
public final class SupportUtils {

    private SupportUtils() {

    }

    /**
     * nullチェック.
     * @param args
     */
    public static void required(Object... args) {
        if (args == null) {
            throw new IllegalArgumentException("Argument shouldn't be null.");
        }

        for (Object target : args) {
            if (target == null) {
                throw new IllegalArgumentException(
                        "Argument shouldn't be null.");
            }
        }
    }

}
