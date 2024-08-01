# Star Wars Explorer

This Android application allows users to explore the Star Wars universe by browsing through characters, their details, and homeworlds. It leverages the SWAPI (Star Wars API) to fetch data and present it in a user-friendly manner.

### Technologies Used
1. Kotlin: The primary programming language for the application.
2. Jetpack Compose: Modern UI toolkit for building native Android UIs in a declarative way.
3. Hilt: Dependency injection framework for Android.
4. Apollo GraphQL: Client for interacting with GraphQL APIs.
5. Coroutines: For managing asynchronous operations.
6. Navigation Component: For handling navigation between screens.
7. MVVM pattern: Separates the UI (View), business logic (ViewModel), and data (Model) to improve code organization and maintainability.

### Architecture
The application follows a clean architecture approach, separating concerns into different layers:
* Presentation Layer: Contains UI components built with Jetpack Compose.
* Domain Layer: Defines use cases and business logic.
* Data Layer: Handles data fetching from the API.

### Communication between layers
1. View (UI) to ViewModel: The UI, built with composable functions, interacts with the ViewModel by observing state
2. ViewModel to View (UI): The ViewModel exposes state and events through observable properties like Flow
3. ViewModel to Model: The ViewModel handles the logic for data retrieval from the Model layer (use cases), through repository.
4. Model to ViewModel: The Model layer returns data from Data sources(GraphQL client). The ViewModel then updates the observable state properties, triggering UI updates.
