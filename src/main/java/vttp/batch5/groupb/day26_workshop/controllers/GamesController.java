package vttp.batch5.groupb.day26_workshop.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import vttp.batch5.groupb.day26_workshop.services.GamesService;

@RestController
public class GamesController {
    
    @Autowired
    private GamesService gamesSvc;

    @GetMapping(path="/games", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getGamesByName(
        @RequestParam(name="limit", defaultValue="25") int limit,
        @RequestParam(name="offset", defaultValue="0") int offset) 
    {
        JsonObject respObj = gamesSvc.getGamesByName(limit, offset);

        return ResponseEntity.ok().body(respObj.toString());
    }

    @GetMapping(path="/games/rank", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getGamesByRank(
        @RequestParam(name="limit", defaultValue="25") int limit,
        @RequestParam(name="offset", defaultValue="0") int offset) 
    {
        JsonObject respObj = gamesSvc.getGamesByRank(limit, offset);

        return ResponseEntity.ok().body(respObj.toString());
    }

    @GetMapping(path="/game/{game_id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getGameById(
        @PathVariable(name="game_id") String gameId
    ) {
        Optional<JsonObject> opt = gamesSvc.getGameById(gameId);

        if (opt.isEmpty()) {
            JsonObjectBuilder objBuilder = Json.createObjectBuilder();
            objBuilder.add("message", "Game id does not exist.");
            String resp = objBuilder.build().toString();

            return ResponseEntity.status(404).body(resp);
        }

        JsonObject gameDetails = opt.get();

        return ResponseEntity.ok().body(gameDetails.toString());
    }
}
