package book.api;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public abstract class WeatherLambdaBase {
    protected static final String tableName = System.getenv("LOCATIONS_TABLE");
    protected static final String DEFAULT_LIMIT = "50";
    protected static final String DEFAULT_LOCATION = "Lviv";

    protected ObjectMapper objectMapper =  new ObjectMapper();
    protected final AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.defaultClient();
    protected final DynamoDB dynamoDB = new DynamoDB(AmazonDynamoDBClientBuilder.defaultClient());

    public abstract ApiGetawayResponse handler(ApiGetawayRequest request) throws IOException;

}
