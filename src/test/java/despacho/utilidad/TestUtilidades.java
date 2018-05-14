package despacho.utilidad;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import despacho.excepciones.DireccionInvalidaExcepcion;
import despacho.excepciones.OperacionInvalidaExcepcion;
import despacho.utilidad.Utilidades;
import despacho.utilidad.Utilidades.Operacion;
import despacho.utilidad.Utilidades.Orientacion;

public class TestUtilidades {
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testOrientacionNueva() throws DireccionInvalidaExcepcion {
		assertEquals("Orientación oriente desde norte", Orientacion.ORIENTE,
				Utilidades.orientacionNueva(Orientacion.NORTE, Operacion.DERECHA));
		assertEquals("Orientación sur desde oriente", Orientacion.SUR,
				Utilidades.orientacionNueva(Orientacion.ORIENTE, Operacion.DERECHA));
		assertEquals("Orientación occidente desde sur", Orientacion.OCCIDENTE,
				Utilidades.orientacionNueva(Orientacion.SUR, Operacion.DERECHA));
		assertEquals("Orientación norte desde occidente", Orientacion.NORTE,
				Utilidades.orientacionNueva(Orientacion.OCCIDENTE, Operacion.DERECHA));
		assertEquals("Orientación oriente desde sur", Orientacion.ORIENTE,
				Utilidades.orientacionNueva(Orientacion.SUR, Operacion.IZQUIERDA));
		assertEquals("Orientación sur desde occidente", Orientacion.SUR,
				Utilidades.orientacionNueva(Orientacion.OCCIDENTE, Operacion.IZQUIERDA));
		assertEquals("Orientación occidente desde norte", Orientacion.OCCIDENTE,
				Utilidades.orientacionNueva(Orientacion.NORTE, Operacion.IZQUIERDA));
		assertEquals("Orientación norte desde oriente", Orientacion.NORTE,
				Utilidades.orientacionNueva(Orientacion.ORIENTE, Operacion.IZQUIERDA));
	}

	@Test
	public void throwsDireccionInvalidaExceptionIfDirecionInvalida() throws DireccionInvalidaExcepcion {
		exception.expect(DireccionInvalidaExcepcion.class);
		exception.expectMessage(Utilidades.DIRECCION_INVALIDA);
		Utilidades.orientacionNueva(Orientacion.NORTE, Operacion.AVANZAR);
	}

	@Test
	public void testGetOperacion() throws OperacionInvalidaExcepcion {
		char operacion = 'A';
		assertEquals("Operación Avanzar", Operacion.AVANZAR, Utilidades.getOperacion(operacion));
		operacion = 'D';
		assertEquals("Operación Derecha", Operacion.DERECHA, Utilidades.getOperacion(operacion));
		operacion = 'I';
		assertEquals("Operación Izquierda", Operacion.IZQUIERDA, Utilidades.getOperacion(operacion));
	}

	@Test
	public void throwsOperacionInvalidaExceptionIfOperacionInvalida() throws OperacionInvalidaExcepcion {
		exception.expect(OperacionInvalidaExcepcion.class);
		exception.expectMessage(Utilidades.OPERACION_INVALIDA);
		char operacion = 'X';
		Utilidades.getOperacion(operacion);
	}
}
