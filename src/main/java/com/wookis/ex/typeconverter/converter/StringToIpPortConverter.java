package com.wookis.ex.typeconverter.converter;

import com.wookis.ex.typeconverter.type.IpPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class StringToIpPortConverter implements Converter<String, IpPort> {

    @Override
    public IpPort convert(String source) {
        log.info("Convert Source : {}", source);

        String[] split = source.split(":");
        String ip = split[0];
        Integer port = Integer.parseInt(split[1]);

        return new IpPort(ip,port);
    }
}
