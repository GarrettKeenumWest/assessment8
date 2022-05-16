package learn.field_agent.data;

import learn.field_agent.models.Agent;
import learn.field_agent.models.Alias;

import java.util.List;

public interface AliasRepository {
    List<Alias> findAllAlias();

    List<Alias> findAllByAgentId(int agent_id);

    Alias addAliasToAgent(Alias alias);

    boolean update(Alias alias);

    boolean deleteById(int id);
}
