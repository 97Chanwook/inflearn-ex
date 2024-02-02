package com.wookis.ex.typeconverter;

import com.wookis.ex.typeconverter.converter.IntegerToStringConverter;
import com.wookis.ex.typeconverter.converter.IpPortToStringConverter;
import com.wookis.ex.typeconverter.converter.StringToIntegerConverter;
import com.wookis.ex.typeconverter.converter.StringToIpPortConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConvertConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToIntegerConverter());
        registry.addConverter(new IntegerToStringConverter());
        registry.addConverter(new StringToIpPortConverter());
        registry.addConverter(new IpPortToStringConverter());
    }
}
