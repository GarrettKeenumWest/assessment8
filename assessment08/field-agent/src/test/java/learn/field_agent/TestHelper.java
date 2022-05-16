package learn.field_agent;

import learn.field_agent.domain.Result;
import learn.field_agent.domain.ResultType;
import learn.field_agent.models.Alias;

public class TestHelper {
    public static final int VALID_ID = 1;

    public static Result makeResult(String... messages){
        Result result = new Result();
        for (String message: messages){
            result.addMessage(message, ResultType.INVALID);
        }
        return result;
    }

    public static Alias makeValidAlias(int id) {
        Alias newAlias = new Alias();
        newAlias.setId(id);
        newAlias.setAliasName("Name");
        newAlias.setPersona("Persona");
        newAlias.setAgent_id(1);
        return newAlias;
    }
}
