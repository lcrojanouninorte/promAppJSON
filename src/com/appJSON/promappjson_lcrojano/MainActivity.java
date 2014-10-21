package com.appJSON.promappjson_lcrojano;






import java.text.DecimalFormat;
import java.util.List;

import org.json.JSONObject;

import com.appJSON.promappjson_lcorjano.R;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.R.menu;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;


public class MainActivity extends FragmentActivity {
	private SimuladorSemestralFragment simulador;
	public Student e;
	public Semester s;
	public Evaluation eval;
	public SharedPreferences prefs ;
	public static final String MyPREFERENCES = "MyPrefs" ;
	private AsignaturaFragment asignaturas;
	private MenuPrincipalFragment menuPrincipal;
	private SemestreFragment semestre;
	public DatabaseHelper mHelper;
	public SimulatorHelper simHelper;
	private Student student;
	private Semester sem;
	public JSONObject jsonResponse;
	public String[] materias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getApplicationContext();
		prefs = getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
        if(mHelper==null){
        	mHelper = new DatabaseHelper(this);
        }
        if(simHelper == null){
        	simHelper = new SimulatorHelper();
        }
        menuPrincipal = new MenuPrincipalFragment();       
        FragmentManager fm = getSupportFragmentManager();  
        fm.beginTransaction()        	
          .add(R.id.container, menuPrincipal, "menu_principal")
          .addToBackStack(null)
          .commit();
    }
    public void setPreferences(String key, String value){
       	
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(key, value);
		editor.commit();
    	
    }
    public String getPreferences(String key){
    	return prefs.getString(key, "not");
    }

    public void showAsignaturaFragment(){
    	
	    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.container, asignaturas)
		.addToBackStack(null)
		.commit();
    }


	public void reloadFragment(String tag){
	   	// Reload current fragment
    	Fragment frg = null;
    	frg = getSupportFragmentManager().findFragmentByTag(tag);
    	final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    	ft.detach(frg);
    	ft.attach(frg);
    	ft.commit();
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_reset) {
        	if (mHelper == null){
        		mHelper = new DatabaseHelper(this);
        	}
        	mHelper.clearAll();
            menuPrincipal = new MenuPrincipalFragment();       
            FragmentManager fm = getSupportFragmentManager();  
            fm.beginTransaction()        	
              .add(R.id.container, menuPrincipal, "menu_principal")
              .addToBackStack(null)
              .commit();
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }



	
	
	double roundTwoDecimals(double d)
	{
	    DecimalFormat twoDForm = new DecimalFormat("#.##");
	    return Double.valueOf(twoDForm.format(d));
	}
    
	public void updateDisplayForError() {
		AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
	    builder.setTitle("Error en la Conexion");
		builder.setMessage("la tabla no existe");
		builder.setPositiveButton(android.R.string.ok, null);
		AlertDialog dialog = builder.create();
		dialog.show();
		
		//TextView emptyTextView = (TextView) getListView().getEmptyView();
		//emptyTextView.setText(getString(R.string.no_items));
	}


}
 
