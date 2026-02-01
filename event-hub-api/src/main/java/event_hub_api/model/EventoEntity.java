package event_hub_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TBL_EVENTO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CD_EVENTO")
    private Long id;

    @Column(name = "TX_NOME", nullable = false)
    private String nome;

    @Column(name = "DT_EVENTO", nullable = false)
    private LocalDateTime dataEvento;

    @Column(name = "TX_LOCAL", nullable = false)
    private String local;

    @Column(name = "NR_CAPACIDADE", nullable = false)
    private Integer capacidade;

    @Column(name = "NR_VAGAS_DISPONIVEIS", nullable = false)
    private Integer vagasDisponiveis;

    @Version
    @Column(name = "NR_VERSION")
    private Long version;

    @Column(name = "DT_GRAVACAO")
    private LocalDateTime dtGravacao = LocalDateTime.now();

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IngressoEntity> ingressos = new ArrayList<>();

}