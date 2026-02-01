package event_hub_api.controller;

import event_hub_api.dto.EventoRequestDTO;
import event_hub_api.dto.EventoResponseDTO;
import event_hub_api.service.EventoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/eventos")
@RequiredArgsConstructor
public class EventoController {

    private final EventoService eventoService;

    @GetMapping
    public ResponseEntity<List<EventoResponseDTO>> listar() {
        return ResponseEntity.ok(eventoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(eventoService.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EventoResponseDTO> criar(@Valid @RequestBody EventoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoService.criar(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EventoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody EventoRequestDTO dto) {
        return ResponseEntity.ok(eventoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        eventoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
