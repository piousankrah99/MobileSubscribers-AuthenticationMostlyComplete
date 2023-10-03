package com.MobileSubscribers.MobileSubscribers.MobileSubscribers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;


public interface SubscriberRepository extends JpaRepository<Subscribers, Long> {

    @Query("SELECT s FROM Subscribers s WHERE s.msisdn = ?1")
    Optional<Subscribers> findByMsisdn(String msisdn);

    void deleteAllById(Long Id);

    List<Subscribers> findByMsisdnContaining(String msisdn);


    Optional<Subscribers> findByEmail(String email);


    long countByServiceType(ServiceType serviceType);


//    long countSubscriberBySubscriber_type(ServiceType serviceType);
//    long countSubscriberBySubscriber_typeIs(ServiceType serviceType);

}
