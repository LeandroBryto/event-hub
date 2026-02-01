package event_hub_api.repository;

import event_hub_api.model.ParticipanteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParticipanteRepository extends JpaRepository<ParticipanteEntity, Long> {
    Optional<ParticipanteEntity> findByEmail(String email);
}
