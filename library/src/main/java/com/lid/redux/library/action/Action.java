package com.lid.redux.library.action;

import android.text.TextUtils;

import com.lid.redux.library.utils.Assert;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by lid on 16/6/14.
 */
///////////////////////////////////////////////////////////////////////////
// 
///////////////////////////////////////////////////////////////////////////
public class Action {

    public enum TYPE {
        REQUEST,
        RESPONSE
    }

    public enum EXE {
        SYNC,
        ASYNC
    }

    // the unique id
    private String _id;

    // request or response
    private int _type;

    // sync or async
    private int _execution;

    // Action name, used reducer and state mapping
    private String _name;

    // action description
    private String _msg;

    // action response code
    private int _code;

    private boolean _result;

    private HashMap<String, Object> _paras;


    private Action(String id, int type, int execution, String name) {
        this._id = id;
        this._type = type;
        this._execution = execution;
        this._name = name;
        this._paras = new HashMap<>();
    }

    public HashMap<String, Object> getParas() {
        return _paras;
    }

    public String getName() {
        return _name;
    }

    public Action setName(String name) {
        this._name = name;
        return this;
    }

    public String getId() {
        return _id;
    }

    public Action setType(int type) {
        this._type = type;
        return this;
    }

    public String getMsg() {
        return _msg;
    }

    public Action setMsg(String msg) {
        this._msg = msg;
        return this;
    }

    public Action setRet(boolean result) {
        this._result = result;
        return this;
    }

    public Action setExecution(int execution) {
        this._execution = execution;
        return this;
    }

    public int getCode() {
        return _code;
    }
    public boolean isSuccess() {
        return _result;
    }
    public boolean isRequest() {
        return _type == TYPE.REQUEST.ordinal();
    }
    public boolean isResponse() {
        return _type == TYPE.RESPONSE.ordinal();
    }
    public boolean isSync() {
        return _execution == EXE.SYNC.ordinal();
    }
    public boolean isAsync() {
        return _execution == EXE.ASYNC.ordinal();
    }
    public boolean containsKey(String key) {
        return _paras.containsKey(key);
    }

    public Action setCode(int code) {
        this._code = code;
        return this;
    }

    public Action put(String key, Object value) {
        Assert.isFalse(TextUtils.isEmpty(key));
        _paras.put(key, value);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T extends Object> T get(String key) {
        Object ret = TextUtils.isEmpty(key) || !containsKey(key) ? null : _paras.get(key);
        return (T) ret;
    }

    @SuppressWarnings("unchecked")
    public <T extends Object> T get(String key, Object obj) {
        Object ret = get(key);
        return ret == null ? (T) obj : (T) ret;
    }

    // gson auto convert int to double.
    public int getAsInt(String key){
        return getAsInt(key, 0);
    }

    public int getAsInt(String key, int defaultVal){
        Integer ret = null;
        try {
            ret = get(key);
        } catch (ClassCastException e){
            Double val = get(key);
            ret = val != null ? val.intValue() : defaultVal;
        }
        return ret != null ? ret : defaultVal;
    }

    public long getAsLong(String key){
        return getAsLong(key, 0);
    }

    public long getAsLong(String key, long defaultVal){
        Long ret = null;
        try {
            ret = get(key);
        } catch (ClassCastException e){
            Double val = get(key);
            ret = val != null ? val.longValue() : defaultVal;
        }
        return ret != null ? ret : defaultVal;
    }

    public float getAsFloat(String key){
        return getAsFloat(key, 0.0f);
    }

    public float getAsFloat(String key, float defaultVal){
        Float ret = null;
        try {
            ret = get(key);
        } catch (ClassCastException e){
            Double val = get(key);
            ret = val != null ? val.floatValue() : defaultVal;
        }
        return ret != null ? ret : defaultVal;
    }

    public double getAsDouble(String key){
        return getAsDouble(key, 0.0);
    }

    public double getAsDouble(String key, double defaultVal){
        Double ret = get(key);
        return ret != null ? ret : defaultVal;
    }

    public static Action makeAsyncRequestAction(String id, String name) {
        return _make(id, TYPE.REQUEST.ordinal(), EXE.ASYNC.ordinal(), name);
    }

    public static Action makeAsyncRequestAction(String name) {
        return makeAsyncRequestAction(UUID.randomUUID().toString(), name);
    }

    public static Action makeSyncRequestAction(String id, String name) {
        return _make(id, TYPE.REQUEST.ordinal(), EXE.SYNC.ordinal(), name);
    }

    public static Action makeSyncRequestAction(String name) {
        return makeSyncRequestAction(UUID.randomUUID().toString(), name);
    }

    public static Action makeResponseAction(Action action, boolean success, int code) {
        return makeResponseAction(action, success, code, "");
    }

    public static Action makeResponseAction(Action action, boolean success, int code, String msg) {
        Assert.notNull(action);
        action.setRet(success);
        action.setType(TYPE.RESPONSE.ordinal());
        action.setExecution(EXE.SYNC.ordinal());
        if (!TextUtils.isEmpty(msg)) {
            action.setMsg(msg);
        }
        action.setCode(code);
        return action;
    }

    public static Action appendResponseAction(Action action) {
        Assert.notNull(action);
        action.setType(TYPE.RESPONSE.ordinal());
        return action;
    }

    public static Action response2Request(Action action){
        Assert.notNull(action);
        action.setType(TYPE.REQUEST.ordinal());
        return action;
    }

    public static Action async2Sync(Action action) {
        Assert.notNull(action);
        action.setExecution(EXE.SYNC.ordinal());
        return action;
    }

    private static Action _make(String id, int type, int execution, String name) {
        return new Action(id, type, execution, name);
    }

    @Override
    public String toString() {
        return "Action{" +
                "id='" + _id + '\'' +
                ", type=" + _getTypeDesc(_type) +
                ", execution=" + _getExecutionDesc(_execution) +
                ", name='" + _name + '\'' +
                ", msg='" + _msg + '\'' +
                ", code=" + _code +
                ", paras=" + _paras +
                '}';
    }


    private String _getTypeDesc(int t) {
        String ret = null;
        if (t == TYPE.REQUEST.ordinal()) {
            ret = "request";
        } else if (t == TYPE.RESPONSE.ordinal()) {
            ret = "response";
        } else {
            ret = "unknown";
        }
        return ret;
    }

    private String _getExecutionDesc(int e) {
        String ret = null;
        if (e == EXE.SYNC.ordinal()) {
            ret = "sync";
        } else if (e == EXE.ASYNC.ordinal()) {
            ret = "async";
        } else {
            ret = "unknown";
        }
        return ret;
    }
}
