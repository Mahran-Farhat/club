package com.isikef.club.forms;


import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springdoc.core.annotations.ParameterObject;

@Getter
@Setter
public class ClubForm {
    @NotNull(message = "name can not be null")
    private String name;

    @NotNull(message = "description can not be null")
    @Size(max = 1000, message = "description max length is 1000 characters")
    @Size(min = 3, message = "description min length is 3 characters")
    private String description;

    public String toString(){
        return "ClubForm{name='" + name + "'," +
                " description='" + description + "'}";
    }
}
