package controllers;

import play.*;
import play.mvc.*;
import models.*;
import views.html.*;
import play.data.*;
import static play.data.Form.*;

public class Application extends Controller {

    @Security.Authenticated(Secured.class)
    public static Result index() {
        return ok(index.render(
        Project.findInvolving(request().username()), 
        Task.findTodoInvolving(request().username()),
        User.find.byId(request().username())
    	));
    }
    public static Result login(){
        return ok(login.render(Form.form(Login.class)));
    }

    public static Result authenticate() {
	    Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
	    if (loginForm.hasErrors()) {
        	return badRequest(login.render(loginForm));
    	} else {
	        session().clear();
	        session("email", loginForm.get().email); 
// public Http.Session(java.util.Map<java.lang.String,java.lang.String> data)
	        return redirect(
	            routes.Application.index()
	        );
    	}
	}

	public static Result logout() {
	    session().clear();
	    flash("success", "You've been logged out");
	    return redirect(
	        routes.Application.login()
	    );
	}

	public static class Login {

	    public String email;
	    public String password;

	    public String validate() {
	    	if (User.authenticate(email, password) == null) {
	      		return "Invalid user or password";
	    	}
	    	return null;
		}
	}

}

