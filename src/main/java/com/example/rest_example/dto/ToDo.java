package com.example.rest_example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ToDo {

    private int id;
    private int userId;
    private String title;
    private boolean completed;
}
