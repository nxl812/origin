package com.nxl.config;

import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;

@Configuration
public class GsonConfig {

    @Value("${spring.gson.date-format:yyyy-MM-dd HH:mm:ss}")
    private String pattern;

    //使用gson作为默认的json解析器
    @Bean
    public HttpMessageConverters customConverters() {

        Collection<HttpMessageConverter<?>> messageConverters = new ArrayList<>();

//        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
//        fastJsonHttpMessageConverter.setDefaultCharset(Charset.forName("UTF-8"));
//        messageConverters.add(fastJsonHttpMessageConverter);

        GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
        gsonHttpMessageConverter.setDefaultCharset(Charset.forName("UTF-8"));
        gsonHttpMessageConverter.setGson(new GsonBuilder().setDateFormat(pattern).create());
        messageConverters.add(gsonHttpMessageConverter);

        return new HttpMessageConverters(true, messageConverters);
    }
}
