package despacho.controlador;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import despacho.excepciones.FueraDeRangoExcepcion;
import despacho.excepciones.OperacionInvalidaExcepcion;
import despacho.utilidad.Utilidades;
import despacho.utilidad.Utilidades.Orientacion;

public class TestDroneControlador {
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testRun() {
		List<String> archivos = new LinkedList<>();
		int drones = Integer.valueOf(System.getProperty("drones"));
		for (int i = 1; i <= drones; i++) {
			String archivoEntrada = String.format("%s%02d.%s", Utilidades.NOMBRE_ARCHIVO_ENTRADA, i,
					Utilidades.EXTENSION_ARCHIVO);
			String archivoSalida = String.format("%s%02d.%s", Utilidades.NOMBRE_ARCHIVO_SALIDA, i,
					Utilidades.EXTENSION_ARCHIVO);
			new Thread(new DroneControlador(archivoEntrada, archivoSalida)).start();
			archivos.add(archivoSalida);
		}
		for (String archivo : archivos) {
			File aO = new File(archivo);
			assertTrue("Existe archivo salida", aO.exists());
		}
	}

	@Test
	public void testProcesarPedidos() {
		DroneControlador dc = new DroneControlador();
		dc.procesarPedidos();
		File aI = new File(dc.getAdminArchivos().getArchivoEntrada());
		File aO = new File(dc.getAdminArchivos().getArchivoSalida());
		assertTrue("Existe archivo entrada", aI.exists());
		assertTrue("Existe archivo salida", aO.exists());
	}

	@Test
	public void testProcesarPedidosNoArchivoLectura() {
		DroneControlador dc = new DroneControlador();
		dc.getAdminArchivos().setArchivoEntrada("iii.in");
		dc.procesarPedidos();
		File aI = new File(dc.getAdminArchivos().getArchivoEntrada());
		assertTrue("No Existe archivo entrada", !aI.exists());
	}

	@Test
	public void testGestionarPedidosRecibidos() {
		DroneControlador dc = new DroneControlador();
		assertEquals("Pedidos vacíos", 0, dc.getDrone().getPedidos().size());
		String pedido = "AAA";
		dc.gestionarPedidosRecibidos(pedido);
		assertEquals("Pedidos ingresado", 1, dc.getDrone().getPedidos().size());
		dc.gestionarPedidosRecibidos(pedido);
		dc.gestionarPedidosRecibidos(pedido);
		dc.gestionarPedidosRecibidos(pedido);
		assertEquals("Pedidos procesados", 1, dc.getDrone().getPedidos().size());
	}

	@Test
	public void testDespachar() {
		DroneControlador dc = new DroneControlador();
		String pedido = "AAAAAA";
		for (int i = 0; i < 3; i++) {
			dc.getDrone().getPedidos().add(pedido);
		}
		dc.despachar();
		assertEquals("Coordenada Y de drone", 0, dc.getDrone().getY());
		assertEquals("Coordenada X de drone", 0, dc.getDrone().getX());
		assertEquals("Orientación", Orientacion.NORTE, dc.getDrone().getOrientacionActual());
	}

	@Test
	public void testDespacharPedidoInvalido() throws OperacionInvalidaExcepcion {
		DroneControlador dc = new DroneControlador();
		String pedido = "zzzzZ";
		dc.getDrone().getPedidos().add(pedido);
		dc.despachar();
		assertEquals("Sin pedidos", 0, dc.getDrone().getPedidos().size());
		assertEquals("Coordenada Y de drone", 0, dc.getDrone().getY());
		assertEquals("Coordenada X de drone", 0, dc.getDrone().getX());
		assertEquals("Orientación", Orientacion.NORTE, dc.getDrone().getOrientacionActual());
	}

	@Test
	public void arrojaExcepcionIOSiAlgoSalioMalConLosArchivos() {
		DroneControlador dc = new DroneControlador();
		String ruta = "W:/MIO.BAL";
		dc.getAdminArchivos().setArchivoSalida(ruta);
		dc.getAdminArchivos().escribir(ruta);
	}

