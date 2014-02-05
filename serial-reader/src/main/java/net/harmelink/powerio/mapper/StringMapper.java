package net.harmelink.powerio.mapper;

public class StringMapper implements Mapper<String> {
    public String map(final String line) {
        return line.substring(line.indexOf('(', line.indexOf(')')));
    }
}
