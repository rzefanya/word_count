@echo off
start "" /b cmd /c "timeout /nobreak 10 >nul & start "" http://localhost:8080"
java -jar count-words-0.0.1.jar