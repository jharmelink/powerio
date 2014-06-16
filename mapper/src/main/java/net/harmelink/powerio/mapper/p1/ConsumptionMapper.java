package net.harmelink.powerio.mapper.p1;

import com.google.inject.Inject;
import net.harmelink.powerio.mapper.DoubleMapper;
import net.harmelink.powerio.mapper.RegexMapper;
import net.harmelink.powerio.mapper.StringMapper;
import net.harmelink.powerio.model.Consumption;

public class ConsumptionMapper extends RegexMapper<Consumption> {

    @Inject
    private DoubleMapper doubleMapper;

    @Inject
    private StringMapper stringMapper;

    protected Consumption mapToObject(final String data) {
        if (data.contains("\n(")) {
            return new Consumption()
                    .withValue(doubleMapper.map(data, "^.+\\n\\((.+?)\\)$"))
                    .withUnit(stringMapper.map(data, "^\\((.+?)\\)\\n.+$"));
        } else {
            return new Consumption()
                    .withValue(doubleMapper.map(data, "^(.+?)\\*.+$"))
                    .withUnit(stringMapper.map(data, "^.+\\*(.+?)$"));
        }
    }
}
