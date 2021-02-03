package com.example.backup.study.dto;

import com.example.backup.study.domain.entity.Member;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String passWord;
    private String passWordConfirm;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;


    public Member toEntity() {
        return Member.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .passWord(passWord)
                .passWordConfirm(passWordConfirm)
                .build();
    }

    @Builder
    public MemberDto(Long id, String firstName, String lastName, String email, String passWord, String passWordConfirm, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passWord = passWord;
        this.passWordConfirm = passWordConfirm;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

}
