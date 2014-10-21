package com.appJSON.promappjson_lcrojano;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.appJSON.promappjson_lcorjano.R;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class viewCalculationsListFragment extends ListFragment{
	String[] text; 
	  @Override  
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {  
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
				
		        hm = new HashMap<String,String>();
		        hm.put("creditos", "" + i);
		        hm.put("nota", "" + calc);
		        aList.add(hm);
		        }
			}
			
           String[] from = { "creditos","nota"};
          // Ids of views in listview_layout
          int[] to = { R.id.creditos,R.id.nota};

      // Instantiating an adapter to store each items
      // R.layout.listview_layout defines the layout of each item
      SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), 
    		  aList, R.layout.list_view_calculatios_layout, from, to);
      setListAdapter(adapter);

      return super.onCreateView(inflater, container, savedInstanceState);

	  }  
	  @Override  
	  public void onListItemClick(ListView l, View v, int position, long id) {  
		  super.onListItemClick(l, v, position, id);
			String val;
			if(text[position] != "!No has ingresado ninguna asignatura!"){

			}
	  }  

}
