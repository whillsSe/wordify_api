package com.wordify.service.api;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wordify.model.dto.EntryRegistrationDto;
import com.wordify.model.dto.TagDto;
import com.wordify.repository.DatabaseConnection;
import com.wordify.repository.dao.CollectionDao;
import com.wordify.repository.dao.DefinitionDao;
import com.wordify.repository.dao.DefinitionTagDao;
import com.wordify.repository.dao.ExampleDao;
import com.wordify.repository.dao.MeaningDao;
import com.wordify.repository.dao.PhoneticDao;
import com.wordify.repository.dao.TagDao;
import com.wordify.repository.dao.WordDao;

public class RegisterService {
    public void registerEntry(EntryRegistrationDto requestEntry) throws SQLException, ClassNotFoundException{
        DatabaseConnection DbConn = DatabaseConnection.getInstance();
        Connection conn = DbConn.getConnection();
         try {
            // Start transaction
            conn.setAutoCommit(false);
                //word・phonetic
                WordDao wDao = new WordDao();
                PhoneticDao pDao = new PhoneticDao();
                TagDao tDao = new TagDao();
                int wId = wDao.getId(conn, requestEntry.getWord());
                int pId = pDao.getId(conn, requestEntry.getPhonetic());
                //tagの存在チェック//省略
                List<TagDto> tagDtos = tDao.retribeTags(conn,requestEntry.getTags());
                List<Integer> tagIds = new ArrayList<>();
                for(TagDto tag:tagDtos){
                    tagIds.add(tag.getId());
                };
                System.out.println(tagIds);
                //definitionの登録
                DefinitionDao dDao = new DefinitionDao();
                int dId = dDao.createDefinition(conn, wId, pId, requestEntry.getUserId());
                CollectionDao cDao = new CollectionDao();
                cDao.RegisterCollection(conn,dId,requestEntry.getUserId());
                //meaningの登録
                if(requestEntry.getMeaning() != ""){
                    MeaningDao mDao = new MeaningDao();
                    mDao.RegisterElement(conn, requestEntry.getMeaning(), dId);
                }
                //exampleの登録
                if(requestEntry.getExample() != ""){
                    ExampleDao eDao = new ExampleDao();
                    eDao.RegisterElement(conn, requestEntry.getExample(), dId);
                }
                //meaning_tagの登録//省略
                if(tagIds.size() > 0){
                    DefinitionTagDao dtDao = new DefinitionTagDao();
                    dtDao.RegisterElement(conn, dId,tagIds);
                }
            conn.commit();
         }catch (SQLException e){
            System.out.println("RegisterServiceでCatchした");
            System.out.println(e.getMessage());
         }
    }
}
