package controllers;

import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class WardrobeController {

	
	
public static ModelAndView init(Request req, Response res) {
		Map<String, Double> model = new HashMap<>();
	
		return new ModelAndView(model,"/guardarropas.hbs");
	}
	
	
	
	
	
}
