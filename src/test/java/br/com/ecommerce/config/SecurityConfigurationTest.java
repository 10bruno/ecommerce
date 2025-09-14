package br.com.ecommerce.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SecurityConfigurationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Deve permitir acesso público aos endpoints de parâmetros")
    void testParameterEndpointsArePublic() throws Exception {
        mockMvc.perform(get("/parameter/list"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/parameter/sequence"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve permitir acesso público aos endpoints do Swagger")
    void testSwaggerEndpointsArePublic() throws Exception {
        // Testa se os padrões do Swagger são públicos - retorno 404 é aceito pois significa que passou da autenticação
        mockMvc.perform(get("/swagger-ui/test.html"))
                .andExpect(status().isNotFound());

        mockMvc.perform(get("/v3/api-docs/test"))
                .andExpect(status().isNotFound());

        mockMvc.perform(get("/swagger-resources/test"))
                .andExpect(status().isNotFound());

        mockMvc.perform(get("/webjars/test"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve permitir acesso público aos endpoints do Actuator")
    void testActuatorEndpointsArePublic() throws Exception {
        // Testa se os padrões do Actuator são públicos - retorno 404 é aceito pois significa que passou da autenticação
        mockMvc.perform(get("/actuator/test"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve negar acesso aos endpoints protegidos sem autenticação")
    void testProtectedEndpointsRequireAuthentication() throws Exception {
        // Testa endpoints que devem estar protegidos
        mockMvc.perform(get("/customer"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(post("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/product"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/inventory"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/payment-historic"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"PAGAMENTOS"})
    @DisplayName("Deve permitir acesso aos endpoints protegidos com usuário autenticado")
    void testProtectedEndpointsAccessibleWithAuthentication() throws Exception {
        // Com usuário autenticado, não deve retornar 401 - pode retornar outros códigos como 404, 500, etc.
        mockMvc.perform(get("/customer"))
                .andExpect(status().is(not(401)));

        mockMvc.perform(get("/product"))
                .andExpect(status().is(not(401)));

        mockMvc.perform(get("/inventory"))
                .andExpect(status().is(not(401)));

        mockMvc.perform(get("/payment-historic"))
                .andExpect(status().is(not(401)));
    }

    @Test
    @WithMockUser(username = "testuser")
    @DisplayName("Deve permitir acesso aos endpoints mesmo sem roles específicas")
    void testEndpointsAccessibleWithoutSpecificRoles() throws Exception {
        // A configuração atual não requer roles específicas, apenas autenticação
        mockMvc.perform(get("/customer"))
                .andExpect(status().is(not(401)));
    }

    @Test
    @DisplayName("Deve rejeitar credenciais inválidas")
    void testInvalidCredentialsAreRejected() throws Exception {
        mockMvc.perform(get("/customer")
                        .header("Authorization", "Basic aW52YWxpZDppbnZhbGlk")) // invalid:invalid em base64
                .andExpect(status().isUnauthorized());
    }


    @Test
    @DisplayName("CSRF deve estar desabilitado")
    void testCsrfIsDisabled() throws Exception {
        // Se CSRF estivesse habilitado, este POST falharia
        mockMvc.perform(post("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isUnauthorized()) // 401 por falta de auth, não 403 por CSRF
                .andExpect(status().is(not(403)));
    }

    @Test
    @DisplayName("HTTP Basic authentication deve estar habilitado")
    void testHttpBasicAuthenticationEnabled() throws Exception {
        mockMvc.perform(get("/customer"))
                .andExpect(status().isUnauthorized())
                .andExpect(header().exists("WWW-Authenticate"))
                .andExpect(header().string("WWW-Authenticate", "Basic realm=\"Realm\""));
    }
}