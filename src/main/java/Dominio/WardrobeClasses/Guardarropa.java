package Dominio.WardrobeClasses;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import Dominio.ClothingClasses.Atuendo;
import Dominio.ClothingClasses.Categoria;
import Dominio.ClothingClasses.Estilo;
import Dominio.ClothingClasses.Prenda;
import Dominio.UserClasses.Evento;
import Dominio.UserClasses.Usuario;
import Dominio.WeatherAPIClasses.GestorClimatico;

public class Guardarropa {
	
	private Usuario creador;
	private List<Usuario> usuariosConAcceso;
	private Estilo estilo;
	private List<Prenda> prendasDisponibles;
	private List<Prenda> prendasNoDisponibles;
	private GestorClimatico climaHelp;

	public Guardarropa(Usuario creador, List<Usuario> compartidos,
			Estilo estilo, List<Prenda> prendas, GestorClimatico climaHelp) {
		this.creador = creador;
		this.usuariosConAcceso = compartidos;
		this.estilo = estilo;
		this.prendasDisponibles = prendas;
		this.prendasNoDisponibles = null;
		this.climaHelp = climaHelp;
	}

	public int cantidadDePrendasDisponibles()
	{
		return prendasDisponibles.size();
	}

	public void permitirAccesoaUsuario (Usuario usuario) {
		usuariosConAcceso.add(usuario);
	}
	
	public void agregarPrenda(Prenda prenda) {

		prendasDisponibles.add(prenda);
	}

	public List<Prenda> getPrendasDisponibles() {
		return this.prendasDisponibles;
	}
	public List<Prenda> getPrendasSuperioresDisponibles(){
		return  prendasDisponibles.stream().filter(p -> p.getCategoria() == Categoria.PARTE_SUPERIOR).collect(Collectors.toList());
	}
	public List<Prenda> getPrendasInferioresDisponibles(){
		return  prendasDisponibles.stream().filter(p -> p.getCategoria() == Categoria.PARTE_INFERIOR).collect(Collectors.toList());
	}
	public List<Prenda> getAccesoriosDisponibles(){
		return  prendasDisponibles.stream().filter(p -> p.getCategoria() == Categoria.ACCESORIOS).collect(Collectors.toList());
	}
	public List<Prenda> getCalzadosDisponibles(){
		return  prendasDisponibles.stream().filter(p -> p.getCategoria() == Categoria.CALZADO).collect(Collectors.toList());
	}
	public Estilo getEstilo()
	{
		return this.estilo;
	}
	
	public Atuendo generarRecomendacion(Evento evento) throws Exception
	{
		Atuendo atuendo = null;
		int limitSup = getPrendasSuperioresDisponibles().size();
		int limitInf = getPrendasInferioresDisponibles().size();
		int limitAccesorios = getAccesoriosDisponibles().size();
		int limitCalzados = getCalzadosDisponibles().size();
	
		double temperatura = GestorClimatico.obtenerTemperatura(evento.getFecha().get(Calendar.DAY_OF_MONTH) , evento.getFecha().get(Calendar.HOUR_OF_DAY));
		
		if(limitSup != 0 && limitInf !=0 && limitCalzados != 0)
		{
			Random rand = new Random();
			int indexSuperior = rand.nextInt(limitSup);
			Prenda prendaSuperior = getPrendasSuperioresDisponibles().get(indexSuperior);
			int indexInferior = rand.nextInt(limitInf);
			Prenda prendaInferior = getPrendasInferioresDisponibles().get(indexInferior);
			int indexAccesorios = rand.nextInt(limitAccesorios);
			Prenda accesorio = getAccesoriosDisponibles().get(indexAccesorios);
			int indexCalzados = rand.nextInt(limitCalzados);
			Prenda calzado = getCalzadosDisponibles().get(indexCalzados);
			ArrayList<Prenda> prendasDeAtuendo = new ArrayList<Prenda>();
			prendasDeAtuendo.add(prendaSuperior);
			prendasDeAtuendo.add(prendaInferior);
			prendasDeAtuendo.add(accesorio);
			prendasDeAtuendo.add(calzado);
			atuendo = new Atuendo(prendasDeAtuendo);
		}
		else
		{
			atuendo = null;
		}
		return atuendo;
	}
	
	public void marcarNoDiponible(Atuendo atuendo)
	{
		atuendo.getPrendas().forEach(prenda -> {
			this.prendasDisponibles.remove(prenda);
			this.prendasNoDisponibles.add(prenda);
		});
	}
}


