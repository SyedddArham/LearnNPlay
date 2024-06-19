# Learn n Play

Learn n Play is an educational app designed to make learning fun for children. The app includes various interactive games and quizzes that help children learn alphabets, shapes, and numbers. It also includes a Parent Portal for tracking the child's progress and providing feedback.

## Features

- **Alphabet Game**: Helps children learn alphabets.
- **Shape Game**: Helps children recognize different shapes.
- **Number Game**: Helps children learn numbers.
- **Parent Portal**: Allows parents to track the progress of their children and provide feedback.
- **User Authentication**: Secure login and user data management using Firebase.
- **User Information**: Retrieve and display user information including name, email, age, and gender from Firebase Firestore.

## Installation

1. **Clone the repository:**
    ```sh
    git clone https://github.com/yourusername/learnnplay.git
    cd learnnplay
    ```

2. **Open the project in Android Studio:**
    - Open Android Studio.
    - Select `Open an existing Android Studio project`.
    - Navigate to the cloned repository and select it.

3. **Add Firebase to your project:**
    - Go to the [Firebase Console](https://console.firebase.google.com/).
    - Create a new project or select an existing project.
    - Add an Android app to your project.
    - Follow the instructions to download the `google-services.json` file and place it in the `app` directory of your Android project.
    - Add Firebase SDK to your project by following the instructions on the Firebase Console.

4. **Sync your project with Gradle files:**
    - Click on `File > Sync Project with Gradle Files`.

## Usage

1. **Run the app:**
    - Connect your Android device or use an emulator.
    - Click on the `Run` button in Android Studio.

2. **Log in or Sign up:**
    - Open the app on your device.
    - Sign up for a new account or log in with an existing account.

3. **Play and Learn:**
    - Explore the different games and activities available in the app.

4. **Parent Portal:**
    - Log in to the Parent Portal to track your child's progress and provide feedback.

## Code Structure

- `MainActivity.java`: Main activity that handles user interactions and navigation.
- `ParentPortalActivity.java`: Activity for the Parent Portal, displaying user's scores and allowing feedback submission.
- `ChangePasswordFragment.java`: Fragment for changing the user's password.
- `FirebaseUserAuthentication.java`: Handles Firebase user authentication.
- `FirebaseFirestoreData.java`: Manages data retrieval and storage from Firebase Firestore.

## Contributing

Contributions are welcome! Please fork the repository and submit a pull request with your changes.

1. Fork the repository.
2. Create a new branch: `git checkout -b my-feature-branch`.
3. Make your changes and commit them: `git commit -m 'Add some feature'`.
4. Push to the branch: `git push origin my-feature-branch`.
5. Submit a pull request.

## Acknowledgments

- Thanks to [Firebase](https://firebase.google.com/) for providing an easy-to-use backend service.
- Special thanks to all the contributors who have helped in making this project better.

## Contact

For any questions or suggestions, feel free to open an issue or contact the project maintainer.
