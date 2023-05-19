package DataBases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;


import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class IOTDataBase {

	public static int addCompany(int company_ID, String company_name, String address, String phone_number) throws SQLException{
		Connection conn = ConnectionTools.getMongoDBConnection();
		
		Statement st = conn.createStatement();
		int rows = 5;

		try (PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO companies (company_ID, company_name, address, phone_number) VALUES (?, ?, ?, ?)")) {
            preparedStatement.setObject(1, company_ID);
            preparedStatement.setObject(2, company_name);
            preparedStatement.setObject(3, address);
            preparedStatement.setObject(4, phone_number);
            rows = preparedStatement.executeUpdate();
              
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
		
		st.close();
		conn.close();
		
		return rows;
	}
	
	public static int mongoDBaddCompany(int company_ID, String company_name, String address, String phone_number) {
	    // Connect to MongoDB
	    String connectionString = "mongodb://localhost:27017/IOTDataBase";
	    MongoClientURI uri = new MongoClientURI(connectionString);
	    MongoClient mongoClient = new MongoClient(uri);

	    // Access the database
	    MongoDatabase database = mongoClient.getDatabase("IOTDataBase");

	    // Check if the collection exists
	    String collectionName = String.valueOf(company_ID);
	    if (!database.listCollectionNames().into(new ArrayList<>()).contains(collectionName)) {
	        // Create the collection if it doesn't exist
	        database.createCollection(collectionName);
	    }

	    // Access the collection
	    MongoCollection<Document> collection = database.getCollection(collectionName);

	    // Insert the document
	    Document document = new Document("company_id", company_ID)
	            .append("company_name", company_name)
	            .append("address", address)
	            .append("phone_number", phone_number);
	    collection.insertOne(document);

	    System.out.println("Document " + document.toString());

	    return 1;
	}
	
	public static int mongoDBaddProduct(int company_ID, String product_id, String product_name, int price) {
	    // Connect to MongoDB
	    String connectionString = "mongodb://localhost:27017/IOTDataBase";
	    MongoClientURI uri = new MongoClientURI(connectionString);
	    MongoClient mongoClient = new MongoClient(uri);

	    // Access the database
	    MongoDatabase database = mongoClient.getDatabase("IOTDataBase");

	    // Check if the collection exists
	    String collectionName = String.valueOf(company_ID);
	    if (!database.listCollectionNames().into(new ArrayList<>()).contains(collectionName)) {
	        // Collection does not exist, do nothing
	        return 0;
	    }

	    // Access the collection
	    MongoCollection<Document> collection = database.getCollection(collectionName);

	    // Insert the document
	    Document document = new Document("product_id", product_id)
	            .append("company_ID", company_ID)
	            .append("product_name", product_name)
	            .append("price", price);
	    collection.insertOne(document);

	    System.out.println("Document " + document.toString());

	    return 1;
	}
}
