package io.github.ambitiousliu.jmp.config;

import io.github.ambitiousliu.jmp.constant.JmpConstant;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author ambitious liu
 * @since 2022-06-18
 */
@Configuration
@ComponentScan("io.github.ambitiousliu.jmp")
public class JmpConfig implements InitializingBean {

    @Override
    public void afterPropertiesSet() {
        System.out.println(JmpConstant.banner + JmpConstant.version);
    }
}
