package com.example.demo.Dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class RepliesRequestDto {

    private String content;



    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
