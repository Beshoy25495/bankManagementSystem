package com.bwagih.bank.management.system.utils;

public final class Defines {

    // ConstantStrings for JWT Security
    public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 1000 * 60 * 60  *10;//10 hours
    public static final String SIGNING_KEY = "secretkey";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

}
