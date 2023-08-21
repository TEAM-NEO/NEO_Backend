package com.neo.needeachother.users.mapper;

import com.neo.needeachother.users.document.NEOStarInfoDocument;
import com.neo.needeachother.users.dto.NEOStarInfoDto;
import com.neo.needeachother.users.entity.NEOStarEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;

@Mapper(componentModel = "spring")
public interface NEOStarMapper {
    NEOStarMapper INSTANCE = Mappers.getMapper(NEOStarMapper.class);

    NEOStarEntity toStarEntity(NEOStarInfoDto request);

    @Mapping(source = "request.customIntroductionList", target="starCustomIntroductionList")
    NEOStarInfoDocument toStarInfoDocument(NEOStarInfoDto request);

    @AfterMapping
    default void initializeListField(@MappingTarget NEOStarEntity.NEOStarEntityBuilder entityBuilder) {
        entityBuilder.starTypeList(new ArrayList<>());
    }
}
