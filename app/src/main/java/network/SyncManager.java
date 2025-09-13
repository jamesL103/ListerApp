package network;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.nio.file.Path;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class SyncManager {

    private static final String SERVER_URL = "http://localhost:3000/session_manager";

    public static final String SERVER_SYNC_PATH = "./temp/sync_res";

    private final HttpClient CLIENT = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();

    public CompletableFuture<HttpResponse<Path>> getDataFromServer(int sessionId) {
        HttpRequest req = HttpRequest.newBuilder()
                        .uri(URI.create(SERVER_URL))
                        .timeout(Duration.ofSeconds(30))
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .POST(HttpRequest.BodyPublishers.ofString("operation=getData&id=" + sessionId))
                        .build();
        return CLIENT.sendAsync(req, HttpResponse.BodyHandlers.ofFile(Path.of("./temp/sync_res")));
    }

}
