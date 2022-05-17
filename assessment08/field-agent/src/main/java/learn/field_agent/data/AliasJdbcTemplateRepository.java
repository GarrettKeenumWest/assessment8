package learn.field_agent.data;

import learn.field_agent.data.mappers.AliasMapper;
import learn.field_agent.models.Alias;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class AliasJdbcTemplateRepository implements AliasRepository {
    private final JdbcTemplate jdbcTemplate;

    public AliasJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Alias> findAllAlias(){
        final String sql = "select * from alias;";
        List<Alias> result = jdbcTemplate.query(sql, new AliasMapper());
        if (result.size() == 0){
            return null;
        }
        return result;
    }

    @Override
    @Transactional
    public List<Alias> findAllByAgentId(int agent_id){
        final String sql = "select * "
                + "from alias "
                + "where agent_id = ?;";
        List<Alias> result = jdbcTemplate.query(sql, new AliasMapper(), agent_id);
        if (result.size() == 0){
            return null;
        }
        return result;
    }

    @Override
    public Alias addAliasToAgent(Alias alias){
        String sql = "insert into alias "
                + "(`name`, persona, agent_id) "
                + "values (?, ?, ?);";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, alias.getAliasName());
            statement.setString(2, alias.getPersona());
            statement.setInt(3, alias.getAgent_id());
            return statement;
        }, keyHolder);

        if (rowsAffected > 0){
            alias.setId(keyHolder.getKey().intValue());
        }

        return alias;
    }

    @Override
    public boolean update(Alias alias) {
        String sql = "update alias set "
                + "`name` = ?, "
                + "persona = ? "
                + "where alias_id = ?;";

        int rowsAffected = jdbcTemplate.update(sql,
                alias.getAliasName(),
                alias.getPersona(),
                alias.getId());

        return rowsAffected > 0;
    }

    @Override
    @Transactional
    public boolean deleteById(int id){
        String sql = "delete from alias where alias_id = ?;";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }
}
