package event_hub_api.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TBL_INGRESSO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IngressoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CD_INGRESSO")
    private Long id;

    @Column(name = "CD_CODIGO", nullable = false, unique = true)
    private String codigo;

    @ManyToOne
    @JoinColumn(name = "CD_EVENTO", nullable = false)
    private EventoEntity evento;

    @ManyToOne
    @JoinColumn(name = "CD_PARTICIPANTE", nullable = false)
    private ParticipanteEntity participante;

    @Column(name = "DT_COMPRA", nullable = false)
    private LocalDateTime dtCompra = LocalDateTime.now();

    @Column(name = "TX_OBSERVACAO")
    private String txObservacao;
}