package unip.api.pedidos1.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração do Swagger / OpenAPI.
 * Depois de subir a aplicação, acesse:
 *   http://localhost:8000/swagger-ui/index.html  -> interface do Swagger
 *   http://localhost:8000/v3/api-docs            -> especificação OpenAPI em JSON
 */
@Configuration
public class Config {

    @Bean
    public OpenAPI pedidosOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Pedidos")
                        .description("Desenvolvimento de Sistemas Distribuídos - Trabalho 1: API de Pedidos")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Nome do grupo")
                                .email("email@exemplo.com")));
    }
}