package com.wookis.ex.typeconverter.converter;

import com.wookis.ex.typeconverter.type.IpPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class IpPortToStringConverter implements Converter<IpPort,String> {

    @Override
    public String convert(IpPort source) {
        log.info("Convert Source : {}",source);

        return source.getIp() + ":"+source.getPort();
    }
}
