package com.epam.esm.utils;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

public class ObjectMapperUtils {

    private static final ModelMapper modelMapper;


    static {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE);
    }


    private ObjectMapperUtils() {
    }

    //Map Object
    public static <D, T> D map(final T entity, Class<D> outClass) {
        return modelMapper.map(entity, outClass);
    }

    //Map ResultList
    public static <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outCLass) {
        return entityList.stream()
                .map(entity -> map(entity, outCLass))
                .collect(Collectors.toList());
    }


    //Map Page
    public static  <D, T> Page<D> mapEntityPageIntoDtoPage(Page<T> entities, Class<D> dtoClass) {
        return entities.map(objectEntity -> modelMapper.map(objectEntity, dtoClass));
    }

    public static void mapObjects(Object o, Object c){
        modelMapper.map(o,c);
    }
}

