//package com.MobileSubscribers.MobileSubscribers.MobileSubscribers;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import java.util.List;
//
//@Configuration
//public class SubscriberConfig {
//
//    @Bean
//    @Autowired
//    CommandLineRunner commandLineRunner(SubscriberRepository repository) {
//        return args -> {
//            Subscribers pious = new Subscribers(
//                    "0248189134",
//                    99,
//                    100,
//                    ServiceType.MobilePrepaid,
//                    System.currentTimeMillis()
//                    );
//
//
//            Subscribers sniper = new Subscribers(
//                    "0273163806",
//                    47,
//                    7,
//                    ServiceType.MobilePostpaid,
//                    System.currentTimeMillis()
//            );
//
//            Subscribers coldSniper = new Subscribers(
//                    "0204314933",
//                    7,
//                    777,
//                    ServiceType.MobilePrepaid,
//                    System.currentTimeMillis()
//            );
//            Subscribers PJaySniper = new Subscribers(
//                    "979797",
//                    97979797,
//                    77799999,
//                    ServiceType.MobilePrepaid,
//                    System.currentTimeMillis()
//            );
//            repository.saveAll(List.of(pious, sniper, coldSniper, PJaySniper)); // Save the Subscribers to the repository
//        };
//    }
//}
