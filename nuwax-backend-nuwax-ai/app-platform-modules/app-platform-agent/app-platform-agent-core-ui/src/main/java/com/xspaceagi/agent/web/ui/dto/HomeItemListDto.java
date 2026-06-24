package com.xspaceagi.agent.web.ui.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class HomeItemListDto {

    @Schema(description = "首页分类")
    private List<HomeCategoryDto> categories;

    @Schema(description = "首页分类数据列表")
    private Map<String, List<HomeItemDto>> categoryItems;

    public List<HomeCategoryDto> getCategories() {
        if (categories == null) {
            categories = new ArrayList<>();
        }
        return categories;
    }

    public Map<String, List<HomeItemDto>> getCategoryItems() {
        if (categoryItems == null) {
            categoryItems = new HashMap<>();
        }
        return categoryItems;
    }
}
