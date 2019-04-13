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

    /**
     * This method takes a class, method name and the method arguments and tries to make the method in this class public
     * so it can be executed.
     *
     * @param clazz Class that contains the given method
     * @param methodName Method name of the method we want to make public
     * @param arguments Arguments that the method takes
     * @return An optional containing the method if the method was found or Optional.empty() if the method was not found
     */
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

    /**
     * This method tries to execute the given method and then cast and return the result of the method.
     *
     * @param method Method that we want to execute
     * @param resultClassType Class to which we cast the result of the method to
     * @param targetObject Instance of the class containing the method on which we will execute the method
     * @param methodArgs Arguments of the method we want to execute
     * @param <T> Type to which we want to cast the result of the method to
     * @return Returns the result of the method cast to the given resultClassType or null if there was an exception
     */
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
