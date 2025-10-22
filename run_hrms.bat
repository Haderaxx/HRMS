@echo off
echo Starting HRMS Application...
echo.

REM Check if XAMPP MySQL is running
echo Checking database connection...

REM Compile the application
echo Compiling application...
javac -cp "lib\mysql-connector-j-9.4.0.jar" -d out src\com\hrms\util\*.java src\com\hrms\dao\*.java src\com\hrms\model\*.java src\com\hrms\service\*.java src\com\hrms\controller\*.java
javac -cp "lib\mysql-connector-j-9.4.0.jar;out" -d out HRMSApp.java

if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b 1
)

echo Compilation successful!
echo.

REM Run the application
echo Starting HRMS Application...
java -cp "lib\mysql-connector-j-9.4.0.jar;out" HRMSApp

pause
