package com.googlecode.dddwms.emulator;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;

public class EmulatorTest {

    private Emulator target = new Emulator("");

    @Test
    public void createItems() throws Exception {
        List<Map<String, Object>> items = target.createItems();
        assertTrue("items.size=" + items.size(), items.size() >= 1);
    }
}
