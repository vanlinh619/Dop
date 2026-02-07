package org.dop.module.manageuser.pojo.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.dop.module.common.validation.anotation.AllowSortName;
import org.dop.module.manageuser.pojo.response.UserInfoResponse;
import org.springframework.data.domain.Sort;

@Data
public class UserPageRequest {

    @NotNull
    @Positive
    private Integer page;

    @NotNull
    @Min(25)
    @Max(100)
    private Integer size;

    @AllowSortName(allowSortFields = {
            UserInfoResponse.Fields.fullName,
            UserInfoResponse.Fields.email,
    })
    private String sortName;

    @NotNull
    private Sort.Direction direction;

    private String search;
}
