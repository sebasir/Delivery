package despacho.modelo;

import java.util.LinkedList;
import java.util.Queue;
import despacho.utilidad.Utilidades.Orientacion;

/**
 * Clase de modelo del drone
 * 
 * @author Andrés Motavita
 *
 */
public class Drone {
	/**
	 * Coordenada X del drone
	 */
	private int X;
	/**
	 * Coordenada Y del drone
	 */
	private int Y;
	/**
	 * Orientación actual del drone
	 */
	private Orientacion orientacionActual;
	/**
	 * Cola de pedidos por entregar
	 */
	private Queue<String> pedidos;

	/**
	 * Constructor del drone para unas corrdenadas y orientación
	 * 
	 * @param x
	 * @param y
	 * @param orientacionActual
	 */
	public Drone(int x, int y, Orientacion orientacionActual) {
		super();
		X = x;
		Y = y;
		this.orientacionActual = orientacionActual;
		this.pedidos = new LinkedList<>();
	}

	/**
	 * 
	 * @return Coordenada X del drone
	 */
	public int getX() {
		return X;
	}

	/**
	 * 
	 * @param x
	 *            Coordenada X nueva del drone
	 */
	public void setX(int x) {
		X = x;
	}

	/**
	 * 
	 * @return Coordenada Y del drone
	 */
	public int getY() {
		return Y;
	}

	/**
	 * 
	 * @param y
	 *            Coordenada Y nueva del drone
	 */
	public void setY(int y) {
		Y = y;
	}

	/**
	 * 
	 * @return Orientación actual del drone
	 */
	public Orientacion getOrientacionActual() {
		return orientacionActual;
	}

	/**
	 * 
	 * @param orientacionActual
	 *            Orientación nueva del drone
	 */
	public void setOrientacionActual(Orientacion orientacionActual) {
		this.orientacionActual = orientacionActual;
	}

	/**
	 * 
	 * @return Cola de pedidos por entregar
	 */
	public Queue<String> getPedidos() {
		return pedidos;
	}

	/**
	 * 
	 * @param pedidos
	 *            Cola de pedidos por entregar
	 */
	public void setPedidos(Queue<String> pedidos) {
		this.pedidos = pedidos;
	}
}
