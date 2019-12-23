package edu.uestc.Utils;

import java.util.List;

public class RuleData {
    private List<Tripples> tripples;
    private List<Method> method;
    private List<String> result;
    public RuleData(){}

    public List<String> getResult() {
        return result;
    }

    public void setResult(List<String> result) {
        this.result = result;
    }

    public List<Tripples> getTripples() {
        return tripples;
    }

    public void setTripples(List<Tripples> tripples) {
        this.tripples = tripples;
    }

    public List<Method> getMethod() {
        return method;
    }

    public void setMethod(List<Method> method) {
        this.method = method;
    }
}
class Tripples{
    private int type;
    private List<String> tripples;
    public Tripples(){}

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<String> getTripples() {
        return tripples;
    }

    public void setTripples(List<String> tripples) {
        this.tripples = tripples;
    }
}
class Method{
    private int size;
    private String methodName;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Method(){}
}
