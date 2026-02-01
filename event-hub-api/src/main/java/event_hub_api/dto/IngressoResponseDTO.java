package event_hub_api.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class IngressoResponseDTO {
    private String codigo;
    private String nomeEvento;
    private LocalDateTime dataEvento;
    private String nomeParticipante;
    private LocalDateTime dataCompra;
}
