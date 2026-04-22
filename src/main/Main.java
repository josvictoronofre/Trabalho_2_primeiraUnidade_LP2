package main;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;

import model.*;
import java.util.Locale;

public class Main {

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("pt", "BR"));
		Scanner scanner = new Scanner(System.in);
		ArrayList<Conta> contas = new ArrayList<>();
		boolean rodando = true;
		
		while(rodando) {
			System.out.println("\n=== SISTEMA BANCÁRIO ===");
            System.out.println("1. Criar Conta Corrente");
            System.out.println("2. Criar Conta Poupança");
            System.out.println("3. Listar Contas");
            System.out.println("4. Sacar");
            System.out.println("5. Transferir");
            System.out.println("6. Calcular o total de tributos");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            
            int opcao = lerInt(scanner, "Escolha uma opção: ");
            
            switch(opcao) {
            	case 1:
            		System.out.print("Nome do Cliente: ");
	                String nomeCC = scanner.nextLine();
	                double saldoCC = lerValor(scanner, "Saldo Inicial: ");
	                contas.add(new ContaCorrente(nomeCC, saldoCC));
	                System.out.println("Conta Corrente criada!");
	                break;
            	case 2:
	            	System.out.print("Nome do Cliente: ");
	                String nomeCP = scanner.nextLine();
	                double saldoCP = lerValor(scanner, "Saldo Inicial: ");
	                contas.add(new ContaPoupanca(nomeCP, saldoCP));
	                System.out.println("Conta Poupança criada!");
                    break;
            	case 3:
            		System.out.println("\n--- Lista de contas ---");
            		for (Conta conta : contas) {
            			System.out.println(conta);
            		}
            		break;
            	case 4:
            		realizarSaque(contas, scanner);
            		break;
            	case 5:
            		System.out.println("\n--- Transferência ---");
            	    if (contas.size() < 2) {
            	        System.out.println("Erro: Você precisa de pelo menos duas contas para transferir.");
            	        break;
            	    }

            	    // 1. Identify the source account

            	    int idOrigem = lerInt(scanner, "Número (ID) da conta de ORIGEM: ");
            	    Conta origem = buscarConta(contas, idOrigem);

            	    // 2. Identify the destination account
            	    int idDestino = lerInt(scanner, "Número (ID) da conta de DESTINO: ");
            	    Conta destino = buscarConta(contas, idDestino);

            	    // 3. Execute the transfer
            	    if (origem != null && destino != null) {
            	        if (origem == destino) {
            	            System.out.println("Erro: A conta de origem e destino não podem ser a mesma.");
            	        } else {
            	            double valorTransf = lerValor(scanner, "Valor da transferencia: ");

            	            if (origem.transferir(valorTransf, destino)) {
            	                System.out.println("Transferência realizada com sucesso!");
            	            } else {
            	                System.out.println("Erro: Saldo insuficiente na conta de origem.");
            	            }
            	        }
            	    } else {
            	        System.out.println("Erro: Uma ou ambas as contas não foram encontradas.");
            	    }
            	    break;
            	case 6:
            		System.out.printf("\nTotal de Tributos: R$%,.2f\n", calculaTributosTotais(contas));
            		break;
            	case 0:
            		System.out.println("Encerrando sistema...");
            		scanner.close();
            		rodando = false;
            		break;
            }
		}
	}
	private static void realizarSaque(ArrayList<Conta> contas, Scanner scanner) {
        int id = lerInt(scanner, "ID da conta: ");

        Conta contaAlvo = buscarConta(contas, id);
        
        if (contaAlvo!= null) {
        	double valor = lerValor(scanner, "Valor do saque: ");
            
            if (contaAlvo.sacar(valor)) {
            	System.out.printf("Sucesso! Novo Saldo: R$%,.2f\n", contaAlvo.getSaldo());
            } else {
            	System.out.println("Erro: Saldo insuficiente (considerando taxas).");
            }
        } else {
        	System.out.println("Erro: Conta com ID " + id + " nao encontrada.");
        }
        
	}
	
	private static Conta buscarConta(ArrayList<Conta> lista, int numeroProcurado) {
	    for (Conta c : lista) {
	        if (c.getNumero() == numeroProcurado) {
	            return c;
	        }
	    }
	    return null; // Return null if the ID doesn't exist
	}
	
	private static double calculaTributosTotais(ArrayList<Conta> lista) {
		double totalTributos = 0;
		
		for (Conta conta : lista) {
			if (conta instanceof ITributavel) {
				ITributavel contaTributavel = (ITributavel) conta;
				totalTributos += contaTributavel.calculaTributos();
			}
		}
		return totalTributos;
	}
	
	private static double lerValor(Scanner scanner, String mensagem) {
		while (true) {
			try {
				System.out.print(mensagem);
				double valor = scanner.nextDouble();
				scanner.nextLine();
				return valor;
			} catch (InputMismatchException e) {
				System.err.println("Erro: Digite um valor numerico valido.");
				scanner.nextLine();
			}
		}
	}
	
	private static int lerInt(Scanner scanner, String mensagem) {
		while (true) {
			try {
				System.out.print(mensagem);
				int valor = scanner.nextInt();
				scanner.nextLine();
				return valor;
			} catch (InputMismatchException e) {
				System.err.println("Erro: digite um numero valido.");
				scanner.nextLine();
			}
		}
	}

}
