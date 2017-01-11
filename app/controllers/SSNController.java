package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Fulfillment;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.Counter;
import services.RestClient;

import javax.inject.Inject;
import java.io.IOException;

/**
 * Created by user on 10/01/17.
 */


public class SSNController extends Controller {


    @Inject
    public SSNController(Counter counter) {

    }

    /**
     * An action that responds with the {@link Counter}'s current
     * count. The result is plain text. This action is mapped to
     * <code>GET</code> requests with a path of <code>/count</code>
     * requests by an entry in the <code>routes</code> config file.
     */
//    public Result save() {
//        return ok(Integer.toString(counter.nextCount()));
//    }

    public Result save(){
        JsonNode data = request().body().asJson();
        JsonNode params = data.get("parameters");
        String cookingSpeed =  params.get("cooking-speed").asText();
        String  protein = params.get("protein").asText();
        String res = RestClient.getRecipe(protein + "," + cookingSpeed);

            return ok(res);

    }

    private String createResponse(String res) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(res);

        int count = actualObj.get("count").asInt();

        JsonNode hits = actualObj.get("hits");
        JsonNode recipe = actualObj.get("recipe");
        String chef = recipe.get("source").asText();
        String label = recipe.get("label").asText();


        Fulfillment f = new Fulfillment();
        f.setDisplaytext(label);
        f.setSource(chef);
        f.setSpeech("I found the " + label);

        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(f);



        return json;
    }


}