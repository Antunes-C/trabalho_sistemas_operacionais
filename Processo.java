public class Processo {
    private int id, tempo_execucao, tempo_chegada, prioridade, tempo_restante, tempo_espera;

    public Processo(int id, int tex, int tc, int p) {
        this.id = id;
        this.tempo_execucao = tex;
        this.tempo_chegada = tc;
        this.prioridade = p;
        this.tempo_restante = tex;
        this.tempo_espera = 0;
    }

    public void info() {
        System.out.println("Processo ["+ this.id +"]: Tempo de Execução: " +this.tempo_execucao+" | Tempo Restante: "+this.tempo_restante+" | Tempo de Chegada: "+this.tempo_chegada+" | Prioridade: "+prioridade);
    }

    public int getId() {
        return this.id;
    }

    public void tempoEsperaIncremento() {
        this.tempo_espera += 1;
    }

    public void tempoRestanteDecremento() {
        this.tempo_restante -= 1;
    }

    public int getTempo_espera() {
        return this.tempo_espera;
    }

    public int getTempo_restante() {
        return this.tempo_restante;
    }

    public int getTempo_chegada(int alg) {
        if ((alg == 1) || (alg ==6)) { //Se o algoritmo for 1 (FCFS) ou 6 (RoundRobin) retorna 0;
            return 0;
        } else {
            return this.tempo_chegada;
        }
    }

    public int getTempo_chegadaOrg() {
        return this.tempo_chegada;
    }

    public int getTempo_execucao() {
        return this.tempo_execucao;
    }

    public int getPrioridade() {
        return this.prioridade;
    }

    public void setTempo_chegada(int temp) { this.tempo_chegada = temp; }
    public void setTempo_restante(int temp) { this.tempo_restante = temp; }
    public void setTempo_espera(int temp) { this.tempo_espera = temp; }

}
