package com.daoy.app.security;

import com.daoy.backend.data.entity.User;

@FunctionalInterface
public interface CurrentUser {

	User getUser();
}
