export interface Evento {
  id?: number;
  nome: string;
  dataEvento: string;
  local: string;
  capacidade: number;
  vagasDisponiveis?: number;
}
