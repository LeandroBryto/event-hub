package event_hub_api.service;

import event_hub_api.dto.CompraIngressoRequestDTO;
import event_hub_api.dto.IngressoResponseDTO;
import event_hub_api.exceptions.BusinessException;
import event_hub_api.model.EventoEntity;
import event_hub_api.model.IngressoEntity;
import event_hub_api.model.ParticipanteEntity;
import event_hub_api.repository.EventoRepository;
import event_hub_api.repository.IngressoRepository;
import event_hub_api.repository.ParticipanteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class IngressoService {

    private final EventoRepository eventoRepository;
    private final ParticipanteRepository participanteRepository;
    private final IngressoRepository ingressoRepository;

    @Transactional(readOnly = true)
    public List<IngressoResponseDTO> listarHistorico(String email) {
        return ingressoRepository.findByParticipanteEmail(email).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public IngressoResponseDTO realizarCompra(CompraIngressoRequestDTO request) {
        EventoEntity evento = eventoRepository.findById(request.getIdEvento())
                .orElseThrow(() -> new BusinessException("ERRO: Evento inexistente."));

        if (evento.getVagasDisponiveis() <= 0) {
            throw new BusinessException("ERRO: Evento lotado! Não há vagas disponíveis.");
        }

        ParticipanteEntity participante = participanteRepository.findByEmail(request.getEmailParticipante())
                .orElse(null);

        if (participante == null) {
            participante = new ParticipanteEntity();
            participante.setNome(request.getNomeParticipante());
            participante.setEmail(request.getEmailParticipante());
            participante = participanteRepository.save(participante);
        }
        evento.setVagasDisponiveis(evento.getVagasDisponiveis() - 1);
        eventoRepository.save(evento);

        IngressoEntity ingresso = new IngressoEntity();
        ingresso.setEvento(evento);
        ingresso.setParticipante(participante);
        ingresso.setCodigo(UUID.randomUUID().toString());
        ingresso.setDtCompra(LocalDateTime.now());
        ingresso.setTxObservacao("Compra realizada via API EventHub");

        IngressoEntity salvo = ingressoRepository.save(ingresso);

        log.info("[INTEGRAÇÃO] Enviando e-mail de confirmação para: {}", participante.getEmail());

        return toDTO(salvo);
    }

    private IngressoResponseDTO toDTO(IngressoEntity entity) {
        IngressoResponseDTO dto = new IngressoResponseDTO();
        dto.setCodigo(entity.getCodigo());
        dto.setNomeEvento(entity.getEvento().getNome());
        dto.setDataEvento(entity.getEvento().getDataEvento());
        dto.setNomeParticipante(entity.getParticipante().getNome());
        dto.setDataCompra(entity.getDtCompra());
        return dto;
    }
}
