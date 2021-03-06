package Dominio.ClothingClasses;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import db.EntidadPersistente;

@Entity
@Table(name = "TipoDeRopa")
public class TipoDeRopa extends EntidadPersistente{
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "abrigo")
	private Integer abrigo;
	 
	@Enumerated(EnumType.ORDINAL)
	private Categoria categoria;
	
	@ElementCollection(targetClass=Capas.class) 
	@CollectionTable( name="tiporopa_capa", joinColumns=@JoinColumn(name="id") ) 
	@Column( name="capas", nullable=false ) @Enumerated(EnumType.ORDINAL) 
	private ArrayList<Capas> capasEnDondePuedeEstar = new ArrayList<Capas>();
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Material> materialesNoCompatibles = new ArrayList<Material>();
	
	@Column(name = "imagen")
	private String imagen;
	
	public TipoDeRopa(String nombre,Integer abrigo,Categoria categoria, ArrayList<Capas> capas,
			ArrayList<Material> materialesNoCompatibles, String imagen) {
		this.nombre = nombre;
		this.abrigo = abrigo;
		this.categoria = categoria;
		this.capasEnDondePuedeEstar = capas;
		this.materialesNoCompatibles = materialesNoCompatibles;
		this.imagen = imagen;
	}
	
	public TipoDeRopa() {}
	
	public Integer getNivelAbrigo() {
		return this.abrigo;
	}
	
	public List<Material> getMaterialesNoCompatible(){
		return this.materialesNoCompatibles;
	}
	
	public Boolean esDeEsaCapa(final int capa){
		return capasEnDondePuedeEstar.stream().anyMatch(p -> p.ordinal() == capa);
	}
	
	
	public boolean compararNombres(String otroNombre){


		return this.getNombre().equals(otroNombre);
	}

	public String getNombre() {
		return nombre;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Boolean sirveParaLluvia() {
		return false;
	}


	
//	REMERAMANGACORTA 3,
//	REMERAMANGALARGA 4,
//	CAMISA 4,
//	CHOMBA 4,
//	MUSCULOSA 1,
//	CAMPERAGRUESA 10,
//	CAMPERAMEDIA 7, 
//	CAMPERAFINITA 4,
//	BUZO 6,
//	PULOVER 7,
//	POLERA 8,
}


//Temp 18, Base 27, 9 grados de calor, +-3(sup), 3 + 4 = 7, pantalon largo.
//Temp 23, Base 27, 4 grados de calor, +-3(sup), 3, pantaloncorto.
//Temp 4, Base 27, 23 grados de calor +-3(sup), 3 + 4 + 6 + 10.
//Temp 11, Base 27, 16 grados de calor +-3(sup), 4 + 7 + 7, 