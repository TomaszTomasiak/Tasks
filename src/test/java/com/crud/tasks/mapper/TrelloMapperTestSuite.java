package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloMapperTestSuite {

    @Autowired
    TrelloMapper trelloMapper;

    @Test
    public void mapToLists() {

        //Given
        List<TrelloListDto> trelloListDtoList = new ArrayList<>();
        trelloListDtoList.add(new TrelloListDto("51", "list", true));

        //When
        List<TrelloList> trelloLists = trelloMapper.mapToLists(trelloListDtoList);

        //Then
        assertEquals(1, trelloListDtoList.size());
        assertTrue(trelloLists.contains(new TrelloList("51", "list", true)));
    }

    @Test
    public void mapToListsDto() {
        //Given
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloList("51", "list", true));

        //When
        List<TrelloListDto> trelloListsDto = trelloMapper.mapToListsDto(trelloLists);

        //Then
        assertEquals(1, trelloLists.size());
        assertTrue(trelloListsDto.contains(new TrelloListDto("51", "list", true)));
    }

    @Test
    public void mapToBoardsDto() {
        //Given
        TrelloList trelloList = new TrelloList("51", "list", true);
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(trelloList);

        TrelloBoard trelloBoard = new TrelloBoard("87", "board1", trelloLists);
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(trelloBoard);

        //When
        List<TrelloBoardDto> trelloBoardDtos = trelloMapper.mapToBaordsDto(trelloBoards);

        //Then
        assertEquals(1, trelloBoards.size());
        assertTrue(trelloBoardDtos.contains(new TrelloBoardDto("87", "board1", Arrays.asList(new TrelloListDto("51", "list", true)))));
    }

    @Test
    public void mapToBoards() {
        //Given
        TrelloListDto trelloListDto = new TrelloListDto("92", "theList", true);
        List<TrelloListDto> trelloListDtos = new ArrayList<>();
        trelloListDtos.add(trelloListDto);

        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("74", "theBoard", trelloListDtos);
        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        trelloBoardDtos.add(trelloBoardDto);

        //When
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloBoardDtos);

        //Then
        assertEquals(1, trelloBoardDtos.size());
        assertTrue(trelloBoards.contains(new TrelloBoard("74", "theBoard", Arrays.asList(new TrelloList("92", "theList", true)))));
    }

    @Test
    public void mapToCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard("cardName", "cardDescription", "position", "25");

        //When
        TrelloCardDto result = trelloMapper.mapToCardDto(trelloCard);

        //Then
        assertTrue(result.equals(new TrelloCardDto("cardName", "cardDescription", "position", "25")));

    }

    @Test
    public void mapToCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("cardName", "cardDescription", "position", "25");

        //When
        TrelloCard result = trelloMapper.mapToCard(trelloCardDto);

        //Then
        assertTrue(result.equals(new TrelloCard("cardName", "cardDescription", "position", "25")));

    }
}
