# TrackMe - Version 1.0 still in progress
This is a real-time tracking app for group of people. Users can easily share their current location with other people within the same group.

The app is being built with the principles of Clean Architecture. These principles help to get the codebase under control and in a more 
manageable state. The app ver 1.0 will contain several layers (modules) to handle principles of Clean Architecture:
- Domain
- Data 
- Remote
- Cache (planned for version 2.0)
- UI



There are implemented range of technologies and libraries to create the project, this includes (not all technologies and libraries are already 
implemented, the app ver. 1.0. is still in progress and all technologies and libraries are subsequently added):

Version 1.0
- Kotlin as a language to code in
- Lifecycle Architecture Components for our View Model classes
- RxJava to handle the orchestration and execution of data operations
- Dagger 2 for Dependency Injection
- Design support library for our UI components
- Espresso for our Android UI tests
- Mockito for handling mock data in our tests
- Firestore for real-time communication

Version 2.0 - upgrade in the future should contain:
- The Room Architecture Component for Realm for local data persistence
- Retrofit for our network request
- Create REST API for writing data to Firestore (client should just listen for data changes)
