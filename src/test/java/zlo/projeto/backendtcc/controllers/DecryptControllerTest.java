package zlo.projeto.backendtcc.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import zlo.projeto.backendtcc.services.DecryptService;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DecryptController.class)
public class DecryptControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DecryptService decryptService;

    @Test
    public void testEncryptUrl_validRequest_returnsEncryptedString() throws Exception {
        // Define o comportamento esperado do serviço mockado
        String rawString = "cpfDep=123456789&emergPhone=987654321";
        String encryptedString = "encrypted_data";
        when(decryptService.encryptUrl(anyString())).thenReturn(encryptedString);

        // Executa a requisição simulada em formato form-data
        mockMvc.perform(post("/api/url/encrypt")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("request", rawString)) // Envia o parâmetro como form-data
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(encryptedString)));
    }

    @Test
    public void testDecryptUrl_validRequest_returnsDecryptedString() throws Exception {
        // Define o comportamento esperado do serviço mockado
        String encryptedString = "encrypted_data";
        String decryptedString = "decrypted_data";
        when(decryptService.decryptUrl(anyString())).thenReturn(decryptedString);

        // Executa a requisição simulada em formato form-data
        mockMvc.perform(post("/api/url/decrypt")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .param("request", encryptedString)) // Envia o parâmetro como form-data
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(decryptedString)));
    }
}