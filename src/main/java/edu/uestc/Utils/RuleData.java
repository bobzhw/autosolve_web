package edu.uestc.Utils;

import java.util.List;

public class RuleData {
    private List<Tripples> tripples;
    private List<Method> method;
    private List<Tripple> result;
    private List<Parameter> parameters;
    public RuleData(){}

    public List<Tripple> getResult() {
        return result;
    }

    public void setResult(List<Tripple> result) {
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

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public static class Parameter {
        private String name;
        private String type;
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Parameter() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String show() {
            return getName() + "【" + getType() + "】";
        }

    }



    public static class Tripples{
        private int type;
        private List<Tripple> tripples;
        public Tripples(){}

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public List<Tripple> getTripples() {
            return tripples;
        }

        public void setTripples(List<Tripple> tripples) {
            this.tripples = tripples;
        }
    }

    public static class Tripple{
        private int type;
        private int id;
        private String tripple_string;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTripple_string() {
            return tripple_string;
        }

        public void setTripple_string(String tripple_string) {
            this.tripple_string = tripple_string;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public Tripple() {
        }

    }
    public static class Method{
        private int size;
        private String methodName;
        private int methodId;

        public int getMethodId() {
            return methodId;
        }

        public void setMethodId(int methodId) {
            this.methodId = methodId;
        }

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
}

