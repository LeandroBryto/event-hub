package event_hub_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TBL_PARTICIPANTE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParticipanteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CD_PARTICIPANTE")
    private Long id;

    @Column(name = "TX_NOME", nullable = false)
    private String nome;

    @Column(name = "TX_EMAIL", nullable = false, unique = true)
    private String email;
}
