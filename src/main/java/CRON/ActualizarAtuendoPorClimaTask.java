package CRON;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import Dominio.UserClasses.Usuario;
import Dominio.WeatherAPIClasses.GestorClimatico;
import Repositorios.factories.FactoryRepositorioUsuario;

public class ActualizarAtuendoPorClimaTask extends TimerTask {
	
	private final static int HORA = 2;
    private final static int MINUTOS = 0;
	
	@Override
	public void run()
	{
		List<Usuario> usuarios = FactoryRepositorioUsuario.get().getUsuariosConEventosProximosYnotificados();
		usuarios.stream().forEach
		(
			u -> u.getEventosProximosYnotificados().forEach
			(
					e ->
					{
						
						try {
							double temperatura = pedirTemperatura(e.getFechaLocalDateTime(), e.getFechaLocalDateTime().getHour());
							
							if(!e.getUltimaSugerencia().abrigaLoNecesario(temperatura, u))
								e.setSeNotificoUltimaSugerencia(false);
							
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						
					}
			)
		);
	}
	
	private double pedirTemperatura(LocalDateTime fecha, int hora) throws IOException {
		return GestorClimatico.getInstance().obtenerTemperatura(fecha, hora);
	}
	
	public void empezar()
	{
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, HORA);
		today.set(Calendar.MINUTE, MINUTOS);
		today.set(Calendar.SECOND, 0);
		
		Timer timer = new Timer();
		
		timer.schedule(this, today.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)); // period: 1 day
	}
}
