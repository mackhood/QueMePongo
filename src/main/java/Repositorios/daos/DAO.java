package Repositorios.daos;

import java.time.LocalDateTime;
import java.util.List;

import Dominio.ClothingClasses.Color;
import Dominio.ClothingClasses.Material;
import Dominio.ClothingClasses.TipoDeRopa;
import Dominio.UserClasses.Usuario;

public interface DAO {
    public <T> List<T> buscarTodos();
    public <T> T buscar(int id);
    public void agregar(Object unObjeto);
    public void modificar(Object unObjeto);
    public void eliminar(Object unObjeto);
    public List<Usuario> getUsuariosConEventosProximosYSinNotificar();
    public List<Usuario> getUsuariosConEventosProximosYnotificados();
    public List<Usuario> getUsuariosConEventosOcurridos(LocalDateTime fecha);
    public boolean existeUsuario(String username, String password);
    public Usuario buscarUsuario(String username, String password);
    //public Material findByName(String nombre);
	public Material findByNameMaterial(String nombre);
	public TipoDeRopa findByNameTipoDeRopa(String nombre);
	public Color findByNameColor(String nombre);
	
}
