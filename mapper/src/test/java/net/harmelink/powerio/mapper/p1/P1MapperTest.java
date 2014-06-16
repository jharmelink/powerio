package net.harmelink.powerio.mapper.p1;

import com.google.inject.Guice;
import com.google.inject.Injector;
import net.harmelink.powerio.model.Telegram;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class P1MapperTest {

    private static final String INPUT = "KMP5 KA6U001258704319\n" +
            "\n" +
            "0-0:96.1.1(204B513655303031309833303434231531)\n" +
            "1-0:1.8.1(03181.000*kWh)\n" +
            "1-0:1.8.2(02582.000*kWh)\n" +
            "1-0:2.8.1(00000.000*kWh)\n" +
            "1-0:2.8.2(00000.000*kWh)\n" +
            "0-0:96.14.0(0001)\n" +
            "1-0:1.7.0(0000.23*kW)\n" +
            "1-0:2.7.0(0000.00*kW)\n" +
            "0-0:17.0.0(999*A)\n" +
            "0-0:96.3.10(1)\n" +
            "0-0:96.13.1()\n" +
            "0-0:96.13.0()\n" +
            "0-1:24.1.0(3)\n" +
            "0-1:96.1.0(3238303039309871313139303563383131)\n" +
            "0-1:24.3.0(140206220000)(00)(60)(1)(0-1:24.2.1)(m3)\n" +
            "(02730.079)\n" +
            "0-1:24.4.0(1)";

    private P1Mapper p1Mapper;

    @Before
    public void setUp() throws Exception {
        final Injector injector = Guice.createInjector();
        p1Mapper = injector.getInstance(P1Mapper.class);
    }

    @Test
    public void testMap() throws Exception {
        final List<Telegram> telegrams = p1Mapper.map(INPUT);
        // TODO: set date and time
        System.out.println(telegrams.get(0).getTimestamp());

        Assert.assertEquals("Kamstrup", telegrams.get(0).getManufacturer());
        Assert.assertEquals("KA6U001258704319", telegrams.get(0).getSerialNumber());

        Assert.assertEquals(new Double(0.23), telegrams.get(0).getActualConsumption().getValue());
        Assert.assertEquals("kW", telegrams.get(0).getActualConsumption().getUnit());

        Assert.assertEquals(new Double(0), telegrams.get(0).getActualDelivery().getValue());
        Assert.assertEquals("kW", telegrams.get(0).getActualDelivery().getUnit());

        Assert.assertEquals(new Double(3181), telegrams.get(0).getTotalConsumedPeak().getValue());
        Assert.assertEquals("kWh", telegrams.get(0).getTotalConsumedPeak().getUnit());

        Assert.assertEquals(new Double(2582), telegrams.get(0).getTotalConsumedOffPeak().getValue());
        Assert.assertEquals("kWh", telegrams.get(0).getTotalConsumedOffPeak().getUnit());

        Assert.assertEquals(new Double(0), telegrams.get(0).getTotalDeliveredPeak().getValue());
        Assert.assertEquals("kWh", telegrams.get(0).getTotalDeliveredPeak().getUnit());

        Assert.assertEquals(new Double(0), telegrams.get(0).getTotalDeliveredOffPeak().getValue());
        Assert.assertEquals("kWh", telegrams.get(0).getTotalDeliveredOffPeak().getUnit());

        Assert.assertEquals(new Double(999), telegrams.get(0).getActualThreshold().getValue());
        Assert.assertEquals("A", telegrams.get(0).getActualThreshold().getUnit());

        Assert.assertNull(telegrams.get(0).getTextMessageCodes());
        Assert.assertNull(telegrams.get(0).getTextMessage());

        Assert.assertEquals("280090\u0098q11905c811", telegrams.get(1).getEquipmentIdentifier());
        Assert.assertEquals(Telegram.DeviceType.GAS, telegrams.get(1).getDeviceType());
        Assert.assertEquals("2014-02-06T22:00+01:00[Europe/Amsterdam]", telegrams.get(1).getTimestamp());
        Assert.assertEquals(Telegram.ValvePosition.OFF, telegrams.get(1).getValvePosition());

        Assert.assertEquals(new Double(2730.079), telegrams.get(1).getTotalConsumedPeak().getValue());
        Assert.assertEquals("m3", telegrams.get(1).getTotalConsumedPeak().getUnit());
    }
}
