package com.blog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.logging.Logger;

@SpringBootApplication
@Slf4j
public class BlogAdminApplication {

    public static void main(String[] args) throws UnknownHostException {
        //SpringApplication.run(BlogAdminApplication.class, args);

        SpringApplication app = new SpringApplication(BlogAdminApplication.class);
        Environment env = app.run(args).getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = Optional.ofNullable(env.getProperty("server.servlet.context-path")).orElse("");
        log.info("\n----------------------------------------------------------\n\t" +
                "Application is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + path + "/\n\t" +
                "External: \thttp://" + ip + ":" + port + path + "/\n\t" +
                // "Swagger3文档: \thttp://" + ip + ":" + port + path + "/swagger-ui/index.html\n" +
                "----------------------------------------------------------");
    }

}
