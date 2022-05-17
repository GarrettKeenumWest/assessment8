package learn.field_agent.models;

import java.time.LocalDate;

public class Alias {
    private int id;
    private String aliasName;
    private String persona;
    private int agent_id;

    public Alias(int id, String aliasName, String persona, int agent_id) {
        this.id = id;
        this.aliasName = aliasName;
        this.persona = persona;
        this.agent_id = agent_id;
    }

    public Alias() {
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(int agent_id) {
        this.agent_id = agent_id;
    }

}
