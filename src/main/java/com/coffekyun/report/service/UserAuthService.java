package com.coffekyun.report.service;

import com.coffekyun.report.model.request.UserAuthRequest;
import com.coffekyun.report.model.response.UserAuthReponse;

public interface UserAuthService {

    UserAuthReponse addUserAuth(UserAuthRequest userAuthRequest);

}
