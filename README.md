# LangMaster - Language Learning Android App

> A comprehensive Android application designed to help users expand their language skills (English and German) through interactive vocabulary quizzes, grammar exercises, and cultural trivia. 

## Project Overview
LangMaster is a full-featured educational mobile app. It provides a secure, personalized learning environment where users can create accounts, track their progress in 10-word session quizzes, and utilize an integrated multi-language translator. 

### Core Features & Architecture:
* **MVP Architecture:** The project is structured using the Model-View-Presenter design pattern, ensuring a clean separation of concerns between the UI (Activities) and business logic.
* **Secure Authentication:** User registration and login system with real-time validation (preventing duplicate usernames) to ensure a personalized experience.
* **Interactive Vocabulary Quizzes:** Users select a category and complete 10-word translation sessions. The app provides immediate feedback on incorrect answers and calculates a final score (e.g., 8/10).
* **Grammar & Sentence Completion:** Fill-in-the-blank exercises to practice correct grammatical structures in context.
* **Integrated Translator:** A built-in, free-text translation tool supporting multiple languages.
* **Dictionary & Trivia:** Access to categorized word lists and cultural facts (e.g., history of famous landmarks like Big Ben) to broaden general knowledge.

## Tech Stack
* **Frontend:** Android SDK, Java, XML Layouts
* **Architecture:** MVP (Model-View-Presenter)
* **Backend / Database:** Direct JDBC connection to an SQL Database (`DatabaseConnector.java`)
* **Build Tool:** Gradle

## Application Gallery



| Main Menu | Vocabulary Quiz | Vocabulary List |
|:---:|:---:|:---:|
| <img src="https://github.com/user-attachments/assets/bc226502-6d9d-4333-adfc-fa4f70db0ba7" width="280" alt="Main Menu" /> | <img src="https://github.com/user-attachments/assets/4aa9578d-f8d1-4ef6-951d-c72845050cf3" width="280" alt="Vocabulary Quiz" /> | <img src="https://github.com/user-attachments/assets/d0916b25-bbab-4f75-a193-6d7695b4074f" width="280" alt="Vocabulary List" /> |

| Sentence Completion | Integrated Translator | Cultural Trivia |
|:---:|:---:|:---:|
| <img src="https://github.com/user-attachments/assets/0e687b78-a8a3-45d0-95e8-dee6af8d94b2" width="280" alt="Sentence Completion" /> | <img src="https://github.com/user-attachments/assets/81d4a685-0b36-4f3c-823b-fe2f8d58dc14" width="280" alt="Integrated Translator" /> | <img src="https://github.com/user-attachments/assets/4787fd6b-dea3-4274-b7b9-d69dc5529e12" width="280" alt="Cultural Trivia" /> |

## Note on Running Locally
This project was initially connected to a private database to fetch dynamic content (words, sentences, users). 

For security reasons, the direct database credentials have been removed from the repository. To run this application locally and view the content:
1. Clone the repository and open it in Android Studio.
2. Set up your own SQL database with the required tables (users, vocabulary, trivia).
3. Navigate to `src/main/java/.../DatabaseConnector.java` and update the connection string, username, and password to point to your local/cloud SQL instance.
