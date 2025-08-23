package com.cab21.delivery.repository;

import com.cab21.delivery.model.Cab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CabRepository extends JpaRepository<Cab, Long> {
    Optional<Cab> findByIdAndStatus(Long id, Integer status);
    Optional<Cab> findByPlate(String plate);
    boolean existsByPlate(String plate);
    List<Cab> findByStatus(Integer status);
    List<Cab> findByPassengerSeatsGreaterThanEqual(Integer minSeats);

    // is this cab free in a time window? (no overlapping OPEN/ONGOING rides)
    @Query("""
        select (count(r) = 0) from Ride r
        where r.cab.id = :cabId
          and r.status in ('OPEN','ONGOING')
          and r.startTime < :end and r.endTime > :start
    """)
    boolean isCabFree(Long cabId, LocalDateTime start, LocalDateTime end);

    // list of available cabs for a window + minimum seats
    @Query("""
    select c from Cab c
    where c.status = 1
      and c.passengerSeats >= :minSeats
      and not exists (
         select 1 from Ride r
         where r.cab = c
           and r.status in ('OPEN','ONGOING')
           and r.startTime < :end and r.endTime > :start
      )
    order by c.plate asc
""")
    List<Cab> findAvailableCabs(LocalDateTime start, LocalDateTime end, Integer minSeats);

    @Query("""
        select c from Cab c
        where (:q is null or :q = '' 
               or lower(c.plate) like lower(concat('%',:q,'%'))
               or lower(c.driverName) like lower(concat('%',:q,'%'))
               or lower(c.model) like lower(concat('%',:q,'%')))
        order by c.driverName asc
    """)
    List<Cab> search(String q);
}
