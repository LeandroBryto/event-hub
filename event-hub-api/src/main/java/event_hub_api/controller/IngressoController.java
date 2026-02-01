package event_hub_api.controller;

import event_hub_api.dto.CompraIngressoRequestDTO;
import event_hub_api.dto.IngressoResponseDTO;
import event_hub_api.service.IngressoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/ingressos")
@RequiredArgsConstructor
public class IngressoController {

    private final IngressoService ingressoService;

    @PostMapping("/compra")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<IngressoResponseDTO> comprar(@Valid @RequestBody CompraIngressoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ingressoService.realizarCompra(dto));
    }

    @GetMapping("/participante/{email}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<IngressoResponseDTO>> historico(@PathVariable String email) {
        return ResponseEntity.ok(ingressoService.listarHistorico(email));
    }
}
