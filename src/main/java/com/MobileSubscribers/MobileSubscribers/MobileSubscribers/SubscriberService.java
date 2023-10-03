package com.MobileSubscribers.MobileSubscribers.MobileSubscribers;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;



import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriberService  implements UserDetailsService {

    private final SubscriberRepository subscriberRepository;


    private final PasswordEncoder passwordEncoder;

    private final SubscriberRepository userRepository;

    private final SubscriberRepository repository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public List<Subscribers> getSubscribers() {
       return subscriberRepository.findAll();
    }

//Search Subscribers by MSISDN
    public List<Subscribers> searchSubscribersByMsisdn(String msisdn) {
        return subscriberRepository.findByMsisdnContaining(msisdn);
    }


    public void addNewSubscriber(Subscribers newSubscriber) {
        Optional<Subscribers> existingSubscriberOptional = 
            subscriberRepository.findByMsisdn(newSubscriber.getMsisdn());
    
//        if (existingSubscriberOptional.isPresent()) {
//            throw new IllegalStateException("Number Taken");
//        }
    
        Subscribers savedSubscriber = subscriberRepository.save(newSubscriber);
    
        System.out.println("New subscriber added: " + savedSubscriber);
    }
    

@Transactional
    //Delete Subscribers
    public void deleteSubscriber(Long SubscribersId){
        boolean exists = subscriberRepository.existsById(SubscribersId);
        if (!exists){
            throw new IllegalStateException(
                "Subscribers with id " + SubscribersId + "does not exists");
        }
        subscriberRepository.deleteById(SubscribersId);
    }

    public void deleteAllSubscribers(){
        subscriberRepository.deleteAll();
    }


    //Return All Subscribers

    public List<Subscribers> getAllSubscribers() {
        return subscriberRepository.findAll();
    }

    @Transactional
    public Subscribers getSubscriberByMsisdn(String msisdn) {
        Optional<Subscribers> subscriber = subscriberRepository.findByMsisdn(msisdn);
        return subscriber.orElse(null);
    }


    public void updateSubscriber(Subscribers subscriber) {
        subscriberRepository.save(subscriber);
    }


    public Subscribers getSubscriberById(Long id) {
        Optional<Subscribers> subscriberOptional = subscriberRepository.findById(id);
        return subscriberOptional.orElse(null);
    }


    public Subscribers getSubscriberId(Long id) {
      return subscriberRepository.findById(id).get();
    }


    public boolean isMSISDNAlreadyExists(String msisdn) {
        // Implement the logic to check if a subscriber with the given MSISDN already exists
        Optional<Subscribers> existingSubscriber = subscriberRepository.findByMsisdn(msisdn);
        return existingSubscriber.isPresent();
    }

    public Subscribers findById(Long id) {
                return subscriberRepository.findById(id).get();

            }

    public long countSubscribers(){
        return subscriberRepository.count();
    }

    public long countSubscribersPrepaid() {
        return subscriberRepository.countByServiceType(ServiceType.MobilePrepaid);

    }

    public long countSubscribersPostpaid() {
        return subscriberRepository.countByServiceType(ServiceType.MobilePostpaid);

    }

    public Subscribers findByEmail(String email) {
        Optional<Subscribers> sub = subscriberRepository.findByEmail(email);
        if(sub.isEmpty()) return null;

        return sub.get();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }



    public Subscribers saveUserPassword(Subscribers subscriber) {
        // Hash the user's password before saving it to the database
        String hashedPassword = passwordEncoder.encode(subscriber.getPassword());
        subscriber.setPassword(hashedPassword);

        // Save the subscriber to the database
        subscriberRepository.save(subscriber);
        return subscriber;
    }

    //Authentication Service


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var subscriber = subscriberRepository.findByEmail(request.getEmail()).get();
        var jwtToken = jwtService.generateToken(subscriber);
        var refreshToken = jwtService.generateRefreshToken(subscriber);
        revokeAllUserTokens(subscriber);
        saveUserToken(subscriber, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(Subscribers subscriber, String jwtToken) {
        var token = Token.builder()
                .subscriber(subscriber)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(Subscribers subscriber) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(subscriber.getId().intValue());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }



    //    public AuthenticationResponse register(RegisterRequest request) {
//        var subscriber = Subscribers.builder()
//                .firstname(request.getFirstname())
//                .lastname(request.getLastname())
//                .email(request.getEmail())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .role(request.getRole())
//                .build();
//        var savedUser = subscriberRepository.save(subscriber);
//        var jwtToken = jwtService.generateToken(subscriber);
//        var refreshToken = jwtService.generateRefreshToken(subscriber);
//        saveUserToken(savedUser, jwtToken);
//        return AuthenticationResponse.builder()
//                .accessToken(jwtToken)
//                .refreshToken(refreshToken)
//                .build();
//    }


}

