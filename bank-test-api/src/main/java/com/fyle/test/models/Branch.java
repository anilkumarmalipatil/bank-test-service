package com.fyle.test.models;

import lombok.Data;

@Data
public class Branch {

    /*ifsc character varying(11) NOT NULL,
    bank_id bigint,
    branch character varying(74),
    address character varying(195),
    city character varying(50),
    district character varying(50),
    state character varying(26)*/

    private String ifscCode;

    private long bankId;

    private String branch;

    private String address;

    private String city;

    private String district;

    private String state;




}
