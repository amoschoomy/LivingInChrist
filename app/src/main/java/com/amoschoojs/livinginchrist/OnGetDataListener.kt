package com.amoschoojs.livinginchrist

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError


interface OnGetDataListener {

    fun onStart()
    fun onSuccess(data: DataSnapshot?)
    fun onFailed(databaseError: DatabaseError?)
}