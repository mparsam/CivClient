package Model;

import java.io.Serializable;
import java.util.HashMap;

public class Request implements Serializable {
    public static final long serialVersionUID = 2L;
    public final String action;
    public final HashMap<String, String> params;
    private Object obj;
    private Class<?> objClass;

    public Request(String action, HashMap<String, String> params) {
        this.action = action;
        this.params = params;
        this.obj = null;
    }

    public Request(String action, HashMap<String, String> params, Object obj) {
        this.action = action;
        this.params = params;
        this.obj = obj;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
//        this.objClass = obj.getClass();
    }

    public Class<?> getObjClass() {
        return objClass;
    }


    @Override
    public String toString() {
        return "Request{" +
                "action='" + action + '\'' +
                ", params=" + params +
                '}';
    }
}


