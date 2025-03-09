package nl.pancompany.clean.architecture.adapter.gateway.repository.dummy.jpa;

import nl.pancompany.clean.architecture.adapter.gateway.repository.dummy.jpa.model.DummyJpaId;
import nl.pancompany.clean.architecture.adapter.gateway.repository.dummy.jpa.model.DummyJpaEntity;
import nl.pancompany.clean.architecture.application.domain.model.dummy.DummyDto;
import nl.pancompany.clean.architecture.application.domain.model.dummy.DummyId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JpaDummyMapper {

    @Mapping(target = "id", ignore = true)
    DummyJpaEntity map(DummyDto dummy);

    DummyJpaId map (DummyId dummyId);

    DummyDto map(DummyJpaEntity dummyJpaEntity);

    DummyId map (DummyJpaId dummyJpaId);

}