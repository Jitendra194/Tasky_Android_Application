package com.example.android.taskremindercapstone.dependencyinjection.baseapplicationinjection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by jiten on 1/29/2018.
 */

@Scope
@Retention(RetentionPolicy.CLASS)
public @interface BaseApplicationScope {
}
