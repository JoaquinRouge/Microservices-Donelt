package com.joaquinrouge.donelt.user.dto;

public record AuthResponseDto(String username,Long id,String message,String jwt,boolean status) {

}
