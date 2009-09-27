package wms.infrastructure.utils;

import org.junit.Test;

/**
 * 可読性向上用ユーティリティの単体テストクラス.
 * 
 * @author digitalsoul
 *
 */
public class SupportUtilsTest {

    /**
     * 引数が１つでもnullならば例外.
     */
    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfOneOfArgumentsIsNull() {
        SupportUtils.required("", "", null);
    }

    /**
     * 引数が１つでそれがnullなら例外.
     */
    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfOnlyOneArgumentIsNull() {
        SupportUtils.required((Object[]) null);
    }
}
