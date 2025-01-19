package com.zaheer.userservice.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_info")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserInfo {

//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    @Id
    @NonNull
    @JsonProperty("user_id")
    private String userId;


    @NonNull
    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
//    @NonNull
    private String lastName;

    @JsonProperty("phone_number")
    @NonNull
    private Long phoneNumber;

    @JsonProperty("email")
    @NonNull
    private String email;

    @JsonProperty("profile_pic")
    private String profilePic;


}
