package event_hub_api.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventoRequestDTO {
    @NotBlank(message = "O nome do evento é obrigatório")
    private String nome;

    @NotNull(message = "A data é obrigatória")
    private LocalDateTime dataEvento;

    @NotBlank(message = "O local é obrigatório")
    private String local;

    @NotNull(message = "A capacidade é obrigatória")
    @Min(value = 1, message = "A capacidade deve ser de pelo menos 1 pessoa")
    private Integer capacidade;
}
