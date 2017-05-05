package de.codecentric;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.type.MethodMetadata;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.support.GenericWebApplicationContext;

import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestConsumerApplicationTests {
    @Autowired
    private GenericWebApplicationContext applicationContext;

    @Autowired

    @Test
    public void contextLoads() {
    }

    @Test
    public void discoveryClientShouldBeConfigured() {
        assertApplicationClassHasAnnotation("EnableDiscoveryClient");
    }

    @Test
    public void bootstrapConfigShouldExist() {
        assertConfigExists("bootstrap.yml");
    }

    @Test
    public void restTemplateShouldExist() {
        applicationContext.getBean("restTemplate", RestTemplate.class);
    }

    @Test
    public void restTemplateShouldBeLoadBalanced() {
        assertBeanIsAnnotatedWith("restTemplate",
                "org.springframework.cloud.client.loadbalancer.LoadBalanced");
    }

    private void assertApplicationClassHasAnnotation(String annotationName) {
        Optional<Annotation> optionalAnnotation = Arrays.stream(RestConsumerApplication.class.getAnnotations())
                .filter(annotation -> annotation.annotationType().getSimpleName().equals(annotationName))
                .findAny();

        assertTrue(optionalAnnotation.isPresent());
    }

    private void assertConfigExists(String fileName) {
        URL resource = getClass().getClassLoader().getResource(fileName);
        assertNotNull(resource);
    }

    private void assertBeanIsAnnotatedWith(String beanName, String annotationName) {
        BeanDefinitionRegistry beanDefinitionRegistry = (BeanDefinitionRegistry) applicationContext.getBeanFactory();
        BeanDefinition beanDefinition = beanDefinitionRegistry.getBeanDefinition(beanName);
        assertTrue(beanDefinition instanceof AnnotatedBeanDefinition);
        assertTrue(beanDefinition.getSource() instanceof MethodMetadata);

        MethodMetadata beanMethod = (MethodMetadata) beanDefinition.getSource();
        assertTrue(beanMethod.isAnnotated(annotationName));
    }

}
