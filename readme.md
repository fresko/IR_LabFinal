# Recuperacion de Información - Busqueda en Reuters

Este laboratorio se enfoca en realizar un sistema de recuperacion de Informacion **SRI**. 
partiendo de archivos reuters .sgm los cuales es necesario extraer el texto en archios .txt
para su posterior indexacion utilizando el **API de Apache Lucene** 
el usuario puede realizar una consulta por medio de una palabra clave 
el sistema debe  listar los txt que continen esta palabra los cuales se pueden seleccionar
y ver su contenido y marcar los relevantes

![Minion](https://github.com/fresko/IR_LabFinal/blob/web/img/lucene.png)

## Desarrollo del Laboratorio 

Desarrollo en Java 8 utilizando API lucene 6.5 y Primefaces

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
   ``` java    
      Path psgm = Paths.get(DIR_SGM);
      Path ptxt = Paths.get(DIR_TXT);
      ExtractReuters extractor  = new ExtractReuters(psgm, ptxt);
                 extractor.extract();  ```                
+ Paso 3:Indexar con lucene
+ Paso 4:Realizar Consulta con Palabra Clave
+ Paso 5:Realizar consulta con lucene y Listar los archivos
+ Paso 6:Seleccionar y Mostrar contenido de archivo txt  
+ Paso 7:Realizar marca si es relevantes  
 
# Presentación web

Frontend

![lucene1](https://github.com/fresko/IR_LabFinal/raw/web/img/1.png)

![lucene2](https://github.com/fresko/IR_LabFinal/raw/web/img/2.png)

![lucene3](https://github.com/fresko/IR_LabFinal/raw/web/img/3.png)

![lucene4](https://github.com/fresko/IR_LabFinal/raw/web/img/4.png)

![lucene5](https://github.com/fresko/IR_LabFinal/raw/web/img/5.png)

![lucene6](https://github.com/fresko/IR_LabFinal/raw/web/img/6.jpeg)
