### Book Management Application

This is a Book Management application developed as a group assignment. The application allows users to manage books by creating and saving data to a local SQLite database using Room. It enables users to list books, add books to bookmarks, and more. This assignment aims to test the group's capability in developing an Android application that manages local data, integrates with the Room database, and provides useful features like searching, adding to bookmarks, and displaying relevant book information.

<p align="center">
  <a href="https://github.com/CrispenGari/AndroidAssignment2/blob/main/LICENSE">
    <img src="https://img.shields.io/badge/license-MIT-green.svg" alt="License: MIT">
  </a>
  <a href="https://developer.android.com/studio">
    <img src="https://img.shields.io/badge/language-java-red.svg" alt="Language: Java">
  </a>
</p>

### 🤝 Collaborators

Thanks to these amazing people for their contributions:

<table align="center">
  <tr>
    <td align="center">
      <a href="https://github.com/ThibazaMlambo">
        <img src="https://github.com/ThibazaMlambo.png" width="80px;" alt="The ThibazaMlambo"/>
        <br /><sub><b>Thibaza Mlambo</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/CrispenGari">
        <img src="https://github.com/CrispenGari.png" width="80px;" alt="Crispen Gari"/>
        <br /><sub><b>Crispen Gari</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/DbyProsper">
        <img src="https://github.com/DbyProsper.png" width="80px;" alt="The DbyProsper"/>
        <br /><sub><b>Prosper Masuku</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/PNomnga">
        <img src="https://github.com/PNomnga.png" width="80px;" alt="The PNomnga"/>
        <br /><sub><b>P. Nomnga</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/Kingsleyxelo">
        <img src="https://github.com/Kingsleyxelo.png" width="80px;" alt="The Kingsleyxelo"/>
        <br /><sub><b>Kingsleyxelo</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/siphocraig">
        <img src="https://github.com/siphocraig.png" width="80px;" alt="The siphocraig"/>
        <br /><sub><b>siphocraig</b></sub>
      </a>
    </td>
  </tr>
</table>

### Tech Stack

- **Java**: The main programming language used for developing the app.
- **Room Persistence Library**: To manage the local SQLite database.
- **RecyclerView**: To display books in a list format.
- **Glide**: For loading and displaying book cover images.
- **Material Design Components**: For modern UI components.
- **SharedPreferences**: For storing user bookmarks locally.
- **Android SDK**: Basic tools and libraries used to build the app.

### Features

- **Manage Books**: Add and save books with details such as title, author, price, cover image, and more.
- **Book List**: View a list of all saved books.
- **Bookmarks**: Add books to bookmarks and view them later.
- **Search Functionality**: Search for books by title or author.
- **Settings**: View app version, clear bookmarks, and report issues on GitHub.

### Database Schema

The app uses the Room Persistence Library to store data in a local SQLite database. The database schema includes:

- **Book**: A table containing details about books (title, author, cover image, etc.).
- **User**: A table containing user information (seller).

### How It Works

1. **Adding Books**: Users can add books through the app’s interface. The books are stored locally using Room.
2. **Viewing Books**: Books are listed in a RecyclerView, showing their title, author, number of copies, and cover image.
3. **Bookmarks**: Users can add books to bookmarks. Bookmarked books are stored in SharedPreferences.
4. **Search**: A search bar allows users to filter books based on the title or author.
5. **Settings**: Users can view the app version, clear their bookmarks, and report issues on GitHub via the Settings screen.

### Installation

To set up and run the app, follow these steps:

1. Clone the repository:
   ```bash
   git clone https://github.com/CrispenGari/AndroidAssignment2.git
   ```
2. Open the project in Android Studio.
3. Ensure all dependencies are set up in the build.gradle file.
4. Run the app on an emulator or a physical device.

### Screenshots

- Coming soon.

### Known Issues

- None at the moment.

### Future Improvements

- Add user authentication to allow personalized bookmarks.
- Implement a better UI for managing and viewing books.
- Add support for sharing books with others.

### License

This project is licensed under the MIT License - see the [LICENSE](https://github.com/CrispenGari/AndroidAssignment2/blob/main/LICENSE) file for details.
