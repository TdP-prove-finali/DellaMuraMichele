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
//       this.txtResult.setText("La città presenta " + this.model.getNVertici() + " distretti e ha " + this.model.getNArchi() +" archi\n");
        this.txtResult.setText("La città presenta " + this.model.getNVertici() + " distretti\n");
        
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
    	
//    	// controlli errore comboBox
//    	String nomeTeam = this.cmbSquadra.getValue();
//    	if (nomeTeam==null) {
//    	    this.txtResult.setText("Please select a Team");
//    	    return;
//    	}
//    	Integer anno = this.cmbAnno.getValue();
//    	if (anno==null) {
//    	    this.txtResult.setText("Please select un anno");
//    	    return;
//    	}
//    	
//    	// controlli errore numero intero
//    	int txtTifosi =0;
//    	try {
//    		txtTifosi = Integer.parseInt( this.txtTifosi.getText() );
//    	} catch(NumberFormatException e) {
//    	    this.txtResult.setText("Invalid argument. Il numero di tifosi must be a integer!");
//    	    return;
//    	}
//    	// controllo che il numero non sia negativo
//    	if(txtTifosi<0) {
//    	  this.txtResult.setText("Il numero di tifosi must be a nonnegative integer.");
//    	  return;
//    	}
//    	
//    	
//    	this.model.eseguiSimulazione(nomeTeam, anno, txtTifosi);
//    	this.txtResult.appendText("Simulazione eseguita.\n");
//    	this.txtResult.appendText("Il numero di tifosi persi dalla squadra "+ nomeTeam + " è: " + model.getnTifosiPersiTot()+"\n");
//    	for (String giocatore : model.getnTifosiPerPlayer().keySet()) {
//    		this.txtResult.appendText("il giocatore "+giocatore+ " ha "+model.getnTifosiPerPlayer().get(giocatore) +" tifosi\n");
//    	}

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
