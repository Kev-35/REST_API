package models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class RsCreateUserApiModel {
    private String name;
    private String job;
    private String id;
    private String createdAt;
}
