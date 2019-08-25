package Dominio.UserClasses;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import Dominio.ClothingClasses.Atuendo;
import Dominio.ClothingClasses.Estilo;
import Dominio.ClothingClasses.Prenda;
import Dominio.EventClasses.Evento;
import Dominio.EventClasses.Frecuencia;
import Dominio.WardrobeClasses.Guardarropa;

public class Usuario {
	private String username;
	private TipoDeUsuario tipoDeCuenta;
	public List<Guardarropa> guardarropas;
	private List<Atuendo> atuendosRechazados;
	private Atuendo ultimoAtuendo;
	private List<Evento> eventos;
	private int offset;
	private String mail;
	private String numeroCelular;
	private List<Atuendo> sugerenciasQueFaltanCalificar;
	
	public Usuario(String username, String mail, String numeroCelular){
		this.username = username;
		this.guardarropas = new ArrayList<Guardarropa>();
		this.eventos = new ArrayList<Evento>();
		this.mail = mail;
		this.numeroCelular = numeroCelular;
	}

	public Usuario(String username, Guardarropa guardarropa) {
		this.username = username;
		this.guardarropas = new ArrayList<Guardarropa>();
		this.inicializarGuardarropa(guardarropa);
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	private void inicializarGuardarropa(Guardarropa guardarropa) {
		this.guardarropas.add(guardarropa);
	}

	public void agregarGuardarropa(Guardarropa guardarropa) {
		this.guardarropas.add(guardarropa);
	}

	public void agregarPrendaAGuardarropa(Guardarropa guardarropa, Prenda prenda) throws Exception {

		if(!this.guardarropas.stream().anyMatch(p -> p.getPrendasDisponibles().contains(prenda))) {
			tipoDeCuenta.agregarPrendaAGuardarropa(guardarropa, prenda);
		} else {
			throw new Exception("Ya tenes la prenda en otro guardarropa");
		}

	}
	
	public void agregarARechazados(Atuendo atuendo)
	{
		this.atuendosRechazados.add(atuendo);
	}
	
	public void eliminarDeRechazados(Atuendo atuendo)
	{
		this.atuendosRechazados.remove(atuendo);
	}

	public Atuendo pedirRecomendacion(Evento evento) throws Exception{
		List<Guardarropa> guardarropasConEstilo = 
				this.guardarropas.stream().filter(g -> g.getEstilo() == evento.getEstilo()).collect(Collectors.toList());
		
		Atuendo atuendoFinal = null;
		Random rand = new Random();
		int cantGuardarropas = guardarropasConEstilo.size();
		while(atuendoFinal != null)
		{
			Guardarropa guardarropa =  guardarropasConEstilo.get(rand.nextInt(cantGuardarropas));
			atuendoFinal = guardarropa.generarRecomendacion(evento, this);
		}
		this.ultimoAtuendo = atuendoFinal;
		return atuendoFinal;
	}

	public int getOffset() {
		return this.offset;
	}

	public void crearEvento(Calendar fecha, String direccion, Estilo estilo, Frecuencia frecuencia) {
		this.eventos.add(new Evento(fecha, direccion, estilo, frecuencia));
	}
	
	public void crearNuevoGuardarropas(Estilo estilo) {
		this.guardarropas.add(new Guardarropa(this, estilo));
	}
	
	public List<Evento> getEventos(){
		return this.eventos;
	}
	
	public String getMail()
	{
		return this.mail;
	}
	
	public String getNumeroCelular()
	{
		return this.numeroCelular;
	}
	
	public boolean tieneEventosProximos()
	{
		return eventos.stream().anyMatch(e -> e.estaProximo());
	}
	
	//Me parece que esta funcion no la usamos pero la dejo por las dudas porque no se si puede romper en otros lados!
	public List<Evento> getEventosProximos()
	{
		return eventos.stream().filter(e -> e.estaProximo()).collect(Collectors.toList());
	}
	
	public boolean tieneEventosOcurridoFrecuentemente(Calendar fecha)
	{
		return eventos.stream().anyMatch(e -> e.ocurre(fecha) && e.esFrecuente());
	}
	
	public List<Evento> getEventosOcurridoFrecuentemente(Calendar fecha)
	{
		return eventos.stream().filter(e -> e.ocurre(fecha) && e.esFrecuente()).collect(Collectors.toList());
	}
	
	public List<Atuendo> getSugerenciasQueFaltanCalificar()
	{
		List<Atuendo> sugerencias = new ArrayList<Atuendo>();
		
		List<Evento> eventosQueEstanProximosYNoSeSugirioLaRecomendacion = 
				this.eventos.stream().filter(e -> e.estaProximo() && !e.getSeNotificoSugerencia()).collect(Collectors.toList());
		
		if(!eventosQueEstanProximosYNoSeSugirioLaRecomendacion.isEmpty())
			eventosQueEstanProximosYNoSeSugirioLaRecomendacion
				.stream().forEach(e -> sugerencias.add(e.getSugerencia()));
		
		return sugerencias;
	}

	public void cambiarCategoria(TipoDeUsuario tipo) {
		this.tipoDeCuenta = tipo;
	}
	
	public boolean tieneEventoSinNotificar()
	{
		return eventos.stream().anyMatch(e -> !e.getSeNotificoSugerencia());
	}
	
	public List<Evento> getEventosProximosYsinNotificar()
	{
		return eventos.stream().filter(e -> e.estaProximo() && !e.getSeNotificoSugerencia()).collect(Collectors.toList());
	}
	
	public List<Evento> getEventosProximosYnotificados()
	{
		return eventos.stream().filter(e -> e.estaProximo() && e.getSeNotificoSugerencia()).collect(Collectors.toList());
	}
}
