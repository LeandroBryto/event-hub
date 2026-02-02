import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { CompraIngresso } from '../models/compra.model';
import { Ingresso } from '../models/ingresso.model';

@Injectable({
  providedIn: 'root'
})
export class IngressoService {
  private apiUrl = `${environment.apiUrl}/ingressos`;

  constructor(private http: HttpClient) { }

  comprar(compra: CompraIngresso): Observable<Ingresso> {
    return this.http.post<Ingresso>(`${this.apiUrl}/compra`, compra);
  }

  listarHistorico(email: string): Observable<Ingresso[]> {
    return this.http.get<Ingresso[]>(`${this.apiUrl}/participante/${email}`);
  }
}
