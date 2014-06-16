package net.harmelink.powerio.mapper;

import net.harmelink.powerio.model.Telegram;

import java.util.List;

public interface Mapper {
    List<Telegram> map(String data);
}
