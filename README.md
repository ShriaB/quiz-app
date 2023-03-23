Quiz Application
===================
 

Introduction
-----------------

Quiz Application is an app that has a set of questions where the user can practice solving technical MCQs.


Features
-----------------
- It has a single activity that hosts multiple fragments.
- It has a database to store the questions locally (implemented using Room Database)



Working
-----------------

- HomeFragment
  - When the app is launched the HomeFragment is displayed with a start button
  - When user clicks on the start button then the quiz starts and the questions are displayed one by one in the QuizFragemnt
  
- QuizFragment - Displays the question and its options 
  - In the QuizFragment user can navigate between questions using the previous and the next button
  - On the last question the next button shows 'Submit' and user can submit the quiz 
  - Then the answers are evaluated 

- QuizWonFragment
  - If the user has answered all the questions correctly then he is taken to the QuizWonFragment which has a button to take him to a URL in a web browser

- QuizOverFragment
  - Else he is taken to the QuizOverFragment where he is shown the total number of questions, no. of questions he attempted and no. of correct answers. 
  - This fragment has a 'Try Again' button which on clicked takes the user back to the QuizFragment and user can try to answer the same set of questions again

- If user presses the back button from the last two fragments (QuizWonFragment or QuizOverFragment) then he is taken to the HomeFragment and there he can start a completely new quiz (new set of questions) by clicking on the start button