package com.appJSON.promappjson_lcrojano;

import com.appJSON.promappjson_lcorjano.R;

import android.R.string;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class AsignaturaFragment extends Fragment{
	EvaluationListFragment evaluaciones;
	AddEvaluacionFragment nAsig;
	Bundle args;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
	}
	String nombre_asignatura;
	String id_asig;
	Toast toast;
	View rootView;
	String mensaje;
	Asignatura asig;
	ToggleButton b ;
	EditText textSim;
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_asignatura, container, false);
		args = getArguments();
		textSim = (EditText)rootView.findViewById(R.id.editTextChangeRequerido);
		id_asig = getArguments().getString("id_asignatura");
		String sem_id = ((MainActivity) getActivity()).getPreferences("semester_id");
		long id_sem = Long.parseLong(sem_id);
		long id_asignatura = Long.parseLong(id_asig);
		toast =  Toast.makeText(getActivity().getApplicationContext(),
	    		 "Datos agregados Correctamente", 
	    		 Toast.LENGTH_SHORT);
		asig = ((MainActivity)getActivity()).mHelper.getSubjectByID(id_asignatura);
		if(asig != null){
			double req = ((MainActivity)getActivity()).roundTwoDecimals(asig.getNotaRequerida());
			((TextView) rootView.findViewById(R.id.editTextChangeRequerido))
			
						.setText(req+"");
			((TextView) rootView.findViewById(R.id.textViewTitleEditAsig))
	        			.setText(asig.getNombre());
			int por = ((MainActivity)getActivity()).mHelper.getPorcentajeIngresado(asig.getID());
            ((TextView) rootView.findViewById(R.id.textViewPorcentaje))
	        			.setText("Porcentaje Ingresado: "+por);
            
			((TextView) rootView.findViewById(R.id.textViewNotaRequerida))
				        .setText(""+req);
			
			
			((TextView) rootView.findViewById(R.id.textViewNotaSimulada))
						.setText(""+asig.getNotaSimulada());
			((MainActivity)getActivity()).setPreferences("asig_id", asig.getID()+"");
			toast.setText(nombre_asignatura);
			toast.show();
		}else{
			toast.setText("Algo anda mal con la db");
			toast.show();
		}
		
		//// get your ToggleButton
		 b = (ToggleButton) rootView.findViewById(R.id.toggleButtonFinalizarAsignatura);
		 if (asig.getEstado().equals("Finalizado")){
			 b.setChecked(true);
		 }else{
			 b.setChecked(false);
		 }
		// attach an OnClickListener
		b.setOnClickListener(new OnClickListener()
		{
		    @Override
		    public void onClick(View v)
		    {
		      if( b.isChecked() ){
		    	  //Finalizar Asignatura
		    	  toast.setText("Finalizada!");
		    	  
		    	mensaje = ((MainActivity)getActivity()).mHelper.finalizarAsignatura(asig);
		    	if(mensaje.equals("OK")){
		    		
		    	}else{
		    		toast.setText(mensaje);
		    		b.setChecked(false);
		    	}
		    	  
		      }else{
		    	  //Todo: recalcular
		    	  toast.setText("En Curso!");
		      }
		      toast.show();
		    }
		   
		});
		Button button2 = (Button) rootView.findViewById(R.id.buttonSimuladorSemestral);
		button2.setOnClickListener(new OnClickListener() {
			@Override

			public void onClick(View v) {
				
				//TODO: recalcular cuanto necesita sacar en las asignaturas que estan no finalizadas.
				long a_id = asig.getID();
		       
		        String not = textSim.getText().toString();
				SimulatorHelper s = new SimulatorHelper();
				if(asig.setNotaRequerida(not)){

					Evaluation[] evals = ((MainActivity)getActivity()).mHelper.getEvaluations(asig.getID());
				    for (Evaluation  eval : evals) {
						//actualizar la nota deseada
				    	if(!eval.getEstado().equals("Finalizado")){
				    		eval.setNota_Requerida(asig.getNotaRequerida());
				    	}
					}
					Evaluation[] evalSim = s.simularPromedioEvaluaciones(asig, evals,asig.getNotaRequerida() );
					
					//actualizar visualmente
					if(evalSim!=null){
						for (Evaluation eva : evalSim) {
							((MainActivity)getActivity()).mHelper.updateEvaluacionesByID(eva);
						}
						((MainActivity)getActivity()).reloadFragment("asignatura");
						//actualizar semestre
						//Asignatura[] asignaturas = new Asignatura[]{asig};
						//((MainActivity)getActivity()).mHelper.updatePromRequeridoAsignaturas(asignaturas);
						toast.setText("Simulado con éxito!");
						toast.show();
					}else{
						textSim.setError("no es posible sacar esa nota");
					}
				}else{
					textSim.setError("Dato no valido");
				}
			}
		});
	
		Button button = (Button) rootView.findViewById(R.id.buttonAddNewEval);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 FragmentManager fm = getActivity().getSupportFragmentManager(); 
		    	 AddEvaluacionFragment nAsig= new AddEvaluacionFragment(); 
				 args.putLong("asignatura", asig.getID());
				 nAsig.setArguments(args);
		    	 nAsig.show(fm, "add_eval"); 
			}
		});

		
 	 
		args.putLong("asignatura_id", asig.getID());
		args.putFloat("nota_requerida", asig.getNotaRequerida());
		evaluaciones = new EvaluationListFragment();
		evaluaciones.setArguments(args);
		FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.row1, evaluaciones,"lista_evaluaciones")
		    .commit();
		return rootView;
	}

}
