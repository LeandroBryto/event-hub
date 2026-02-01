package event_hub_api.repository;

import event_hub_api.model.IngressoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngressoRepository extends JpaRepository<IngressoEntity, Long> {
    List<IngressoEntity> findByParticipanteEmail(String email);
}
