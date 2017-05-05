package de.codecentric;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestServiceApplicationTests {

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

	private void assertApplicationClassHasAnnotation(String annotationName) {
		Optional<Annotation> optionalAnnotation = Arrays.stream(RestServiceApplication.class.getAnnotations())
				.filter(annotation -> annotation.annotationType().getSimpleName().equals(annotationName))
				.findAny();

		assertTrue(optionalAnnotation.isPresent());
	}

	private void assertConfigExists(String fileName) {
		URL resource = getClass().getClassLoader().getResource(fileName);
		assertNotNull(resource);
	}

}
