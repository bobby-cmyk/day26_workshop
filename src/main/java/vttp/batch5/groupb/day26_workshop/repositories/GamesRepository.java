package vttp.batch5.groupb.day26_workshop.repositories;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

@Repository
public class GamesRepository {

    @Autowired
    private MongoTemplate mongoTemplate;
    
    /*
     * db.games.find({})
        .sort({name : 1})
        .limit(25)
        .skip(0)
        .projection({name:1})
     * 
     */
    public List<JsonObject> getGamesByName(int limit, int offset) {

        Query query = new Query();

        Sort sortbyName = Sort.by(Sort.Direction.ASC, "name");

        query.with(sortbyName);
        
        query.limit(limit).skip(offset);

        query.fields()
            .include("name");

        List<Document> results = mongoTemplate.find(query, Document.class, "games");

        List<JsonObject> gameSummaries = new ArrayList<>();

        for (Document doc : results) {
            JsonObjectBuilder objBuilder = Json.createObjectBuilder();

            objBuilder.add("game_id", doc.getObjectId("_id").toString());
            objBuilder.add("name", doc.getString("name"));

            gameSummaries.add(objBuilder.build());
        }

        return gameSummaries;
    }

    /*
     * db.games.find({})
        .sort({ranking : 1})
        .limit(25)
        .skip(0)
        .projection({_id:0, gid:1, name:1})
     * 
     */

     public List<JsonObject> getGamesByRank(int limit, int offset) {

        Query query = new Query();

        Sort sortbyName = Sort.by(Sort.Direction.ASC, "ranking");

        query.with(sortbyName);
        
        query.limit(limit).skip(offset);

        query.fields()
            .include("name");

        List<Document> results = mongoTemplate.find(query, Document.class, "games");

        List<JsonObject> gameSummaries = new ArrayList<>();

        for (Document doc : results) {
            JsonObjectBuilder objBuilder = Json.createObjectBuilder();

            objBuilder.add("game_id", doc.getObjectId("_id").toString());
            objBuilder.add("name", doc.getString("name"));

            gameSummaries.add(objBuilder.build());
        }

        return gameSummaries;
    }

    /*
     * db.games.find({
        _id : ObjectId("67aaa2c23412e06c165eb50c")
        })
        .projection({
        gid:-1
        })
     * 
     * 
     */

     public Optional<JsonObject> getGameById(String gameId) {
        Query query = new Query();

        Criteria criteriaByGameId = Criteria.where("_id").is(gameId);

        query.addCriteria(criteriaByGameId);

        query.fields().exclude("gid");

        List<Document> results = mongoTemplate.find(query, Document.class, "games");

        if (results.isEmpty()) {
            return Optional.empty();
        }

        Document doc = results.get(0);

        JsonObjectBuilder objBuilder = Json.createObjectBuilder();
        objBuilder.add("game_id", doc.getObjectId("_id").toString());
        objBuilder.add("name", doc.getString("name"));
        objBuilder.add("year", doc.getInteger("year"));
        objBuilder.add("ranking", doc.getInteger("ranking"));
        objBuilder.add("users_rated", doc.getInteger("users_rated"));
        objBuilder.add("url", doc.getString("url"));
        objBuilder.add("thumbnail", doc.getString("image"));

        LocalDateTime now = LocalDateTime.now();
        long epoch = now.toEpochSecond(ZoneOffset.UTC);

        objBuilder.add("timestampe", epoch);

        return Optional.of(objBuilder.build());
     }
}
