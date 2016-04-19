package com.pigatron;

import com.pigatron.server.security.SecUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.mongodb.core.MongoTemplate;

public abstract class AbstractIntegrationTest {

    @Value("${url.admin}")
    private String adminUrl;

    @Value("${url.shop}")
    private String shopUrl;

    @Value("${local.server.port}")
    private String serverPort;

    protected String adminApiUrl;
    protected String publicApiUrl;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private SecUserDetailsService userDetailsService;


    private String getAdminApiUrl() {
        return "http://localhost:" + serverPort + "/" + adminUrl + "/api";
    }

    private String getPublicApiUrl() {
        return "http://localhost:" + serverPort + "/" + shopUrl + "/api";
    }

    private void setApiUrl(String apiUrl) {
        adminApiUrl = getAdminApiUrl() + apiUrl;
        publicApiUrl = getPublicApiUrl() + apiUrl;
    }

    private void dbSetup() {
        mongoTemplate.getDb().dropDatabase();
        publisher.publishEvent(new ContextRefreshedEvent(context)); //force initial data setup
        userDetailsService.createAdminUser("admin", "password");
    }

    protected void setup(String apiUrl) {
        setApiUrl(apiUrl);
        dbSetup();
    }

}
