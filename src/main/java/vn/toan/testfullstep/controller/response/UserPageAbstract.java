package vn.toan.testfullstep.controller.response;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class UserPageAbstract implements Serializable {
    public int pageNumber;
    public int pageSize;
    public long totalPages;
    public long totalElements;
}
