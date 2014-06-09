package net.harmelink.powerio.persistence.rrd;

import org.junit.Test;
import org.rrd4j.ConsolFun;
import org.rrd4j.core.FetchData;
import org.rrd4j.core.FetchRequest;
import org.rrd4j.core.RrdDb;

import java.io.IOException;

public class MongoWriterTest {
    private static MongoWriter mongoWriter = new MongoWriter("test", 10);

    @Test
    public void writeTest() throws IOException, InterruptedException {
        final long start = System.currentTimeMillis();
        for (int i = 0; i < 20; i++) {
            Thread.sleep(10);
            final double value = i;
            final long time = System.currentTimeMillis();
            mongoWriter.write(time, value);
            System.out.println(time + ": " + value);
        }
        final long end = System.currentTimeMillis();
        mongoWriter.close();
        System.out.println("");

        RrdDb rrdDb = new RrdDb("usage");
        FetchRequest fetchRequest = rrdDb.createFetchRequest(ConsolFun.AVERAGE, start, end);
        FetchData fetchData = fetchRequest.fetchData();
        System.out.println(fetchData.dump());
        rrdDb.close();
    }
}