package com.usb.ir.presentacion;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.lucene.benchmark.utils.ExtractReuters;
import org.apache.lucene.document.Document;
import org.primefaces.component.inputtext.InputText;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.usb.ir.logic.IndexFiles;
import com.usb.ir.logic.SearchFiles;
import com.usb.ir.model.DocumentoDTO;

import utilidades.Utility; 
 
@ManagedBean(name="dtPaginatorView")
@ViewScoped
public class PaginatorView implements Serializable {
	
	public static final String DIR_SGM = "C:\\IR_Reuters\\reuters_sgm\\";
    public static final String DIR_TXT = "C:\\IR_Reuters\\reuters_txt\\";
    
    private InputText txtPalabras;
     
    private List<DocumentoDTO> docs;
    
    private DocumentoDTO selectedDoc;
    private List<DocumentoDTO> selectedDocs;
    
    private String relevante = "";
    
    Utility utility = new Utility();
 
    @PostConstruct
    public void init() {
    	txtPalabras = new InputText();
        docs = new ArrayList<DocumentoDTO>();
        
    }
     
    public List<DocumentoDTO> getDocs() {
        return docs;
    }
         
    public DocumentoDTO getSelectedDoc() {
        return selectedDoc;
    }
 
    public void setSelectedDoc(DocumentoDTO selectedDoc) {
        this.selectedDoc = selectedDoc;
    }
 
    public List<DocumentoDTO> getSelectedDocs() {
        return selectedDocs;
    }
 
    public void setSelectedCars(List<DocumentoDTO> selectedDocs) {
        this.selectedDocs = selectedDocs;
    }
       
    public void buttonActionIndexar(ActionEvent actionEvent) {
    	
    	Path psgm = Paths.get(DIR_SGM);
        Path ptxt = Paths.get(DIR_TXT);
        IndexFiles ind = new IndexFiles();
        String[] param = {"-docs",DIR_TXT};
        
        ExtractReuters extractor;
		try {
			extractor = new ExtractReuters(psgm, ptxt);
			extractor.extract();
		} catch (IOException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error extrayendo los reuters",  null);
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}
        
		//Genera los indices con lucene
        ind.index(param);
    	
        addMessage("Indexaci�n exitosa!!");
    }
    
    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public InputText getTxtPalabras() {
		return txtPalabras;
	}

	public void setTxtPalabras(InputText txtPalabras) {
		this.txtPalabras = txtPalabras;
	}
	
	public void buttonActionBuscar(ActionEvent actionEvent) {
		
		String palabras = (String) txtPalabras.getValue();
		SearchFiles sf = new SearchFiles();
		
		String[] paramSearch = new String[2];
        List<DocumentoDTO> lstDoc = new ArrayList<>();
		
		paramSearch[0] = "-query";                 
        paramSearch[1] = palabras;
        try {
			lstDoc = sf.search(paramSearch);
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error buscando los documentos",  null);
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}
        
        docs = new ArrayList<DocumentoDTO>();
        
        for (DocumentoDTO document : lstDoc) {
        	
        	DocumentoDTO doc = new DocumentoDTO();
        	doc.setPath(document.getPath());
        	doc.setScore(document.getScore());
        	doc.setTitulo(utility.obtenerTituloArchivo(document.getPath()));
        	doc.setContenido(utility.obtenerContenidoArchivo(document.getPath()));
        	docs.add(doc);
        	
          System.out.println( "Doc" + doc.getTitulo() +" Path :" + doc.getPath());
        }
		
	}
	
	public void changeListener(String ruta){
	    System.out.println(relevante+" "+ruta);
	}

	public String getRelevante() {
		return relevante;
	}

	public void setRelevante(String relevante) {
		this.relevante = relevante;
	}
	
	

}