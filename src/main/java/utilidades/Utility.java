package utilidades;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Utility {
	
	   public String obtenerContenidoArchivo(String ruta) {
		   
	      File archivo = null;
	      FileReader fr = null;
	      BufferedReader br = null;
	      String contenido = "";
	      
	      try {
	         
	         archivo = new File (ruta);
	         fr = new FileReader (archivo);
	         br = new BufferedReader(fr);
	         
	         String linea;
	         while((linea=br.readLine())!=null)
	        	 contenido += linea;
	         
	         return contenido;
	      }
	      catch(Exception e){
	         e.printStackTrace();
	         
	         return contenido;
	      }
	      finally{	         
	         try{                    
	            if( null != fr ){   
	               fr.close();     
	            }                  
	         }
	         catch (Exception e2){ 
	            e2.printStackTrace();
	         }         
	         
	      }
	   }
	   
	   public String preprocesar(String palabra) {
		   
		   palabra = palabra.toLowerCase();
		   
		   palabra = palabra.replaceAll("á", "a");
		   palabra = palabra.replaceAll("é", "e");
		   palabra = palabra.replaceAll("í", "i");
		   palabra = palabra.replaceAll("ó", "o");
		   palabra = palabra.replaceAll("ú", "u");
		   
		   palabra = palabra.replaceAll(",", "");
		   
		   return palabra;
		   
	   }
	  
	}