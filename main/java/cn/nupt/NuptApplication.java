package cn.nupt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class NuptApplication {

    public static void main(String[] args) {
        SpringApplication.run(NuptApplication.class, args);
    }

}
