package event_hub_api.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventoResponseDTO {
    private Long id;
    private String nome;
    private LocalDateTime dataEvento;
    private String local;
    private Integer capacidade;
    private Integer vagasDisponiveis;
}
