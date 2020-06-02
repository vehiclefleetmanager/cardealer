package com.example.cardealer.events.control;

import com.example.cardealer.events.boundary.CessionRepository;
import com.example.cardealer.events.entity.Cession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CessionService {

    private final CessionRepository cessionRepository;

    public List<Cession> findAllCessions() {
        return cessionRepository.findAllCessions();
    }

    public Page<Cession> findAllCessions(Pageable pageable) {
        return cessionRepository.findAllCessions(pageable);
    }

    public Cession save(Cession cession) {
        Cession save = cessionRepository.save(cession);
        save.getAgreement().getTransaction().name();
        return save;
    }
}
