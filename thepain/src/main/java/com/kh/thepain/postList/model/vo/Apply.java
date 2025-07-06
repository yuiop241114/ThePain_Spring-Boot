package com.kh.thepain.postList.model.vo;

import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Apply {

    private int rmNo;
    private int memberNo;
    private Integer fileNo; // int -> Integer로 변경
    private int postNo;
    private Date applyDate;
    private String read;
    private String readMe;
    private String result;

    // JOB_WRITE_POST 조인 컬럼들
    private String postTitle;
    private String companyName;
    private String hireType;
    private int salaryMin;
    private int salaryMax;
    private String requireDescription;
    private String mainDuties;
    private String niceToHaveSkills;
    private String status;
}
