package org.dop.module.common.pojo.request;

import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private Integer page;
    private Integer size;
    private String sort;
    private Sort.Direction direction;

    private T filter;
}
