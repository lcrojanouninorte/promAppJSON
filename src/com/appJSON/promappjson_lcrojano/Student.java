package com.appJSON.promappjson_lcrojano;

import java.util.Date;
import java.util.UUID;

public class Student {
    private long mId;    
    private Date mStartDate; 
    private String nombre;
    private String estado;
    private float promAcum;
    private float promAcumDeseado;
	   
	private int credCursados;
	private Semester semester;
	private float maxDeseado;
	   
	 
	public Student() {
		mId = 0; 
	    mStartDate = new Date();
	    this.promAcum = 0;
	    this.promAcumDeseado = 0;
	    this.estado = "";
	    

		
	}
	public boolean setPromAcumDeseado(float promAcumDeseado) {

		if(promAcumDeseado>=0 && promAcumDeseado <=5){
			this.promAcumDeseado = promAcumDeseado;
		}else{
			return false;
		}
		return true;
		
	}

	private boolean setEstado(String promAcum) {
		float prom = Float.parseFloat(promAcum);
		if(prom<3.25){
			this.estado = "PP o FP";
		}else{
			if(prom<3.95){
				this.estado = "NN";
			}else{
				if(prom < 5.0){
					this.estado = "ED";
				}
			else{
				return false;
			}
		}
	}
		return true;
	}
	public long getID() {
		return mId;
	}
	public void setID(long mId) {
		this.mId = mId;
	}

	public float getPromAcum() {
		// TODO Auto-generated method stub
		return promAcum;
	}
	public int getCreditos() {
		// TODO Auto-generated method stub
		return credCursados;
	}
    public String getEstado() {
		return estado;
	}
	

	   
    public Date getmStartDate() {
		return mStartDate;
	}
	
	public String getNombre() {
		return nombre;
	}
	public boolean setNombre(String nombre) {
		if(!nombre.isEmpty() ){
			this.nombre = nombre;
		}else{
			return false;
		}
		return true;
	}
	public int getCredCursados() {
		return credCursados;
	}
	public boolean setCredCursados(int cred) {
		if(cred >=0){
			this.credCursados = cred;
		}else{
			return false;
		}
		return true;
	}
	public boolean setCredCursados(String credCursados) {
		if(!credCursados.isEmpty()){
			int cred = Integer.parseInt(credCursados);
			if(cred >=0){
				this.credCursados = cred;
			}else{
				return false;
			}
			return true;
		}else{
			return false;
		}
	}
	public void setmStartDate(Date mStartDate) {
		this.mStartDate = mStartDate;
	}

	public boolean setPromAcum(String promAcum) {
		if(!promAcum.isEmpty()){
			float prom = Float.parseFloat(promAcum);
			if(prom>=0 && prom<=5){
				this.promAcum = prom;
				setEstado(promAcum);
			}else{
				return false;
			}
			return true;
		}else{
			return false;
		}
		
	}
	public float getPromAcumDeseado() {
	
		return this.promAcumDeseado;
	}
	public void setSemester(Semester semester) {
		this.semester = semester;
		
	}
	public void setMaxDeseado(float maxDeseado) {
		this.maxDeseado = maxDeseado;
		
	}
	public float getMaxDeseado() {
		return maxDeseado;
	}


	   
}
