package com.amoschoojs.livinginchrist.networkstream

/**
 * Interface for handling connection status changed
 */
interface OnConnectionStatusChanged {

    /**
     * Abstract method to handle logic of changes of internet connection
     * @param type true means Internet available, false means not available
     */
    fun onChange(type: Boolean)

}