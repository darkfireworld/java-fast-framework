package org.darkgem;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import org.darkgem.io.fs.FSIo;
import org.darkgem.io.mail.MailIo;
import org.darkgem.web.support.handler.TokenHandler;
import org.darkgem.web.support.msg.MessageExceptionHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@PropertySource("classpath:project.properties")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan("org.darkgem")
public class SpringConf {
    static Logger logger = LoggerFactory.getLogger(SpringConf.class);

    /**
     * 执行器配置
     */
    @Component
    @EnableAsync
    @EnableScheduling
    static class ExecutorConf implements SchedulingConfigurer, AsyncConfigurer {

        @Override
        public Executor getAsyncExecutor() {
            return Executors.newFixedThreadPool(10);
        }

        @Override
        public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
            return new AsyncUncaughtExceptionHandler() {
                @Override
                public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
                    logger.error(SpringConf.class.getName(), throwable);
                }
            };
        }

        @Override
        public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
            taskRegistrar.setScheduler(Executors.newScheduledThreadPool(1));
        }
    }

    /**
     * Io层配置
     */
    @Component
    @EnableTransactionManagement
    static class IoConf implements EnvironmentAware {
        Environment environment;

        //#fix null point environment
        @Override
        public void setEnvironment(Environment environment) {
            this.environment = environment;
        }

        @Bean(initMethod = "init", destroyMethod = "close")
        public DataSource dataSource() throws Exception {
            DruidDataSource druidDataSource = new DruidDataSource();
            druidDataSource.setDriverClassName(environment.getProperty("jdbc-class"));
            druidDataSource.setUrl(environment.getProperty("jdbc-url"));
            druidDataSource.setUsername(environment.getProperty("jdbc-username"));
            druidDataSource.setPassword(environment.getProperty("jdbc-password"));
            druidDataSource.setFilters("log4j");
            return druidDataSource;
        }

        @Bean
        public PlatformTransactionManager transactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }

        @Bean
        public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) {
            SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
            sqlSessionFactoryBean.setDataSource(dataSource);
            sqlSessionFactoryBean.setTypeHandlersPackage("org.darkgem.io");
            return sqlSessionFactoryBean;
        }

        @Bean
        public MapperScannerConfigurer mapperScannerConfigurer() {
            MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
            mapperScannerConfigurer.setBasePackage("org.darkgem.io");
            return mapperScannerConfigurer;
        }

        @Bean
        public MailIo mailIo() {
            MailIo mailIo = new MailIo();
            mailIo.setEmail(environment.getProperty("email-account"));
            mailIo.setSmtp(environment.getProperty("email-smtp"));
            return mailIo;
        }

        @Bean
        public FSIo fsIo() {
            FSIo fsIo = new FSIo();
            fsIo.setUrl(environment.getProperty("fs"));
            return fsIo;
        }

    }

    /**
     * Spring MVC 配置
     */
    @Component
    @EnableWebMvc
    static class SpringMvcConf extends WebMvcConfigurerAdapter {
        //Spring MVC
        @Bean
        public MultipartResolver multipartResolver() {
            CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
            commonsMultipartResolver.setMaxUploadSize(1024 * 1024 * 5);
            return commonsMultipartResolver;
        }

        @Override
        public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
            argumentResolvers.add(new TokenHandler());
        }

        @Override
        public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
            configurer.enable();
        }

        @Override
        public void configureViewResolvers(ViewResolverRegistry registry) {
            registry.order(1);

            FastJsonJsonView fastJsonJsonView = new FastJsonJsonView();
            fastJsonJsonView.setExtractValueFromSingleKeyModel(true);

            registry.enableContentNegotiation(fastJsonJsonView);

        }

        @Override
        public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
            exceptionResolvers.add(new MessageExceptionHandler());
        }
    }


}
