package com.mjc.school.service.checker;

import com.mjc.school.service.validator.constraint.Max;
import org.springframework.stereotype.Component;

@Component
public class MaxConstraintChecker implements ConstraintChecker<Max>{

    @Override
    public boolean check(Object value, Max constraint) {
        return !(value instanceof Number number) || number.longValue() <= constraint.value();
    }

    @Override
    public Class<Max> getType() {
        return Max.class;
    }
}
