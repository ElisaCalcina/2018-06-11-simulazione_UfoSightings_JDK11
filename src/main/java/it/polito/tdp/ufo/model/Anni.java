package it.polito.tdp.ufo.model;

import java.time.Year;

public class Anni {
	Year anno;
	Integer conto;
	
	public Anni(Year anno, Integer conto) {
		super();
		this.anno = anno;
		this.conto = conto;
	}

	public Year getAnno() {
		return anno;
	}

	public void setAnno(Year anno) {
		this.anno = anno;
	}

	public Integer getConto() {
		return conto;
	}

	public void setConto(Integer conto) {
		this.conto = conto;
	}

	@Override
	public String toString() {
		return  anno + " - "+ conto;
	}
	

}
