// package shops.example.shops.auth.controller;

// import static org.mockito.Mockito.when;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;
// import shops.example.shops.auth.dto.LoginUserDto;
// import shops.example.shops.auth.dto.RegisterUserDto;
// import shops.example.shops.auth.entity.User;
// import shops.example.shops.auth.responses.LoginResponse;
// import shops.example.shops.auth.service.AuthenticationService;
// import shops.example.shops.auth.service.JwtService;
// import java.util.UUID;





// @WebMvcTest(AuthenticationController.class)
// public class AuthenticationControllerTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @MockBean
//     private JwtService jwtService;

//     @MockBean
//     private AuthenticationService authenticationService;

//     @Autowired
//     private ObjectMapper objectMapper;

//     private User user;
//     private RegisterUserDto registerUserDto;
//     private LoginUserDto loginUserDto;
//     private LoginResponse loginResponse;

//     @BeforeEach
//     void setUp() {
//         user.setId(UUID.randomUUID());
//         user.setId(randomUUID());
//         user.setUsername("testuser");
//         user.setPassword("password");

//         registerUserDto = new RegisterUserDto();
//         registerUserDto.setUsername("testuser");
//         registerUserDto.setPassword("password");

//         loginUserDto = new LoginUserDto();
//         loginUserDto.setUsername("testuser");
//         loginUserDto.setPassword("password");

//         loginResponse = new LoginResponse();
//         loginResponse.setToken("jwt-token");
//         loginResponse.setExpiresIn(3600L);
//     }

//     @Test
//     void testRegister() throws Exception {
//         when(authenticationService.signup(registerUserDto)).thenReturn(user);

//         mockMvc.perform(post("/api/v1/auth/signup")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(objectMapper.writeValueAsString(registerUserDto)))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.id").value(user.getId()))
//                 .andExpect(jsonPath("$.username").value(user.getUsername()));
//     }

//     @Test
//     void testAuthenticate() throws Exception {
//         when(authenticationService.authenticate(loginUserDto)).thenReturn(user);
//         when(jwtService.generateToken(user)).thenReturn("jwt-token");
//         when(jwtService.getExpirationTime()).thenReturn(3600L);

//         mockMvc.perform(post("/api/v1/auth/login")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(objectMapper.writeValueAsString(loginUserDto)))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.token").value("jwt-token"))
//                 .andExpect(jsonPath("$.expiresIn").value(3600L));
//     }
// }