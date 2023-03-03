package com.mjc.school.service.validator;

import com.mjc.school.service.checker.ConstraintChecker;
import com.mjc.school.service.validator.constraint.Constraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.logging.Logger;

import static java.util.stream.Collectors.toMap;

@Component
@SuppressWarnings({"unchecked", "rawtypes"})
public class ValidatorImpl implements Validator{

    public static Logger LOGGER;
    private final Map<Class<? extends Annotation>, ConstraintChecker> checkersMap;

    @Autowired
    public ValidatorImpl(final List<ConstraintChecker> checkers) {
        this.checkersMap = checkers
                .stream()
                .collect(toMap(ConstraintChecker::getType, Function.identity()));
    }

    @Override
    public Set<ConstraintViolation> validate(Object object) {
        if(object == null) {
            return Collections.emptySet();
        }
        var violations = new HashSet<ConstraintViolation>();
        for(var declaredField : object.getClass().getDeclaredFields()) {
            validateField(violations, declaredField, object);
        }
        return violations;
    }

    private void validateObject(Set<ConstraintViolation> violations, final Object object) {
        if (object == null) {
            return;
        }
        for (var declaredField : object.getClass().getDeclaredFields()) {
            validateField(violations, declaredField, object);
        }
    }

    private void validateField(Set<ConstraintViolation> violations, Field declaredField, Object object) {
        for(var declaredAnnotation : declaredField.getDeclaredAnnotations()) {
            var annotationType = declaredAnnotation.annotationType();
            if(annotationType.isAnnotationPresent(Constraint.class)) {
                try {
                    if(declaredField.trySetAccessible() && declaredField.canAccess(object)) {
                        var value = declaredField.get(object);
                        var checker = checkersMap.get(annotationType);
                        if(checker != null && checker.check(value, annotationType.cast(declaredAnnotation))) {
                            violations.add(
                                    new ConstraintViolation(
                                            "Constraint '%s' violated for the value '%s'".formatted(
                                                    annotationType.getSimpleName(),
                                                    value
                                            )
                                    )
                            );
                        }
                        validateObject(violations, value);
                    }
                } catch (IllegalAccessException e) {
                    LOGGER.warning(e.getMessage());
                }
            }
        }
    }

}
