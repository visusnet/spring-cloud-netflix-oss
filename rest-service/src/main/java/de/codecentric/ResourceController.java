package de.codecentric;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ResourceController {
    @RequestMapping("/resource")
    public String ping() {
        return "Hello, World!";
    }
}
