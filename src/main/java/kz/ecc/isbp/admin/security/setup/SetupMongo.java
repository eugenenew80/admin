package kz.ecc.isbp.admin.security.setup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Singleton
@Startup
public class SetupMongo {
	
	@PostConstruct
	public void init() {
		MongoClient mongoClient = new MongoClient();
		MongoDatabase database = mongoClient.getDatabase("isbp");
		database.getCollection("permissions").drop();
		
		MongoCollection<Document> collection = database.getCollection("permissions");
		List<Document> docs = new ArrayList<>();
		
		Document document = new Document("user", "admin").append("appTopMenu", Arrays.asList("staff", "adm"));
		docs.add(document);
		
		document = new Document("user", "admin").append("admLeftMenu", Arrays.asList("users", "budgetVers", "intro"));
		docs.add(document);
		
		document = new Document("user", "admin").append("admUser", Arrays.asList("list", "search", "view", "edit", "viewPermissions", "editPermissions"));
		docs.add(document);
		
		collection.insertMany(docs);
		mongoClient.close();
	}
}
