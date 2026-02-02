import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { MessageService } from 'primeng/api';
import { IngressoService } from '../../services/ingresso.service';
import { Ingresso } from '../../models/ingresso.model';

@Component({
  selector: 'app-meus-ingressos',
  standalone: true,
  imports: [CommonModule, FormsModule, InputTextModule, ButtonModule],
  templateUrl: './meus-ingressos.component.html',
  styleUrl: './meus-ingressos.component.scss'
})
export class MeusIngressosComponent {
  email: string = '';
  ingressos: Ingresso[] = [];
  loading: boolean = false;
  buscou: boolean = false;

  constructor(
    private ingressoService: IngressoService,
    private messageService: MessageService
  ) {}

  buscarIngressos(): void {
    if (!this.email) {
        this.messageService.add({ severity: 'warn', summary: 'Atenção', detail: 'Digite um e-mail para buscar.' });
        return;
    }

    this.loading = true;
    this.ingressoService.listarHistorico(this.email).subscribe({
      next: (data) => {
        this.ingressos = data;
        this.buscou = true;
        this.loading = false;
        if (data.length === 0) {
            this.messageService.add({ severity: 'info', summary: 'Info', detail: 'Nenhum ingresso encontrado.' });
        }
      },
      error: (err) => {
        console.error(err);
        this.loading = false;
        this.messageService.add({ severity: 'error', summary: 'Erro', detail: 'Erro ao buscar ingressos.' });
      }
    });
  }
}
