package cz.muni.fi.pa165.dmbk.machinerental;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Configures OpenAPI with swagger UI which automates the generation
 * of machine and human readable specifications for JSON APIs
 * written using the spring family of projects.<br />
 * <b><i>/v2/api-docs</i></b> and <b><i>/swagger-ui.html</i></b>
 * paths within deployed application can be used to see documentation
 * for all of the REST APIs contained by <b><i>cz.fi.muni.pa165.dmbk.machinerental</i></b>
 * packages.
 *
 * @author Norbert Dopjera 456355@mail.muni.cz
 */
@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .servers(getServers())
                .components(new Components())
                .info(new Info()
                        .title("DMBK Machine Rental API")
                        .license(createLicense()));
    }

    private final License createLicense() {
        return new License()
                .name("©DMBK Ltd.");
    }

    private final List<Server> getServers() {
        return Collections.singletonList(new Server().url("http://localhost:8080/pa165/"));
    }
}
