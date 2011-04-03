package models;

import java.util.*;
import javax.persistence.*;
 
import play.data.validation.Required;
import play.db.jpa.*;

@Entity
public class Todo extends Model {
	
	@Lob
	@Required
	public String task;
	
	@Required
	public Date completionDate;

	public Date addedAt;	
	public boolean isCompleted;
	
	@ManyToOne
	@Required
	public TodoList todoList;
	
	public Todo(String task, Date completionDate, TodoList todoList) {
		this.task = task;
		this.todoList = todoList;
		this.isCompleted = false;
		this.completionDate = completionDate;
		this.addedAt = new Date();
	}

}
