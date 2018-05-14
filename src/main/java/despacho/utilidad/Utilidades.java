package despacho.utilidad;

import despacho.excepciones.DireccionInvalidaExcepcion;
import despacho.excepciones.OperacionInvalidaExcepcion;

/**
 * Clase de utilidades utilizadas por la aplicaci�n
 * 
 * @author Andr�s Motavita
 *
 */
public class Utilidades {
	/**
	 * Constante prefijo de nombre de archivo de entrada.
	 */
	public static final String NOMBRE_ARCHIVO_ENTRADA = "in";
	/**
	 * Constante prefijo de nombre de archivo de salida.
	 */
	public static final String NOMBRE_ARCHIVO_SALIDA = "out";
	/**
	 * Constante extensi�n de archivos
	 */
	public static final String EXTENSION_ARCHIVO = "txt";
	/**
	 * Constante paquetes que puede cargar un drone
	 */
	public static final int PAQUETES_X_DRONE = 3;
	/**
	 * Constante posici�n coordenada X del drone
	 */
	public static final int POSICION_INICIAL_X = 0;
	/**
	 * Constante posici�n coordenada Y del drone
	 */
	public static final int POSICION_INICIAL_Y = 0;
	/**
	 * Constante orientaci�n del drone
	 */
	public static final Orientacion ORIENTACION_INICIAL = Orientacion.NORTE;
	/**
	 * Constante cuadras a la redonda que puede entregar el drone
	 */
	public static final int RANGO_LIMITE_ENTREGA = 10;
	/**
	 * Constante mensaje de cabecera de salida en archivo
	 */
	public static final String CABECERA_SALIDA = "== Reporte de entregas ==";
	/**
	 * Constante mensaje de fuera de rango.
	 */
	public static final String LIMITE_ENTREGA = "Fuera de rango";
	/**
	 * Constante mensaje de operaci�n inv�lida.
	 */
	public static final String OPERACION_INVALIDA = "Operacion Inv�lida";
	/**
	 * Constante mensaje de direcci�n inv�lida.
	 */
	public static final String DIRECCION_INVALIDA = "Direcci�n Inv�lida";

	/**
	 * Orientaciones posibles que toma el drone
	 * 
	 * @author Andr�s Motavita
	 *
	 */
	public enum Orientacion {
		NORTE("Norte"), ORIENTE("Oriente"), SUR("Sur"), OCCIDENTE("Occidente");
		private String o;

		private Orientacion(String o) {
			this.o = o;
		}

		public String getValue() {
			return this.o;
		}
	}

	/**
	 * Operaciones posibles que el drone acepta
	 * 
	 * @author Andr�s Motavita
	 *
	 */
	public enum Operacion {
		IZQUIERDA('I'), DERECHA('D'), AVANZAR('A');
		private char o;

		private Operacion(char o) {
			this.o = o;
		}

		public char getValue() {
			return this.o;
		}
	}

	/**
	 * M�todo que dada una orientaci�n actual y una direcci�n, entrega una nueva
	 * orientaci�n
	 * 
	 * @param actual
	 * @param direccion
	 * @return Orientaci�n nueva
	 * @throws DireccionInvalidaExcepcion
	 */
	public static Orientacion orientacionNueva(Orientacion actual, Operacion direccion)
			throws DireccionInvalidaExcepcion {
		Orientacion[] array = Orientacion.values();
		int ordinal = actual.ordinal();
		switch (direccion) {
		case IZQUIERDA:
			ordinal--;
			break;
		case DERECHA:
			ordinal++;
			break;
		default:
			throw new DireccionInvalidaExcepcion(DIRECCION_INVALIDA);
		}
		if (ordinal < 0)
			ordinal += 4;
		else if (ordinal > 3)
			ordinal -= 4;
		return array[ordinal];
	}

	/**
	 * Evalua si una operaci�n es aceptada para el drone
	 * 
	 * @param operacion
	 * @return Operacion en caso de ser v�lida.
	 * @throws OperacionInvalidaExcepcion
	 */
	public static Operacion getOperacion(char operacion) throws OperacionInvalidaExcepcion {
		for (Operacion o : Operacion.values())
			if (o.getValue() == operacion)
				return o;
		throw new OperacionInvalidaExcepcion(OPERACION_INVALIDA);
	}
}
