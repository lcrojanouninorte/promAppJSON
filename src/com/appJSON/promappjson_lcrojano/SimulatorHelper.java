package com.appJSON.promappjson_lcrojano;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;

import android.util.Log;

public class SimulatorHelper {
	int minCred = 1;
	float maxPromSemestre = (float) 5;
	
	public float getMaxDeseado(float promAcum, float promDeseado, int credCursados, int maxCred){
		long startTime = System.currentTimeMillis();
		float requerido = (promDeseado*(credCursados + maxCred) - (promAcum*credCursados))/maxCred;
		//Si el promedio requerido del semestre es mayor que 5, recomiendo bajar el desead acumulado
		if(requerido>maxPromSemestre){
			do{
				promDeseado = (float) (promDeseado - 0.001);
				requerido = (promDeseado*(credCursados + maxCred) - (promAcum*credCursados))/maxCred;
			}while(requerido>maxPromSemestre);
		}else{
			do{
				promDeseado = (float) (promDeseado + 0.001);
				requerido = (promDeseado*(credCursados + maxCred) - (promAcum*credCursados))/maxCred;
			}while(requerido<=maxPromSemestre);
			promDeseado = (float) (promDeseado - 0.001);
		}
		long endTime = System.currentTimeMillis();
		long consumedTime = endTime-startTime;
		Log.d("max", consumedTime+"");
		
		return (float) roundTwoDecimals(promDeseado);
		
	}
	double roundTwoDecimals(double d)
	{
	    DecimalFormat twoDForm = new DecimalFormat("#.##");
	    return Double.valueOf(twoDForm.format(d));
	}
	public float getPromRequeridoSemestral(int CC, int CM, float PD,float PA) {
		float PS = 0;
		PS = (PD*(CC+CM)-(CC*PA))/CM;
		return PS;
	}
	
	public Asignatura[] distribuirPromedioAAsignaturas(Asignatura[] asignaturas, float PromDeseado){
		 for (Asignatura asignatura : asignaturas) {  
			 if(!asignatura.getEstado().equals("Finalizado")){
				 asignatura.setNotaRequerida(PromDeseado+"");
			 }
		 }
		 
		 return asignaturas;
	}
	public Evaluation[] distribuirPromedioAEvaluaciones(Evaluation[] evals, float PromDeseado){
		 for (Evaluation eval : evals) {  
			 if(!eval.getEstado().equals("Finalizado")){
				 eval.setNota_Requerida(PromDeseado);
			 }
		
		 }
		 return evals;
	}
	public float getPuntosObtenidosAsignatura(Asignatura[] asignaturas, float PromDeseado){
		
		float puntos = 0;
		asignaturas = distribuirPromedioAAsignaturas(asignaturas, PromDeseado);
	    for (Asignatura asignatura : asignaturas) {  
	    	if(!asignatura.getEstado().equals("Finalizado")){
			 puntos = puntos +((float) asignatura.getCreditos()*asignatura.getNotaRequerida());
			 }else{
				 puntos = puntos + asignatura.getCreditos()*asignatura.getNotaReal();
			 }
		 }
		 
		 return puntos;
		 
	}

