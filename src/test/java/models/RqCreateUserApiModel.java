package models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

// Аннотация lombok - создаёт один конструктор со всеми полями класса
@AllArgsConstructor
@Setter
@Getter
public class RqCreateUserApiModel {
    private String name;
    private String job;
}
