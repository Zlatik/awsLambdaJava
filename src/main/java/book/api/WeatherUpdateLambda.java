package book.api;

import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;

import java.io.IOException;

public class WeatherUpdateLambda extends WeatherLambdaBase{

    public ApiGetawayResponse handler(ApiGetawayRequest request) throws IOException {
        Table table = dynamoDB.getTable(tableName);
        WeatherEvent weatherEvent = objectMapper.readValue(request.body, WeatherEvent.class);
        UpdateItemSpec  updateItemSpec = new UpdateItemSpec().withPrimaryKey("locationName", weatherEvent.locationName)
                .withUpdateExpression("set #t = :t , #g = :g , #p = :p , #d = :d")
                .withNameMap(new NameMap().with("#t", "temperature").with("#g", "longitude")
                            .with("#p", "timestamp").with("#d", "latitude"))
                .withValueMap(new ValueMap().withNumber(":t", weatherEvent.temperature)
                        .withNumber(":g", weatherEvent.longitude).withNumber(":p", weatherEvent.timestamp)
                        .withNumber(":d", weatherEvent.latitude))
                .withReturnValues(ReturnValue.UPDATED_OLD);
        UpdateItemOutcome outcome = table.updateItem(updateItemSpec);

        return new ApiGetawayResponse(200, outcome.getItem().toJSON());
    }


}
