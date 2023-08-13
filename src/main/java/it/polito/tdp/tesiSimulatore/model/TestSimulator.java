package it.polito.tdp.tesiSimulatore.model;

public class TestSimulator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Simulatore sim = new Simulatore(2022, 10, 1, 1, 0.5);
		
		sim.popolaCoda();	
		// Eseguire simulazione
		sim.processaEventi();

	}

}
