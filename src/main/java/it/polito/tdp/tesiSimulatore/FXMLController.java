package it.polito.tdp.tesiSimulatore;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.tesiSimulatore.model.Area;
import it.polito.tdp.tesiSimulatore.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;
	
	private Map<Integer, String> monthsMap;
	
	// percentuale vittime salvate
	private double percentage;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCreateGraph;

    @FXML
    private Button btnSimulation;
    

    @FXML
    private ComboBox<Integer> cmbMonth;

    @FXML
    private ComboBox<Integer> cmbYear;

    @FXML
    private TextField txtNumberAmbulance;

    @FXML
    private TextField txtNumberHospital;

    @FXML
    private TextField txtProbability;

    @FXML
    private TextArea txtResult;
    



    
    @FXML
    void handleCreateGraph(ActionEvent event) {
    	
    	txtResult.clear();
        this.txtNumberAmbulance.clear();;
        this.txtNumberHospital.clear();
        this.txtProbability.clear();
    	
    	// recupero valori immessi dall'utente con i relativi controlli  	
    	
    	// controlli errore comboBox
    	Integer year = this.cmbYear.getValue();
    	if (year==null) {
    	    this.txtResult.setText("Si prega di selezionare un anno");
    	    return;
    	}

    	
    	// creo il grafo
//    	long tic = System.currentTimeMillis();
        this.model.creaGrafo(year);
//      long toc = System.currentTimeMillis();
  
        // per verificare che il grafo sia creato con l'esatto numero di vertici e archi
//      this.txtResult.setText("La città presenta " + this.model.getNVertici() + " distretti e ha " + this.model.getNArchi() +" archi\n");
        
        this.txtResult.setText("La città presenta " + this.model.getNVertici() + " distretti\n");
//      this.txtResult.appendText("Elapsed time: " + (toc-tic) + " millisecondi" );
        
        this.cmbYear.setDisable(true);
        this.btnCreateGraph.setDisable(true); 
        this.btnSimulation.setDisable(false);
        this.cmbMonth.setDisable(false);
        this.txtNumberAmbulance.setDisable(false);
        this.txtNumberHospital.setDisable(false);
        this.txtProbability.setDisable(false);
    	//popolare la cmbMonth
    	this.cmbMonth.getItems().clear();
    	for (int i=1; i<13; i++)
    		this.cmbMonth.getItems().add(i);

    }

    @FXML
    void handleSimulate(ActionEvent event) {
    	
    	txtResult.clear();
    	
    	Integer year = this.cmbYear.getValue();
    	
    	// controlli errore comboBox
    	Integer month = this.cmbMonth.getValue();
    	if (month==null) {
    	    this.txtResult.setText("Si prega di selezionare un mese");
    	    return;
    	}
    	
 	
    	// controlli errore numero intero
    	int txtNumberHospital =0;
    	try {
    		txtNumberHospital = Integer.parseInt( this.txtNumberHospital.getText().strip() );
    	} catch(NumberFormatException e) {
    	    this.txtResult.setText("Formato non valido. Il numero di ospedali deve essere un intero");
    	    return;
    	}
    	// controllo che il numero sia compreso tra 1 e 21
    	if(txtNumberHospital <=0 || txtNumberHospital > 21) {
    	  this.txtResult.setText("Il numero di ospedali deve essere un numero compreso tra 1 e 21");
    	  return;
    	}
    	
    	
    	// controlli errore numero intero
    	int txtNumberAmbulance =0;
    	try {
    		txtNumberAmbulance = Integer.parseInt( this.txtNumberAmbulance.getText().strip() );
    	} catch(NumberFormatException e) {
    	    this.txtResult.setText("Formato non valido. Il numero di ambulanze deve essere un intero");
    	    return;
    	}
    	
    	// controllo che il numero di ambulanze sia almeno pari al numero di ospedali
    	if(txtNumberAmbulance < txtNumberHospital) {
    	  this.txtResult.setText("Il numero di ambulanze totale deve almeno essere pari al numero di ospedali");
    	  return;
    	}
    	// controllo che il numero sia compreso tra 1 e 75
    	if(txtNumberAmbulance <= 0 || txtNumberAmbulance > 75) {
    	  this.txtResult.setText("Il numero di ambulanze totale deve essere un numero compreso tra 1 e 75");
    	  return;
    	}
    	
      	// controlli errore numero double
    	double txtProbability = 0.0;
    	try {
    		txtProbability = Double.parseDouble( this.txtProbability.getText().strip() );
    	} catch(NumberFormatException e) {
    	    this.txtResult.setText("Formato non valido. Si prega di immettere un valore numerico come probabilità (utilizzare il punto e non la virgola come separatore decimale)");
    	    return;
    	}
    	// controllo che il numero non sia compreso tra 1 e 21
    	if(txtProbability < 0 || txtProbability > 1) {
    	  this.txtResult.setText("La probabilità deve essere un numero compreso tra 0 e 1");
    	  return;
    	}
    
    	long tic = System.currentTimeMillis(); 
    	try {
    		this.model.eseguiSimulazione(year, month, txtNumberHospital, txtNumberAmbulance, txtProbability);
    		long toc = System.currentTimeMillis();
        	this.txtResult.appendText("Elapsed time: " + (toc-tic)/1000 + " secondi \n" );
        	DecimalFormat df = new DecimalFormat("###.###"); // per stampare la % con 3 cifre decimali
        	this.percentage = ((double) this.model.getPeopleSavedNumber() / this.model.getCollisionsNumber() * 100);   	
        	
        	this.txtResult.appendText("Simulazione eseguita a partire dal mese di " + this.monthsMap.get(month) + " dell'anno " + year + ".\n");
        	this.txtResult.appendText("Sono state salvate " + this.model.getPeopleSavedNumber() + " vite,"
        			+ " corrispondenti al " + df.format(percentage) + "% del totale di " + this.model.getCollisionsNumber() + " vittime,"
        					+ " considerando un totale di "+txtNumberHospital+ " ospedali e "+ txtNumberAmbulance+" ambulanze.\n");
        	this.txtResult.appendText("Gli ospedali si trovano nei seguenti distretti:\n");
        	
        	boolean firstIteration = true;

        	for (Area a : this.model.getRandomAreas()) {
        	    if (firstIteration) {
        	        this.txtResult.appendText(a.toString());
        	        firstIteration = false;
        	    } else {
        	        this.txtResult.appendText(", " + a);
        	    }
        	}
        	this.txtResult.appendText("\nI luoghi in cui sono avvenuti gli incidenti sono:");
        	for (String luogo : this.model.getPlaceCollisionSet()) {
        		this.txtResult.appendText("\n" + luogo.strip());
        	}
        	
        	this.cmbYear.setDisable(false);  
        	this.btnCreateGraph.setDisable(false); 
    	}
    	catch (IllegalArgumentException e) {
    		this.txtResult.setText("Si prega di creare la città prima di avviare la simulazione con un nuovo anno.");
			
    		
		}
    		


    }

    @FXML
    void initialize() {
        assert btnCreateGraph != null : "fx:id=\"CreateGraph\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimulation != null : "fx:id=\"Simulation\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbMonth != null : "fx:id=\"cmbMonth\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbYear != null : "fx:id=\"cmbYear\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNumberAmbulance != null : "fx:id=\"txtNumberAmbulance\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNumberHospital != null : "fx:id=\"txtNumberHospital\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtProbability != null : "fx:id=\"txtProbability\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        
    	//popolo la cmbYear
    	this.cmbYear.getItems().clear();
    	for (int i=2010; i<2023; i++)
    		this.cmbYear.getItems().add(i);
    	
    	
    	
        this.btnSimulation.setDisable(true);
        this.cmbMonth.setDisable(true);
        this.txtNumberAmbulance.setDisable(true);
        this.txtNumberHospital.setDisable(true);
        this.txtProbability.setDisable(true);
        

    }

	public void setModel(Model model) {
		// TODO Auto-generated method stub
		this.model = model;
    	
	    // popolo la monthsMap
	    this.monthsMap = new HashMap<>();
	    this.monthsMap.put(1, "gennaio");
	    this.monthsMap.put(2, "febbraio");
	    this.monthsMap.put(3, "marzo");
	    this.monthsMap.put(4, "aprile");
	    this.monthsMap.put(5, "maggio");
	    this.monthsMap.put(6, "giugno");
	    this.monthsMap.put(7, "luglio");
	    this.monthsMap.put(8, "agosto");
	    this.monthsMap.put(9, "settembre");
	    this.monthsMap.put(10, "ottobre");
	    this.monthsMap.put(11, "novembre");
	    this.monthsMap.put(12, "dicembre");
		
	}

}
