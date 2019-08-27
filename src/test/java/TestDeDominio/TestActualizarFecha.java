package TestDeDominio;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import Dominio.Estilish.Formal;
import Dominio.EventClasses.Anualmente;
import Dominio.EventClasses.Diario;
import Dominio.EventClasses.DiarioPersonalizado;
import Dominio.EventClasses.Evento;
import Dominio.EventClasses.Mensualmente;
import Dominio.EventClasses.Semanalmente;

public class TestActualizarFecha {
	private Evento eventoOcurrioYesFrecuente1;
	private Evento eventoOcurrioYesFrecuente1Personalizado;
	private Evento eventoOcurrioYesFrecuente7;
	private Evento eventoOcurrioYesFrecuente30;
	private Evento eventoOcurrioYesFrecuente365;
	
	@Before
	public void init() {
		Calendar ayer = Calendar.getInstance();
		ayer.add(Calendar.DATE, -1); // le resto un dia a la fecha actual 
		
		eventoOcurrioYesFrecuente1 = new Evento(ayer,"TuVieja",new Formal(), new Diario());
		
		ArrayList<Integer> diasQueRepite = new ArrayList<Integer>();
		diasQueRepite.add(Calendar.MONDAY);
		diasQueRepite.add(Calendar.WEDNESDAY);
		
		eventoOcurrioYesFrecuente1Personalizado = new Evento(ayer,"TuVieja",new Formal(), new DiarioPersonalizado(diasQueRepite));
		
		Calendar unaSemana = Calendar.getInstance();
		unaSemana.add(Calendar.DATE, -7); // le resto una semana a la fecha actual 
		eventoOcurrioYesFrecuente7 = new Evento(unaSemana,"TuVieja",new Formal(), new Semanalmente());
		
		Calendar unMes = Calendar.getInstance();
		unMes.add(Calendar.DATE, -31); // le resto un mes a la fecha actual 
		eventoOcurrioYesFrecuente30 = new Evento(unMes,"TuVieja",new Formal(), new Mensualmente());
		
		Calendar unAnio = Calendar.getInstance();
		unAnio.add(Calendar.DATE, -365); // le resto un a�o a la fecha actual 
		eventoOcurrioYesFrecuente365 = new Evento(unAnio,"TuVieja",new Formal(), new Anualmente());

	}
	
	@Test
	public void actualizarFechaEventoDiario() {
		eventoOcurrioYesFrecuente1.actualizarFecha();
		assertEquals(eventoOcurrioYesFrecuente1.getFecha().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
	}
	
	@Test
	public void actualizarFechaEventoDiarioPersonalizado() {
		eventoOcurrioYesFrecuente1Personalizado.actualizarFecha();
	
		
		assertEquals(eventoOcurrioYesFrecuente1Personalizado.getFecha().get(Calendar.DAY_OF_WEEK), Calendar.MONDAY);
	}
	
	@Test
	public void actualizarFechaEventoSemanal() {
		eventoOcurrioYesFrecuente7.actualizarFecha();
		assertEquals(eventoOcurrioYesFrecuente7.getFecha().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
	}
	
	@Test
	public void actualizarFechaEventoMensual() {
		eventoOcurrioYesFrecuente30.actualizarFecha();
		assertEquals(eventoOcurrioYesFrecuente30.getFecha().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
	}
	
	@Test
	public void actualizarFechaEventoAnual() {
		eventoOcurrioYesFrecuente365.actualizarFecha();
		assertEquals(eventoOcurrioYesFrecuente365.getFecha().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
	}
	
}