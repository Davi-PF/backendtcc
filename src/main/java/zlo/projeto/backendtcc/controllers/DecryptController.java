package zlo.projeto.backendtcc.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zlo.projeto.backendtcc.services.DecryptService;

@RestController
@RequestMapping("/api/url")
@Tag(name = "Encrypt", description = "Endpoints para lidar com a Criptografia.")
public class DecryptController {

    private final DecryptService decryptService;

    public DecryptController(DecryptService decryptService) {
        this.decryptService = decryptService;
    }

    @Operation(summary = "Encrypt an url")
    @PostMapping(value = "/encrypt")
    public String encryptUrl(@RequestBody String request) {

        return decryptService.encryptUrl(request);
    }

    @Operation(summary = "Decrypt an url")
    @PostMapping(value = "/decrypt")
    public String decryptUrl(@RequestBody String request) {

        return decryptService.decryptUrl(request);
    }
}
