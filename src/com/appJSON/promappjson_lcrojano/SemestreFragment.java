package com.appJSON.promappjson_lcrojano;

import com.appJSON.promappjson_lcorjano.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class SemestreFragment extends Fragment {
	private SimuladorSemestralFragment simulador;
	private String TAG = MenuPrincipalFragment.class.getSimpleName();
	private AsignaturasListFragment asignaturas;
	public  SemestreFragment() {
		// TODO Auto-generated constructor stub
	 
	
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
	}
	Toast toast;
	@Override
	public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_semestre, container, false);
		Student stud = ((MainActivity)getActivity()).mHelper.getStudent(); 
		//TODO: Cargar el estudiante logueado
		//TODO: mostrar promedio obtenido hasta el momento
		if(stud!= null){
			((MainActivity) getActivity()).setPreferences("stud_id", stud.getID()+"");
			Semester sem = ((MainActivity)getActivity()).mHelper.getSemester(stud.getID());
			((MainActivity) getActivity()).setPreferences("semester_id", sem.getID()+"");
			toast = new Toast(getActivity());
			((TextView) rootView.findViewById(R.id.textViewSemestre))
						.setText(sem.getSemestre()+" Semestre");
            ((TextView) rootView.findViewById(R.id.textViewCreditosMatriculados))
	        			.setText("Creditos Matriculados: "+sem.getCreditos());
            double req = ((MainActivity)getActivity()).roundTwoDecimals(sem.getPromRequerido());
			((TextView) rootView.findViewById(R.id.textViewPromedioRequerido))
				        .setText(""+req);
            if(sem.getPromSimulado()==0){
    			((TextView) rootView.findViewById(R.id.textViewPromedioSimulado))
				.setText("Aún no ha simulado!");
            }else{
    			((TextView) rootView.findViewById(R.id.textViewPromedioSimulado))
				.setText(""+sem.getPromSimulado());
            }


			
			
			//toast.setText("cargado");
			//toast.show();
		
		}else{
			//si no hay estudiante, volver a la pantalla de crear estudiante
        	FragmentManager fm = getActivity().getSupportFragmentManager(); 
	    	FirstTimeFragment nAsig = new FirstTimeFragment();                
	    	nAsig.show(fm, "FirstTime"); 
		}

		
		Button button = (Button) rootView.findViewById(R.id.buttonAddNewSubject);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 FragmentManager fm = getActivity().getSupportFragmentManager(); 
		    	 AddAsignaturaFragment nAsig = new AddAsignaturaFragment();                
		    	 nAsig.show(fm, "nueva_asignatura"); 
			}
		});
		simulador = new SimuladorSemestralFragment();
		asignaturas = new AsignaturasListFragment();
		FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.row1, simulador);
			ft.replace(R.id.row2, asignaturas)
		    .commit();
		return rootView;
	}
	

		
	 

	
}
