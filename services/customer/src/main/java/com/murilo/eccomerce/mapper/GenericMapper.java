package com.murilo.eccomerce.mapper;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GenericMapper {

    public static <T, U> T map(U source, Class<T> destinationClass) {
        T destinationObject;

        try {
            destinationObject = destinationClass.getDeclaredConstructor().newInstance();

            Field[] sourceFields = source.getClass().getDeclaredFields();
            Field[] destinationFields = source.getClass().getDeclaredFields();

            Map<String, Field> sourceFieldMap = Arrays.stream(sourceFields)
                    .peek(field -> field.setAccessible(true))
                    .collect(Collectors.toMap(Field::getName, Function.identity()));

            Arrays.stream(destinationFields)
                    .peek(destinationField -> destinationField.setAccessible(true))
                    .filter(destinationField -> sourceFieldMap.containsKey(destinationField.getName()) && sourceFieldMap.get(destinationField.getName()).getType().equals(destinationField.getType()))
                    .forEach(field -> {
                        try {
                            field.set(destinationObject, sourceFieldMap.get(field.getName()).get(source));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
