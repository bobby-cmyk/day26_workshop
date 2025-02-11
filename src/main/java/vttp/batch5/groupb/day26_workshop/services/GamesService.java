package vttp.batch5.groupb.day26_workshop.services;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import vttp.batch5.groupb.day26_workshop.repositories.GamesRepository;

@Service
public class GamesService {
    
    @Autowired
    private GamesRepository gamesRepo;

    public JsonObject getGamesByName(int limit, int offset) {

        List<JsonObject> gameSummaries = gamesRepo.getGamesByName(limit, offset);
        
        JsonObjectBuilder objBuilder = Json.createObjectBuilder();

        objBuilder.add("offset", offset);
        objBuilder.add("limit", limit);
        objBuilder.add("total", gameSummaries.size());

        LocalDateTime now = LocalDateTime.now();
        long epoch = now.toEpochSecond(ZoneOffset.UTC);
        objBuilder.add("timestamp", epoch);

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();

        for (JsonObject gameObj : gameSummaries) {
            arrBuilder.add(gameObj);
        }

        objBuilder.add("games", arrBuilder.build());

        return objBuilder.build();
    }
    
    public JsonObject getGamesByRank(int limit, int offset) {

        List<JsonObject> gameSummaries = gamesRepo.getGamesByName(limit, offset);
        
        JsonObjectBuilder objBuilder = Json.createObjectBuilder();

        objBuilder.add("offset", offset);
        objBuilder.add("limit", limit);
        objBuilder.add("total", gameSummaries.size());

        LocalDateTime now = LocalDateTime.now();
        long epoch = now.toEpochSecond(ZoneOffset.UTC);
        objBuilder.add("timestamp", epoch);

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();

        for (JsonObject gameObj : gameSummaries) {
            arrBuilder.add(gameObj);
        }

        objBuilder.add("games", arrBuilder.build());

        return objBuilder.build();
    }
}
