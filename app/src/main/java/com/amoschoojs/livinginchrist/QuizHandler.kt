package com.amoschoojs.livinginchrist




interface QuizHandler {

    fun mReadDataOnce(onGetDataListener: OnGetDataListener)
    fun checkDatabase()
    fun randomSetter(): List<Int> {
        var set= setOf(1,2,3,4)
        val first=set.random()
        set=set.minus(first)
        val second=set.random()
        set=set.minus(second)
        val third=set.random()
        set=set.minus(third)
        val fourth=set.iterator().next()
        return listOf(first,second,third,fourth)

    }
    fun setButtonVal(index:Int,choice:String,list:List<Int>)
    fun quizDisplay()
    fun handleAnswer()
    fun resetButtonState()
    fun disableButtonAfterAnswering()
    fun nextQuestion()





}