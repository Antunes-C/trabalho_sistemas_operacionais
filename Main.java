import java.util.*;
import java.util.stream.Collectors;

public class Main {
    static int index_troca;
    public static void main(String[] args) {
        int opcao = -1;
        ArrayList<Processo> processos = new ArrayList<>();
        Scanner teclado = new Scanner (System.in);

        popularProcessos(processos);
        imprimeProcessos(processos);

        while (opcao != 0) {

            System.out.println("");
            System.out.println("Selecione uma das opções abaixo: ");
            System.out.println("");
            System.out.println("1 - FCFS");
            System.out.println("2 - SJF Preemptivo");
            System.out.println("3 - SJF Não Preemptivo");
            System.out.println("4 - Prioridade Preemptivo");
            System.out.println("5 - Prioridade Não Preemptivo");
            System.out.println("6 - Round_Robin");
            System.out.println("7 - Imprime lista de processos");
            System.out.println("8 - Popular processos novamente");
            System.out.println("9 - Sair");
            System.out.println("");
            System.out.println("Digite sua opção: ");
            opcao = Integer.parseInt(teclado.nextLine());

            if (opcao == 1) {
                restartProcessos(processos);
                fcfs(processos);
            } else {
                if (opcao == 2) {
                    restartProcessos(processos);
                    sjfPreemptivo(processos);
                } else {
                    if (opcao == 3) {
                        restartProcessos(processos);
                        sjfNaoPreemptivo(processos);
                    } else {
                        if (opcao == 4) {
                            restartProcessos(processos);
                            prioridadePreemptivo(processos);
                        } else {
                            if (opcao == 5) {
                                restartProcessos(processos);
                                prioridadeNaoPreemptivo(processos);
                            } else {
                                if (opcao == 6) {
                                    restartProcessos(processos);
                                    roundRobin(processos);
                                } else {
                                    if (opcao == 7) {
                                        imprimeProcessos(processos);
                                    } else {
                                        if (opcao == 8) {
                                            popularProcessos(processos);
                                        } else {
                                            if (opcao == 9) {
                                                System.exit(0);
                                            } else {
                                                System.out.println("Opção Inválida. Digite novamente");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void popularProcessos(List<Processo> processos) {
        String rand = "aux";
        boolean rand_boolean = false;
        Random random = new Random();
        Scanner teclado = new Scanner (System.in);
        int num_processos, tex, tc, pri;

        processos.clear();
        System.out.println("Digite o número de processos que deseja trabalhar: ");
        num_processos = Integer.parseInt(teclado.nextLine());
        while (!(rand.toUpperCase().equals("S")) && !(rand.toUpperCase().equals("N"))) {
            System.out.println("Deseja que os processos sejam definidos aleatoriamente [S/N]: ");
            rand = teclado.nextLine();
            if (!(rand.toUpperCase().equals("S")) && !(rand.toUpperCase().equals("N"))) {
                System.out.println("Valor inválido! Digite: S ou N");
            } else {
                if (rand.toUpperCase().equals("S")) {
                    rand_boolean = true;
                } else {
                    rand_boolean = false;
                }
            }
        }

        if (rand_boolean) {
            for (int i = 0; i < num_processos; i++) {
                processos.add(new Processo(i,random.nextInt(10)+1, random.nextInt(10)+1,random.nextInt(15)+1));
            }
        } else {
            for (int i = 0; i < num_processos; i++) {
                System.out.println("Digite o tempo de execução do processo["+i+"]:  ");
                tex = teclado.nextInt();
                System.out.println("Digite o tempo de chegada do processo["+i+"]:  ");
                tc = teclado.nextInt();
                System.out.println("Digite a prioridade do processo["+i+"]:  ");
                pri = teclado.nextInt();
                processos.add(new Processo( i, tex, tc, pri));
            }
        }
    }


    public static void imprimeProcessos(List<Processo> processos) {
        System.out.println("");
        for (int i = 0; i < processos.size(); i++) {
            processos.get(i).info();
        }
    }

    public static void fcfs(List<Processo> processos) {
        int ut = 1, tempo_exec, index = 0, alg = 1;
        //processos.sort(Comparator.comparing(Processo::getTempo_chegada)); //Organiza os processos por ordem de chegada
        tempo_exec =  processos.stream().collect(Collectors.summingInt(Processo::getTempo_execucao)); //Soma os tempos de execução dos processos

        while (tempo_exec > 0) {
            while(processos.get(index).getTempo_restante() > 0) {
                    processos.get(index).tempoRestanteDecremento();
                    calcTempoEspera(processos, processos.get(index), ut, alg);
                    System.out.println("Tempo["+ut+"]: Processo[" + processos.get(index).getId() + "] - Tempo Restante: "+processos.get(index).getTempo_restante());
                    tempo_exec--;
                    ut++;
                    delay();
            }
            index++;
        }
        imprimeEspera(processos);
    }

    public static void sjfPreemptivo(List<Processo> processos) {
        int ut = 1, tempo_exec, index = 0, alg = 2;
        tempo_exec =  processos.stream().collect(Collectors.summingInt(Processo::getTempo_execucao));
        index = calcSjfPreemptivo(processos, index, ut);

        while (tempo_exec > 0) {
            if (processos.get(index).getTempo_chegada(alg) <= ut) {
                while (processos.get(index).getTempo_restante() > 0) {
                    processos.get(index).tempoRestanteDecremento();
                    calcTempoEspera(processos, processos.get(index), ut, alg);
                    System.out.println("Tempo[" + ut + "]: Processo[" + processos.get(index).getId() + "] - Tempo Restante: " + processos.get(index).getTempo_restante());
                    tempo_exec--;
                    ut++;
                    delay();
                    index = calcSjfPreemptivo(processos, index, ut);
                }
                index++;
                index = calcSjfPreemptivo(processos, index, ut);
            } else {
                System.out.println("Tempo["+ut+"]: Nenhum processo está pronto");
                ut++;
                delay();
            }
        }
        imprimeEspera(processos);
    }

    public static void sjfNaoPreemptivo(List<Processo> processos) {
        int ut = 1, tempo_exec, index = 0, alg = 3;
        processos.sort(Comparator.comparing(Processo::getTempo_execucao)); //Organiza os processos por tempo de execução
        processos.sort(Comparator.comparing(Processo::getTempo_chegadaOrg)); //Organiza os processos por ordem de chegada
        tempo_exec =  processos.stream().collect(Collectors.summingInt(Processo::getTempo_execucao));

        while (tempo_exec > 0) {
            if (processos.get(index).getTempo_chegada(alg) <= ut) {
                while(processos.get(index).getTempo_restante() > 0) {
                    processos.get(index).tempoRestanteDecremento();
                    calcTempoEspera(processos, processos.get(index), ut, alg);
                    System.out.println("Tempo[" + ut + "]: Processo[" + processos.get(index).getId() + "] - Tempo Restante: " + processos.get(index).getTempo_restante());
                    tempo_exec--;
                    ut++;
                    delay();
                }
                processos = calcSjfNaoPreemptivo(processos, ut);
                index++;
            } else {
                System.out.println("Tempo[" + ut + "]: Nenhum processo está pronto");
                ut++;
                delay();
            }
        }
        imprimeEspera(processos);
    }

    public static void prioridadePreemptivo(List<Processo> processos) {
        int ut = 1, tempo_exec, index = 0, alg = 4;
        processos.sort(Comparator.comparing(Processo::getPrioridade)); //Organiza os processos por prioridade
        processos.sort(Comparator.comparing(Processo::getTempo_chegadaOrg)); //Organiza os processos por ordem de chegada
        tempo_exec =  processos.stream().collect(Collectors.summingInt(Processo::getTempo_execucao));
        index = calcPrioridadePreemptivo(processos, index, ut);

        while (tempo_exec > 0) {
            if (processos.get(index).getTempo_chegada(alg) <= ut) {
                while (processos.get(index).getTempo_restante() > 0) {
                    processos.get(index).tempoRestanteDecremento();
                    calcTempoEspera(processos, processos.get(index), ut, alg);
                    System.out.println("Tempo[" + ut + "]: Processo[" + processos.get(index).getId() + "] - Tempo Restante: " + processos.get(index).getTempo_restante());
                    tempo_exec--;
                    ut++;
                    delay();
                    index = calcPrioridadePreemptivo(processos, index, ut);
                }
                index++;
                index = calcPrioridadePreemptivo(processos, index, ut);
            } else {
                System.out.println("Tempo["+ut+"]: Nenhum processo está pronto");
                ut++;
                delay();
            }
        }
        imprimeEspera(processos);
    }

    public static void prioridadeNaoPreemptivo(List<Processo> processos) {
        int ut = 1, tempo_exec, index = 0, alg = 3;
        processos.sort(Comparator.comparing(Processo::getPrioridade)); //Organiza os processos por prioridade
        processos.sort(Comparator.comparing(Processo::getTempo_chegadaOrg)); //Organiza os processos por ordem de chegada
        tempo_exec =  processos.stream().collect(Collectors.summingInt(Processo::getTempo_execucao));

        while (tempo_exec > 0) {
            if (processos.get(index).getTempo_chegada(alg) <= ut) {
                while(processos.get(index).getTempo_restante() > 0) {
                    processos.get(index).tempoRestanteDecremento();
                    calcTempoEspera(processos, processos.get(index), ut, alg);
                    System.out.println("Tempo[" + ut + "]: Processo[" + processos.get(index).getId() + "] - Tempo Restante: " + processos.get(index).getTempo_restante());
                    tempo_exec--;
                    ut++;
                    delay();
                }
                processos = calcPrioridadeNaoPreemptivo(processos, ut);
                index++;
            } else {
                System.out.println("Tempo[" + ut + "]: Nenhum processo está pronto");
                ut++;
                delay();
            }
        }
        imprimeEspera(processos);
    }

    public static void roundRobin(List<Processo> processos) {
        Scanner teclado = new Scanner (System.in);
        int time_slice, tempo_exec, index = 0,ut = 1, alg = 6;;

        System.out.println("Escolha o time slice: ");
        time_slice = Integer.parseInt(teclado.nextLine());

        tempo_exec =  processos.stream().collect(Collectors.summingInt(Processo::getTempo_execucao)); //Soma os tempos de execução dos processos

        while (tempo_exec > 0) {
            for (int i = 0; i < time_slice; i++) {
                if (index > processos.size()-1) {
                    index = 0;
                }
                if (processos.get(index).getTempo_restante() == 0) {
                    index++;
                    i = -1;
                } else {
                    processos.get(index).tempoRestanteDecremento();
                    calcTempoEspera(processos, processos.get(index), ut, alg);
                    System.out.println("Tempo["+ut+"]: Processo[" + processos.get(index).getId() + "] - Tempo Restante: "+processos.get(index).getTempo_restante());
                    tempo_exec--;
                    ut++;
                    delay();
                }
                if (tempo_exec == 0) {
                    break;
                }
            }
            index++;
        }
        imprimeEspera(processos);
    }

    public static void calcTempoEspera(List<Processo> processos, Processo processo_exec, int ut, int alg) {
        for (int i = 0; i < processos.size(); i++) {
            if ((processos.get(i) != processo_exec) && (processos.get(i).getTempo_chegada(alg) <= ut) && (processos.get(i).getTempo_restante() != 0)) {
                processos.get(i).tempoEsperaIncremento();
            }
        }
    }

    public static void delay() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void imprimeEspera(List<Processo> processos) {
        double media;
        double totalespera = (processos.stream().collect(Collectors.summingInt(Processo::getTempo_espera)));
        media = totalespera / processos.size();
        processos.sort(Comparator.comparing(Processo::getId));
        System.out.println("");
        for (int i = 0; i < processos.size(); i++) {
            System.out.println("Processo ["+processos.get(i).getId()+"]: Tempo de Espera: "+processos.get(i).getTempo_espera());
        }
        System.out.println("");
        System.out.printf("Tempo médio de espera: %.2f", media);
        System.out.println("");
    }

    public static void restartProcessos(List<Processo> processos) {
        processos.sort(Comparator.comparing(Processo::getId));
        for (int i = 0; i < processos.size(); i++) {
            processos.get(i).setTempo_restante(processos.get(i).getTempo_execucao());
            processos.get(i).setTempo_espera(0);
        }
    }

    public static int calcSjfPreemptivo(List<Processo> processos,int index, int ut) {
        int aux = 0;
        processos.sort(Comparator.comparing(Processo::getTempo_restante));
        processos.sort(Comparator.comparing(Processo::getTempo_chegadaOrg));
        aux = processos.stream().max(Comparator.comparing(Processo::getTempo_restante)).orElseThrow(NoSuchElementException::new).getTempo_restante() + 1;
        for (int i = 0; i < processos.size(); i++) {
            if ((processos.get(i).getTempo_chegada(2) <= ut) && (processos.get(i).getTempo_restante() != 0) && (processos.get(i).getTempo_restante() < aux)) {
                index = i;
                aux = processos.get(i).getTempo_restante();
            }
        }
        return index;
    }

    public static List<Processo> calcSjfNaoPreemptivo(List<Processo> processos, int ut) {
        for (int i = 0; i < (processos.size() - 1); i++) {
            if ((processos.get(i).getTempo_restante() != 0) && (processos.get(i+1).getTempo_restante() != 0) && (processos.get(i+1).getTempo_chegada(3) <= ut)) {
                if (processos.get(i+1).getTempo_restante() < processos.get(i).getTempo_restante()) {
                    Collections.swap(processos, i, i + 1);
                }
            }
        }
        return processos;
    }

    public static int calcPrioridadePreemptivo(List<Processo> processos,int index, int ut) {
        int aux = 0;
        aux = processos.stream().min(Comparator.comparing(Processo::getPrioridade)).orElseThrow(NoSuchElementException::new).getPrioridade() - 1;
        for (int i = 0; i < processos.size(); i++) {
            if ((processos.get(i).getTempo_chegada(4) <= ut) && (processos.get(i).getTempo_restante() != 0) && (processos.get(i).getPrioridade() > aux)) {
                index = i;
                aux = processos.get(i).getPrioridade();
            }
        }
        return index;
    }

    public static List<Processo> calcPrioridadeNaoPreemptivo(List<Processo> processos, int ut) {
        for (int i = 0; i < (processos.size() - 1); i++) {
            if ((processos.get(i).getTempo_restante() != 0) && (processos.get(i+1).getTempo_restante() != 0) && (processos.get(i+1).getTempo_chegada(3) <= ut)) {
                if (processos.get(i+1).getPrioridade() > processos.get(i).getPrioridade()) {
                    Collections.swap(processos, i, i + 1);
                }
            }
        }
        return processos;
    }
}