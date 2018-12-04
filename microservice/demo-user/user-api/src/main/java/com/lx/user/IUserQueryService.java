package com.lx.user;

import com.lx.user.dto.UserQueryRequest;
import com.lx.user.dto.UserQueryResponse;

public interface IUserQueryService {
    UserQueryResponse getUserById(UserQueryRequest request);
}
