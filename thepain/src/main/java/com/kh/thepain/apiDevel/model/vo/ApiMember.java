package com.kh.thepain.apiDevel.model.vo;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ApiMember {

    private int memberNo;
    private String email;
    private String enterpriseName;
    private int enterpriseNo;
    private String gitUserName;
    private String memberName;
    private String phone;
    private List<Skills> skills;
}
