package com.tourist.service;

import com.tourist.dto.*;
import com.tourist.entity.Tourist;
import com.tourist.repository.TouristRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TouristService {
    private final TouristRepository repository;
    public TouristService(TouristRepository repository) { this.repository = repository; }
    public List<TouristResponse> findAll() { return repository.findAll().stream().map(this::response).toList(); }
    public TouristResponse find(Long id) { return response(repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Tourist not found"))); }
    public TouristResponse create(TouristRequest request) {
        return response(repository.save(new Tourist(request.name(), request.destination(), request.email(), request.phone())));
    }
    public TouristResponse update(Long id, TouristRequest request) {
        Tourist tourist = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Tourist not found"));
        tourist.update(request.name(), request.destination(), request.email(), request.phone());
        return response(repository.save(tourist));
    }
    private TouristResponse response(Tourist tourist) {
        return new TouristResponse(tourist.getId(), tourist.getName(), tourist.getDestination(), tourist.getEmail(), tourist.getPhone());
    }
}
