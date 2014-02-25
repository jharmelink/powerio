package net.harmelink.powerio.mapper;

public interface Mapper<T> {
    T map(String data) throws InstantiationException, IllegalAccessException;

    char getStartChar();

    char getEndChar();
}
