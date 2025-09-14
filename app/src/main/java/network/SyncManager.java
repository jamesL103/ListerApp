package network;

import org.json.JSONObject;

import java.net.URI;
import java.net.http.*;
import java.nio.file.Path;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class SyncManager {

    private static final String SERVER_URL = "http://localhost:3000/session_manager";

    public static final Path SERVER_SYNC_PATH = Path.of("./temp/sync_res");

    public static final int NO_LIST_FOUND = 201;

    private final HttpClient CLIENT = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();

    public CompletableFuture<HttpResponse<Path>> getDataFromServer(int sessionId) {
        HttpRequest req = HttpRequest.newBuilder()
                        .uri(URI.create(SERVER_URL))
                        .timeout(Duration.ofSeconds(30))
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .POST(HttpRequest.BodyPublishers.ofString("operation=getData&id=" + sessionId))
                        .build();
        return CLIENT.sendAsync(req, HttpResponse.BodyHandlers.ofFile(SERVER_SYNC_PATH));
    }

    public CompletableFuture<HttpResponse<String>> sendData(JSONObject body) {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(SERVER_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                .timeout(Duration.ofSeconds(30))
                .build();
        return CLIENT.sendAsync(req, HttpResponse.BodyHandlers.ofString());
    }

}
