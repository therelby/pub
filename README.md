# Automation Team Testing Framework 

# Getting Started
1. Check near the tab area above this README for a blue banner that states `Safe mode, limited Functionality`. 
   - If it exists, click "Trust project...", and click the "Trust Project" button in the resulting window to continue.
   - If it does not exist, no further action is required.
2. Press shift twice to open a search bar, and type `InstallHealth.groovy`
   1. A blue banner should appear prompting a JDK install for Amazon Corretto 1.8.0
   2. Click "Download JDK"
   3. If there is an error, or the banner does not appear, go to `File -> Project Structure -> Project Settings -> Project`
   and click the select box for `Project SDK`.
      1. Go to `Add SDK -> Download JDK`
      2. Select `Version 1.8`
      3. Select `Amazon Corretto`
      4. Click the "Download" button
      5. Click "OK" to close the settings window
3. Click the "Build Project" Icon, or press CTRL+F9 to build the project.
4. While wait for the build to finish (loading bar on the bottom right), do the following:
   1. [Configuring the terminal in IDEA here](https://tfs.clarkinc.biz/DefaultCollection/Automation%20Projects/_wiki/wikis/Automation-Projects.wiki/1398/Configuring-Terminal-In-IDEA)
   2. [Follow the guide on setting file templates](https://tfs.clarkinc.biz/DefaultCollection/Automation%20Projects/_wiki/wikis/Automation-Projects.wiki/3126/File-Template-Setup)
   3. [Follow the guide for live templates](https://tfs.clarkinc.biz/DefaultCollection/Automation%20Projects/_wiki/wikis/Automation-Projects.wiki/3860/Installing-Live-Templates-(verify-UUID-etc))
   4. [Complete "Installing Certificates and Database Authorization"](#Installing-Certificates-and-Database-Authorization)

# Installing Certificates and Database Authorization
1. In IntelliJ IDEA, press shift twice and type`certSetup.sh`.
2. Right-click anywhere in the code window for `certSetup.sh` and click `Run 'certSetup.sh'`. The option should have a green arrow icon.
3. If your IDEA terminal is set for Git Bash, a prompt will appear in IDEA's terminal, otherwise it will appear in a 
new Git Bash window.
4. When it prompts `Trust this certificate? [no]`, type `y` and click enter.
5. There should be a prompt as follows:
   ```bash
    Certificate was added to keystore
    Completed ClarkInc. certificate setup.
    ```
   1. If this step fails, check the error text to see if there are already existing certificates (if there are, then certificates
have been successfully installed).
7. Run the script `InstallHealth.groovy`
   1. If the file is not open:
      1. Press shift twice to open a search bar
      2. Start typing in `InstallHealth.groovy` and click the resulting file
   2. Click on the green arrow near `public static void main` or right click anywhere and select `Run 'InstallHealth.main()'`
8. Look for the green messages. If there are red messages, there was a problem installing certificates. Follow the 
instructions in the error messages and try again.

#Documentation
https://tfs.clarkinc.biz/DefaultCollection/Automation%20Projects/_wiki/wikis/Automation-Projects.wiki?wikiVersion=GBwikiMaster&pagePath=%2FAutomation%20Framework&pageId=1372
