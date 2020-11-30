package com.example.cardealer.events.control;

import com.example.cardealer.events.boundary.CessionRepository;
import com.example.cardealer.events.entity.Cession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CessionService {

    private final CessionRepository cessionRepository;

    /*public List<Cession> findAllCessions() {
        return cessionRepository.findAllCessions();
    }

    public Page<Cession> findAllCessions(Pageable pageable) {
        return cessionRepository.findAllCessions(pageable);
    }*/

    public Cession save(Cession cession) {
        return cessionRepository.save(cession);
    }

    /*public Page<Cession> findAllCessionsOfUser(Long id, Pageable pageable) {
        return cessionRepository.findAllCessionsOfUser(id, pageable);
    }*/
}
