package com.maids.library.management.system.service;

import com.maids.library.management.system.model.Patron;
import com.maids.library.management.system.repository.PatronRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatronService {

    private final PatronRepository patronRepository;

    @Cacheable("patrons")
    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    @Cacheable(value = "patrons", key = "#id")
    public Patron getPatronById(Long id) {
        Optional<Patron> optionalPatron = patronRepository.findById(id);
        return optionalPatron.orElse(null);
    }

    @CacheEvict(value = "patrons", key = "#id")
    public void addPatron(Patron patron) {
        patronRepository.save(patron);
    }

    @CacheEvict(value = "patrons", key = "#id")
    public Patron updatePatron(Long id, Patron patron) {
        if (patronRepository.existsById(id)) {
            patron.setId(id);
            return patronRepository.save(patron);
        }
        return null;
    }

    @CacheEvict(value = "patrons", key = "#id")
    public void deletePatron(Long id) {
        patronRepository.deleteById(id);
    }

}