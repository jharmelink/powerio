package net.harmelink.powerio.mapper;

import net.harmelink.powerio.model.Consumption;

import java.util.Arrays;
import java.util.List;

public class ConsumptionMapper extends SimpleRegexMapper<Consumption> {
    public ConsumptionMapper() {
        super(Consumption.class);
    }

    @Override
    protected List<RegexMapping> getRegexMappings() {
        return Arrays.asList(
                new RegexMapping("^(.+?)\\*.+$", "value", DoubleMapper.class),
                new RegexMapping("^.+\\*(.+?)$", "unit", StringMapper.class),
                new RegexMapping("^.+\\n\\((.+?)\\)$", "value", DoubleMapper.class),
                new RegexMapping("^\\((.+?)\\)\\n.+$", "unit", StringMapper.class));
    }
}
