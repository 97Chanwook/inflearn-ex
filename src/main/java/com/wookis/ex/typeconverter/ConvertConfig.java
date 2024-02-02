package com.wookis.ex.typeconverter;

import com.wookis.ex.typeconverter.converter.IntegerToStringConverter;
import com.wookis.ex.typeconverter.converter.IpPortToStringConverter;
import com.wookis.ex.typeconverter.converter.StringToIntegerConverter;
import com.wookis.ex.typeconverter.converter.StringToIpPortConverter;
import com.wookis.ex.typeconverter.formatter.MyNumberFormatter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConvertConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        //우선순위 주석 [컨버터 -> 포멧터]
//        registry.addConverter(new StringToIntegerConverter());
//        registry.addConverter(new IntegerToStringConverter());
        registry.addConverter(new StringToIpPortConverter());
        registry.addConverter(new IpPortToStringConverter());

        //포멧터 추가
        registry.addFormatter(new MyNumberFormatter());
    }
}
