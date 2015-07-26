package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import model.User;
import play.Logger;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

public class UserController extends Controller {
    public Result getPerson() {
        try {
            throw new RuntimeException("Internal Server Error");
        } catch (Exception e) {
            return internalServerError(e.getMessage());
        }
    }

    public Result getPersonById(Long id) {
        // DEBUG
        Logger.info("GET on id: " + id);
        ObjectNode result = Json.newObject();
        User user = new User(id, "First" + id, "Last " + id, id.intValue());
        if (user == null) {
            return notFound(); // 404
        } else {
            result.set("person", Json.toJson(user));
            return ok(result);

        }
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result storePerson() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            System.err.println("POST Data");
            JsonNode json = request().body().asJson();
            System.err.println("json payload: " + json);
            User newPerson = mapper.readValue(json.toString(), User.class);
            ObjectNode result = Json.newObject();
            result.set("person", Json.toJson(newPerson));
            return created(result);
        } catch (Exception e) {
            e.printStackTrace();
            return badRequest("Missing information");
        }

    }
}
