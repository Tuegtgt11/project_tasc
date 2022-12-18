package com.tass.project_tasc.model.response;

import com.tass.project_tasc.model.BasePagingData;
import com.tass.project_tasc.model.BaseResponse;
import com.tass.project_tasc.model.dto.ProductInfo;
import lombok.Data;

import java.util.List;

@Data
public class SearchProductResponse extends BaseResponse {
    private ProductInfo data;

    public SearchProductResponse() {
        super();
    }
    @lombok.Data
    public static class Data extends BasePagingData {

        private List<ProductInfo> items;
    }
}
