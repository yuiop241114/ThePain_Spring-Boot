package com.kh.thepain.apiDevel.model.vo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JobPost {
    /*'
    jp.RECRUITMENT_NO
     , jp.RECRUITMENT_DATE
     , jp.COUNT
     , jp.DEADLINE
     , jw.COMPANY_NAME
     , jw.POST_TITLE
     , jw.HIRE_TYPE
     , jw.START_SALARY
     , jw.END_SALARY
     , jw.REQUIRE_DESCRIPTION
     , jw.MAIN_DUTIES
     , jw.NICE_TO_HAVE_SKILLS
     */

    private int postNo; //RECRUITMENT_NO
    private String postEnrollDate; //RECRUITMENT_DATE
    private int count;
    private String deadline;

    private String companyName;
    private String postTitle;
    private String hireType;
    private int startSalary;
    private int endSalary;
    private String requireDescription;
    private String mainDuties;
    private String requiredSkills; //niceToHaveSkills

}
