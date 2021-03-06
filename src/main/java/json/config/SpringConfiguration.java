package json.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import json.body.VisiableThreadPoolTaskExecutor;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import json.service.DemoService;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;

import javax.xml.ws.Endpoint;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Z0522
 */
@Configurable
public class SpringConfiguration {
    //webservice配置类

    @Configuration
    public class CxfConfig {
        @Autowired
        private Bus bus;

        @Autowired
        DemoService demoService;

        /**
         * 此方法作用是改变项目中服务名的前缀名，此处127.0.0.1或者localhost不能访问时，请使用ipconfig查看本机ip来访问
         * 此方法被注释后:wsdl访问地址为http://127.0.0.1:8080/services/user?wsdl
         * 去掉注释后：wsdl访问地址为：http://127.0.0.1:8080/soap/user?wsdl
         *
         * @return
         */
        @Bean
        public ServletRegistrationBean restServlet() {
            //注解扫描上下文
            AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
            //base package
//        applicationContext.scan("com.wiseda.xygzcs");
            //通过构造函数指定dispatcherServlet的上下文
            DispatcherServlet rest_dispatcherServlet = new DispatcherServlet(applicationContext);

            //用ServletRegistrationBean包装servlet
            ServletRegistrationBean registrationBean = new ServletRegistrationBean(rest_dispatcherServlet);
            registrationBean.setLoadOnStartup(1);
            //指定urlmapping
            registrationBean.addUrlMappings("/*");
            //指定name，如果不指定默认为dispatcherServlet
            registrationBean.setName("rest");
            return registrationBean;
        }

        @SuppressWarnings("all")
        @Bean
        public ServletRegistrationBean dispatcherServlet() {
            return new ServletRegistrationBean(new CXFServlet(), "/soap/*");
        }

        /**
         * JAX-WS
         * 站点服务
         **/
        @SuppressWarnings("all")
        @Bean
        public Endpoint endpoint() {
            EndpointImpl endpoint = new EndpointImpl(bus, demoService);
            endpoint.publish("/user");
            return endpoint;
        }

    }

    //json转json对象

    @Configuration
    class JacksonConfiguration {
        @Bean
        public ObjectMapper objectMapper() {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
            return objectMapper;
        }

    }

    //格式化日期

    @Configuration
    static class FastJsonHttpDateConverter extends FastJsonHttpMessageConverter {

        private static SerializeConfig mapping = new SerializeConfig();
        private static String dateFormat;

        static {
            dateFormat = "yyyy-MM-dd";
            mapping.put(Date.class, new SimpleDateFormatSerializer(dateFormat));
        }

        @Override
        protected void writeInternal(Object obj, HttpOutputMessage outputMessage)
                throws IOException, HttpMessageNotWritableException {
            // TODO Auto-generated method stub

            OutputStream out = outputMessage.getBody();
            String text = JSON.toJSONString(obj, mapping, this.getFeatures());
            byte[] bytes = text.getBytes(this.getCharset());
            out.write(bytes);
        }

    }

    //跨域访问

    @Configuration
    class CorsConfig extends WebMvcConfigurerAdapter {

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOrigins("*")
                    .allowedHeaders("*")
                    .allowCredentials(true)
                    .allowedMethods("GET", "POST", "DELETE", "PUT")
                    .maxAge(3600);
        }

    }

    //线程池配置

    @Configuration
    @Slf4j
    @Component
    static class AsyncTaskConfig implements AsyncConfigurer {
        /**
         * ThredPoolTaskExcutor的处理流程 当池子大小小于corePoolSize，就新建线程，并处理请求
         * 当池子大小等于corePoolSize，把请求放入workQueue中，池子里的空闲线程就去workQueue中取任务并处理
         * 当workQueue放不下任务时，就新建线程入池，并处理请求，如果池子大小撑到了maximumPoolSize，就用RejectedExecutionHandler来做拒绝处理
         * 当池子的线程数大于corePoolSize时，多余的线程会等待keepAliveTime长时间，如果无请求可处理就自行销毁
         */
        @Override
        @Bean(name = "asyncExecutor")
        public Executor getAsyncExecutor() {
            ThreadPoolTaskExecutor executor = new VisiableThreadPoolTaskExecutor();
            // 最小线程数(核心线程数)
            executor.setCorePoolSize(5);
            // 最大线程数
            executor.setMaxPoolSize(10);
            // 等待队列(队列最大长度)
            executor.setQueueCapacity(25);
            // 线程池维护线程所允许的空闲时间 ，单位s
            executor.setKeepAliveSeconds(5);
            // 线程池对拒绝任务(无线程可用)的处理策略
            executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

            executor.initialize();
            log.info("线程池初始化完成...");
            return executor;
        }

        /**
         * 异步异常处理
         *
         * @return
         */
        @Override
        public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
            return new SpringAsyncExceptionHandler();
        }

        class SpringAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
            @Override
            public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {
                log.error("Exception occurs in async method", throwable.getMessage());
            }
        }
    }
}
