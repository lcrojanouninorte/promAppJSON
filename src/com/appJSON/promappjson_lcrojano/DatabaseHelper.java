package com.appJSON.promappjson_lcrojano;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.appJSON.promappjson_lcorjano.R;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper  extends SQLiteOpenHelper {
	private static final String DB_NAME = "students.db";    
	private static final int VERSION = 7;        
	private static final String COLUMN_ID= "_id"; 
	
	private static final String TABLE_STUDENT = "students";    
	private static final String COLUMN_STUDENT_NAME = "nombre"; 
	private static final String COLUMN_STUDENT_PROM_ACUM = "prom_acum";
	private static final String COLUMN_STUDENT_PROM_ACUM_REQUERIDO = "prom_acum_requerido";//Deseado
	private static final String COLUMN_STUDENT_CREDITOS_CURSADOS = "creditos_cursados"; 
	
	private static final String TABLE_SEMESTER= "semesters";    
	private static final String COLUMN_SEMESTER_NAME = "nombre"; 
	private static final String COLUMN_SEMESTER_PROM_REQUERIDO = "prom_requerido";  
	private static final String COLUMN_SEMESTER_PROM_REAL = "prom_real"; 
	private static final String COLUMN_SEMESTER_PROM_SIMULADO = "prom_simulada"; 
	private static final String COLUMN_SEMESTER_CREDITOS = "creditos";
	private static final String COLUMN_SEMESTER_ESTADO = "estado";
	private static final String COLUMN_SEMESTER_STUDENT_ID = "student_id";
	
	private static final String TABLE_SUBJECTS= "subjects";    
	private static final String COLUMN_SUBJECT_NAME = "nombre"; 
	private static final String COLUMN_SUBJECT_NOTA_REQUERIDA = "nota_requerido";  
	private static final String COLUMN_SUBJECT_NOTA_REAL = "nota_real";
	private static final String COLUMN_SUBJECT_NOTA_SIMULADA = "nota_simulada";
	private static final String COLUMN_SUBJECT_CREDITOS = "creditos";
	private static final String COLUMN_SUBJECT_ESTADO = "estado";
	private static final String COLUMN_SUBJECT_SEMESTER_ID = "semester_id";
	
	private static final String TABLE_EVALUATIONS= "evaluations";    
	private static final String COLUMN_EVALUATION_NAME = "nombre"; 
	private static final String COLUMN_EVALUATION_NOTA_REQUERIDA = "nota_requerida";  
	private static final String COLUMN_EVALUATION_NOTA_REAL = "nota_real"; 
	private static final String COLUMN_EVALUATION_NOTA_SIMULADA = "nota_simulada"; 
	private static final String COLUMN_EVALUATION_PORCENTAJE = "porcentaje";
	private static final String COLUMN_EVALUATION_ESTADO = "estado";
	private static final String COLUMN_EVALUATION_SUBJECT_ID = "subject_id";
	
	private static final String CREATE_TABLE_STUDENTS = 
			"CREATE TABLE " + TABLE_STUDENT 
			+ "(" 
            + COLUMN_ID + " INTEGER PRIMARY KEY autoincrement, " 
			+ COLUMN_STUDENT_NAME + " TEXT, " 
            + COLUMN_STUDENT_PROM_ACUM + " REAL, " 
			+ COLUMN_STUDENT_PROM_ACUM_REQUERIDO + " REAL, " 
			+ COLUMN_STUDENT_CREDITOS_CURSADOS + " INTEGER " 
			+ ");";
	private static final String CREATE_TABLE_SEMESTERS = 
			"CREATE TABLE " + TABLE_SEMESTER
			+ "(" 
            + COLUMN_ID + " INTEGER PRIMARY KEY autoincrement, " 
			+ COLUMN_SEMESTER_NAME + " TEXT, " 
            + COLUMN_SEMESTER_PROM_REAL + " REAL, " 
			+ COLUMN_SEMESTER_PROM_REQUERIDO + " REAL, " 
			+ COLUMN_SEMESTER_PROM_SIMULADO + " REAL, " 
			+ COLUMN_SEMESTER_ESTADO + " TEXT, " 
			+ COLUMN_SEMESTER_CREDITOS + " TEXT, " 
			+ COLUMN_SEMESTER_STUDENT_ID + " INTEGER, " 
			+ "FOREIGN KEY(" + COLUMN_SEMESTER_STUDENT_ID  
			+ ") references " + TABLE_STUDENT + "(" + COLUMN_ID+")" 
			+ ");";
	private static final String CREATE_TABLE_SUBJECTS = 
			"CREATE TABLE " + TABLE_SUBJECTS
			+ "(" 
            + COLUMN_ID + " INTEGER PRIMARY KEY autoincrement, " 
			+ COLUMN_SUBJECT_NAME + " TEXT, "
			+ COLUMN_SUBJECT_CREDITOS + " INTEGER, " 
            + COLUMN_SUBJECT_NOTA_REAL + " REAL, " 
			+ COLUMN_SUBJECT_NOTA_REQUERIDA + " REAL, " 
			+ COLUMN_SUBJECT_NOTA_SIMULADA + " REAL, " 
			+ COLUMN_SUBJECT_ESTADO + " TEXT, " 
			+ COLUMN_SUBJECT_SEMESTER_ID + " INTEGER, " 
			+ "FOREIGN KEY(" + COLUMN_SUBJECT_SEMESTER_ID   
			+ ") references " + TABLE_SEMESTER + "(" + COLUMN_ID +") " 
			+ ");";
	private static final String CREATE_TABLE_EVALUATIONS = 
			"CREATE TABLE " + TABLE_EVALUATIONS
			+ "(" 
            + COLUMN_ID + " INTEGER PRIMARY KEY autoincrement, " 
			+ COLUMN_EVALUATION_NAME + " TEXT, "
			+ COLUMN_EVALUATION_PORCENTAJE + " INTEGER, "
            + COLUMN_EVALUATION_NOTA_REAL + " REAL, " 
			+ COLUMN_EVALUATION_NOTA_REQUERIDA + " REAL, "
			+ COLUMN_EVALUATION_NOTA_SIMULADA + " REAL, "
			+ COLUMN_EVALUATION_ESTADO + " TEXT, " 
			+ COLUMN_EVALUATION_SUBJECT_ID + " INTEGER, "
			+ "FOREIGN KEY(" + COLUMN_EVALUATION_SUBJECT_ID 
			+") references " + TABLE_SUBJECTS + "(" + COLUMN_ID +") " 
			+ ");";
	public DatabaseHelper(Context context) {        
		super(context, DB_NAME, null, VERSION);    
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_EVALUATIONS);
		db.execSQL(CREATE_TABLE_SUBJECTS);
		db.execSQL(CREATE_TABLE_SEMESTERS);
		db.execSQL(CREATE_TABLE_STUDENTS);       
			
		       
		
	    if (!db.isReadOnly()) {
	        // Enable foreign key constraints
	        db.execSQL("PRAGMA foreign_keys=ON;");
	        
	    }
	}
	@Override
	public void onOpen(SQLiteDatabase db) {
	    super.onOpen(db);
	    if (!db.isReadOnly()) {
	        // Enable foreign key constraints
	        db.execSQL("PRAGMA foreign_keys=ON;");
	        
	    }
	}
	@Override
	public void onConfigure(SQLiteDatabase db) {
	    if (!db.isReadOnly()) {
	        // Enable foreign key constraints
	        db.execSQL("PRAGMA foreign_keys=ON;");
	        
	    }
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	
		   // on upgrade drop older tables
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVALUATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBJECTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SEMESTER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);


        
     
 
        // create new tables
        onCreate(db);
		
	} 
	public void clearAll(){
		SQLiteDatabase bd = getWritableDatabase();
		bd.execSQL("delete from "+ TABLE_EVALUATIONS);
		bd.execSQL("delete from "+ TABLE_SUBJECTS);
		bd.execSQL("delete from "+ TABLE_SEMESTER);
		bd.execSQL("delete from "+ TABLE_STUDENT);
		
		
		
		

	}
	 public long insertStudentFirsTime(Student student) {        
		 ContentValues cv = new ContentValues();        
		 cv.put(COLUMN_STUDENT_NAME, student.getNombre());
		 cv.put(COLUMN_STUDENT_PROM_ACUM, student.getPromAcum());
		 cv.put(COLUMN_STUDENT_PROM_ACUM_REQUERIDO, student.getPromAcumDeseado());
		 cv.put(COLUMN_STUDENT_CREDITOS_CURSADOS, student.getCreditos());
	
		 return getWritableDatabase().insert(TABLE_STUDENT, null, cv);    
	} 
	 public long insertSemester(long student, Semester semester) {        
		 ContentValues cv = new ContentValues();        
		 cv.put(COLUMN_SEMESTER_NAME, semester.getName());
		 cv.put(COLUMN_SEMESTER_CREDITOS, semester.getCreditos());
		 cv.put(COLUMN_SEMESTER_ESTADO, semester.getEstado());
		 cv.put(COLUMN_SEMESTER_PROM_REQUERIDO, semester.getPromRequerido());
		 cv.put(COLUMN_SEMESTER_PROM_SIMULADO, semester.getPromSimulado());
		 cv.put(COLUMN_SEMESTER_PROM_REAL, semester.getPromReal());
		 cv.put(COLUMN_SEMESTER_STUDENT_ID, student); 
		 
		 return getWritableDatabase().insert(TABLE_SEMESTER, null, cv);    
	}
	
	public long insertSubject(Asignatura asignatura, long semester) {
		 ContentValues cv = new ContentValues();        
		 cv.put(COLUMN_SUBJECT_NAME, asignatura.getNombre().toLowerCase());
		 cv.put(COLUMN_SUBJECT_CREDITOS, asignatura.getCreditos());
		 cv.put(COLUMN_SUBJECT_ESTADO, asignatura.getEstado());
		 cv.put(COLUMN_SUBJECT_NOTA_REQUERIDA, asignatura.getNotaRequerida());
		 cv.put(COLUMN_SUBJECT_NOTA_REAL, asignatura.getNotaReal());
		 cv.put(COLUMN_SUBJECT_NOTA_SIMULADA, asignatura.getNotaSimulada());
		 cv.put(COLUMN_SUBJECT_SEMESTER_ID, semester); 
		 
		 return getWritableDatabase().insert(TABLE_SUBJECTS, null, cv); 
	}
	public long insertEvaluation(Evaluation e) {
		 ContentValues cv = new ContentValues();        
		 cv.put(COLUMN_EVALUATION_NAME, e.getNombre().toLowerCase());
		 cv.put(COLUMN_EVALUATION_PORCENTAJE, e.getPorcentaje());
		 cv.put(COLUMN_EVALUATION_ESTADO, e.getEstado());
		 cv.put(COLUMN_EVALUATION_NOTA_REQUERIDA, e.getNota_requerida());
		 cv.put(COLUMN_EVALUATION_NOTA_REAL, e.getNota_real());
		 cv.put(COLUMN_EVALUATION_NOTA_SIMULADA, e.getNota_simulada());
		 cv.put(COLUMN_EVALUATION_SUBJECT_ID, e.getAsignaturaId()); 
		 
		 return getWritableDatabase().insert(TABLE_EVALUATIONS, null, cv); 
	}
	
	

	// closing database
	    public void closeDB() {
	        SQLiteDatabase db = this.getReadableDatabase();
	        if (db != null && db.isOpen())
	            db.close();
	    }
	
    public Student getStudent() {
    	//debido a que solo estamos almacenado un estudiante no importa que registro estamso consultando
		Cursor cursor = getWritableDatabase().rawQuery("SELECT * FROM " + TABLE_STUDENT, null);
		if (cursor.moveToFirst()){
			Student s = new Student();
			s.setID(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
			s.setNombre(cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_NAME)));
			s.setPromAcum(cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_PROM_ACUM)));
			s.setPromAcumDeseado(cursor.getFloat(cursor.getColumnIndex(COLUMN_STUDENT_PROM_ACUM_REQUERIDO)));
			s.setCredCursados(cursor.getInt(cursor.getColumnIndex(COLUMN_STUDENT_CREDITOS_CURSADOS)));						
			return s;
		}else{
			return null;
		}
	
	}
	public Semester getSemester(long id) {
		Cursor cursor = getWritableDatabase().rawQuery(
				"SELECT * FROM " + TABLE_SEMESTER + " WHERE " + 
		COLUMN_SEMESTER_STUDENT_ID + " = " + id + "", null);
		if (cursor.moveToFirst()){
			Semester s = new Semester();
			s.setID(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
			s.setNombre(cursor.getString(cursor.getColumnIndex(COLUMN_SEMESTER_NAME)));
			s.setCreditos(getCreditosSemestre(s.getID()));
			s.setEstado(cursor.getString(cursor.getColumnIndex(COLUMN_SEMESTER_ESTADO)));
			s.setPromRequerido(cursor.getFloat(cursor.getColumnIndex(COLUMN_SEMESTER_PROM_REQUERIDO)));
			s.setPromReal(cursor.getFloat(cursor.getColumnIndex(COLUMN_SEMESTER_PROM_REAL)));
			s.setPromSimulado(cursor.getFloat(cursor.getColumnIndex(COLUMN_SEMESTER_PROM_SIMULADO)));
			s.setStudentId(cursor.getLong(cursor.getColumnIndex(COLUMN_SEMESTER_STUDENT_ID)));
			
		
			return s;
		} else{
			return null;
		}
		
		
	}
	public ArrayList<Asignatura> getSubjects(long id) {
		ArrayList<Asignatura> asignaturas = new ArrayList<Asignatura>();
		// TODO Auto-generated method stub
		Cursor cursor = getWritableDatabase().rawQuery(
				"SELECT * FROM " + TABLE_SUBJECTS + " WHERE " + 
		COLUMN_SUBJECT_SEMESTER_ID + " = " + id + "", null);
		
		while(cursor.moveToNext()){
			Asignatura s = new Asignatura();
			//s.setID(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
			//s.setNombre(cursor.getString(cursor.getColumnIndex(COLUMN_SEMESTER_NAME)));
			//s.setCreditos(cursor.getInt(cursor.getColumnIndex(COLUMN_SEMESTER_CREDITOS)));
			//s.setEstado(cursor.getString(cursor.getColumnIndex(COLUMN_SEMESTER_ESTADO)));
			//s.setPromRequerido(cursor.getFloat(cursor.getColumnIndex(COLUMN_SEMESTER_PROM_REQUERIDO)));
			//s.setPromReal(cursor.getFloat(cursor.getColumnIndex(COLUMN_SEMESTER_PROM_REAL)));
			//s.setStudentId(cursor.getLong(cursor.getColumnIndex(COLUMN_SEMESTER_STUDENT_ID)));
			asignaturas.add(s);
		}
		if(asignaturas.isEmpty()){
			return null;
		}else{
			return asignaturas;
		}
		
		
	}
	public String[] getSubjectsNames(long id) {
		Cursor cursor = getWritableDatabase().rawQuery(
				"SELECT "+COLUMN_SUBJECT_NAME+" FROM " + TABLE_SUBJECTS + " WHERE " + 
		COLUMN_SUBJECT_SEMESTER_ID + " = " + id + "", null);
		if(cursor.getCount()>0){
		    String[] nombres = new String[cursor.getCount()];
		    int i = 0;
			while(cursor.moveToNext()){
				nombres[i]=cursor.getString(0);
				i = i+1;
			}
			return nombres;
		}else{
			return null;
		}
		
		
		
	}
	public Asignatura getSubjectByName(long semester_id, String name) {
		Cursor cursor = getWritableDatabase().rawQuery(
			"SELECT * FROM " + TABLE_SUBJECTS + " WHERE " + 
			COLUMN_SUBJECT_SEMESTER_ID + " = " + semester_id + 
			" AND " +COLUMN_SUBJECT_NAME + " = '" + name +"'" , null);
		Asignatura s;
		if(cursor.moveToFirst()){
			s = new Asignatura();
			s.setID(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
			s.setNombre(cursor.getString(cursor.getColumnIndex(COLUMN_SUBJECT_NAME)));
			s.setCreditos(cursor.getString(cursor.getColumnIndex(COLUMN_SUBJECT_CREDITOS)));
			s.setEstado(cursor.getString(cursor.getColumnIndex(COLUMN_SUBJECT_ESTADO)));
			s.setNotaRequerida(cursor.getString(cursor.getColumnIndex(COLUMN_SUBJECT_NOTA_REQUERIDA)));
			s.setNotaReal(cursor.getString(cursor.getColumnIndex(COLUMN_SUBJECT_NOTA_REAL)));
			s.setNota_simulada(cursor.getString(cursor.getColumnIndex(COLUMN_SUBJECT_NOTA_SIMULADA)));
			s.setSemestreID(cursor.getLong(cursor.getColumnIndex(COLUMN_SUBJECT_SEMESTER_ID)));
			
			return s;
		}else{
			return null;
		}
		
	}
	public Evaluation getEvaluationByName(long subject_id, String name) {
		Cursor cursor = getWritableDatabase().rawQuery(
			"SELECT * FROM " + TABLE_EVALUATIONS + " WHERE " + 
			COLUMN_EVALUATION_SUBJECT_ID + " = " + subject_id + 
			" AND " +COLUMN_EVALUATION_NAME + " = '" + name+"'" , null);
		Evaluation s;
		if(cursor.moveToFirst()){
			s = new Evaluation();
			s.setID(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
			s.setNombre(cursor.getString(cursor.getColumnIndex(COLUMN_EVALUATION_NAME)));
			s.setPorcentaje(cursor.getInt(cursor.getColumnIndex(COLUMN_EVALUATION_PORCENTAJE)));
			s.setEstado(cursor.getString(cursor.getColumnIndex(COLUMN_EVALUATION_ESTADO)));
			s.setNota_Requerida(cursor.getFloat(cursor.getColumnIndex(COLUMN_EVALUATION_NOTA_REQUERIDA)));
			s.setNota_real(cursor.getFloat(cursor.getColumnIndex(COLUMN_EVALUATION_NOTA_REAL)));
			s.setNota_simulada(cursor.getFloat(cursor.getColumnIndex(COLUMN_EVALUATION_NOTA_SIMULADA)));
			s.setAsignaturaID(cursor.getLong(cursor.getColumnIndex(COLUMN_EVALUATION_SUBJECT_ID)));
			
			return s;
		}else{
			return null;
		}
		
	}
	public String[] getEvaluacionesNames(long asignatura_id) {
		Cursor cursor = getWritableDatabase().rawQuery(
				"SELECT "+COLUMN_EVALUATION_NAME+" FROM " + TABLE_EVALUATIONS + " WHERE " + 
		COLUMN_EVALUATION_SUBJECT_ID + " = " + asignatura_id + "", null);
		if(cursor.getCount()>0){
		    String[] nombres = new String[cursor.getCount()];
		    int i = 0;
			while(cursor.moveToNext()){
				nombres[i]=cursor.getString(0);
				i = i+1;
			}
			return nombres;
		}else{
			return null;
		}
		
	}
	public int getCreditosSemestre(long id) {
		Cursor cursor = getWritableDatabase().rawQuery(
				"SELECT "+COLUMN_SUBJECT_CREDITOS+" FROM " + TABLE_SUBJECTS + " WHERE " + 
		        COLUMN_SUBJECT_SEMESTER_ID + " = " + id + "", null);
		if(cursor.getCount()>0){
		    int creditos = 0;
			while(cursor.moveToNext()){
				creditos = creditos + cursor.getInt(0);

			}
			return creditos;
		}else{
			return 0;
		}
		
	}
	SimulatorHelper sHelper;
	float prom_obt;
	public String setPromDeseadoAcumulado(Student s, Semester sem,float promDeseado) {
		Evaluation[] evaluaciones = null;
		Asignatura[] asignaturas;
		SQLiteDatabase db = this.getWritableDatabase();
	    sHelper = new SimulatorHelper();
		ContentValues data=new ContentValues();
		//promedio obtenido en el semestre
		
		float semProm = sHelper.getPromRequeridoSemestral(s.getCredCursados(), sem.getCreditos(), promDeseado, s.getPromAcum());
		
		if(semProm <=5 && semProm>0){

			s.setPromAcumDeseado(promDeseado);
			sem.setPromSimulado(semProm);
		    asignaturas = getAsignaturas(sem.getID());
			 //TODO:actualizar el promedio necesitado en las asignaturas}
			prom_obt= sHelper.getPromObtenidoSemestral(asignaturas,sem.getCreditos());
			//.setPromSimulado(prom_obt);
		    float puntosRequeridos = sem.getPromSimulado()*sem.getCreditos(); //Requeridos par aucmplir con el acum deseado
			float puntosObtenidos = sHelper.getPuntosObtenidosAsignatura(asignaturas,sem.getPromSimulado());//obtenidos con la actual config de notas
			sem.setDiferencia(puntosRequeridos-puntosObtenidos);
			String out = setNotaRequeridaAsignaturas(s,sem, asignaturas);
			if(out == "OK"){
			//setNotaRequeridaAsignaturas(s, sem);
				
			 //Si todo va bien	
			//Guardar prom deseado estudiante
			 data.put(COLUMN_STUDENT_PROM_ACUM_REQUERIDO,promDeseado);
			 db.update(TABLE_STUDENT, data,COLUMN_ID + " = " + s.getID(), null);
			 //guardar semestre
			 //TODO: verificar si el simestre esta finalizado
			 sem.setPromReal(prom_obt);
			 updatePromRequeridoSemestre(sem,semProm);
			 
			 //actualizar asignaturas:
			 updatePromRequeridoAsignaturas(asignaturas);
			 //actualizar evaluaciones:
			 updatePromRequeridoEvaluaciones(evaluaciones);
			 
		     
			 return "OK";
			}else{
				return out;
			}
		
		}else{
			if(semProm == Float.POSITIVE_INFINITY){
				//esto pasaria si no se han ingresado asignaturas
				return "Ingresa por lo menos una antes de simular";
			}else{
				return "Ese promedio no es alcanzable";
			}
		}
		
	}
	public void updatePromRequeridoSemestre(Semester sem, float prom) {
		SQLiteDatabase db = this.getWritableDatabase();
		sHelper = new SimulatorHelper();
		 ContentValues data=new ContentValues();
		 data = new ContentValues();
		 data.put(COLUMN_SEMESTER_PROM_SIMULADO,sem.getPromReal());
		 data.put(COLUMN_SEMESTER_PROM_REAL,sem.getPromReal());
		 data.put(COLUMN_SEMESTER_PROM_REQUERIDO,prom);
		 db.update(TABLE_SEMESTER, data,COLUMN_ID + " = " + sem.getID(), null);
		 sem.setPromRequerido(prom);
		
	}
	public void  updatePromRequeridoAsignaturas(Asignatura[] asignaturas){
		SQLiteDatabase db = this.getWritableDatabase();
		for (Asignatura asignatura : asignaturas) {
			 ContentValues data=new ContentValues();
			 data = new ContentValues();
			 if(asignatura.getEstado().equals("Finalizado")){
				 data.put(COLUMN_SUBJECT_NOTA_REAL,asignatura.getNotaRequerida());
			 }
			 data.put(COLUMN_SUBJECT_NOTA_REQUERIDA,asignatura.getNotaRequerida());
			 db.update(TABLE_SUBJECTS, data,COLUMN_ID + " = " + asignatura.getID(), null);
			 updatePromRequeridoEvaluaciones(asignatura.getEvaluaciones());
		}

		
	}
	public void  updateNotaSimuladaAsignatura(Asignatura asignatura){
		SQLiteDatabase db = this.getWritableDatabase();
	
			 ContentValues data=new ContentValues();
			 data = new ContentValues();
			 data.put(COLUMN_SUBJECT_NOTA_SIMULADA,asignatura.getNotaSimulada());
			 db.update(TABLE_SUBJECTS, data,COLUMN_ID + " = " + asignatura.getID(), null);
			 //updatePromRequeridoEvaluaciones(asignatura.getEvaluaciones());
		

		
	}


	public String setNotaRequeridaAsignaturas(Student s, Semester sem, Asignatura[] asignaturas) {
		//Se compara la diferencia de puntos del semestre normal, y la nota simulada 
		//nota que debe tener cada asignatura no bloqueada
		String estado = "";
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm =  sHelper.getNotaAsignaturasSimuladas(asignaturas,sem.getPromSimulado(),sem.getDiferencia() ,sem.getCreditos()); 
	    if(hm.get("mensaje").equals("OK")){
	    	asignaturas = (Asignatura[]) hm.get("asignaturas");
			if(asignaturas != null){
		    	//una ves verificado y obtenido cuanto se debe subir cada asignatura a verificar si las evaluaciones
		    	//permiten esa nota
		    	for (Asignatura asignatura : asignaturas) {
					Evaluation[] evals = getEvaluations(asignatura.getID());
					if(!asignatura.getEstado().equals("Finalizado")){
						asignatura.setEvaluaciones(evals);
						if(evals!= null){
							sHelper.distribuirPromedioAEvaluaciones(evals, asignatura.getNotaRequerida());
					    	float porcentajeRequerido = asignatura.getNotaRequerida();
					    	float porcentajeObtenido = sHelper.getPorcentajeObtenidoEvaluacion(evals);
					    	// if(porcentajeObtenido<porcentajeRequerido){
					    		 //nota que debe tener cada evaluacion de la asignatura
					    		 evals =  sHelper.getNotaEvaluacionesSimuladas(evals, porcentajeRequerido - porcentajeObtenido); 
					    		 if(evals != null){
					    			 hm.put("mensaje", "OK");
					    			 return  hm.get("mensaje").toString();
					    			 
					    		 }else{
					    			 hm.put("mensaje", "Hay notas de evaluaciones que querarian por encima de 5!");
					    			 return  hm.get("mensaje").toString();
					    		 }
					    	 }
						//}
					}
				}
	
		    	return  hm.get("mensaje").toString();
		    	
		    }else{
		    	return hm.get("mensaje").toString(); 
		    }
	
		 

	    }else{
	    	return hm.get("mensaje").toString();
	    }
	   
		
	}
	public Asignatura[] getAsignaturas(long sem_id){
		 String[] sub =  getSubjectsNames(sem_id);
		 Asignatura[] a = new Asignatura[sub.length];
		 int i = 0;
		 for (String nombre : sub) {
			 a[i] = getSubjectByName(sem_id, nombre);
		     i++;
		}
		 return a;
	}
	public Evaluation[] getEvaluations(long asig_id){
		 String[] sub =  getEvaluacionesNames(asig_id);
		 if(sub!=null){
			 Evaluation[] a = new Evaluation[sub.length];
			 int i = 0;
			 for (String nombre : sub) {
				 a[i] = getEvaluationByName(asig_id, nombre);
			     i++;
			}
			 return a;
		 }else{
			 return null;
		 }
		 
		
	}
	public List<HashMap<String,String>> getEvaluationsItems(long asignatura_id){
		List<HashMap<String,String>>   aList = new ArrayList<HashMap<String,String>>();
		  HashMap<String, String> hm = new HashMap<String,String>();
		  Cursor cursor = getWritableDatabase().rawQuery(
			"SELECT "+COLUMN_EVALUATION_NAME+", "+
		    COLUMN_EVALUATION_NOTA_REQUERIDA +", " +
		    COLUMN_EVALUATION_PORCENTAJE +", " +
		    COLUMN_EVALUATION_ESTADO + ", " +
		    COLUMN_ID+" " + " FROM " + TABLE_EVALUATIONS + " WHERE " + 
			COLUMN_EVALUATION_SUBJECT_ID + " = " + asignatura_id + "", null);
			if(cursor.getCount()>0){
				hm = new HashMap<String,String>();
			    int i = 0;
				while(cursor.moveToNext()){
					 hm = new HashMap<String,String>();
					    hm.put("nombre", "" + cursor.getString(0));
				        hm.put("nota", "Prom. Requerido: " + cursor.getString(1));
				        hm.put("porcentaje", "Porcentaje: " + cursor.getString(2) + "%");
				       String a = cursor.getString(3);
				       if(a != null){
					        if(a.equals("Finalizado")){
					        	hm.put("img", ""+R.drawable.finalizado);
					        	hm.put("estado", "Estado: Finalizado!");
					        }else{
					        	hm.put("img", "" + R.drawable.enprogreso);
					        	hm.put("estado", "Estado: En Progreso!");
					        }
				       }else{
				    	   hm.put("img", "" + R.drawable.enprogreso);
				    	   hm.put("estado", "" + "Estado: En Progreso!");
				       }
				       hm.put("id_eval", "" + cursor.getString(4));
				    aList.add(hm);
					i = i+1;
				}
				return aList;
			}else{
				return null;
			}

	}
	public void setPromDeseadoRequeridoEvaluaciones(String stud_id, String prom) {
		SQLiteDatabase db = this.getWritableDatabase();
		 ContentValues data=new ContentValues();
		 data.put(COLUMN_STUDENT_PROM_ACUM_REQUERIDO,prom);
		 db.update(TABLE_STUDENT, data,COLUMN_ID + " = " + stud_id, null);
		 
		 //TODO: update requerido semeste y asignaturas en estado libre
		 
		 
		
	}
	public boolean isPorcentajeLleno(int newporcent, long subject_id, int restar) {
		Cursor cursor = getWritableDatabase().rawQuery(
				"SELECT "+COLUMN_EVALUATION_PORCENTAJE+" FROM " + TABLE_EVALUATIONS + " WHERE " + 
		        COLUMN_EVALUATION_SUBJECT_ID + " = " + subject_id + "", null);
		if(cursor.getCount()>0){
		    int porcent = 0;
			while(cursor.moveToNext()){
				porcent = porcent + cursor.getInt(0);
			}
			if(restar!=-1){
				porcent = porcent - restar;
			}
			int total = (porcent+newporcent);
			if(total>100){
				return true;
			}else{
				return false;
			}
			
		}else{
			return false;
		}
		
	}
	public int  getPorcentajeIngresado(long id) {
		// TODO Auto-generated method stub
		Cursor cursor = getWritableDatabase().rawQuery(
				"SELECT SUM("+COLUMN_EVALUATION_PORCENTAJE+") FROM " + TABLE_EVALUATIONS + " WHERE " + 
		        COLUMN_EVALUATION_SUBJECT_ID + " = " + id+ "", null);	    
			if(cursor.moveToFirst())
			{
				int porcent = cursor.getInt(0);
				return porcent; 
			}else{
				return 0;
			}
	
	}
	public String finalizarAsignatura(Asignatura asig) {
		//Verificar que se hayan ingresado todos los porcenajes
		Asignatura backUpAsignatura = asig;
		
		String mensaje;
		long id =asig.getID();
		int porcentaje = getPorcentajeIngresado(id);
		if(porcentaje == 100){
			//Actualizar estado de cada evaluacion
		    Evaluation[] evals = getEvaluations(id);
		    Evaluation[] backUpEvaluation = evals;
		    float nr;
		    float pf = 0;
		    for (Evaluation eval: evals) {
		    	//Calcular la nota final que se obtuvo
		    	if(true){
		    		nr = eval.getNota_requerida();
		    		eval.setNota_real(nr);
		    		eval.setEstado("Finalizado");
		    		pf = pf + nr*((float)eval.getPorcentaje()/100);//promedio final de la asignatura
		    		
		    	}
			}
		    
		    if(pf<asig.getNotaRequerida()){
		    	//Mostrar un mensaje de que no se logro lo esperado
		    }
	        //actualizar asignatura con nuevo promedio y estado
	    	asig.setEstado("Finalizado");
	    	for (Evaluation evaluation : evals) {
				evaluation.setEstado("Finalizado");
			}
	    	asig.setNota_simulada(pf+"");
	    	asig.setNotaReal(pf+"");
	    	asig.setNotaRequerida(pf+"");
	    	if(updateAsignatura(asig, evals, true) == "OK"){
	    		return "Asignatura Finalizada!";
	    	}else{
	    		mensaje = updateAsignatura(backUpAsignatura, backUpEvaluation, false);
	    		mensaje = "No se Guardaron lo Cambios";
	    	}
		    
		  
		}else{
			return "Debes Ingresar Todas las Evaluaciones para Poder FInalizar La Asignatura";
		}
		return "";
		
	}
	private String updateAsignatura(Asignatura asig, Evaluation[] evals, boolean recal) {
		String mensaje = "OK";
		SQLiteDatabase db = this.getWritableDatabase();
		 ContentValues data=new ContentValues();
		 data.put(COLUMN_SUBJECT_NOTA_REQUERIDA,asig.getNotaRequerida());
		 data.put(COLUMN_SUBJECT_NOTA_SIMULADA,asig.getNotaSimulada());
		 data.put(COLUMN_SUBJECT_NOTA_REAL,asig.getNotaReal());
		 data.put(COLUMN_SUBJECT_ESTADO,asig.getEstado());
		 db.update(TABLE_SUBJECTS, data,COLUMN_ID + " = " + asig.getID(), null);
		 updateEvaluaciones(evals, recal);
		 if(recal){
			 Student s = getStudent();
			 Semester sem = getSemester(s.getID());
			 mensaje = setPromDeseadoAcumulado(s, sem, s.getPromAcumDeseado());
		 }
		return mensaje;
	}
	public boolean   updateEvaluaciones(Evaluation[] evals, boolean recal){
		SQLiteDatabase db = this.getWritableDatabase();
		if(evals != null){
			for (Evaluation eval: evals) {
				 ContentValues data=new ContentValues();
				 data = new ContentValues();
				 data.put(COLUMN_EVALUATION_NOTA_REAL,eval.getNota_real());
				 data.put(COLUMN_EVALUATION_NOTA_REQUERIDA,eval.getNota_requerida());
				 data.put(COLUMN_EVALUATION_NOTA_SIMULADA,eval.getNota_simulada());
				 data.put(COLUMN_EVALUATION_ESTADO,eval.getEstado());
				 data.put(COLUMN_EVALUATION_NAME,eval.getNombre().toLowerCase());
				 data.put(COLUMN_EVALUATION_PORCENTAJE,eval.getPorcentaje());
				 db.update(TABLE_EVALUATIONS, data,COLUMN_ID + " = " + eval.getID(), null);
			}
		}else{
			return false;
		}
		return true;
	}
	public boolean   updateEvaluacionesByName(String nomEval, Evaluation eval, long asig_id){
		SQLiteDatabase db = this.getWritableDatabase();

			     
				 Evaluation ev = getEvaluationByName(asig_id, nomEval);
				 ContentValues data=new ContentValues();
				 data = new ContentValues();
				 if(eval.getEstado().equals("Finalizado")){
					 data.put(COLUMN_EVALUATION_NOTA_REAL,eval.getNota_requerida());				 
				 }else{
					 data.put(COLUMN_EVALUATION_NOTA_REAL,eval.getNota_real());
				 }
				
				 data.put(COLUMN_EVALUATION_NOTA_REQUERIDA,eval.getNota_requerida());
				 data.put(COLUMN_EVALUATION_NOTA_SIMULADA,eval.getNota_simulada());
				 data.put(COLUMN_EVALUATION_ESTADO,eval.getEstado());
				 data.put(COLUMN_EVALUATION_NAME,eval.getNombre().toLowerCase());
				 data.put(COLUMN_EVALUATION_PORCENTAJE,eval.getPorcentaje());
				 db.update(TABLE_EVALUATIONS, data,COLUMN_ID + " = " + ev.getID(), null);

		return true;
	}
	public boolean   updateEvaluacionesByID(Evaluation eval){
		SQLiteDatabase db = this.getWritableDatabase();

			     
		
				 ContentValues data=new ContentValues();
				 data = new ContentValues();
				 if(eval.getEstado().equals("Finalizado")){
					 data.put(COLUMN_EVALUATION_NOTA_REAL,eval.getNota_requerida());
				 }else{
					 data.put(COLUMN_EVALUATION_NOTA_REAL,eval.getNota_real());
				 }
				 data.put(COLUMN_EVALUATION_NOTA_REAL,eval.getNota_real());
				 data.put(COLUMN_EVALUATION_NOTA_REQUERIDA,eval.getNota_requerida());
				 data.put(COLUMN_EVALUATION_NOTA_SIMULADA,eval.getNota_simulada());
				 data.put(COLUMN_EVALUATION_ESTADO,eval.getEstado());
				 data.put(COLUMN_EVALUATION_NAME,eval.getNombre().toLowerCase());
				 data.put(COLUMN_EVALUATION_PORCENTAJE,eval.getPorcentaje());
				 db.update(TABLE_EVALUATIONS, data,COLUMN_ID + " = " + eval.getID(), null);

		return true;
	}
	
	
	
	public void  updatePromRequeridoEvaluaciones(Evaluation[] evals){
		SQLiteDatabase db = this.getWritableDatabase();
		if(evals != null){
		for (Evaluation eval: evals) {
			 ContentValues data=new ContentValues();
			 data = new ContentValues();
			 data.put(COLUMN_EVALUATION_NOTA_REQUERIDA,eval.getNota_requerida());
			 data.put(COLUMN_EVALUATION_ESTADO,"En Progreso");
			 db.update(TABLE_EVALUATIONS, data,COLUMN_ID + " = " + eval.getID(), null);
		}
		}
		
	}
	public List<HashMap<String, Object>> getSubjectItems(long sem_id) {
		List<HashMap<String,Object>>   aList = new ArrayList<HashMap<String,Object>>();
		  HashMap<String, Object> hm = new HashMap<String,Object>();
		  Cursor cursor = getWritableDatabase().rawQuery(
			"SELECT "+COLUMN_SUBJECT_NAME+", "+
		    COLUMN_SUBJECT_NOTA_REQUERIDA +", " +
		    COLUMN_SUBJECT_NOTA_REAL+", " +
		    COLUMN_SUBJECT_CREDITOS+", " +
		    COLUMN_SUBJECT_ESTADO + ", " +
		    COLUMN_ID+" " + " FROM " + TABLE_SUBJECTS+ " WHERE " + 
			COLUMN_SUBJECT_SEMESTER_ID + " = " + sem_id + "", null);
			if(cursor.getCount()>0){
				hm = new HashMap<String,Object>();
			    int i = 0;
				while(cursor.moveToNext()){
					 hm = new HashMap<String,Object>();
					    hm.put("nombre", "" + cursor.getString(0).toUpperCase());
				        hm.put("nota_deseada", "Nota Requerida: " + cursor.getString(1));
				        hm.put("creditos", "Creditos: "+ cursor.getString(3) + "");
				        hm.put("nota_real", "" + cursor.getString(2) );
				       String a = cursor.getString(4);
				       if(a != null){
					        if(a.equals("Finalizado")){
					        	hm.put("img", ""+R.drawable.estado_finalizado);
					        	hm.put("estado", "Estado: Finalizado!");
					        	hm.put("toggle_estado", true);
					        }else{
					        	hm.put("img", "" + R.drawable.estado_progreso);
					        	hm.put("estado", "Estado: En Progreso!");
					        	hm.put("toggle_estado", false);
					        }
				       }else{
				    	   hm.put("img", "" + R.drawable.estado_progreso);
				    	   hm.put("estado", "" + "Estado: En Progreso!");
				    	   hm.put("toggle_estado", false);
				       }
				       hm.put("id_asignatura", "" + cursor.getString(5));
				    aList.add(hm);
					i = i+1;
				}
				return aList;
			}else{
				return null;
			}
	}
	public boolean subjectNameExist(String nombre, String sem_id) {
		Cursor cursor = getWritableDatabase().rawQuery(
				"SELECT "+COLUMN_SUBJECT_NAME+" FROM " + TABLE_SUBJECTS + " WHERE " + 
		         COLUMN_SUBJECT_NAME + " = '" + nombre + "' AND " +
	             COLUMN_SUBJECT_SEMESTER_ID + " = " + sem_id+ "", null);	    
			if(cursor.moveToFirst())
			{
				return true; 
			}else{
				return false;
			}
		
	}
	public Asignatura getSubjectByID(long id_asignatura) {
		Cursor cursor = getWritableDatabase().rawQuery(
				"SELECT * FROM " + TABLE_SUBJECTS + " WHERE " + 
				COLUMN_ID+ " = " +  id_asignatura +"" , null);
			Asignatura s;
			if(cursor.moveToFirst()){
				s = new Asignatura();
				s.setID(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
				s.setNombre(cursor.getString(cursor.getColumnIndex(COLUMN_SUBJECT_NAME)));
				s.setCreditos(cursor.getString(cursor.getColumnIndex(COLUMN_SUBJECT_CREDITOS)));
				s.setEstado(cursor.getString(cursor.getColumnIndex(COLUMN_SUBJECT_ESTADO)));
				s.setNotaRequerida(cursor.getString(cursor.getColumnIndex(COLUMN_SUBJECT_NOTA_REQUERIDA)));
				s.setNotaReal(cursor.getString(cursor.getColumnIndex(COLUMN_SUBJECT_NOTA_REAL)));
				s.setNota_simulada(cursor.getString(cursor.getColumnIndex(COLUMN_SUBJECT_NOTA_SIMULADA)));
				s.setSemestreID(cursor.getLong(cursor.getColumnIndex(COLUMN_SUBJECT_SEMESTER_ID)));
				
				return s;
			}else{
				return null;
			}

	}




}
