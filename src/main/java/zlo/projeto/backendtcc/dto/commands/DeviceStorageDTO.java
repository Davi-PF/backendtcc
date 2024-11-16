package zlo.projeto.backendtcc.dto.commands;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class DeviceStorageDTO extends RepresentationModel<DeviceStorageDTO> implements Serializable {

    @Serial
    private static final long serialVersionUID = 8115865406889814562L;

    @Size(max = 255, message = "O token do dispositivo deve ter no máximo 255 caracteres.")
    @NotBlank(message = "Token do dispositivo não pode ser vazio")
    private String tokenDispositivo;

    @NotBlank(message = "CPF do responsável não pode ser vazio")
    private String cpfResponsavel;

    public DeviceStorageDTO(String token, String cpf) {
        this.tokenDispositivo = token;
        this.cpfResponsavel = cpf;
    }

    public DeviceStorageDTO() {

    }
}
