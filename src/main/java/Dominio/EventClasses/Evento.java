package Dominio.EventClasses;

import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import Dominio.ClothingClasses.Atuendo;
import Dominio.Estilish.Estilo;
import Dominio.UserClasses.Usuario;
import db.EntidadPersistente;

@Entity
@Table(name = "evento")
public class Evento extends EntidadPersistente{
	@Column(name = "nombre")
	private String nombre;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id", referencedColumnName = "id")
	private Usuario creador;
	
	@Column(name = "fecha")
	private Calendar fecha;
	
	@Column(name = "direccion")
	private String direccion;
	
	@Enumerated(EnumType.ORDINAL)
	private Estilo estilo;
	
	@Transient
	private Frecuencia frecuencia; 
	
	@Transient
	private ImportanciaEvento importancia;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Atuendo> sugerencias;
	
	@Column(name = "sugerenciaNotificada")
	private boolean sugerenciaNotificada;
	
	
	public Evento (String nombre, Calendar fecha, String direccion, Estilo estilo, Frecuencia frecuencia)
	{
		this.nombre = nombre;
		this.fecha = fecha;
		this.direccion = direccion;
		this.estilo = estilo;
		this.frecuencia = frecuencia;
		this.sugerenciaNotificada = false;
	}
	
	public String getNombre()
	{
		return this.nombre;
	}
	
	public Estilo getEstilo()
	{
		return this.estilo;
	}
	
	public Calendar getFecha()
	{
		return this.fecha;
	}
	
	public boolean estaProximo()
	{
		return importancia.estaProximo(this);
	}
	
	public boolean ocurre(Calendar fechaInteres)
	{
		return this.fecha.compareTo(fechaInteres) == 0;
	}
	
	public boolean esFrecuente() 
	{
		return this.frecuencia != null;
	}
	
	public void actualizarFecha()
	{
		this.frecuencia.actualizarFecha(this);
		this.sugerenciaNotificada = false;
	}
	
	public void sumarDias(int dias)
	{
		this.fecha.add(Calendar.DAY_OF_YEAR, dias);
	}
	
	public void agregarSugerencia(Atuendo sugerencia)
	{
		this.sugerencias.add(sugerencia);
	}
	
	public Atuendo getUltimaSugerencia()
	{
		return sugerencias.get(sugerencias.size() - 1); 
	}
	
	public boolean getSeNotificoUltimaSugerencia()
	{
		return sugerenciaNotificada;
	}
	
	public void setFecha(Calendar fecha)
	{
		this.fecha = fecha;
	}
	
	public void setSeNotificoUltimaSugerencia(Boolean bool)
	{
		this.sugerenciaNotificada = bool;
	}
}
