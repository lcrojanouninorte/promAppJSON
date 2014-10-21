package com.appJSON.promappjson_lcrojano;



import java.util.HashMap;
import java.util.List;

import com.appJSON.promappjson_lcorjano.R;


import android.os.Bundle;

import android.support.v4.app.FragmentManager;

import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;

import android.widget.ListView;
import android.widget.SimpleAdapter;


public class EvaluationListFragment extends ListFragment {
	String[] text; 
	Bundle args;
	ImageButton imgButton;
	long asignatura_id;
	 List<HashMap<String,String>> aList;
	 View rootView;
	
	  @Override  
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {  
		  rootView = inflater.inflate(R.layout.listfragment_asignaturas, container, false);			
		/*  
*/
			
		  //obtener las asignaturas creadas, si las hay
		  args = new Bundle();
		  asignatura_id = getArguments().getLong("asignatura_id");
	//////////////////////////////////7
		  aList = ((MainActivity) getActivity()).mHelper.getEvaluationsItems(asignatura_id);
		  
		 // String[] evaluaciones = ((MainActivity) getActivity()).mHelper.getEvaluacionesNames(asignatura_id);
		  if( aList  != null){
			  //text = evaluaciones;
			  String[] from = { "nombre","nota","porcentaje","estado","img","id_eval"};
	          // Ids of views in listview_layout
	          int[] to = { R.id.nombre,R.id.nota,R.id.porcentaje,R.id.estado, R.id.img, R.id.id_eval};

	      // Instantiating an adapter to store each items
	      // R.layout.listview_layout defines the layout of each item
	      SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), 
	    		  aList, R.layout.list_item_evaluaciones_layout, from, to);
	      setListAdapter(adapter);

	      return super.onCreateView(inflater, container, savedInstanceState);

		      
			  
		  }else{
			  text = new String[1];
			  text[0] = "!No has ingresado ninguna evaluación!";
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
			
			HashMap<String, String> hm = new HashMap<String,String>();
			hm = aList.get(position);
			String nombre = hm.get("nombre");
			val = hm.get("estado").replace("!", "");
			val = val.replace("Estado: ", "");
			String estado = val;
			val = hm.get("nota").replace("Prom. Requerido: ", "");
			String nota = val;
			String img = hm.get("img");
			val =hm.get("porcentaje").replace("%","");
			val = val.replace("Porcentaje: ", "");
			String porcentaje = val;
			String ideval = hm.get("id_eval");
			
			 imgButton = (ImageButton)v.findViewById(R.id.imageButtonDelete);
			 
		
			args.putString("nombre_eval", nombre);
			args.putLong("asignatura_id", asignatura_id);
			args.putString("estado", estado);
			args.putString("nota", nota);
			args.putString("porcentaje", porcentaje);
			args.putString("img", img);
			args.putString("id_eval", ideval);
			
				FragmentManager fm = getActivity().getSupportFragmentManager(); 
		    	EditEvaluationFragment nAsig = new EditEvaluationFragment();   
		    	nAsig.setArguments(args);
		    	nAsig.show(fm, "edit_eval"); 
			
	  }  
	  
	
		 /*     imgButton = (ImageButton)rootView.findViewById(R.id.imageButtonDelete);
			  imgButton.setOnClickListener(new OnClickListener() {
				  @Override
				  public void onClick(View view) {
					  final int position = getListView().getPositionForView(view);
					    if (position != ListView.INVALID_POSITION) {
					        //DO THE STUFF YOU WANT TO DO WITH THE position
					    	HashMap<String, String> hm = new HashMap<String,String>();
							hm = aList.get(position);
							String nombre = hm.get("nombre");
							String estado = hm.get("nombre");
							String nota = hm.get("nombre");
							String porcentaje = hm.get("nombre");
							
					    }
			 
				  }
			  
			  });
			return super.getView();*/
		
}
