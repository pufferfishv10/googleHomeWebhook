package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

    public Result save() throws IOException {
        JsonNode data = request().body().asJson();


        JsonNode result = data.get("result");
        JsonNode params = result.get("parameters");



        String cookingSpeed =  params.get("cooking-speed").asText();
        String  protein = params.get("protein").asText();
        String res = RestClient.getRecipe(protein);



/*
        ///////////////
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(res);

        int count = actualObj.get("count").asInt();

        JsonNode hits = actualObj.get("hits");
        JsonNode zero = hits.path(0);
        JsonNode recipe = zero.get("recipe");

        String chef = recipe.get("source").asText();
        String label = recipe.get("label").asText();*/

        JsonNode fulfillment = result.get("fulfillment");

        ((ObjectNode) fulfillment).put("speech", "Today in Boston: Fair, the temperature is 37 F");

        /*"speech": "Today in Boston: Fair, the temperature is 37 F",
                "source": "apiai-weather-webhook-sample",
                "displayText": "Today in Boston: Fair, the temperature is 37 F"
*/

        /*Fulfillment f = new Fulfillment();
        f.setDisplaytext(label + "cooking speed is"+ cookingSpeed );
        f.setSource(chef);
        f.setSpeech("I found the " + label);

        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(f);*/
        /////////////////

            return ok(data);

    }

    private String createResponse(String res) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(res);

        int count = actualObj.get("count").asInt();

        JsonNode hits = actualObj.get("hits");
        JsonNode recipe = hits.get("recipe");
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