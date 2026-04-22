package model;

public abstract class Conta {
	
	private static int contador = 1;

	private int numero;
	private String cliente;
	private double saldo;
	
	public Conta( String cliente, double saldo) {
		this.numero = contador;
		this.cliente = cliente;
		this.saldo = saldo;
		contador++;
	}

	public void depositar(double valor) {
		saldo += valor;
	}
	
	public abstract boolean sacar(double valor);
	public abstract boolean transferir(double valor, Conta conta);
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public double getSaldo() {
		return saldo;
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	
	@Override
	public String toString() {
	    return String.format("Id - %d / Nome - %s / Saldo: R$%.2f", 
	                         this.numero, this.cliente, this.saldo);
	}
	
}
