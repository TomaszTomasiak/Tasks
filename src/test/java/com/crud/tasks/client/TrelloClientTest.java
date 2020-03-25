package com.crud.tasks.client;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.config.TrelloConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloClientTest {

    private String endpoint = "http://test.com";
    private String test = "test";
    private String name = "Test task";
    private String desc = "Test description";
    private String pos = "top";
    private String id = "test_id";

    @InjectMocks
    private TrelloClient trelloClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private TrelloConfig trelloConfig;

    @Before
    public void init() {
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn(endpoint);
        when(trelloConfig.getTrelloAppKey()).thenReturn(test);
        when(trelloConfig.getTrelloToken()).thenReturn(test);
        when(trelloConfig.getUserName()).thenReturn(test);
    }

    @Test
    public void shouldFetchTrelloBoards() throws URISyntaxException {
        //Given
        TrelloBoardDto[] trelloBoards = new TrelloBoardDto[1];
        trelloBoards[0] = new TrelloBoardDto("test_id", "test_board", new ArrayList<>());

        when(restTemplate.getForObject(urlTrelloBoards(), TrelloBoardDto[].class)).thenReturn(trelloBoards);

        //When
        List<TrelloBoardDto> fetchedTrelloBoards = trelloClient.getTrelloBoards();

        //Then
        assertEquals(1, fetchedTrelloBoards.size());
        assertEquals("test_id", fetchedTrelloBoards.get(0).getId());
        assertEquals("test_board", fetchedTrelloBoards.get(0).getName());
        assertEquals(new ArrayList<>(), fetchedTrelloBoards.get(0).getLists());
    }

    @Test
    public void shouldCreateCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto(name, desc, pos, id);
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("1", name, "http://test.com");

        when(restTemplate.postForObject(urlCreateCard(), null, CreatedTrelloCardDto.class)).thenReturn(createdTrelloCardDto);

        //When
        CreatedTrelloCardDto newCard = trelloClient.createNewCard(trelloCardDto);

        //Then
        assertEquals("1", newCard.getId());
        assertEquals(name, newCard.getName());
        assertEquals("http://test.com", newCard.getShortUrl());
    }

    @Test
    public void shouldReturnEmptyList() {
        //Given

        when(restTemplate.getForObject(urlTrelloBoards(), TrelloBoardDto[].class)).thenReturn(null);

        //When
        List<TrelloBoardDto> fetchedTrelloBoards = trelloClient.getTrelloBoards();

        //Then
        assertEquals(0, fetchedTrelloBoards.size());

    }


    private URI urlTrelloBoards() {
        return UriComponentsBuilder.fromHttpUrl(endpoint + "/members/" + test + "/boards")
                .queryParam("key", test)
                .queryParam("token", test)
                .queryParam("fields", "name,id")
                .queryParam("lists", "all").build().encode().toUri();
    }

    private URI urlCreateCard() {
        return UriComponentsBuilder.fromHttpUrl(endpoint + "/cards")
                .queryParam("key", test)
                .queryParam("token", test)
                .queryParam("name", name)
                .queryParam("desc", desc)
                .queryParam("pos", pos)
                .queryParam("idList", "test_id").build().encode().toUri();
    }
}