/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.usb.ir.main;

import com.usb.ir.index.IndexFiles;
import ir.SearchFiles;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.benchmark.utils.ExtractReuters;
import org.apache.lucene.document.Document;

/**
 *
 * @author SONY
 */
public class Ppal {
     public static final String DIR_SGM = "D:\\IR_Reuters\\reuters_sgm\\";
     public static final String DIR_TXT = "D:\\IR_Reuters\\reuters_txt\\";
    /**
     * @param args the command line arguments
     */ 
    public static void main(String[] args) {
         try {
             Path psgm = Paths.get(DIR_SGM);
             Path ptxt = Paths.get(DIR_TXT);
             IndexFiles ind = new IndexFiles();
             SearchFiles sf = new SearchFiles();
             String[] param = {"-docs",DIR_TXT};
             Scanner in = new Scanner(System.in);
             String[] paramSearch = new String[2];
             List<Document> lstDoc = new ArrayList<>();
                 
                 ExtractReuters extractor  = new ExtractReuters(psgm, ptxt);
                 extractor.extract();
                 //Genera los indices con lucene
                 ind.index(param);
                 //Se consulta los indices generados segun paramentro clave
                 System.out.println( "Palabra clave :");
                 String q = in.nextLine();
                 paramSearch[0] = "-query";                 
                 paramSearch[1] = q;
                 lstDoc = sf.search(paramSearch);
                 
                 for (Document document : lstDoc) {
                   System.out.println( "Doc" + document.get("contents") +" Path :" + document.get("path"));
                 }
         
         } catch (IOException ex) {
                 Logger.getLogger(Ppal.class.getName()).log(Level.SEVERE, null, ex);
         } catch (Exception ex) {
             Logger.getLogger(Ppal.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
}
