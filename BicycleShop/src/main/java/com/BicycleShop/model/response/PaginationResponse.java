package com.BicycleShop.model.response;

import com.BicycleShop.model.dto.bicycle.BicycleSearchDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaginationResponse<T> implements Serializable {
    public List<T> content;
    private Pagination pagination;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pagination implements Serializable {
        private int page;
        private int pages;
        private int limit;
        private long total;
    }

}
