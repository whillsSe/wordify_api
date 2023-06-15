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
import com.wordify.model.dto.DefinitionRequestDto;
import com.wordify.model.dto.DefinitionTagDto;
import com.wordify.model.dto.EntryDto;
import com.wordify.model.dto.ExampleDto;
import com.wordify.model.dto.MeaningDto;
import com.wordify.model.dto.QueryDto;
import com.wordify.model.dto.SearchRequestDto;
import com.wordify.model.dto.TagDto;
import com.wordify.repository.DatabaseConnection;
import com.wordify.repository.dao.DefinitionContextDao;
import com.wordify.repository.dao.DefinitionTagDao;
import com.wordify.repository.dao.EntryDao;
import com.wordify.repository.dao.ExampleDao;
import com.wordify.repository.dao.MeaningDao;

public class EntryService {
    public List<EntryDto> getEntriesByQuery(SearchRequestDto searchRequest) throws SQLException, ClassNotFoundException{
        List<EntryDto> list = new ArrayList<>();
        DatabaseConnection DbConn = DatabaseConnection.getInstance();
        EntryDao dao = new EntryDao();
        //ここで、userのmodeに合わせてuserIdsを編集したりせんとアカンかも？
        try(Connection conn = DbConn.getConnection()){
            list = dao.getEntryByQuery(conn, searchRequest);
        }catch(SQLException e){
            throw e;
        }
        return list;
    }
    public DefinitionContextDto getEntryDetails(DefinitionRequestDto requestDto) throws SQLException, ClassNotFoundException{
        DefinitionContextDto definitionContext;
        DatabaseConnection DbConn = DatabaseConnection.getInstance();
        Connection conn = DbConn.getConnection();
        try {
            // Start transaction
            conn.setAutoCommit(false);
            try {
                DefinitionContextDao definitionContextDao = new DefinitionContextDao();
                // Get DefinitionContext from DefinitionContextDao
                definitionContext = definitionContextDao.getDefinitionContextByQuery(conn, requestDto);
                // Get set of definition IDs from DefinitionContext
                Set<Integer> definitionIds = definitionContext.getDefinitions().stream()
                        .map(DefinitionDto::getId)
                        .collect(Collectors.toSet());
    
                // Fetch definition elements for each definition ID
                ExampleDao exampleDao = new ExampleDao();
                MeaningDao meaningDao = new MeaningDao();
                DefinitionTagDao definitionsTagDao = new DefinitionTagDao();
                Map<Integer, List<ExampleDto>> examples = exampleDao.getElementMapByDefinitionIds(conn, definitionIds);
                Map<Integer, List<MeaningDto>> meanings = meaningDao.getElementMapByDefinitionIds(conn, definitionIds);
                Map<Integer, List<TagDto>> tags = definitionsTagDao.getElementMapByDefinitionIds(conn, definitionIds);
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