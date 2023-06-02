package com.wordify.repository.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wordify.model.dto.EntryDto;
import com.wordify.model.dto.PhoneticDto;
import com.wordify.model.dto.WordDto;
import com.wordify.model.dto.DefinitionContextDto;
import com.wordify.model.dto.DefinitionDto;

public class DefinitionContextMapper {
    public DefinitionContextDto map(ResultSet resultSet) throws SQLException {
        DefinitionContextDto definitionContext = new DefinitionContextDto();

        List<DefinitionDto> definitions = new ArrayList<>();
        EntryDto prevEntry = null;
        EntryDto nextEntry = null;

        while (resultSet.next()) {
            int prevWordId = resultSet.getInt("prev_word_id");
            String prevWord = resultSet.getString("prev_word");
            int prevPhoneticId = resultSet.getInt("prev_phonetic_id");
            String prevPhonetic = resultSet.getString("prev_phonetic");

            int nextWordId = resultSet.getInt("next_word_id");
            String nextWord = resultSet.getString("next_word");
            int nextPhoneticId = resultSet.getInt("next_phonetic_id");
            String nextPhonetic = resultSet.getString("next_phonetic");

            if (prevEntry == null) {
                WordDto prevWordDto = new WordDto(prevWordId, prevWord);
                PhoneticDto prevPhoneticDto = new PhoneticDto(prevPhoneticId, prevPhonetic);
                prevEntry = new EntryDto(prevWordDto, prevPhoneticDto);
            }

            WordDto nextWordDto = new WordDto(nextWordId, nextWord);
            PhoneticDto nextPhoneticDto = new PhoneticDto(nextPhoneticId, nextPhonetic);
            nextEntry = new EntryDto(nextWordDto, nextPhoneticDto);
            //DefinitionDtoを作って、user_idと一緒にsetしてやらんばいけん。
            DefinitionDto definition = new DefinitionDto();
            definition.setId(resultSet.getInt("definition_id"));
            definition.setUser_id(resultSet.getInt("user_id"));
            definitions.add(definition);
        }

        definitionContext.setPrev_entry(prevEntry);
        definitionContext.setNext_entry(nextEntry);
        definitionContext.setDefinitionIds(definitions);

        return definitionContext;
    }
}
