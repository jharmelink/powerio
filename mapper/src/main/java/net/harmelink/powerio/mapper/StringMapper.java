package net.harmelink.powerio.mapper;

public class StringMapper extends RegexMapper<String> {

    @Override
    protected String mapToObject(final String data) {
        return data;
    }
}
