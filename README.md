# ModFest Mod Templates!

Mod templates tailored towards use at [ModFest](https://modfest.net/) events.

## To Use:

### Reserve your Modrinth slug

On [Modrinth](https://modrinth.com/):
- Click `+`->`🧊 New Project`
- Enter a mod name and short summary of your intended mod
- Leave the URL (aka "mod slug") as default (`name-of-your-mod` in kebab case)
- Click `+ Create Project`

If the mod name is taken, come up with a new name!

### Copy the template

On the [template repo page](https://github.com/ModFest/template):
- Click `Use this template`->`Create a new repository`
- Check `Include all branches`
- Type your **mod slug** under "repository name"
  - This should match your modrinth URL
- Click `Create repository`
- Click `About ⚙️` and enter a short mod summary

### Clean and clone the repo

On your **mod repo page**:
- Click `⚙️ Settings`->`Default branch`->`⇄ (Switch to another branch)`
- Select your desired target version, and click `Update`->`I understand`
- Click the `< > Code` tab to return to the main repo page
- Click `🔌 X Branches`, above the file view
- Click `🗑️ (Delete Branch)` on `readme` and all other undesired branches
- Click the green `< > Code` button and clone the repo locally using `git` or `gh` as indicated
- Open the cloned folder in your preferred IDE, e.g. [IntelliJ IDEA CE](https://www.jetbrains.com/idea/download#community-edition)

### Correct the metadata

In your IDE:
- Open `gradle.properties` and replace the following:
  - `username`(x2) with your github username (as it appears in your repo URL)
  - `slug` to your mod slug (as it appears in github and modrinth URLs)
  - `modId` to your mod ID (in snake case, e.g. `name_of_your_mod` - not too long!)
  - `modDescription` to your short mod summary
  - `authors` to your handle/name, along with any other authors (comma-space-separated - `me, you, another`)
  - `contributors` to anyone who helped you (comma-space-separated - `me, you, another`), or just blank (`contributors=`)
- Rename the following using your IDE rename tools (Shift+F6 in IntelliJ):
  - rename `src/main/java/io/github/username/mod_id/ModId.java` from `ModId` to your mod ID (in PascalCase)
  - replace **mod_id** with your mod ID (in snake_case) in:
    - `src/main/java/io/github/username/mod_id`
    - `src/main/resources/mod_id.mixins.json`
    - `src/main/resources/assets/mod_id`
  - replace **username** with your github username (in snake_case) in:
    - `src/main/java/io/github/username`
- In `****.mixins.json`, correct `username.mod_id` to match the new file path
- In `fabric.mod.json`, correct `username.mod_id.ModId` to match the new file path/name
- Run `./gradlew runClient` to validate the mod still launches
- Commit and push these changes (Ctrl+K in IntelliJ)

### Develop your mod

*draw the rest of the owl*

Why not try [Introduction to Fabric and Modding](https://docs.fabricmc.net/develop/getting-started/introduction-to-fabric-and-modding)? Note that, for this template:
- Metadata usually in `fabric.mod.json` has been migrated to `gradle.properties` for easy editing
- Dependency versions usually in `build.gradle` are in `libs.versions.toml` for programmatic use 
  - You can define further any dependencies in `build.gradle` for simplicity if desired 
- You should replace `sec/main/resources/assets/****/icon.png` with your own icon (MS Paint is fine!)
- After changing a part of the mod, use `./gradlew runClient` to test it, then push a commit describing your changes!

### Enable Modrinth publishing

On Modrinth's [Personal access tokens](https://modrinth.com/settings/pats) page:
- Click `+ Create a PAT`
- Enter `Github Actions` as a name
- Check `Create versions` and `Write projects`
- Set the expiry to 1 year from the current date
- Click `+ Create PAT`
- Click the `📋` button that appears with a long secret string

On your **mod repo page**:
- Click `⚙️ Settings`->`Security`->`Secrets and variables`->`Actions`
- Click `New Repository Secret`
- Enter `MODRINTH_TOKEN` as the name, and paste your secret string from modrinth
- Click `Add Secret`

### Make a release

On your **mod repo page**:
- Click `Releases` on the right pane
- Click `Draft a new release`
- Click `Choose a tag`, and enter the `modVersion` set in `gradle.properties`, then click `+ Create a new tag`
  - If you're using multiple branches to support multiple versions, you should use `modVersion+branchName` instead
- Enter a description of your changes, e.g. `Initial Release` if this is the first release
- Click `Generate release notes` to append useful links
- Click `Publish release` - *without* attaching any files!

After a few minutes:
- The mod jar will appear on the github release page
- A matching release will be created on the modrinth page.
 
You can review this process on the repo page under `▶️ Actions`.