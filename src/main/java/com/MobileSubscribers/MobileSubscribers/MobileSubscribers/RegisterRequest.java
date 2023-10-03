package com.MobileSubscribers.MobileSubscribers.MobileSubscribers;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Role role;
    private String msisdn;
    private Integer customer_id_owner;
    private Integer customer_id_user;
    private ServiceType serviceType;
    private Long unixEpochMillis;

}