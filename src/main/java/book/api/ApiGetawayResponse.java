package book.api;

public class ApiGetawayResponse {
    public  Integer statusCode;
    public String message;

    public ApiGetawayResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
