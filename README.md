File Organizer
A Java-based file organizer that categorizes and moves files from one folder to specific subfolders based on their file types (e.g., Images, Documents, Videos). It also logs the file movements in a log file.

Features 📂
File Categorization: Organizes files into categories such as Images, Documents, and Videos based on file extensions.
Move Confirmation: Before moving files, it prompts the user for confirmation.
Duplicate Handling: Renames files if a duplicate exists in the target folder.
Logging: Logs every file move in a log.txt file.
Flexible Path Input: Allows the user to specify any folder or defaults to the current directory.
Supported File Types 📚
Images: .jpg, .png, .gif
Documents: .pdf, .docx, .pptx, .txt, xlsx, xls, csv
Videos: .mp4, .avi
Others: Any file types not covered in the categories above are categorized based on their extension.
Requirements 🖥💻
Java 8 or higher
Usage
Clone the repository or download the source code.

Compile the Java file:

javac FileOrganizer.java
Run the program:

java FileOrganizer
Follow the prompts in the terminal:

Enter the folder path to organize, or type 'current' to use the current directory.
Confirm whether you want to start organizing the files.
For each file, confirm if you want to move it to the corresponding folder based on its type.
Example 📝
Organizing Files:
Enter the folder path to organize or type 'current' to use the current directory:
current
Start organizing? (y/n):
y
Move 'image.jpg' to 'Images'? (y/n):
y
Moved: image.jpg -> Images
Move 'document.pdf' to 'Documents'? (y/n):
y
Moved: document.pdf -> Documents
Move 'video.mp4' to 'Videos'? (y/n):
y
Moved: video.mp4 -> Videos
File organization completed.
Log Output:
Moved: image.jpg -> Images
Moved: document.pdf -> Documents
Moved: video.mp4 -> Videos
Screenshot
image

Notes 🧾
The program will create subfolders for each category (e.g., Images, Documents, Videos) if they do not exist.
If a file already exists in the target folder, the program will rename the file by appending a number to the name (e.g., file_1.jpg).
