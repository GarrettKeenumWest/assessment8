package learn.field_agent.data.mappers;

import learn.field_agent.models.Agent;
import learn.field_agent.models.Alias;
import org.springframework.jdbc.core.RowMapper;

import javax.swing.tree.TreePath;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AliasMapper implements RowMapper<Alias> {

    @Override
    public Alias mapRow(ResultSet resultSet, int i) throws SQLException {
        Alias alias = new Alias();
        alias.setId(resultSet.getInt("alias_id"));
        alias.setAliasName(resultSet.getString("name"));
        if (resultSet.getString("persona") != null){
            alias.setPersona(resultSet.getString("persona"));
        }
        alias.setAgent_id(resultSet.getInt("agent_id"));

        return alias;
    }
}
