package com.taian.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Image {

    String imageKey;

    Long uploadTime;

    Integer articleId;
}
