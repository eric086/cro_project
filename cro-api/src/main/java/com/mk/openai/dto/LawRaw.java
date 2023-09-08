/*
package com.mk.openai.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("law_raw_data")
public class LawRaw {
    @Id
    private String id;

    @Field("law_name")
    private String lawName;

    @Field("law_no")
    private Integer lawNo;

    @Field("law_content")
    private String lawContent;

    private Integer status;

    @CreatedDate
    @Field("created_at")
    private Long createdAt;
}
*/
