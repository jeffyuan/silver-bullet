package com.silverbullet.msg.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.servlet.DispatcherServlet;

import javax.jms.Queue;
import javax.jms.Topic;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * ActiveMQ 配置
 * Created by jeffyuan on 2018/3/20.
 */
@Configuration
@EnableJms
public class ActiveMQConfig {

    @Value("${spring.activemq.broker-url}")
    private String brokenUrl;
    @Value("${spring.activemq.user}")
    private String user;
    @Value("${spring.activemq.password}")
    private String password;

    /**
     * 点对点
     * @return
     */
    @Bean
    public Queue queue(){
        return new ActiveMQQueue("task.queue");
    }

    /**
     * 发布/订阅
     * @return
     */
    @Bean
    public Topic topic(){
        return new ActiveMQTopic("sysmsg.topic");
    }

    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        return new ActiveMQConnectionFactory(user, password, brokenUrl);
    }

    /**
     * 注册topic的
     * @param connectionFactory
     * @return
     */
    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerTopic(ActiveMQConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
        bean.setPubSubDomain(true);

        bean.setConnectionFactory(connectionFactory);
        return bean;
    }

    /**
     * 注册queue的factory
     * @param connectionFactory
     * @return
     */
    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerQueue(ActiveMQConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
        bean.setConnectionFactory(connectionFactory);
        return bean;
    }

    /**
     * 注册jms模板对象
     * @param connectionFactory
     * @return
     */
    @Bean
    public JmsMessagingTemplate jmsMessagingTemplate(ActiveMQConnectionFactory connectionFactory){
        return new JmsMessagingTemplate(connectionFactory);
    }

    /**
     * 注入activemq的ajax控制器
     *
     */
     @Bean
     public ServletRegistrationBean ajaxServletRegistration() {
         DispatcherServlet dispatcherServlet = new DispatcherServlet();
         ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet);
         registration.setEnabled(true);
         registration.addUrlMappings("/amq/*");
         return registration;
     }

     //向servletContext中初始化activemq的消息访问url
     @Bean
     public ServletContextInitializer initializer() {
         return new ServletContextInitializer() {

             @Override
             public void onStartup(ServletContext servletContext) throws ServletException {
                servletContext.setInitParameter("org.apache.activemq.brokerURL", brokenUrl);
             }
         };
     }
}
