package zlo.projeto.backendtcc.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceStorageDTO {

    @Size(max = 255)
    @NotNull
    private String tokenDispositivo;

    @NotNull
    private String cpfResponsavel;
}
