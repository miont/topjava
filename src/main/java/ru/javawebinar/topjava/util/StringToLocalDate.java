package ru.javawebinar.topjava.util;

import org.slf4j.Logger;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;

import static org.slf4j.LoggerFactory.getLogger;

public class StringToLocalDate implements Converter<String, LocalDate> {
    private static final Logger log = getLogger(StringToLocalDate.class);

    @Override
    public LocalDate convert(String source) {
        log.info("convert {} to LocalDate", source);
        return DateTimeUtil.parseLocalDate(source);
    }
}
