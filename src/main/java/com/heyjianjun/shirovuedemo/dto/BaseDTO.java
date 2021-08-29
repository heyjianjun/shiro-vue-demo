package com.heyjianjun.shirovuedemo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author : heyjianjun
 * @create 2021/8/29 18:03
 */
@Data
public class BaseDTO implements Serializable {

    private Long current;

    private Long size;
}
