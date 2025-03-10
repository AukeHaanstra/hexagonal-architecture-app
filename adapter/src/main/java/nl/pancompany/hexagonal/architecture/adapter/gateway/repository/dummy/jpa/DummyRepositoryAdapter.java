package nl.pancompany.hexagonal.architecture.adapter.gateway.repository.dummy.jpa;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.pancompany.hexagonal.architecture.common.annotation.architecture.Adapter;
import nl.pancompany.hexagonal.architecture.application.domain.model.dummy.Dummy;
import nl.pancompany.hexagonal.architecture.application.domain.model.dummy.DummyDto;
import nl.pancompany.hexagonal.architecture.application.domain.model.dummy.DummyId;
import nl.pancompany.hexagonal.architecture.application.port.out.DummyRepositoryPort;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Adapter("dummy-repository-adapter")
public class DummyRepositoryAdapter implements DummyRepositoryPort {

    private final JpaDummyRepository jpaDummyRepository;
    private final JpaDummyMapper jpaDummyMapper;

    @Override
    @Transactional
    public void save(DummyDto dummyDto) {
        final var jpaDummyEntity = jpaDummyRepository.findByDummyId(jpaDummyMapper.map(dummyDto.dummyId()));
        if (jpaDummyEntity == null) {
            final var newDummyEntity = jpaDummyMapper.map(dummyDto);
            jpaDummyRepository.save(newDummyEntity);
        } else {
            jpaDummyEntity.setDummyData(dummyDto.dummyData());
        }
    }

    @Override
    public Dummy get(DummyId dummyId) {
        return new Dummy(jpaDummyMapper.map(jpaDummyRepository.findByDummyId(jpaDummyMapper.map(dummyId))));
    }

}
