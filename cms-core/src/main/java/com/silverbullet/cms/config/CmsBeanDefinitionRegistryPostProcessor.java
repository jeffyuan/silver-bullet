package com.silverbullet.cms.config;

import com.silverbullet.cms.service.impl.FastDfsFileServiceImpl;
import com.silverbullet.cms.service.impl.FileServiceImpl;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by jeffyuan on 2018/3/19.
 */
@Configuration
public class CmsBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    private static final Logger logger = LoggerFactory.getLogger(CmsBeanDefinitionRegistryPostProcessor.class);

    private ScopeMetadataResolver scopeMetadataResolver = new AnnotationScopeMetadataResolver();
    private BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();

    /**
     * 获取配置文件参数信息
     * @param propertiesPath 配置文件名称，默认在resource下
     * @return
     */
    private Map<String, String> getProperties(String propertiesPath) {
        HashMap<String, String> propertiesMap = new HashMap<String, String>();
        try {
            Properties properties = PropertiesLoaderUtils.loadAllProperties(propertiesPath);

            for (Object key : properties.keySet()) {
                String keyStr = key.toString();

                try {
                    //PropertiesLoaderUtils的默认编码是ISO-8859-1,在这里转码一下
                    propertiesMap.put(keyStr, new String(properties.getProperty(keyStr).getBytes("ISO-8859-1"),"utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }catch (Exception e){
                    e.printStackTrace();
                };
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return propertiesMap;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {

        Map<String, String> propertiesMap = getProperties("repository.properties");

        String fileEngine = propertiesMap.get("file.engine");
        Class beanClass = null;
        if (fileEngine.equals("localFileEngine")) {
            beanClass = FileServiceImpl.class;
        } else if (fileEngine.equals("FastDfsEngine")) {
            beanClass = FastDfsFileServiceImpl.class;
        }

        AnnotatedGenericBeanDefinition abd = new AnnotatedGenericBeanDefinition(beanClass);

        ScopeMetadata scopeMetadata = this.scopeMetadataResolver.resolveScopeMetadata(abd);
        abd.setScope(scopeMetadata.getScopeName());

        AnnotationConfigUtils.processCommonDefinitionAnnotations(abd);

        BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(abd, fileEngine);
        BeanDefinitionReaderUtils.registerBeanDefinition(definitionHolder, beanDefinitionRegistry);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {


    }
}
