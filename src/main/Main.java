package main;

import java.util.Scanner;
import java.util.ArrayList;
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
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            
            int opcao = scanner.nextInt();
            scanner.nextLine();
            
            switch(opcao) {
            	case 1:
            		System.out.print("Nome do Cliente: ");
                    String nomeCC = scanner.nextLine();
                    System.out.print("Saldo Inicial: ");
                    double saldoCC = scanner.nextDouble();
                    scanner.nextLine();
                    contas.add(new ContaCorrente(nomeCC, saldoCC));
                    System.out.println("Conta Corrente criada!");
                    break;
            	case 2:
            		System.out.print("Nome do Cliente: ");
                    String nomeCP = scanner.nextLine();
                    System.out.print("Saldo Inicial: ");
                    double saldoCP = scanner.nextDouble();
                    scanner.nextLine();
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
            	    System.out.print("Número (ID) da conta de ORIGEM: ");
            	    int idOrigem = scanner.nextInt();
            	    scanner.nextLine();
            	    Conta origem = buscarConta(contas, idOrigem);

            	    // 2. Identify the destination account
            	    System.out.print("Número (ID) da conta de DESTINO: ");
            	    int idDestino = scanner.nextInt();
            	    scanner.nextLine();
            	    Conta destino = buscarConta(contas, idDestino);

            	    // 3. Execute the transfer
            	    if (origem != null && destino != null) {
            	        if (origem == destino) {
            	            System.out.println("Erro: A conta de origem e destino não podem ser a mesma.");
            	        } else {
            	            System.out.print("Valor da transferência: ");
            	            double valorTransf = scanner.nextDouble();

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
            	case 0:
            		System.out.println("Encerrando sistema...");
            		scanner.close();
            		rodando = false;
            		break;
            }
		}
	}
	private static void realizarSaque(ArrayList<Conta> contas, Scanner scanner) {
		System.out.print("ID da conta: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Conta contaAlvo = buscarConta(contas, id);
        
        if (contaAlvo!= null) {
        	System.out.print("Valor do saque: ");
            double valor = scanner.nextDouble();
            scanner.nextLine();
            
            if (contaAlvo.sacar(valor)) {
            	System.out.printf("Sucesso! Novo Saldo: R$%,.2f", contaAlvo.getSaldo());
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

}
