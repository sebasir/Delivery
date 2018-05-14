package despacho.excepciones;

/**
 * Excepcion de operación invalida
 * 
 * @author Andrés Motavita
 *
 */
public class OperacionInvalidaExcepcion extends Exception {

	/**
	 * Serializado
	 */
	private static final long serialVersionUID = 1048479077821063374L;

	/**
	 * Constructor
	 * 
	 * @param mensaje
	 */
	public OperacionInvalidaExcepcion(String mensaje) {
		super(mensaje);
	}

}
