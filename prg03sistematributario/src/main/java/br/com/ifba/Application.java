package br.com.ifba;

import br.com.ifba.usuario.view.TelaLogin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {

    public static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        context = new SpringApplicationBuilder(Application.class)
                      .headless(false)
                      .run(args);

        TelaLogin telalogin = context.getBean(TelaLogin.class);
        telalogin.setVisible(true);
    }
}
