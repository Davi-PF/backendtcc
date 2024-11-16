package zlo.projeto.backendtcc.dto.commands.notification;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Setter
@Getter
public class NotificationStorageDTO {

    @NotBlank(message = "O título da notificação precisa estar presente.")
    private String titulo;
    @NotBlank(message = "A notificação precisa ter uma mensagem")
    private String mensagem;
    @NotBlank(message = "O CPF do responsável deve ser informado.")
    private String cpfResponsavel;
    @NotBlank(message = "A data de envio da notificação precisa estar presente")
    private ZonedDateTime dataEnvio;
    private Boolean lida;

}

