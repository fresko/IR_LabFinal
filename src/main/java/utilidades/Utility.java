package utilidades;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

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
	
	public void marcarArchivoRelevante(String ruta, String titulo) {
		
		FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter(ruta);
            pw = new PrintWriter(fichero);
            
            //if(titulo.startsWith(prefix))

            for (int i = 0; i < 10; i++)
                pw.println("Linea " + i);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
    }
	   
}