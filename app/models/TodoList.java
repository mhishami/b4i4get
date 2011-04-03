package models;

import java.util.*;

import javax.persistence.*;
 
import play.data.validation.Required;
import play.db.jpa.*;

@Entity
public class TodoList extends Model {
	
	@Required
	@ManyToOne
	public User user;
	
	@Required
	public String title;	
	
	public Date addedAt;
	
	@OneToMany(mappedBy="task", cascade=CascadeType.ALL)
	public List<Todo> todoList;

	public TodoList(User user, String title) {
		this.user = user;
		this.title = title;
		this.addedAt = new Date();
		this.todoList = new ArrayList<Todo>();
	}
	
	public TodoList addTodo(String task, Date completionDate) {
		Todo todo = new Todo(task, completionDate, this).save();
		this.todoList.add(todo);
		this.save();
		
		return this;
	}
	
}
