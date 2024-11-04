package org.acme.repository;


import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.Stream;

import java.util.Optional;



@ApplicationScoped
public class StreamRepository implements PanacheRepository<Stream> {
    public Optional<Stream> findByName(String name) {
        return find("name", name).firstResultOptional();
    }
}
