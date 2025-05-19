package com.joaquinrouge.donelt.user.dto;

public record AuthResponseDto(String username,String message,String jwt,boolean status) {

}
