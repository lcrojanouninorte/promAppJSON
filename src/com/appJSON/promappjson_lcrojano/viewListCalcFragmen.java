package com.appJSON.promappjson_lcrojano;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.appJSON.promappjson_lcorjano.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;
public class viewListCalcFragmen extends DialogFragment{


    ListView mylist;

    @Override
    public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.listfragment_view_simulated, container, false);
        mylist = (ListView) view.findViewById(R.id.listViewSimuProm);

        getDialog().setTitle("Promedio Semestral Simulado");
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

     // View rootView = inflater.inflate(R.layout.listfragment_asignaturas, container, false);
		  //obtener las asignaturas creadas, si las hay
		  float pd = (float)getArguments().getFloat("pd");
		  long cm = getArguments().getInt("cm");
		  DatabaseHelper helper = ((MainActivity)getActivity()).mHelper;
		  Student stud = helper.getStudent();
		  Semester sem = helper.getSemester(stud.getID());
		  int cc = stud.getCredCursados();
		  float pa = stud.getPromAcum();
		  List<HashMap<String,String>>   aList = new ArrayList<HashMap<String,String>>();
		  HashMap<String, String> hm = new HashMap<String,String>();
	        hm.put("creditos", "Creditos");
	        hm.put("nota", "Promedio");
	        aList.add(hm);
		  for (int i = 2;i<=22;i++){

			SimulatorHelper sim = new SimulatorHelper();
			float calculado  = sim.getPromRequeridoSemestral(cc, i, pd, pa);
			double calc  = ((MainActivity)getActivity()).roundTwoDecimals(calculado);
			if(true){
				  if(i == cm && cm<=22){
					  hm = new HashMap<String,String>();
				        hm.put("creditos", "(-- " + i+  " --)");
				        hm.put("nota", "" +"(-- "+ calc+" --)");
				        aList.add(hm);
					  
				  }else{
					hm = new HashMap<String,String>();
			        hm.put("creditos", "" + i);
			        hm.put("nota", "" + calc);
			        aList.add(hm);
				  }
		        }
			}
			
         String[] from = { "creditos","nota"};
        // Ids of views in listview_layout
        int[] to = { R.id.creditos,R.id.nota};

    // Instantiating an adapter to store each items
    // R.layout.listview_layout defines the layout of each item
    SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), 
  		  aList, R.layout.list_view_calculatios_layout, from, to);
        mylist.setAdapter(adapter);

       

    }

}
