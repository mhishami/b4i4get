import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class BasicTest extends UnitTest {

    @Before
    public void setup() {
        Fixtures.deleteAll();
    }
    
    @Test
    public void aVeryImportantThingToTest() {
        assertEquals(2, 1 + 1);
    }

    @Test
    public void createAndRetrieveUser() {
        // Create a new user and save it
        new User("bob@gmail.com", "secret", "Bob").save();
        
        // Retrieve the user with e-mail address bob@gmail.com
        User bob = User.find("byEmail", "bob@gmail.com").first();
        
        // Test 
        assertNotNull(bob);
        assertEquals("Bob", bob.fullname);
    }
    
    @Test
    public void tryConnectAsUser() {
        // Create a new user and save it
        new User("bob@gmail.com", "secret", "Bob").save();
        
        // Test 
        assertNotNull(User.connect("bob@gmail.com", "secret"));
        assertNull(User.connect("bob@gmail.com", "badpassword"));
        assertNull(User.connect("tom@gmail.com", "secret"));
    }
    
    @Test
    public void createTodoList() {
    	User bob = new User("bob@gmail.com", "secret", "Bob").save();
    	
    	new TodoList(bob, "Home chores").save();
    	
    	// test them
    	assertEquals(1, TodoList.count());
    	
    	// retrieve all todoList by bob
    	List<TodoList> bobList = TodoList.find("byUser", bob).fetch();
    	
    	assertEquals(1, bobList.size());
    	TodoList firstList = bobList.get(0);
    	assertNotNull(firstList);
    	assertEquals(bob, firstList.user);
    	assertNotNull(firstList.addedAt);    	
    }
    
    @Test
    public void createTodo() {
    	User bob = new User("bob@gmail.com", "secret", "Bob").save();    	
    	TodoList bobList = new TodoList(bob, "Some Tasks").save();
    	
    	// create a few todos
    	new Todo("Bake a cake", new Date(), bobList).save();
    	new Todo("Make dinner", new Date(), bobList).save();
    	
    	// Retrieve all todos
    	List<Todo> todos = Todo.find("byTodoList", bobList).fetch();
    	
    	// Tests
    	assertEquals(2, todos.size());
    	
    	// test first one
    	Todo firstTodo = todos.get(0);
    	assertNotNull(firstTodo);
    	assertEquals("Bake a cake", firstTodo.task);
    	assertEquals(bobList, firstTodo.todoList);
    	assertEquals(false, firstTodo.isCompleted);
    	
    	// test the second one
    	Todo secondTodo = todos.get(1);
    	assertNotNull(firstTodo);
    	assertEquals("Make dinner", secondTodo.task);
    	assertEquals(bobList, secondTodo.todoList);
    	assertEquals(false, secondTodo.isCompleted);
    	
    }
}
