import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TableModule } from 'primeng/table';
import { DialogModule } from 'primeng/dialog';
import { ButtonModule } from 'primeng/button';
import { ToolbarModule } from 'primeng/toolbar';
import { InputTextModule } from 'primeng/inputtext';
import { InputNumberModule } from 'primeng/inputnumber';
import { CalendarModule } from 'primeng/calendar';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { MessageService, ConfirmationService } from 'primeng/api';
import { EventoService } from '../../services/evento.service';
import { Evento } from '../../models/evento.model';

@Component({
  selector: 'app-evento-admin',
  standalone: true,
  imports: [
    CommonModule, 
    FormsModule, 
    TableModule, 
    DialogModule, 
    ButtonModule, 
    ToolbarModule, 
    InputTextModule, 
    InputNumberModule,
    CalendarModule,
    ConfirmDialogModule
  ],
  providers: [ConfirmationService],
  templateUrl: './evento-admin.component.html',
  styleUrl: './evento-admin.component.scss'
})
export class EventoAdminComponent implements OnInit {
  eventos: Evento[] = [];
  evento: Evento = { nome: '', local: '', capacidade: 0, dataEvento: '' };
  selectedEventos: Evento[] | null = null;
  submitted: boolean = false;
  eventoDialog: boolean = false;
  
  dataEventoDate: Date | undefined;

  constructor(
    private eventoService: EventoService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit(): void {
    this.carregarEventos();
  }

  carregarEventos(): void {
    this.eventoService.listar().subscribe({
      next: (data) => this.eventos = data,
      error: () => this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao carregar eventos' })
    });
  }

  openNew(): void {
    this.evento = { nome: '', local: '', capacidade: 0, dataEvento: '' };
    this.dataEventoDate = undefined;
    this.submitted = false;
    this.eventoDialog = true;
  }

  editEvento(evento: Evento): void {
    this.evento = { ...evento };
    if (this.evento.dataEvento) {
        this.dataEventoDate = new Date(this.evento.dataEvento);
    }
    this.eventoDialog = true;
  }

  deleteEvento(evento: Evento): void {
    this.confirmationService.confirm({
      message: 'Tem certeza que deseja excluir ' + evento.nome + '?',
      header: 'Confirmar',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        if (evento.id) {
            this.eventoService.deletar(evento.id).subscribe({
                next: () => {
                    this.eventos = this.eventos.filter(val => val.id !== evento.id);
                    this.evento = { nome: '', local: '', capacidade: 0, dataEvento: '' };
                    this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Evento excluído' });
                },
                error: () => this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao excluir evento' })
            });
        }
      }
    });
  }

  hideDialog(): void {
    this.eventoDialog = false;
    this.submitted = false;
  }

  saveEvento(): void {
    this.submitted = true;

    if (this.evento.nome?.trim()) {
        if (this.dataEventoDate) {
            const offset = this.dataEventoDate.getTimezoneOffset() * 60000;
            const localISOTime = (new Date(this.dataEventoDate.getTime() - offset)).toISOString().slice(0, -1);
            this.evento.dataEvento = localISOTime;
        }

        if (this.evento.id) {
            this.eventoService.atualizar(this.evento.id, this.evento).subscribe({
                next: (res) => {
                    this.carregarEventos();
                    this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Evento Atualizado' });
                    this.eventoDialog = false;
                    this.evento = { nome: '', local: '', capacidade: 0, dataEvento: '' };
                },
                error: () => this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao atualizar evento' })
            });
        } else {
            this.eventoService.criar(this.evento).subscribe({
                next: (res) => {
                    this.eventos.push(res);
                    this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Evento Criado' });
                    this.eventoDialog = false;
                    this.evento = { nome: '', local: '', capacidade: 0, dataEvento: '' };
                },
                error: () => this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao criar evento' })
            });
        }
    }
  }

  deleteSelectedEventos(): void {
    if (!this.selectedEventos || !this.selectedEventos.length) return;

    this.confirmationService.confirm({
        message: 'Tem certeza que deseja excluir os eventos selecionados?',
        header: 'Confirmar',
        icon: 'pi pi-exclamation-triangle',
        accept: () => {
            let deletedCount = 0;
            const total = this.selectedEventos!.length;

            this.selectedEventos?.forEach(evento => {
                if (evento.id) {
                    this.eventoService.deletar(evento.id).subscribe({
                        next: () => {
                            this.eventos = this.eventos.filter(val => val.id !== evento.id);
                            deletedCount++;
                            if (deletedCount === total) {
                                this.messageService.add({ severity: 'success', summary: 'Sucesso', detail: 'Eventos excluídos' });
                                this.selectedEventos = null;
                            }
                        },
                        error: () => {
                             this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao excluir evento ' + evento.nome });
                        }
                    });
                }
            });
        }
    });
  }
}
