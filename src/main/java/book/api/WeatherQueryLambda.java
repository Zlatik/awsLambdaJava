package book.api;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class WeatherQueryLambda {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AmazonDynamoDB dynamoDb = AmazonDynamoDBClientBuilder.defaultClient();
    private final String tableName = System.getenv("LOCATIONS_TABLE");
    private static final String DEFAULT_LIMIT = "50";

    public ApiGetawayResponse handler(ApiGetawayRequest request) throws IOException {
        final String limitParameter = request.queryParameters == null ? DEFAULT_LIMIT : request.queryParameters.getOrDefault("limit", DEFAULT_LIMIT);
        final int limit = Integer.parseInt(limitParameter);
        final ScanRequest scanRequest = new ScanRequest().withTableName(tableName).withLimit(limit);
        final ScanResult scanResult = dynamoDb.scan(scanRequest);
        final List<WeatherEvent> events = scanResult.getItems().stream().map(
                item -> new WeatherEvent(item.get("locationName").getS(), Double.parseDouble(item.get("temperature").getN()),
                        Long.parseLong(item.get("timestamp").getN()), Double.parseDouble(item.get("longitude").getN()), Double.parseDouble(item.get("latitude").getN())))
                .collect(Collectors.toList());
        final String json = objectMapper.writeValueAsString(events);

        return new ApiGetawayResponse(200, json);
    }


}
