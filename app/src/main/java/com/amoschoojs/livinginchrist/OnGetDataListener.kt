package com.amoschoojs.livinginchrist

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError


/**
 * Interface for database listener when data is fetched
 */
interface OnGetDataListener {

    /**
     * Abstract method for when database starts fetching data
     */
    fun onStart()

    /**
     * Abstract method for when database successfully fetched data
     */
    fun onSuccess(data: DataSnapshot?)

    /**
     * Abstract method for when database failed to fetch data
     */
    fun onFailed(databaseError: DatabaseError?)
}