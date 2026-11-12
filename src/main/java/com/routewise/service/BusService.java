package com.routewise.service;

import com.routewise.entity.Bus;
import com.routewise.repository.BusRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BusService {

    private final BusRepository busRepository;

    public BusService(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    public List<Bus> getAllBuses() {
        return busRepository.findAll();
    }

    public List<Bus> getActiveBuses() {
        return busRepository.findByIsActiveTrue();
    }

    public Optional<Bus> getBusById(Long id) {
        return busRepository.findById(id);
    }

    @Transactional
    public Bus saveBus(Bus bus) {
        return busRepository.save(bus);
    }

    @Transactional
    public Bus updateBus(Long id, Bus updated) {
        Bus existing = busRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Bus not found with id: " + id));

        existing.setBusNumber(updated.getBusNumber());
        existing.setBusName(updated.getBusName());
        existing.setBusType(updated.getBusType());
        existing.setTotalSeats(updated.getTotalSeats());
        existing.setSeatLayoutType(updated.getSeatLayoutType());
        existing.setAmenities(updated.getAmenities());
        existing.setIsActive(updated.getIsActive());

        return busRepository.save(existing);
    }

    @Transactional
    public void deleteBus(Long id) {
        busRepository.deleteById(id);
    }

    @Transactional
    public Bus toggleBusStatus(Long id) {
        Bus bus = busRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Bus not found with id: " + id));
        bus.setIsActive(!Boolean.TRUE.equals(bus.getIsActive()));
        return busRepository.save(bus);
    }

    public long countActiveBuses() {
        return busRepository.countByIsActiveTrue();
    }
}
