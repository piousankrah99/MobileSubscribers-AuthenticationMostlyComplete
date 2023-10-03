package com.MobileSubscribers.MobileSubscribers.MobileSubscribers;

import jakarta.persistence.*;
import lombok.*;

import javax.persistence.Column;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;




@ToString
@Data
@Setter
@Getter
@Entity
@Builder
@Table(name = "subscribers")
@AllArgsConstructor// automating the process of creating a constructor that sets all the fields...


public class Subscribers implements UserDetails{

    @Id
    @SequenceGenerator(
            name = "Subscribers_sequence",
            sequenceName = "Subscribers_sequence",
            allocationSize = 1
    )


    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "subscribers_sequence"
    )
    private Long id;
    private String msisdn;
    private Integer customer_id_owner;
    private Integer customer_id_user;
    private String firstname;
    private String lastname;
    private String email;
    @Getter
    private String password;

    @jakarta.persistence.Enumerated(jakarta.persistence.EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "subscriber")
    private List<Token> tokens;



    public Subscribers(String admin) {
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }


    public String getUsername() {
        return email;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }



    @Column(name = "unix_epoch_millis")
    private Long unixEpochMillis;

    @Enumerated(EnumType.STRING) // Specify how the enum values should be stored (as strings)
    @Column(name = "service_type")
    private ServiceType serviceType; // Use the enum type here


    public Subscribers() {
        this.unixEpochMillis = System.currentTimeMillis();
    }



    public Subscribers(Long id, java.lang.String msisdn,String firstname,String lastname,String email,String password, Role role, Integer customer_id_owner, Integer customer_id_user,
                       ServiceType serviceType, Long unixEpochMillis) {
        this.id = id;
        this.msisdn = msisdn;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.customer_id_owner = customer_id_owner;
        this.customer_id_user = customer_id_user;
        this.serviceType = serviceType;
        this.unixEpochMillis = unixEpochMillis;
    }



    public Subscribers(java.lang.String msisdn,String firstname,String lastname,String email,String password, Role role, Integer customer_id_owner, Integer customer_id_user,
                       ServiceType serviceType, Long unixEpochMillis) {
        this.msisdn = msisdn;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.customer_id_owner = customer_id_owner;
        this.customer_id_user = customer_id_user;
        this.serviceType = serviceType;
        this.unixEpochMillis = unixEpochMillis;
    }


    public void setServiceType(ServiceType service_type) {
        // Assuming 'service_type' is an instance variable in the Subscribers class.
        this.serviceType = service_type; // Set the instance variable with the provided value.
    }


    @Override
    public String toString() {
        return "Subscribers{" +
                "id=" + id +
                ", msisdn='" + msisdn + '\'' +
                ", customer_id_owner=" + customer_id_owner +
                ", customer_id_user=" + customer_id_user +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", tokens=" + tokens +
                ", unixEpochMillis=" + unixEpochMillis +
                ", serviceType=" + serviceType +
                '}';
    }

}


