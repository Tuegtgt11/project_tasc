package com.tass.project_tasc.model;

import lombok.Data;

@Data
public class BasePagingData {
    private int currentPage;
    private int pageSize;
    private long totalItem;
}
