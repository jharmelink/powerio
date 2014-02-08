package net.harmelink.powerio.mapper;


import net.harmelink.powerio.model.Consumption;
import net.harmelink.powerio.model.Supplier;
import net.harmelink.powerio.model.Telegram;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.StringReader;

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
        final Telegram telegram = telegramMapper.map(new BufferedReader(new StringReader(input)));

        Assert.assertEquals(Supplier.Id.KA, telegram.getSupplier().getId());
        Assert.assertEquals(Supplier.Name.KAMSTRUP, telegram.getSupplier().getName());

        Assert.assertEquals(new Double(0.23), telegram.getCurrentConsumption().getValue());
        Assert.assertEquals(Consumption.Unit.K_W, telegram.getCurrentConsumption().getUnit());

        Assert.assertEquals(new Double(0), telegram.getCurrentDelivery().getValue());
        Assert.assertEquals(Consumption.Unit.K_W, telegram.getCurrentDelivery().getUnit());

        Assert.assertEquals(new Double(3181), telegram.getTotalConsumptionOffPeak().getValue());
        Assert.assertEquals(Consumption.Unit.K_WH, telegram.getTotalConsumptionOffPeak().getUnit());

        Assert.assertEquals(new Double(2582), telegram.getTotalConsumptionPeak().getValue());
        Assert.assertEquals(Consumption.Unit.K_WH, telegram.getTotalConsumptionPeak().getUnit());

        Assert.assertEquals(new Double(0), telegram.getTotalDeliveryOffPeak().getValue());
        Assert.assertEquals(Consumption.Unit.K_WH, telegram.getTotalConsumptionOffPeak().getUnit());

        Assert.assertEquals(new Double(0), telegram.getTotalDeliveryPeak().getValue());
        Assert.assertEquals(Consumption.Unit.K_WH, telegram.getTotalConsumptionPeak().getUnit());

        Assert.assertEquals(new Double(999), telegram.getMaximumPower().getValue());
        Assert.assertEquals(Consumption.Unit.A, telegram.getMaximumPower().getUnit());

        Assert.assertEquals(Boolean.TRUE, telegram.getOffPeak());
        Assert.assertEquals(Boolean.TRUE, telegram.getPowerSwitchPosition());
        Assert.assertEquals(Boolean.TRUE, telegram.getGasSwitchPosition());

        Assert.assertEquals("KA6U001258704319", telegram.getSerial());
        Assert.assertEquals("204B513655303031309833303434231531", telegram.getPowerMeterId());
        Assert.assertEquals("3238303039309871313139303563383131", telegram.getGasMeterId());
        Assert.assertEquals("", telegram.getMessageText());
        Assert.assertEquals(null, telegram.getMessageCode());
    }
}
