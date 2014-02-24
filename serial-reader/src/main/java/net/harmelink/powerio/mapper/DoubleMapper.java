package net.harmelink.powerio.mapper;

public class DoubleMapper implements Mapper<Double> {
    @Override
    public Double map(final String doubleValue) {
        return new Double(doubleValue);
    }
}
