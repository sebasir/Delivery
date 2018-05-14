package despacho.controlador;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

import despacho.excepciones.FueraDeRangoExcepcion;
import despacho.modelo.Drone;
import despacho.utilidad.Utilidades;
import despacho.utilidad.Utilidades.Operacion;
import despacho.utilidad.Utilidades.Orientacion;

public class DroneControlador implements Runnable {
	/**
	 * Clase para la administración de escritura y lectura de archivos
	 * 
	 * @author Andrés Motavita
	 *
	 */
	class AdministradorArchivos {
		/**
		 * Objeto de lectura de archivos
		 */
		private BufferedReader leer;
		/***
		 * Objeto de escritura de archivos
		 */
		private BufferedWriter escribir;
		/**
		 * Indicador que está iniciados el lector y escritor de archivos
		 */
		private boolean iniciado;
		/**
		 * Ruta de archivo de entrada
		 */
		private String archivoEntrada;
		/**
		 * Ruta de archivo de salida
		 */
		private String archivoSalida;

		/**
		 * Constructor de administrador de archivos.
		 * 
		 * @param archivoEntrada
		 * @param archivoSalida
		 */
		public AdministradorArchivos(String archivoEntrada, String archivoSalida) {
			this.setArchivoEntrada(archivoEntrada);
			this.setArchivoSalida(archivoSalida);
			this.setIniciado(false);
		}

		/**
		 * 
		 * @return Indicador que está iniciados el lector y escritor de archivos
		 */
		public boolean isIniciado() {
			return iniciado;
		}

		/**
		 * 
		 * @param iniciado
		 *            Indicador que está iniciados el lector y escritor de archivos
		 */
		public void setIniciado(boolean iniciado) {
			this.iniciado = iniciado;
		}

		/**
		 * 
		 * @return Ruta de archivo de entrada
		 */
		public String getArchivoEntrada() {
			return archivoEntrada;
		}

		/**
		 * 
		 * @param archivoEntrada
		 *            Ruta de archivo de entrada
		 */
		public void setArchivoEntrada(String archivoEntrada) {
			this.archivoEntrada = archivoEntrada;
		}

		/**
		 * 
		 * @return Ruta de archivo de salida
		 */
		public String getArchivoSalida() {
			return archivoSalida;
		}

		/**
		 * 
		 * @param archivoSalida
		 *            ruta de archivo de salida
		 */
		public void setArchivoSalida(String archivoSalida) {
			this.archivoSalida = archivoSalida;
		}

		/**
		 * Inicializa los objetos de lectura y escritura de archivos
		 */
		public void inicializar() {
			log.info("Inicialización manejo archivos");
			File aI = new File(this.getArchivoEntrada());
			try {
				if (!this.getArchivoEntrada().isEmpty() && aI.exists()) {
					this.leer = new BufferedReader(new FileReader(getArchivoEntrada()));
				}
				if (!this.getArchivoSalida().isEmpty())
					this.escribir = new BufferedWriter(new FileWriter(getArchivoSalida()));
				if (this.leer != null && this.escribir != null) {
					this.setIniciado(true);
					log.info("Archivos iniciados");
				} else
					log.info("Archivos no iniciados");
			} catch (Exception e) {
				String mensaje = String.format("Error manejando archivos. Error: %s", e.getMessage());
				log.error(mensaje);
			}
		}

		/**
		 * Escribe un resultado en archivo
		 * 
		 * @param X
		 *            Coordenada X de la Posición del drone
		 * @param Y
		 *            Coordenada X de la Posición del drone
		 * @param orientacion
		 *            Orientación actual del drone
		 * @return True si pudo escribir, False en caso contrario
		 */
		public boolean escribirResultado(int X, int Y, String orientacion) {
			String salida = String.format("(%d, %d) dirección %s", X, Y, orientacion);
			return escribir(salida);
		}

