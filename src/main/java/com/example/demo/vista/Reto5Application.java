package com.example.demo.vista;

import com.example.demo.controlador.ControladorProducto;
import com.example.demo.modelo.RepositorioProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication
@ComponentScan("com.example.demo.modelo")
@EnableJdbcRepositories("com.example.demo.modelo")
public class Reto5Application {

    @Autowired
    private RepositorioProducto repositorio;

    public static void main(String[] args) {
        //SpringApplication.run(Reto5Application.class, args);
        SpringApplicationBuilder builder = new SpringApplicationBuilder(Reto5Application.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);
    }

     @Bean
    ApplicationRunner applicationRnner(){
        return args -> {
            PantallaProducto vista = new PantallaProducto();
            ControladorProducto controlador = new ControladorProducto(vista, repositorio);
            vista.setVisible(true);
        };
    }

}
