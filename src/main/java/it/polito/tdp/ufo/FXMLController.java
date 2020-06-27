package it.polito.tdp.ufo;

import java.net.URL;
import java.time.Year;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.ufo.model.Anni;
import it.polito.tdp.ufo.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Anni> boxAnno;

    @FXML
    private ComboBox<String> boxStato;

    @FXML
    private TextArea txtResult;

    @FXML
    void handleAnalizza(ActionEvent event) {
    	txtResult.clear();
    	
    	String stato= this.boxStato.getValue();
    	if(stato==null) {
    		txtResult.appendText("Seleziona uno stato");
    		return;
    	}
    	
    	List<String> precedenti= this.model.precedenti(stato);
    	List<String> successivi= this.model.successivi(stato);
    	List<String> elenco= this.model.elencoArchi(stato);

    	txtResult.appendText("Stati immediatamente precedenti a "+ stato +":\n");
    	txtResult.appendText(precedenti.toString()+"\n");
    	
    	txtResult.appendText("Stati immediatamente successivi a "+ stato +":\n");
    	txtResult.appendText(successivi.toString()+"\n");
    	
    	txtResult.appendText("Stati raggiungibili da "+ stato +"\n");
    	for(String s: elenco) {
    		txtResult.appendText(s.toString()+"\n");
    	}
    }

    @FXML
    void handleAvvistamenti(ActionEvent event) {
    	txtResult.clear();

    	Year anno= this.boxAnno.getValue().getAnno();
    	if(anno==null) {
    		txtResult.appendText("seleziona un anno");
    		return;
    	}
    	
    	this.model.creaGrafo(anno);
    	txtResult.appendText("Grafo creato con "+ this.model.nVertici() +"vertici e con "+ this.model.nArchi()+ "archi\n");
    	
    	this.boxStato.getItems().clear();
    	this.boxStato.getItems().addAll(this.model.getVertici(anno));
    }

    @FXML
    void handleSequenza(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Ufo.fxml'.";
        assert boxStato != null : "fx:id=\"boxStato\" was not injected: check your FXML file 'Ufo.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Ufo.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		this.boxAnno.getItems().addAll(this.model.getAnni());
	}
}
