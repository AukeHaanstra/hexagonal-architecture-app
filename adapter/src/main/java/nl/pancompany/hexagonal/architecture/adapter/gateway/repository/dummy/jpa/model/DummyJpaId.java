package nl.pancompany.hexagonal.architecture.adapter.gateway.repository.dummy.jpa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class DummyJpaId {

    @Column(name = "name")
    private String name;
}