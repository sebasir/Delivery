package despacho.excepciones;

/**
 * Clase de excepción de dirección inválida
 * 
 * @author Andrés Motavita
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
