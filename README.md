Prueba Técnica
========

Despacho de Corrientazos con Drones elaborada por Andrés Felipe Motavita Medellín


Ejecución del proyecto
---------------

Para ejecutar el proyecto usar:
  
		mvn install -DskipTests
		mvn test -Ddrones=20
  
Esto ejecutará el proyecto mostrando la ejecución de las pruebas unitarias, 
la obtención del log y los archivos de entrada y salida. Donde Ddrones es la cantidad de drones a especificar.

En caso de cambiar la cantidad de pedidos que puede cargar un dron, ir a la clase Utilidades y cambiar el valor de PAQUETES_X_DRONE al tamaño deseado 

Para reemplazar el archivo de entrada, sustituir los archivos in<n>.txt
