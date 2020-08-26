package it.polito.tdp.food.model;

import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDAO;

public class Model {

	private Graph<String,DefaultWeightedEdge> graph;
	FoodDAO dao;
	List<String> vertici;
	
	Double bestPeso;
	List<String> bestCammino;
	
	public Model() {
		this.dao=new FoodDAO();
	}
	
	//creo il grafo
	public void creaGrafo(int c) {
		
	this.graph=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
	
	vertici=this.dao.getVertici(c);
	Graphs.addAllVertices(graph, vertici);
	
	List<Arco> archi=this.dao.getArco();
	
	for(Arco a: archi) {
		if(this.graph.containsVertex(a.getP1()) && this.graph.containsVertex(a.getP2())) {
			Graphs.addEdgeWithVertices(graph, a.getP1(), a.getP2(),a.getPeso());
		}
	}
	
	}
	
	
	//NUMERO VERTICI:

	public int nVertici() {
			return this.graph.vertexSet().size();
		}

	//NUMERO ARCHI:

		public int nArchi() {
			return this.graph.edgeSet().size();
		}
		
		public List<String> getPortion(){
			return this.vertici;
		}
		
	//dato la porzione voglio i vicini 
		public List<Arco> getCorrelati(String p){
			List<Arco> result=new ArrayList<>();
			
			List<String> porzioni=Graphs.neighborListOf(graph, p);
			
			for(String s: porzioni) {
				if(!result.contains(s)) {
					double peso=this.graph.getEdgeWeight(this.graph.getEdge(p, s));
					Arco a=new Arco(p,s,peso);
					result.add(a);
				}
			}
			
			return result;
		}
		
	//inizializzo 
		public void init(String partenza, int n) {
			this.bestPeso=0.0;
			this.bestCammino=null;
			
			List<String> parziale= new ArrayList<>();
			parziale.add(partenza);
			ricorsione(parziale,1,n);
			
		}

	private void ricorsione(List<String> parziale, int l, int n) {
		
			//caso terminale 
			if(l==n+1) {
			double peso=this.pesoCammino(parziale);
			if(peso>bestPeso) {
				
			this.bestPeso=peso;
			this.bestCammino=new ArrayList<>(parziale);
			}
			return;
			}
			
			List<String> vicini=Graphs.neighborListOf(graph, parziale.get(l-1));
			//ricorsione
			
			for(String v:vicini) {
				if(!parziale.contains(v)) {
					parziale.add(v);
					ricorsione(parziale,l+1,n);
					parziale.remove(v);
				}
			}
		
	}

		private double pesoCammino(List<String> parziale) {
			double peso=0.0;
			
			for(int i=1; i<parziale.size(); i++) {
				double pNew=this.graph.getEdgeWeight(this.graph.getEdge(parziale.get(i-1), parziale.get(i)));
				peso+=pNew;
			}
			
			
			return peso;
			
		}

		public Double getBestPeso() {
			return bestPeso;
		}

		public List<String> getBestCammino() {
			return bestCammino;
		}
		
		
		
}
