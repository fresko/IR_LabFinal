package com.usb.ir.presentacion;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.lucene.benchmark.utils.ExtractReuters;
import org.apache.lucene.document.Document;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.selectoneradio.SelectOneRadio;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.usb.ir.logic.IR_CArray;
import com.usb.ir.logic.IndexFiles;
import com.usb.ir.logic.SearchFiles;
import com.usb.ir.model.DocumentoDTO;

import utilidades.Utility; 
 
@ManagedBean(name="dtPaginatorView")
@ViewScoped
public class PaginatorView implements Serializable {
	
	public static final String DIR_SGM = "C:\\IR_Reuters\\reuters_sgm\\";
    public static final String DIR_TXT = "C:\\IR_Reuters\\reuters_txt\\";
    public static final String DIR_CA = "C:\\IR_Reuters\\IR_CArray\\ca";
    
    private InputText txtPalabras;
    private SelectOneRadio radioRelevante;
     
    private List<DocumentoDTO> docs;
    
    private DocumentoDTO selectedDoc;
    private DocumentoDTO selectedDoc2;
    private List<DocumentoDTO> selectedDocs;
    
    private String relevante = "";
    private boolean valueFrase;
    
    Utility utility = new Utility();
    
    private HorizontalBarChartModel barModel;
    
    private TreeNode root;
 
    @PostConstruct
    public void init() {
    	txtPalabras = new InputText();
        docs = new ArrayList<DocumentoDTO>();
        barModel = new HorizontalBarChartModel();
        root = new DefaultTreeNode(new DocumentoDTO(), null);
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
			addMessage("Extracción exitosa!!");
			//Genera los indices con lucene
	        ind.index(param);
	        addMessage("Indexación exitosa!!");
			
		} catch (IOException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error extrayendo los reuters",  null);
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}
        
        docs = new ArrayList<DocumentoDTO>();
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
		
		if(palabras==null || palabras.equals("")) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Por favor ingrese una palabra o frase",  null);
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}
		
		if(valueFrase) {
			palabras = "\""+palabras+"\"";
		}
		
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
        	doc.setRelevante(utility.obtenerRelevanciaArchivo(document.getPath()));
        	doc.setFecha(utility.obtenerFechaArchivo(document.getPath()));
        	doc.setTitulo(utility.obtenerTituloArchivo(document.getPath()));
        	doc.setContenido(utility.obtenerContenidoArchivo(document.getPath()));
        	docs.add(doc);
        	
        	System.out.println( "Doc" + doc.getTitulo() +" Path :" + doc.getPath());
        }
        
        //Calcular clusters
        
        try {
			createClusters();
		} catch (IOException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error construyendo los clusters",  null);
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}
        
        //calcular indices
        
        createBarModel();
		
	}
	
	public void changeListener(DocumentoDTO doc){
	    
	    String relevanteValue =String.valueOf(radioRelevante.getValue());
	    
	    System.out.println(relevante+" "+relevanteValue);
	    
	    utility.marcarRelevanciaArchivo(doc, relevanteValue);
	    
	}

	public String getRelevante() {
		return relevante;
	}

	public void setRelevante(String relevante) {
		this.relevante = relevante;
	}

	public SelectOneRadio getRadioRelevante() {
		return radioRelevante;
	}

	public void setRadioRelevante(SelectOneRadio radioRelevante) {
		this.radioRelevante = radioRelevante;
	}
	
	public BarChartModel getBarModel() {
        return barModel;
    }
	
	private HorizontalBarChartModel initBarModel() {
		HorizontalBarChartModel model = new HorizontalBarChartModel();
 
        ChartSeries ssb = new ChartSeries();
        ssb.setLabel("SSB");
        ssb.set("Índices", 120);        
 
        ChartSeries ssw = new ChartSeries();
        ssw.setLabel("SSW");
        ssw.set("Índices", 50);
        
        ChartSeries silueta = new ChartSeries();
        silueta.setLabel("Silueta");
        silueta.set("Índices", 80);
 
        model.addSeries(ssb);
        model.addSeries(ssw);
        model.addSeries(silueta);
         
        return model;
    }
	
	private void createBarModel() {
        barModel = initBarModel();
         
        barModel.setTitle("Evaluación");
        barModel.setLegendPosition("ne");
        barModel.setAnimate(true);
         
        Axis xAxis = barModel.getAxis(AxisType.Y);
        //xAxis.setLabel("índices");
         
        Axis yAxis = barModel.getAxis(AxisType.X);
        yAxis.setLabel("Valores");
        yAxis.setMin(0);
        yAxis.setMax(200);
    }
	
	public TreeNode getRoot() {
        return root;
    }
	
	public void createClusters() throws IOException {
		root = new DefaultTreeNode(new DocumentoDTO(), null);
		
		TreeNode cluster0 = new DefaultTreeNode(new DocumentoDTO("Cluster 0", ""), root);
		TreeNode cluster1 = new DefaultTreeNode(new DocumentoDTO("Cluster 1", ""), root);
		TreeNode cluster2 = new DefaultTreeNode(new DocumentoDTO("Cluster 2", ""), root);
		TreeNode cluster3 = new DefaultTreeNode(new DocumentoDTO("Cluster 3", ""), root);
		TreeNode cluster4 = new DefaultTreeNode(new DocumentoDTO("Cluster 4", ""), root);
		
		IR_CArray fileCa = new IR_CArray();
        //List<DocumentoDTO> lstDoc = new ArrayList<DocumentoDTO>();
        
        int index = fileCa.getBestGroup(fileCa.maztrixDist(fileCa.FileToMatrix(DIR_CA, " "),docs));
        char[] vecBest  = fileCa.FileToMatrix(DIR_CA, " ").get(index);
        Arrays.asList(vecBest).forEach(System.out::println);
        
        for (int i = 0; i < docs.size(); i++) {
			char c = vecBest[i];
						
			TreeNode docu;
			
			switch (c) {
				case '0':
					docu = new DefaultTreeNode(docs.get(i), cluster0);
					break;
				case '1':
					docu = new DefaultTreeNode(docs.get(i), cluster1);
					break;
				case '2':
					docu = new DefaultTreeNode(docs.get(i), cluster2);
					break;
				case '3':
					docu = new DefaultTreeNode(docs.get(i), cluster3);
					break;
				case '4':
					docu = new DefaultTreeNode(docs.get(i), cluster4);
					break;
			}
		}
        
        //3 2 3 3 3 0 0 0 1 4
		
	}

	public DocumentoDTO getSelectedDoc2() {
		return selectedDoc2;
	}

	public void setSelectedDoc2(DocumentoDTO selectedDoc2) {
		this.selectedDoc2 = selectedDoc2;
	}

	public boolean isValueFrase() {
		return valueFrase;
	}

	public void setValueFrase(boolean valueFrase) {
		this.valueFrase = valueFrase;
	}
	
	

}