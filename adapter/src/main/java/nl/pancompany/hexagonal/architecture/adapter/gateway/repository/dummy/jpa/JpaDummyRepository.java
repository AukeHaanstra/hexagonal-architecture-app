package nl.pancompany.hexagonal.architecture.adapter.gateway.repository.dummy.jpa;

import nl.pancompany.hexagonal.architecture.adapter.gateway.repository.dummy.jpa.model.DummyJpaEntity;
import nl.pancompany.hexagonal.architecture.adapter.gateway.repository.dummy.jpa.model.DummyJpaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaDummyRepository extends JpaRepository<DummyJpaEntity, Long> {

    DummyJpaEntity findByDummyId(final DummyJpaId dummyJpaId);

}
