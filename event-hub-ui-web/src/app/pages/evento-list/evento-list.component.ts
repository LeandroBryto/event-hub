import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { InputTextModule } from 'primeng/inputtext';
import { MessageService } from 'primeng/api';
import { EventoService } from '../../services/evento.service';
import { IngressoService } from '../../services/ingresso.service';
import { Evento } from '../../models/evento.model';
import { CompraIngresso } from '../../models/compra.model';

@Component({
  selector: 'app-evento-list',
  standalone: true,
  imports: [CommonModule, FormsModule, ButtonModule, DialogModule, InputTextModule],
  templateUrl: './evento-list.component.html',
  styleUrl: './evento-list.component.scss'
})
export class EventoListComponent implements OnInit {
  eventos: Evento[] = [];
  displayCompraDialog: boolean = false;
  selectedEvento: Evento | null = null;
  
  nomeParticipante: string = '';
  emailParticipante: string = '';
  loadingCompra: boolean = false;

  constructor(
    private eventoService: EventoService,
    private ingressoService: IngressoService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {
    this.carregarEventos();
  }

  carregarEventos(): void {
    this.eventoService.listar().subscribe({
      next: (data) => this.eventos = data,
      error: (err) => {
        console.error(err);
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao carregar eventos.' });
      }
    });
  }

  abrirCompra(evento: Evento): void {
    this.selectedEvento = evento;
    this.nomeParticipante = '';
    this.emailParticipante = '';
    this.displayCompraDialog = true;
  }

  confirmarCompra(): void {
    if (!this.selectedEvento?.id || !this.nomeParticipante || !this.emailParticipante) {
      this.messageService.add({ severity: 'warn', summary: 'Atenção', detail: 'Preencha todos os campos!' });
      return;
    }

    this.loadingCompra = true;
    const compra: CompraIngresso = {
      idEvento: this.selectedEvento.id,
      nomeParticipante: this.nomeParticipante,
      emailParticipante: this.emailParticipante
    };

    this.ingressoService.comprar(compra).subscribe({
      next: (ingresso) => {
        this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: `Ingresso comprado! Código: ${ingresso.codigo}` });
        this.displayCompraDialog = false;
        this.loadingCompra = false;
        this.carregarEventos(); 
      },
      error: (err) => {
        console.error(err);
        this.loadingCompra = false;
        const msg = err.error?.message || 'Erro ao realizar compra.';
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: msg });
      }
    });
  }
}
