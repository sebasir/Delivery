package despacho.utilidad;

import despacho.excepciones.DireccionInvalidaExcepcion;
import despacho.excepciones.OperacionInvalidaExcepcion;

/**
 * Clase de utilidades utilizadas por la aplicación
 * 
 * @author Andrés Motavita
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
	 * Constante extensión de archivos
	 */
	public static final String EXTENSION_ARCHIVO = "txt";
	/**
	 * Constante paquetes que puede cargar un drone
	 */
	public static final int PAQUETES_X_DRONE = 3;
	/**
	 * Constante posición coordenada X del drone
	 */
	public static final int POSICION_INICIAL_X = 0;
	/**
	 * Constante posición coordenada Y del drone
	 */
	public static final int POSICION_INICIAL_Y = 0;
	/**
	 * Constante orientación del drone
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
	 * Constante mensaje de operación inválida.
	 */
	public static final String OPERACION_INVALIDA = "Operacion Inválida";
	/**
	 * Constante mensaje de dirección inválida.
	 */
	public static final String DIRECCION_INVALIDA = "Dirección Inválida";

	/**
	 * Orientaciones posibles que toma el drone
	 * 
	 * @author Andrés Motavita
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
	 * @author Andrés Motavita
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
	 * Método que dada una orientación actual y una dirección, entrega una nueva
	 * orientación
	 * 
	 * @param actual
	 * @param direccion
	 * @return Orientación nueva
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
	 * Evalua si una operación es aceptada para el drone
	 * 
	 * @param operacion
	 * @return Operacion en caso de ser válida.
	 * @throws OperacionInvalidaExcepcion
	 */
	public static Operacion getOperacion(char operacion) throws OperacionInvalidaExcepcion {
		for (Operacion o : Operacion.values())
			if (o.getValue() == operacion)
				return o;
		throw new OperacionInvalidaExcepcion(OPERACION_INVALIDA);
	}
}
