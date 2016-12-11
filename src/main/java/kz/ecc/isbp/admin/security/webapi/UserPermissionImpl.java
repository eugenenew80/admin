package kz.ecc.isbp.admin.security.webapi;

import java.util.ArrayList;
import static java.util.stream.Collectors.*;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;


@RequestScoped
@Path("/userPermissions")
@Produces({"application/json;charset=utf-8", "application/xml;charset=utf-8"})
@Consumes({"application/json;charset=utf-8", "application/xml;charset=utf-8"})
public class UserPermissionImpl {

	@GET
	public Response getAll() {
		try(MongoClient mongoClient = new MongoClient()) {
			MongoDatabase db = mongoClient.getDatabase("isbp");
			String str = db.getCollection("permissions").find(new Document("user", "admin"))
				.into(new ArrayList<>())
				.stream()
				.map(Document::toJson)
				.collect(joining(",", "[", "]"));

			return Response.ok()
					.entity(str)	
					.build();		
		}	
	}
}
