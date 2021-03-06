package Repositorios.daos;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import Dominio.ClothingClasses.Color;
import Dominio.ClothingClasses.Material;
import Dominio.ClothingClasses.TipoDeRopa;
import Dominio.UserClasses.Usuario;
import Models.Model;
import db.EntityManagerHelper;

public class DAOMySQL implements DAO {
    private Model model;

    public DAOMySQL(Model model){
        this.model = model;
    }

    @Override
    public <T> List<T> buscarTodos() {
        return this.model.buscarTodos();
    }

    @Override
    public <T> T buscar(int id) {
        return this.model.buscar(id);
    }
    
    @Override
    public void agregar(Object unObjeto) {
        this.model.agregar(unObjeto);
    }

    @Override
    public void modificar(Object unObjeto) {
        this.model.modificar(unObjeto);
    }

    @Override
    public void eliminar(Object unObjeto) {
        this.model.eliminar(unObjeto);
    }
    
    @Override
    public List<Usuario> getUsuariosConEventosProximosYSinNotificar()
    {
    	String query =    "SELECT u FROM Usuario u\r\n" +
    		    "INNER JOIN u.eventos e \r\n"  + 
    		    "JOIN e.importancia i \r\n" + 
    			"WHERE e.sugerenciaNotificada = 0 "+
    			"AND DATEDIFF((SELECT fecha FROM Evento ev WHERE ev.id = e.id), now()) <= \r\n" + 
    			"	(CASE WHEN i.class = 'Baja' THEN 1\r\n" + 
    			"   ELSE (CASE WHEN i.class = 'Media' THEN 7 ELSE (CASE WHEN i.class = 'Alta' THEN 30 ELSE -1 END)END) END) \r\n"; 
    	
    	return model.buscarPorQuery(query); 
    }
    
    @Override
    public List<Usuario> getUsuariosConEventosProximosYnotificados()
    {
    	String query =    "SELECT u FROM Usuario u\r\n" +
    		    "INNER JOIN u.eventos e \r\n"  + 
    		    "JOIN e.importancia i \r\n" + 
    			"WHERE e.sugerenciaNotificada = 1 "+
    			"AND DATEDIFF((SELECT fecha FROM Evento ev WHERE ev.id = e.id), now()) <= \r\n" + 
    			"	(CASE WHEN i.class = 'Baja' THEN 1\r\n" + 
    			"   ELSE (CASE WHEN i.class = 'Media' THEN 7 ELSE (CASE WHEN i.class = 'Alta' THEN 30 ELSE -1 END)END) END) \r\n"; 

    	
    	return model.buscarPorQuery(query); 
    }
    
    @Override
    public List<Usuario> getUsuariosConEventosOcurridos(LocalDateTime fecha)
    {
    	String query = "SELECT u FROM Usuario u\r\n" +
    		    "INNER JOIN u.eventos e \r\n"  + 
    		    "JOIN e.importancia i \r\n" + 
    			"WHERE e.sugerenciaNotificada = 0 "+
    			"AND DATEDIFF((SELECT fecha FROM Evento ev WHERE ev.id = e.id),"+ fecha +") = 0 \r\n"; 

	return model.buscarPorQuery(query);
    }
    
    @Override
    public boolean existeUsuario(String username, String password) {
    	Optional<Usuario> usuario = ((List<Usuario>)(List<?>) this.buscarTodos()).stream()
				.filter(c -> Objects.equals(c.getPassword(), password) && Objects.equals(c.getUsername(), username))
				.findFirst();
    	
		if (!usuario.isPresent()) {
				return false;
		}
		return true;
    }

	@Override
	public Usuario buscarUsuario(String username, String password) {
		return  EntityManagerHelper.getEntityManager().createQuery("from Usuario c where c.username = :u and c.password = :p", Usuario.class)
    			.setParameter("u", username).setParameter("p", password).getSingleResult();
	}
	
	@Override
	public Material findByNameMaterial(String nombre) {
	 return EntityManagerHelper.getEntityManager().createQuery("from Material m where m.nombre = :n", Material.class)
 			.setParameter("n", nombre).getSingleResult();
    }
	
	@Override
	public TipoDeRopa findByNameTipoDeRopa(String nombre) {
		System.out.println(nombre);
	 return EntityManagerHelper.getEntityManager().createQuery("from TipoDeRopa tr where tr.nombre = :n", TipoDeRopa.class)
 			.setParameter("n", nombre).getSingleResult();
    }
	
	@Override
	public Color findByNameColor(String nombre) {
	 return EntityManagerHelper.getEntityManager().createQuery("from Color c where c.nombre = :n", Color.class)
 			.setParameter("n", nombre).getSingleResult();
    }

}
