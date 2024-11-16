package zlo.projeto.backendtcc.dto.commands.notification;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationRequestDTO {
    @NotBlank(message = "O título é obrigatório")
    private String title;

    @NotBlank(message = "A mensagem é obrigatória")
    private String body;

    @NotBlank(message = "O CPF do responsável é obrigatório")
    private String cpfResponsavel;

    // Getters e setters
}

