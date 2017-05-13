package com.usb.ir.test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.lucene.benchmark.utils.ExtractReuters;
import org.apache.lucene.document.Document;
import org.junit.Test;

import com.usb.ir.logic.IndexFiles;
import com.usb.ir.logic.SearchFiles;

public class TestIndex {
	
	@Test
	public void index() {
		
	    String DIR_TXT = "D:\\IR_Reuters\\reuters_txt\\";
		
        IndexFiles ind = new IndexFiles();        
        String[] param = {"-docs",DIR_TXT};
        
        //Genera los indices con lucene
        ind.index(param);
	}

}
