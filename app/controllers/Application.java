/**
 * 
 */
package controllers;

import play.*;
import play.data.validation.Required;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

	@Before
	static void addDefaults() {
	    renderArgs.put("appTitle", Play.configuration.getProperty("app.Title"));
	    renderArgs.put("appDesc", Play.configuration.getProperty("app.Desc"));
	}
	
    public static void index() {
    	String user = Security.connected();
    	if (null != user) {
    		user = user.split("@")[0];
    	}
        render(user);
    }
    
    public static void sayHello(@Required String name) {
    	if (validation.hasErrors()) {
    		flash.error("Opps, please enter your name");
    		index();
    	}
    	render(name);
    }

}