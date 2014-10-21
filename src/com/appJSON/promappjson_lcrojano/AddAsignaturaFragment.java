package com.appJSON.promappjson_lcrojano;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.security.auth.Subject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.appJSON.promappjson_lcorjano.R;



import android.R.bool;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddAsignaturaFragment extends DialogFragment{
	View vi;
	Toast toast;
	protected String TAG = AddAsignaturaFragment.class.getSimpleName();
	protected static Context mContext;
	protected JSONObject mData;
	ArrayList<String> subjectList;
	ArrayList<String> evaluationsList;
	 ArrayList<Asignatura> asignaturas;
	private ArrayList<Asignatura> asignaturasList;

	@Override   
	 public void onCreate(Bundle savedInstanceState) {       
		 super.onCreate(savedInstanceState);    
		 subjectList = new ArrayList<String>();
		 //student = new Student();  
		 
		
	 }

    

	 @Override   
	 public Dialog onCreateDialog(Bundle savedInstanceState) { 
		 //TODO: cargar un promedio deseado de sugerencia
		 vi = getActivity().getLayoutInflater().inflate(R.layout.fragment_add_asignatura, null); 
		 AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		 builder.setView(vi);
		 builder.setTitle(R.string.text_title_nueva_asignatura);
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
	
	 @Override
	 public void onStart()
	 {
		
	     super.onStart();    //super.onStart() is where dialog.show() is actually called on the underlying dialog, so we have to do it after this point
	     
	     String[] materias = ((MainActivity)getActivity()).materias;
	    		 
	     AutoCompleteTextView textView = (AutoCompleteTextView) vi.findViewById(R.id.autoCompleteSubjectName);
	     ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), 
	    		 R.layout.autocomplete_custom_layout ,materias);
	     textView.setAdapter(adapter);
	     
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
	                    	 if(!insertSubject(vi)){    
	                    		 
	                    	 }else{
		                         toast.setText("Datos Ingresados Correctamente");
		                       	 toast.show();
		                       	 dismiss();          
		                       	 //recargar layout menuprincipal		                       
		                       	((MainActivity)getActivity()).reloadFragment("semestre");
	                        }
	                     }
	                 });
	     }
	 }
		private boolean insertSubject(View v){
			 //obtener los views y sus datos
			//Estudiante
			 boolean go = true;
			 DatabaseHelper mHelper =  ((MainActivity)getActivity()).mHelper;
			 String sem_id = ((MainActivity) getActivity()).getPreferences("semester_id");
			 
			 Asignatura e = new Asignatura();
			 AutoCompleteTextView textView = (AutoCompleteTextView) vi.findViewById(R.id.autoCompleteSubjectName);
		   	 String nombre = textView.getText().toString();
		   	 //TODO:verificar que la asignatura no exista
		   	 if(mHelper.subjectNameExist(nombre, sem_id)){
		   		// edit.setError("Una asignatura con ese nombre, ya existe, te recomiendo colocarle otro nobre");
		   		 go =false;
		   	 }
		   	 if(!e.setNombre(nombre)){
		   		// edit.setError("Olvidaste colocar el nombre!");
		   		 go =false;
		   	 }
		   	EditText edit = (EditText)v.findViewById(R.id.editTextCreditosAsignatura);
		   	 String creditos = edit.getText().toString();
		   	 if(!e.setCreditos(creditos)){
		   		 edit.setError("Ingrese creditos validos");
		   		 go =false;
		   	 }
		   	 edit = (EditText)v.findViewById(R.id.editTextNotaRequerida);
		   	 String nota = edit.getText().toString();
		   	 if(!e.setNotaRequerida(nota)){//el nota deseado es la requeridad
		   		 edit.setError("Estos creditos no son validos");
		   		go =false;
		   	 }
		   	 
		   	  //TODO: verificar si el promedio deseado cumple con las restriccioen
		   	  
		   	 
		   	 if(go){
				
				long id = Long.parseLong(sem_id);
			   	e.setSemestreID(id);
				e.setNota_simulada(e.getNotaRequerida()+"");
		   	    
		   	    e.setID(mHelper.insertSubject(e,id));
				if(e.getID() == -1){
					go = false;
					toast.setText("Error al Ingresar Datos");
                  	toast.show();
                  	edit.setError(null);
                  	//colocar evaluaciones
                  
				}else{			
					((MainActivity)getActivity()).setPreferences(e.getNombre(),e.getID()+"" );
					insertEvaluations(nombre, e.getID(), e.getNotaRequerida());
				}
			}
		   	((MainActivity)getActivity()).mHelper.closeDB();
			return go;
		}
		
		
		public boolean insertEvaluations(String materiaNombre, long asig_id, float nota_requerida) {
			mData = ((MainActivity) getActivity()).jsonResponse;
			if (mData == null){
				((MainActivity) getActivity()).updateDisplayForError();
			} else {
				try {
					JSONArray materias = mData.getJSONArray("materias");
					String[] mat = ((MainActivity)getActivity()).materias;
					int index = -1;
					for (int i = 0; i < mat.length; i++) {
						if (mat[i].equals(materiaNombre)){
							index = i;
							break;
						}
					}
					//obtener evaluaciones
					JSONObject materia = materias.getJSONObject(index);
					JSONArray evals = materia.getJSONArray("componetes");
					boolean bol = false;
					for (int i = 0;i< evals.length();i++){	
						JSONObject post =  evals.getJSONObject(i);
						String desc = post.getString("desc");
						String peso = post.getString("peso");
						if(insertEval(desc, peso, asig_id, nota_requerida)){
							toast.setText("Evaluacion Ingresada");
							toast.show();
							bol = true;
						}
						//a.setNombre(title);	
						//if(!checkDuplicate(title)){
						//	subjectList.add(title);							
						//}
					}
					return true;
					
				} catch (JSONException e) {
					Log.e(TAG,"Exception caught!",e);
				}
			}
			return false;
		}
		private boolean insertEval(String nomEval, String peso, long asig_id, float nota_requerida){
			//obtener los views y sus datos
			//Estudiante
			 boolean go = true;
			 Evaluation e = new Evaluation();
		   	 String nombre = nomEval;
		   	 //TODO:verificar que la asignatura no exista
		   	 //TODO:verificar que no exida el porcentaje
		   	 String porcentaje = peso;
		   	 e.setPorcentaje(porcentaje);
		   	 
		   	 if(!e.setPorcentaje(porcentaje)){
		   		// edit.setError("Ingrese un porcentaje valido");
		   		 go =false;
		   	 }else{
				//  boolean porcentajeLleno = ((MainActivity)getActivity()).mHelper.isPorcentajeLleno(e.getPorcentaje(), asig_id,-1);
				//  if(porcentajeLleno){
					 // edit.setError("Esa asignatura Sobrepasa el porcentaje");
				 //     go =false;
				//  }
		   	 }
			  
		   	 
		   	 if(!e.setNombre(nombre)){
		   		// edit.setError("Olvidaste colocar el nombre!");
		   		 go =false;
		   	 }

		   	  	 
		   	 if(go){
		   		
		   		e.setNota_Requerida(nota_requerida);
			   	e.setAsignaturaID(asig_id);
		   	    DatabaseHelper mHelper =  ((MainActivity)getActivity()).mHelper;
		   	    e.setID(mHelper.insertEvaluation(e));
				if(e.getID() == -1){
					go = false;
					toast.setText("Error al Ingresar Datos");
                  	toast.show();
                  	//edit.setError(null);					
				}else{
					

				}
			}
		   	((MainActivity)getActivity()).mHelper.closeDB();
			return go;
		}
		
		boolean checkDuplicate(String subject) {
		    for (String sub : subjectList) {
		      if (sub.equals(subject)){ 
		    	  return true;
		      }
		    }
		    return false;
		}
		
		
		

}
