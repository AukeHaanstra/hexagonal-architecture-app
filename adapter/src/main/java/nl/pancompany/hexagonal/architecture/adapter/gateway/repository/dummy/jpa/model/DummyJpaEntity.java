package nl.pancompany.hexagonal.architecture.adapter.gateway.repository.dummy.jpa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dummy", schema="demo")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DummyJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dummyIdGenerator")
    @SequenceGenerator(name = "dummyIdGenerator", sequenceName = "dummy_id_seq", schema = "demo", allocationSize = 1, initialValue = 1)
    @Column(name = "id")
    private Long id;

    @Embedded
    @Column(name = "dummy_id")
    private DummyJpaId dummyId;

    @Column(name = "dummy_data")
    private String dummyData;

}
