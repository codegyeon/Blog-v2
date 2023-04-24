package com.example.demo.Dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class CommentsRequestDto {


    private String content;



    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
