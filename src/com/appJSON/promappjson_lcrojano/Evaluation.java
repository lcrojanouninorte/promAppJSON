package com.appJSON.promappjson_lcrojano;

import java.text.DecimalFormat;

public class Evaluation {
	private long id;
	private long asignaturaId;
	private int porcentaje;
	private String nombre;
	private float nota_requerida;
	private float nota_simulada;
	private float nota_real;
	private String estado;
	
	public Evaluation() {
		this.estado = "";
		this.nombre = "";
	}
	public boolean setNombre(String nombre) {
		if(nombre != "" ){
			this.nombre = nombre;
		}else{
			return false;
		}
		return true;
	}
	public String getNombre() {
		return this.nombre;
	}

	public boolean setPorcentaje(int porcentaje) {
		//if(porcentaje>=0 && porcentaje <= 100){
			this.porcentaje = porcentaje;
			return true;
		//}
		//return false;
		
	}
	public boolean setPorcentaje(String por) {
		if(por  != "" ){
			int porcentaje = Integer.parseInt(por);
			return setPorcentaje(porcentaje);
		}
			return false;
		
		
	}
	public int getPorcentaje() {
		return porcentaje;
	}

	public void setAsignaturaID(long asig_id) {
		this.asignaturaId = asig_id;
		
	}
	public long getAsignaturaId() {
		return asignaturaId;
	}

	public long getID() {	
		return this.id;
	}
	public void setID(long id){
		this.id =  id;
	}
	public boolean setNota_simulada(String nota_simulada) {
		if(nota_simulada  != "" ){
			float prom = Float.parseFloat(nota_simulada);
			return setNota_simulada(prom);
		}else{
			return false;
		}
	}


	public boolean setNota_simulada(float prom) {
		if(prom>=0 && prom<=5){
			this.nota_simulada= roundTwoDecimals(prom);
		}else{
			return false;
		}
		return true;
		
	}

	public float getNota_simulada() {
		return nota_simulada;
	}
	public boolean setNota_requerida(String nota) {
		if(nota != "" ){
			float prom = Float.parseFloat(nota);
			if(prom>=0 && prom<=5){
				this.nota_requerida= roundTwoDecimals(prom);
			}else{
				return false;
			}
			return true;
		}else{
			return false;
		}
	}
	public boolean setNota_Requerida(float prom) {
		// TODO Auto-generated method stub
		if(prom>=0 && prom<=5){
			this.nota_requerida= roundTwoDecimals(prom);
		}else{
			return false;
		}
		return true;
	}
	public float getNota_requerida() {
		return nota_requerida;
	}
	
	public boolean setNota_real(String nota) {
		if(nota  != "" ){
			float prom = Float.parseFloat(nota);
			return setNota_real(prom);
		}else{
			return false;
		}
	}
	public boolean setNota_real(float prom) {
		if(prom>=0 && prom<=5){
			this.nota_real= roundTwoDecimals(prom);
		}else{
			return false;
		}
		return true;
		
	}
	public float getNota_real() {
		return nota_real;
	}
	
	public String getEstado() {
		if(this.estado == null){
			this.estado = "";
		}
		return this.estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	float roundTwoDecimals(float d)
	{
	    DecimalFormat twoDForm = new DecimalFormat("#.#");
	    return Float.valueOf(twoDForm.format(d));
	}

}