	@Test
	public void testProcesarPedidoCorrecto() throws Exception {
		String pedido = "AAAAIAAD";
		DroneControlador dc = new DroneControlador();
		dc.procesarPedido(pedido);
		assertEquals("Coordenada X", -2, dc.getDrone().getX());
		assertEquals("Coordenada Y", 4, dc.getDrone().getY());
		assertEquals("Orientación", Orientacion.NORTE, dc.getDrone().getOrientacionActual());
	}

	@Test
	public void testProcesarPedidoInalcanzable() throws Exception {
		String pedido = "AAAAAAAAAAAADAAAAADA";
		DroneControlador dc = new DroneControlador();
		exception.expect(FueraDeRangoExcepcion.class);
		exception.expectMessage(Utilidades.LIMITE_ENTREGA);
		dc.procesarPedido(pedido);
	}

	@Test
	public void testProcesarPedidoErroneo() throws Exception {
		String pedido = "ASAD";
		DroneControlador dc = new DroneControlador();
		exception.expect(OperacionInvalidaExcepcion.class);
		exception.expectMessage(Utilidades.OPERACION_INVALIDA);
		dc.procesarPedido(pedido);
		assertEquals("Coordenada Y de drone", 0, dc.getDrone().getY());
		assertEquals("Coordenada X de drone", 0, dc.getDrone().getX());
		assertEquals("Orientación", Orientacion.NORTE, dc.getDrone().getOrientacionActual());
	}

	@Test
	public void testProcesarPedidoErroneoConVacio() throws Exception {
		String pedido = "A  D";
		DroneControlador dc = new DroneControlador();
		exception.expect(OperacionInvalidaExcepcion.class);
		exception.expectMessage(Utilidades.OPERACION_INVALIDA);
		dc.procesarPedido(pedido);
		assertEquals("Coordenada Y de drone", 0, dc.getDrone().getY());
		assertEquals("Coordenada X de drone", 0, dc.getDrone().getX());
		assertEquals("Orientación", Orientacion.NORTE, dc.getDrone().getOrientacionActual());
	}

	@Test
	public void testProcesarPedidoErroneoVacio() throws Exception {
		String pedido = "";
		DroneControlador dc = new DroneControlador();
		dc.procesarPedido(pedido);
		assertEquals("Coordenada Y de drone", 0, dc.getDrone().getY());
		assertEquals("Coordenada X de drone", 0, dc.getDrone().getX());
		assertEquals("Orientación", Orientacion.NORTE, dc.getDrone().getOrientacionActual());
	}

	@Test
	public void testAvanzar() throws FueraDeRangoExcepcion {
		DroneControlador dc = new DroneControlador();
		assertEquals("Coordenada Y de drone", 0, dc.getDrone().getY());
		dc.avanzar();
		assertEquals("Coordenada Y después de avanzar en orientación norte", 1, dc.getDrone().getY());
		dc.getDrone().setOrientacionActual(Orientacion.ORIENTE);
		dc.avanzar();
		assertEquals("Coordenada X después de avanzar en orientación Oriente", 1, dc.getDrone().getX());
		dc.getDrone().setOrientacionActual(Orientacion.SUR);
		dc.avanzar();
		assertEquals("Coordenada Y después de avanzar en orientación sur", 0, dc.getDrone().getY());
		dc.getDrone().setOrientacionActual(Orientacion.OCCIDENTE);
		dc.avanzar();
		assertEquals("Coordenada X después de avanzar en orientación occidente", 0, dc.getDrone().getX());
	}

	@Test(expected = FueraDeRangoExcepcion.class)
	public void arrojaFueraDeRantoExcepcionSiRangoNoEsAlcanzable() throws FueraDeRangoExcepcion, IOException {
		DroneControlador dc = new DroneControlador();
		for (int i = 0; i < Utilidades.RANGO_LIMITE_ENTREGA + 5; i++)
			dc.avanzar();
	}

}