		/**
		 * Escribe un mensaje de texto en archivo. En caso de no poder escribir,
		 * registra en bitácora
		 * 
		 * @param mensaje
		 * @return True si pudo escribir, False en caso contrario
		 */
		public boolean escribir(String mensaje) {
			String lg = null;
			try {
				if (isIniciado()) {
					lg = String.format("Escribiendo Mensaje: %s", mensaje);
					log.info(lg);
					escribir.write(mensaje);
					escribir.write("\n");
					escribir.flush();
					lg = String.format("Mensaje escrito: %s", mensaje);
					return true;
				} else
					log.warn("No se encuentra inicializado la escritura");
			} catch (Exception e) {
				lg = String.format("Error escribiendo mensaje: %s. Error: %s", mensaje, e.getMessage());
				log.error(lg);
			}
			return false;
		}

		/**
		 * Lee una línea de archivo de entrada. En caso de no poder registra en
		 * bitácora.
		 * 
		 * @return
		 */
		public String leer() {
			try {
				log.info("Leyendo desde archivo");
				if (isIniciado())
					return leer.readLine();
				else
					log.warn("No se encuentra inicializado el lector");
			} catch (Exception e) {
				String mensaje = String.format("No fue posible leer. Error: %s", e.getMessage());
				log.error(mensaje);
			}
			return null;
		}

		/**
		 * Cierra los archivos de lectura y escritura. En caso de no poder cerrar
		 * registra en bitácora.
		 */
		public void cerrar() {
			log.info("Cerrando objetos de archivo");
			try {
				leer.close();
				escribir.flush();
				escribir.close();
				log.info("Cerrado de objetos de archivos exitoso");
			} catch (Exception e) {
				String mensaje = String.format("No fue posible cerrar los objetos de archivos. Error: %s",
						e.getMessage());
				log.error(mensaje);
			}
		}
	}

	/**
	 * Drone
	 */
	private Drone drone;
	/**
	 * Administrador de archivos de lectura y escritura
	 */
	private AdministradorArchivos adminArchivos;
	/**
	 * Registro en bitácora
	 */
	private final Logger log = Logger.getLogger(DroneControlador.class);;

	/**
	 * Constructor del controlador por defecto. Establece archivo por defecto de
	 * lectura y escritura
	 * 
	 * @throws IOException
	 */
	public DroneControlador() {
		super();
		this.drone = new Drone(Utilidades.POSICION_INICIAL_X, Utilidades.POSICION_INICIAL_Y,
				Utilidades.ORIENTACION_INICIAL);
		String aI = String.format("%s.%s", Utilidades.NOMBRE_ARCHIVO_ENTRADA, Utilidades.EXTENSION_ARCHIVO);
		String aO = String.format("%s.%s", Utilidades.NOMBRE_ARCHIVO_SALIDA, Utilidades.EXTENSION_ARCHIVO);
		this.adminArchivos = new AdministradorArchivos(aI, aO);
	}

	/**
	 * Constructor del controlador de drone con rutas de archivo parametrizables
	 * 
	 * @param in
	 * @param out
	 */
	public DroneControlador(String in, String out) {
		super();
		this.drone = new Drone(Utilidades.POSICION_INICIAL_X, Utilidades.POSICION_INICIAL_Y,
				Utilidades.ORIENTACION_INICIAL);
		this.adminArchivos = new AdministradorArchivos(in, out);
	}

	/**
	 * 
	 * @return Drone
	 */
	public Drone getDrone() {
		return drone;
	}

	/**
	 * 
	 * @return Administrador de Archivos
	 */
	public AdministradorArchivos getAdminArchivos() {
		return adminArchivos;
	}

	/**
	 * comienza el proceso de procesamiento de pedidos desde un archivo de texto. Si
	 * no se puede inicializar los archivos de escritura y lectura, finaliza.
	 */
	public void procesarPedidos() {
		log.info("-- Inicio de procesamiento Pedidos --");
		adminArchivos.inicializar();
		if (!adminArchivos.isIniciado()) {
			log.warn("-- Fin, No puedo procesar --");
			return;
		}
		adminArchivos.escribir(Utilidades.CABECERA_SALIDA);
		String pedido = null;
		while ((pedido = adminArchivos.leer()) != null) {
			gestionarPedidosRecibidos(pedido);
		}
		despachar();
		adminArchivos.cerrar();
		log.info("-- Fin de procesamiento Pedidos --");
	}

