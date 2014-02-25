package net.harmelink.powerio.mapper.p1;


import net.harmelink.powerio.model.Telegram;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TelegramMapperTest {
    private static final String input = "KMP5 KA6U001258704319\n" +
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

    private TelegramMapper telegramMapper;

    @Before
    public void setUp() throws Exception {
        telegramMapper = new TelegramMapper();
    }

    @Test
    public void testMap() throws Exception {
        final Telegram telegram = telegramMapper.map(input);

        Assert.assertEquals("Unknown", telegram.getManufacturer());
        Assert.assertEquals("KA6U001258704319", telegram.getSerialNumber());

        Assert.assertEquals(new Double(0.23), telegram.getActualPowerConsumption().getValue());
        Assert.assertEquals("kW", telegram.getActualPowerConsumption().getUnit());

        Assert.assertEquals(new Double(0), telegram.getActualPowerDelivery().getValue());
        Assert.assertEquals("kW", telegram.getActualPowerDelivery().getUnit());

        Assert.assertEquals(new Double(3181), telegram.getTotalPowerConsumedPeak().getValue());
        Assert.assertEquals("kWh", telegram.getTotalPowerConsumedPeak().getUnit());

        Assert.assertEquals(new Double(2582), telegram.getTotalPowerConsumedOffPeak().getValue());
        Assert.assertEquals("kWh", telegram.getTotalPowerConsumedOffPeak().getUnit());

        Assert.assertEquals(new Double(0), telegram.getTotalPowerDeliveredPeak().getValue());
        Assert.assertEquals("kWh", telegram.getTotalPowerDeliveredPeak().getUnit());

        Assert.assertEquals(new Double(0), telegram.getTotalPowerDeliveredOffPeak().getValue());
        Assert.assertEquals("kWh", telegram.getTotalPowerDeliveredOffPeak().getUnit());

        Assert.assertEquals(new Double(999), telegram.getActualPowerThreshold().getValue());
        Assert.assertEquals("A", telegram.getActualPowerThreshold().getUnit());

        Assert.assertNull(telegram.getTextMessageCodes());
        Assert.assertNull(telegram.getTextMessage());

        Assert.assertEquals("280090\u0098q11905c811", telegram.getBusDevices().get(0).getEquipmentIdentifier());
        Assert.assertEquals(new Integer(3), telegram.getBusDevices().get(0).getDeviceType());
        Assert.assertEquals("2014-01-06T22:00:00.000+01:00", telegram.getBusDevices().get(0).getMeasureDate());
        Assert.assertEquals("OFF", telegram.getBusDevices().get(0).getValvePosition());

        Assert.assertEquals(new Double(2730.079), telegram.getBusDevices().get(0).getTotalConsumed().getValue());
        Assert.assertEquals("m3", telegram.getBusDevices().get(0).getTotalConsumed().getUnit());
    }
}
