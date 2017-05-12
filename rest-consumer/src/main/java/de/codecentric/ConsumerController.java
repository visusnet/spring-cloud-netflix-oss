package de.codecentric;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerController {
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/doSomething")
    public String doSomething() {
        return restTemplate.getForObject("http://localhost:9001/resource", String.class);
    }
}
