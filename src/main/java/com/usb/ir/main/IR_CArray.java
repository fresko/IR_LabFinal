/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.usb.ir.ca;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author SONY
 */
public class IR_CArray {

    public static final String DIR_CA = "D:\\IR_CArray\\ca";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            IR_CArray fileCa = new IR_CArray();
            fileCa.FileToMatrix(DIR_CA, " ").forEach(System.out::println);

        } catch (IOException ex) {
            Logger.getLogger(IR_CArray.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Metodo que lee el contenido de un archivo y retorna su contenido en un
     * String
     *
     * @return
     * @throws IOException
     */
    public String readFiletoString(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }

    /**
     * Metodo que lee el contenido de un archivo y retorna su contenido en un
     * Stream
     *
     * @param path
     * @return
     * @throws IOException
     */
    public Stream<String> readFiletoStream(String path) throws IOException {
        return Files.lines(Paths.get(path));
    }

    /**
     * Metodo que Transforma contenido de archivo (en este caso el CArray) a una
     * matriz en memoria
     *
     * @param path
     * @param regex
     * @return
     */
    public List<char[]> FileToMatrix(String path, String regex) throws IOException {
        IR_CArray fileCa = new IR_CArray();
        List<char[]> lstCA = new ArrayList<char[]>();
        //Se lee el archivo y se deja en una lista por cada linea del doc CA
        Stream<String> ca = fileCa.readFiletoStream(DIR_CA);
        List<String> lstPre = ca.collect(Collectors.toList());
        //Se recorre la lista y por cada linea remueven los tab 
        //Se recorre la lista y por cada linea geenera un vector de char[] separado por cada catacteres
        //Se agrega a la lista resultado lstCA el vector de  
        for (String s : lstPre) {
            char[] contentCA = s.replace(" ", "").toCharArray(); // el caracter de separacion eS TAB
            lstCA.add(contentCA);
        }

        return lstCA;

    }

}
