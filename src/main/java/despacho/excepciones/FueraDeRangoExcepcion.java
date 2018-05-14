package despacho.excepciones;

/**
 * Clase de excepcion para fuera de rango
 * 
 * @author Andr�s Motavita
 *
 */
public class FueraDeRangoExcepcion extends Exception {
	/**
	 * Serializado
	 */
	private static final long serialVersionUID = 102038516877176349L;

	/**
	 * Constructor
	 * 
	 * @param mensaje
	 */
	public FueraDeRangoExcepcion(String mensaje) {
		super(mensaje);
	}
}
