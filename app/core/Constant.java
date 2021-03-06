package core;

/**
 * Created by atul on 29/06/17.
 */
public enum Constant {

    /****** INTERNAL URIs **********/
    PATH_GENERATOR("/generator"),

    /****** STRING CONSTANTS **********/
    APPLICATION_JSON("application/json"),
    ERROR("error"),
    SUCCESS("success"),
    CONFLICT("conflict"),

    /****** HTTP METHOD TYPE STRING CONSTANTS **********/
    POST("POST"),
    GET("GET"),
    PUT("PUT"),
    DELETE("DELETE"),

    /****** HTTP STATUS CODES **********/
    STATUS_OK("200"),
    STATUS_BAD_REQUEST("400"),
    STATUS_CONFLICT("409"),

    /****** METHOD MAPPING CONSTANTS **********/
    METHOD_MAPPING_GET_CALL("controllers.MockedAPIController.getMappedResponseForGetCall()"),
    METHOD_MAPPING_POST_CALL("controllers.MockedAPIController.getMappedResponseForPostCall()"),
    METHOD_MAPPING_ONE_PARAM_GET_CALL("controllers.MockedAPIController.getMappedResponseForOnePathParamGetCall(var1)"),
    METHOD_MAPPING_TWO_PARAM_GET_CALL("controllers.MockedAPIController.getMappedResponseForTwoPathParamGetCall(var1, var2)"),
    METHOD_MAPPING_THREE_PARAM_GET_CALL("controllers.MockedAPIController.getMappedResponseForThreePathParamGetCall(var1, var2, var3)"),
    METHOD_MAPPING_ONE_PARAM_POST_CALL("controllers.MockedAPIController.getMappedResponseForOnePathParamPostCall(var1)"),
    METHOD_MAPPING_TWO_PARAM_POST_CALL("controllers.MockedAPIController.getMappedResponseForTwoPathParamPostCall(var1, var2)"),
    METHOD_MAPPING_THREE_PARAM_POST_CALL("controllers.MockedAPIController.getMappedResponseForThreePathParamPostCall(var1, var2, var3)"),

    /****** FILE PATH **********/
    FILE_SERIALIZE("target/mocker.container"),
    FILE_ROUTE("conf/routes"),

    /********* SPECIAL CHAR **************/
    TAB("\t"),
    NEWLINE("\n")
    ;

    private final String constant;

    Constant(String constant) {
        this.constant=constant;
    }

    public String value(){
        return constant;
    }

    public int asInteger() throws NumberFormatException{
        return Integer.parseInt(constant);
    }

    public String repeat(int count){
        String val=constant;
        while (count!=0){
            val=val+constant;
            count--;
        }
        return val;
    }
}
