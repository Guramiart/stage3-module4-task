package com.mjc.school.service.checker;

import com.mjc.school.service.validator.constraint.Size;
import org.springframework.stereotype.Component;

@Component
public class SizeConstraintChecker implements ConstraintChecker<Size>{

    @Override
    public boolean check(Object value, Size constraint) {
        if(value instanceof CharSequence charSequence) {
            return (constraint.min() < 0 || constraint.min() <= charSequence.length())
                    && (constraint.max() < 0 || constraint.max() >= charSequence.length());
        }
        return true;
    }

    @Override
    public Class<Size> getType() {
        return Size.class;
    }
}
