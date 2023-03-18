package com.example.quizapp.data

import com.example.quizapp.data.model.Question

val MAX_QUESTION = 5

val questionList1: List<Question> = listOf(
    Question(
        question = "Under which of the following Android is licensed?",
   option1 =  "OSS" , option2 = "Sourceforge" , option3 = "Apache/MIT" , option4 = "None of the above", answer = "Apache/MIT"),

    Question(question = "Which of the following virtual machine is used by the Android operating system?",
       option1 =  "JVM", option2= "Dalvik virtual machine", option3 = "Simple virtual machine", option4 =  "None of the above", answer = "Dalvik virtual machine"),

    Question(question ="Android is based on which of the following language?",
        option1 = "Java", option2 = "C++", option3 = "C", option4 = "None of the above", answer = "Java"),

    Question(question = "Which of the following converts Java byte code into Dalvik byte code?",
       option1 =  "Dalvik converter", option2 = "Dex compiler", option3 = "Mobile interpretive compiler (MIC)", option4 = "None of the above", answer = "Dex compiler"),

    Question(question = "Which of the following kernel is used in Android?",
       option1 =  "MAC", option2 =  "Windows", option3 = "Linux", option4 = "Redhat", answer = "Linux")
)
