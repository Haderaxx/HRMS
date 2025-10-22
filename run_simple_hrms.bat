@echo off
echo Starting HRMS Application (Simple Version - No Database Required)...
echo.

REM Compile the simple version
echo Compiling simple HRMS application...
javac HRMSAppSimple.java

if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b 1
)

echo Compilation successful!
echo.

REM Run the simple application
echo Starting Simple HRMS Application...
java HRMSAppSimple

pause
