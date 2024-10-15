package com.binitajha.lynx.server;

import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class SpringConfig {
//    @Bean
//    public static BeanDefinitionRegistryPostProcessor jobRegistryBeanPostProcessorRemover() {
//        return registry -> registry.removeBeanDefinition("jobRegistryBeanPostProcessor");
//
//    }
}
