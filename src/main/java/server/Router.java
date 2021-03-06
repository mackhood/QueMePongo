package server;


import java.util.HashSet;
import java.util.Set;

import controllers.LoginController;
import controllers.WardrobeController;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import utils.BooleanHelper;
import utils.HandlebarsTemplateEngineBuilder;
import utils.SessionHandler;
import controllers.*;


public class Router {

	static Set<String> publicRoutes = new HashSet<String>();

	public static Boolean isPublic(String route)
	{
		return publicRoutes.contains(route);
	}

	private static void setPublicRoutes(Set<String> publicRoutes)
	{	
		publicRoutes.add("/login");
		publicRoutes.add("/loginFailure");
		publicRoutes.add("/logout");
	}

	public static void configure() {
		WardrobeController controller = new WardrobeController();
		Spark.staticFiles.location("/public");
		setPublicRoutes(publicRoutes);
		
		//es asi porque si, corta
		HandlebarsTemplateEngine engine = HandlebarsTemplateEngineBuilder
				.create()
				.withDefaultHelpers()
				.withHelper("isTrue", BooleanHelper.isTrue)
				.build();
		
		//el before chequea si esta autenticado (con username) y si la ruta es p�blica, en caso de que una no lo sea, redirige al login
		//el get te devuelve un model and view
		//el post te "redirecciona" debido a una modificacion que haces (si bien devuelven model and view en realidad devuelven null)
		
		Spark.before("/", SessionHandler.allowed());
		Spark.before("/eventos", SessionHandler.allowed());
		Spark.before("/guardarropas/:idGuardarropa",SessionHandler.allowed());
		Spark.before("/guardarropas",SessionHandler.allowed());
		Spark.before("/calificaciones",SessionHandler.allowed());
		Spark.before("/out",SessionHandler.allowed());
		Spark.before("/eventos/alta",SessionHandler.allowed());
	    Spark.before("/verRecomendacion",SessionHandler.allowed()); 
	    Spark.before("/generarRecomendacion",SessionHandler.allowed()); 
	    Spark.before("/aceptarRecomendacion",SessionHandler.allowed()); 
	    Spark.before("/rechazarRecomendacion",SessionHandler.allowed()); 
	    Spark.before("/deshacerRecomendacion",SessionHandler.allowed()); 
	    Spark.before("/altaPrenda",SessionHandler.allowed()); 
	    Spark.before("/sugerencias",SessionHandler.allowed()); 

		//Spark.before("/eventos/info", SessionHandler.allowed());
		
		Spark.get("/login", LoginController::init,engine);
		Spark.get("/loginFailure", LoginController::loginFailure, engine);
		Spark.post("/loginFailure", LoginController::processLogin, engine);
		Spark.post("/login", LoginController::processLogin, engine);
		 
		Spark.get("/", controller::init,engine);
		Spark.get("/guardarropas/:idGuardarropa", controller::indexViewDatosDeUnGuardarropa, engine);
		Spark.get("/guardarropas", controller::mostrarPrendas, engine);
		
		Spark.get("/altaPrenda", controller::indexViewAgregarPrenda, engine);	
		Spark.post("/altaPrenda", controller::registrarPrenda);
		
		Spark.path("/eventos",  () -> {
			Spark.get("", controller::verEventos, engine); // creo que no tendria que ser Wardrobe, pero lo puse para ponerle un cliente a Event
			Spark.get("/alta", controller::agregarEvento, engine);
			Spark.post("/alta", controller::processAgregarEvento);
		});
		
		Spark.get("/calificaciones", controller::indexObtenerAtuendosCalificar,engine);
		Spark.post("/calificaciones", controller::califico,engine);
		Spark.get("/sugerencias", controller::mostrarSugerencias,engine);
		Spark.post("/verRecomendacion", controller::envioSugerencia, engine);
		Spark.post("/generarRecomendacion", controller::generaRecomendacion, engine);
		Spark.post("/sugerencias", controller::aceptarRecomendacion, engine);
		Spark.post("/rechazarRecomendacion", controller::rechazarRecomendacion, engine);
		Spark.post("/deshacerRecomendacion", controller::deshacerRecomendacion, engine);

		Spark.get("/out", controller::logOut, engine); // este boton no esta en nuestro tp, pero lo puse porque si
	}

}