	public HashMap<String, Object> getNotaAsignaturasSimuladas(Asignatura[] asignaturas, float notaRequerida, float dif, int CM) {
		//getPuntosObtenidosAsignatura(asignaturas);
		//obtener creidotos a excluir, es decir las asignaturas finalizadas
		HashMap<String, Object> hm = new HashMap<String, Object>();
		int creditosExcluidos = 0;
		float creditosUsables = 0;
		float notaSimulada  = -1;
		float puntos = 0;
		float porcion = 0;
		for (Asignatura asignatura : asignaturas) {
			if (asignatura.getEstado().equals("Finalizado")){
				creditosExcluidos = creditosExcluidos + asignatura.getCreditos(); //porcion que le toca de la diferencia;
			}		
		}
		creditosUsables = CM - creditosExcluidos;
		for (Asignatura asignatura : asignaturas) {
			if (!asignatura.getEstado().equals("Finalizado")){
				if(notaSimulada == -1 && asignatura.getCreditos()>0){
					//calcular la notaSimulada si no se ha hecho
					puntos = notaRequerida*asignatura.getCreditos();
					float cred = asignatura.getCreditos(); 
					porcion = (float)(cred/creditosUsables)*dif; //porcion que le toca de la diferencia;
					notaSimulada =(float) (puntos+porcion)/cred;
					
				}
				if(!(notaSimulada <= 5 && notaSimulada>=0)){
					//si no es menor de 5 no es posible ese prom deseado
					//Puee que sea una nota menor a 0 o mayor a 5
					hm.put("mensaje", "Requeire una nota de "+notaSimulada + "que no es posible sacar");
					hm.put("asignaturas", null);
					return hm;
				}else{
					asignatura.setNota_simulada(notaSimulada+"");
					asignatura.setNotaRequerida(notaSimulada+"");
				}
			}
			
		}
		hm.put("mensaje", "OK");
		hm.put("asignaturas", asignaturas);
		return hm;
	}
	public Evaluation[] getNotaEvaluacionesSimuladas(Evaluation[] evals, float dif) {
		//getPuntosObtenidosAsignatura(asignaturas);
		//obtener creidotos a excluir, es decir las asignaturas finalizadas
		int porcentajesExcluidos = 0;
		int porcentajesUsables = 0;
		float notaSimulada  = -1;
		float puntos = 0;
		float porcion = 0;
		for (Evaluation eval : evals) {
			if (eval.getEstado().equals("Finalizado")){
				porcentajesExcluidos = porcentajesExcluidos + eval.getPorcentaje(); //porcion que le toca de la diferencia;
			}		
		}
		porcentajesUsables = 100 - porcentajesExcluidos; //100 corresponde al 100%
		for (Evaluation eval : evals) {
			if (!eval.getEstado().equals("Finalizado")){
				if(notaSimulada == -1){
					//calcular la notaSimulada si no se ha hecho
					puntos = (float)eval.getNota_requerida()*eval.getPorcentaje();
					porcion = (float)(eval.getPorcentaje()/porcentajesUsables)*dif; //porcion que le toca de la diferencia;
					notaSimulada = (puntos+porcion)/eval.getPorcentaje();
					if(!(notaSimulada <= 5)){
						//si no es menor de 5 no es posible ese prom deseado
						return null;
					}else{
						eval.setNota_Requerida(notaSimulada);
					}
					
				}
			}
			
		}
		return evals;
	}

	

	public float getPorcentajeObtenidoEvaluacion(Evaluation[] evals) {
		 float puntos = 0;
		 for (Evaluation eval : evals) {  
			puntos = puntos + eval.getPorcentaje()*eval.getNota_requerida();
		 }
		 
		 return puntos;
	}
	
	public Evaluation[] simularPromedioEvaluaciones(Asignatura asig, Evaluation[] evals, float deseado) {
//getPuntosObtenidosAsignatura(asignaturas);
		//obtener creidotos a excluir, es decir las asignaturas finalizadas
		int porcentajesExcluidos = 0;
		int porcentajesUsables = 0;
		float notaSimulada  = -1;
		float puntos = 0;
		float porcion = 0;
		
		//Calcular promedio con la actual configuracion de notas
		float promObtenido = getPromObtenidoEvals(evals);
		float dif = deseado - promObtenido;
		for (Evaluation eval : evals) {
			if (eval.getEstado().equals("Finalizado")){
				porcentajesExcluidos = porcentajesExcluidos + eval.getPorcentaje(); //porcion que le toca de la diferencia;
				
			}		
		}
		porcentajesUsables = 100 - porcentajesExcluidos; //100 corresponde al 100%
		for (Evaluation eval : evals) {
			if (!eval.getEstado().equals("Finalizado")){
				if(notaSimulada == -1){
					//calcular la notaSimulada si no se ha hecho
					puntos = (float)eval.getNota_requerida()*((float)eval.getPorcentaje()/100);
					porcion = (float)(((float)eval.getPorcentaje()/100)/((float)porcentajesUsables/100))*dif; //porcion que le toca de la diferencia;
					notaSimulada = (puntos+porcion)/((float)eval.getPorcentaje()/100);
					if(!(notaSimulada <= 5)){
						//si no es menor de 5 no es posible ese prom deseado
						return null;
					}else{
						eval.setNota_Requerida(notaSimulada);
					}
					
				}
			}
			
		}
		return evals;
	}

	public float getPromObtenidoEvals(Evaluation[] evals) {
		float prom = 0;
		float p = 0;
	 for (Evaluation eval: evals) {
		p = eval.getNota_requerida()*((float)eval.getPorcentaje()/100);
		prom = prom + p;
	}
		return prom;
	}

	public float getPromObtenidoSemestral(Asignatura[] asigs, int cred) {
		float prom = 0;
		float p = 0;
	 for (Asignatura asig: asigs) {
		p = asig.getNotaReal()*((float)asig.getCreditos());
		prom = prom + p;
	}
		return (float)prom/cred;
	}
}
