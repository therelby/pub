#!/bin/bash

user="$USERNAME"
resPath="$PWD"

# Complicated step to find the most recent jdk version and apply it to that
expectedPath="C:/Users/${user}/.jdks"
readarray -d '' allJdks < <(find ${expectedPath}/corretto-1.8* -maxdepth 0 -type d -print0)
echo $allJdks
if [ -n "$allJdks" ]; then
    jdkPath=${allJdks[-1]}
    echo "Found jdk at ${jdkPath}"
else
    echo "Could not find Corretto JDK path in ${expectedPath}. Please install it through IntelliJ IDEA."
fi

# First step - JDBC Authorization
cp "${resPath}/mssql-jdbc_auth-8.4.0.x64.dll" "${jdkPath}/bin"
echo "JDBC Authorization complete."

# Second step - ClarkInc certificate
${jdkPath}/bin/keytool.exe -importcert -alias clarkinc256 -keystore "${jdkPath}/jre/lib/security/cacerts" -storepass changeit -file "${resPath}/clarkinc256.cer"

certStatus=$?
if [ $certStatus -eq 0 ]; then
    echo "Completed ClarkInc. certificate setup. Feel free to close this window."
else
    echo "Certificate setup not completed. Error code $certStatus"
fi