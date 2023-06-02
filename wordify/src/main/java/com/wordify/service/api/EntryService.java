package com.wordify.service.api;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.wordify.model.dto.DefinitionContextDto;
import com.wordify.model.dto.DefinitionDto;
import com.wordify.model.dto.EntryDto;
import com.wordify.model.dto.ExampleDto;
import com.wordify.model.dto.MeaningDto;
import com.wordify.model.dto.QueryDto;
import com.wordify.model.dto.TagDto;
import com.wordify.repository.DatabaseConnection;
import com.wordify.repository.dao.DefinitionContextDao;
import com.wordify.repository.dao.EntryDao;
import com.wordify.repository.dao.ExampleDao;
import com.wordify.repository.dao.MeaningDao;
import com.wordify.repository.dao.TagsDao;

public class EntryService {
    public List<EntryDto> getEntriesByQuery(QueryDto query) throws SQLException{
        List<EntryDto> list = new ArrayList<>();
        DatabaseConnection DbConn = DatabaseConnection.getInstance();
        EntryDao dao = new EntryDao();
        try(Connection conn = DbConn.getConnection()){
            list = dao.getEntryByQuery(conn, query);
        }catch(SQLException e){
            throw e;
        }
        return list;
    }
    public DefinitionContextDto getEntryDetails(QueryDto query) throws SQLException{
        DefinitionContextDto definitionContext;
        DatabaseConnection DbConn = DatabaseConnection.getInstance();
        try (Connection conn = DbConn.getConnection()) {
            // Start transaction
            conn.setAutoCommit(false);
            try {
                DefinitionContextDao definitionContextDao = new DefinitionContextDao();
                // Get DefinitionContext from DefinitionContextDao
                definitionContext = definitionContextDao.getDefinitionContextByQuery(conn, query);
                // Get set of definition IDs from DefinitionContext
                Set<Integer> definitionIds = definitionContext.getDefinitions().stream()
                        .map(DefinitionDto::getId)
                        .collect(Collectors.toSet());
    
                // Fetch definition elements for each definition ID
                ExampleDao exampleDao = new ExampleDao();
                MeaningDao meaningDao = new MeaningDao();
                TagsDao tagsDao = new TagsDao();
                Map<Integer, List<ExampleDto>> examples = exampleDao.getElementMapByDefinitionIds(conn, definitionIds);
                Map<Integer, List<MeaningDto>> meanings = meaningDao.getElementMapByDefinitionIds(conn, definitionIds);
                Map<Integer, List<TagDto>> tags = tagsDao.getElementMapByDefinitionIds(conn, definitionIds);
                // Combine result sets and populate DefinitionContextDto
                // Assuming you have appropriate setters in your DefinitionContextDto
                for (DefinitionDto def : definitionContext.getDefinitions()) {
                    int id = def.getId();
                    def.setExamples(examples.get(id));
                    def.setMeanings(meanings.get(id));
                    def.setTags(tags.get(id));
                }
                // If everything is successful, commit the transaction
                conn.commit();
            } catch (Exception e) {
                // If anything goes wrong, rollback the transaction
                conn.rollback();
                // Rethrow the exception to the caller
                throw e;
            }
            return definitionContext;
        } catch (SQLException e) {
            // Handle exception - log it, wrap it in a runtime exception, or handle it in another appropriate way for your application
            throw e;
        }
    }
}
