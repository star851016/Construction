package temp.chiu.com.clinico.model;

/**
 * Created by Kate on 2015/4/2
 */
public class BackendResponse<T> {
    public T baseResult;
    private float code = 1;
    private String message;

    public float getCode() {
        return code;
    }

    public String getRM() {
        return message;
    }

    public boolean isErrorResponse() {
        return baseResult == null || getData() == null;
    }

    public Object getData() {
        return null;
    }

    public boolean isEmpty() {
        return isErrorResponse();
    }
}

