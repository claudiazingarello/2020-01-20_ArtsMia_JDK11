package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	ArtsmiaDAO dao;
	List<Adiacenza> adiacenze;
	Graph<Integer, DefaultWeightedEdge> grafo;

	public Model(){
		dao = new ArtsmiaDAO();

	}

	public List<String> listRole(){
		return dao.listRole();
	}

	public void creaGrafo(String ruolo) {
		grafo = new SimpleWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);

		//Aggiungo i vertici
		Graphs.addAllVertices(grafo, dao.listArtistByRole(ruolo));
		
		adiacenze = dao.getAdiacenze(ruolo);
		for(Adiacenza a : adiacenze) {
		/*
			if(!grafo.containsVertex(a.getA1()))
				this.grafo.addVertex(a.getA1());

			if(!grafo.containsVertex(a.getA2()))
				this.grafo.addVertex(a.getA2());
		*/
			if(this.grafo.getEdge(a.getA1(), a.getA2()) == null) 
				Graphs.addEdgeWithVertices(grafo, a.getA1(), a.getA2(), a.getPeso());
		}

		System.out.println("Grafo creato!");
		System.out.println("#vertici: "+ grafo.vertexSet().size());
		System.out.println("#archi: "+ grafo.vertexSet().size());
	}

	public int vertici() {
		return this.grafo.vertexSet().size();
	}

	public int archi() {
		return this.grafo.edgeSet().size();
	}

	public List<Adiacenza> getArtistiConnessi() {
		//List<Adiacenza> result = new ArrayList<Adiacenza>(adiacenze);
		Collections.sort(adiacenze);
		return adiacenze;
	}
}
