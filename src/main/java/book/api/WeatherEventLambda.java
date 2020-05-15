package book.api;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.fasterxml.jackson.databind.DeserializationFeature;

import java.io.IOException;


public class WeatherEventLambda extends WeatherLambdaBase{

    public ApiGetawayResponse handler(ApiGetawayRequest request) throws IOException {
        this.configureObjectMapper();
        final WeatherEvent weatherEvent = objectMapper.readValue(request.body, WeatherEvent.class);
        final Table table = dynamoDB.getTable("WeatherApi-LocationsTable-OX4Y82H4BVDP");
        final Item item = new Item().withPrimaryKey("locationName", weatherEvent.locationName)
                .withDouble("temperature", weatherEvent.temperature)
                .withLong("timestamp", weatherEvent.timestamp)
                .withDouble("longitude", weatherEvent.longitude)
                .withDouble("latitude", weatherEvent.latitude);

        table.putItem(item);

        return new ApiGetawayResponse(200, weatherEvent.toString());
    }

    private void configureObjectMapper() {
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}
