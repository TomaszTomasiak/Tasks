package com.crud.tasks.client;

import com.crud.tasks.domain.TrelloBoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class TrelloClient {

    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;

    @Value("${trello.app.key}")
    private String trelloAppKey;

    @Value("${trello.app.token}")
    private String trelloToken;

    @Value("${trello.app.username}")
    private String userName;

    private URI url() {
        return UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/members/" + userName + "/boards")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("fields", "name,id")
                .queryParam("lists", "all").build().encode().toUri();
    }



    @Autowired
    private RestTemplate restTemplate;

    public List<TrelloBoardDto> getTrelloBoards() {

        TrelloBoardDto[] boardsResponse = restTemplate.getForObject(url(), TrelloBoardDto[].class);

        Optional<TrelloBoardDto[]> optional = Optional.of(boardsResponse);


        if(optional.isPresent()) {
            return Arrays.asList(boardsResponse);
        }
        return new ArrayList<>();
    }
}
