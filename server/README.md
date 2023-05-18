# Server Group XX.

## Replace all placeholders by your groups number/name.

Create an issue (e.g., *Initial Configuration Server*) to implement the following changes in your repository:

- [ ] **gradle.properties**: `sonar.projectName` and `sonar.projectKey`: replace the XX by your groups number.
- [ ] **server > conf > config-deploy.json**: port should be 8000 + groups number. Now, copy the EXAMPLE config and call it "config.json". Pick any port, and the url of the API should be: `https://project-i.ti.howest.be/2022-2023/battleship-api-spec/openAPI-battleship.yaml` (Do not forget it is a String).
- [ ] **server > src > main > resources > vertx-default-jul-logging.properties**: replace the XX in `java.util.logging.FileHandler.pattern` by your groups number.
- [ ] **server > src > main > java > be.howest.ti.battleship > web > WebServer.java**: The constant `EXPECTED_LOGGER_FILE_NAME` should be updated like the one above.

Commit and push your changes.
If all went well, then you should be able to visit your server at `https://project-i.ti.howest.be/battleship-XX/api/info`.

If so, checkout the main branch, and run the sonarqube task.

Remove this section from the readme if ready.
