package models;

import java.util.*;
import javax.persistence.*;
 
import play.data.validation.Email;
import play.data.validation.Required;
import play.db.jpa.*;

@Entity
public class User extends Model {

	@Email
	@Required
	public String email;
	
	@Required
	public String password;
	
	@Required
	public String fullname;
	
	public boolean isAdmin;
	public Date addedAt;
	
	public User(
			@Required String email, 
			@Required String password, 
			@Required String fullname) {
		this.email = email;
		this.password = password;
		this.fullname = fullname;
		this.isAdmin = false;
		this.addedAt = new Date();
	}
	
	public static User connect(
			@Required String email, 
			@Required String password) {
		return find("byEmailAndPassword", email, password).first();
	}
	
}
