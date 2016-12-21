package examples.monitoring.com;

import monitoring.com.server.controller.EnableMonitorServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.concurrent.TimeUnit;

import static org.springframework.http.ResponseEntity.ok;

@SpringBootApplication
@EnableEurekaClient
@EnableMonitorServer
//@EnableMonitorClient
@Controller
@EnableConfigurationProperties({ZipkinUiProperties.class})
public class MonitorServerApplication {

    @Value("classpath:monitorui/monitorui.html")
    Resource testHtml;

    @Value("classpath:zipkinui/index.html")
    Resource indexHtml;

    public static void main(String[] args) {
        SpringApplication.run(MonitorServerApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {

        return new WebMvcConfigurerAdapter() {

            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/monitorui/**")
                        .addResourceLocations("classpath:/monitorui/");
                registry.addResourceHandler("/zipkinui/**")
                        .addResourceLocations("classpath:/zipkinui/");

                registry.addResourceHandler(new String[]{"/hystrix"})
                        .addResourceLocations(new String[]{"classpath:/hystrix/"});

            }

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/monitor/push").allowedOrigins("http://localhost:4200");
                registry.addMapping("/monitor").allowedOrigins("http://localhost:4200");
                registry.addMapping("/monitor/push").allowedOrigins("http://localhost:4200/");
                registry.addMapping("/monitor").allowedOrigins("http://localhost:4200/");
            }
        };
    }
    @Autowired
    ZipkinUiProperties ui;

    @RequestMapping(value = "/monitor.html", method = RequestMethod.GET)
    public ResponseEntity<Resource> serveIndex() {
        return ok()
                .cacheControl(CacheControl.maxAge(1, TimeUnit.MINUTES))
                .body(testHtml);
    }

    @RequestMapping(value = "/zipkin.html", method = RequestMethod.GET)
    public ResponseEntity<Resource> indexHtml() {
        return ok()
                .cacheControl(CacheControl.maxAge(1, TimeUnit.MINUTES))
                .body(indexHtml);
    }
    @RequestMapping(
            value = {"/zipkinui/config.json"},
            method = {RequestMethod.GET},
            produces = {"application/json"}
    )
    public ResponseEntity<ZipkinUiProperties> serveUiConfig() {
        return ((ResponseEntity.BodyBuilder)ResponseEntity.ok().cacheControl(CacheControl.maxAge(10L, TimeUnit.MINUTES))).body(this.ui);
    }
    @RequestMapping(
            value = {"/zipkinui", "/zipkinui/traces/{id}", "/zipkinui/dependency"},
            method = {RequestMethod.GET}
    )
    public ModelAndView forwardUiEndpoints(ModelMap model) {
        return new ModelAndView("forward:/zipkin.html", model);
    }
}
