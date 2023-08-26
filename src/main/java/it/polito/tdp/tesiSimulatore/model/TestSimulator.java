package it.polito.tdp.tesiSimulatore.model;

public class TestSimulator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Simulatore sim = new Simulatore(2021, 11, 3, 75, 0.5, null);
		
		sim.popolaCoda();	
		// Eseguire simulazione
		sim.processaEventi();

	}

}
