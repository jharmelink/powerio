package net.harmelink.powerio.mapper;

import org.apache.commons.lang3.StringUtils;

public class HexMapper extends RegexMapper<String> {

    @Override
    public String mapToObject(final String hexString) {
        if (!StringUtils.isEmpty(hexString)) {
            final StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < hexString.length() - 1; i += 2) {
                final String output = hexString.substring(i, (i + 2));
                final int decimal = Integer.parseInt(output, 16);

                stringBuilder.append((char) decimal);
            }

            return stringBuilder.toString();
        }

        return null;
    }
}
