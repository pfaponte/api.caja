package ec.edu.espam.api.caja.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.edu.espam.api.caja.Application;
import ec.edu.espam.api.caja.domain.Client;
import ec.edu.espam.api.caja.domain.auth.UserInfo;
import ec.edu.espam.api.caja.domain.dto.ClientDto;
import ec.edu.espam.api.caja.repository.ClientRepository;
import ec.edu.espam.api.caja.repository.auth.UserInfoRepository;
import ec.edu.espam.api.caja.service.auth.JwtService;
import ec.edu.espam.api.caja.service.auth.UserInfoService;
import ec.edu.espam.api.caja.util.Mapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
        classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClientIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    ObjectMapper objectMapper;

    static Client client;
    static String accessToken;

    public static final MediaType APPLICATION_JSON_UTF8 = MediaType.APPLICATION_JSON;

    @BeforeAll
    void preconditionCreate() {
        createUser();
        createClient();
    }

    void createClient() {
        client = clientRepository.findByDni("1103654578")
                .orElseGet(() -> {
                    Client newClient = Client.builder()
                            .name("Pedro")
                            .gender("M")
                            .age(37)
                            .dni("1103654578")
                            .address("Loja")
                            .phone("0991425658")
                            .password("1234")
                            .state(true)
                            .build();
                    return clientRepository.save(newClient);
                });
    }

    void createUser() {
        UserInfo user = userInfoRepository.findByName("pedro")
                .orElseGet(() -> {
                    UserInfo userNew = new UserInfo();
                    userNew.setPassword(encoder.encode("1234"));
                    userNew.setName("pedro");
                    userNew.setEmail("pedro@gmail.com");
                    userNew.setRoles("ROLE_ADMIN");
                    return userInfoRepository.save(userNew);
                });

        accessToken = jwtService.generateToken(user.getName());
    }

    @Test
    void getById() throws Exception {
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/clientes/" + client.getId())
                .header("Authorization", "Bearer " + accessToken));
        response.andExpect(status().isOk());
        response.andExpect(jsonPath("$.dni", is("1103654578")));
    }
    
    @Test
    void getAll() throws Exception {
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/clientes")
                .header("Authorization", "Bearer " + accessToken));
        response.andExpect(status().isOk());
        response.andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    void create() throws Exception {
        ClientDto clientDto = ClientDto.builder()
                .name("Pedro")
                .gender("M")
                .age(37)
                .dni("113234565")
                .address("Loja")
                .phone("0991425678")
                .password("1234")
                .state(true)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/clientes")
                .contentType(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(clientDto))
                .header("Authorization", "Bearer " + accessToken)
        ).andExpect(status().isCreated());

        Optional<Client> optional = clientRepository.findByDni("113234565");

        assertTrue(optional.isPresent());

        clientRepository.delete(optional.get());
    }

    @Test
    void delete() throws Exception {
        assertTrue(clientRepository.findByDni(client.getDni()).isPresent());

        mockMvc.perform(MockMvcRequestBuilders.delete("/clientes/{id}", client.getId())
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk());

        assertFalse(clientRepository.findByDni(client.getDni()).isPresent());
        createClient();
    }

    @Test
    void deleteError() throws Exception {
        assertFalse(clientRepository.findById(0l).isPresent());

        mockMvc.perform(MockMvcRequestBuilders.delete("/clientes/{id}", 0)
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message", is("Client not found")));
    }

    @Test
    void update() throws Exception {
        assertTrue(clientRepository.findByDni(client.getDni()).isPresent());

        ClientDto clientDto = convertEntityToDto(client);
        clientDto.setName("Fernando");

        mockMvc.perform(MockMvcRequestBuilders.put("/clientes/{id}", client.getId())
                .contentType(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(clientDto))
                .header("Authorization", "Bearer " + accessToken)
        ).andExpect(status().isOk());

        Optional<Client> optional = clientRepository.findByDni("1103654578");
        assertTrue(optional.isPresent());
        assertEquals("Fernando", optional.get().getName());
    }

    private ClientDto convertEntityToDto(Client entity) {
        return Mapper.modelMapper().map(entity, ClientDto.class);
    }
}
