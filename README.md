# A collection of Spigot plugins for Minecraft.

## BasicNeeds
### Features
- Welcomes new players and greets returning players on join.
- Protects the spawn area to only allow edits for users with the basicneeds.spawnadmin permission.
- Temporarily bans users for 15 minutes upon death.
- Players will drop their heads upon death.
- Spawners can be mined using a tool with the enchantment Silk Touch.
- Players cannot starve to death.
### Commands
- **/sethome**: Sets the users home.
- **/home:** Teleports the user to their home location.
- **/spawn**: Teleports the user to the spawn location.
- **/requesttp <user>:** Sends a teleport request to a user.
- **/rtpaccept:** Accepts the previous teleport request.
- **/rtpdeny:** Denies the previous teleport request.
- **/back:** Teleports the player to their previous location.
- **/seen <user>:** Displays the last time a player was online.
## Ranks
### Commands
- **/ranks check <user> <permission>:** Checks if a given user has access to the provided permission.
- **/ranks permission add <user> <permission>:** Adds the given permission to the provided user.
- **/ranks permission remove <user> <permission>:** Removes the given permission from the provided user.
- **/ranks group <group>:** If the group doesn't exist the group is created. Otherwise, the groups permissions are displayed.
- **/ranks group <group> add <player>:** Add a player to the given group.
- **/ranks group <group> remove <player>:** Remove a player from the given group.
- **/ranks group <group> addpermission <permission>:** Add a given permission to a group.
- **/ranks group <group> removepermission <permission>:** Remove a given permission from a group.
## Scuba
### Commands
- **/scuba:** When holding a piece of glass, equip it as scuba gear. Granting water-breathing, night-vision, and slowness until removal.
## BetterPets
### Features
- Allows players to breed cats and dogs regardless of owner
- The bred animal will belong to the player who initated the breeding
- Child animals will inherit type/variant based upon the parent animals

