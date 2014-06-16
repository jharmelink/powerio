package net.harmelink.powerio.mapper;

import org.junit.Assert;
import org.junit.Test;

public class DoubleMapperTest {
    @Test
    public void testMap() throws Exception {
        final DoubleMapper mapper = new DoubleMapper();
        Assert.assertNull(mapper.map("", "^(.+?)$"));
        Assert.assertNull(mapper.map("987h.87", "^(.+?)$"));
        Assert.assertEquals(new Double(4987.898), mapper.map("4987.898", "^(.+?)$"));
    }
}
