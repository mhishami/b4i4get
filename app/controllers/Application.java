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
    		User u = User.getUser(user);
    		List<TodoList> todoList = TodoList.find("byUser", u).fetch();
    		user = user.split("@")[0];
    		render(user, todoList);
    	} else {
            render(user);    		
    	}
    }
    
    public static void getTodoList(@Required Long id) {
    	TodoList todoList = (TodoList) TodoList.findById(id);
    	List<Todo> todos = Todo.find("byTodoList", todoList).fetch();
    	String title = todoList.title;
    	
    	// add some log
    	Logger.info("Todos count = %d", todos.size());
    	render(todos, title);
    }

    public static void markTodoListDone(@Required Long id, boolean done) {
    	TodoList todoList = (TodoList) TodoList.findById(id);
    	todoList.isDone = done;
    	todoList.save();
    	
    	redirect("/");
    }
    
    public static void getTodo(@Required Long id) {
    	Todo todo = Todo.findById(id);
    	render(todo);
    }
    
    public static void markTodoCompleted(@Required Long id, boolean done) {
    	Todo todo = Todo.findById(id);
    	todo.isCompleted = done;
    	todo.save();
    	
    	redirect("/list/" + todo.todoList.id);
    }
    
}