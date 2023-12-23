package org.geekhub.hw10;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestRunner {
    private static final String DELIMITER = "========================================";
    private TestRunner(){
    }
    public static void runTests(Class<?>... classes) {
        int totalTests = 0;
        int passedTests = 0;
        int failedTests = 0;

        printStart();

        for (Class<?> clazz : classes) {
            System.out.println("\nRunning tests in " + clazz.getSimpleName() + " class");

            Method[] methods = clazz.getDeclaredMethods();
            List<Method> testMethods = getTestMethods(methods);
            totalTests += testMethods.size();
            for (Method testMethod : testMethods) {
                try {
                    Object instance = clazz.getDeclaredConstructor().newInstance();

                    executeSetupMethods(clazz, instance);

                    if (testMethod.getParameterCount() <= 0) {
                        testMethod.invoke(instance);
                    } else {
                        String parameterSource = testMethod.getAnnotation(Test.class).parameterSource();
                        Method parameterMethod = clazz.getDeclaredMethod(parameterSource);
                        Object[] parameters = (Object[]) parameterMethod.invoke(instance);
                        testMethod.invoke(instance, parameters);
                    }
                    System.out.println("  + Test: " + testMethod.getName() + " - Passed");
                    passedTests++;

                    executeTeardownMethods(clazz, instance);
                } catch (InvocationTargetException e) {
                    System.out.println("  - Test: " + testMethod.getName() + " - Failed");
                    System.out.println("    Reason: " + e.getCause().getMessage());
                    failedTests++;
                } catch (Exception e) {
                    System.out.println("  - Test: " + testMethod.getName() + " - Failed");
                    System.out.println("    Reason: " + e.getMessage());
                    failedTests++;
                }
            }
        }

        printEnd(passedTests,failedTests,totalTests);
    }

    private static List<Method> getTestMethods(Method[] methods){
        List<Method> testMethods = new ArrayList<>();

        Arrays.stream(methods).forEach(method -> {
            int modifier = method.getModifiers();
            if (method.isAnnotationPresent(Test.class) && !Modifier.isStatic(modifier)
                && !Modifier.isPrivate(modifier) && !Modifier.isProtected(modifier)) {
                testMethods.add(method);
            }
        });
        return testMethods;
    }
    private static void printStart(){
        System.out.println(DELIMITER);
        System.out.println("Testing framework");
        System.out.println(DELIMITER);
    }
    private static void printEnd(int passedTests, int failedTests,int totalTests){
        System.out.println("\n" + DELIMITER);
        System.out.println("Summary");
        System.out.println(DELIMITER);
        System.out.println("Total tests run: " + totalTests);
        System.out.println("Passed tests: " + passedTests);
        System.out.println("Failed tests: " + failedTests);
    }
    private static void executeSetupMethods(Class<?> clazz, Object instance) throws InvocationTargetException, IllegalAccessException {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(BeforeMethod.class)) {
                method.invoke(instance);
            }
        }
    }

    private static void executeTeardownMethods(Class<?> clazz, Object instance) throws InvocationTargetException, IllegalAccessException {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(AfterMethod.class)) {
                method.invoke(instance);
            }
        }
    }
}
