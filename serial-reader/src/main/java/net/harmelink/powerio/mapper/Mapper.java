package net.harmelink.powerio.mapper;

public interface Mapper<T> {
    T map(String line);
}
