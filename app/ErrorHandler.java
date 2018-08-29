import play.http.HttpErrorHandler;
import play.mvc.*;
import play.mvc.Http.*;
import views.html.error;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import javax.inject.Singleton;

@Singleton
public class ErrorHandler implements HttpErrorHandler {
    public CompletionStage<Result> onClientError(RequestHeader request, int statusCode, String message) {
        return CompletableFuture.completedFuture(
                Results.status(statusCode, error.render(statusCode))
        );
    }

    public CompletionStage<Result> onServerError(RequestHeader request, Throwable exception) {
        return CompletableFuture.completedFuture(
                Results.internalServerError(error.render(500))
        );
    }
}