package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.Controller;
import play.mvc.Result;
import services.Counter;

import javax.inject.Inject;

/**
 * Created by user on 10/01/17.
 */


public class SSNController extends Controller {

    private final Counter counter;

    @Inject
    public SSNController(Counter counter) {
        this.counter = counter;
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

            return ok(data);

    }


}