	/**
	 * Gestiona un pedido entregado. En caso de llenar la cola, despacha los
	 * pedidos.
	 * 
	 * @param pedido
	 */
	public void gestionarPedidosRecibidos(String pedido) {
		if (drone.getPedidos().size() >= Utilidades.PAQUETES_X_DRONE)
			despachar();
		drone.getPedidos().add(pedido);
	}

	/**
	 * Dada la cola llena de pedidos, se procesan. Retorna al drone a la pocisión
	 * inicial para despachar más pedidos
	 */
	public void despachar() {
		while (!drone.getPedidos().isEmpty()) {
			try {
				procesarPedido(drone.getPedidos().poll());
				adminArchivos.escribirResultado(drone.getX(), drone.getY(), drone.getOrientacionActual().getValue());
			} catch (Exception e) {
				String mensaje = String.format("Error despachando. Error: %s", e.getMessage());
				log.error(mensaje);
			}
		}
		this.drone = new Drone(Utilidades.POSICION_INICIAL_X, Utilidades.POSICION_INICIAL_Y,
				Utilidades.ORIENTACION_INICIAL);
	}

	/**
	 * Procesa un pedido
	 * 
	 * @param pedido
	 * @throws Exception
	 *             Operación Inválida cuando una operación leída no es procesable.
	 *             Dirección Inválida cuando no es un movimiento válido
	 */
	public void procesarPedido(String pedido) throws Exception {
		int X = drone.getX();
		int Y = drone.getY();
		Orientacion o = drone.getOrientacionActual();
		String mensaje = null;
		try {
			mensaje = String.format("Procesando pedido <%s>", pedido);
			log.info(mensaje);
			for (int i = 0; i < pedido.length(); i++) {
				Operacion op = Utilidades.getOperacion(pedido.charAt(i));
				switch (op) {
				case AVANZAR:
					avanzar();
					break;
				case DERECHA:
				case IZQUIERDA:
					drone.setOrientacionActual(Utilidades.orientacionNueva(drone.getOrientacionActual(), op));
					break;
				}
			}
			log.info("Pedido procesado.");
		} catch (Exception e) {
			mensaje = String.format("Error procesando pedido <%s>, Error: %s", pedido, e.getMessage());
			log.error(mensaje);
			drone.setX(X);
			drone.setY(Y);
			drone.setOrientacionActual(o);
			throw e;
		}
	}

	/**
	 * Realiza el avance del drone. Evalúa si la pocisión obtenida después de mover
	 * el drone se encuentra dentro del rango. En caso que se pueda mover, actualiza
	 * la ubicación del drone.
	 * 
	 * @throws FueraDeRangoExcepcion
	 *             cuando la posición no es alcanzable
	 */
	public void avanzar() throws FueraDeRangoExcepcion {
		int posicionActualX = drone.getX();
		int posicionActualY = drone.getY();
		switch (drone.getOrientacionActual()) {
		case NORTE:
			posicionActualY++;
			break;
		case ORIENTE:
			posicionActualX++;
			break;
		case SUR:
			posicionActualY--;
			break;
		case OCCIDENTE:
			posicionActualX--;
			break;
		}
		if (Math.sqrt((posicionActualX * posicionActualX)
				+ (posicionActualY * posicionActualY)) <= Utilidades.RANGO_LIMITE_ENTREGA) {
			drone.setX(posicionActualX);
			drone.setY(posicionActualY);
		} else
			throw new FueraDeRangoExcepcion(Utilidades.LIMITE_ENTREGA);
	}

	@Override
	public void run() {
		procesarPedidos();
	}
}
