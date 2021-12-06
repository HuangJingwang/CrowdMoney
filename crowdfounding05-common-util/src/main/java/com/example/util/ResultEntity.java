package com.example.util;

import com.sun.corba.se.impl.orbutil.graph.NodeData;

public class ResultEntity<T> {
    private static final String SUCCESS ="SUCCESS";
    private static final String FAILED = "FAILED";

    private static final String NO_MESSAGE ="NO_MESSAGE";
    private static final String NO_DATA="NO_DATA";
    private  String result;
    private String message;
    private T data;

    public ResultEntity() {
    }

    public ResultEntity(String result, String message, T data) {
        this.result = result;
        this.message = message;
        this.data = data;
    }

    public static <Type> ResultEntity<Type> successWithoutData()
    {

        return new ResultEntity<Type>(SUCCESS,null,null);
    }

    public static<Type> ResultEntity<Type> successWithData(Type data)
    {
        return new ResultEntity<Type>(SUCCESS,NO_MESSAGE,data);
    }
    public static <Type> ResultEntity<Type> failed(String message)
    {
        return new ResultEntity<Type>(FAILED,message, null);
    }

}
