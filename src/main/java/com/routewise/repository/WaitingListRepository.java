package com.routewise.repository;

import com.routewise.entity.WaitingListEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WaitingListRepository extends JpaRepository<WaitingListEntry, Long> {

    List<WaitingListEntry> findByScheduleIdAndStatusOrderByPriorityLevelDescCreatedAtAsc(Long scheduleId, String status);

    List<WaitingListEntry> findByScheduleIdAndStatusOrderByCreatedAtAsc(Long scheduleId, String status);

    List<WaitingListEntry> findByStatusOrderByCreatedAtDesc(String status);
}
