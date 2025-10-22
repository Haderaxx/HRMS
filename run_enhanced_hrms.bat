@echo off
echo ========================================
echo    Enhanced HRMS Application Launcher
echo ========================================
echo.

REM Check if XAMPP MySQL is running
echo Checking database connection...

REM Compile all the enhanced application components
echo Compiling enhanced HRMS application...

REM Compile utility classes
javac -cp "lib\mysql-connector-j-9.4.0.jar" -d out src\com\hrms\util\*.java
if %errorlevel% neq 0 (
    echo Error compiling utility classes!
    pause
    exit /b 1
)

REM Compile model classes
javac -cp "lib\mysql-connector-j-9.4.0.jar;out" -d out src\com\hrms\model\*.java
if %errorlevel% neq 0 (
    echo Error compiling model classes!
    pause
    exit /b 1
)

REM Compile DAO classes
javac -cp "lib\mysql-connector-j-9.4.0.jar;out" -d out src\com\hrms\dao\*.java
if %errorlevel% neq 0 (
    echo Error compiling DAO classes!
    pause
    exit /b 1
)

REM Compile UI classes
javac -cp "lib\mysql-connector-j-9.4.0.jar;out" -d out src\com\hrms\ui\*.java
if %errorlevel% neq 0 (
    echo Error compiling UI classes!
    pause
    exit /b 1
)

REM Compile service classes (if any)
javac -cp "lib\mysql-connector-j-9.4.0.jar;out" -d out src\com\hrms\service\*.java
if %errorlevel% neq 0 (
    echo Warning: Service classes compilation failed (may not exist yet)
)

REM Compile controller classes (if any)
javac -cp "lib\mysql-connector-j-9.4.0.jar;out" -d out src\com\hrms\controller\*.java
if %errorlevel% neq 0 (
    echo Warning: Controller classes compilation failed (may not exist yet)
)

REM Compile main launcher
javac -cp "lib\mysql-connector-j-9.4.0.jar;out" -d out HRMSLauncher.java
if %errorlevel% neq 0 (
    echo Error compiling main launcher!
    pause
    exit /b 1
)

echo Compilation successful!
echo.

REM Check if database exists
echo Checking database setup...
echo Please ensure you have run the enhanced_hrms_schema.sql in phpMyAdmin
echo.

REM Run the enhanced application
echo Starting Enhanced HRMS Application...
echo.
echo Login Credentials:
echo - Username: admin, Password: admin
echo - Username: hr, Password: hr  
echo - Username: user, Password: user
echo.

java -cp "lib\mysql-connector-j-9.4.0.jar;out" HRMSLauncher

if %errorlevel% neq 0 (
    echo.
    echo Application exited with error!
    echo Please check the error messages above.
    pause
)
