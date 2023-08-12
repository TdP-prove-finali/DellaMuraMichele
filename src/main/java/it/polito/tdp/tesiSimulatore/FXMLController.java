package it.polito.tdp.tesiSimulatore;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.tesiSimulatore.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

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
    	// recupero valori immessi dall'utente con i relativi controlli  	
    	
    	// controlli errore comboBox
    	Integer year = this.cmbYear.getValue();
    	if (year==null) {
    	    this.txtResult.setText("Si prega di selezionare un anno");
    	    return;
    	}

    	
    	// creo il grafo
        this.model.creaGrafo(year);
  
        // per verificare che il grafo sia creato con l'esatto numero di vertici e archi
//      this.txtResult.setText("La città presenta " + this.model.getNVertici() + " distretti e ha " + this.model.getNArchi() +" archi\n");
        
        this.txtResult.setText("La città presenta " + this.model.getNVertici() + " distretti\n");
        
        this.cmbYear.setDisable(true);        
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
    	
    	// controlli errore comboBox
    	Integer cmbMonth = this.cmbMonth.getValue();
    	if (cmbMonth==null) {
    	    this.txtResult.setText("Si prega di selezionare un mese");
    	    return;
    	}
    	
    	// controlli errore numero intero
    	int txtNumberAmbulance =0;
    	try {
    		txtNumberAmbulance = Integer.parseInt( this.txtNumberAmbulance.getText() );
    	} catch(NumberFormatException e) {
    	    this.txtResult.setText("Formato non valido. Il numero di ambulanze deve essere un intero!");
    	    return;
    	}
    	// controllo che il numero non sia compreso tra 1 e 3
    	if(txtNumberAmbulance<=0 || txtNumberAmbulance >3) {
    	  this.txtResult.setText("Il numero di ambulanze per ogni distretto deve essere un numero compreso tra 1 e 3");
    	  return;
    	}
    	
    	
    	// controlli errore numero intero
    	int txtNumberHospital =0;
    	try {
    		txtNumberHospital = Integer.parseInt( this.txtNumberHospital.getText() );
    	} catch(NumberFormatException e) {
    	    this.txtResult.setText("Formato non valido. Il numero di distretti deve essere un intero");
    	    return;
    	}
    	// controllo che il numero non sia compreso tra 1 e 21
    	if(txtNumberHospital<=0 || txtNumberHospital >21) {
    	  this.txtResult.setText("Il numero di distretti deve essere un numero compreso tra 1 e 21");
    	  return;
    	}
    	
      	// controlli errore numero double
    	double txtProbability = 0.0;
    	try {
    		txtProbability = Double.parseDouble( this.txtProbability.getText() );
    	} catch(NumberFormatException e) {
    	    this.txtResult.setText("Formato non valido. Si prega di inserire un numero");
    	    return;
    	}
    	// controllo che il numero non sia compreso tra 1 e 21
    	if(txtProbability < 0 || txtProbability > 1) {
    	  this.txtResult.setText("La probabilità deve essere un numero compreso tra 0 e 1");
    	  return;
    	}
    
    	
    	
//    	
//    	
//    	this.model.eseguiSimulazione(nomeTeam, anno, txtTifosi);
//    	this.txtResult.appendText("Simulazione eseguita.\n");
//    	this.txtResult.appendText("Il numero di tifosi persi dalla squadra "+ nomeTeam + " è: " + model.getnTifosiPersiTot()+"\n");
//    	for (String giocatore : model.getnTifosiPerPlayer().keySet()) {
//    		this.txtResult.appendText("il giocatore "+giocatore+ " ha "+model.getnTifosiPerPlayer().get(giocatore) +" tifosi\n");
//    	}
    	
    	this.cmbYear.setDisable(false);  

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
        
    	//popolare la cmbYear
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
		
	}

}
