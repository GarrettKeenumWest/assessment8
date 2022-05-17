package learn.field_agent.controllers;

import learn.field_agent.domain.AliasService;
import learn.field_agent.domain.Result;
import learn.field_agent.models.Agent;
import learn.field_agent.models.Alias;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/alias")
public class AliasController {
    private final AliasService service;

    public AliasController(AliasService service) {
        this.service = service;
    }

    @GetMapping
    public List<Alias> findAllAliases(){
        return service.findAllAlias();
    }

    @GetMapping("/agent/{agent_id}")
    public List<Alias> findAllByAgent(@PathVariable int agent_id){
        return service.findAllByAgent(agent_id);
    }

    @PostMapping("/agent/{agent_id}")
    public ResponseEntity<Object> add(@PathVariable int agent_id, @RequestBody Alias alias) {
        if (agent_id != alias.getAgent_id()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Result<Alias> result = service.addAlias(alias);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{alias_id}")
    public ResponseEntity<Object> update(@PathVariable int alias_id, @RequestBody Alias alias) {
        if (alias_id != alias.getId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Alias> result = service.update(alias);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{alias_id}")
    public ResponseEntity<Void> deleteById(@PathVariable int alias_id) {
        if (service.deleteById(alias_id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
