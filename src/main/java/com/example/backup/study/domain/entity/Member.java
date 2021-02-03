package com.example.backup.study.domain.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 10, nullable = false)
    private String firstName;

    @Column(length = 10, nullable = false)
    private String lastName;

    @Column(length = 64, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String passWord;

    @Column(length = 100, nullable = false)
    private String passWordConfirm;

    @Builder
    public Member(Long id, String firstName, String lastName, String email, String passWord, String passWordConfirm) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passWord = passWord;
        this.passWordConfirm = passWordConfirm;
    }
}
