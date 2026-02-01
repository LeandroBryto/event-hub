package event_hub_api.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import event_hub_api.dto.EventoRequestDTO;
import event_hub_api.dto.EventoResponseDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import event_hub_api.exceptions.BusinessException;
import event_hub_api.model.EventoEntity;
import event_hub_api.repository.EventoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;

    @Cacheable(value = "eventosAtivos")
    @Transactional(readOnly = true)
    public List<EventoResponseDTO> listarTodos() {
        return eventoRepository.findAllByOrderByDataEventoAsc().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EventoResponseDTO buscarPorId(Long id) {
        return eventoRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new BusinessException("Evento não encontrado."));
    }

    @CacheEvict(value = "eventosAtivos", allEntries = true)
    @Transactional
    public EventoResponseDTO criar(EventoRequestDTO dto) {
        if (dto.getDataEvento().toLocalDate().isBefore(LocalDate.now())) {
            throw new BusinessException("A data do evento não pode ser anterior ao dia de hoje.");
        }

        EventoEntity entidade = new EventoEntity();
        BeanUtils.copyProperties(dto, entidade);

        entidade.setVagasDisponiveis(dto.getCapacidade());

        EventoEntity salvo = eventoRepository.save(entidade);
        return toDTO(salvo);
    }

    @CacheEvict(value = "eventosAtivos", allEntries = true)
    @Transactional
    public EventoResponseDTO atualizar(Long id, EventoRequestDTO dto) {

        EventoEntity entidade = eventoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Evento não encontrado para atualização."));

        int vendidos = entidade.getCapacidade() - entidade.getVagasDisponiveis();

        if (dto.getCapacidade() < vendidos) {
            throw new BusinessException(
                    "A nova capacidade não pode ser menor que a quantidade de ingressos já vendidos (" + vendidos + ")."
            );
        }
        if (dto.getCapacidade() > entidade.getCapacidade()) {
            int diff = dto.getCapacidade() - entidade.getCapacidade();
            entidade.setVagasDisponiveis(entidade.getVagasDisponiveis() + diff);
        }

        BeanUtils.copyProperties(dto, entidade, "id", "vagasDisponiveis", "version", "dtGravacao", "ingressos");

        entidade.setCapacidade(dto.getCapacidade());

        EventoEntity salvo = eventoRepository.save(entidade);

        return toDTO(salvo);
    }

    @CacheEvict(value = "eventosAtivos", allEntries = true)
    @Transactional
    public void deletar(Long id) {
        if (!eventoRepository.existsById(id)) {
            throw new BusinessException("Evento não encontrado para deleção.");
        }
        eventoRepository.deleteById(id);
    }

    private EventoResponseDTO toDTO(EventoEntity entity) {
        EventoResponseDTO dto = new EventoResponseDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }
}
