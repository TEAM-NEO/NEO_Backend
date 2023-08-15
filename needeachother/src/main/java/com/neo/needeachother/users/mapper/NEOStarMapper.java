package com.neo.needeachother.users.mapper;

import com.neo.needeachother.users.document.NEOStarInfoDocument;
import com.neo.needeachother.users.entity.NEOStarEntity;
import com.neo.needeachother.users.entity.NEOStarTypeEntity;
import com.neo.needeachother.users.request.NEOCreateStarInfoRequest;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;

@Mapper(componentModel = "spring")
public interface NEOStarMapper {
    NEOStarMapper INSTANCE = Mappers.getMapper(NEOStarMapper.class);

    NEOStarEntity toStarEntity(NEOCreateStarInfoRequest request);

    @Mapping(source = "request.customIntroductionList", target="starCustomIntroductionList")
    NEOStarInfoDocument toStarInfoDocument(NEOCreateStarInfoRequest request);

    @AfterMapping
    default void initializeListField(@MappingTarget NEOStarEntity.NEOStarEntityBuilder entityBuilder) {
        entityBuilder.starTypeList(new ArrayList<>());
    }
}
