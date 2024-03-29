package com.wookis.ex.converter;

import com.wookis.ex.typeconverter.converter.IntegerToStringConverter;
import com.wookis.ex.typeconverter.converter.IpPortToStringConverter;
import com.wookis.ex.typeconverter.converter.StringToIntegerConverter;
import com.wookis.ex.typeconverter.converter.StringToIpPortConverter;
import com.wookis.ex.typeconverter.type.IpPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.support.DefaultConversionService;

import static org.assertj.core.api.Assertions.*;

public class ConversionServiceTest {

    @Test
    void conversionService() {
        DefaultConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(new StringToIntegerConverter());
        conversionService.addConverter(new IntegerToStringConverter());
        conversionService.addConverter(new StringToIpPortConverter());
        conversionService.addConverter(new IpPortToStringConverter());

        //사용
        assertThat(conversionService.convert("10", Integer.class)).isEqualTo(10);
        assertThat(conversionService.convert(10,String.class)).isEqualTo("10");

        IpPort ipPort = conversionService.convert("127.0.0.1:8080", IpPort.class);
        assertThat(ipPort).isEqualTo(new IpPort("127.0.0.1",8080));

        String ipPortStr = conversionService.convert(new IpPort("127.0.0.1", 8080), String.class);
        assertThat(ipPortStr).isEqualTo("127.0.0.1:8080");

    }
}
