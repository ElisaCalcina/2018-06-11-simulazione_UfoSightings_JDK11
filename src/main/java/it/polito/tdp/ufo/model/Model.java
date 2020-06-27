package it.polito.tdp.ufo.model;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.ufo.db.SightingsDAO;

public class Model {

	SightingsDAO dao;
	Graph<String, DefaultEdge> grafo;
	List<String> vertici;
	List<Arco> archi;
	
	public Model() {
		dao= new SightingsDAO();
	}
	
	
	public void creaGrafo(Year anno) {
		grafo= new SimpleDirectedGraph<>(DefaultEdge.class);
		vertici= this.dao.getVertici(anno);
		archi= this.dao.getArchi(anno);
		
		Graphs.addAllVertices(grafo, vertici);
		
		for(Arco a: archi) {
			if(grafo.containsVertex(a.getStato1()) && this.grafo.containsVertex(a.getStato2())) {
				Graphs.addEdgeWithVertices(grafo, a.getStato1(), a.getStato2());
				//grafo.addEdge(a.getStato1(), a.getStato2());
				
			}
		}
		
		System.out.println("Grafo creato con "+ this.grafo.vertexSet().size()+"vertici e con "+ this.grafo.edgeSet().size()+"archi");
		
	}
	
	public List<Anni> getAnni() {
		return this.dao.getAnni();
	}
	
	public List<String> getVertici(Year anno){
		return this.dao.getVertici(anno);
	}

	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public List<String> precedenti(String stato){
		return Graphs.predecessorListOf(grafo, stato);
	}
	
	public List<String> successivi(String stato){
		return Graphs.successorListOf(grafo, stato);
	}
	
	public List<String> elencoArchi(String stato){
		BreadthFirstIterator<String, DefaultEdge> bfv= new BreadthFirstIterator<>(this.grafo, stato);
		
		List<String> result= new ArrayList<>();
		bfv.next();
		while(bfv.hasNext()) {
			result.add(bfv.next());
		}
		return result;
	}
}
