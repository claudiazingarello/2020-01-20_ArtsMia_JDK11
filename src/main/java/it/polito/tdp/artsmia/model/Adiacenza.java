package it.polito.tdp.artsmia.model;

public class Adiacenza implements Comparable<Adiacenza> {
	private int a1;
	private int a2;
	private int peso;
	
	public Adiacenza(int a1, int a2, int peso) {
		super();
		this.a1 = a1;
		this.a2 = a2;
		this.peso = peso;
	}

	public int getA1() {
		return a1;
	}

	public void setA1(int a1) {
		this.a1 = a1;
	}

	public int getA2() {
		return a2;
	}

	public void setA2(int a2) {
		this.a2 = a2;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	@Override
	public String toString() {
		return "(" + a1 + "," + a2 + ")=" + peso;
	}


	@Override
	public int compareTo(Adiacenza o) {
		return -(this.peso - o.getPeso());
	}
	
	
}
