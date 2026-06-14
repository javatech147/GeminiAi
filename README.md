- This is the sample project demonstrating Google's Gemini AI integration in Android Applicatin.
- This app is developed in Jetpack Compose. This app is able to answer all the text queries and is similar to ChatGPT/Gemini Application.
- Hilt, Retrofit, Flow is used with MVVM architecture.
- Created separate Interceptor class to pass API Key as Header request.
- API Key is stored on Local machine in `gradle.properties` file (ideally should get from BE server) and accessing through `buildConfigField()`. 
- You can generate API key from here - https://aistudio.google.com/

- Google supports API key authentication in two ways:
-   Option1: Query Parameter:
      POST https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=API_KEY
- Option2: Header
      POST https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent
      Pass header with key name: `x-goog-api-key`
- Here I am using Option2 i.e. passing api key to header



**Preview of the App:** 

<img width="1080" height="2280" alt="image" src="https://github.com/user-attachments/assets/33e10ddd-7a5b-40d3-bd1a-1657990b243a" />
