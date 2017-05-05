package de.codecentric;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConfigServiceApplicationTests {
	@Autowired
	private Environment environment;

	@Test
	public void contextLoads() {
	}

	@Test
	public void configServerShouldBeConfigured() {
		assertApplicationClassHasAnnotation("EnableConfigServer");
	}

	@Test
	public void discoveryServiceConfigShouldExist() {
		assertConfigExists("discovery-service.yml");
	}

	@Test
	public void edgeServiceConfigShouldExist() {
		assertConfigExists("edge-service.yml");
	}

	@Test
	public void restConsumerConfigShouldExist() {
		assertConfigExists("rest-consumer.yml");
	}

	@Test
	public void restServiceConfigShouldExist() {
		assertConfigExists("rest-service.yml");
	}

	@Test
	public void nativeProfileShouldBeActive() {
		assertTrue(Arrays.asList(environment.getActiveProfiles()).contains("native"));
	}

	private void assertApplicationClassHasAnnotation(String annotationName) {
		Optional<Annotation> optionalAnnotation = Arrays.stream(ConfigServiceApplication.class.getAnnotations())
				.filter(annotation -> annotation.annotationType().getSimpleName().equals(annotationName))
				.findAny();

		assertTrue(optionalAnnotation.isPresent());
	}

	private void assertConfigExists(String fileName) {
		URL resource = getClass().getClassLoader().getResource(fileName);
		assertNotNull(resource);
	}
}
