package com.appJSON.promappjson_lcrojano;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.Inflater;

import com.appJSON.promappjson_lcorjano.R;



import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.ToggleButton;



public class AsignaturasListFragment extends ListFragment{
	String[] text; 
  

	Bundle args;
	ImageButton imgButton;
	long asignatura_id;
	 List<HashMap<String,Object>> aList;
	 View rootView;
	
 
  @Override  
  public View onCreateView(LayoutInflater inflater, ViewGroup container,  
    Bundle savedInstanceState) {  
	  
	  /** Restore from the previous state if exists */
   

	  rootView = inflater.inflate(R.layout.listfragment_asignaturas, container, false);
	  //obtener las asignaturas creadas, si las hay
	  args = new Bundle();
	  String sem_id = ((MainActivity) getActivity()).getPreferences("semester_id");
	  long id = Long.parseLong(sem_id);
	  aList = ((MainActivity) getActivity()).mHelper.getSubjectItems(id);
	  if( aList  != null){
		  //text = evaluaciones;
		  String[] from = { "nombre","nota_deseada","nota_real","creditos","estado","img","id_asignatura","toggle_estado"};
          // Ids of views in listview_layout
          int[] to = { R.id.nombre,R.id.nota_deseada,R.id.nota_real,R.id.creditos,R.id.estado, R.id.img, R.id.id_asignatura,R.id.toggle_estado};

      // Instantiating an adapter to store each items
      // R.layout.listview_layout defines the layout of each item
      SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), 
    		  aList, R.layout.list_item_semester_layout, from, to);
      setListAdapter(adapter);

      return super.onCreateView(inflater, container, savedInstanceState);

	      
		  
	  }else{
		
			  text = new String[1];
			  text[0] = "!No has ingresado ninguna asignatura!";
		  
		  ArrayAdapter<String> adapter = new ArrayAdapter<String>(  
				     inflater.getContext(), 
				     android.R.layout.simple_list_item_1,  
				     text);  
				   setListAdapter(adapter);  
				   return super.onCreateView(inflater, container, savedInstanceState);  
	  }
	  
 
  }  

  @Override  
  public void onListItemClick(ListView l, View v, int position, long id) {  
	  super.onListItemClick(l, v, position, id);
		String val;
		String asignatura_id;
		String asig_name;
		ListView lView = (ListView) l;
        SimpleAdapter adapter = (SimpleAdapter) lView.getAdapter();
        HashMap<String,Object> hm = (HashMap) adapter.getItem(position);
        /** The clicked Item in the ListView */
        LinearLayout rLayout = (LinearLayout) v;
        /** Getting the toggle button corresponding to the clicked item */
        ToggleButton tgl = (ToggleButton) rLayout.findViewById(R.id.toggle_estado);
        if(text==null){
        	asignatura_id = hm.get("id_asignatura").toString();
    		asig_name = hm.get("nombre").toString();
    		AsignaturaFragment asigFragment = new AsignaturaFragment();
    		args.putString("id_asignatura", asignatura_id);
    		asigFragment.setArguments(args); 
    		FragmentTransaction ft = getFragmentManager().beginTransaction();
    		ft.replace(R.id.container, asigFragment,"asignatura");	
    		ft.addToBackStack(null);
    		ft.commit();
    	    Toast.makeText(getActivity(), asig_name, Toast.LENGTH_SHORT).show();
        }else{
        	Toast.makeText(getActivity(), "Ingresa una asignatura", Toast.LENGTH_SHORT).show();
        }
  }
}
