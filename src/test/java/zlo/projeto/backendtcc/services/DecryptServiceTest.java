package zlo.projeto.backendtcc.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import zlo.projeto.backendtcc.exceptions.RequiredObjectIsNullException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DecryptServiceTest {

    @Autowired
    private DecryptService decryptService;

    private String testUrl;

    @BeforeEach
    public void setUp() {
        testUrl = "cpfDep=99988877766&emergPhone=21987654321";
    }

    @Test
    public void testEncryptUrl_validInput_returnsEncryptedString() {
        String encrypted = decryptService.encryptUrl(testUrl);
        assertNotNull(encrypted, "Encrypted string should not be null");
        assertNotEquals(testUrl, encrypted, "Encrypted string should be different from the original input");
    }

    @Test
    public void testDecryptUrl_validEncryptedString_returnsDecryptedString() {
        String encrypted = decryptService.encryptUrl(testUrl);
        String decrypted = decryptService.decryptUrl(encrypted);
        assertEquals(testUrl, decrypted, "Decrypted string should match the original input");
    }

    @Test
    public void testEncryptUrl_nullInput_throwsException() {
        assertThrows(RequiredObjectIsNullException.class, () -> decryptService.encryptUrl(null),
                "Encrypting a null URL should throw RequiredObjectIsNullException");
    }

    @Test
    public void testEncryptUrl_emptyInput_throwsException() {
        assertThrows(RequiredObjectIsNullException.class, () -> decryptService.encryptUrl(""),
                "Encrypting an empty URL should throw RequiredObjectIsNullException");
    }

    @Test
    public void testDecryptUrl_nullInput_throwsException() {
        assertThrows(RequiredObjectIsNullException.class, () -> decryptService.decryptUrl(null),
                "Decrypting a null URL should throw RequiredObjectIsNullException");
    }

    @Test
    public void testDecryptUrl_emptyInput_throwsException() {
        assertThrows(RequiredObjectIsNullException.class, () -> decryptService.decryptUrl(""),
                "Decrypting an empty URL should throw RequiredObjectIsNullException");
    }

    @Test
    public void testDecryptUrl_invalidEncryptedString_throwsException() {
        String invalidEncryptedString = "invalid_encrypted_data";
        assertThrows(RequiredObjectIsNullException.class, () -> decryptService.decryptUrl(invalidEncryptedString),
                "Decrypting an invalid encrypted string should throw RequiredObjectIsNullException");
    }
}
