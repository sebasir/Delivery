package despacho.excepciones;

/**
 * Clase de excepci�n de direcci�n inv�lida
 * 
 * @author Andr�s Motavita
 *
 */
public class DireccionInvalidaExcepcion extends Exception {

	/**
	 * Serializado
	 */
	private static final long serialVersionUID = -7791281748807382535L;

	/**
	 * Constructor
	 * 
	 * @param mensaje
	 */
	public DireccionInvalidaExcepcion(String mensaje) {
		super(mensaje);
	}

}
