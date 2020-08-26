package it.polito.tdp.food;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Arco;
import it.polito.tdp.food.model.Model;
import javafx.collections.ObservableList;
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
    private TextField txtCalorie;

    @FXML
    private TextField txtPassi;

    @FXML
    private Button btnAnalisi;

    @FXML
    private Button btnCorrelate;

    @FXML
    private Button btnCammino;

    @FXML
    private ComboBox<String> boxPorzioni;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCammino(ActionEvent event) {
        txtResult.clear();
    	
    	String p= this.boxPorzioni.getValue();
    	
    	 if(p == null) {
         	txtResult.clear();
         	txtResult.appendText("Seleziona un tipo di porzione!\n");
         	return ;
     	}
    	
    	String cS=this.txtPassi.getText();
      	
        Integer n;
      	
      	try {
      		n=Integer.parseInt(cS);
      	}catch(NumberFormatException e) {
      		
      		txtResult.setText("Devi inserire solo numeri");
      		return ;
      	}
      	
      	this.model.init(p, n);
      	
       txtResult.appendText("Il cammino trovato per la "+p+" con passi "+ n+" ha peso: "+model.getBestPeso()+"\n");
    	
    	List<String> cammino=model.getBestCammino();
    	for(String c: cammino) {
    		txtResult.appendText(c+"\n");
    	}
    	 
    }

    @FXML
    void doCorrelate(ActionEvent event) {

    	txtResult.clear();
    	
    	String p= this.boxPorzioni.getValue();
    	
    	 if(p == null) {
         	txtResult.clear();
         	txtResult.appendText("Seleziona un tipo di porzione!\n");
         	return ;
     	}
    	 
    	 List<Arco> archi=this.model.getCorrelati(p);
    	 
    	 for(Arco a:archi) {
    		 txtResult.appendText(a.toString()+"\n");
    	 }

    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	this.boxPorzioni.getItems().clear();
    	
    	String cS=this.txtCalorie.getText();
     	
        Integer c;
     	
     	try {
     		c=Integer.parseInt(cS);
     	}catch(NumberFormatException e) {
     		
     		txtResult.setText("Devi inserire solo numeri");
     		return ;
     	}
     	
     
     	this.model.creaGrafo(c);
     	this.boxPorzioni.getItems().addAll(this.model.getPortion());
     	
     	txtResult.appendText("Grafo Creato!\n");
      	txtResult.appendText("# Vertici: " + model.nVertici()+ "\n");
      	txtResult.appendText("# Archi: " + model.nArchi() + "\n");
     	
    }

    @FXML
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtPassi != null : "fx:id=\"txtPassi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCorrelate != null : "fx:id=\"btnCorrelate\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCammino != null : "fx:id=\"btnCammino\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxPorzioni != null : "fx:id=\"boxPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
	}
}
