package learn.field_agent.domain;

import learn.field_agent.data.AgentRepository;
import learn.field_agent.data.AliasRepository;
import learn.field_agent.models.Agent;
import learn.field_agent.models.Alias;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AliasService {

    private final AliasRepository aliasRepository;
    private final AgentRepository agentRepository;

    public AliasService(AliasRepository repository, AgentRepository repository2){
        this.aliasRepository = repository;
        this.agentRepository = repository2;
    }

    public List<Alias> findAllAlias() {
        return aliasRepository.findAllAlias();
    }

    public List<Alias> findAllByAgent(int agent_id){
        return aliasRepository.findAllByAgentId(agent_id);
    }

    public Result<Alias> addAlias(Alias alias){
        Result<Alias> result = validateAlias(alias.getAgent_id(), alias);
        if (!result.isSuccess()) {
            return result;
        }
        if (alias.getId() != 0) {
            result.addMessage("Alias Id cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }
        alias = aliasRepository.addAliasToAgent(alias);
        result.setPayload(alias);
        return result;
    }

    public Result<Alias> update(Alias alias) {
        Result<Alias> result = validateAlias(alias.getAgent_id(), alias);
        if (!result.isSuccess()) {
            return result;
        }
        if (!aliasRepository.update(alias)) {
            String msg = String.format("AliasId: %s, not found", alias.getId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }
        return result;
    }

    public boolean deleteById(int alias_id){
        return aliasRepository.deleteById(alias_id);
    }

    private Result<Alias> validateAlias(int agent_id, Alias alias){
        Result<Alias> result = new Result<>();
        if (agentRepository.findById(agent_id) == null) {
            result.addMessage("Agent Cannot Be Null", ResultType.INVALID);
            return result;
        }
        if (alias == null) {
            result.addMessage("Alias Cannot Be Null", ResultType.INVALID);
            return result;
        }
        if (alias.getAliasName() == null){
            result.addMessage("Alias Must Include Name", ResultType.INVALID);
            return result;
        }
        List<Agent> agents = agentRepository.findAll();
        if (agents.size() == 0){
            result.addMessage("No Agents Found", ResultType.INVALID);
            return result;
        }
        for (Agent a: agents) {
            List<Alias> agentAliases = aliasRepository.findAllByAgentId(a.getAgentId());
            if (agentAliases == null){
                break;
            }
            for (Alias al: agentAliases) {
                if (Objects.equals(al.getAliasName(), alias.getAliasName())){
                    if (alias.getPersona() == null || Objects.equals(alias.getPersona(), al.getPersona())){
                        result.addMessage("Duplicate Aliases Must Have Unique Persona", ResultType.INVALID);
                        return result;
                    }
                }
            }
        }
        return result;
    }
}
