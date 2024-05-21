package com.behsacorp.processmanagement.common;

public class Results {
    public static final Result SUCCESS = new Result(0, "success");
    public static final Result FAILED_GENERAL = new Result(10001, "failed_general");


    public static class Result {
        public int code;
        public String message;

        public Result(int code, String message) {
            this.code = code;
            this.message = message;
        }
    }

}
