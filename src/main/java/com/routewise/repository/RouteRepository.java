package com.routewise.repository;

import com.routewise.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {

    @Query("SELECT r FROM Route r WHERE LOWER(r.sourceCity.name) = LOWER(:source) AND LOWER(r.destinationCity.name) = LOWER(:destination) AND r.isActive = true")
    List<Route> findBySourceAndDestinationIgnoreCase(@Param("source") String source, @Param("destination") String destination);

    @Query("SELECT r FROM Route r WHERE r.sourceCity.id = :sourceId AND r.destinationCity.id = :destId")
    Optional<Route> findBySourceCityIdAndDestinationCityId(@Param("sourceId") Long sourceId, @Param("destId") Long destId);

    List<Route> findByIsActiveTrue();
}
