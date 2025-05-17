package org.dop.module.common.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageRq<T> {

    private Integer page;
    private Integer size;
    private String sort;
    private Sort.Direction direction;

    private T filter;
}
