package Dominio;

import java.util.List;

public class Accion { // Aceptar/Rechazar
	public List<Paso> pasosAAplicar;
	public List<Paso> pasosADeshacer;
	
	public void aplicar(Usuario usuario, Atuendo atuendo, Guardarropa guardarropa, Estilo estilo)
	{
		pasosAAplicar.stream().forEach(paso -> {
			try {
				paso.ejecutar(usuario, atuendo, guardarropa, estilo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	public void deshacer(Usuario usuario, Atuendo atuendo, Guardarropa guardarropa, Estilo estilo)
	{
		pasosADeshacer.stream().forEach(paso -> {
			try {
				paso.ejecutar(usuario, atuendo, guardarropa, estilo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
