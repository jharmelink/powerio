package net.harmelink.powerio.mapper;

public class MapperUnit {
    private final String field;

    private final Class mapperClass;

    public MapperUnit(final String field, final Class<? extends Mapper> mapperClass) {
        this.field = field;
        this.mapperClass = mapperClass;
    }

    public String getField() {
        return field;
    }

    public Class<? extends Mapper> getMapperClass() {
        return mapperClass;
    }
}
