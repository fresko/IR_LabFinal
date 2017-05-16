# Recuperacion de Información - Busqueda en Reuters

Este laboratorio se enfoca en realizar un sistema de recuperacion de Informacion **SRI**. 
partiendo de archivos reuters .sgm los cuales es necesario extraer el texto en archios .txt
para su posterior indexacion utilizando el **API de Apache Lucene** 
el usuario puede realizar una consulta por medio de una palabra clave 
el sistema debe  listar los txt que continen esta palabra los cuales se pueden seleccionar
y ver su contenido y marcar los relevantes

![Minion](https://github.com/fresko/IR_LabFinal/blob/web/img/lucene.png)

## Desarrollo del Laboratorio 

Desarrollo en Java 8 utilizando API lucene 6.5 y Primefaces 6.1 , eclipse Neo 4.6 

El SRI : 

+ Paso 0:Agregar dependencias **Lucene**  
+ Paso 1:Leer Archivos reuters .gsm
+ Paso 2:Extraer contenido a Archivos Textos .txt
+ Paso 3:Indexar con lucene
+ Paso 4:Realizar Consulta con Palabra Clave
+ Paso 5:Realizar consulta con lucene y Listar los archivos
+ Paso 6:Seleccionar y Mostrar contenido de archivo txt  
+ Paso 7:Realizar marca si es relevantes 

## Desarrollo

Se agrega dependencia 

```xml
    //Dependencias Lucene
    <dependencies>
     <dependency>
        <groupId>org.apache.lucene</groupId>
        <artifactId>lucene-core</artifactId>
        <version>6.5.1</version>
     </dependency>
    <!-- https://mvnrepository.com/artifact/org.apache.lucene/lucene-benchmark -->
     <dependency>
        <groupId>org.apache.lucene</groupId>
        <artifactId>lucene-benchmark</artifactId>
        <version>6.5.1</version>
      </dependency>
   </dependencies>
 ```
 ```java
    /**
     * Metodo que calcula el centroide del cluster
     *
     * @param mi cantidad de veces que se repeti el cluster en el grupo ca (fila
     * de ca)
     * @param sumhit sumatorias del peso o score de cluster en el grupo ca (fila
     * de ca)
     * @return
     */
     public static double centroid(int mi, double sumhit) {
        return (mi != 0) ? ((1.0 / mi * 1.0) * sumhit) : 0.0;
    }
    /**
     * Metodo que calcula el la distancia
     *
     * @param centroid
     * @param cluster cluster del ca
     * @return
     */
     public static double dist(double centroid, List<Double> lstScoreSeleted) {
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
        double[] vectScore = getScores(lstDoc);
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
 ```

## Presentacion Web

+ Indexación de documentos (reuters)
+ Búsqueda por palabras claves o frase completa
+ Marcación de documentos relevantes o no relevantes
+ Resultados en tabla paginada (5,10,15,21 resultados)
+ Resultados agrupados en 5 Clusters
+ Gráficas de índices de evaluación (SSB, SSW, Silueta)

![lucene1](https://github.com/fresko/IR_LabFinal/raw/web/img/11.png)

![lucene2](https://github.com/fresko/IR_LabFinal/raw/web/img/22.png)

![lucene3](https://github.com/fresko/IR_LabFinal/raw/web/img/33.png)

![lucene5](https://github.com/fresko/IR_LabFinal/raw/web/img/55.png)

![lucene5](https://github.com/fresko/IR_LabFinal/raw/web/img/66.png)

![lucene4](https://github.com/fresko/IR_LabFinal/raw/web/img/44.png)



