package com.appJSON.promappjson_lcrojano;

import com.appJSON.promappjson_lcorjano.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ViewCalculationsDialgogFragment extends DialogFragment {
	View vi;
	Toast toast;
	@Override   
	 public void onCreate(Bundle savedInstanceState) {       
		 super.onCreate(savedInstanceState);    
		 //student = new Student();  
	 }
	 @Override   
	 public Dialog onCreateDialog(Bundle savedInstanceState) { 
		 //TODO: cargar un promedio deseado de sugerencia
		 vi = getActivity().getLayoutInflater().inflate(R.layout.fragmentdialog_view_calculations, null); 
		 AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		 builder.setView(vi);
		 builder.setTitle(R.string.text_simulador_acumulado);
		 builder.setPositiveButton(android.R.string.ok, 
				 new DialogInterface.OnClickListener() {
				 	@Override
		            public void onClick(DialogInterface dialog, int which)
		            {
		                //Do nothing here solo para compatibilidad
		            }
		         });
	
		/* viewCalculationsListFragment list = new viewCalculationsListFragment();
		 FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
		 ft.replace(R.id.row1, list).commit();*/

		 return builder.create();
    
	}  
	 

			
}
