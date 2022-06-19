package com.br.presentation.extensions

import android.util.Log

private const val TAG = "MyLog"

fun debugLog(message: Any) = Log.d(TAG, message.toString())

fun errorLog(message: Any) = Log.e(TAG, message.toString())