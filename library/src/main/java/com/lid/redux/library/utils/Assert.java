package com.lid.redux.library.utils;


public class Assert {

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isTrue(boolean expression) {
        isTrue(expression, "[Assertion failed] - this expression must be true");
    }

    public static void expected(String expectedValue,String actualValue){
        isTrue(expectedValue.equals(actualValue),"expected "+expectedValue+",but got "+actualValue);
    }

    public static void isFalse(boolean expression, String reason) {
        isTrue(!expression, reason);
    }
    
    public static void isFalse(boolean expression) {
        isTrue(!expression, "[Assertion failed] - this expression must be false");
    }

    public static void notTrue(boolean expression) {
        isTrue(!expression, "[Assertion failed] - this expression must be not true");
    }

    public static void isNull(Object object, String message) {
        if (object != null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isNull(Object object) {
        isNull(object, "[Assertion failed] - the object argument must be null");
    }

    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notNull(Object object) {
        notNull(object, "[Assertion failed] - this argument is required; it must not be null");
    }

}
