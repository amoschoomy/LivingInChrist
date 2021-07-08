package com.amoschoojs.livinginchrist

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.*

class TimedQuiz : AppCompatActivity(),QuizHandler {

    private lateinit var database: FirebaseDatabase
    private val arrayList=ArrayList<Quiz>()
    private lateinit var questionQuiz: TextView
    private lateinit var answer:String
    private lateinit var choice1: Button
    private lateinit var choice2: Button
    private lateinit var choice3: Button
    private lateinit var choice4: Button
    private lateinit var dataReference: DatabaseReference
    private lateinit var nextButton: Button
    private lateinit var scoreView: TextView
    private var count=0
    private var correct=false
    private var answered=false
    private var timeExceeded=false
    private lateinit var animation: ObjectAnimator
    private lateinit var backgroundTintList: ColorStateList


    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timed_quiz)
        progressBar = findViewById(R.id.progressBar)
        database= FirebaseDatabase.getInstance("https://living-in-christ-default-rtdb.asia-southeast1.firebasedatabase.app")
        database.setPersistenceEnabled(true)
        dataReference=database.reference.child("quizzes")

        questionQuiz=findViewById(R.id.questiontimedquiz)
        choice1=findViewById(R.id.choice1timed)
        choice2=findViewById(R.id.choice2timed)
        choice3=findViewById(R.id.choice3timed)
        choice4=findViewById(R.id.choice4timed)
        nextButton=findViewById(R.id.nextq)
        scoreView=findViewById(R.id.scorequiz)
        backgroundTintList= choice1.backgroundTintList!!

        checkDatabase()




    }







    private fun countTime() {
        animation = ObjectAnimator.ofInt(progressBar, "progress", 0, 100);
        animation.duration = 30000;
        animation.interpolator = DecelerateInterpolator();
        animation.addListener(object: Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {
            }

            override fun onAnimationEnd(p0: Animator?) {
                if(!answered){
                Toast.makeText(applicationContext,"Time has exceeded sorry!",Toast.LENGTH_SHORT).show()
                disableButtonAfterAnswering()
                timeExceeded=true
            }
                calcScore()
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationRepeat(p0: Animator?) {
            }

        })
        animation.start()


    }

    override fun mReadDataOnce(listener: OnGetDataListener) {
        listener.onStart();
        dataReference.addListenerForSingleValueEvent (
            object:ValueEventListener{
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    listener.onSuccess(dataSnapshot);
                }


                override fun onCancelled(databaseError: DatabaseError) {
                    listener.onFailed(databaseError);
                }
            }
        )
    }

    override fun checkDatabase() {
        mReadDataOnce(object : OnGetDataListener {
            override fun onStart() {
            }

            override fun onSuccess(data: DataSnapshot?) {
                //DO SOME THING WHEN GET DATA SUCCESS HERE
                if (data != null) {
                    for (d in data.children!!) {
                        d.getValue(Quiz::class.java)?.let { arrayList.add(it) }
                    }
                }
                arrayList.shuffle()
                quizDisplay()
                count+=1
                handleAnswer()


                nextButton.setOnClickListener {
                    if (answered || timeExceeded) {
                        nextQuestion()
                    }
                    else{
                        MaterialAlertDialogBuilder(this@TimedQuiz).setMessage("Do you want to skip? " +
                                "You have not answered the question.").setTitle("Confirmation").setPositiveButton("Yes"){
                                _,_-> nextQuestion() }.setNegativeButton("No",null).show()
                    }
                }

            }

            override fun onFailed(databaseError: DatabaseError?) {
                //DO SOME THING WHEN GET DATA FAILED HERE
            }
        })
    }

    override fun setButtonVal(index: Int, choice: String, list: List<Int>) {
        when(list[index]) {
            1 -> {
                choice1.text=choice
            }
            2 -> {
                choice2.text=choice
            }
            3 -> {
                choice3.text=choice
            }
            4 -> {
                choice4.text=choice
            }
        }
    }

    override fun quizDisplay() {
        if (count<arrayList.size){
            val quiz=arrayList[count]
            answer=quiz.answer
            questionQuiz.text=quiz.question
            val list=randomSetter()
            setButtonVal(0,quiz.choice1,list)
            setButtonVal(1,quiz.choice2,list)
            setButtonVal(2,quiz.choice3,list)
            setButtonVal(3,quiz.choice4,list)
            countTime()



        }
        else{
            finish()
            val scoreRound=scoreView.text.toString().filter { it.isDigit()}
            Toast.makeText(this, "Finished quiz. Thank you for playing. Your score is : $scoreRound"
                ,Toast.LENGTH_SHORT).show()
            val sharedPreferences=getSharedPreferences("abc",0)
            val prevHighScore=sharedPreferences.getInt("highscore",0)

            if (scoreRound.toInt()>prevHighScore){
                sharedPreferences.edit().putInt("highscore",scoreRound.toInt()).apply()
            }

        }

        answered=false
        timeExceeded=false
    }

    override fun handleAnswer() {
        var answerId: Int? =null
        val shake: Animation = AnimationUtils.loadAnimation(this, R.anim.shake)

        when(answer){
            choice1.text ->{answerId=choice1.id}
            choice2.text ->{answerId=choice2.id}
            choice3.text ->{answerId=choice3.id}
            choice4.text ->{answerId=choice4.id}
        }

        choice1.setOnClickListener {
            if (answerId==choice1.id){
                choice1.startAnimation(shake)
                choice1.backgroundTintList=this.resources.getColorStateList(R.color.green,this.theme)
                correct=true

            }
            else{
                if (answerId != null) {
                    val button=findViewById<Button>(answerId)
                    button.backgroundTintList=this.resources.getColorStateList(R.color.green,this.theme)
                    button.startAnimation(shake)
                    choice1.backgroundTintList=this.resources.getColorStateList(R.color.red,this.theme)

                }
            }
            answered=true
            disableButtonAfterAnswering()
            animation.cancel()
            vibrateAns(this)
                    }

        choice2.setOnClickListener {
            if (answerId==choice2.id){
                choice2.startAnimation(shake)
                choice2.backgroundTintList=this.resources.getColorStateList(R.color.green,this.theme)
                correct=true

            }
            else{
                if (answerId != null) {
                    val button=findViewById<Button>(answerId)
                    button.backgroundTintList=this.resources.getColorStateList(R.color.green,this.theme)
                    button.startAnimation(shake)
                    choice2.backgroundTintList=this.resources.getColorStateList(R.color.red,this.theme)
                }
            }
            answered=true
            disableButtonAfterAnswering()
            animation.cancel()
            vibrateAns(this)

        }
        choice3.setOnClickListener {
            if (answerId==choice3.id){
                choice3.startAnimation(shake)
                choice3.backgroundTintList=this.resources.getColorStateList(R.color.green,this.theme)
                correct=true
            }
            else{
                if (answerId != null) {
                    val button=findViewById<Button>(answerId)
                    button.backgroundTintList=this.resources.getColorStateList(R.color.green,this.theme)
                    button.startAnimation(shake)
                    choice3.backgroundTintList=this.resources.getColorStateList(R.color.red,this.theme)
                }
            }
            answered=true
            disableButtonAfterAnswering()
            animation.cancel()
            vibrateAns(this)

        }
        choice4.setOnClickListener {
            if (answerId==choice4.id){
                choice4.startAnimation(shake)
                choice4.backgroundTintList=this.resources.getColorStateList(R.color.green,this.theme)
                correct=true

            }
            else{
                if (answerId != null) {
                    val button=findViewById<Button>(answerId)
                    button.backgroundTintList=this.resources.getColorStateList(R.color.green,this.theme)
                    button.startAnimation(shake)
                    choice4.backgroundTintList=this.resources.getColorStateList(R.color.red,this.theme)
                }
            }
            answered=true
            disableButtonAfterAnswering()
            animation.cancel()
            vibrateAns(this)

        }


    }

    override fun resetState() {
        choice1.backgroundTintList=backgroundTintList
        choice2.backgroundTintList=backgroundTintList
        choice3.backgroundTintList=backgroundTintList
        choice4.backgroundTintList=backgroundTintList
        choice1.isEnabled=true
        choice2.isEnabled=true
        choice3.isEnabled=true
        choice4.isEnabled=true
        correct=false

    }

    override fun disableButtonAfterAnswering() {
        choice1.isEnabled=false
        choice2.isEnabled=false
        choice3.isEnabled=false
        choice4.isEnabled=false
    }

    override fun nextQuestion() {
        quizDisplay()
        count += 1
        resetState()
        handleAnswer()
    }

    override fun onBackPressed() {

        super.onBackPressed()
        animation.removeAllListeners()
        finish()
    }

    private fun calcScore(){
        if (correct){
            var score=scoreView.text.toString().filter { it.isDigit() }.toInt()
            score += (100 - (1 * progressBar.progress))
            scoreView.text="Score: "+score.toString()

        }
    }

}