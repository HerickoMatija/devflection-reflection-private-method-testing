package com.devflection;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PrivateMethodClassTest {

    private static final String PRIVATE_METHOD_NAME = "isDivisibleBy2";

    @Test
    public void isDivisibleBy2_numberIsOdd_shouldReturnFalse() {
        PrivateMethodClass privateMethodClass = new PrivateMethodClass();

        Optional<Boolean> methodResult = makeMethodAccessible(PrivateMethodClass.class, PRIVATE_METHOD_NAME, int.class)
                .map(method -> invokeMethod(method, Boolean.class, privateMethodClass, 3));

        if (methodResult.isPresent()) {
            assertFalse(methodResult.get());
        } else {
            throw new IllegalStateException(String.format("Could not invoke method [%s]", PRIVATE_METHOD_NAME));
        }
    }

    @Test
    public void isDivisibleBy2_numberIsEven_shouldReturnTrue() {
        PrivateMethodClass privateMethodClass = new PrivateMethodClass();

        Optional<Boolean> methodResult = makeMethodAccessible(PrivateMethodClass.class, PRIVATE_METHOD_NAME, int.class)
                .map(method -> invokeMethod(method, Boolean.class, privateMethodClass, 4));

        if (methodResult.isPresent()) {
            assertTrue(methodResult.get());
        } else {
            throw new IllegalStateException(String.format("Could not invoke method [%s]", PRIVATE_METHOD_NAME));
        }
    }

    @Test
    public void isDivisibleBy2_numberIs0_shouldReturnTrue() {
        PrivateMethodClass privateMethodClass = new PrivateMethodClass();

        Optional<Boolean> methodResult = makeMethodAccessible(PrivateMethodClass.class, PRIVATE_METHOD_NAME, int.class)
                .map(method -> invokeMethod(method, Boolean.class, privateMethodClass, 0));

        if (methodResult.isPresent()) {
            assertTrue(methodResult.get());
        } else {
            throw new IllegalStateException(String.format("Could not invoke method [%s]", PRIVATE_METHOD_NAME));
        }
    }

    @Test
    public void isDivisibleBy2_numberIsNegativeAndOdd_shouldReturnFalse() {
        PrivateMethodClass privateMethodClass = new PrivateMethodClass();

        Optional<Boolean> methodResult = makeMethodAccessible(PrivateMethodClass.class, PRIVATE_METHOD_NAME, int.class)
                .map(method -> invokeMethod(method, Boolean.class, privateMethodClass, -7));

        if (methodResult.isPresent()) {
            assertFalse(methodResult.get());
        } else {
            throw new IllegalStateException(String.format("Could not invoke method [%s]", PRIVATE_METHOD_NAME));
        }
    }

    @Test
    public void isDivisibleBy2_numberIsNegativeAndEven_shouldReturnTrue() {
        PrivateMethodClass privateMethodClass = new PrivateMethodClass();

        Optional<Boolean> methodResult = makeMethodAccessible(PrivateMethodClass.class, PRIVATE_METHOD_NAME, int.class)
                .map(method -> invokeMethod(method, Boolean.class, privateMethodClass, -24));

        if (methodResult.isPresent()) {
            assertTrue(methodResult.get());
        } else {
            throw new IllegalStateException(String.format("Could not invoke method [%s]", PRIVATE_METHOD_NAME));
        }
    }

    private Optional<Method> makeMethodAccessible(Class<?> clazz, String methodName, Class<?>... arguments) {
        try {
            Method method = clazz.getDeclaredMethod(methodName, arguments);
            method.setAccessible(true);
            return Optional.of(method);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private <T> T invokeMethod(Method method, Class<T> resultClassType, Object targetObject, Object... methodArgs) {
        try {
            Object result = method.invoke(targetObject, methodArgs);
            return resultClassType.cast(result);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
