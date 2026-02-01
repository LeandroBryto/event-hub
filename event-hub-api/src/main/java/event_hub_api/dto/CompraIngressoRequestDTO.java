package event_hub_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompraIngressoRequestDTO {
    @NotNull(message = "ID do evento é obrigatório")
    private Long idEvento;

    @NotBlank(message = "Nome do participante é obrigatório")
    private String nomeParticipante;

    @Email(message = "E-mail inválido")
    @NotBlank(message = "E-mail é obrigatório")
    private String emailParticipante;
}
