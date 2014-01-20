package controllers;

import play.*;
import play.mvc.*;
import models.*;
import views.html.*;
import play.data.*;
import static play.data.Form.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render(Project.find.all(),Task.find.all()));
    }

}
