package model;

public class ContaCorrente extends Conta implements ITributavel{
	
	private final double taxaDeServico = 1.05;

	public ContaCorrente (String cliente, double saldo){
		super( cliente, saldo);
	}
	
	@Override
	public boolean sacar(double valor) {
		if (valor * taxaDeServico <= super.getSaldo()) {
			super.setSaldo(super.getSaldo() - (valor * taxaDeServico));
			return true;
		} else {
			return false;
		}
		
	}

	@Override
	public boolean transferir(double valor, Conta conta) {
		if (this.sacar(valor)) {
			conta.setSaldo(conta.getSaldo() + valor);
			return true;
		} else return false;
	}

	@Override
	public double calculaTributos() {
		return super.getSaldo() * 0.01;
	}

}
