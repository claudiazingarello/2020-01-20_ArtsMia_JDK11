package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.sun.javafx.binding.StringFormatter;

import it.polito.tdp.artsmia.model.Adiacenza;
import it.polito.tdp.artsmia.model.Model;
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
    private Button btnCreaGrafo;

    @FXML
    private Button btnArtistiConnessi;

    @FXML
    private Button btnCalcolaPercorso;

    @FXML
    private ComboBox<String> boxRuolo;

    @FXML
    private TextField txtArtista;

    @FXML
    private TextArea txtResult;

    @FXML
    void doArtistiConnessi(ActionEvent event) {
    	txtResult.clear();
    	
    	List<Adiacenza> adiacenze = this.model.getArtistiConnessi();
    	
    	if(adiacenze == null) {
    		txtResult.appendText("Devi creare prima il grafo");
    		return;
    	}
    	for(Adiacenza a : adiacenze) {
    		txtResult.appendText(a.toString() +"\n");
    	}
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {

    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	
    	String ruolo = boxRuolo.getValue();
    	
    	if(ruolo == null) {
    		txtResult.appendText("Devi scegliere un ruolo");
    		return;
    	}
    	
    	this.model.creaGrafo(ruolo);
    	txtResult.appendText(String.format("Grafo creato!\n#%d vertici\n#%d archi", model.vertici(), model.archi()));
    	
    }

    @FXML
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnArtistiConnessi != null : "fx:id=\"btnArtistiConnessi\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnCalcolaPercorso != null : "fx:id=\"btnCalcolaPercorso\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert boxRuolo != null : "fx:id=\"boxRuolo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtArtista != null : "fx:id=\"txtArtista\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		
		boxRuolo.getItems().addAll(this.model.listRole());
	}
}

