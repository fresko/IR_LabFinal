/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.usb.ir.main;

import com.usb.ir.dto.DocumentoDTO;
import java.io.IOException;
import java.lang.reflect.Array;
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
            List<DocumentoDTO> lstDoc = new ArrayList<DocumentoDTO>();

            //se convierte archivo Carray a matrix en memoria
            //fileCa.FileToMatrix(DIR_CA, " ").forEach(System.out::println);
            //se genera la matrix de distancias 
            fileCa.maztrixDist(fileCa.FileToMatrix(DIR_CA, " "), lstDoc).forEach(System.out::println);
            // mejor grupo
            //  int idex = getBestGroup(fileCa.maztrixDist(fileCa.FileToMatrix(DIR_CA, " "),lstDoc));
            //  char[] vecBest  = fileCa.FileToMatrix(DIR_CA, " ").get(idex);

            //System.out.print(vecBest.toString());
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

    /**
     * Metodo que calcula el centroide del cluster
     *
     * @param mi cantidad de veces que se repeti el cluster en el grupo ca (fila
     * de ca)
     * @param sumhit sumatorias del peso o score de cluster en el grupo ca (fila
     * de ca)
     * @return
     */
    static public double centroid(int mi, double sumhit) {
        return (mi != 0) ? ((1.0 / mi * 1.0) * sumhit) : 0.0;
    }

    /**
     * Metodo que calcula el la distancia
     *
     * @param centroid
     * @param cluster cluster del ca
     * @return
     */
    static public double dist(double centroid, List<Double> lstScoreSeleted) {
        double dist = 0.0;
        for (Double score : lstScoreSeleted) {
            dist = dist + Math.sqrt(Math.pow(centroid,2)+ Math.pow(score.doubleValue(),2));
        }
        return dist;
    }

    /**
     * Metodo que genera la matrix de distancias
     *
     * @param lstCa
     * @return
     */
    public List<double[]> maztrixDist(List<char[]> lstCa, List<DocumentoDTO> lstDoc) {
        char[] vectCluster = {'0', '1', '2', '3', '4'}; //es necesario no sacarlo de la lista (para deuda tecnica)
        //double[] vectScore = {20, 30, 40, 50, 10, 15, 16, 17, 31, 40, 11, 12, 13, 14, 22, 23, 24, 25, 19, 32, 33}; //es necesario no sacarlo de los Documentos (para deuda tecnica)
         double[] vectScore = {2, 4, 1}; //es necesario no sacarlo de los Documentos (para deuda tecnica)
        //double[] vectScore = {20, 30, 40, 50, 10, 15, 16, 17, 31, 40}; //es necesario no sacarlo de los Documentos (para deuda tecnica)
        //double[] vectScore = getScores(lstDoc);
        List<double[]> maztrixDist = new ArrayList<double[]>();

        for (int ca = 0; ca < lstCa.size(); ca++) {
            int indexDist = 0;
            double[] vectDist = new double[vectCluster.length];
            for (int i = 0; i < vectCluster.length; i++) {
                List<Double> lstScoreSeleted = new ArrayList<Double>();
                int mi = 0;
                double sumhit = 0.0;                
                for (int j = 0; j < /*lstCa.get(ca).length*/ vectScore.length; j++) {
                    if (vectCluster[i] == lstCa.get(ca)[j]) {
                        mi++;
                        sumhit = sumhit + vectScore[j];
                        lstScoreSeleted.add(Double.valueOf(vectScore[j]));
                    }
                    if (j == vectScore.length - 1) { // es la ultima posicion 
                        vectDist[i] = dist(centroid(mi, sumhit), lstScoreSeleted); //calculos
                       // indexDist++;
                    }
                }
                if (i == vectCluster.length - 1) { // si es el ultimo
                    maztrixDist.add(ca, vectDist);
                }
            }

        }
        return maztrixDist;
    }

    /**
     * Metodo que obtine los score de los documentos
     *
     * @param lstDoc
     * @return
     */
    public double[] getScores(List<DocumentoDTO> lstDoc) {
        double[] vctScore = new double[lstDoc.size()];
        for (int i = 0; i < lstDoc.size(); i++) {
            DocumentoDTO doc = lstDoc.get(i);
            vctScore[i] = Double.valueOf(doc.getScore());
        }

        return vctScore;
    }

    /**
     * Metodo que retorna el indice ca del mejor grupo para documentos
     * relevantes
     *
     * @param lstDist
     * @return
     */
    public static int getBestGroup(List<double[]> lstDist) {
        double sumLow = 0;
        int index = 0;

        for (int i = 0; i < lstDist.size(); i++) {
            double[] dist = lstDist.get(i);
            double sumDist = 0;

            for (int j = 0; j < dist.length; j++) {
                sumDist = sumDist + dist[j];

            }
            if (i == 0) {
                sumLow = sumDist;
            }
            if (sumDist < sumLow) { // se actuliza la suma mas baja o mejor grupo
                sumLow = sumDist;
                index = i;
            }
        }

        return index;
    }

}
