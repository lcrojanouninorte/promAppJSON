package com.appJSON.promappjson_lcrojano;





import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;

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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.AttributeSet;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class MenuPrincipalFragment extends Fragment {
	private static final String DIALOG_FIRST = "FIRST";
	private String TAG = MenuPrincipalFragment.class.getSimpleName();
	private SemestreFragment semesterFragment;
	private SimuladorSemestralFragment simulador ;
	private AsignaturasListFragment asignaturas ;
	protected JSONObject mData;
	ArrayList<String> subjectList;
	ArrayList<String> evaluationsList;
	ArrayList<Asignatura> asignaturasList;
	
	public MenuPrincipalFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}
		
	private DatabaseHelper mHelper;
	private Student student;
	 Toast toast;
	 protected static Context mContext;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_menu_principal, container, false);
		//toast = Toast.makeText(getActivity().getApplicationContext(),"", Toast.LENGTH_SHORT);
		DatabaseHelper mHelper =  ((MainActivity)getActivity()).mHelper;
        student = mHelper.getStudent();
        //TODO: autenticar usuario, ahora solo coje al primero que encuentre
		  if(student == null){
			  //Dialog Fragment si es primera vez en la aplicacion
	        	FragmentManager fm = getActivity().getSupportFragmentManager(); 
	        	toast = Toast.makeText(getActivity().getApplicationContext(),"Bienvenido!", Toast.LENGTH_SHORT);
		    	FirstTimeFragment nAsig = new FirstTimeFragment();                
		    	nAsig.show(fm, "FirstTime"); 
	        }else{
	        	//actualizar vista 
	        	toast = Toast.makeText(getActivity().getApplicationContext(),"Bienvenido!", Toast.LENGTH_SHORT);

	            ((TextView) rootView.findViewById(R.id.textViewNombreEstudiante))
	            	        .setText("Nombre: "+student.getNombre());
	            ((TextView) rootView.findViewById(R.id.textViewPromedioAcum))
            		        .setText("Prom. Acum: "+student.getPromAcum());
	            ((TextView) rootView.findViewById(R.id.textViewCreditosCursados))
	            			.setText("Creditos Cursados: "+student.getCredCursados());
	            double reqsem = ((MainActivity)getActivity()).roundTwoDecimals(student.getPromAcumDeseado());
	            ((TextView) rootView.findViewById(R.id.textViewPromDeseado))
            				.setText("Prom. Requerido: "+reqsem);
	            ((MainActivity)getActivity()).setPreferences("stud_id", student.getID()+"");
	            //TODO: investigar si es mejor enviar una instancia del estudiante al main
	            //O guardar su id como preferencial o enviarlo como argumento
	            toast.setText("Hola! Bienvenido");
	            toast.show();
	        }
		Button button = (Button) rootView.findViewById(R.id.buttonEditarSemestre);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {	
				//Abrir fragment semestre, y cargar los datos requeridos
				Log.d(TAG, "Editar Semestre on click");
				int val;
				Bundle args = new Bundle();
		    	SemestreFragment semestre = new SemestreFragment();
			    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
				ft.replace(R.id.container, semestre,"semestre")
				.addToBackStack(null)
				.commit();
			}
		});
		Button buttonSimuladorSemestral = ((Button) rootView.findViewById(R.id.buttonSimuladorPromSemestral));
		buttonSimuladorSemestral.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(TAG, "Editar Simulador Prom Semestral");
				int val;
				
				Bundle args = new Bundle();
				SimuladorPromedioAcumuladoFragment sim = new SimuladorPromedioAcumuladoFragment();
				sim.setArguments(args);
			    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
				ft.replace(R.id.container, sim,"sim")
				.addToBackStack(null)
				.commit();
				
			}
		});
		Button buttonSimuladorAsignatura = ((Button) rootView.findViewById(R.id.buttonSimPromAsignaturas));
		buttonSimuladorAsignatura.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Bundle args = new Bundle();
		    	SemestreFragment semestre = new SemestreFragment();
			    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
				ft.replace(R.id.container, semestre,"semestre")
				.addToBackStack(null)
				.commit();
				
			}
		});
		
	   mHelper.closeDB();
	    
		return rootView;
	}
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	super.onActivityCreated(savedInstanceState);  	
    	mContext = getActivity().getApplicationContext();
		if (isNetworkAvailable()) {
			GetDataTask getDataTask = new GetDataTask();
			getDataTask.execute();
		}
    }
	ProgressDialog progressDialog;

	public class GetDataTask extends AsyncTask<Object, Void, JSONObject> {


		  
		    protected void onProgressUpdate(Integer... progress) {
		        int current = progress[0];
		        int total = progress[1];

		        float percentage = 100 * (float) current / (float) total;

		        progressDialog.setProgress((int) percentage);
		    }
		    protected void onPreExecute() 
		    { 
		    	
		    	progressDialog = new ProgressDialog(getActivity()); 
		        progressDialog.setTitle("Cargando Datos del Servidor...");
		        progressDialog.setMessage("Por favor espere");
		        progressDialog.setCancelable(false);
		        progressDialog.setCanceledOnTouchOutside(false);
		        progressDialog.show();
		    }

		@Override
		protected JSONObject doInBackground(Object... params) {
		    int responseCode = -1;
		    JSONObject jsonResponse = null;
		    StringBuilder builder = new StringBuilder();
		    HttpClient client = new DefaultHttpClient();
		    HttpGet httpget = new HttpGet("http://augustodesarrollador.com/promedio_app/read.php");

		    try {
		        HttpResponse response = client.execute(httpget);
		        StatusLine statusLine = response.getStatusLine();
		        responseCode = statusLine.getStatusCode();

		        if (responseCode == HttpURLConnection.HTTP_OK) {
		            HttpEntity entity = response.getEntity();
		            InputStream content = entity.getContent();
		            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
		            String line;
		            while((line = reader.readLine()) != null){
		                builder.append(line);
		            }
		            jsonResponse = new JSONObject(builder.toString());
		        }
		        else {
		            Log.i(TAG, String.format("Unsuccessful HTTP response code: %d", responseCode));
		        }
		    }
		    catch (JSONException e) {
		      
		    }
		    catch (Exception e) {
		        
		    }           
		    return jsonResponse;
		} 

		@Override
		protected void onPostExecute(JSONObject result) {
			mData = result;
			((MainActivity)getActivity()).jsonResponse = result;
			((MainActivity)getActivity()).materias = getMateriasAutocomplete();
			 progressDialog.dismiss();
	       
			//handleBlogResponse();
			//Spinner mySpinner = (Spinner)vi.findViewById(R.id.spinnerNombreAsignatura);
			 
	            // Spinner adapter
	       /* mySpinner.setAdapter(new ArrayAdapter<String>(
	            				getActivity(),
	                            android.R.layout.simple_spinner_dropdown_item,
	                            subjectList));
	        
	     // Spinner on item click listener
            mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
 
                        @Override
                        public void onItemSelected(AdapterView<?> arg0,
                                View arg1, int position, long arg3) {
                            // TODO Auto-generated method stub
                            // llenar los nrcs


                          
                        }
 
                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                            // TODO Auto-generated method stub
                        }
                    });*/
		}
		public String[] getMateriasAutocomplete() {
			String[] subj = null ;
			mData =((MainActivity)getActivity()).jsonResponse;
			if (mData == null){
				((MainActivity)getActivity()).updateDisplayForError();
			} else {
				try {
					JSONArray jsonPosts = mData.getJSONArray("materias");
					subj = new String[jsonPosts.length()];
					for (int i = 0;i< jsonPosts.length();i++){
						JSONObject post = jsonPosts.getJSONObject(i);
						String title = post.getString("nombre_materia");
						///title  = Html.fromHtml(title).toString();
						subj[i] = title;							
					}
					
				} catch (JSONException e) {
					Log.e(TAG,"Exception caught!",e);
				}
			}
			 return subj;
		}

	}
	

	private boolean isNetworkAvailable() {
		ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(mContext.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getActiveNetworkInfo();
		boolean isNetworkAvaible = false;
		if (networkInfo != null && networkInfo.isConnected()) {
			isNetworkAvaible = true;
			toast.setText("Network is available ");
			toast.show();
		} else {
			toast.setText("Network is NOT available ");
			toast.show();
		}
		return isNetworkAvaible;
	}
	
}
