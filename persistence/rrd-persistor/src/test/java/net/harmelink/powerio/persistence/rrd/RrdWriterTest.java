package net.harmelink.powerio.persistence.rrd;

import net.harmelink.powerio.mapper.p1.TelegramMapper;
import org.junit.Test;
import org.rrd4j.ConsolFun;
import org.rrd4j.core.FetchData;
import org.rrd4j.core.FetchRequest;
import org.rrd4j.core.RrdDb;

import java.io.IOException;

public class RrdWriterTest {
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

    private RrdWriter rrdWriter = new RrdWriter(new TelegramMapper());

    @Test
    public void testWrite() throws IOException, InterruptedException {
        final long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            rrdWriter.writeMessage(input);
            Thread.sleep(5000);
        }

        RrdDb rrdDb = new RrdDb("usage");
        FetchRequest fetchRequest = rrdDb.createFetchRequest(ConsolFun.AVERAGE, start, System.currentTimeMillis());
        FetchData fetchData = fetchRequest.fetchData();
        System.out.println(fetchData.dump());
        rrdDb.close();
    }
}