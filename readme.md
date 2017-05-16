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
   ``` xml
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
+ Paso 1:Leer Archivos reuters .gsm
+ Paso 2:Extraer contenido a Archivos Textos .txt 


