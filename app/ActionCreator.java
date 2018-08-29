import beans.ActionMappingEntity;
import core.RequestMappingHolder;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import java.util.concurrent.CompletionStage;

import java.lang.reflect.Method;

public class ActionCreator implements play.http.ActionCreator {
    @Override
    public Action createAction(Http.Request request, Method actionMethod) {
        ActionMappingEntity mappingEntity=new ActionMappingEntity();
        mappingEntity.setActionName(actionMethod.getName());
        mappingEntity.setHttpMethodType(request.method());
        mappingEntity.setPath(request.path());
        mappingEntity.setQueryParams(request.queryString());
        mappingEntity.setUri(request.uri());
        mappingEntity.setHeaders(request.headers());
        mappingEntity.setJsonBody(request.body().asJson());

        RequestMappingHolder.getInstance().setActionMappingEntity(mappingEntity);
        return new Action.Simple() {
            @Override
            public CompletionStage<Result> call(Http.Context ctx) {
                return delegate.call(ctx);
            }
        };
    }
}
