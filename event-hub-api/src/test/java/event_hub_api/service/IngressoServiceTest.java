package event_hub_api.service;

import event_hub_api.dto.CompraIngressoRequestDTO;
import event_hub_api.dto.IngressoResponseDTO;
import event_hub_api.exceptions.BusinessException;
import event_hub_api.model.EventoEntity;
import event_hub_api.model.ParticipanteEntity;
import event_hub_api.repository.EventoRepository;
import event_hub_api.repository.IngressoRepository;
import event_hub_api.repository.ParticipanteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngressoServiceTest {

    @InjectMocks
    private IngressoService ingressoService;

    @Mock
    private EventoRepository eventoRepository;

    @Mock
    private ParticipanteRepository participanteRepository;

    @Mock
    private IngressoRepository ingressoRepository;

    @Test
    @DisplayName("Deve realizar compra com sucesso quando houver vagas")
    void deveComprarComSucesso() {
        EventoEntity evento = new EventoEntity();
        evento.setId(1L);
        evento.setNome("Show Teste");
        evento.setDataEvento(LocalDateTime.now().plusDays(1));
        evento.setCapacidade(10);
        evento.setVagasDisponiveis(10);

        ParticipanteEntity participante = new ParticipanteEntity(1L, "Leandro", "leandro@email.com");

        CompraIngressoRequestDTO dto = new CompraIngressoRequestDTO();
        dto.setIdEvento(1L);
        dto.setNomeParticipante("Leandro");
        dto.setEmailParticipante("leandro@email.com");

        when(eventoRepository.findById(1L)).thenReturn(Optional.of(evento));
        when(participanteRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(participanteRepository.save(any())).thenReturn(participante);
        when(ingressoRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        IngressoResponseDTO resultado = ingressoService.realizarCompra(dto);

        assertNotNull(resultado);
        assertEquals(9, evento.getVagasDisponiveis());
        verify(eventoRepository, times(1)).save(evento);
        verify(ingressoRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o evento estiver lotado")
    void deveLancarErroQuandoLotado() {

        EventoEntity evento = new EventoEntity();
        evento.setId(1L);
        evento.setVagasDisponiveis(0);

        CompraIngressoRequestDTO dto = new CompraIngressoRequestDTO();
        dto.setIdEvento(1L);
        dto.setNomeParticipante("Leandro");
        dto.setEmailParticipante("leandro@email.com");

        when(eventoRepository.findById(1L)).thenReturn(Optional.of(evento));

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            ingressoService.realizarCompra(dto);
        });

        assertEquals("ERRO: Evento lotado! Não há vagas disponíveis.", exception.getMessage());
        verify(ingressoRepository, never()).save(any());
    }
}
