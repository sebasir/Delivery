Prueba T�cnica
========

Despacho de Corrientazos con Drones elaborada por Andr�s Felipe Motavita Medell�n


Ejecuci�n del proyecto
---------------

Para ejecutar el proyecto usar:
  
		mvn install -DskipTests
		mvn test -Ddrones=20
  
Esto ejecutar� el proyecto mostrando la ejecuci�n de las pruebas unitarias, 
la obtenci�n del log y los archivos de entrada y salida. Donde Ddrones es la cantidad de drones a especificar.

En caso de cambiar la cantidad de pedidos que puede cargar un dron, ir a la clase Utilidades y cambiar el valor de PAQUETES_X_DRONE al tama�o deseado 

Para reemplazar el archivo de entrada, sustituir los archivos in<n>.txt
