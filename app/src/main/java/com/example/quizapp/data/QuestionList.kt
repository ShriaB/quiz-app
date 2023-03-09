package com.example.quizapp.data

import com.example.quizapp.model.Question

val MAX_QUESTION = 5
val questionList: List<Question> = listOf(
    Question("Under which of the following Android is licensed?",
    "OSS" ,"Sourceforge" ,"Apache/MIT" ,"None of the above","Apache/MIT"),

    Question("Which of the following virtual machine is used by the Android operating system?",
        "JVM","Dalvik virtual machine", "Simple virtual machine", "None of the above","Dalvik virtual machine"),

    Question("Android is based on which of the following language?",
        "Java", "C++", "C","None of the above","Java"),

    Question("Which of the following converts Java byte code into Dalvik byte code?",
        "Dalvik converter", "Dex compiler", "Mobile interpretive compiler (MIC)","None of the above","Dex compiler"),

    Question("Which of the following kernel is used in Android?",
        "MAC", "Windows", "Linux","Redhat","Linux")
)