package json;

import json.config.SpringConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
<<<<<<< HEAD
=======
import org.springframework.context.annotation.ComponentScan;
>>>>>>> b82eb2d4510b17a80f3512b8cb8f1816841371c0
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Z0522
 */
@EnableAsync
/*@EnableScheduling*/
@SpringBootApplication
@MapperScan("json.mapper")
@Import(SpringConfiguration.class)

public class App extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 设置启动类,用于独立tomcat运行的入口
        return builder.sources(App.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}


