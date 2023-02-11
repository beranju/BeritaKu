package com.nextgen.beritaku.utils

import android.util.Patterns

fun String.isEmailValid(): Boolean = Patterns.EMAIL_ADDRESS.matcher(this).matches()