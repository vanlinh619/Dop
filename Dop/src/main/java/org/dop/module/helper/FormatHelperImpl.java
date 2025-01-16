package org.dop.module.helper;

import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Service
public class FormatHelperImpl implements FormatHelper {
    @Override
    public String formatProperties(Object object) {
        String pre = "get";
        StringBuilder result = new StringBuilder();
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
             try {
                 if (field.canAccess(object)) {
                     var value = field.get(object);
                     result.append(String.format("\n - %s: %s", name, value));
                 } else {
                        Method method = clazz.getMethod(pre + name.substring(0, 1).toUpperCase() + name.substring(1));
                        if (method.canAccess(object)) {
                            Object value = method.invoke(object);
                            result.append(String.format("\n - %s: %s", name, value));
                        }
                 }
            } catch (Exception ignore) {
            }
        }
        return result.toString();
    }
}
