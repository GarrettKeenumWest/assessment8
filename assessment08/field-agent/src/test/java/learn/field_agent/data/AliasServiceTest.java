package learn.field_agent.data;

import learn.field_agent.domain.AliasService;
import learn.field_agent.domain.Result;
import learn.field_agent.models.Agent;
import learn.field_agent.models.Alias;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;

import java.util.ArrayList;
import java.util.List;

import static learn.field_agent.TestHelper.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AliasServiceTest {

    @Autowired
    AliasService service;

    @MockBean
    AliasRepository repository;

    @Test
    void shouldFindAll() throws DataAccessException {
        List<Alias> toReturn = List.of(
                makeValidAlias(1),
                makeValidAlias(2));

        List<Alias> expected = List.of(
                makeValidAlias(1),
                makeValidAlias(2));

        when(repository.findAllAlias()).thenReturn(toReturn);
        List<Alias> actual = service.findAllAlias();
        assertEquals(expected.size(), actual.size());
    }

    @Test
    void shouldNotDeleteNonExisitng() throws DataAccessException {
        boolean success = service.deleteById(100);
        assertFalse(success);
    }

    @Test
    void shouldNotFindFromNonExistingAgent() throws DataAccessException {
        List<Alias> actual = service.findAllByAgent(10);
        List<Alias> expected = new ArrayList<>();
        assertEquals(actual, expected);
    }

    @Test
    void shouldNotFindAllIfNonExist() throws DataAccessException {
        List<Alias> actual = service.findAllAlias();
        List<Alias> expected = new ArrayList<>();
        assertEquals(actual, expected);
    }
}
