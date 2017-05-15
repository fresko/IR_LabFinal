package utilidades;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import com.usb.ir.model.DocumentoDTO;

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
	
	public String obtenerTituloArchivo(String ruta) {
		
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
		String titulo = "";

		try {

			archivo = new File (ruta);
			fr = new FileReader (archivo);
			br = new BufferedReader(fr);

			String linea;
			int numLinea = 0;
			while((linea=br.readLine())!=null && numLinea < 3) {
				titulo = linea;
				numLinea++;
			}

			return titulo;
		}
		catch(Exception e){
			e.printStackTrace();
			return titulo;
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
	
	public void marcarRelevanciaArchivo(DocumentoDTO doc, String relevancia) {
		
		File archivo = null;
		File archivoNuevo = null;
		
        FileReader fr = null;
		BufferedReader br = null;
		
		BufferedWriter bw = null;
		FileWriter fw = null;
		
        try
        {
        	//copiar archivo
        	archivo = new File(doc.getPath());        	
        	
			fr = new FileReader (archivo);
			br = new BufferedReader(fr);
        	
        	//archivoNuevo = new File(archivo.getParent()+"/copia_"+archivo.getName());
        	fw = new FileWriter(archivo.getParent()+"/copia_"+archivo.getName());
        	
        	bw = new BufferedWriter(fw);     	
                        
            String linea = br.readLine();
            if(linea.startsWith("+") || linea.startsWith("-")) {
            	bw.write(relevancia+linea.substring(1));
            }
            else {
            	bw.write(relevancia+linea);
            }
            
			while((linea=br.readLine())!=null) {
				bw.newLine();
				bw.write(linea);				
			}
			
			if(bw!=null)   
	        	bw.close();
	           
            if (null != br)
                br.close();
           
            if(null != fr ){   
				fr.close();     
			}
			
			if(archivo.exists()) {
				archivo.delete();
			}
            
			archivoNuevo = new File(archivo.getParent()+"/copia_"+archivo.getName());
			archivoNuevo.renameTo(archivo);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
        	if(bw!=null)   
        	   bw.close();
           
           if (null != br)
              br.close();
           
           if( null != fr ){   
				fr.close();     
			}
           
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
    }
	
	public String obtenerFechaArchivo(String ruta) {
		
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
		
		try {

			archivo = new File (ruta);
			fr = new FileReader (archivo);
			br = new BufferedReader(fr);

			String linea = br.readLine();
			return linea;
		}
		catch(Exception e){
			e.printStackTrace();
			return "";
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
	
	public String obtenerRelevanciaArchivo(String ruta) {
		
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
		
		try {

			archivo = new File (ruta);
			fr = new FileReader (archivo);
			br = new BufferedReader(fr);

			String linea = br.readLine();
			
			if(linea.startsWith("+")) {
				return "+";				
            }
			else if (linea.startsWith("-")){
				return "-";
			}
			else return "";
			
		}
		catch(Exception e){
			e.printStackTrace();
			return "";
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
	   
}