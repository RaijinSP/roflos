package com.raijin.serializer;

import com.raijin.serializer.model.annotations.ObjGetter;
import com.raijin.serializer.model.annotations.ParamGetter;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Serializer implements Serializable {

    public static Map<String, Object> serialize(final Object obj)
            throws InvocationTargetException, IllegalAccessException {

        if (obj == null) return null;

        List<Method> methodList = new ArrayList<>();
        Map<Method, Object> nestedObjects = new HashMap<>();
        Method[] methods = obj.getClass().getMethods();

        for (Method method: methods) {
            if (isGetter(method)) {
                methodList.add(method);
                if (isObjGetter(method)) nestedObjects.put(method, method.invoke(obj));
            }
        }

        return new HashMap<>(getClassRepresentation(methodList, obj, nestedObjects));
    }


    private static HashMap<String, Object> getClassRepresentation(List<Method> methods, Object obj,
                                                                  Map<Method, Object> nested)
            throws InvocationTargetException, IllegalAccessException {
        Map<String, Object> C = new HashMap<>();
        C.put(className(obj, null, nested.isEmpty()), getMethodsRepresentation(methods, obj, nested));
        return (HashMap<String, Object>) C;
    }


    private static HashMap<String, Object> getMethodsRepresentation(List<Method> methods,
                                                                    Object obj, Map<Method,
            Object> nested)
            throws InvocationTargetException, IllegalAccessException {

        Map<String, Object> M = new HashMap<>();
        for (Method method: methods) {
            if(!nested.containsKey(method)) {
                M.put(methodName(method), getInvokeResult(method, obj));
            } else {
                M.put(className(method.invoke(obj), method, true),
                        getMethodsRepresentation(getSimpleGetters(method.invoke(obj)),
                                method.invoke(obj),
                                new HashMap<>()));
            }
        }
        return (HashMap<String, Object>) M;
    }


    private static List<Method> getSimpleGetters(Object obj){
        if (obj == null) {
            return new ArrayList<>();
        }
        List<Method> methodList = new ArrayList<>();
        Method[] methods = obj.getClass().getMethods();

        for (Method method: methods) {
            if (isSimpleGetter(method)) {
                methodList.add(method);
            }
        }
        return methodList;
    }


    private static Object getInvokeResult(Method method, Object obj)
            throws InvocationTargetException, IllegalAccessException {

        if (obj == null) return null;
        return method.invoke(obj);
    }


    private static String className(Object obj, Method method, boolean inner) {
        return inner ? (obj == null ? methodName(method) : obj.getClass().getSimpleName())
                : obj.getClass().getSimpleName();
    }


    private static String methodName(Method method) {
        if (method == null) return null;
        return method.getName().replace("get", "");
    }


    private static boolean isSimpleGetter(Method method) {
        return method.isAnnotationPresent(ParamGetter.class);
    }


    private static boolean isObjGetter(Method method) {
        return method.isAnnotationPresent(ObjGetter.class);
    }


    private static boolean isGetter(Method method) {
        return isSimpleGetter(method) || isObjGetter(method);
    }
}