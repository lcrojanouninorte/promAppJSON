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
import android.widget.Toast;

public class AddEvaluacionFragment extends DialogFragment {
	View vi;
	private Toast toast;
	@Override   
	 public void onCreate(Bundle savedInstanceState) {       
		 super.onCreate(savedInstanceState);    
		 //student = new Student();  
	 }
	 @Override   
	 public Dialog onCreateDialog(Bundle savedInstanceState) { 
		 vi = getActivity().getLayoutInflater().inflate(R.layout.fragment_add_evauacion, null); 
		 AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		 builder.setView(vi);
		 builder.setTitle(R.string.text_title_nueva_eval);
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
			 long asig_id = getArguments().getLong("asignatura");
			 Evaluation e = new Evaluation();
		   	 EditText edit = (EditText)v.findViewById(R.id.editTextNombreEval);
		   	 String nombre = edit.getText().toString();
		   	 //TODO:verificar que la asignatura no exista
		   	 //TODO:verificar que no exida el porcentaje
		   	 edit = (EditText)v.findViewById(R.id.editTextPorcentajeEval);
		   	 String porcentaje = edit.getText().toString();
		   	 if(!e.setPorcentaje(porcentaje)){
		   		 edit.setError("Ingrese un porcentaje valido");
		   		 go =false;
		   	 }else{
				  boolean porcentajeLleno = ((MainActivity)getActivity()).mHelper.isPorcentajeLleno(e.getPorcentaje(), asig_id,-1);
				  if(porcentajeLleno){
					  edit.setError("Esa asignatura Sobrepasa el porcentaje");
				      go =false;
				  }
		   	 }
			  
		   	 
		   	 if(!e.setNombre(nombre)){
		   		 edit.setError("Olvidaste colocar el nombre!");
		   		 go =false;
		   	 }

		   	  	 
		   	 if(go){
		   		
		   		e.setNota_Requerida(getArguments().getFloat("nota_requerida"));
			   	e.setAsignaturaID(asig_id);
		   	    DatabaseHelper mHelper =  ((MainActivity)getActivity()).mHelper;
		   	    e.setID(mHelper.insertEvaluation(e));
				if(e.getID() == -1){
					go = false;
					toast.setText("Error al Ingresar Datos");
                  	toast.show();
                  	edit.setError(null);					
				}else{
					

				}
			}
		   	((MainActivity)getActivity()).mHelper.closeDB();
			return go;
		}


}
