package event_hub_api.repository;

import event_hub_api.model.EventoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<EventoEntity, Long> {
    @Query("SELECT e FROM EventoEntity e WHERE e.dataEvento > CURRENT_TIMESTAMP")
    List<EventoEntity> findAllFuturos();
}
