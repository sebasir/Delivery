package despacho.excepciones;

/**
 * Excepcion de operaci�n invalida
 * 
 * @author Andr�s Motavita
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
