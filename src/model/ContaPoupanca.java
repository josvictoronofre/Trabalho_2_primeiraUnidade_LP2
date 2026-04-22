package model;

public class ContaPoupanca extends Conta {

	public ContaPoupanca(String cliente, double saldo) {
		super(cliente, saldo);
	}

	@Override
	public boolean sacar(double valor) {
		if (valor <= super.getSaldo()) {
			super.setSaldo(super.getSaldo() - valor);
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

}
