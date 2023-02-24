package com.sube.reggie.config

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport

/**
 * @Author : Sube
 * @Date : 2023/2/23 19:30
 * @Version : 1.0
 * @Description : web资源配置
 */

@Configuration
class WebMvcConfig : WebMvcConfigurationSupport() {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {

        logger.info("---静态资源映射---")

        registry.addResourceHandler("/backend/**")
            .addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**")
            .addResourceLocations("classpath:/front/**");
    }

    /**
     * 扩展mvc消息转换器
     */
    override fun extendMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
        // 创建消息转换器对象
        val messageConverter = MappingJackson2HttpMessageConverter()
        // 设置对象转换器，底层使用Jackson将Java对象转为json
        messageConverter.objectMapper = JacksonObjectMapper()
        // 将上面的消息转换器对象追加到mvc框架的转换器容器中
        converters.add(0, messageConverter)
    }
}