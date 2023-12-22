package com.alterdekim.javabot.dto;

import com.alterdekim.javabot.entities.ColumnType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SynergyResult {
    private Long id;
    private String firstEntityText;
    private String secondEntityText;
    private ColumnType firstType;
    private ColumnType secondType;
    private Float probabilityValue;
}
