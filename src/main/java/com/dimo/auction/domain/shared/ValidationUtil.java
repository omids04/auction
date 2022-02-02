package com.dimo.auction.domain.shared;

import java.util.Objects;

public class ValidationUtil {

    private static final ValidationUtil VALIDATOR = new ValidationUtil();

    public static ValidationUtil getValidator(){
        return VALIDATOR;
    }

    public ValidationUtil notNull(Object o, String fieldName){
        Objects.requireNonNull(o, fieldName + " can't be null");
        return VALIDATOR;
    }

    public ValidationUtil isFalse(boolean statement, String msg){
        if(statement)
            throw new IllegalArgumentException(msg);
        return VALIDATOR;
    }

    public ValidationUtil isTrue(boolean statement, String msg){
        isFalse(!statement, msg);
        return VALIDATOR;
    }
    public ValidationUtil isFalseState(boolean statement, String msg){
        if(statement)
            throw new IllegalStateException(msg);
        return VALIDATOR;
    }
    public ValidationUtil isTrueState(boolean statement, String msg){
        isFalse(!statement, msg);
        return VALIDATOR;
    }
}
