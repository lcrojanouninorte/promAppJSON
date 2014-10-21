package com.appJSON.promappjson_lcrojano;

import com.appJSON.promappjson_lcorjano.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class EditEvaluationFragment extends DialogFragment {
	View vi;
	Evaluation eval;
	private Toast toast;
	 EditText nombre;
	 TextView ideva;
	 EditText nota;
	 EditText porcentaje;
	 ToggleButton estado;
	 ImageView img;
	Bundle args;
	TextView title;
	String nombre_evaluacion;
	String eva_id;
	 long asig_id;
	@Override   
	 public void onCreate(Bundle savedInstanceState) {       
		 super.onCreate(savedInstanceState);    
		 //student = new Student();  
	 }
	 @Override   
	 public Dialog onCreateDialog(Bundle savedInstanceState) { 
		// String eval_name= getArguments().getString("eval_name");
		// asig_id = getArguments().getLong("asig_id");
		// ((MainActivity)getActivity()).mHelper.getEvaluationByName(asig_id, eval_name);
		 
		 asig_id = getArguments().getLong("asignatura_id");
		 eva_id = getArguments().getString("id_eval");
		 ((MainActivity)getActivity()).setPreferences("asignatura_id", asig_id+"");
		 nombre_evaluacion = getArguments().getString("nombre_eval");
		 ((MainActivity)getActivity()).setPreferences("nombre_eval_old", nombre_evaluacion+"");
		 vi = getActivity().getLayoutInflater().inflate(R.layout.dialog_fragment_edit_evaluation, null); 
		 title = (TextView)vi.findViewById(R.id.textViewTitle);
		 ideva = (TextView) vi.findViewById(R.id.id_eval);
		 ideva.setText(eva_id);
		 title.setText(getArguments().getString("nombre_eval"));
		 img = (ImageView) vi.findViewById(R.id.imageEstado);
		 img.setImageResource(getArguments().getInt("img"));
		 nombre = (EditText)vi.findViewById(R.id.nombre);
		 nombre.setText(getArguments().getString("nombre_eval"));
		 nota = (EditText)vi.findViewById(R.id.nota);
		 nota.setText(getArguments().getString("nota"));
		 porcentaje = (EditText)vi.findViewById(R.id.porcentaje);
		 porcentaje.setText(getArguments().getString("porcentaje"));
		 estado = (ToggleButton)vi.findViewById(R.id.toggle_estado);
		 if(getArguments().getString("estado").equals("Finalizado")){
			 estado.setChecked(true);
			 img.setImageResource(R.drawable.finalizado);
		 }else{
			 estado.setChecked(false);
			 img.setImageResource(R.drawable.enprogreso);
		 }
		 
		 
		 AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		 builder.setView(vi);
		 builder.setTitle(R.string.text_title_edit_eval);
		 builder.setPositiveButton(android.R.string.ok, 
				 new DialogInterface.OnClickListener() {
				 	@Override
		            public void onClick(DialogInterface dialog, int which)
		            {
		                //Do nothing here solo para compatibilidad
		            }
		         });
		   
		 return builder.create(); 
	}  
	 public void onStart()
	 {
	     super.onStart();    //super.onStart() is where dialog.show() is actually called on the underlying dialog, so we have to do it after this point
	     AlertDialog d = (AlertDialog)getDialog();
	     toast = Toast.makeText(getActivity().getApplicationContext(),
	    		 "Datos agregados Correctamente", 
	    		 Toast.LENGTH_SHORT);
	     if(d != null)
	     {
	         Button positiveButton = (Button) d.getButton(Dialog.BUTTON_POSITIVE);
	         positiveButton.setOnClickListener(
	        		 new View.OnClickListener() {
	                     @Override
	                     public void onClick(View v)
	                     {
	                    	
	                    	 if(!insertEval(vi)){    
	                    		 toast.setText("Verifica los mensajes de error");
	                    	 }else{
		                         toast.setText("Datos Ingresados Correctamente");
		                       	 toast.show();

		                       	 dismiss();
		                         
		                       	 //recargar layout menuprincipal
		                       
		                       	((MainActivity)getActivity()).reloadFragment("asignatura");
	                        }
	                     }
	                 });
	     }
	 }
		private boolean insertEval(View v){
			//obtener los views y sus datos
			//Estudiante
			
			 boolean go = true;
			 String a = ((MainActivity)getActivity()).getPreferences("asignatura_id");
			 asig_id = Long.parseLong(a);

			   	
			 
			 Evaluation e = new Evaluation();
		   	 String nom = nombre.getText().toString();
		   	 String not = nota.getText().toString();
		     String porcent = porcentaje.getText().toString();
		   	 String est = estado.getText().toString();
		   	 String id_evaluacion = ideva.getText().toString();
	

		   	 if(!e.setPorcentaje(porcent)){
		   		 porcentaje.setError("Ingrese un porcentaje valido");
		   		 go =false;
		   	 }else{
		   		 int resto = (100 - e.getPorcentaje());
				  boolean porcentajeLleno = ((MainActivity)getActivity()).mHelper.isPorcentajeLleno(e.getPorcentaje(), asig_id,resto);
				  if(porcentajeLleno){
					  porcentaje.setError("Esa asignatura Sobrepasa el porcentaje");
				      go =false;
				  }
		   	 }
			  
			 if(estado.isChecked()){
				 e.setEstado("Finalizado");
			 }else{
				 e.setEstado("Progreso");
			 }
	
		   	 if(!e.setNombre(nom)){
		   		 nombre.setError("Olvidaste colocar el nombre!");
		   		 go =false;
		   	 }
		   	 if(est.equals("Finalizado")){
			   	 if(!e.setNota_simulada(not)||e.setNota_real(not)||e.setNota_requerida(not)){
			   	
			   		 nota.setError("Nota no valida!");
			   		 go =false;
			   	 }
		   	 }else{
			   	 if(!e.setNota_simulada(not)||!e.setNota_requerida(not)){
					   	
			   		 nota.setError("Nota no valida!");
			   		 go =false;
			   	 }
		   		 
		   	 }

		   	  	 
		   	 if(go){
		   		
		   		//e.setNota_Requerida(getArguments().getFloat("nota_requerida"));
			   	e.setAsignaturaID(asig_id);
		   	    DatabaseHelper mHelper =  ((MainActivity)getActivity()).mHelper;
		        SimulatorHelper simHelper = new SimulatorHelper();
		   	
		   	   
		   	    long id_eva = Long.parseLong(id_evaluacion);
		   	    e.setID(id_eva);
		   	    e.setNombre(nombre_evaluacion);
		   	    Asignatura asignatura = mHelper.getSubjectByID(asig_id);
		   	    Evaluation[] evals = mHelper.getEvaluations(asig_id);
		   	    float pr  = simHelper.getPromObtenidoEvals(evals);
		   	    if(pr<asignatura.getNotaRequerida()){
		   	    	//TODO:mostrar algo si el promedio es menor al requerido
		   	    }
				if(!mHelper.updateEvaluacionesByID(e)){
					
					go = false;
					toast.setText("Error al Ingresar Datos");
                  	toast.show();
                  	//nombre.setError(null);					
				}else{
					//actualizar promedio obtenido
					
					asignatura.setNota_simulada(pr+"");
					mHelper.updateNotaSimuladaAsignatura(asignatura);
				}
			}
			   	 
		   	((MainActivity)getActivity()).mHelper.closeDB();
			return go;
		}


}
