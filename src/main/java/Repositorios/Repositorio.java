package Repositorios;

import Repositorios.daos.DAO;

public abstract class Repositorio {

	 protected DAO dao;

	    public void setDao(DAO dao) {
	        this.dao = dao;
	    }

	    public void agregar(Object unObjeto){
	        this.dao.agregar(unObjeto);
	    }

	    public void modificar(Object unObjeto){
	        this.dao.modificar(unObjeto);
	    }

	    public void eliminar(Object unObjeto){
	        this.dao.eliminar(unObjeto);
	    }	
	

	
//    String nombreArchivo;
//   
//    public String getJsonFile() { //Separe este metodo para poder mockearlo al momento de testear
//        return getClass().getClassLoader().getResource(nombreArchivo).getFile();
//    }
}
