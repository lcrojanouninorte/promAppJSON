package com.appJSON.promappjson_lcrojano;

import com.appJSON.promappjson_lcorjano.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SimuladorPromedioAcumuladoFragment extends DialogFragment{
	EditText promDeseado;
	EditText promRequerido;
	EditText creditosCursados ;
	Toast toast;
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.simulador_prom_acum_fragment, container, false);
		toast = Toast.makeText(getActivity().getApplicationContext(),"Bienvenido Nuevamente", Toast.LENGTH_SHORT);

		 promDeseado =(EditText) v.findViewById(R.id.editTextPromAcumDeseado);
		 promRequerido =(EditText) v.findViewById(R.id.editTextPromRequerido);
	     creditosCursados =(EditText) v.findViewById(R.id.editTextCreditosCursados);
		
		Button button = (Button) v.findViewById(R.id.buttonCambiarAcumDeseado);
		button.setOnClickListener(new OnClickListener() {
			//cambiar promedio acumulado deseado, y todos los que dependan de este
			@Override
			public void onClick(View v) {
				String prom = promDeseado.getText().toString();	
				String creditos  = creditosCursados.getText().toString();	
				if(!prom.isEmpty()){
					if(!creditos.isEmpty()){
						float pd = Float.parseFloat(prom);
						int cm = Integer.parseInt(creditos);
						Bundle args = new Bundle();
						args.putFloat("pd", pd);
						args.putInt("cm", cm);
						FragmentManager manager = getFragmentManager();
				        viewListCalcFragmen dialog = new viewListCalcFragmen();
				        dialog.setArguments(args);
				        dialog.show(manager, "dialog_simulated");
					
						toast.setText("Calculado!");
						toast.show();
					}else{
						creditosCursados.setError("No lo dejes vacio!");
					}
					
				}else{
					 promDeseado.setError("No lo dejes vacio!");
				}
			}
		});
	return v;
	}


}
