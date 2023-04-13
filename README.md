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

- Initialisation
  - Establish database connection
  - Fetch the questions from database which is stored in the ViewModel
    <img src="https://user-images.githubusercontent.com/127374103/231672363-f20e2078-fed1-4b37-b0b3-0f0a28b9cf86.png" width="600px">

- HomeFragment
  - When the app is launched the HomeFragment is displayed with a start button
  - When user clicks on the start button then the quiz starts and the questions are displayed one by one in the QuizFragemnt
    <img src="https://user-images.githubusercontent.com/127374103/231673773-c6f507c9-f1f7-4cc6-bff5-7806092a91d7.png" height="200px">

  
- QuizFragment - Displays the question and its options 
  - In the QuizFragment user can navigate between questions using the previous and the next button
  - On the last question the next button shows 'Submit' and user can submit the quiz 
  - Then the answers are evaluated 
 
    <img src="https://user-images.githubusercontent.com/127374103/231673344-9204af9c-3705-4b96-8b1e-bae7ac9537ba.png" height="200px">
    <img src="https://user-images.githubusercontent.com/127374103/231674151-67b83db6-43fc-458d-861f-ad6ad6ff0179.png" height="200px">


- QuizWonFragment
  - If the user has answered all the questions correctly then he is taken to the QuizWonFragment which has a button to take him to a URL in a web browser 
  
    <img src="https://user-images.githubusercontent.com/127374103/231673851-298f9ea1-5e98-4d8c-bf26-1046e078b8c5.png" height="200px">


- QuizOverFragment
  - Else he is taken to the QuizOverFragment where he is shown the total number of questions, no. of questions he attempted and no. of correct answers. 
  - This fragment has a 'Try Again' button which on clicked takes the user back to the QuizFragment and user can try to answer the same set of questions again
  
    <img src="https://user-images.githubusercontent.com/127374103/231673912-fbf2707c-e7a8-4cb6-aa00-7087cabe318f.png" height="250px">


- If user presses the back button from the last two fragments (QuizWonFragment or QuizOverFragment) then he is taken to the HomeFragment and there he can start a completely new quiz (new set of questions) by clicking on the start button
