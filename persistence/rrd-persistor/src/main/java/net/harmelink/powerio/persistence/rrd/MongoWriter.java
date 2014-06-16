package net.harmelink.powerio.persistence.rrd;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import org.rrd4j.ConsolFun;
import org.rrd4j.DsType;
import org.rrd4j.core.RrdDb;
import org.rrd4j.core.RrdDef;
import org.rrd4j.core.RrdMongoDBBackendFactory;
import org.rrd4j.core.Sample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.UnknownHostException;

class MongoWriter {
    private static final Logger LOG = LoggerFactory.getLogger(RrdWriter.class);
    private static final String HOST = "localhost";
    private static final int PORT = 27017;
    private static final String DB_NAME = "powerio";
    private static final String COLLECTION = "usage";

    private final String dataSourceName;
    private final long interval;
    private RrdDb rrdDb;

    public MongoWriter(final String dataSourceName, final long interval) {
        this.dataSourceName = dataSourceName;
        this.interval = interval;
        initRrdDb();
    }

    public void write(final long timestamp, final double value) {
        try {
            final Sample sample = rrdDb.createSample(timestamp);
            sample.setValue(dataSourceName, value);
            sample.update();
        } catch (IOException e) {
            LOG.error("Unable to create RRD sample.\n{}", e);
        }
    }

    public void close() {
        try {
            rrdDb.close();
        } catch (final IOException e) {
            LOG.error("Unable to close the RRD database.\n{}", e);
        }
    }

    private void initRrdDb() {
        try {
            final MongoClient mongoClient = new MongoClient(HOST, PORT);
            final DB db = mongoClient.getDB(DB_NAME);
            final DBCollection collection = db.getCollection(COLLECTION);
            final RrdMongoDBBackendFactory rrdMongoBackendFactory = new RrdMongoDBBackendFactory(collection);

            RrdDb.setDefaultFactory("MONGODB");
            LOG.debug("Successfully attached the Mongo DB backend to RRD4J");

            rrdDb = new RrdDb(getRrdDef(), rrdMongoBackendFactory);
        } catch (final UnknownHostException e) {
            LOG.error("Unable to connect to MongoDB.\n{}", e);
        } catch (IOException e) {
            LOG.error("Unable to create RRD database.\n{}", e);
        }
    }

    private RrdDef getRrdDef() {
        final RrdDef rrdDef = new RrdDef(COLLECTION, System.currentTimeMillis(), interval);
        rrdDef.addDatasource(dataSourceName, DsType.GAUGE, interval * 2, 0, Double.MAX_VALUE);
        rrdDef.addArchive(ConsolFun.AVERAGE, 0.5, 10, 17280);
        rrdDef.addArchive(ConsolFun.AVERAGE, 0.5, 6, 10);
        rrdDef.addArchive(ConsolFun.TOTAL, 0.5, 1, 10);
        LOG.debug(rrdDef.dump());

        return rrdDef;
    }
}
